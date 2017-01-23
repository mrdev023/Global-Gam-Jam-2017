package globalgamejam.world;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.audio.Audio;
import globalgamejam.game.Coffre;
import globalgamejam.game.EObjetType;
import globalgamejam.game.EffectEnum;
import globalgamejam.game.MainGame;
import globalgamejam.game.Mur;
import globalgamejam.game.Objet;
import globalgamejam.game.Player;
import globalgamejam.input.Input;
import globalgamejam.math.Mathf;
import globalgamejam.math.Vector2f;
import globalgamejam.tiles.CoffreTile;
import globalgamejam.tiles.EffectTile;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.Tile;
import globalgamejam.tiles.VaguesTile;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private ArrayList<Tile> arrierePlan;
    private ArrayList<Tile> objetPlan;
    private ArrayList<Tile> premierPlan;

    private MainGame game;
    
    private Player player1,player2;
    private VaguesTile vagues;
    private float vaguesValue;
    
    private ArrayList<Objet> listObjet;
    private ArrayList<Mur> listMur;
    private Mur mur1,mur2,mur3,murGauche,murDroit,murHaut,murBas;
    private long tempsEntreVague,TempsAncienneVague;
    private boolean etatVague;
    
    private float maxVague = 0;
    private boolean maxVagueAtteint = false;
    private boolean despawnVagueACalculer = false;

	private int nextVagueHeight;
	
	private Coffre coffre;
	private int compteurVague;
	private boolean vagueCoffre;
	
	private EffectEnum actualEffect;
	private EffectTile effectTileJ1;
	private EffectTile effectTileJ2;
	private long timeStartEffect;
	private long durationEffect;
	private long timeElapsedEffect;

    public MainWorld(MainGame game){
        this.game = game;
        init();
    }

    public void init(){
    	
    	try {
			if(game.audioBackground != null){
				game.audioBackground.pauseSound();
			}
    		game.audioBackground = game.audioBennyHill;
    		game.audioBackground.rewindSound();
		} catch (Exception e) {}
    	
        arrierePlan = new ArrayList<>();
        objetPlan = new ArrayList<>();
        premierPlan = new ArrayList<>();
        listObjet = new ArrayList<>();
        listMur = new ArrayList<>();
    	player1 = new Player("res/textures/perso.png", Main.WIDTH/4-20, 150);
        player2 = new Player("res/textures/perso2.png", Main.WIDTH/4 * 3-20, 150);

		Fond fond = new Fond("res/textures/sand.jpg");
		fond.getTransform().translate(Main.WIDTH/2, Main.HEIGHT/2, 0);
		fond.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
		
		vagues = new VaguesTile("res/textures/vagues.png");
		vagues.getTransform().translate(Main.WIDTH/2, -Main.HEIGHT/2, 0);
		vagues.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);

		this.murGauche = new Mur(0,Main.HEIGHT/2+40,"res/textures/murcoté.png");
		this.murDroit = new Mur(Main.WIDTH,Main.HEIGHT/2+40,"res/textures/murcoté.png");
		this.murHaut = new Mur(Main.WIDTH/2,Main.HEIGHT+10,"res/textures/murhauteur.png");
		this.murBas = new Mur(Main.WIDTH/2,80,"res/textures/murbas.png");
		this.mur1 = new Mur(Main.WIDTH/2,Main.HEIGHT-20,"res/textures/murmilieuhaut.png");
		this.mur2 = new Mur(Main.WIDTH/2, Main.HEIGHT/2+30 ,"res/textures/murmilieumilieu.png");
		this.mur3 = new Mur(Main.WIDTH/2, 100, "res/textures/murmilieubas.png");
		arrierePlan.add(fond);
		
		arrierePlan.add(mur1.getTile());
		arrierePlan.add(mur2.getTile());
		arrierePlan.add(mur3.getTile());
		arrierePlan.add(murGauche.getTile());
		arrierePlan.add(murDroit.getTile());
		arrierePlan.add(murHaut.getTile());
		arrierePlan.add(murBas.getTile());

        listMur.add(mur1);
        listMur.add(mur2);
        listMur.add(mur3);
        listMur.add(murGauche);
        listMur.add(murDroit);
        listMur.add(murHaut);
        listMur.add(murBas);
        
        premierPlan.add(vagues);
        
        premierPlan.add(player1.getTile());
        premierPlan.add(player2.getTile());
        
        tempsEntreVague=0;
        TempsAncienneVague=System.currentTimeMillis();
        etatVague =false;
        
        maxVague = 0;
        maxVagueAtteint = false;
        despawnVagueACalculer = false;
        compteurVague=0;
        vagueCoffre = true;
        actualEffect = null;

        effectTileJ1 = new EffectTile("res/textures/default_transp.png", 100, 583);
        effectTileJ2 = new EffectTile("res/textures/default_transp.png", 500, 583);
        premierPlan.add(effectTileJ1);
        premierPlan.add(effectTileJ2);
        
        genererBonusMalus(4);
        
        game.audioBackground.playSound();
    }

    public void update(){
    	
    	if(actualEffect != null){
    		timeElapsedEffect = System.currentTimeMillis() - timeStartEffect;
    		if(timeElapsedEffect >= durationEffect){
    			actualEffect = null;
    			effectTileJ1.clear();
    			effectTileJ2.clear();
    			game.audioEffect.pauseSound();
    			game.audioBackground.setGain(0.4f);
    		}
    	}
    	
    	boolean joysticksHelp = false;
    	if(Input.getJoysticks().size() > 0){
    		try{
    			ByteBuffer b = Input.getJoysticksButton(0);
        		if(b.get(3) == 1)joysticksHelp = true;
    		}catch(Exception e){}
    	}
    	this.moveObjets();
    	
    	if(!Input.isKey(this.game.HELP_KEY) && !joysticksHelp){
    		//Player 1
    		boolean keyBoard1Enable = false;
        	float xDep = 0, yDep = 0;
    		if(Input.getJoysticks().size() > 0){
    			try{
    				FloatBuffer bufferAxis = Input.getJoysticksAxis(0);
        			xDep = bufferAxis.get(0) *  player1.getSpeed();
        			yDep = bufferAxis.get(1) *  player1.getSpeed();
        			
        			/* if(xDep != 0.0 && yDep != 0.0){
             	    	xDep *= Math.cos(Math.PI / 4);
             	    	yDep *= Math.cos(Math.PI / 4);
             	    }*/
        			 float rot = player1.getSpeed() * -bufferAxis.get(2) / 2.0f;
        			 if(actualEffect == EffectEnum.INVERSE_COMMAND_J1){
        				 xDep *= -1;
        				 yDep *= -1;
        				 rot *= -1;
        			 }
        			 
        			 if(actualEffect != EffectEnum.HEAVY_J1){
        				 player1.move(xDep, yDep);
        			 }
             	    
             	   player1.rotate(rot);
    			}catch(Exception e){
    				keyBoard1Enable = true;
    			}
    		}else{
    			keyBoard1Enable = true;
    		}
    		if(keyBoard1Enable){
    			if(Input.isKey(GLFW.GLFW_KEY_W)){
//        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle() + 90));
//        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle() + 90));
        	    	yDep = player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_S)){
//        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle() - 90));
//        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle() - 90));
        	    	yDep = -player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_A)){
        	    	xDep = -player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_D)){
        	    	xDep = player1.getSpeed() / 2.0f;
        	    }
        	    
        	    if(xDep != 0.0 && yDep != 0.0){
        	    	xDep *= Math.cos(Math.PI / 4);
        	    	yDep *= Math.cos(Math.PI / 4);
        	    }
        	   
        	    
        	    float rot = 0;
        	    if(Input.isKey(GLFW.GLFW_KEY_Q)){
        	    	rot = player1.getSpeed()/2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_E)){
        	    	rot = -player1.getSpeed()/2.0f;
        	    }
	   			 if(actualEffect == EffectEnum.INVERSE_COMMAND_J1){
	   				 xDep *= -1;
	   				 yDep *= -1;
	   				 rot *= -1;
	   			 }
   			 
	   			if(actualEffect != EffectEnum.HEAVY_J1){
	   				player1.move(xDep, yDep);
	   			}
        	    
        	   player1.rotate(rot);
    		}
    		
    		if(player1.collideWithRoundHitBox(coffre)){
   				System.out.println("coffre collide J1");
   				coffre.makeEffect(1);
   				objetPlan.remove(coffre.getTile());
   				coffre = null;
    		}

    	    //Player 2
    		boolean keyBoard2Enable = false;
            xDep = 0;
    	    yDep = 0;
    	    if(Input.getJoysticks().size() > 1){
    			try{
    				FloatBuffer bufferAxis = Input.getJoysticksAxis(1);
        			xDep = bufferAxis.get(0) *  player2.getSpeed();
        			yDep = bufferAxis.get(1) *  player2.getSpeed();
        			
        			/* if(xDep != 0.0 && yDep != 0.0){
             	    	xDep *= Math.cos(Math.PI / 4);
             	    	yDep *= Math.cos(Math.PI / 4);
             	    }*/
        			 
        			 float rot = player2.getSpeed() * -bufferAxis.get(2) / 2.0f;
       			 if(actualEffect == EffectEnum.INVERSE_COMMAND_J2){
    				 xDep *= -1;
    				 yDep *= -1;
    				 rot *= -1;
    			 }
    			 
       			if(actualEffect != EffectEnum.HEAVY_J2){
       				player2.move(xDep, yDep);
       			}
         	    
         	   player2.rotate(rot);
    			}catch(Exception e){
    				keyBoard2Enable = true;
    			}
    		}else{
    			keyBoard2Enable = true;
    		}
    	    
    	    if(keyBoard2Enable){
    	    	if(Input.isKey(GLFW.GLFW_KEY_I)){
//        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() + 90));
//        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() + 90));
    	    		yDep = player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_K)){
//        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() - 90));
//        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() - 90));
        	    	yDep = -player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_J)){
//        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() - 180));
//        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() - 180));
        	    	xDep = -player1.getSpeed() / 2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_L)){
//        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle()));
//        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle()));
        	    	xDep = player1.getSpeed() / 2.0f;
        	    }

                if(xDep != 0.0 && yDep != 0.0){
                    xDep *= Math.cos(Math.PI / 4);
                    yDep *= Math.cos(Math.PI / 4);
                }

                float rot = 0;
        	    if(Input.isKey(GLFW.GLFW_KEY_U)){
        	    	rot = player2.getSpeed()/2.0f;
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_O)){
        	    	rot = -player2.getSpeed()/2.0f;
        	    }
	   			 if(actualEffect == EffectEnum.INVERSE_COMMAND_J2){
	   				 xDep *= -1;
	   				 yDep *= -1;
	   				 rot *= -1;
	   			 }
   			 
	   			if(actualEffect != EffectEnum.HEAVY_J2){
       				player2.move(xDep, yDep);
       			}
        	    
        	   player2.rotate(rot);
    	    }
    	    
    	    if(player2.collideWithRoundHitBox(coffre)){
    	    	System.out.println("coffre collide J2");
   				coffre.makeEffect(2);
   				objetPlan.remove(coffre.getTile());
   				coffre = null;
    		}
    	    
    	    if(!Input.isKey(this.game.HELP_KEY) && this.game.scores[0] > 0 && this.game.scores[1] > 0 && MainGame.time_in_sec > 0){
	            if(actualEffect != EffectEnum.SCORE_FREEZE){
	    	    	for(Objet o : this.listObjet){
		            	
	    	    		float gainScore = o.getType().getPoints() * Main.delta;
	    	    		
		            	if(o.getTile().getPosition().x < Main.WIDTH/2.0f){
		            		
		            		if(actualEffect == EffectEnum.MUSIC_MULTIPLICATOR_J1){
		            			gainScore = (float)Math.max(gainScore, 2.0);
		            			gainScore *= 1.5;
		            		}
		            		
		            		this.game.scores[0] += gainScore;
		            	}else{
		            		
		            		if(actualEffect == EffectEnum.MUSIC_MULTIPLICATOR_J2){
		            			gainScore = (float)Math.max(gainScore, 0.0);
		            			gainScore *= 1.5;
		            		}
		            		
		            		this.game.scores[1] += gainScore;
		            	}
		            }
	            }
    	    }
            
            ArrayList<Objet> listObjetADespawn = new ArrayList<>();
    	    
            if(despawnVagueACalculer){
            	despawnVagueACalculer = false;
            	
            	for(Objet o : this.listObjet){
            		if(o.underWave(this.vagues.getPosition().y + this.vagues.getTexture().height / 2)){
        	    		if(o.shouldDespawn()){
        	    			listObjetADespawn.add(o);
        	    		}
        	    	}
            	}
            	
            	for(Objet o : listObjetADespawn){
        			this.objetPlan.remove(o.getTile());
        	    	this.listObjet.remove(o);
        	    }
            	
            	genererBonusMalus((int)(Math.random() * 4) + 2);
            	GenererCoffre();
            }
            
            for(Objet o : this.listObjet){
    	    	
    	    	if(player1.brosseCollideWith(o)){
    	    		o.resolveCollideWith(player1.getBrosse());
    	    	}
    	    	
    	    	if(player2.brosseCollideWith(o)){
    	    		o.resolveCollideWith(player2.getBrosse());
    	    	}
    	    	
    	    	for(Objet o2 : this.listObjet){
    	    		if(!o.equals(o2) && o.collideWith(o2)){
    	    			o.resolveCollideWith(o2);
    	    		}
    	    	}
    	    	
    	    	for(Mur m : this.listMur){
    	    		if(o.collideWith(m)){
    	    			o.resolveCollideWith(m);
    	    		}
    	    	}
    	    }
    	}
    	if(System.currentTimeMillis()-TempsAncienneVague>=tempsEntreVague){
    	etatVague = vagues.getPosition().y>-224.0f;
		vaguesValue += Main.delta * 2;
		applyVaguesCoeff((Math.cos(vaguesValue)>0.5)?
			((float)Math.cos(vaguesValue)-0.5f)*2:0,(int)Main.HEIGHT/8,nextVagueHeight);
			if(vagues.getPosition().y==-225.0f && etatVague){
				tempsEntreVague = (long) (Math.random() * 5000 + 5000);
		        TempsAncienneVague=System.currentTimeMillis();
		        etatVague =false;
		        nextVagueHeight = (int)(Math.random() * (Main.HEIGHT/4f*3f - 160f) + 160f);
		        compteurVague++;
		        System.out.println(compteurVague);
			}
    	}

    	
    }

    public void render(){
    	boolean joysticksHelp = false;
    	if(Input.getJoysticks().size() > 0){
    		try{
    			ByteBuffer b = Input.getJoysticksButton(0);
        		if(b.get(3) == 1)joysticksHelp = true;
    		}catch(Exception e){}
    	}
    	if(!Input.isKey(this.game.HELP_KEY) && this.game.scores[0] > 0 && this.game.scores[1] > 0 && MainGame.time_in_sec > 0 && !joysticksHelp){
    		for(Tile t : arrierePlan)t.render();
    		if(coffre!=null){
    			//System.out.println("dd");
    			coffre.getTile().render();
    		}
    		for(Tile t : objetPlan)t.render();
    		for(Tile t : premierPlan)t.render();
    	}
    }

    public void destroy(){
        for(Tile t : arrierePlan)t.destroy();
        arrierePlan.clear();
        for(Tile t : objetPlan)t.destroy();
        objetPlan.clear();
        for(Tile t : premierPlan)t.destroy();
        premierPlan.clear();
        for(Objet t : listObjet)t.getTile().destroy();;
        listObjet.clear();
        for(Mur t : listMur)t.getTile().destroy();
        listMur.clear();
        
    }
    
    public void genererBonusMalus(int nombre){
    	int minWidth = 80;
    	int maxWidth = Main.WIDTH - minWidth;
    	EObjetType[] types = EObjetType.values();
    	for(int i = 0;i < nombre;i++){
    		EObjetType type = types[(int)(Math.random() * types.length)];
    		Objet o = new Objet(type.getFilename(), (float)(Math.random() * (maxWidth - minWidth)) + minWidth, 150, 0, 0, 5, 0.02f);
    		o.setType(type);
    		objetPlan.add(o.getTile());
    		listObjet.add(o);
    	}
    }
    
    private void applyVaguesCoeff(float coeff,int offset,int height){
    	vagues.getTransform().loadIdentity();
    	vagues.setPosition(new Vector2f(Main.WIDTH/2, -Main.HEIGHT/2 + height*coeff + offset));
    	vagues.setScale(new Vector2f(Main.WIDTH,Main.HEIGHT));
    	vagues.applyTransform();
    	
    	if(vagues.getPosition().y >= this.maxVague){
    		this.maxVague = vagues.getPosition().y;
    	}
    	else if(!this.maxVagueAtteint){
    		this.maxVagueAtteint = true;
    		
    		this.despawnVagueACalculer = true;
    	}
    	else if(vagues.getPosition().y == -225.0){
    		this.maxVague = -225.0f;
    		this.maxVagueAtteint = false;
    	}
    	
    }
    
    private void moveObjets(){
    	for(Objet o : this.listObjet){
    		o.move();
    	}
    }
    
    private void inverserObjetGaucheDroite(){
    	for(Objet o : this.listObjet){
    		
    		float distMilieu = Main.WIDTH/2.0f - o.getX();
    			
    		o.setPosition(Main.WIDTH/2.0f + distMilieu, o.getY());
			
			o.setVelocity(-o.getxVelocity(), o.getyVelocity());
    	}
    }
    
    
    public void GenererCoffre(){
    	
    	if(coffre != null){
    		objetPlan.remove(coffre.getTile());
    		coffre = null;
    	}
    	
    	final float ChancePop = 0.6f;
    	int minHeight = 150;
    	int maxHeight = Main.HEIGHT - 50;
    	int minWidth = 80;
    	int maxWidth = Main.HEIGHT - minWidth;
    	if(!vagueCoffre){
    		vagueCoffre=true;
    	}
    	else{
    		if(Math.random()>=1-ChancePop&& compteurVague>=4){
        		System.out.println("dfjkl");
        		vagueCoffre = false;
        		coffre = new Coffre("res/textures/coffre.png",(float) (Math.random() *(maxWidth-minWidth) + minWidth),(float) (Math.random() *(maxHeight-minHeight) + minHeight)){
        		
        			@Override
        			public void makeEffect(int numPlayerHitCoffre){
    					effectTileJ1.clear();
    					effectTileJ2.clear();
        				
        				double effectRand = Math.random();
        				
        				if(effectRand < 0.2){
        					// effet 1 avec 20% de chance
        					actualEffect = EffectEnum.INVERSE_SCREEN;
        					inverserObjetGaucheDroite();
        				}
        				else if(effectRand < 0.4){
        					// effet 2 avec 20% de chance
        					if(numPlayerHitCoffre == 1){
        						actualEffect = EffectEnum.MUSIC_MULTIPLICATOR_J1;
        						effectTileJ1.setTexture("res/textures/noteMusic.png");
        					}
        					else{
        						actualEffect = EffectEnum.MUSIC_MULTIPLICATOR_J2;
        						effectTileJ2.setTexture("res/textures/noteMusic.png");
        					}
        					if(game.audioEffect != null){
        						game.audioEffect.pauseSound();
        						game.audioEffect.rewindSound();
        					}
    						try{
    							game.audioBackground.setGain(0.1f);
    							game.audioEffect = game.audioMacarena;
    							game.audioEffect.playSound();
    						} catch(Exception e){}
        				}
        				else if(effectRand < 0.6){
        					// effet 3 avec 20% de chance
        					if(numPlayerHitCoffre == 1){
        						actualEffect = EffectEnum.INVERSE_COMMAND_J2;
        						effectTileJ2.setTexture("res/textures/inverser.png");
        					}
        					else{
        						actualEffect = EffectEnum.INVERSE_COMMAND_J1;
        						effectTileJ1.setTexture("res/textures/inverser.png");
        					}
        				}
        				else if(effectRand < 0.8){
        					// effet 4 avec 20% de chance
        					actualEffect = EffectEnum.SCORE_FREEZE;
    						effectTileJ1.setTexture("res/textures/gel.png");
    						effectTileJ2.setTexture("res/textures/gel.png");
    						
        				}
        				else{
        					// effet 5 avec 20% de chance
        					if(numPlayerHitCoffre == 1){
        						actualEffect = EffectEnum.HEAVY_J2;
        						effectTileJ2.setTexture("res/textures/stop.png");
        					}
        					else{
        						actualEffect = EffectEnum.HEAVY_J1;
        						effectTileJ1.setTexture("res/textures/stop.png");
        					}
        				}
        				
        				durationEffect = 7_000; // ms
        				timeStartEffect = System.currentTimeMillis();
        			}
        			
        		};
    	}
    		
    	}
  
    	    	
    }
}

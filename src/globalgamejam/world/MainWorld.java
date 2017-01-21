package globalgamejam.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.game.EObjetType;
import globalgamejam.game.MainGame;
import globalgamejam.game.Mur;
import globalgamejam.game.Objet;
import globalgamejam.game.Player;
import globalgamejam.input.Input;
import globalgamejam.math.Mathf;
import globalgamejam.math.Vector2f;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.Tile;
import globalgamejam.tiles.VaguesTile;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private ArrayList<Tile> tiles;

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

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<>();
        listObjet = new ArrayList<>();
        listMur = new ArrayList<>();
        init();
    }

    public void init(){
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
		tiles.add(fond);
		
        tiles.add(mur1.getTile());
        tiles.add(mur2.getTile());
        tiles.add(mur3.getTile());
		tiles.add(murGauche.getTile());
		tiles.add(murDroit.getTile());
		tiles.add(murHaut.getTile());
		tiles.add(murBas.getTile());


        
        
        
        listMur.add(mur1);
        listMur.add(mur2);
        listMur.add(mur3);
        listMur.add(murGauche);
        listMur.add(murDroit);
        listMur.add(murHaut);
        listMur.add(murBas);
        
        tiles.add(vagues);
        
		tiles.add(player1.getTile());
        tiles.add(player2.getTile());
        
        tempsEntreVague=0;
        TempsAncienneVague=System.currentTimeMillis();
        etatVague =false;
        
        maxVague = 0;
        maxVagueAtteint = false;
        despawnVagueACalculer = false;

        genererBonusMalus(3);
        
    }

    public void update(){
    	
    	this.moveObjets();
    	
    	if(!Input.isKey(this.game.helpKey)){
    		//Player 1
    		boolean keyBoard1Enable = false;
        	float xDep = 0, yDep = 0;
    		if(Input.getJoysticks().size() > 0){
    			try{
    				FloatBuffer bufferAxis = Input.getJoysticksAxis(0);
        			xDep = bufferAxis.get(0) *  player1.getSpeed();
        			yDep = bufferAxis.get(1) *  player1.getSpeed();
        			
        			 if(xDep != 0.0 && yDep != 0.0){
             	    	xDep *= Math.cos(Math.PI / 4);
             	    	yDep *= Math.cos(Math.PI / 4);
             	    }
             	   
             	    player1.move(xDep, yDep);
             	    
             	   player1.rotate(player1.getSpeed() * -bufferAxis.get(2) / 2.0f);
    			}catch(Exception e){
    				keyBoard1Enable = true;
    			}
    		}else{
    			keyBoard1Enable = true;
    		}
    		
    		if(keyBoard1Enable){
    			if(Input.isKey(GLFW.GLFW_KEY_W)){
        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle() + 90));
        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle() + 90));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_S)){
        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle() - 90));
        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle() - 90));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_A)){
        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle() - 180));
        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle() - 180));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_D)){
        	    	xDep = player1.getSpeed() * Mathf.cos(Mathf.toRadians(player1.getAngle()));
        	    	yDep = player1.getSpeed() * Mathf.sin(Mathf.toRadians(player1.getAngle()));
        	    }
        	    
        	    if(xDep != 0.0 && yDep != 0.0){
        	    	xDep *= Math.cos(Math.PI / 4);
        	    	yDep *= Math.cos(Math.PI / 4);
        	    }
        	   
        	    player1.move(xDep, yDep);
        	    
        	    if(Input.isKey(GLFW.GLFW_KEY_Q)){
        	    	player1.rotate(player1.getSpeed());
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_E)){
        	    	player1.rotate(-player1.getSpeed());
        	    }
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
        			
        			 if(xDep != 0.0 && yDep != 0.0){
             	    	xDep *= Math.cos(Math.PI / 4);
             	    	yDep *= Math.cos(Math.PI / 4);
             	    }
             	   
             	    player2.move(xDep, yDep);
             	    
             	   player2.rotate(player2.getSpeed() * -bufferAxis.get(2) / 2.0f);
    			}catch(Exception e){
    				keyBoard2Enable = true;
    			}
    		}else{
    			keyBoard2Enable = true;
    		}
    	    
    	    if(keyBoard2Enable){
    	    	if(Input.isKey(GLFW.GLFW_KEY_I)){
        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() + 90));
        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() + 90));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_K)){
        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() - 90));
        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() - 90));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_J)){
        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle() - 180));
        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle() - 180));
        	    }
        	    if(Input.isKey(GLFW.GLFW_KEY_L)){
        	    	xDep = player2.getSpeed() * Mathf.cos(Mathf.toRadians(player2.getAngle()));
        	    	yDep = player2.getSpeed() * Mathf.sin(Mathf.toRadians(player2.getAngle()));
        	    }

                if(xDep != 0.0 && yDep != 0.0){
                    xDep *= Math.cos(Math.PI / 4);
                    yDep *= Math.cos(Math.PI / 4);
                }

                
                player2.move(xDep, yDep);

                if(Input.isKey(GLFW.GLFW_KEY_U)){
                    player2.rotate(player2.getSpeed());
                }
                if(Input.isKey(GLFW.GLFW_KEY_O)){
                    player2.rotate(-player2.getSpeed());
                }
    	    }
    	    
            for(Objet o : this.listObjet){
            	
            	if(o.getTile().getPosition().x < Main.WIDTH/2.0f){
            		this.game.scores[0] += o.getType().getPoints() * Main.delta;
            	}else{
            		this.game.scores[1] += o.getType().getPoints() * Main.delta;
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
        			this.tiles.remove(o.getTile());
        	    	this.listObjet.remove(o);
        	    }
            	
            	genererBonusMalus((int)(Math.random() * 4) + 2);
            }
            
            for(Objet o : this.listObjet){
    	    	
    	    	if(o.underWave(this.vagues.getPosition().y + this.vagues.getTexture().height / 2)){
    	    		if(o.shouldDespawn()){
    	    			listObjetADespawn.add(o);
    	    		}
    	    	}
    	    	else{
    	    	
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
    	}
    	if(System.currentTimeMillis()-TempsAncienneVague>=tempsEntreVague){
    	etatVague = vagues.getPosition().y>-224.0f;
		vaguesValue += Main.delta * 2;
		applyVaguesCoeff((Math.cos(vaguesValue)>0.5)?
			((float)Math.cos(vaguesValue)-0.5f)*2:0,(int)Main.HEIGHT/8,nextVagueHeight);
			if(vagues.getPosition().y==-225.0f && etatVague){
				System.out.println("aaaaaaa");
				tempsEntreVague = (long) (Math.random() * 5000 + 5000);
		        TempsAncienneVague=System.currentTimeMillis();
		        etatVague =false;
		        nextVagueHeight = (int)(Math.random() * (Main.HEIGHT/4f*3f - 160f) + 160f);
			}
    	}

    	
    }

    public void render(){
    	if(!Input.isKey(this.game.helpKey) && this.game.scores[0] > 0 && this.game.scores[1] > 0 && MainGame.time_in_sec > 0){
    		for(Tile t : tiles)t.render();
    	}
    }

    public void destroy(){
        for(Tile t : tiles)t.destroy();
        tiles.clear();
    }
    
    public void genererBonusMalus(int nombre){
    	int minWidth = 50;
    	int minHeight = Main.WIDTH - minWidth;
    	EObjetType[] types = EObjetType.values();
    	for(int i = 0;i < nombre;i++){
    		EObjetType type = types[(int)(Math.random() * types.length)];
    		Objet o = new Objet(type.getFilename(), (float)(Math.random() * (minHeight - minWidth)), 150, 0, 0, 5, 0.02f);
    		o.setType(type);
    		tiles.add(o.getTile());
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
    

}

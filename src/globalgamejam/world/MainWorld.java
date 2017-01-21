package globalgamejam.world;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.game.MainGame;
import globalgamejam.game.Objet;
import globalgamejam.game.Player;
import globalgamejam.input.Input;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.Mur;
import globalgamejam.tiles.ObjetTile;
import globalgamejam.tiles.Tile;
import globalgamejam.tiles.VaguesTile;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private ArrayList<Tile> tiles;

    private MainGame game;
    
    private Player player1,player2;
    private Mur mur1,mur2,mur3,murGauche,murDroit,murHaut,murBas;
    private VaguesTile vagues;
    private float vaguesValue;
    
    private ArrayList<Objet> listObjet;

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<>();
        listObjet = new ArrayList<>();
        init();
    }

    public void init(){
    	player1 = new Player(200, 150);
        player2 = new Player(400, 150);


		Fond fond = new Fond("res/textures/fond.png");
		fond.getTransform().translate(Main.WIDTH/2, Main.HEIGHT/2, 0);
		fond.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
		
		vagues = new VaguesTile("res/textures/vagues.png");
		vagues.getTransform().translate(Main.WIDTH/2, -Main.HEIGHT/2, 0);
		vagues.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
		
		this.mur1 = new Mur(Main.WIDTH/2-10, Main.HEIGHT - 50, 20, 150,"res/textures/mur.png");
		this.mur2 = new Mur(Main.WIDTH/2-10, Main.HEIGHT - 250, 20, 50,"res/textures/mur.png");
		this.mur3 = new Mur(Main.WIDTH/2-10, Main.HEIGHT - 400, 20, 150,"res/textures/mur.png");
		this.murGauche=new Mur(0,Main.HEIGHT/2+40,30,Main.HEIGHT-80,"res/textures/murcoté.png");
		this.murDroit=new Mur(Main.WIDTH,Main.HEIGHT/2+40,30,Main.HEIGHT-80,"res/textures/murcoté.png");
		this.murHaut=new Mur(Main.WIDTH/2,Main.HEIGHT,Main.WIDTH,70,"res/textures/murhauteur.png");
		this.murBas=new Mur(Main.WIDTH/2,80,Main.WIDTH,20,"res/textures/murhauteur.png");
		tiles.add(fond);
		tiles.add(vagues);
		tiles.add(murGauche);
		tiles.add(murDroit);
		tiles.add(murHaut);
		tiles.add(murBas);
        tiles.add(this.mur1);
        tiles.add(this.mur2);
        tiles.add(this.mur3);
		tiles.add(player1.getTile());
        tiles.add(player2.getTile());

        
		
		generateEntity(3);
    }

    public void update(){
    	
    	this.moveObjets();
    	
    	if(!Input.isKey(this.game.helpKey)){
    		//Player 1
        	float xDep = 0, yDep = 0;
    	    if(Input.isKey(GLFW.GLFW_KEY_W) && player1.getTile().getPosition().y + player1.getTile().getScale().y/2.0f <= Main.HEIGHT - murHaut.getScale().y/2.0f){
    	    	yDep = player1.getSpeed();
    	    }
    	    if(Input.isKey(GLFW.GLFW_KEY_S) && player1.getTile().getPosition().y - player1.getTile().getScale().y/2.0f >= murBas.getScale().y/2.0f + murBas.getPosition().y){
    	    	yDep = -player1.getSpeed();
    	    }
    	    if(Input.isKey(GLFW.GLFW_KEY_A) && player1.getTile().getPosition().x - player1.getTile().getScale().x/2.0f >= 0.0f + murGauche.getScale().getX()/2.0f){
    	    	xDep = -player1.getSpeed();
    	    }
    	    if(Input.isKey(GLFW.GLFW_KEY_D) && player1.getTile().getPosition().x + player1.getTile().getScale().x/2.0f <= Main.WIDTH/2.0f){
    	    	xDep = player1.getSpeed();
    	    }
    	    
    	    if(xDep != 0.0 && yDep != 0.0){
    	    	xDep *= Math.cos(Math.PI / 4);
    	    	yDep *= Math.cos(Math.PI / 4);
    	    }
    	   
    	    player1.move(xDep, yDep);
    	    
    	    if(Input.isKey(GLFW.GLFW_KEY_Q)){
    	    	player1.rotate(5);
    	    }
    	    if(Input.isKey(GLFW.GLFW_KEY_E)){
    	    	player1.rotate(-5);
    	    }
    	    
    	    for(Objet o : this.listObjet){
    	    	if(player1.brosseCollideWith(o)){
    	    		o.resolveCollideWith(player1.getBrosse());
    	    	}
    	    	
    	    	for(Objet o2 : this.listObjet){
    	    		if(!o.equals(o2) && o.collideWithSquareHitBox(o2)){
    	    			o.resolveCollideWith(o2);
    	    		}
    	    	}
    	    }

    	    //Player 2
            xDep = 0;
    	    yDep = 0;
            if(Input.isKey(GLFW.GLFW_KEY_I) && player2.getTile().getPosition().y + player2.getTile().getScale().y/2.0f <= Main.HEIGHT - murHaut.getScale().y/2.0f){
                yDep = player2.getSpeed();
            }
            if(Input.isKey(GLFW.GLFW_KEY_K) && player2.getTile().getPosition().y - player2.getTile().getScale().y/2.0f >= murBas.getScale().y/2.0f + murBas.getPosition().y){
                yDep = -player2.getSpeed();
            }
            if(Input.isKey(GLFW.GLFW_KEY_J ) && player2.getTile().getPosition().x - player2.getTile().getScale().x/2.0f >= Main.WIDTH/2.0f){
                xDep = -player2.getSpeed();
            }
            if(Input.isKey(GLFW.GLFW_KEY_L) && player2.getTile().getPosition().x + player2.getTile().getScale().x/2.0f <= Main.WIDTH - murDroit.getScale().getX()/2.0f){
                xDep = player2.getSpeed();
            }

            if(xDep != 0.0 && yDep != 0.0){
                xDep *= Math.cos(Math.PI / 4);
                yDep *= Math.cos(Math.PI / 4);
            }

            player2.move(xDep, yDep);

            if(Input.isKey(GLFW.GLFW_KEY_U)){
                player2.rotate(5);
            }
            if(Input.isKey(GLFW.GLFW_KEY_O)){
                player2.rotate(-5);
            }
    	}
    	vaguesValue += Main.delta * 2;
    	applyVaguesCoeff((Math.cos(vaguesValue)>0.5)?
    			((float)Math.cos(vaguesValue)-0.5f)*2:0,(int)Main.HEIGHT/8,Main.HEIGHT/4*3);
    	
    }

    public void render(){
    	if(!Input.isKey(this.game.helpKey)){
    		for(Tile t : tiles)t.render();
    	}
    }

    public void destroy(){
        for(Tile t : tiles)t.destroy();
        tiles.clear();
    }
    
    private void generateEntity(int nb){
		final int MIN_HAUTEUR_MAX=150;//gére la hauteur min de la hauteur maximum de la vague 
		final int MIN_HAUTEUR=80;//hauteur min que doit monter la vague
		final int MAX_HAUTEUR_MAX=Main.HEIGHT-80;//hauteur maximum que peut monter la vague au max
		
		
		int hauteurMax = (int) (MIN_HAUTEUR_MAX +Math.random()* MAX_HAUTEUR_MAX);
		int nbMin = 1;
		int nbMax = 1;
		
		if(hauteurMax<=MIN_HAUTEUR_MAX){
			nbMin=nb-1;
			nbMax=1;
		}
		if(hauteurMax>MIN_HAUTEUR_MAX && hauteurMax<Main.HEIGHT/2){
			nbMin=nb-1;
			nbMax=nb+1;
		}
		if(hauteurMax>=Main.HEIGHT/2){
			nbMin=1;
			nbMax=nb+1;
		}
		int countJ1=(int)(nbMin + Math.random()*nbMax);
		int countJ2=(int)(nbMin + Math.random()*nbMax);
		
		for(int i =0;i<countJ1;i++){
			
			Objet o = new Objet("",(float)(Math.random()* Main.WIDTH/2),(float) (MIN_HAUTEUR+Math.random()* hauteurMax), 50, 0, 0, 0f, 0.1f);
			
			listObjet.add(o);
			
			tiles.add(o.getTile());
		}
		
		for(int i =0;i<countJ2;i++){
			
			Objet o = new Objet("",(float)(Main.WIDTH/2+Math.random()* Main.WIDTH),(float) (MIN_HAUTEUR +Math.random()* hauteurMax), 50, 0, 0, 0f, 0.1f);
			
			listObjet.add(o);
			
			tiles.add(o.getTile());
		}
    }
    
    private void applyVaguesCoeff(float coeff,int offset,int height){
    	vagues.getTransform().loadIdentity();
    	vagues.getTransform().translate(Main.WIDTH/2, -Main.HEIGHT/2 + height*coeff + offset,0);
    	vagues.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
    }
    
    private void moveObjets(){
    	for(Objet o : this.listObjet){
    		o.move();
    	}
    }
}

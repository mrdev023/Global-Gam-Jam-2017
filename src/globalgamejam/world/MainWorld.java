package globalgamejam.world;

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
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.MurTile;
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
    private VaguesTile vagues;
    private float vaguesValue;
    
    private ArrayList<Objet> listObjet;
    private ArrayList<Mur> listMur;
    private Mur mur1,mur2,mur3,murGauche,murDroit,murHaut,murBas;

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<>();
        listObjet = new ArrayList<>();
        listMur = new ArrayList<>();
        init();
    }

    public void init(){
    	player1 = new Player(Main.WIDTH/4-20, 150);
        player2 = new Player(Main.WIDTH/4 * 3-20, 150);

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
		this.mur1 = new Mur(Main.WIDTH/2,Main.HEIGHT/2,"res/textures/mur.png");
		this.mur2 = new Mur(Main.WIDTH/2-10, Main.HEIGHT/2 - 250,"res/textures/mur.png");
		this.mur3 = new Mur(Main.WIDTH/2-10, Main.HEIGHT - 400, "res/textures/mur.png");
		tiles.add(fond);
		tiles.add(vagues);
        tiles.add(mur1.getTile());
        tiles.add(mur2.getTile());
        tiles.add(mur3.getTile());
		tiles.add(murGauche.getTile());
		tiles.add(murDroit.getTile());
		tiles.add(murHaut.getTile());
		tiles.add(murBas.getTile());

		tiles.add(player1.getTile());
        tiles.add(player2.getTile());

        listMur.add(mur1);
        listMur.add(mur2);
        listMur.add(mur3);
        listMur.add(murGauche);
        listMur.add(murDroit);
        listMur.add(murHaut);
        listMur.add(murBas);
		
        genererBonusMalus(3);
    }

    public void update(){
    	
    	this.moveObjets();
    	
    	if(!Input.isKey(this.game.helpKey)){
    		//Player 1
        	float xDep = 0, yDep = 0;
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

    	    //Player 2
            xDep = 0;
    	    yDep = 0;
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
    	    
    	    for(Objet o : this.listObjet){
    	    	if(player1.brosseCollideWith(o)){
    	    		o.resolveCollideWith(player1.getBrosse());
    	    		this.game.scores[0]-=10;
    	    	}
    	    	
    	    	if(player2.brosseCollideWith(o)){
    	    		o.resolveCollideWith(player2.getBrosse());
    	    		this.game.scores[1]-=10;
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
    	
    	vaguesValue += Main.delta * 2;
    	applyVaguesCoeff((Math.cos(vaguesValue)>0.5)?
    			((float)Math.cos(vaguesValue)-0.5f)*2:0,(int)Main.HEIGHT/8,Main.HEIGHT/4*3);
    	
    }

    public void render(){
    	if(!Input.isKey(this.game.helpKey) && this.game.scores[0] > 0 && this.game.scores[1] > 0){
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
    	vagues.getTransform().translate(Main.WIDTH/2, -Main.HEIGHT/2 + height*coeff + offset,0);
    	vagues.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
    }
    
    private void moveObjets(){
    	for(Objet o : this.listObjet){
    		o.move();
    	}
    }
    
    public void degenererObjet(int hauteur){
    	
    }
}

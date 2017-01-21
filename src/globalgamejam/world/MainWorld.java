package globalgamejam.world;

import globalgamejam.Main;
import globalgamejam.game.MainGame;
import globalgamejam.game.Player;
import globalgamejam.gui.ActionGUI;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;
import globalgamejam.input.Input;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.Objet;
import globalgamejam.tiles.TestTile;
import globalgamejam.tiles.Tile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private ArrayList<Tile> tiles;

    private MainGame game;
    
    private Player player1,player2;

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<Tile>();
        init();
    }

    public void init(){
    	player1 = new Player(200, 150);
        player2 = new Player(400, 150);


		Fond fond = new Fond("res/textures/fond.png");
		fond.getTransform().translate(Main.WIDTH/2, Main.HEIGHT/2, 0);
		fond.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);

		tiles.add(fond);
		tiles.add(player1.getTile());
        tiles.add(player2.getTile());
		
		generateEntity(3);
    }

    public void update(){
        //Player 1
    	float xDep = 0, yDep = 0;
	    if(Input.isKey(GLFW.GLFW_KEY_W)){
	    	yDep = 10;
	    }
	    if(Input.isKey(GLFW.GLFW_KEY_S)){
	    	yDep = -10;
	    }
	    if(Input.isKey(GLFW.GLFW_KEY_A)){
	    	xDep = -10;
	    }
	    if(Input.isKey(GLFW.GLFW_KEY_D)){
	    	xDep = 10;
	    }
	    
	    if(xDep != 0.0 && yDep != 0.0){
	    	xDep *= Math.cos(Math.PI / 4);
	    	yDep *= Math.cos(Math.PI / 4);
	    }
	    
	    player1.move(xDep, yDep);
	    
	    if(Input.isKey(GLFW.GLFW_KEY_Q)){
	    	player1.rotate(-5);
	    }
	    if(Input.isKey(GLFW.GLFW_KEY_E)){
	    	player1.rotate(5);
	    }

	    //Player 2
        xDep = 0;
	    yDep = 0;
        if(Input.isKey(GLFW.GLFW_KEY_I)){
            yDep = 10;
        }
        if(Input.isKey(GLFW.GLFW_KEY_K)){
            yDep = -10;
        }
        if(Input.isKey(GLFW.GLFW_KEY_J)){
            xDep = -10;
        }
        if(Input.isKey(GLFW.GLFW_KEY_L)){
            xDep = 10;
        }

        if(xDep != 0.0 && yDep != 0.0){
            xDep *= Math.cos(Math.PI / 4);
            yDep *= Math.cos(Math.PI / 4);
        }

        player2.move(xDep, yDep);

        if(Input.isKey(GLFW.GLFW_KEY_U)){
            player2.rotate(-5);
        }
        if(Input.isKey(GLFW.GLFW_KEY_O)){
            player2.rotate(5);
        }

    }

    public void render(){
    	for(int i = tiles.size() - 1;i >= 0 ;i--)
			tiles.get(i).render();
    }

    public void destroy(){
        tiles.clear();
    }
    
    public void generateEntity(int nb){
		final int MIN_HAUTEUR_MAX=150;
		final int MIN_HAUTEUR=80;
		
		
		int hauteurMax = (int) (MIN_HAUTEUR_MAX +Math.random()* Main.HEIGHT-80);
		int nbMin = 0;
		int nbMax = 0;
		ArrayList<Tile> list = new ArrayList<>();
		if(hauteurMax<MIN_HAUTEUR_MAX){
			nbMin=nb-2;
			nbMax=0;
		}
		if(hauteurMax>MIN_HAUTEUR_MAX && hauteurMax<Main.HEIGHT/2){
			nbMin=nb-2;
			nbMax=nb+2;
		}
		if(hauteurMax>Main.HEIGHT/2){
			nbMin=0;
			nbMax=nb+2;
		}
		int countJ1=(int)(nbMin + Math.random()*nbMax);
		int countJ2=(int)(nbMin + Math.random()*nbMax);
		
		for(int i =0;i<countJ1;i++){
			
			list.add(new Objet((int)(Math.random()* Main.WIDTH/2),(int) (MIN_HAUTEUR+Math.random()* hauteurMax)));
		}
		
		for(int i =0;i<countJ2;i++){
			
			list.add(new Objet((int)(Main.WIDTH/2+Math.random()* Main.WIDTH),(int) (MIN_HAUTEUR +Math.random()* hauteurMax)));
		}
		
		for(Tile t : list){
			tiles.add(t);
		}
    }

}

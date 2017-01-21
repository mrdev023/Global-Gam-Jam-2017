package globalgamejam.world;

import globalgamejam.game.MainGame;
import globalgamejam.game.Player;
import globalgamejam.input.Input;
import globalgamejam.tiles.Tile;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private ArrayList<Tile> tiles;

    private MainGame game;
    
    private Player player1;

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<Tile>();
        init();
    }

    public void init(){
    	player1 = new Player(200, 150);
    	tiles.add(player1.getTile());
    }

    public void update(){
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
	    
	    if(Input.isKey(GLFW.GLFW_KEY_SPACE)){
	    	player1.rotate(-5);
	    }
	    if(Input.isKey(GLFW.GLFW_KEY_LEFT_ALT)){
	    	player1.rotate(5);
	    }
	    
	    System.out.println(player1);
    }

    public void render(){
        for(Tile t : tiles)t.render();
    }

    public void destroy(){
        tiles.clear();
    }

}

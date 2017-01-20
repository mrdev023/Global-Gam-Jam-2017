package globalgamejam.world;

import globalgamejam.game.MainGame;
import globalgamejam.tiles.Tile;

import java.util.ArrayList;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainWorld {

    private  ArrayList<Tile> tiles;

    private MainGame game;

    public MainWorld(MainGame game){
        this.game = game;
        tiles = new ArrayList<Tile>();
        init();
    }

    public void init(){
    }

    public void update(){

    }

    public void render(){
        for(Tile t : tiles)t.render();
    }

    public void destroy(){
        tiles.clear();
    }

}

package globalgamejam.game;

import globalgamejam.render.*;
import globalgamejam.tiles.TestTile;
import globalgamejam.tiles.Tile;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

    private ArrayList<Tile> tiles;

	@Override
	public void init() {
		tiles = new ArrayList<Tile>();
		TestTile t = new TestTile();
		t.getTransform().translate(100,100,0);
		t.getTransform().scale(10,10,0);
		tiles.add(t);
	}

	@Override
	public void update() {
	    Camera.transform();

	}

	@Override
	public void render2D() {
		for(Tile t : tiles)t.render();
	}


	@Override
	public void renderGUI() {
		
	}

	@Override
	public void destroy() {
		tiles.clear();
	}

}

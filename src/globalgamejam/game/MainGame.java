package globalgamejam.game;

import globalgamejam.render.*;
import globalgamejam.tiles.TestTile;
import globalgamejam.tiles.Tile;

import java.util.ArrayList;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

    private ArrayList<Tile> tiles;
    private FrameBufferObject fbo;

	@Override
	public void init() {
		fbo = new FrameBufferObject();
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
		fbo.startRenderToFBO();
		for(Tile t : tiles)t.render();
		fbo.stopRenderToFBO();

		fbo.renderFBO();
	}


	@Override
	public void renderGUI() {
		
	}

	@Override
	public void destroy() {
		fbo.destroy();
		tiles.clear();
	}

}

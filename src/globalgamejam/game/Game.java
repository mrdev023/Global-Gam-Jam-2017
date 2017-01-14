package globalgamejam.game;

import java.util.*;

import globalgamejam.render.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public abstract class Game {
	
	public Game(){
		Camera.init();
		init();
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render2D();
	public abstract void renderGUI();
	public abstract void destroy();
	
	
}

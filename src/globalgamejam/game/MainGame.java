package globalgamejam.game;

import globalgamejam.*;
import globalgamejam.math.*;
import globalgamejam.render.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{
	
    private float value = 0;

	@Override
	public void init() {
		

	}

	@Override
	public void update() {
	    Camera.update();
	    Camera.transform();

	}

	@Override
	public void render2D() {
		
	}


	@Override
	public void renderGUI() {
		
	}

	@Override
	public void destroy() {

	}

}

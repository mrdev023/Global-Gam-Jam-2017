package globalgamejam.game;



import org.lwjgl.glfw.GLFW;

import globalgamejam.interfaces.MainInterfaces;
import globalgamejam.world.MainWorld;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

	private MainWorld world;
	private MainInterfaces interfaces;
    public int[] scores;
    public final int helpKey = GLFW.GLFW_KEY_H;

	@Override
	public void init() {
		this.scores = new int[2];
		world = new MainWorld(this);
		interfaces = new MainInterfaces(this);
	}

	@Override
	public void update() {
		interfaces.update();
		world.update();
	}

	@Override
	public void render2D() {
		world.render();
	}


	@Override
	public void renderGUI() {
		interfaces.render();
	}

	@Override
	public void destroy() {
		interfaces.destroy();
		world.destroy();
	}

	
}

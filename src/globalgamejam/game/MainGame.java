package globalgamejam.game;



import org.lwjgl.glfw.GLFW;

import globalgamejam.interfaces.MainInterfaces;
import globalgamejam.world.MainWorld;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

	public static final int MAX_SCORE = 1000;
	private MainWorld world;
	private MainInterfaces interfaces;
    public int[] scores;
    public final int helpKey = GLFW.GLFW_KEY_H;

	@Override
	public void init() {
		this.scores = new int[2];
		this.scores[0] = MAX_SCORE;
		this.scores[1] = MAX_SCORE;
		world = new MainWorld(this);
		interfaces = new MainInterfaces(this);
	}
	
	public void reset(){
		this.scores[0] = MAX_SCORE;
		this.scores[1] = MAX_SCORE;
		world.destroy();
		world = new MainWorld(this);
		world.init();
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

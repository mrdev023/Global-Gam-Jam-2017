package globalgamejam.game;

import globalgamejam.interfaces.MainInterfaces;

import globalgamejam.world.MainWorld;

import java.util.Random;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

	private MainWorld world;
	private MainInterfaces interfaces;
    public int[] scores;
    private Random rand;
    
    private Player player1;

	@Override
	public void init() {
		rand = new Random();
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

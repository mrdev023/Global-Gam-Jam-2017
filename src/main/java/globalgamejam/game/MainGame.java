package globalgamejam.game;



import java.awt.Color;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.audio.Audio;
import globalgamejam.gui.GUILabel;
import globalgamejam.input.IO;
import globalgamejam.interfaces.MainInterfaces;
import globalgamejam.world.MainWorld;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{
	
	public Audio audioBackground;
	public Audio audioMacarena;
	public Audio audioBennyHill;
	public Audio audioEffect;

	public static final int SCORE_INIT = 500;
	public static final float START_TIMER = 300;
	public static float time_in_sec = 0;
	private MainWorld world;
	private MainInterfaces interfaces;
    public float[] scores;
    public final int HELP_KEY = GLFW.GLFW_KEY_H;
    public HighScore highScore;
    public boolean isEnd = false;
    private GUILabel loading;
	@Override
	public void init() {
		loading = new GUILabel("Loading",10,10,Color.WHITE,"Arial",16);
		loading.render();
		
		// load audio
		try{
			audioMacarena = new Audio("res/audio/macarena_court.ogg");
			audioMacarena.setGain(0.4f);
			
			audioBennyHill = new Audio("res/audio/background.ogg");
			audioBennyHill.setGain(0.4f);
		} catch(Exception e){}
		
		this.scores = new float[2];
		this.scores[0] = SCORE_INIT;
		this.scores[1] = SCORE_INIT;
		time_in_sec = START_TIMER;
		try{
			this.highScore = IO.loadHighScore("res/highscore");
		}catch(Exception e){
			this.highScore = new HighScore();
		}
		world = new MainWorld(this);
		interfaces = new MainInterfaces(this);
		loading.destroy();
	}
	
	public void saveHighScore(){
		IO.writeHighScore("res/highscore", this.highScore);
	}
	
	public void reset(){
		this.scores[0] = SCORE_INIT;
		this.scores[1] = SCORE_INIT;
		time_in_sec = START_TIMER;
		world.destroy();
		isEnd = false;
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

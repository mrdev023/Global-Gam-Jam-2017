package globalgamejam.game;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import globalgamejam.Main;
import globalgamejam.gui.ActionGUI;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;
import globalgamejam.render.Camera;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.Objet;
import globalgamejam.tiles.TestTile;
import globalgamejam.tiles.Tile;


/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

    private ArrayList<Tile> tiles;
    
    private Random rand;
    
    private Player player1;

	private ArrayList<GUI> guis;
    private GUILabel label;


	@Override
	public void init() {
		tiles = new ArrayList<Tile>();
		Fond fond = new Fond("res/textures/fond.png");
		fond.getTransform().translate(Main.WIDTH/2, Main.HEIGHT/2, 0);
		fond.getTransform().scale(Main.WIDTH,Main.HEIGHT, 0);
		fond.getTransform().rotate(180, 0, 0);
		guis = new ArrayList<GUI>();

		tiles.add(fond);
		TestTile test = new TestTile();
		test.getTransform().translate(0, 80, 0);
		test.getTransform().scale(10, 10, 0);
		tiles.add(test);
		player1 = new Player(-100, 0);
		tiles.add(player1.getTile());
		
		rand = new Random();

		label = new GUILabel("Test");
		label.setX(10);
		label.setY(10);
		label.setAction(new ActionGUI() {
			@Override
			public void enter(float mouseX, float mouseY) {
				label.setColor(Color.RED);
			}

			@Override
			public void leave(float mouseX, float mouseY) {
				label.setColor(Color.WHITE);
			}
		});
		guis.add(label);
		generateEntity(3);
	}

	@Override
	public void update() {
	    Camera.transform();
	  //  player1.setPosition((rand.nextFloat() - 0.5f) * 200f, (rand.nextFloat() - 0.5f) * 150f);
	  //  player1.applyTransform();
	    for(GUI g : guis)g.update();

	}

	@Override
	public void render2D() {
		for(int i = tiles.size() - 1;i >= 0 ;i--)
			tiles.get(i).render();
	}


	@Override
	public void renderGUI() {
		for(GUI g : guis)g.render();
	}

	@Override
	public void destroy() {
		tiles.clear();
		guis.clear();
	}

	public void generateEntity(int nb){
		final int MIN_HAUTEUR_MAX=150;
		final int MIN_HAUTEUR=80;
		
		
		int hauteurMax = (int) (MIN_HAUTEUR_MAX +Math.random()* Main.HEIGHT-80);
		int nbMin = 0;
		int nbMax = 0;
		ArrayList<Tile> list = new ArrayList<>();
		if(hauteurMax<MIN_HAUTEUR_MAX){
			nbMin=nb-2;
			nbMax=0;
		}
		if(hauteurMax>MIN_HAUTEUR_MAX && hauteurMax<Main.HEIGHT/2){
			nbMin=nb-2;
			nbMax=nb+2;
		}
		if(hauteurMax>Main.HEIGHT/2){
			nbMin=0;
			nbMax=nb+2;
		}
		int countJ1=(int)(nbMin + Math.random()*nbMax);
		int countJ2=(int)(nbMin + Math.random()*nbMax);
		
		for(int i =0;i<countJ1;i++){
			
			list.add(new Objet((int)(Math.random()* Main.WIDTH/2),(int) (MIN_HAUTEUR+Math.random()* hauteurMax)));
		}
		
		for(int i =0;i<countJ1;i++){
			
			list.add(new Objet((int)(Main.WIDTH/2+Math.random()* Main.WIDTH),(int) (MIN_HAUTEUR +Math.random()* hauteurMax)));
		}
		
		for(Tile t : list){
			tiles.add(t);
		}
	}
}

package globalgamejam.game;


import java.awt.Color;
import java.util.ArrayList;

import globalgamejam.Main;

import globalgamejam.math.Vector2f;

import globalgamejam.gui.ActionGUI;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;
import globalgamejam.render.Camera;
import globalgamejam.tiles.Fond;
import globalgamejam.tiles.TestTile;
import globalgamejam.tiles.Tile;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


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
		TestTile t = new TestTile();
		t.getTransform().translate(100,100,0);
		t.getTransform().scale(10,10,0);
		tiles.add(fond);
		tiles.add(t);
		

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

	public void generateEntity(){
		int nb =3;
		
	}
}

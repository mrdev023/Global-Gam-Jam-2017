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


import globalgamejam.interfaces.MainInterfaces;

import globalgamejam.world.MainWorld;

import java.util.Random;

import org.lwjgl.glfw.GLFW;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class MainGame extends Game{

	private MainWorld world;
	private MainInterfaces interfaces;
    public int[] scores;

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

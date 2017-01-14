package globalgamejam.render;

import static org.lwjgl.opengl.GL11.*;

import globalgamejam.*;
import globalgamejam.math.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class DisplayManager {

	public static Matrix4f projection = new Matrix4f();
	
	public static void clear(){
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public static void preRender2D(){
		projection.loadIdentity();
		projection.Ortho2D(0, Main.WIDTH, 0, Main.HEIGHT, -1, 1);
		glEnable(GL_DEPTH_TEST);
	    glDepthFunc(GL_LESS);
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void preRenderGUI(){
		projection.loadIdentity();
		//Permet de centrer la camera au centre de l'ecran
		projection.Ortho2D(-Main.WIDTH/2.0f, Main.WIDTH/2.0f, -Main.HEIGHT/2.0f, Main.HEIGHT/2.0f, -1, 1);
		glEnable(GL_DEPTH_TEST);
	    glDepthFunc(GL_LESS);
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void render2D(){
		Main.game.render2D();
	}
	
	public static void renderGUI(){
		Main.game.renderGUI();
	}
	
}

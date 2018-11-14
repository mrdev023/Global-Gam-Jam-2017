package globalgamejam.game;

import java.awt.Color;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.gui.ActionGUI;
import globalgamejam.gui.GUILabel;

public class MainMenuGame extends Game{
	
	private GUILabel menuLabel;
	private GUILabel jouerLabel;
	private GUILabel quitterLabel;


	@Override
	public void init() {
		menuLabel = new GUILabel("MENU", 0, 10, Color.WHITE, "Arial", 32);
		menuLabel.setPosition(Main.WIDTH/2 - menuLabel.getWitdh()/2, 10);
		jouerLabel = new GUILabel("JOUER (A)", 0, 60, Color.WHITE, "Arial", 24);
		jouerLabel.setPosition(Main.WIDTH/2 - jouerLabel.getWitdh()/2, 70);
		jouerLabel.setAction(new ActionGUI(){
			@Override
        	public void enter(float mouseX,float mouseY){
				jouerLabel.setColor(new Color(1, 1, 1, 0.5f));
        	}
        	@Override
            public void leave(float mouseX,float mouseY){
        		jouerLabel.setColor(Color.WHITE);
        	}
			@Override
		    public void clicked(float mouseX, float mouseY, int buttonKey, int buttonState) {
				Main.changeGame(new MainGame());
		    }
			@Override
			public void joystickButtonState(int idJoy,int id,int state){
				if(idJoy == GLFW.GLFW_JOYSTICK_1){
					if(state == 1 && id == 0){
						Main.changeGame(new MainGame());
					}
				}
			}
		});
		quitterLabel = new GUILabel("QUITTER (B)", 0, 80, Color.WHITE, "Arial", 24);
		quitterLabel.setPosition(Main.WIDTH/2 - quitterLabel.getWitdh()/2, 100);
		quitterLabel.setAction(new ActionGUI(){
			@Override
        	public void enter(float mouseX,float mouseY){
				quitterLabel.setColor(new Color(1, 1, 1, 0.5f));
        	}
        	@Override
            public void leave(float mouseX,float mouseY){
        		quitterLabel.setColor(Color.WHITE);
        	}
			@Override
		    public void clicked(float mouseX, float mouseY, int buttonKey, int buttonState) {
				Main.isDestroy = true;
		    }
			@Override
			public void joystickButtonState(int idJoy,int id,int state){
				if(idJoy == GLFW.GLFW_JOYSTICK_1){
					if(state == 1 && id == 1){
						Main.isDestroy = true;
					}
				}
			}
		});
	}

	@Override
	public void update() {
		menuLabel.update();
		jouerLabel.update();
		quitterLabel.update();
	}

	@Override
	public void render2D() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderGUI() {
		menuLabel.render();
		jouerLabel.render();
		quitterLabel.render();
	}

	@Override
	public void destroy() {
		menuLabel.destroy();
		jouerLabel.destroy();
		quitterLabel.destroy();
	}

}

package globalgamejam.interfaces;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import globalgamejam.Main;
import globalgamejam.game.MainGame;
import globalgamejam.gui.ActionGUI;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;
import globalgamejam.input.Input;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainInterfaces {

    private MainGame game;
    
    private ArrayList<GUI> guis;
    private GUILabel p1,p2,timer;
    
    private ArrayList<GUI> guisHelp;
    private GUILabel helpLabel;
    private GUILabel joueur1Help;
    private GUILabel joueur1HelpRotationG;
    private GUILabel joueur1HelpRotationD;
    private GUILabel joueur1HelpDeplacementVerticaleH;
    private GUILabel joueur1HelpDeplacementVerticaleB;
    private GUILabel joueur1HelpDeplacementHorizontaleG;
    private GUILabel joueur1HelpDeplacementHorizontaleD;
    private GUILabel joueur2Help;
    private GUILabel joueur2HelpRotationG;
    private GUILabel joueur2HelpRotationD;
    private GUILabel joueur2HelpDeplacementVerticaleH;
    private GUILabel joueur2HelpDeplacementVerticaleB;
    private GUILabel joueur2HelpDeplacementHorizontaleG;
    private GUILabel joueur2HelpDeplacementHorizontaleD;
    private GUILabel manetteHelp;
    private GUILabel manetteHelpDirection;
    private GUILabel manetteHelpRotation;
    
    private ArrayList<GUI> guisPartieTerminer;
    private GUILabel labelPartieTerminer;
    private GUILabel recommencerPartie;
    private GUILabel quitter;
    private GUILabel[] highScoresLabel;


    public MainInterfaces(MainGame game){
        this.game = game;
        guis = new ArrayList<GUI>();
        guisHelp = new ArrayList<GUI>();
        guisPartieTerminer = new ArrayList<GUI>();
        init();
    }

    public void init(){
        p1 = new GUILabel("Player 1 : ", Main.WIDTH/4,10, Color.RED,"Arial",16);
        p2 = new GUILabel("Player 2 : ", Main.WIDTH/4 * 3,10, Color.BLUE,"Arial",16);
        timer = new GUILabel(" ", Main.WIDTH/4 * 2,10, Color.BLACK,"Arial",16);
        guis.add(p1);
        guis.add(p2);
        guis.add(timer);
        
        //Menu Help
        helpLabel = new GUILabel("HELP",Main.WIDTH/2,10,Color.WHITE,"Arial",64);
        helpLabel.setX(Main.WIDTH/2 - helpLabel.getWitdh()/2);
        guisHelp.add(helpLabel);
        //Joueur 1
        joueur1Help = new GUILabel("JOUEUR 1",Main.WIDTH/4,100,Color.WHITE,"Arial",32);
        joueur1Help.setX(Main.WIDTH/4 - joueur1Help.getWitdh()/2);
        joueur1HelpRotationG = new GUILabel("A = rotation a gauche",Main.WIDTH/4,140,Color.WHITE,"Arial",16);
        joueur1HelpRotationG.setX(Main.WIDTH/4 - joueur1HelpRotationG.getWitdh()/2);
        joueur1HelpRotationD = new GUILabel("E = rotation a droite",Main.WIDTH/4,160,Color.WHITE,"Arial",16);
        joueur1HelpRotationD.setX(Main.WIDTH/4 - joueur1HelpRotationD.getWitdh()/2);
        joueur1HelpDeplacementVerticaleH = new GUILabel("Z = Haut",Main.WIDTH/4,180,Color.WHITE,"Arial",16);
        joueur1HelpDeplacementVerticaleH.setX(Main.WIDTH/4 - joueur1HelpDeplacementVerticaleH.getWitdh()/2);
        joueur1HelpDeplacementVerticaleB = new GUILabel("S = Bas",Main.WIDTH/4,200,Color.WHITE,"Arial",16);
        joueur1HelpDeplacementVerticaleB.setX(Main.WIDTH/4 - joueur1HelpDeplacementVerticaleB.getWitdh()/2);
        joueur1HelpDeplacementHorizontaleG = new GUILabel("A = Gauche",Main.WIDTH/4,220,Color.WHITE,"Arial",16);
        joueur1HelpDeplacementHorizontaleG.setX(Main.WIDTH/4 - joueur1HelpDeplacementHorizontaleG.getWitdh()/2);
        joueur1HelpDeplacementHorizontaleD = new GUILabel("D = Droite",Main.WIDTH/4,240,Color.WHITE,"Arial",16);
        joueur1HelpDeplacementHorizontaleD.setX(Main.WIDTH/4 - joueur1HelpDeplacementHorizontaleD.getWitdh()/2);
        guisHelp.add(joueur1Help);
        guisHelp.add(joueur1HelpRotationG);
        guisHelp.add(joueur1HelpRotationD);
        guisHelp.add(joueur1HelpDeplacementVerticaleH);
        guisHelp.add(joueur1HelpDeplacementVerticaleB);
        guisHelp.add(joueur1HelpDeplacementHorizontaleG);
        guisHelp.add(joueur1HelpDeplacementHorizontaleD);
        //Joueur 2
        joueur2Help = new GUILabel("JOUEUR 2",Main.WIDTH/4,100,Color.WHITE,"Arial",32);
        joueur2Help.setX(Main.WIDTH/4 * 3 - joueur2Help.getWitdh()/2);
        joueur2HelpRotationG = new GUILabel("U = rotation a gauche",Main.WIDTH/4,140,Color.WHITE,"Arial",16);
        joueur2HelpRotationG.setX(Main.WIDTH/4 * 3 - joueur1HelpRotationG.getWitdh()/2);
        joueur2HelpRotationD = new GUILabel("O = rotation a droite",Main.WIDTH/4,160,Color.WHITE,"Arial",16);
        joueur2HelpRotationD.setX(Main.WIDTH/4 * 3 - joueur1HelpRotationD.getWitdh()/2);
        joueur2HelpDeplacementVerticaleH = new GUILabel("I = Haut",Main.WIDTH/4,180,Color.WHITE,"Arial",16);
        joueur2HelpDeplacementVerticaleH.setX(Main.WIDTH/4 * 3 - joueur1HelpDeplacementVerticaleH.getWitdh()/2);
        joueur2HelpDeplacementVerticaleB = new GUILabel("K = Bas",Main.WIDTH/4,200,Color.WHITE,"Arial",16);
        joueur2HelpDeplacementVerticaleB.setX(Main.WIDTH/4 * 3 - joueur1HelpDeplacementVerticaleB.getWitdh()/2);
        joueur2HelpDeplacementHorizontaleG = new GUILabel("J = Gauche",Main.WIDTH/4,220,Color.WHITE,"Arial",16);
        joueur2HelpDeplacementHorizontaleG.setX(Main.WIDTH/4 * 3 - joueur1HelpDeplacementHorizontaleG.getWitdh()/2);
        joueur2HelpDeplacementHorizontaleD = new GUILabel("L = Droite",Main.WIDTH/4,240,Color.WHITE,"Arial",16);
        joueur2HelpDeplacementHorizontaleD.setX(Main.WIDTH/4 * 3 - joueur1HelpDeplacementHorizontaleD.getWitdh()/2);
        guisHelp.add(joueur2Help);
        guisHelp.add(joueur2HelpRotationG);
        guisHelp.add(joueur2HelpRotationD);
        guisHelp.add(joueur2HelpDeplacementVerticaleH);
        guisHelp.add(joueur2HelpDeplacementVerticaleB);
        guisHelp.add(joueur2HelpDeplacementHorizontaleG);
        guisHelp.add(joueur2HelpDeplacementHorizontaleD);
        //Manette
        manetteHelp = new GUILabel("Manettes",Main.WIDTH/2,270,Color.WHITE,"Arial",32);
        manetteHelp.setX(Main.WIDTH/2 - manetteHelp.getWitdh()/2);
        manetteHelpDirection = new GUILabel("Stick Gauche = Direction",Main.WIDTH/2,310,Color.WHITE,"Arial",16);
        manetteHelpDirection.setX(Main.WIDTH/2 - manetteHelpDirection.getWitdh()/2);
        manetteHelpRotation = new GUILabel("Stick Droit = Rotation",Main.WIDTH/2,330,Color.WHITE,"Arial",16);
        manetteHelpRotation.setX(Main.WIDTH/2 - manetteHelpRotation.getWitdh()/2);
        guisHelp.add(manetteHelp);
        guisHelp.add(manetteHelpDirection);
        guisHelp.add(manetteHelpRotation);
        
        //Menu Partie Terminer
        labelPartieTerminer = new GUILabel("PARTIE TERMINER",Main.WIDTH/2,10,Color.WHITE,"Arial",32);
        labelPartieTerminer.setX(Main.WIDTH/2 - labelPartieTerminer.getWitdh()/2);
        
        recommencerPartie = new GUILabel("RECOMMENCER (A)",Main.WIDTH/2,100,Color.WHITE,"Arial",16);
        recommencerPartie.setX(Main.WIDTH/2 - recommencerPartie.getWitdh()/2);
        recommencerPartie.setAction(new ActionGUI(){
        	@Override
        	public void enter(float mouseX,float mouseY){
        		recommencerPartie.setColor(new Color(1, 1, 1, 0.5f));
        	}
        	@Override
            public void leave(float mouseX,float mouseY){
        		recommencerPartie.setColor(Color.WHITE);
        	}
        	@Override
        	public void clicked(float mouseX,float mouseY,int buttonKey,int buttonState){
        		game.reset();
        	}
        	@Override
			public void joystickButtonState(int idJoy,int id,int state){
				if(idJoy == GLFW.GLFW_JOYSTICK_1){
					if(state == 1 && id == 0){
						game.reset();
					}
				}
			}
        });
        
        quitter = new GUILabel("QUITTER (B)", Main.WIDTH/2, 120, Color.WHITE, "Arial", 16);
        quitter.setX(Main.WIDTH/2 - quitter.getWitdh()/2);
        quitter.setAction(new ActionGUI(){
        	@Override
        	public void enter(float mouseX,float mouseY){
        		quitter.setColor(new Color(1, 1, 1, 0.5f));
        	}
        	@Override
            public void leave(float mouseX,float mouseY){
        		quitter.setColor(Color.WHITE);
        	}
        	@Override
        	public void clicked(float mouseX,float mouseY,int buttonKey,int buttonState){
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
        
        highScoresLabel = new GUILabel[10];
        for(int i = 0;i<10;i++){
        	highScoresLabel[i] = new GUILabel(this.game.highScore.getTopScores()[i] + "",Main.WIDTH/2,150 + 20 * i,Color.WHITE,"Arial",16);
        	highScoresLabel[i].setX(Main.WIDTH/2 - highScoresLabel[i].getWitdh()/2);
        	guisPartieTerminer.add(highScoresLabel[i]);
        }
        guisPartieTerminer.add(labelPartieTerminer);
        guisPartieTerminer.add(recommencerPartie);
        guisPartieTerminer.add(quitter);
    }

    public void update(){
    	boolean joysticksHelp = false;
    	if(Input.getJoysticks().size() > 0){
    		try{
    			ByteBuffer b = Input.getJoysticksButton(0);
        		if(b.get(3) == 1)joysticksHelp = true;
    		}catch(Exception e){}
    	}
        if(Input.isKey(this.game.HELP_KEY) || joysticksHelp){
        	for(GUI g : guisHelp)g.update();
        }else if(this.game.scores[0] <= 0 || this.game.scores[1] <= 0 || MainGame.time_in_sec <= 0){
        	int score = 0;
        	if(!this.game.isEnd){
        		if(this.game.scores[1]<this.game.scores[0]){
            		this.game.highScore.put((int)this.game.scores[0]);
            		score = (int)this.game.scores[0];
            	}else{
            		this.game.highScore.put((int)this.game.scores[1]);
            		score = (int)this.game.scores[1];
            	}
            	this.game.saveHighScore();
            	game.audioBackground.pauseSound();
            	if(game.audioEffect != null){
            		game.audioEffect.pauseSound();
            	}
        	}
        	for(int i = 0;i<10;i++){
            	highScoresLabel[i].setText(this.game.highScore.getTopScores()[i] + "");
            	highScoresLabel[i].setX(Main.WIDTH/2 - highScoresLabel[i].getWitdh()/2);
            	if(this.game.highScore.getTopScores()[i] == score){
            		highScoresLabel[i].setColor(Color.RED);
            	}else{
            		highScoresLabel[i].setColor(Color.WHITE);
            	}
            }
        	for(GUI g : guisPartieTerminer)g.update();
        }else{
        	p1.setText("Player 1 : " + (int)this.game.scores[0]);
            p1.setX((Main.WIDTH)/4 - p1.getWitdh()/2);            
            p2.setText("Player 2 : " + (int)this.game.scores[1]);
            p2.setX((Main.WIDTH)/4*3 - p2.getWitdh()/2);
            int sec = (int)MainGame.time_in_sec;
            int min = sec/60;
            sec -= min * 60;
            timer.setText(min + ":" + sec);
            timer.setX((Main.WIDTH)/4*2 - timer.getWitdh()/2);
            MainGame.time_in_sec -= Main.delta;
            for(GUI g : guis)g.update();
        }
    }

    public void render(){
    	boolean joysticksHelp = false;
    	if(Input.getJoysticks().size() > 0){
    		try{
    			ByteBuffer b = Input.getJoysticksButton(0);
        		if(b.get(3) == 1)joysticksHelp = true;
    		}catch(Exception e){}
    	}
    	if(Input.isKey(this.game.HELP_KEY) || joysticksHelp){
    		for(GUI g : guisHelp)g.render();
        }else if(this.game.scores[0] <= 0 || this.game.scores[1] <= 0){
        	if(this.game.scores[0] <= 0){
        		labelPartieTerminer.setText("PARTIE TERMINER (GAGNANT : JOUEUR 2)");
        		labelPartieTerminer.setX(Main.WIDTH/2 - labelPartieTerminer.getWitdh()/2);
        	}else{
        		labelPartieTerminer.setText("PARTIE TERMINER (GAGNANT : JOUEUR 1)");
        		labelPartieTerminer.setX(Main.WIDTH/2 - labelPartieTerminer.getWitdh()/2);
        	}
        	for(GUI g : guisPartieTerminer)g.render();
        }else if(MainGame.time_in_sec <= 0){
        	if(this.game.scores[0] < this.game.scores[1]){
        		labelPartieTerminer.setText("PARTIE TERMINER (GAGNANT : JOUEUR 2)");
        		labelPartieTerminer.setX(Main.WIDTH/2 - labelPartieTerminer.getWitdh()/2);
        	}else{
        		labelPartieTerminer.setText("PARTIE TERMINER (GAGNANT : JOUEUR 1)");
        		labelPartieTerminer.setX(Main.WIDTH/2 - labelPartieTerminer.getWitdh()/2);
        	}
        	for(GUI g : guisPartieTerminer)g.render();
        }else{
        	for(GUI g : guis)g.render();
        }
    }

    public void destroy(){
        for(GUI g : guis)g.destroy();
        guis.clear();
        for(GUI g : guisHelp)g.destroy();
        guisHelp.clear();
        for(GUI g : guisPartieTerminer)g.destroy();
        guisPartieTerminer.clear();
    }

}

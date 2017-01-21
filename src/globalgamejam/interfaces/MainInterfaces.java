package globalgamejam.interfaces;

import globalgamejam.Main;
import globalgamejam.game.MainGame;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;
import globalgamejam.input.Input;

import java.awt.*;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainInterfaces {

    private final int SIZE_OF_DETAILS = 100;

    private MainGame game;
    private ArrayList<GUI> guis;

    private GUILabel p1,p2;
    
    private ArrayList<GUI> guisHelp;
    private GUILabel helpLabel;


    public MainInterfaces(MainGame game){
        this.game = game;
        guis = new ArrayList<GUI>();
        guisHelp = new ArrayList<GUI>();
        init();
    }

    public void init(){
        p1 = new GUILabel("Player 1 : ", Main.WIDTH/4 - 50,10, Color.BLACK,"Arial",16);
        p2 = new GUILabel("Player 2 : ", Main.WIDTH/4 * 3 - 50,10, Color.BLACK,"Arial",16);
        guis.add(p1);
        guis.add(p2);
        
        //Menu Help
        helpLabel = new GUILabel("HELP",Main.WIDTH/2,10,Color.WHITE,"Arial",32);
        helpLabel.setX(Main.WIDTH/2 - helpLabel.getWitdh()/2);
        guisHelp.add(helpLabel);
    }

    public void update(){
        if(Input.isKey(this.game.helpKey)){
        	for(GUI g : guisHelp)g.update();
        }else{
        	p1.setText("Player 1 : " + this.game.scores[0]);
            p1.setX((Main.WIDTH-SIZE_OF_DETAILS)/4 - p1.getWitdh()/2);
            p2.setText("Player 2 : " + this.game.scores[1]);
            p2.setX((Main.WIDTH-SIZE_OF_DETAILS)/4*3 - p2.getWitdh()/2);
            for(GUI g : guis)g.update();
        }
    }

    public void render(){
    	if(Input.isKey(this.game.helpKey)){
    		for(GUI g : guisHelp)g.render();
        }else{
        	for(GUI g : guis)g.render();
        }
    }

    public void destroy(){
        for(GUI g : guis)g.destroy();
        guis.clear();
        for(GUI g : guisHelp)g.destroy();
        guisHelp.clear();
    }

}

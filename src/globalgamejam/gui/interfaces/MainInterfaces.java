package globalgamejam.gui.interfaces;

import globalgamejam.Main;
import globalgamejam.game.MainGame;
import globalgamejam.gui.GUI;
import globalgamejam.gui.GUILabel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by trexr on 20/01/2017.
 */
public class MainInterfaces {

    private final int SIZE_OF_DETAILS = 100;

    private MainGame game;
    private ArrayList<GUI> guis;

    private GUILabel p1,p2;


    public MainInterfaces(MainGame game){
        this.game = game;
        guis = new ArrayList<GUI>();
        init();
    }

    public void init(){
        p1 = new GUILabel("Player 1 : ", Main.WIDTH/4 - 50,10, Color.WHITE,"Arial",16);
        p2 = new GUILabel("Player 2 : ", Main.WIDTH/4 * 3 - 50,10, Color.WHITE,"Arial",16);
        guis.add(p1);
        guis.add(p2);
    }

    public void update(){
        p1.setText("Player 1 : " + this.game.scores[0]);
        p1.setX((Main.WIDTH-SIZE_OF_DETAILS)/4 - p1.getWitdh()/2);
        p2.setText("Player 2 : " + this.game.scores[1]);
        p2.setX((Main.WIDTH-SIZE_OF_DETAILS)/4*3 - p2.getWitdh()/2);
        for(GUI g : guis)g.update();
    }

    public void render(){
        for(GUI g : guis)g.render();
    }

    public void destroy(){
        guis.clear();
    }

}

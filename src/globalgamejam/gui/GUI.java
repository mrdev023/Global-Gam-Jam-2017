package globalgamejam.gui;

import globalgamejam.input.Input;

/**
 * Created by trexr on 20/01/2017.
 */
public abstract class GUI {
    private int mouseInGUI = 0;//0 = En dehors, 1 = entrer, 2 = deplacer

    protected int x, y;
    protected int width,height;

    protected IActionGUI action;

    public GUI(int x,int y){
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
    }

    public void update(){
        float mouseX = Input.getMousePosition().x;
        float mouseY = Input.getMousePosition().y;
        if(mouseX >= this.x && mouseX <= this.x && mouseY >= this.y && mouseY <= this.y){
            for(int i = 0;i < Input.NBRE_BUTTON;i++){
                if(Input.isButton(i)){
                    action.clicked(mouseX,mouseY,i,Input.getButtonState(i));
                }
            }
            if(mouseInGUI == 0){
                mouseInGUI = 1;
                action.enter(mouseX,mouseY);
            }else if(mouseInGUI == 1 && mouseInGUI == 2){
                mouseInGUI = 2;
                action.move(mouseX,mouseY);
            }
        }else{
            if(mouseInGUI == 1 && mouseInGUI == 2){
                mouseInGUI = 0;
                action.leave(mouseX,mouseY);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

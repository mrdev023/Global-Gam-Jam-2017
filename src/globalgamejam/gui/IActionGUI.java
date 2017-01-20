package globalgamejam.gui;

/**
 * Created by trexr on 20/01/2017.
 */
public interface IActionGUI {
    public void enter(float mouseX,float mouseY);
    public void leave(float mouseX,float mouseY);
    public void move(float mouseX,float mouseY);
    public void hover(float mouseX,float mouseY);
    public void clicked(float mouseX,float mouseY,int buttonKey,int buttonState);
}

package globalgamejam.gui;

/**
 * Created by trexr on 20/01/2017.
 */
public class ActionGUI implements IActionGUI{


    @Override
    public void enter(float mouseX, float mouseY) {}

    @Override
    public void leave(float mouseX, float mouseY) {}

    @Override
    public void move(float mouseX, float mouseY) {}

    @Override
    public void hover(float mouseX, float mouseY) {}

    @Override
    public void clicked(float mouseX, float mouseY, int buttonKey, int buttonState) {}
    
    @Override
    public void joystickButtonState(int idJoy,int id,int state){}
    
    @Override
    public void joystickAxisState(int idJoy,int id,float value){}
}

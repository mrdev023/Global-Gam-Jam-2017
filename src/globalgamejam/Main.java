package globalgamejam;

//http://www.tomdalling.com/blog/modern-opengl/08-even-more-lighting-directional-lights-spotlights-multiple-lights/

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import globalgamejam.audio.*;
import globalgamejam.game.*;
import globalgamejam.input.*;
import globalgamejam.math.*;
import globalgamejam.render.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Main {

    //Valeur de la fenetre
    public static final int WIDTH = 800,HEIGHT = 600;
    public static final String TITLE = "Test Shader OpenGL";

    //Variable pour la gestion de la fenetre
    public static long windowID = 0;
    public static GLFWErrorCallback errorCallback;

    //variable du moteur du jeu
    public static float delta = 0;
    public static Game game;
    public static long previous = System.currentTimeMillis(),previousInfo = System.currentTimeMillis(),previousTicks = System.currentTimeMillis();
    public static int FPS = 0,TICKS = 0;

    public static void main(String[] args) throws Exception {
        //Creation de la fenetre
        //------------------------------------------------------------------------------------
        errorCallback = new GLFWErrorCallback() {
            public void invoke(int error, long description) {
                System.err.println("ID : " + error + " | Description :" + description);
            }
        };
//		glfwSetErrorCallback(errorCallback);

        if(glfwInit())throw new Exception("GLFW not init");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        windowID = glfwCreateWindow(WIDTH,HEIGHT,TITLE,NULL,NULL);
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(windowID,(vidmode.width()-WIDTH)/2,(vidmode.height()-HEIGHT)/2);
        glfwShowWindow(windowID);
        glfwMakeContextCurrent(windowID);
        GL.createCapabilities();
        System.out.println("OpenGL Version :" + glGetString(GL_VERSION));
        System.out.println("GLSL Shader Version :" + glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
        //------------------------------------------------------------------------------------

        //Creation du device audio
        //------------------------------------------------------------------------------------
        Audio.create();
        //------------------------------------------------------------------------------------

        //initialisation
        //------------------------------------------------------------------------------------
        Input.init();
        game = new MainGame();

        Camera.transform();
        //------------------------------------------------------------------------------------

        while(glfwWindowShouldClose(windowID)){

            if(System.currentTimeMillis() - previousTicks >= 1000/60){//Update TICKS
                glfwPollEvents();
                Input.update();
                game.update();
                previousTicks = System.currentTimeMillis();
                delta = (float)(System.currentTimeMillis() - previous)/1000.0f;
                previous = System.currentTimeMillis();
                TICKS++;
            }else{//Update FPS
                DisplayManager.clear();
                DisplayManager.preRender2D();
                DisplayManager.render2D();
                DisplayManager.preRenderGUI();
                DisplayManager.renderGUI();
                glfwSwapBuffers(windowID);
                FPS++;
            }

            if(System.currentTimeMillis() - previousInfo >= 1000){
                glfwSetWindowTitle(windowID, TITLE + " | FPS:" + FPS + " TICKS:" + TICKS);
                FPS = 0;
                TICKS = 0;
                previousInfo = System.currentTimeMillis();
            }
        }

        Audio.destroy();
        glfwDestroyWindow(windowID);
        glfwTerminate();
    }

}

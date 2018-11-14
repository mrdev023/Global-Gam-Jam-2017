package globalgamejam;

//http://www.tomdalling.com/blog/modern-opengl/08-even-more-lighting-directional-lights-spotlights-multiple-lights/
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.File;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import globalgamejam.audio.Audio;
import globalgamejam.game.Game;
import globalgamejam.game.MainMenuGame;
import globalgamejam.input.Input;
import globalgamejam.render.Camera;
import globalgamejam.render.DisplayManager;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Main {

    //Valeur de la fenetre
    public static final int WIDTH = 800,HEIGHT = 600;
    public static final String TITLE = "Beach Fighter (OpenGL)";

    //Variable pour la gestion de la fenetre
    public static long windowID = 0;
    public static GLFWErrorCallback errorCallback;

    //variable du moteur du jeu
    public static float delta = 0;
    public static Game game;
    public static long previous = System.currentTimeMillis(),previousInfo = System.currentTimeMillis(),previousTicks = System.currentTimeMillis();
    public static int FPS = 0,TICKS = 0;

	public static boolean isDestroy = false;

    public static void main(String[] args) throws Exception {
    	// System.setProperty("org.lwjgl.librarypath", new File("libs").getAbsolutePath());
        //Creation de la fenetre
        //------------------------------------------------------------------------------------
        errorCallback = new GLFWErrorCallback() {
            public void invoke(int error, long description) {
                System.err.println("ID : " + error + " | Description :" + description);
            }
        };
//		glfwSetErrorCallback(errorCallback);

        if(!glfwInit())throw new Exception("GLFW not init");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);//Activation du MSAA x4
//        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
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
        //glEnable(GL_MULTISAMPLE);//Activation du MSAA
        Input.init();
        game = new MainMenuGame();

        Camera.transform();
        //------------------------------------------------------------------------------------

        while(!glfwWindowShouldClose(windowID) && !isDestroy){

            if(System.currentTimeMillis() - previousTicks >= 1000/120){//Update TICKS
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
        
    }
    
    public static void destroy(){
        game.destroy();
        Audio.destroy();
        glfwDestroyWindow(windowID);
        glfwTerminate();
    }

    public static void changeGame(Game g){
        game.destroy();
        game = g;
    }

}

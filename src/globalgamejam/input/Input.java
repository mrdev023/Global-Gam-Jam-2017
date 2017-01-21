package globalgamejam.input;


import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWJoystickCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import globalgamejam.Main;
import globalgamejam.math.Vector2f;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Input{

	public static GLFWScrollCallback scroll;
	public static GLFWCursorPosCallback mousePos;
	public static GLFWJoystickCallback joyCall;
	
	private static ArrayList<Integer> joysticks = new ArrayList<Integer>();

	private static Vector2f mousePosition = new Vector2f();
	private static Vector2f dMouse = new Vector2f();
	private static Vector2f previousDMouse = new Vector2f();

	public static final int NONE = 0,PRESSED = 1,RELEASED = 2,REPEATED = 3,UP = 4,DOWN = 5,
			NBRE_KEY = 0x15D,NBRE_BUTTON = 10,
			MOUSE_OFFSET = NBRE_KEY + 1,MOUSE_WHEEL_OFFSET = MOUSE_OFFSET + 1,
			NBRE_MAX_JOYSTICK = 16;

	private static HashMap<Integer,Integer> state = new HashMap<Integer,Integer>();

	private static double ywheel = 0;

	public static void init(){
		for(int i = 0;i < NBRE_MAX_JOYSTICK;i++){
			if(glfwJoystickPresent(GLFW_JOYSTICK_1 + i))joysticks.add(GLFW_JOYSTICK_1 + i);
		}
		glfwSetJoystickCallback(joyCall = new GLFWJoystickCallback() {
			
			@Override
			public void invoke(int id, int event) {
				if (event == GLFW_CONNECTED)
			    {
			        joysticks.add(id);
			        System.out.println(glfwGetJoystickName(id) + " connecter");
			    }
			    else if (event == GLFW_DISCONNECTED)
			    {
			        joysticks.remove(id);
			        System.out.println("Manettes #" + id + " deconnecter");
			    }
			}
		});
		glfwSetScrollCallback(Main.windowID, scroll = new GLFWScrollCallback() {
			public void invoke(long window, double xoffset, double yoffset) {
				scroll(window, xoffset, yoffset);
			}
		});
		glfwSetCursorPosCallback(Main.windowID, mousePos = new GLFWCursorPosCallback() {
			public void invoke(long window, double xpos, double ypos) {
				mousepos(window, xpos, ypos);
			}
		});
		for(int i = 0;i < NBRE_KEY;i++){
			state.put(i, NONE);
		}
		for(int i = 0;i < NBRE_BUTTON;i++){
			state.put(i + MOUSE_OFFSET, NONE);
		}
		state.put(MOUSE_WHEEL_OFFSET, NONE);
	}

	public static void update(){
		for(Entry<Integer, Integer> set : state.entrySet()){
			int i = set.getKey();
			int st = set.getValue();
			if(i > -1 && i < NBRE_KEY){
				if(glfwGetKey(Main.windowID, i) == 0 && st == NONE)continue;
				if(glfwGetKey(Main.windowID, i) == 1 && st == NONE){
					state.replace(i, PRESSED);
				}else if(glfwGetKey(Main.windowID, i) == 1 && st == PRESSED){
					state.replace(i, REPEATED);
				}else if(glfwGetKey(Main.windowID, i) == 0 && (st == PRESSED || st == REPEATED)){
					state.replace(i, RELEASED);
				}else if(glfwGetKey(Main.windowID, i) == 0 && st == RELEASED){
					state.replace(i, NONE);
				}
			}else if(i >= MOUSE_OFFSET && i < MOUSE_OFFSET + NBRE_BUTTON){
				if(glfwGetMouseButton(Main.windowID, i - MOUSE_OFFSET) == 0 && st == NONE)continue;
				if(glfwGetMouseButton(Main.windowID, i - MOUSE_OFFSET) == 1 && st == NONE){
					state.replace(i, PRESSED);
				}else if(glfwGetMouseButton(Main.windowID, i - MOUSE_OFFSET) == 1 && st == PRESSED){
					state.replace(i, REPEATED);
				}else if(glfwGetMouseButton(Main.windowID, i - MOUSE_OFFSET) == 0 && (st == PRESSED || st == REPEATED)){
					state.replace(i, RELEASED);
				}else if(glfwGetMouseButton(Main.windowID, i - MOUSE_OFFSET) == 0 && st == RELEASED){
					state.replace(i, NONE);
				}
			}
		}
		int st = state.get(MOUSE_WHEEL_OFFSET);
		if(ywheel > 0 && (st == NONE || st == UP)){
			state.replace(MOUSE_WHEEL_OFFSET, UP);
		}else if(ywheel < 0 && (st == NONE || st == DOWN)){
			state.replace(MOUSE_WHEEL_OFFSET, DOWN);
		}else if(ywheel == 0 && (st == DOWN || st == UP)){
			state.replace(MOUSE_WHEEL_OFFSET, NONE);
		}
		ywheel = 0;
		if(dMouse.equals(previousDMouse)){
			dMouse = new Vector2f();
		}else{
			previousDMouse = dMouse;
		}
	}

	public static void destroy(){
		mousePos.free();
		scroll.free();
	}

	public static void scroll(long window, double xoffset, double yoffset) {
		ywheel = yoffset;
	}

	public static void mousepos(long window, double xpos, double ypos) {
		dMouse.x = (float) (xpos - mousePosition.x);
		dMouse.y = (float) (ypos - mousePosition.y);
		mousePosition.x = (float) xpos;
		mousePosition.y = (float) ypos;
	}

	public static boolean isButtonDown(int button){
		return state.get(button + MOUSE_OFFSET) == PRESSED;
	}

	public static boolean isButtonUp(int button){
		return state.get(button + MOUSE_OFFSET) == RELEASED;
	}

	public static boolean isButton(int button){
		return state.get(button + MOUSE_OFFSET) == PRESSED || state.get(button + MOUSE_OFFSET) == REPEATED;
	}

	public static int getButtonState(int button){
		return state.get(button + MOUSE_OFFSET);
	}

	public static boolean isKeyDown(int key){
		return state.get(key) == PRESSED;
	}

	public static boolean isKeyUp(int key){
		return state.get(key) == RELEASED;
	}

	public static boolean isKey(int key){
		return state.get(key) == PRESSED || state.get(key) == REPEATED;
	}

	public static int getKeyState(int key){
		return state.get(key);
	}

	public static int isMouseWheelState(){
		return state.get(MOUSE_WHEEL_OFFSET);
	}

	public static boolean isMouseWheelUp(){
		return state.get(MOUSE_WHEEL_OFFSET) == UP;
	}

	public static boolean isMouseWheelDown(){
		return state.get(MOUSE_WHEEL_OFFSET) == DOWN;
	}

	public static GLFWScrollCallback getScroll() {
		return scroll;
	}

	public static void setScroll(GLFWScrollCallback scroll) {
		Input.scroll = scroll;
	}

	public static GLFWCursorPosCallback getMousePos() {
		return mousePos;
	}

	public static void setMousePos(GLFWCursorPosCallback mousePos) {
		Input.mousePos = mousePos;
	}

	public static Vector2f getMousePosition() {
		return mousePosition;
	}

	public static void setMousePosition(Vector2f mousePosition) {
		Input.mousePosition = mousePosition;
	}

	public static Vector2f getDMouse() {
		return dMouse;
	}

	public static void setDMouse(Vector2f dMouse) {
		Input.dMouse = dMouse;
	}

	public static HashMap<Integer, Integer> getState() {
		return state;
	}

	public static void setState(HashMap<Integer, Integer> state) {
		Input.state = state;
	}

	public static double getYwheel() {
		return ywheel;
	}

	public static void setYwheel(double ywheel) {
		Input.ywheel = ywheel;
	}

	public static int getNone() {
		return NONE;
	}

	public static int getPressed() {
		return PRESSED;
	}

	public static int getReleased() {
		return RELEASED;
	}

	public static int getRepeated() {
		return REPEATED;
	}

	public static int getUp() {
		return UP;
	}

	public static int getDown() {
		return DOWN;
	}

	public static int getNbreKey() {
		return NBRE_KEY;
	}

	public static int getNbreButton() {
		return NBRE_BUTTON;
	}

	public static int getMouseOffset() {
		return MOUSE_OFFSET;
	}

	public static int getMouseWheelOffset() {
		return MOUSE_WHEEL_OFFSET;
	}

	public static FloatBuffer getJoysticksAxis(int i){
		return glfwGetJoystickAxes(joysticks.get(i));
	}
	
	public static ByteBuffer getJoysticksButton(int i){
		return glfwGetJoystickButtons(joysticks.get(i));
	}

	public static ArrayList<Integer> getJoysticks() {
		return joysticks;
	}

}

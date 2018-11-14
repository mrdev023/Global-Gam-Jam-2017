package globalgamejam.render;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import globalgamejam.*;
import globalgamejam.input.*;
import globalgamejam.math.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Camera {

	public static Matrix4f matrix = new Matrix4f();
	public static float rotation = 0.0f;//rotation de la camera
	public static Vector2f pos = new Vector2f();

	public static void init(){
		matrix = new Matrix4f();
		rotation = 0.0f;
		pos = new Vector2f();
	}

	public static void transform(){
		matrix.loadIdentity();
		matrix.translate(-pos.x,-pos.y,0);
		matrix.rotate(new Quaternion(new Vector3f(0,0,1),rotation));
	}

}

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
	public static final float SPEED = 1.0f;//Speed de base



	public static float rot = 0.0f;//rotation de la camera
	public static Vector2f pos = new Vector2f();

	public static void update(){
		float speed = SPEED * Main.delta;//speed reel par frame en fonction des fps
		//class Input pour tous ce qui est entrer et sortis

	}

	public static void transform(){
		matrix.loadIdentity();
		matrix.rotate(new Quaternion(new Vector3f(0,0,1),rot));
		matrix.tranlate(-pos.x, -pos.y, 0);
	}

}

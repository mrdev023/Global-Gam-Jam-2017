package globalgamejam.math;


import java.util.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Vector2f {

	public float x,y;
	
	public Vector2f(){
		x = 0;
		y = 0;
	}
	
	public Vector2f(float x,float y){
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public String toString(){
		StringJoiner st = new StringJoiner(",","vec2(",")");
		st.add("" + x);
		st.add("" + y);
		return st.toString();
	}
}

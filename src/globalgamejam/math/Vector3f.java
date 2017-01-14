package globalgamejam.math;


import java.util.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Vector3f {

	public float x,y,z;
	
	public Vector3f(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3f(float x,float y,float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f vec,float z){
		this(vec.x,vec.y,z);
	}
	
	public Vector3f(Vector3f vec){
		this(vec.x,vec.y,vec.z);
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float length(){
		return Mathf.sqrt(x * x + y * y + z * z);
	}
	
	public Vector3f lookAt(Vector3f d){
		Vector3f rot = new Vector3f();
		float x1 = d.x - x;
		float y1 = d.y - y;
		float z1 = d.z - z;
		
		return rot;
	}
	
	public Vector3f normalize(){
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	public Vector3f mul(float m){
		x *= m;
		y *= m;
		z *= m;
		return this;
	}
	
	public String toString(){
		StringJoiner st = new StringJoiner(",","vec3(",")");
		st.add("" + x);
		st.add("" + y);
		st.add("" + z);
		return st.toString();
	}

	public Vector3f toRadians() {
		x = Mathf.toRadians(x);
		y = Mathf.toRadians(y);
		z = Mathf.toRadians(z);
		return this;
	}
	
	public Vector3f toDegrees() {
		x = Mathf.toDegrees(x);
		y = Mathf.toDegrees(y);
		z = Mathf.toDegrees(z);
		return this;
	}
	
}

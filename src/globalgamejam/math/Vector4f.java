package globalgamejam.math;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Vector4f {

	public float x,y,z,w;
	
	public Vector4f(float x,float y,float z,float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector3f v,float w){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = w;
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	
	
	
	
}

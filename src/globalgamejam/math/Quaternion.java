package globalgamejam.math;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Quaternion {

	public float x,y,z,w;
	
	public Quaternion(){
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	
	public Quaternion(Vector3f axis,float angle){
		float sin = Mathf.sin(Mathf.toRadians(angle/2.0f));
		float cos = Mathf.cos(Mathf.toRadians(angle/2.0f));
		x = axis.getX() * sin;
		y = axis.getY() * sin;
		z = axis.getZ() * sin;
		w = cos;
	}
	
	public Quaternion(Vector3f rot){
		this(rot.x,rot.y,rot.z);
	}
	
	public Quaternion (float yaw, float roll, float pitch) {
		yaw = Mathf.toRadians(yaw);
		roll = Mathf.toRadians(roll);
		pitch = Mathf.toRadians(pitch);
        float angle;
        float sinRoll, sinPitch, sinYaw, cosRoll, cosPitch, cosYaw;
        angle = pitch * 0.5f;
        sinPitch = Mathf.sin(angle);
        cosPitch = Mathf.cos(angle);
        angle = roll * 0.5f;
        sinRoll = Mathf.sin(angle);
        cosRoll = Mathf.cos(angle);
        angle = yaw * 0.5f;
        sinYaw = Mathf.sin(angle);
        cosYaw = Mathf.cos(angle);

        // variables used to reduce multiplication calls.
        float cosRollXcosPitch = cosRoll * cosPitch;
        float sinRollXsinPitch = sinRoll * sinPitch;
        float cosRollXsinPitch = cosRoll * sinPitch;
        float sinRollXcosPitch = sinRoll * cosPitch;
        
        w = (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw);
        x = (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw);
        y = (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw);
        z = (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw);
        
        normalize();
    }
	
	public void normalize(){
		float n = (float)(1.0/Math.sqrt(norm()));
        x *= n;
        y *= n;
        z *= n;
        w *= n;
	}
	
	public float norm(){
		return w * w + x * x + y * y + z * z;
	}
	
	public Quaternion Euler(Vector3f rot) {
	    x = Mathf.toRadians(rot.x);
	    y = Mathf.toRadians(rot.y);
	    z = Mathf.toRadians(rot.z);
		float c1 = Mathf.cos(y/2);
		float s1 = Mathf.sin(y/2);
		float c2 = Mathf.cos(z/2);
		float s2 = Mathf.sin(z/2);
		float c3 = Mathf.cos(x/2);
		float s3 = Mathf.sin(x/2);
	    float c1c2 = c1*c2;
	    float s1s2 = s1*s2;
	    this.w =c1c2*c3 - s1s2*s3;
	  	this.x =c1c2*s3 + s1s2*c3;
		this.y =s1*c2*c3 + c1*s2*s3;
		this.z =c1*s2*c3 - s1*c2*s3;
		return new Quaternion(x, y, z, w);
	}
	
	public Vector3f toEulerAngles(){
		Vector3f euler = new Vector3f();

	    float sqw = w * w;
	    float sqx = x * x;
	    float sqy = y * y;
	    float sqz = z * z;
	    float unit = sqx + sqy + sqz + sqw; // if normalized is one, otherwise
	                                                                            // is correction factor
	    float test = x * y + z * w;
	    if (test > 0.499 * unit) { // singularity at north pole
	            euler.y = 2 * Mathf.atan2(x, w);
	            euler.z = Mathf.PI/2.0f;
	            euler.x = 0;
	    } else if (test < -0.499 * unit) { // singularity at south pole
	    		euler.y = -2 * Mathf.atan2(x, w);
	    		euler.z = -Mathf.PI/2.0f;
	    		euler.x = 0;
	    } else {
	    		euler.y = Mathf.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw); // roll or heading 
	    		euler.z = Mathf.asin(2 * test / unit); // pitch or attitude
	    		euler.x = Mathf.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw); // yaw or bank
	    }
        return euler.toDegrees();
	}
	
	public Quaternion(float axisX,float axisY,float axisZ,float angle){
		float sin = Mathf.sin(Mathf.toRadians(angle/2.0f));
		float cos = Mathf.cos(Mathf.toRadians(angle/2.0f));
		x = axisX * sin;
		y = axisY * sin;
		z = axisZ * sin;
		w = cos;
	}
	
	public Matrix4f toMatrixRotation(){
		Vector3f forward =  new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return Matrix4f.rotate(forward, up, right);
	}
	
}

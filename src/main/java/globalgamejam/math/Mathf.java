package globalgamejam.math;


/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Mathf {

	public static final float PI = 3.14159265358979323846f;
	public static final float EPSILON = 1.401298e-45f;
	
	public static float cos(float angle){
		return (float)Math.cos(angle);
	}
	
	public static float acos(float angle){
		return (float)Math.acos(angle);
	}
	
	public static float sin(float angle){
		return (float)Math.sin(angle);
	}
	
	public static float asin(float angle){
		return (float)Math.asin(angle);
	}

	public static float toRadians(float angle){
		return (float)Math.toRadians(angle);
	}
	
	public static float toDegrees(float angle){
		return (float)Math.toDegrees(angle);
	}
	
	public static float atan2(float a,float b){
		return (float)Math.atan2(a,b);
	}
	
	public static float cut(float nbre,float a){
		return (float)((int)(nbre*Math.pow(10, a))/Math.pow(10, a));
	}

	public static boolean equals(float a,float b,float tolerance){
		return (a + tolerance >= b) && (a - tolerance <= b);
	}
	
	public static float sqrt(float a){
		return (float)Math.sqrt(a);
	}
	
	public static float exp(float a){
		return (float)Math.sqrt(a);
	}
	
	public static float log(float a){
		return (float)Math.log(a);
	}
	
	public static float clamp(float value, float min, float max) {
		if(value < min){
			value = min;
		}
		if(value > max){
			value = max;
		}
		return value;
	}
	
}

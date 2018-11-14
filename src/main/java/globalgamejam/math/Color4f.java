package globalgamejam.math;


import static org.lwjgl.opengl.GL11.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Color4f {
	
	public static final Color4f 
			RED = new Color4f(1,0,0,1),
			BLUE = new Color4f(0,0,1,1),
			GREEN = new Color4f(0,1,0,1),
			YELLOW = new Color4f(1,1,0,1),
			PURPLE = new Color4f(1,0,1,1),
			CYAN = new Color4f(0,1,1,1),
			BLACK = new Color4f(0,0,0,1),
			WHITE = new Color4f(1,1,1,1);
	
	public float r,g,b,a;
		
	public Color4f(float r,float g,float b,float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public static Color4f mul (Color4f a, float b){
	   return new Color4f(a.r * b,a.g * b,a.b * b,a.a * b);
	}
	
	public static Color4f mul (float o,Color4f... a){
		float r = 0;
		float b = 0;
		float g = 0;
		float al = 0;
		for(Color4f c : a){
			r += c.r;
			g += c.g;
			b += c.b;
			al += c.a;
		}
		r /= a.length;
		g /= a.length;
		b /= a.length;
		al /= a.length;
	   return new Color4f(r * o,g * o,b * o,al * o);
	}
	
	public static Color4f mul (Color4f... a){
		float r = 0;
		float b = 0;
		float g = 0;
		float al = 0;
		for(Color4f c : a){
			r += c.r;
			g += c.g;
			b += c.b;
			al += c.a;
		}
		r /= a.length;
		g /= a.length;
		b /= a.length;
		al /= a.length;
	   return new Color4f(r,g,b,al);
	}
	
	public Color4f() {
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}
	
	public void bind(){
		glColor4f(r,g,b,a);
	}
	
	public void unbind(){
		BLACK.bind();
	}
	
}

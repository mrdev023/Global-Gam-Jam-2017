package globalgamejam.gui;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import globalgamejam.math.Color4f;
import globalgamejam.math.Matrix4f;
import globalgamejam.render.Camera;
import globalgamejam.render.DisplayManager;
import globalgamejam.render.Shaders;
import globalgamejam.render.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

/**
 * usefull to print 2D text in openGL LWJGL application
 * @author Jean-Baptiste Pommeret (Fiesta)
 * @version 1.0
 */
public class GUILabel extends GUI {
	
	private Texture texture;
	private String text;
	private Color color;
	private int size;
	private String font;
	private int vbo,numberOfVertices;
	
	/**
	 * Full constructor of a Label
	 * @param text (String) : the text to print
	 * @param xC (float) : the x coordonnate of the frame where start printing the Label (upper left corner)
	 * @param yC (float) : the y coordonnate of the frame where start printing the Label (upper left corner)
	 * @param color (java.awt.Color) : the Color you wish for the text
	 * @param font (String) : the font (i.e. "Arial" or "Times new roman")
	 * @param size (int) : the font size
	 */
	public GUILabel(String text, int xC, int yC, Color color, String font, int size){
		super(xC,yC);
		this.font = font;
		this.color = color;
		this.text = text;
		this.size = size;
		if(this.texture != null)this.texture.destroy();
		this.texture = Texture.loadFont(text,color,font,size);
		super.width = this.texture.width;
		super.height = this.texture.height;
		this.vbo = GL15.glGenBuffers();
		float[] a = new float[]{
				0,0,        0.0f,0.0f,
				this.texture.width,0,         1.0f,0.0f,
				this.texture.width,this.texture.height,          1.0f,1.0f,
				0,this.texture.height,         0.0f,1.0f
		};
		FloatBuffer buff = BufferUtils.createFloatBuffer(a.length);
		buff.put(a).flip();
		this.numberOfVertices = a.length/(2+2);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void render(){
		Shaders.MAIN_SHADERS.bind();
		Shaders.MAIN_SHADERS.uniform("camera", Camera.matrix);
		Matrix4f transform = new Matrix4f();
		transform.translate(super.x,super.y,0);
		Shaders.MAIN_SHADERS.uniform("transform", transform);
		Shaders.MAIN_SHADERS.uniform("projection", DisplayManager.projection);
		Shaders.MAIN_SHADERS.uniform("color", Color4f.WHITE);

		texture.bind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
		GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vert"));
		GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vert"), 2, GL11.GL_FLOAT, false, (2+2)*4, 0);

		GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"));
		GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"), 2, GL11.GL_FLOAT, true, (2+2)*4, 2*4);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, numberOfVertices);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		texture.unbind();
		Shaders.MAIN_SHADERS.unbind();
	}
	
	/**
	 * Default constructor of a Label. Call the full constructor -> Label("", 100, 100, Color.white, "Arial", 30)
	 */
	public GUILabel(){
		this("");
	}

	/**
	 * Construct a Label from the text param and default statement.
	 * Call the full constructor -> Label(text, 100, 100, Color.white, "Arial", 30)
	 * @param text (String) : the text to print
	 */
	public GUILabel(String text){
		this(text, 100, 100, Color.white, "Arial", 30);
	}
	
	/**
	 * Return the actual Color of the Label
	 * @return color (java.awt.Color) : the Color of the Label
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Set the Color of the Label. Automaticaly update the Label to use the new Color
	 * @param color (java.awt.Color) : the new Color of the Label
	 */
	public void setColor(Color color) {
		this.color = color;
		if(this.texture != null)this.texture.destroy();
		this.texture = Texture.loadFont(text,color,font,size);
	}
	
	/**
	 * Return the text of the Label
	 * @return text (String) : the text of the Label
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Set the text of the Label. Automaticaly update the Label to use the new text
	 * @param text (String) : the new text to display
	 */
	public void setText(String text) {
		this.text = text;
		if(this.texture != null)this.texture.destroy();
		this.texture = Texture.loadFont(text,color,font,size);
	}


	
	/**
	 * Return the x coordonnate of the Label (upper left corner)
	 * @return x (float) : the x coordonnate of the Label
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set the x coordonnate of the Label (upper left corner)
	 * @param x (float) : the new x coordonnate of the Label
	 */
	public void setX(int x) {
		super.setX(x);
	}
	
	/**
	 * Return the y coordonnate of the Label (upper left corner)
	 * @return y (float) : the y coordonnate of the Label
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the y coordonnate of the Label (upper left corner)
	 * @param y (float) : the new y coordonnate of the Label
	 */
	public void setY(int y) {
		super.setY(y);
	}
	
	/**
	 * Set both x and y coordonnate of the Label
	 * @param x (float) : the new x coordonnate of the Label
	 * @param y (float) : the new y coordonnate of the Label
	 */
	public void setPosition(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Return the witdh of the Label
	 * @return witdh (int) : the width
	 */
	public int getWitdh() {
		return super.width;
	}

	/**
	 * Return the height of the Label
	 * @return height (int) : the height
	 */
	public int getHeight() {
		return super.height;
	}

	public void destroy(){
		GL15.glDeleteBuffers(vbo);
		texture.destroy();
	}
	
	/**
	 * make the image transparent
	 * @param obj_img (BufferedImage) : the BufferedImage to make transparent
	 */
/*	private void makeTransparent(BufferedImage obj_img){
		byte alpha = (byte)255;
	    alpha %= 0xff; 
	    for (int cx=0;cx<obj_img.getWidth();cx++) {          
	        for (int cy=0;cy<obj_img.getHeight();cy++) {
	            int color = obj_img.getRGB(cx, cy);

	            int mc = (alpha << 24) | 0x00ffffff;
	            int newcolor = color & mc;
	            obj_img.setRGB(cx, cy, newcolor);            

	        }

	    }
	}*/
}

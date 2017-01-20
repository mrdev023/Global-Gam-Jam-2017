package globalgamejam.gui;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

/**
 * usefull to print 2D text in openGL LWJGL application
 * @author Jean-Baptiste Pommeret (Fiesta)
 * @version 1.0
 */
public class GUILabel extends GUI {

	private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
	
	private Image label;
	private String text;
	private Color color;
	private int size;
	private String font;
	
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
		this.text = text;
		this.color = color;
		this.size = size;
		this.font = font;
		
		Font f_font = new Font(font, Font.PLAIN, size);
		
		// to get the width of the text
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(f_font);
		
		super.width = fm.stringWidth(this.text);
		super.height = fm.getHeight();
		
		final BufferedImage image = new BufferedImage(super.width, this.height, BufferedImage.TYPE_INT_ARGB);
//		makeTransparent(image);
		
		Graphics g = image.getGraphics();
		g.setFont(f_font);
		g.setColor(this.color);
		g.drawString(this.text, 0, size);
		g.dispose();
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}
		
		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		
		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:
		
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.label = new Image(xC, yC, image.getWidth(), image.getHeight(), textureID);
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
		
		Font f_font = new Font(font, Font.PLAIN, size);
		
		// to get the width of the text
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(f_font);

		super.width = fm.stringWidth(this.text);
		super.height = fm.getHeight();
		
		final BufferedImage image = new BufferedImage(super.width, super.height, BufferedImage.TYPE_INT_ARGB);
//		makeTransparent(image);
		
		Graphics g = image.getGraphics();
		g.setFont(f_font);
		g.setColor(this.color);
		g.drawString(this.text, 0, size);
		g.dispose();
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}
		
		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		
		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:
		
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.label = new Image(this.x, this.y, image.getWidth(), image.getHeight(), textureID);
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
		
		Font f_font = new Font(font, Font.PLAIN, size);
		
		// to get the width of the text
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(f_font);

		super.width = fm.stringWidth(this.text);
		super.height = fm.getHeight();
		
		final BufferedImage image = new BufferedImage(super.width, super.height, BufferedImage.TYPE_INT_ARGB);
//		makeTransparent(image);
		
		Graphics g = image.getGraphics();
		g.setFont(f_font);
		g.setColor(this.color);
		g.drawString(this.text, 0, size);
		g.dispose();
		
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}
		
		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		
		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:
		
		int textureID = glGenTextures(); //Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		
		//Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		//Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.label = new Image(this.x, this.y, image.getWidth(), image.getHeight(), textureID);
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
		label.setX(x);
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
		label.setY(y);
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
	 * Return the Image of the Label
	 * @return label (Image) : the Image of the Label
	 */
	public Image getLabel(){
		return this.label;
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

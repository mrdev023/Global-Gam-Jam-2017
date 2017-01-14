package globalgamejam.render;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

import javax.imageio.*;

import org.lwjgl.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Texture {

	int width, height;
	int id;
	
	public Texture(int width,int height,int id){
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public static Texture loadTexture(String path){
		try {
			BufferedImage image = ImageIO.read(new File(path));
			int width = image.getWidth();
			int height = image.getHeight();
			int[] pixels = new int[width * height];
			
			image.getRGB(0, 0, width, height, pixels, 0,width);
			
			int[] data = new int[pixels.length];
			for (int i = 0; i < data.length; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				
				data[i] = a << 24 | b << 16 | g << 8 | r;
			}
			
			IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
			buffer.put(data);
			buffer.flip();
			
			int id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
			System.out.println("Texture loaded ! " + width + "x" + height + " id:" + id);
			
			return new Texture(width, height, id);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getID(){
		return id;
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind(){
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void destroy(){
		glDeleteTextures(id);
	}
	
}

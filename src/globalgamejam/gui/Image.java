package globalgamejam.gui;

public class Image {
	protected float x, y;
	protected float width, height;
	protected int angle;
	protected int textureID;
	
	public Image(float x, float y, float width, float height, int textureID) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textureID = textureID;
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

	public float getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public int getTextureID() {
		return textureID;
	}
	
	/**
	 * IT DOESN'T WORKS
	 * Rotate the image in the counter-clock wise of the specified angle in degrees
	 * @param rotationAngle (int) : the angle to rotate in degrees
	 */
	public void rotate(int rotationAngle){
		this.angle += rotationAngle;
		this.angle = this.angle % 360;
	}
	
	/**
	 * IT DOESN'T WORKS
	 * Set the angle of the Image
	 * @param angle (int) : the angle, between -360 and +360 degrees
	 */
	public void rotateTo(int angle){
		this.angle = angle;
		this.angle = this.angle % 360;
	}
	
	/**
	 * IT DOESN'T WORKS
	 * Return the actuel angle 
	 * @return angle (int) : the actual angle
	 */
	public int getAngle() {
		return angle;
	}
	
	public void setPosition(float x, float y){
		this.setX(x);
		this.setY(y);
	}
}

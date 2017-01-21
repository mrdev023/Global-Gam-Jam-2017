package globalgamejam.physics;

/**
 * 
 * @author Jean-Baptiste
 *
 */
public class PhysicalEntity {

	protected float x;
	protected float y;
	
	private float sizeRadius;
	
	private float xVelocity;
	private float yVelocity;
	private float speedFactor;
	
	private float speed;
	
	private float frictionFactor;
	
	public PhysicalEntity(float x, float y, float sizeRadius, float speedFactor, float xVelocity, float yVelocity, float frictionFactor) {
		this.x = x;
		this.y = y;
		this.sizeRadius = sizeRadius;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.frictionFactor = frictionFactor;
		this.speedFactor = speedFactor;
		this.speed = 0;
	}

	public boolean collideWithSquareHitBox(PhysicalEntity entity){
		
		// on teste une collision avec une hitbox carré
		return (this.x + this.sizeRadius >= entity.x - entity.sizeRadius
			&& this.x - this.sizeRadius <= entity.x + entity.sizeRadius
			&& this.y + this.sizeRadius >= entity.y - entity.sizeRadius
			&& this.y - this.sizeRadius <= entity.y + entity.sizeRadius);
	}
	/*
	public boolean collideWithRoundHitBox(PhysicalEntity entity){
		if(this.collideWithSquareHitBox(entity)){
			
			// teste avec une hitbox ronde à venir ...
			return true;
		}
		return false;
	}
	*/
	
	public void resolveCollideWith(PhysicalEntity entity){
		float xVel = entity.getSpeed() * (this.getX() - entity.getX()) / this.getSizeRadius();
		float yVel = entity.getSpeed() * (this.getY() - entity.getY()) / this.getSizeRadius();
		
		this.addVelocity(xVel, yVel);
	}
	
	/**
	 * Déplace l'entity et actualise ça vélocité
	 */
	public void move(){
		this.x += this.xVelocity;
		this.y += this.yVelocity;

		this.xVelocity *= 1 - this.frictionFactor;
		this.yVelocity *= 1 - this.frictionFactor;
		
		if(this.xVelocity < 0.001 && this.xVelocity > 0.001){
			this.xVelocity = 0;
		}
		
		if(this.yVelocity < 0.001 && this.yVelocity > 0.001){
			this.yVelocity = 0;
		}
		
		this.speed = (float)Math.sqrt( this.xVelocity * this.xVelocity + this.yVelocity * this.yVelocity );
		
		this.moveTile();
	}
	
	
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void addPosition(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	public void setVelocity(float x, float y){
		this.xVelocity = x;
		this.yVelocity = y;
	}
	
	public void addVelocity(float x, float y){
		this.xVelocity += x;
		this.yVelocity += y;
	}
	
	protected void moveTile(){
		
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getSpeedFactor() {
		return speedFactor;
	}

	/**
	 * @return the sizeRadius
	 */
	public float getSizeRadius() {
		return sizeRadius;
	}
	
	@Override
	public String toString(){
		return this.x + " " + this.y;
	}
}

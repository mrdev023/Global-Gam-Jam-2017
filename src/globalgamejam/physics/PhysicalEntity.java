package globalgamejam.physics;

import globalgamejam.game.Mur;

/**
 * 
 * @author Jean-Baptiste
 *
 */
public class PhysicalEntity {

	private final static float MAX_SPEED = 15f;
	
	protected float x;
	protected float y;

//	private float sizeRadius;
	private float sizeX;
	private float sizeY;
	
	private float xVelocity;
	private float yVelocity;
	private float speedFactor;
	
	private float speed;
	
	private float frictionFactor;
	
/*	public PhysicalEntity(float x, float y, float sizeRadius, float speedFactor, float xVelocity, float yVelocity, float frictionFactor) {
		this.x = x;
		this.y = y;
		this.sizeX = sizeRadius * 2;
		this.sizeY = sizeRadius * 2;
		this.sizeRadius = sizeRadius;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.frictionFactor = frictionFactor;
		this.speedFactor = speedFactor;
		this.speed = 0;
	}*/
	
	public PhysicalEntity(float x, float y, float sizeX, float sizeY, float speedFactor, float xVelocity, float yVelocity, float frictionFactor) {
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	//	this.sizeRadius = -1;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.frictionFactor = frictionFactor;
		this.speedFactor = speedFactor;
		this.speed = 0;
	}
	
	public boolean collideWith(PhysicalEntity entity){
	//	if(this.sizeRadius == -1 || entity.sizeRadius == -1){
			return this.collideWithSquareHitBox(entity);
	/*	}
		else{
			return this.collideWithRoundHitBox(entity);
		}*/
	}
	
	public boolean collideWithSquareHitBox(PhysicalEntity entity){
		
		// on teste une collision avec une hitbox carré
		return (this.x + this.sizeX / 2 >= entity.x - entity.sizeX / 2
			&& this.x - this.sizeX / 2 <= entity.x + entity.sizeX / 2
			&& this.y + this.sizeY / 2 >= entity.y - entity.sizeY / 2
			&& this.y - this.sizeY / 2 <= entity.y + entity.sizeY / 2);
	}
	/*
	public boolean collideWithRoundHitBox(PhysicalEntity entity){
		
		float distX = this.x - entity.x;
		float distY = this.y - entity.y;
		
		float dist = (float)Math.sqrt( distX * distX + distY * distY );
		
		return dist <= this.sizeRadius + entity.sizeRadius;
	}
	*/
	public void resolveCollideWith(PhysicalEntity entity){
		
		if(entity instanceof Mur){
			
			// on a touché le bas du Mur
			if(this.y <= entity.y - entity.sizeY / 2 && this.yVelocity > 0){
				this.yVelocity *= -1;
			}
			
			// on a touché le haut du Mur
			if(this.y >= entity.y + entity.sizeY / 2 && this.yVelocity < 0){
				this.yVelocity *= -1;
			}
			
			// on a touché le coté gauche du Mur
			if(this.x <= entity.x - entity.sizeX / 2  && this.xVelocity > 0){
				this.xVelocity *= -1;
			}
			
			// on a touché le coté droit du Mur
			if(this.x >= entity.x + entity.sizeX / 2  && this.xVelocity < 0){
				this.xVelocity *= -1;
			}
			
		}
		else{
			float xVel = entity.getSpeed() * (this.getX() - entity.getX()) / this.getSizeRadius();
			float yVel = entity.getSpeed() * (this.getY() - entity.getY()) / this.getSizeRadius();
			
			this.addVelocity(xVel, yVel);
		}
	}
	
	/**
	 * Déplace l'entity et actualise ça vélocité
	 */
	public void move(){
		this.x += this.xVelocity;
		this.y += this.yVelocity;

		this.xVelocity *= 1 - this.frictionFactor;
		this.yVelocity *= 1 - this.frictionFactor;
		
		if(this.xVelocity < 0.01 && this.xVelocity > -0.01){
			this.xVelocity = 0;
		}
		
		if(this.yVelocity < 0.01 && this.yVelocity > -0.01){
			this.yVelocity = 0;
		}
		
		this.speed = (float)Math.sqrt( this.xVelocity * this.xVelocity + this.yVelocity * this.yVelocity );
		
		if(this.speed >= PhysicalEntity.MAX_SPEED){
			this.xVelocity = (this.xVelocity * PhysicalEntity.MAX_SPEED) / this.speed;
			this.yVelocity = (this.yVelocity * PhysicalEntity.MAX_SPEED) / this.speed;
			
			this.speed = PhysicalEntity.MAX_SPEED;
		}
		
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
		return (this.sizeX + this.sizeY) / 2;
	}
/*	
	public void setSizeRadius(float size){
		this.sizeRadius = size;
		this.sizeX = size;
		this.sizeY = size;
	}*/
	
	public void setSizeXY(float sizeX, float sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	@Override
	public String toString(){
		return this.x + " " + this.y;
	}
}

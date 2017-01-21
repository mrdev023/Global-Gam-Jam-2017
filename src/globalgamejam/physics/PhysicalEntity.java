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
	
	private float frictionFactor;
	
	public PhysicalEntity(float x, float y, float sizeRadius, float xVelocity, float yVelocity, float frictionFactor) {
		this.x = x;
		this.y = y;
		this.sizeRadius = sizeRadius;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.frictionFactor = frictionFactor;
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
	/**
	 * Déplace l'entity et actualise ça vélocité
	 */
	public void move(){
		this.x += this.xVelocity;
		this.y += this.yVelocity;

		this.xVelocity *= -this.frictionFactor;
		this.yVelocity *= -this.frictionFactor;
		
		if(this.xVelocity <= 0.1){
			this.xVelocity = 0;
		}
		
		if(this.yVelocity <= 0.1){
			this.yVelocity = 0;
		}
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void addPosition(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	@Override
	public String toString(){
		return this.x + " " + this.y;
	}
}

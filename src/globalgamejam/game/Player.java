package globalgamejam.game;

import globalgamejam.math.Vector2f;
import globalgamejam.physics.PhysicalEntity;
import globalgamejam.render.Texture;
import globalgamejam.tiles.PlayerTile;
import globalgamejam.tiles.Tile;

/**
 * 
 * @author Jean-Baptiste
 *
 */
public class Player extends PhysicalEntity {
	
	private final Tile tile;
	private float angle;
	
	private float speed = 15;
	
	private final PhysicalEntity brosse;
	private final float longueurBalai;
	
	public Player(String path, float x, float y){
		super(x, y, 0, 0, 3, 0, 0, 10);
		this.tile = new PlayerTile(path, x, y);
		
		this.setSizeXY(this.tile.getTexture().width, this.tile.getTexture().height);
		
		this.longueurBalai = 82;
		
		this.brosse = new PhysicalEntity(x, y + this.longueurBalai, 18f, 12f, 15, 0, 0, 0){
			@Override
			public float getSpeed(){
				return getSpeedFactor();
			}
		};
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	public void move(float x, float y){
		this.addPosition(x, y);
		
		this.tile.setPosition(new Vector2f(this.x, this.y));
		this.tile.applyTransform();
		
		this.brosse.addPosition(x, y);
	}
	
	public void rotate(float angleRotation){
		this.angle += angleRotation;
		this.angle %= 360;
		if(this.angle < 0){
			this.angle += 360;
		}
		
		this.tile.setRotation(this.angle);
		this.tile.applyTransform();
		
		float angleRad = (float)(this.angle * (Math.PI / 180));
		
		float xBrosse = this.x + this.longueurBalai * -(float)Math.sin(angleRad);
		float yBrosse = this.y + this.longueurBalai * (float)Math.cos(angleRad);
		
		this.brosse.setPosition(xBrosse, yBrosse);
	}
	
	public boolean brosseCollideWith(PhysicalEntity entity){
		return this.brosse.collideWithSquareHitBox(entity);
	}
	
	/**
	 * @return the velocity
	 */
	public float getSpeed() {
		return this.speed;
	}
	
	public PhysicalEntity getBrosse(){
		return this.brosse;
	}

	@Override
	public String toString(){
		return this.brosse.toString();
	}

	public float getAngle() {
		return angle;
	}
	
	
}

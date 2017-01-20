package globalgamejam.game;

import globalgamejam.math.Vector2f;
import globalgamejam.physics.PhysicalEntity;
import globalgamejam.render.Texture;
import globalgamejam.tiles.Tile;

/**
 * 
 * @author Jean-Baptiste
 *
 */
public class Player extends PhysicalEntity {
	
	private final Tile tile;
	private float angle;
	
	private final PhysicalEntity brosse;
	private final float longueurBalai;
	
	public Player(float x, float y){
		super(x, y, 100, 0, 0, 10);
		this.tile = new PlayerTile("res/textures/default.png", -250, 0);
		
		this.longueurBalai = 100;
		this.brosse = new PhysicalEntity(x, y + this.longueurBalai, 20, 0, 0, 0);
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	public void rotate(float angleRotation){
		this.angle += angleRotation;
		this.angle %= 360;
		if(this.angle < 0){
			this.angle += 360;
		}
		
		this.tile.setRotation(this.angle);
		
		float angleRad = (float)(this.angle * (Math.PI / 180));
		
		float xBrosse = this.x + this.longueurBalai * (float)Math.cos(angleRad);
		float yBrosse = this.y + this.longueurBalai * (float)Math.sin(angleRad);
		
		this.brosse.setPosition(xBrosse, yBrosse);
	}
	
	public boolean brosseCollideWith(PhysicalEntity entity){
		return this.brosse.collideWithSquareHitBox(entity);
	}
	
	private class PlayerTile extends Tile {
		
		public PlayerTile(String texturePath, float x, float y){
			super();
			
			this.setTexture(Texture.loadTexture(texturePath));
			
			this.setPosition(x, y);
			
			this.setScale(new Vector2f(50, 50));
			
			this.applyTransform();
		}
		
		public void setPosition(float x, float y){
			this.setPosition(new Vector2f(x, y));
			
		}
	}
}

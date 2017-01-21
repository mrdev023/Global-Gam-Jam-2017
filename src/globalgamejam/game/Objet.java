package globalgamejam.game;

import globalgamejam.math.Vector2f;
import globalgamejam.physics.PhysicalEntity;
import globalgamejam.tiles.ObjetTile;
import globalgamejam.tiles.Tile;

public class Objet extends PhysicalEntity {
	
	private EObjetType type;
	private final Tile tile;
	
	public Objet(String texturePath, float x, float y, float speed, float xVelocity, float yVelocity, float frictionFactor){
		super(x, y, 0, 0, speed, xVelocity, yVelocity, frictionFactor);
		
		this.tile = new ObjetTile(texturePath, x, y);
		
		this.setSizeXY(this.tile.getTexture().width, this.tile.getTexture().height);
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	@Override
	protected void moveTile(){
		this.tile.setPosition(new Vector2f(this.x, this.y));
		this.tile.applyTransform();
	}

	@Override
	public String toString(){
		return "x : " + this.x + ", y : " + this.y; 
	}

	public EObjetType getType() {
		return type;
	}

	public void setType(EObjetType type) {
		this.type = type;
	}
	
	
}

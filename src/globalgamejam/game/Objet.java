package globalgamejam.game;

import globalgamejam.math.Vector2f;
import globalgamejam.physics.PhysicalEntity;
import globalgamejam.tiles.ObjetTile;
import globalgamejam.tiles.Tile;

public class Objet extends PhysicalEntity {

	private final Tile tile;
	
	public Objet(String texturePath, float x, float y, float sizeRadius, float speed, float xVelocity, float yVelocity, float frictionFactor){
		super(x, y, sizeRadius, speed, xVelocity, yVelocity, frictionFactor);
		
		this.tile = new ObjetTile(texturePath, x, y);
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
}

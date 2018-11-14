package globalgamejam.game;

import java.awt.Color;

import globalgamejam.Main;
import globalgamejam.math.Color4f;
import globalgamejam.math.Vector2f;
import globalgamejam.physics.PhysicalEntity;
import globalgamejam.tiles.ObjetTile;
import globalgamejam.tiles.Tile;

public class Objet extends PhysicalEntity {
	
	private static final int TIME_IN_SEC = 5;
	
	private EObjetType type;
	private float inactiveDelay = 0;
	private float despawnRate;
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
		this.despawnRate = this.type.getDespawnRate();
	}

	public float getInactiveDelay() {
		return inactiveDelay;
	}
	
	public boolean underWave(float yWave){
		return yWave >= this.y + this.getSizeRadius();
	}
	
	public boolean shouldDespawn(){
		if(this.despawnRate >= Math.random()){
			return true;
		}
		this.despawnRate *= 1.05f;
		return false;
	}
}

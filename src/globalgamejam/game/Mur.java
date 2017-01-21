package globalgamejam.game;

import globalgamejam.physics.PhysicalEntity;
import globalgamejam.tiles.MurTile;
import globalgamejam.tiles.Tile;

public class Mur extends PhysicalEntity {

	private final Tile tile;

	public Mur(float x, float y, String texturePath){
		super(x, y, 0, 0, 5, 0, 0, 0);
		
		this.tile = new MurTile(x, y, texturePath);
		
		this.setSizeXY(this.tile.getTexture().width, this.tile.getTexture().height);
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	@Override
	public float getSpeed(){
		return this.getSpeedFactor();
	}
}

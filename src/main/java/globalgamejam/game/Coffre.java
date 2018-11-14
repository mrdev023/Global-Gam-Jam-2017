package globalgamejam.game;

import globalgamejam.physics.PhysicalEntity;
import globalgamejam.tiles.CoffreTile;
import globalgamejam.tiles.Tile;

public class Coffre extends PhysicalEntity {

	private final Tile tile;

	public Coffre(String texturePath, float x, float y){
		super(x, y, 0, 0, 0, 0, 0, 0);
		
		this.tile = new CoffreTile(texturePath, x, y);
		
		this.setSizeXY(this.tile.getTexture().width, this.tile.getTexture().height);
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	public void makeEffect(int numPlayerHitCoffre){
		
	}
}

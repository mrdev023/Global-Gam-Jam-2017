package globalgamejam.tiles;

import globalgamejam.math.Color4f;
import globalgamejam.math.Vector2f;
import globalgamejam.render.Texture;

public class ObjetTile extends Tile {
	
	public ObjetTile(String texturePath, float x, float y){
		super();
		this.setTexture(Texture.loadTexture(texturePath));
		
		this.setPosition(new Vector2f(x, y));
		
		this.setScale(new Vector2f(this.getTexture().width, this.getTexture().height));
		
		this.applyTransform();
	}
}

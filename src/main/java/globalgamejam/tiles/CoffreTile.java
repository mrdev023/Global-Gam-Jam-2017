package globalgamejam.tiles;

import globalgamejam.math.Vector2f;
import globalgamejam.render.Texture;

public class CoffreTile extends Tile {
	public CoffreTile(String texturePath, float x, float y){
		super();
		super.setTexture(Texture.loadTexture(texturePath));
		super.setScale(new Vector2f(this.getTexture().width, this.getTexture().height));
		super.setPosition(new Vector2f(x, y));
		super.applyTransform();
	}
}

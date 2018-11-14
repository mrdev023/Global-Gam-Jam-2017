package globalgamejam.tiles;

import globalgamejam.math.Vector2f;
import globalgamejam.render.Texture;

public class MurTile extends Tile{
	
	public MurTile(float x, float y, String path){
		super();
		super.setPosition(new Vector2f(x, y));
		super.setTexture(Texture.loadTexture(path));
		super.setScale(new Vector2f(this.getTexture().width, this.getTexture().height));
		
		super.applyTransform();
	}
}
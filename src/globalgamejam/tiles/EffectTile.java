package globalgamejam.tiles;

import globalgamejam.math.Color4f;
import globalgamejam.math.Vector2f;
import globalgamejam.render.Texture;

public class EffectTile extends Tile {
	
	public EffectTile(String texturePath, float x, float y){
		super();
		super.setTexture(Texture.loadTexture(texturePath));
		super.setColor(new Color4f(1, 1, 1, 0));
		super.setScale(new Vector2f(this.getTexture().width, this.getTexture().height));
		super.setPosition(new Vector2f(x, y));
		super.applyTransform();
	}
	
	@Override
	public void setTexture(String path){
		super.setTexture(Texture.loadTexture(path));
		super.setColor(new Color4f(1, 1, 1, 1));
	}
	
	public void clear(){
		super.setColor(new Color4f(1, 1, 1, 0));
	}
}

package globalgamejam.tiles;

import globalgamejam.math.Color4f;
import globalgamejam.math.Vector2f;
import globalgamejam.render.Texture;

public class Mur extends Tile{
	
	public Mur(int x,int y,int scaleX,int scaleY,String path){
		super();
		//super.setColor(Color4f.BLACK);
		super.setPosition(new Vector2f(x, y));
		super.setScale(new Vector2f(scaleX, scaleY));
		super.setTexture(Texture.loadTexture(path));
		
		super.applyTransform();
	}
}
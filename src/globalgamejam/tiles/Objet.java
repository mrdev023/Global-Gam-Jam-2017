package globalgamejam.tiles;

import globalgamejam.math.Color4f;
import globalgamejam.math.Vector2f;

public class Objet extends Tile {
	public Objet(int x,int y){
		super();
		super.setColor(Color4f.RED);
		super.setPosition(new Vector2f(x, y));
		super.setScale(new Vector2f(10, 10));
		super.applyTransform();
	}
}

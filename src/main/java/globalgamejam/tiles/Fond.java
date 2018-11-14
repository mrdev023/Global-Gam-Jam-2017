package globalgamejam.tiles;

import globalgamejam.render.Texture;

public class Fond extends Tile {
	public Fond(String path){
		super();
		
		super.setTexture(Texture.loadTexture(path));
	}
}

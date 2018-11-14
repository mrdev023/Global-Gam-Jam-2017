package globalgamejam.tiles;

import globalgamejam.render.Texture;

public class VaguesTile extends Tile {
	
	public VaguesTile(String path){
		super();
		super.setTexture(Texture.loadTexture(path));
	}

}

package globalgamejam.game;

public enum EObjetType {

	POISSON("res/textures/dechets1.png",-20),POMME("res/textures/dechets.png",-30),
	ETOILE_DE_MER("res/textures/bonus1.png",20),COQUILLAGE("res/textures/bonus2.png",30),
	COQUILLAGE2("res/textures/bonus3.png",40),BANANE("res/textures/banane.png",50);
	
	private int points;
	private String filename;
	
	EObjetType(String filename,int points){
		this.points = points;
		this.filename = filename;
	}

	public int getPoints() {
		return points;
	}

	public String getFilename() {
		return filename;
	}
	
}

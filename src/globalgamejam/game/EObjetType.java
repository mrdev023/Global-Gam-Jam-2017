package globalgamejam.game;

public enum EObjetType {

	POISSON("res/textures/dechets1.png",-2, 0.75f),POMME("res/textures/dechets.png",-3, 0.75f),
	ETOILE_DE_MER("res/textures/bonus1.png",2, 0.75f),COQUILLAGE("res/textures/bonus2.png",3, 0.75f),
	COQUILLAGE2("res/textures/bonus3.png",4, 0.75f),BANANE("res/textures/banane.png",-5, 0.75f);
	
	private int points;
	private String filename;
	private float despawnRate;
	
	EObjetType(String filename, int points, float despawnRate){
		this.points = points;
		this.filename = filename;
		this.despawnRate = despawnRate;
	}

	public int getPoints() {
		return points;
	}

	public String getFilename() {
		return filename;
	}
	
	public float getDespawnRate(){
		return this.despawnRate;
	}
}

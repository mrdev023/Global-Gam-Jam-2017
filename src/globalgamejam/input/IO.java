package globalgamejam.input;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import globalgamejam.game.HighScore;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class IO {

	public static String loadFile(String path) throws Exception{
		String r = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String buffer = "";
		while ((buffer = reader.readLine()) != null) {
				r += buffer + "\n";
		}
		reader.close();
		return r;
	}
	
	public static HighScore loadHighScore(String path) throws Exception{
		FileInputStream fin = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fin);
		return (HighScore) ois.readObject();
	}
	
	public static void writeHighScore(String path,HighScore highscore) {
		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(highscore);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	
}

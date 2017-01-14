package globalgamejam.input;
import java.io.*;

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
	
}

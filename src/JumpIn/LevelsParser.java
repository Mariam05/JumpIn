package JumpIn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

import com.eclipsesource.json.*;

/**
 * This class parses the levels json file to get the desired level.
 * 
 * @author tomar
 *
 */
public class LevelsParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CUSTOM_LEVELS_LABEL = "customLevels";
	private static final String DEFAULT_LEVELS_LABEL = "defaultLevels";
	private static final String FILENAME = "SYSC3110Project/LevelsV2.json";

	/**
	 * Represent the json levels as a hashmap
	 */
	private static HashMap<String, String> levels = new HashMap<>();
	private static HashMap<String, String> defaults = new HashMap<>();
	private static HashMap<String, String> customs = new HashMap<>();

	private static JsonArray defaultLevels, customLevels;

	/**
	 * Add the levels from the json file to the hashmap
	 */
	public static void getLevelsFromJson() {

		try {
			InputStream is = LevelsParser.class.getClass().getResourceAsStream("/LevelsV2.json");

			Scanner s = new Scanner(is).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			
//			File f = new File(LevelsParser.class.getClassLoader().getResource("LevelsV2.json").toURI());

			defaultLevels = Json.parse(result).asObject().get(DEFAULT_LEVELS_LABEL).asArray();

			// defaultLevels = Json.parse(new
			// FileReader("LevelsV2.json")).asObject().get(DEFAULT_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : defaultLevels) {
				String board = dLevel.asObject().getString("board", "");
				String name = dLevel.asObject().getString("name", "");
				if (name.equals(""))
					return;
				levels.put(name, board);
				defaults.put(name, board);
			}

			customLevels = Json.parse(result).asObject().get(CUSTOM_LEVELS_LABEL).asArray();

			// customLevels = Json.parse(new
			// FileReader("LevelsV2.json")).asObject().get(CUSTOM_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : customLevels) {
				String board = dLevel.asObject().getString("board", "");
				String name = dLevel.asObject().getString("name", "");
				if (name.equals(""))
					return; // so that we don't store empty boards
				levels.put(name, board);
				customs.put(name, board);
			}
		} catch (Exception e) {
			System.out.println("COuldn't parse file");
		}

	}

	public static void addCustomLevelToFile(String levelName, String boardRepresentation) {
		getLevelsFromJson();
		JsonObject newLevel = Json.object().add("name", levelName).add("board", boardRepresentation);
		customLevels.add(newLevel);
		JsonObject allLevels = Json.object().add(DEFAULT_LEVELS_LABEL, defaultLevels).add(CUSTOM_LEVELS_LABEL,
				customLevels);
		try {
			Writer fileWriter = new FileWriter(FILENAME, false); // false overwrites the file
			fileWriter.write(allLevels.toString());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	private static String getJsonString() {
//		StringBuilder stringBuilder = new StringBuilder();
//		String line = null;
//
//		String ls = System.getProperty("line.separator");
//		try {
//
////			System.out.println(LevelsParser.class.getClass().getResource(FILENAME).getFile());
////			File inputStream = new File(LevelsParser.class.getClass().getResource(FILENAME).getFile());
//			InputStream is = LevelsParser.class.getClassLoader().getResourceAsStream(FILENAME);
//
//			Scanner s = new Scanner(is).useDelimiter("\\A");
//			String result = s.hasNext() ? s.next() : "";
//			System.out.print(result);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//
//			while ((line = reader.readLine()) != null) {
//				stringBuilder.append(line);
//				stringBuilder.append(ls);
//			}
//
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return stringBuilder.toString();
//
//	}

	/**
	 * returns the hashmap of all the default levels in the json
	 * 
	 * @return
	 */
	public static HashMap<String, String> getDefaultLevels() {
		getLevelsFromJson();
		return defaults;
	}

	/**
	 * returns the hashmap of all the default levels in the json
	 * 
	 * @return
	 */
	public static HashMap<String, String> getCustomLevels() {
		getLevelsFromJson();
		return customs;
	}

	
	/**
	 * Return the board corresponding to the specified level
	 * 
	 * @param levelName
	 * @return the board represented as a string
	 */
	public static String getLevel(String levelName) {
		getLevelsFromJson();
		return levels.get(levelName);
	}

	public static void main(String[] args) {
		System.out.println("Entering main");
		// LevelsParser.getJsonString();
		System.out.println("Done");
		InputStream is = LevelsParser.class.getClass().getResourceAsStream("/LevelsV2.json");

		Scanner s = new Scanner(is).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		System.out.println(result);
		
	}
}

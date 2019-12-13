package JumpIn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;

import com.eclipsesource.json.*;

/**
 * This class parses the levels json file to get the desired level.
 * 
 * @author tomar
 *
 */
public class LevelsParser {

	private static final String CUSTOM_LEVELS_LABEL = "customLevels";
	private static final String DEFAULT_LEVELS_LABEL = "defaultLevels";

	/**
	 * Represent the json levels as a hashmap
	 */
	private static HashMap<String, String> levels = new HashMap<>();

	/**
	 * hashmap to hold all default levels
	 */
	private static HashMap<String, String> defaults = new HashMap<>();

	/**
	 * Hashmap to hold all custom levels
	 */
	private static HashMap<String, String> customs = new HashMap<>();

	/**
	 * The levels in json format.
	 */
	private static JsonArray defaultLevels, customLevels;

	private static File homeDir, dir;

	public static File createExternFile() {

		InputStream in = LevelsParser.class.getClass().getClass().getResourceAsStream("/JumpIn/LevelsV2.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		homeDir = new File(System.getProperty("user.home"));
		dir = new File(homeDir, "Levels.json");

		if (!dir.exists()) {

			try {
				FileWriter fWriter = new FileWriter(dir, false);
				fWriter.write(reader.readLine());
				fWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return dir;
	}

	/**
	 * Add the levels from the json file to the hashmap
	 */
	public static void getLevelsFromJson() {

		// createExternFile();
		try {
			// get all default levels
			defaultLevels = Json.parse(new FileReader(dir)).asObject().get(DEFAULT_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : defaultLevels) {
				String board = dLevel.asObject().getString("board", "");
				String name = dLevel.asObject().getString("name", "");
				if (name.equals(""))
					return;
				levels.put(name, board);
				defaults.put(name, board);
			}

			// get all custom levels
			customLevels = Json.parse(new FileReader(dir)).asObject().get(CUSTOM_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : customLevels) {
				String board = dLevel.asObject().getString("board", "");
				String name = dLevel.asObject().getString("name", "");
				if (name.equals(""))
					return; // so that we don't store empty boards
				levels.put(name, board);
				customs.put(name, board);
			}
		} catch (Exception e) {
			System.out.println("Couldn't parse file");
		}

	}

	/**
	 * Update the json file to include a custom level
	 * 
	 * @param levelName           the name of the level to save
	 * @param boardRepresentation the board to save
	 */
	public static void addCustomLevelToFile(String levelName, String boardRepresentation) {
		getLevelsFromJson();
		JsonObject newLevel = Json.object().add("name", levelName).add("board", boardRepresentation);
		customLevels.add(newLevel);
		JsonObject allLevels = Json.object().add(DEFAULT_LEVELS_LABEL, defaultLevels).add(CUSTOM_LEVELS_LABEL,
				customLevels);
		try {
//			String file = LevelsParser.class.getClass().getResource("/LevelsV2.json").getFile();
			Writer fileWriter = new FileWriter(dir, false); // false overwrites the file
			fileWriter.write(allLevels.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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
}

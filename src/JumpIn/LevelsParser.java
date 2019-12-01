package JumpIn;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import com.eclipsesource.json.*;

/**
 * This class parses the levels json file to get the desired level. 
 * @author tomar
 *
 */
public class LevelsParser {
	
	private static final String CUSTOM_LEVELS_LABEL = "customLevels";
	private static final String DEFAULT_LEVELS_LABEL = "defaultLevels";
	private static final String FILENAME = "LevelsV2.json";

	/**
	 * Represent the json levels as a hashmap
	 */
	private static HashMap<String, String> levels = new HashMap<>();
	private static HashMap<String, String> defaults = new HashMap<>();
	private static HashMap<String, String> customs = new HashMap<>();
	
	private static JsonArray defaultLevels,  customLevels;
	
	/**
	 * Add the levels from the json file to the hashmap
	 */
	public static void getLevelsFromJson() {

		try {
			
			defaultLevels = Json.parse(new FileReader(FILENAME)).asObject().get(DEFAULT_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : defaultLevels) {
				  String board = dLevel.asObject().getString("board", "");
				  String name = dLevel.asObject().getString("name","");
				  if (name.equals("")) return;
				  levels.put(name, board);
				  defaults.put(name, board);
			}
			customLevels = Json.parse(new FileReader(FILENAME)).asObject().get(CUSTOM_LEVELS_LABEL).asArray();
			for (JsonValue dLevel : customLevels) {
				  String board = dLevel.asObject().getString("board", "");
				  String name = dLevel.asObject().getString("name","");
				  if (name.equals("")) return; //so that we don't store empty boards
				  levels.put(name, board);
				  customs.put(name, board);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void addCustomLevelToFile(String levelName, String boardRepresentation) {
		getLevelsFromJson();
		JsonObject newLevel = Json.object().add("name", levelName).add("board", boardRepresentation);
		customLevels.add(newLevel);
		JsonObject allLevels = Json.object().add(DEFAULT_LEVELS_LABEL, defaultLevels).add(CUSTOM_LEVELS_LABEL,customLevels);
		try {
			Writer fileWriter = new FileWriter(FILENAME, false); //false overwrites the file
			fileWriter.write(allLevels.toString());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * returns the hashmap of all the default levels in the json
	 * 
	 * @return
	 */
	public static HashMap<String, String> getDefaultLevels(){
		return defaults;
	}
	
	/**
	 * returns the hashmap of all the default levels in the json
	 * 
	 * @return
	 */
	public static HashMap<String, String> getCustomLevels(){
		return customs;
	}
	
	/**
	 * Return the board corresponding to the specified level
	 * @param levelName
	 * @return the board represented as a string
	 */
	public static String getLevel(String levelName) {
		getLevelsFromJson();
		return levels.get(levelName);
	}

	
	public static void main(String[] args) {
		System.out.println("Entering main");
		LevelsParser.getLevelsFromJson();
		System.out.println("Done");
	}
}

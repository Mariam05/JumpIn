package JumpIn;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import com.eclipsesource.json.*;

/**
 * This class parses the levels json file to get the desired level. 
 * @author tomar
 *
 */
public class LevelsParser {
	
	/**
	 * Represent the json levels as a hashmap
	 */
	private static HashMap<String, String> levels = new HashMap<>();
	
	private final static String filename = "LevelsV2.json";
	
	/**
	 * Add the levels from the json file to the hashmap
	 * @param filename
	 */
	public static void getLevelsFromJson() {

		try {
			JsonArray defaultLevels = Json.parse(new FileReader(filename)).asObject().get("defaultLevels").asArray();
			for (JsonValue dLevel : defaultLevels) {
				  String board = dLevel.asObject().getString("board", "");
				  String name = dLevel.asObject().getInt("name",0) + "";
				  if (name.equals("0")) return;
				  levels.put(name, board);
			}
			JsonArray customLevels = Json.parse(new FileReader(filename)).asObject().get("customLevels").asArray();
			for (JsonValue dLevel : customLevels) {
				  String board = dLevel.asObject().getString("board", "");
				  String name = dLevel.asObject().getInt("name",0) + "";
				  if (name.equals("0")) return; //so that we don't store empty boards
				  levels.put(name, board);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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

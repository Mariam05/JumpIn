package unitTests;
import org.junit.*;
import static org.junit.Assert.*;
import JumpIn.*;
/**
 * Basically checks if we're reading the json file correctly 
 * @author tomar
 *
 */
public class LevelsParserTest {

	
	private String name;
	private String board; 
	
	@Before
	public void setUp() {
		name = "1";
		board = "X X X X X MSH X X X X MSH X X X X X MSH RA1 X X X X X X X";
	}
	
	@Test
	public void testGetLevel() {
		String boardReceived = LevelsParser.getLevel(name);
		assertEquals(boardReceived, board);
	}
	
	@Test
	public void testAddCustomLevelToFile() {
		LevelsParser.addCustomLevelToFile("Test", board); //add level 1 as a custom level 
		assertEquals(LevelsParser.getLevel("Test"), board);
	}
}

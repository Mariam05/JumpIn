import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Taher Shabaan
 * 
 * This class is testing CommandWord class as to 
 * see if the commands that the user will input are as we have or not
 */
public class CommandWordTest {

	private CommandWord commandWord;
	
	
	@Before
	public void setUp() throws Exception {
		commandWord = new CommandWord();
	}
	/*
	 * We are testing if the return command will as"     move quit help" or not
	 */
	@Test
	public void getCommandWordTest() {
		assertEquals("    move quit help",commandWord.getCommandWords());
	}
	
	/*
	 * the first three asserEquals are true and I added a fourth one just to make sure that 
	 * the functionality of  isCommand method is correct
	 */
	@Test
	public void isCommandTest() {
		assertEquals(true,commandWord.isCommand("move"));
		assertEquals(true,commandWord.isCommand("quit"));
		assertEquals(true,commandWord.isCommand("help"));
		assertEquals(false,commandWord.isCommand("play"));
	}
}

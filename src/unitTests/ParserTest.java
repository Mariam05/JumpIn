package unitTests;

import static org.junit.Assert.*;
import org.junit.Test;
import JumpIn.*;

public class ParserTest {

	private String cmd = "quit";
	private Parser p = new Parser();
	
	@Test
	public void testParser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCommand() {
		Command cmd1 = p.getCommand();
		System.out.println(cmd1.toString());
		assert(cmd1.equals(cmd));
	}

}

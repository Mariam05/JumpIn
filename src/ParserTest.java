import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Taher Shabaan
 */
public class ParserTest {
	
	private Parser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new Parser();
}
	
	@Test
	public void getCommandTest() {
		assert(((parser.getCommand()).getCommandWord()).equalsIgnoreCase("move"));
		assert(((parser.getCommand()).getCommandWord()).equalsIgnoreCase("quit"));
		assert(((parser.getCommand()).getCommandWord()).equalsIgnoreCase("help"));
	}
	
	
	
}

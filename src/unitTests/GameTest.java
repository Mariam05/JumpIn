package unitTests;

import static org.junit.Assert.*;
import org.junit.*;

import JumpIn.*;
/**
 * Testing Game class functionality
 * 
 * @author Nazifa Tanzim
 *
 */
public class GameTest {
	private Game g;
	private String cmd1;
	private String cmd2;
	private String cmd3;
	Command c;
	Fox fox;
	Fox foxTail;
	Rabbit rabbit;
	Board b;
	
	/**
	 * Set up everything before performing tests
	 */
	@Before
	public void setUp() {
		g = new Game();
		cmd1 = "move"; // starting with a fully valid command
		cmd2 = "ra1";
		cmd3 = "32";
		c = new Command(cmd1, cmd2, cmd3);
		rabbit = new Rabbit("RA1");
		fox = new Fox("FO1", Fox.FoxType.HORIZONTAL, true);
		foxTail = new Fox("F1T", Fox.FoxType.HORIZONTAL, false);
		fox.addAssociatedPart(foxTail);
		Board b = new Board();
		b.addPiece(rabbit, 3, 0);
		b.addPiece(fox, 4, 3);
		b.addPiece(foxTail, fox.getXPos() - 1, fox.getYPos());
	}
	
	/**
	 * Test processCommand
	 */
	@Test
	public void testProcessCommand() {
		assert(g.processCommand(c)); // valid command word, piece, destination
		// invalid piece e.g. everything else is valid
		c = new Command(cmd1, "invalid", cmd3);
		assert(!g.processCommand(c));
		
		// invalid destination only
		c = new Command(cmd1, cmd2, "67");
		assert(!g.processCommand(c));
		
		// user wants help
		cmd1 = "help";
		c = new Command(cmd1, null, null);
		assert(!g.processCommand(c));
		
		// user wants to quit
		cmd1 = "quit";
		c = new Command(cmd1, null, null);
		assert(!g.processCommand(c));
	}

	/**
	 * Test handleFoxMove
	 */
	@Test
	public void testHandleFoxMove() {
		// valid destination
		c = new Command("move", fox.toString(), "23");
		assert(g.handleFoxMove(fox,c));
		
		// invalid destination
		c = new Command("move", fox.toString(), "34");
		assert(!g.handleFoxMove(fox,c));
	}

	/**
	 * Test handleRabbitMove
	 */
	@Test
	public void testHandleRabbitMove() {
		// valid destination
		c = new Command("move", rabbit.toString(), "32"); 
		assert(g.handleRabbitMove(rabbit, c));
		
		// invalid destination
		c = new Command("move", rabbit.toString(), "20");
		assert(!g.handleRabbitMove(rabbit, c));
	}

}

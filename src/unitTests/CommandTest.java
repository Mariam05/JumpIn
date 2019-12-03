package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import JumpIn.Command;

/**
 * JUnit Tests to test the functionality of methods in Command class
 * 
 * @author Nazifa Tanzim & Taher
 *
 */
public class CommandTest { 
	private String validCmd1;
	private String piece;
	private String destinationX;
	private String destinationY;
	private Command c1;
	private Command c2;
	private Command c3;
	private Command c4;
	
	/**
	 * Setting up all instance variables required before each test
	 */
	@Before
	public void setUp() {
		this.validCmd1 = "move";
		this.piece = "F1";
		this.destinationX = "2";
		this.destinationY = "4";
		// Command objects with different valid commans
		c1 = new Command(this.validCmd1, this.piece, this.destinationX.concat(this.destinationY));
		c2 = new Command(null, this.piece, this.destinationX.concat(this.destinationY)); // command with invalid word
		c3 = new Command(this.validCmd1, null, this.destinationX.concat(this.destinationY)); // invalid piece
		c4 = new Command(this.validCmd1, this.piece, null); // invalid destination
	}
	
	/**
	 * Testing getCommandWord with a valid, and invalid word
	 */
	@Test
	public void testGetCommandWord() {
		assert(c1.getCommandWord().equals(this.validCmd1));
		assert(c2.getCommandWord().equals(""));
	}

	/**
	 * Testing getPiece with a valid, and invalid piece
	 */
	@Test
	public void testGetPiece() {
		assert(c1.getPiece().equals(this.piece));
		assert(c3.getPiece() == null);
	}
	
	/**
	 * Testing getDestination with a valid, and invalid destination
	 */
	@Test
	public void testGetDestination() {
		assert(c1.getDestination().equals(this.destinationX.concat(this.destinationY)));
		assert(c4.getDestination() ==  null);
	}

	/**
	 * Testing getX with a valid, and invalid X
	 */
	@Test
	public void testGetX() {
		assert(c1.getX() == Integer.parseInt(this.destinationX));
	}

	/**
	 * Testing getY with a valid, and invalid Y
	 */
	@Test
	public void testGetY() {
		assert(c1.getY() == Integer.parseInt(this.destinationY));
	}

	/**
	 * Testing isUnknown with Command objects that have a valid (known), and invalid (unknown) command word 
	 */
	@Test
	public void testIsUnknown() {
		assert(!c1.isUnknown());
		assert(c2.isUnknown());
	}


	/**
	 * Testing hasPiece with Command objects that have a valid, and invalid piece
	 */
	@Test
	public void testHasPiece() {
		assert(c1.hasPiece());
		assert(!c3.hasPiece());
	}

	/**
	 * Testing hasDestination with Command objects that have a valid, and invalid destination
	 */
	@Test
	public void testHasDestination() {
		assert(c1.hasDestination());
		assert(!c4.hasDestination());
	}

}

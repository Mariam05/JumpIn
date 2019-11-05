package unitTests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JumpIn.*;

/*
 * @author TS
 * 
 * This class is testing the mushroom constructor, position, name and validateThemove
 */
public class MushroomTest {
	
	

	private Mushroom mushroom ;
	
	
	@Before
	public void setUp() throws Exception {
		mushroom = new Mushroom("Mushroom1");
	}

	@Test
	public void ConstructorTest() {
		assertEquals(mushroom.getPieceType(),Piece.Type.MUSHROOM);
	}
	
	/*
	 * mushroom will not move so it has to be false
	 */
	@Test
	public void ValidatemoveTest() {
		assertEquals(false,mushroom.validateMove(0, 2));
	}
	
	@Test
	public void toStringTest() {
		assertEquals("Mushroom1",mushroom.toString());
	}
	
	@Test
	public void mushroomNameTest() {
		assertEquals("Mushroom1",mushroom.toString());
	}
	/*
	 * we'll add the x-position with the y-position so the total is 5 as 2 + 3 = 5
	 */
	@Test
	public void PositionTest() {
		mushroom.setPosition(2,3);
		assertEquals("23",mushroom.getXPos() +""+ mushroom.getYPos());
	}
}

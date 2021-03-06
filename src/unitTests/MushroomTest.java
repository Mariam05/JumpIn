package unitTests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JumpIn.*;

/*
 * @author Taher Shabaan
 * 
 * This class is testing the mushroom constructor, position, name and validateThemove
 */
public class MushroomTest {
	
	private Mushroom mushroom ;
	private Board board;
	
	@Before
	public void setUp() throws Exception {
		mushroom = new Mushroom("Mushroom1");
		board = new Board();
	}

	@Test
	public void ConstructorTest() {
		assertEquals(mushroom.toString(), "Mushroom1");
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

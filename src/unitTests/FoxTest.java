package unitTests;

import static org.junit.Assert.*;
import JumpIn.*;

import org.junit.Before;
import org.junit.Test;
/*
 * @author TS
 * 
 * This class is testing the Fox constructor through ConstructorTest(),
 * testing the Fox movement through ValidatemoveTest(),
 * testing the fox type so it's either HORIZONTAL or VERTICAL
 * and testing the setpositionTest through PositionTest()
 * 
 */
public class FoxTest {
	
	private Fox fox1,fox2;
	
	@Before
	public void setUp() throws Exception {
		fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL, true);
		fox2 = new Fox("F2", Fox.FoxType.VERTICAL, true);
	}
	
	/*
	 * testing the constructor by passing the fox type
	 * and also checking if it has the correct name or not
	 * by using toString
	 */
	@Test
	public void ConstructorTest() {
		assertEquals(fox1.getPieceType(),Piece.Type.FOX);
		assertEquals(fox1.toString(),"F1");
		assertEquals(fox2.getPieceType(),Piece.Type.FOX);
		assertEquals(fox2.toString(),"F2");
	}
	/*
	 * testing the type of each fox
	 */
	@Test
	public void getFoxTypeTest() {
		assertTrue(fox1.isHorizontal());
		assertFalse(fox2.isHorizontal());
	}
	/*
	 * testing the position of the two foxes
	 */
	@Test
	public void setPositionTest() {
		fox1.setPosition(3, 3);
		assertEquals("33",fox1.getXPos() +""+ fox1.getYPos());
		fox2.setPosition(1, 1);
		assertEquals("11",fox2.getXPos() +""+ fox2.getYPos());
	}
	/*
	 * We are testing the only valid positions for the horizontal fox
	 * and vertical fox
	 */
	@Test
	public void ValidatemoveTest() {
		assertEquals(true,fox1.validateMove(fox1.getXPos()-1,fox1.getYPos()));
		assertEquals(true,fox1.validateMove(fox1.getXPos()-2,fox1.getYPos()));
		assertEquals(true,fox1.validateMove(fox1.getXPos()-3,fox1.getYPos()));
		
		assertEquals(true,fox2.validateMove(fox2.getXPos(),fox2.getYPos()+1));
		assertEquals(true,fox2.validateMove(fox2.getXPos(),fox2.getYPos()+2));
		assertEquals(true,fox2.validateMove(fox2.getXPos(),fox2.getYPos()+3));

		}	
	}	
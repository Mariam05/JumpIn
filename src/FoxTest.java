import static org.junit.Assert.*;

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
	Fox associatePart;
	
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
	 * check if the foxes are head or tail because the fox occupies two squares
	 */
	@Test
	public void isHeadTest() {
		assertEquals(true,fox1.isHead());
		assertEquals(true,fox2.isHead());

	}
	
	/*
	 * We are checking if we added the associated part or not for the fox
	 */
	@Test
	public void addAssociatedPartTest() {
		assertEquals(true,fox1.addAssociatedPart(this.associatePart));
		assertEquals(true,fox2.addAssociatedPart(this.associatePart));

	}
	
	/*
	 * testing the type of each fox
	 */
	@Test
	public void getFoxTypeTest() {
		assertEquals(fox1.getFoxType(),Fox.FoxType.HORIZONTAL);
		assertEquals(fox2.getFoxType(),Fox.FoxType.VERTICAL);

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
	@Test
	public void ValidatemoveTest() {
		assertEquals(true,fox1.validateMove(fox1.getXPos(),fox1.getYPos()-1));
		assertEquals(true,fox1.validateMove(fox1.getXPos(),fox1.getYPos()-2));
		assertEquals(true,fox1.validateMove(fox1.getXPos(),fox1.getYPos()-3));
		if(fox1.getYPos() <= 2) {
			assertEquals(true,fox2.validateMove(fox2.getXPos()+1,fox2.getYPos()));
			assertEquals(true,fox2.validateMove(fox2.getXPos()+2,fox2.getYPos()));
		}else {
			assertEquals(true,fox2.validateMove(fox2.getXPos() + 1,fox1.getYPos()));
		}	
	}
	*/
	
}

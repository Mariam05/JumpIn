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
	
	@Before
	public void setUp() throws Exception {
		fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL);
		fox2 = new Fox("F2", Fox.FoxType.VERTICAL);
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
		fox1.setPosition(3, 3);
		assertEquals(fox1.getFoxType(),Fox.FoxType.HORIZONTAL);
		assertEquals(fox2.getFoxType(),Fox.FoxType.VERTICAL);

	}
	/*
	 * testing the position of the two foxes
	 */
	@Test
	public void setPositionTest() {
		
		assertEquals(fox1.getFoxType(),Fox.FoxType.HORIZONTAL);
		assertEquals(fox2.getFoxType(),Fox.FoxType.VERTICAL);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

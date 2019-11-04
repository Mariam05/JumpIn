import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author TS
 * 
 * This class is testing the rabbit constructor through ConstructorTest(),
 * testing the rabbit movement through ValidatemoveTest()
 * and testing the positionTest through PositionTest()
 */
public class RabbitTest {

	private Rabbit rabbit1, rabbit2, rabbit3;
	
	@Before
	public void setUp() throws Exception {
		rabbit1 = new Rabbit("Rabbit1");
		rabbit2 = new Rabbit("Rabbit2");
		rabbit3 = new Rabbit("Rabbit3");
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals(rabbit1.getPieceType(),Piece.Type.RABBIT);
		assertEquals(rabbit1.toString(),"Rabbit1");
		assertEquals(rabbit2.getPieceType(),Piece.Type.RABBIT);
		assertEquals(rabbit2.toString(),"Rabbit2");
		assertEquals(rabbit3.getPieceType(),Piece.Type.RABBIT);
		assertEquals(rabbit3.toString(),"Rabbit3");
	}
	
	/*
	 * we'll add the x-position with the y-position so the total is 5 as 2 + 3 = 5
	 */
	@Test
	public void PositionTest() {
		rabbit1.setPosition(2,3);
		assertEquals("23",rabbit1.getXPos() +""+ rabbit1.getYPos()+"");
		rabbit2.setPosition(4,1);
		assertEquals("41",rabbit2.getXPos() +""+ rabbit2.getYPos()+"");
		rabbit3.setPosition(2,4);
		assertEquals("24",rabbit3.getXPos() +""+ rabbit3.getYPos()+"");
	}
	
	
	/*
	 * The only valid movement for the {grey rabbit"rabbit1"} or
	 * {whiteRabbit"rabbit2} or {yellowRabbit"rabbit"} is to be jump above the mushrooms or foxes
	 * and other than that it's invalid
	 */
	@Test
	public void ValidatemoveTest() {
		//the greyRabbit
		assertEquals(true,rabbit1.validateMove(rabbit1.getXPos()+2, rabbit1.getYPos()));
		//the wihiteRabbit
		assertEquals(true,rabbit2.validateMove(rabbit2.getXPos(), rabbit1.getYPos()+2));
		//the yellowRabbit
		assertEquals(true,rabbit3.validateMove(rabbit3.getXPos()+2, rabbit3.getYPos()));	
	}

}

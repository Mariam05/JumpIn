package unitTests;
import static org.junit.Assert.*;

import java.awt.Color;

import JumpIn.*;
import org.junit.Before;
import org.junit.Test;

/*
 * @author Taher Shabaan
 * 
 * This class is testing the rabbit constructor through ConstructorTest(),
 * testing the rabbit movement through ValidatemoveTest()
 * and testing the positionTest through PositionTest()
 */
public class RabbitTest {

	private Rabbit rabbit1, rabbit2, rabbit3;
	private Board board;
	private Piece fox1, fox1T;
	
	@Before
	public void setUp() throws Exception {
		rabbit1 = new Rabbit("Rabbit1",Color.white);
		rabbit2 = new Rabbit("Rabbit2",Color.gray);
		rabbit3 = new Rabbit("Rabbit3",Color.yellow);
		board = new Board();
		fox1 = new Fox("F1H", Fox.FoxType.HORIZONTAL, true);
		fox1T = ((Fox) fox1).getAssociatedPart();
	}
	
	@Test
	public void ConstructorTest() {
		assertEquals(rabbit1.toString(),"Rabbit1");
		assertEquals(rabbit2.toString(),"Rabbit2");
		assertEquals(rabbit3.toString(),"Rabbit3");
	}
	
	/*
	 * we'll add the x-position with the y-position so the total is 5 as 2 + 3 = 5
	 */
	@Test
	public void PositionTest() {
		rabbit1.setPosition(2,3);
		assertEquals("23",rabbit1.getXPos() +""+ rabbit1.getYPos());
		rabbit2.setPosition(4,1);
		assertEquals("41",rabbit2.getXPos() +""+ rabbit2.getYPos());
		rabbit3.setPosition(2,4);
		assertEquals("24",rabbit3.getXPos() +""+ rabbit3.getYPos());
	}
	
	/*
	 * get String Representation for each rabbit
	 */
	@Test
	public void getStringRepresentationTest() {
		assertEquals("Rabbit1"+ ","  +rabbit1.getXPos()+","+rabbit1.getYPos(),rabbit1.getStringRepresentation());
		assertEquals("Rabbit2"+ ","  +rabbit2.getXPos()+","+rabbit2.getYPos(),rabbit2.getStringRepresentation());
		assertEquals("Rabbit3"+ ","  +rabbit3.getXPos()+","+rabbit3.getYPos(),rabbit3.getStringRepresentation());
	}
	
	/*
	 * testing if manufacture Piece is working and creating a new rabbit or not or not
	 */
	@Test
	public void manufacturePieceTests() {
		//testing rabbit 1
		String str = "Rabbit1"+ ","  +rabbit1.getXPos()+","+rabbit1.getYPos();
		Piece rabbitW  = rabbit1.manufacturePiece(str);
		assertEquals(rabbitW.toString(),"Rabbit1");
		//testing rabbit 2
		String str1 = "Rabbit2"+ ","  +rabbit2.getXPos()+","+rabbit2.getYPos();
		Piece rabbitG  = rabbit2.manufacturePiece(str1);
		assertEquals(rabbitG.toString(),"Rabbit2");
		
		//testing rabbit 2
		String str2 = "Rabbit3"+ ","  +rabbit3.getXPos()+","+rabbit3.getYPos();
		Piece rabbitY  = rabbit3.manufacturePiece(str2);
		assertEquals(rabbitY.toString(),"Rabbit3");
	}
	
	
	
	/*
	 * The only valid movement for the {grey rabbit"rabbit1"} or
	 * {whiteRabbit"rabbit2} or {yellowRabbit"rabbit"} is to be jump above the mushrooms or foxes
	 * and other than that it's invalid
	 */
	@Test
	public void ValidatemoveTest() {
		board.addPiece(rabbit1, 3, 0);
		board.addPiece(rabbit2, 4, 2);
		board.addPiece(rabbit3, 1, 4);
		// Add the Fox on the board to pass the second test "GreyRabbit" to the board.
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox1T, fox1T.getXPos(), fox1T.getYPos()); // add fox1 tail
	
		//the whiteRabbit
		assertEquals(true,rabbit1.validateMove(board, rabbit1.getXPos(), rabbit1.getYPos()+2));

		//the grayRabbit
		assertEquals(true,rabbit2.validateMove(board, rabbit2.getXPos(), rabbit2.getYPos()+2));
		
		//the yellowRabbit
		 assertEquals(true,rabbit3.validateMove(board, rabbit3.getXPos()+2, rabbit3.getYPos()));	
	}
	
	/*
	 * testing if the handleMove method is working properly or not!
	 */
	@Test
	public void handleMoveTest() {
		board.addPiece(rabbit1, 3, 0);
		board.addPiece(rabbit2, 4, 2);
		board.addPiece(rabbit3, 1, 4);
		// Add the Fox on the board to pass the second test "GreyRabbit" to the board.
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox1T, fox1T.getXPos(), fox1T.getYPos()); // add fox1 tail
		
		//first we're storing the pervious values then adding the difference to see
		//if the function's works or not
		int columX = rabbit1.getXPos();
		int rowY = rabbit1.getYPos();
		
		rabbit1.handleMove(board, 3,2);
		assertEquals(columX,rabbit1.getXPos());
		assertEquals(rowY + 2,rabbit1.getYPos());

		int columX1 = rabbit2.getXPos();
		int rowY1 = rabbit2.getYPos();
		
		rabbit2.handleMove(board, 4,4);
		assertEquals(columX,rabbit1.getXPos());
		assertEquals(rowY + 2,rabbit1.getYPos());
		
		int columX2 = rabbit3.getXPos();
		int rowY2 = rabbit3.getYPos();
		
		rabbit3.handleMove(board, 3,4);
		assertEquals(columX2+2,rabbit3.getXPos());
		assertEquals(rowY2 ,rabbit3.getYPos());
	}

}

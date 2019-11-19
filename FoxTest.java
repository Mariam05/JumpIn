package unitTests;

import static org.junit.Assert.*;
import JumpIn.*;
import JumpIn.Fox.FoxType;

import org.junit.Before;
import org.junit.Test;
/*
 * @author Taher Shabaan
 * 
 * "TESTING is completely different from MILESTONE 2, as we added methods and changed the current methods"
 *
 *
 * This class is testing the Fox constructor through ConstructorTest(),
 * testing the Fox movement through ValidatemoveTest(),
 * testing the fox type so it's either HORIZONTAL or VERTICAL
 * and testing the setpositionTest through PositionTest()
 *  
 * 
 */
public class FoxTest {
	
	private Fox fox1,fox2,foxH,foxY,fox1Tail,fox2Tail;
	private Board board;

	@Before
	public void setUp() throws Exception {
		fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL, true);
		fox2 = new Fox("F2", Fox.FoxType.VERTICAL, true);
		board = new Board();
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
	 * testing if it's his fox head or not
	 */
	@Test
	public void isHeadTest() {
		assertEquals(true,fox1.isHead());
		assertEquals(true,fox2.isHead());
	}
	/*
	 * testing if the string representation is correct or not
	 */
	@Test
	public void getStringRepresentationTest() {
		assertEquals("F1,HORIZONTAL,true,"+fox1.getXPos()+","+fox1.getYPos(),fox1.getStringRepresentation());
		assertEquals("F2,VERTICAL,true,"+fox2.getXPos()+","+fox2.getYPos(),fox2.getStringRepresentation());
	}
	
	/*
	 * testing if manufacture Piece is working and creating a new fox or not
	 */
	@Test
	public void manufacturePieceTests() {
		String str = "F1,HORIZONTAL,true,"+fox1.getXPos()+","+fox1.getYPos();
		Fox f  = fox1.manufacturePiece(str);
		assertEquals(f.toString(),"F1");
	}
	
	/*
	 * testing the getAssociatedPart method so to see if the method is working
	 *  and returning the exact form or not 
	 */
	@Test
	public void getAssociatedPartTest() {
		foxH = new Fox("F"+fox1.toString().charAt(1)+ "T", FoxType.HORIZONTAL,false);
		foxY = new Fox("F"+fox2.toString().charAt(1)+ "T", FoxType.VERTICAL,false);
		assertEquals(fox1.getAssociatedPart(),foxH);
		assertEquals(fox2.getAssociatedPart(),foxY);
	}

	/*
	 * testing if the fox is horizontal or vertical
	 */
	@Test
	public void isHorizontalTest() {
		assertEquals(true,fox1.isHorizontal());
		assertEquals(false,fox2.isHorizontal());
	}
	
	/*
	 * We are testing the only valid positions for the horizontal fox
	 * and vertical fox
	 */
	@Test
	public void ValidatemoveTest() {
		
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox2, 1, 1);
		fox1Tail = fox1.getAssociatedPart();
		fox2Tail = fox2.getAssociatedPart();
		board.addPiece(fox1Tail,3,3);
		board.addPiece(fox2Tail,1,0);
		
		//Testing the horizontal fox
		assertEquals(true,fox1.validateMove(board, fox1.getXPos()-1, fox1.getYPos()));
		assertEquals(true,fox1.validateMove(board, fox1.getXPos()-2, fox1.getYPos()));
		assertEquals(true,fox1.validateMove(board, fox1.getXPos()-3, fox1.getYPos()));
		assertEquals(true,fox1Tail.validateMove(board, fox1Tail.getXPos()-1, fox1Tail.getYPos()));
		assertEquals(true,fox1Tail.validateMove(board, fox1Tail.getXPos()-2, fox1Tail.getYPos()));
		assertEquals(true,fox1Tail.validateMove(board, fox1Tail.getXPos()-3, fox1Tail.getYPos()));

		//Testing the vertical fox
		assertEquals(true,fox2.validateMove(board, fox2.getXPos(), fox2.getYPos()+1));
		assertEquals(true,fox2.validateMove(board, fox2.getXPos(), fox2.getYPos()+2));
		assertEquals(true,fox2.validateMove(board, fox2.getXPos(), fox2.getYPos()+3));
		assertEquals(true,fox2Tail.validateMove(board, fox2Tail.getXPos(), fox2Tail.getYPos()+1));
		assertEquals(true,fox2Tail.validateMove(board, fox2Tail.getXPos(), fox2Tail.getYPos()+2));
		assertEquals(true,fox2Tail.validateMove(board, fox2Tail.getXPos(), fox2Tail.getYPos()+3));
	}	
	
	/*
	 * testing of handling move method and see if the functionallity is working or not
	 */
	@Test
	public void handleMoveTest() {
		//first we'll add the pieces
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox2, 1, 1);
		fox1Tail = fox1.getAssociatedPart();
		fox2Tail = fox2.getAssociatedPart();
		board.addPiece(fox1Tail,3,3);
		board.addPiece(fox2Tail,1,0);
		
		int coluX = fox1Tail.getXPos();
		int rowY = fox1Tail.getYPos();
		fox1Tail.handleMove(board, 2, 3);
		//testing for the horizontal fox tail
		assertEquals(coluX-1,fox1Tail.getXPos());
		assertEquals(rowY,fox1Tail.getYPos());
		
		int coluX1 = fox2.getXPos();
		int rowY1 = fox2.getYPos();
		fox2.handleMove(board, 1, 2);
		//testing for the head of vertical fox
		assertEquals(coluX1,fox2.getXPos());
		assertEquals(rowY1+1,fox2.getYPos());
			
	}
}	
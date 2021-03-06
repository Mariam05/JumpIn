package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JumpIn.Command;

import JumpIn.Game;


public class RedoTest {

	private Game game;
	private Command command;
	private Command command1;
	private Command command4;
	private Command command2;
	
	@Before
	public void setUp() throws Exception {
		game = new Game("4"); 
		command = new Command("move","RA1","32");
		command1 = new Command("move","RA2","22");
		command4 = new Command("move","F2V","13");
		command2 = new Command("move","RA1","34");
	}
	
	/*
	 * Testing Undo with no moves made
	 */
	@Test
	public void redotestwithNoUndo() {
		//Check if redo will not work with no moves made
		assertEquals(false,game.redo());
		
		//Testing if all pieces stayed where they are
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 0).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(4, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
	}
	
	/*
	 * Testing Undo after one move
	 */
	@Test
	public void redotestwithOneUndo() {
		
		game.processCommand(command);
		//check if redo is performing correctly
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces stayed where they are
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(4, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
		
	}
	
	/*
	 * Testing Undo after two moves
	 */
	@Test
	public void redotestwithtwoUndo() {
		
		game.processCommand(command);
		game.processCommand(command1);
		//check if redo is performing correctly
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(4, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
		
	}
	
	/*
	 * Testing Undo after three moves
	 */
	@Test
	public void redotestwithThreeMoves() {
		
		game.processCommand(command);
		game.processCommand(command1);
		game.processCommand(command4);
		//check if redo is performing correctly
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 3).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 2).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(4, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
	}
	
	/*
	 * Testing Undo after Four moves
	 */
	@Test
	public void redotestwithFourMoves() {
		
		game.processCommand(command);
		game.processCommand(command1);
		game.processCommand(command4);
		game.processCommand(command2);
		//check if undo is performing correctly
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 4).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 3).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 2).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 3).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 2).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
		
		assertEquals(true,game.undo());
		assertEquals(true,game.undo());
		assertEquals(true,game.redo());
		//Testing if all pieces are in the correct place
		//Rabbit1
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),"RA1");
		//Rabbit2
		assertEquals(game.getBoard().getSquare(4, 2).getPieceString(),"RA2");
		//Rabbit3
		assertEquals(game.getBoard().getSquare(1, 4).getPieceString(),"RA3");
		//Horizontal Fox
		assertEquals(game.getBoard().getSquare(4, 3).getPieceString(),"F1H");
		assertEquals(game.getBoard().getSquare(3, 3).getPieceString(),"F1T");
		//Vertical Fox
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),"F2V");
		assertEquals(game.getBoard().getSquare(1, 0).getPieceString(),"F2T");
	}

}

package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import JumpIn.Command;
import JumpIn.Game;

public class SaveLoadTest {

	private Game game;
	private Game loadedGame;
	private Command command;
	private Command command1;
	private Command command4;
	private Command command2;
	
	@Before
	public void setUp() throws Exception {
		game = new Game();
		command = new Command("move","RA1","32");
		command1 = new Command("move","RA2","22");
		command4 = new Command("move","F2V","13");
		command2 = new Command("move","RA1","34");
	}

	/*
	 * Tests if file is saved and loaded correctly
	 */
	@Test
	public void SaveLoadtest() {
		//Do some moves to change state of the game
		game.processCommand(command);
		game.processCommand(command1);
		game.processCommand(command4);
		game.processCommand(command2);
		
		//save the game
		game.saveGame();
		
		//check if loaded game is equal to the previously saved game
		loadedGame = game.loadGame();
		
		//Testing if all pieces are in the correct place
				//Rabbit1
				assertEquals(loadedGame.getBoard().getSquare(3, 4).getPieceString(),"RA1");
				//Rabbit2
				assertEquals(loadedGame.getBoard().getSquare(2, 2).getPieceString(),"RA2");
				//Rabbit3
				assertEquals(loadedGame.getBoard().getSquare(1, 4).getPieceString(),"RA3");
				//Horizontal Fox
				assertEquals(loadedGame.getBoard().getSquare(4, 3).getPieceString(),"F1H");
				assertEquals(loadedGame.getBoard().getSquare(3, 3).getPieceString(),"F1T");
				//Vertical Fox
				assertEquals(loadedGame.getBoard().getSquare(1, 3).getPieceString(),"F2V");
				assertEquals(loadedGame.getBoard().getSquare(1, 2).getPieceString(),"F2T");		
	}
	
	/*
	 * Test if undo works after loading game
	 */
	@Test
	public void UndoTestafterLoadingGame() {
		//Do some moves to change state of the game
				game.processCommand(command);
				game.processCommand(command1);
				game.processCommand(command4);
				game.processCommand(command2);
				
				//save the game
				game.saveGame();
				
				//check if loaded game is equal to the previously saved game
				loadedGame = game.loadGame();
				
				assertEquals(true,loadedGame.undo());
				
				//Testing if all pieces are in the correct place
				//Rabbit1
				assertEquals(loadedGame.getBoard().getSquare(3, 2).getPieceString(),"RA1");
				//Rabbit2
				assertEquals(loadedGame.getBoard().getSquare(2, 2).getPieceString(),"RA2");
				//Rabbit3
				assertEquals(loadedGame.getBoard().getSquare(1, 4).getPieceString(),"RA3");
				//Horizontal Fox
				assertEquals(loadedGame.getBoard().getSquare(4, 3).getPieceString(),"F1H");
				assertEquals(loadedGame.getBoard().getSquare(3, 3).getPieceString(),"F1T");
				//Vertical Fox
				assertEquals(loadedGame.getBoard().getSquare(1, 3).getPieceString(),"F2V");
				assertEquals(loadedGame.getBoard().getSquare(1, 2).getPieceString(),"F2T");
	}
	
	/*
	 * Test if redo works after loading game
	 */
	@Test
	public void RedoTestafterLoadingGame() {
		//Do some moves to change state of the game
				game.processCommand(command);
				game.processCommand(command1);
				game.processCommand(command4);
				game.processCommand(command2);
				
				//save the game
				game.saveGame();
				
				//check if loaded game is equal to the previously saved game
				loadedGame = game.loadGame();
				
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
	}
}

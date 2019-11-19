package unitTests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import JumpIn.*;

/**
 * Test the functionality of the solver.
 * Because we used BFS, we know that the solution has to be the shortest, and we use the solution from 
 * the original JumpIn game manual. Therefore, we test the solver by making it solve the original puzzle (with no
 * moves applied) and checking that each corresponding hint is correct. 
 * 
 * We also make sure that the solver works if the player moves the pieces randomly.
 * 
 * @author Hassan Hassan
 *
 */
public class SolverTest {

	private Game game;
	private Solver solver;
	private Command command;
	private Command command1;
	private Command command2;
	private Command command3;
	private Command command4;
	private Command command5;
	private Command random;
	private Command random1;
	private Command random2;
	private Command random3;

	//Best possible scenario is move RA1 32 , move RA2 22 , move RA1 34 , move RA3 44 , move F2V 14 , move RA1 04
	
	@Before
	public void setUp() throws Exception {
		game = new Game();
		command = new Command("move","RA1","32");
		command1 = new Command("move","RA2","22");
		command2 = new Command("move","RA1","34");
		command3 = new Command("move","RA3","44");
		command4 = new Command("move","F2V","14");
		command5 = new Command("move","RA1","04");
		random = new Command("move","F1H","13");
		random1 = new Command("move","RA3","12");
		random2 = new Command("move","RA3","34");
		random3 = new Command("move","F2V","12");
	}
	
	/*
	 * Testing GetHint with no moves made
	 */
	@Test
	public void testGetHint() {
		//Testing hint before any move is made
		solver = new Solver(game);
		assertEquals(command,solver.getHint());		
	}
	
	/*
	 * Testing getHint after the best first move is made
	 */
	@Test
	public void testGetHintAfterMove() {
		//Testing hint after one move is made
		game.handleMove(command);
		solver = new Solver(game);
		assertEquals(command1,solver.getHint());
	}
	
	/*
	 * Testing getHint after the best second move is made
	 */
	@Test
	public void testGetHintAfterTwoMove() {
		//Testing hint after two moves are made
		game.handleMove(command);
		game.handleMove(command1);
		solver = new Solver(game);
		assertEquals(command2,solver.getHint());
	}
	
	/*
	 * Testing getHint after the best third move is made
	 */
	@Test
	public void testGetHintAfterThirdMove() {
		//Testing hint after three moves are made
		game.handleMove(command);
		game.handleMove(command1);
		game.handleMove(command2);
		solver = new Solver(game);
		assertEquals(command3,solver.getHint());
	}
	
	/*
	 * Testing getHint after the best fourth move is made
	 */
	@Test
	public void testGetHintAfterFourthMove() {
		//Testing hint after four moves are made
		game.handleMove(command);
		game.handleMove(command1);
		game.handleMove(command2);
		game.handleMove(command3);
		solver = new Solver(game);
		assertEquals(command4,solver.getHint());
	}
	
	/*
	 * Testing getHint after the best fifth move is made
	 */
	@Test
	public void testGetHintAfterFifthMove() {
		//Testing hint after five moves are made
		game.handleMove(command);
		game.handleMove(command1);
		game.handleMove(command2);
		game.handleMove(command3);
		game.handleMove(command4);
		solver = new Solver(game);
		assertEquals(command5,solver.getHint());
	}
	
	/*
	 * Testing getHint after the one random  move is made
	 */
	@Test
	public void testGetHintAfterRandomMove() {
		//Testing hint after random move is made
		game.handleMove(random);
		solver = new Solver(game);
		assertEquals(command,solver.getHint());		
	}
	
	/*
	 * Testing getHint after the two random moves is made
	 */
	@Test
	public void testGetHintAfterTwoRandomMoves() {
		//Testing hint after two random moves are made
		game.handleMove(random);
		game.handleMove(random1);
		solver = new Solver(game);
		assertEquals(command,solver.getHint());		
	}
	
	/*
	 * Testing getHint after the three random moves is made
	 */
	@Test
	public void testGetHintAfterThreeRandomMoves() {
		//Testing hint after three random moves are made
		game.handleMove(random);
		game.handleMove(random1);
		game.handleMove(command);
		solver = new Solver(game);
		assertEquals(command1,solver.getHint());		
	}
	
	/*
	 * Testing getHint after the four random moves is made
	 */
	@Test
	public void testGetHintAfterFourRandomMoves() {
		//Testing hint after three random moves are made in different order
		game.handleMove(random2);//"move","RA3","34"
		game.handleMove(random);//"move","F1H","13"
		game.handleMove(command);//move RA1 32
		game.handleMove(random3);//"move","F2V","12"
		solver = new Solver(game);
		assertEquals(command1,solver.getHint());		
	}
	
	/*
	 * Testing getHint after the five random moves is made
	 */
	@Test
	public void testGetHintAfterFiveRandomMoves() {
		//Testing hint after three random moves are made in different order
		game.handleMove(random2);//"move","RA3","34"
		game.handleMove(random);//"move","F1H","13"
		game.handleMove(command);//move RA1 32
		game.handleMove(random3);//"move","F2V","12"
		game.handleMove(command1);//move RA2 22
		solver = new Solver(game);
		Command comm = new Command("move","RA1","02");
		assertEquals(comm,solver.getHint());		
	}

}

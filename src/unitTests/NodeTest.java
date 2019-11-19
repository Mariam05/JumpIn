import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {

	private Node node;
	private Game game;
	private Command command;
	
	@Before
	public void setUp() throws Exception {
		game = new Game();
		node = new Node(null,game.getAnimalsExceptTails(),null);
		command = new Command("move","RA1","32");
	}

	/*
	 * Testing if the node constructor instantiates its command, parent code and animal list correctly
	 */
	@Test
	public void constructorTest() {
		assertEquals(null,node.getCommand());
		assertEquals(null,node.getParentNode());
		assertEquals(game.getAnimalsExceptTails(),node.getPieces());
	}
	
	/*
	 * Testing if getPieces returns the correct list of pieces of animals
	 */
	@Test
	public void getPiecesTest() {
		//testing with original pieces with no pieces moved
		assertEquals(game.getAnimalsExceptTails(),node.getPieces());
		
		//testing after pieces are moved
		Node node1 = new Node(node,node.getPieces(),new Command("move","RA1","32"));
		Node node2 = new Node(node1,node1.getPieces(),null);
		assertEquals(node2.getPieces(),node1.getPieces());
	}
	
	/*
	 * Testing if getCommand returns the correct command 
	 */
	@Test
	public void getCommandTest() {
		//testing for null command
		assertEquals(null,node.getCommand());
		
		//testing with a command
		node = new Node(null,game.getAnimalsExceptTails(),command);
		assertEquals(command,node.getCommand());
	}
	
	
	/*
	 * Testing if the pieces, holes and mushrooms are placed correctly on each node
	 */
	@Test
	public void getBoardTest() {
		//testing the place of Fox2 on the board if it matches on both boards
		assertEquals(game.getBoard().getSquare(1, 1).getPieceString(),node.getBoard().getSquare(1, 1).getPieceString());
		
		//Testing the place of rabbit1 if it matches on both boards
		assertEquals(game.getBoard().getSquare(3, 0).getPieceString(),node.getBoard().getSquare(3, 0).getPieceString());
		
		//Testing the place of mushroom if it matches on both boards
		assertEquals(game.getBoard().getSquare(2, 4).getPieceString(),node.getBoard().getSquare(2, 4).getPieceString());
		
		//Testing the place of hole if it matches on both boards
		assertEquals(game.getBoard().getSquare(2, 2).getPieceString(),node.getBoard().getSquare(2, 2).getPieceString());
	}
	
	/*
	 * Testing if getParentNode returns the correct node
	 */
	@Test
	public void getParentNodeTest() {
		//Testing with null parent node
		assertEquals(node.getParentNode(),null);
		
		//testing with parent node
		Node parent = new Node(null,game.getAnimalsExceptTails(),null);
		node = new Node(parent,game.getAnimalsExceptTails(),null);
		assertEquals(parent,node.getParentNode());
		
	}
	
	/*
	 * Testing if animals move to the correct position based on command
	 */
	@Test
	public void getAnimalToMoveTest() {
		//Testing if rabbit 1 is moved to column 3 and row 2 in both boards
		game.handleMove(command);
		node = new Node(null,game.getAnimalsExceptTails(),command);
		assertEquals(game.getBoard().getSquare(3, 2).getPieceString(),node.getBoard().getSquare(3, 2).getPieceString()); 
	}
	
	/*
	 * The node constructor calls addAnimalsToBoard method, therefore, test is done without calling it
	 * Testing if animals are added to board
	 */
	@Test
	public void addAnimalsToBoardTest() {		
		assertEquals(game.getAnimalsExceptTails(),node.getPieces()); 
	}
	
	/*
	 * Testing if the node is in winning state meaning when all rabbits are in a hole
	 */
	@Test
	public void isWinningStateTest() {
		//Testing when node is wrong or null
		assertEquals(false,node.isWinningState());
		node = new Node(null,game.getAnimalsExceptTails(),new Command("move","F2V","14"));
		
		//Testing when game is won and node1 is the correct hint
		Node node1 = new Node(node,node.getPieces(),new Command("move","RA1","32"));
		Node node2 = new Node(node1,node1.getPieces(),new Command("move","RA2","22"));
		Node node3 = new Node(node2,node2.getPieces(),new Command("move","RA1","34"));
		Node node4 = new Node(node3,node3.getPieces(),new Command("move","RA3","44"));
		Node node5 = new Node(node4,node4.getPieces(),new Command("move","F2V","14"));
		Node node6 = new Node(node5,node5.getPieces(),new Command("move","RA1","04"));		
		
		assertEquals(true,node6.isWinningState());
	}
	
	/*
	 * Testing if getCopyOfAnimalList returns the correct copy of list of animal pieces
	 */
	@Test
	public void getCopyOfAnimalListTest() {
		//Testing with game pieces on the board
		node.getCopyOfAnimalList(game.getAnimalsExceptTails());
		assertEquals(node.getPieces(),game.getAnimalsExceptTails());
		
		//Testing with another node
		node = new Node(null,game.getAnimalsExceptTails(),new Command("move","F2V","14"));
		Node node1 = new Node(node,node.getPieces(),new Command("move","RA1","32"));
		
		assertEquals(node1.getPieces(),node.getCopyOfAnimalList(node1.getPieces()));
		
	}
	
	/*
	 * Testing if hashCode returns the correct hashCode number 
	 */
	@Test
	public void hashCodeTest() {
		//Testing with an Empty List, hashcode of emtpy list is 1
		List list = new ArrayList<>();
	    Node node1 = new Node(null,list,null);
		assertEquals(32,node1.hashCode());
		
		//Testing with a non-Empty List
		assertEquals(game.getAnimalsExceptTails().hashCode()+(31*1),node.hashCode());
	}
	
	/*
	 * Testing if equals method is implemented correctly
	 */
	@Test
	public void equalsTest() {
		//Testing with two identical nodes
		Node node1 = new Node(null,game.getAnimalsExceptTails(),null);
		assertEquals(true,node.equals(node1));
		
		//Testing with a null object
		assertEquals(false,node.equals(null));
		
		//Testing with different classes
		assertEquals(false,node.equals(game));
		
		//Testing with two different nodes
		assertEquals(false,node.equals(new Node(null,new ArrayList<>(),null)));
		
		assertEquals(false,node.equals(new Node(null,game.getAnimalsExceptTails(),command)));
		
		Node node2 = new Node(node1,game.getAnimalsExceptTails(),command);
		assertEquals(false,node.equals(node2));
	}
	

}

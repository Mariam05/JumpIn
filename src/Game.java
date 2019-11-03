import java.util.HashMap;

/**
 * This is the main class for the JumpIn came. Acts as the controller
 * 
 * @author Mariam Almalki
 * @version 2.0 of JumpIn
 *
 */
public class Game {

	private Parser parser;
	private CommandWord commandWords;
	private Board board;
	private Piece fox1, fox2, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3;
	private HashMap<String, Piece> animalPieces;

	/**
	 * Instantiate the parser and commandWords objects.
	 * Set up the board with the pieces
	 */
	public Game() {
		parser = new Parser();
		commandWords = new CommandWord();
		animalPieces = new HashMap<>();
		board = new Board();

		// Instantiate the pieces on the board
		fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL);
		fox2 = new Fox("F2", Fox.FoxType.VERTICAL);
		mushroom1 = new Mushroom("MS");
		mushroom2 = new Mushroom("MS");
		rabbit1 = new Rabbit("R1");
		rabbit2 = new Rabbit("R2");
		rabbit3 = new Rabbit("R3");
		
		// Add the pieces to the piece hashmap
		animalPieces.put(fox1.toString(), fox1);
		animalPieces.put(fox2.toString(), fox2);
		animalPieces.put(rabbit1.toString(), rabbit1);
		animalPieces.put(rabbit2.toString(), rabbit2);
		animalPieces.put(rabbit3.toString(), rabbit3);
		

		// Add the pieces to the board. Coordinates are row col
		board.addPiece(fox1, 1, 2);
		board.addPiece(fox2, 4, 3);
		board.addPiece(rabbit1, 3, 0);
		board.addPiece(rabbit2, 4, 2);
		board.addPiece(rabbit3, 1, 4);


	}
	
	 /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {            
        printGameInstructions();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
        	board.printBoard();
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) {
    	boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("help")) {
            printGameInstructions();
        }
        else if (commandWord.equalsIgnoreCase("move")) {
            handleMove(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = true;
        }
        else {
            System.out.println("Not a valid command");
        }

        return wantToQuit;
    }
    

	public void handleMove(Command command) {
		//get the piece specified
		Piece piece = null;
		if (piece instanceof Fox) {
			handleFoxMove(piece);
		} else if (piece instanceof Rabbit) {
			handleRabbitMove(piece);
		}
	}

	public void handleFoxMove(Piece fox) {
		
	}

	public void handleRabbitMove(Piece rabbit) {

	}
	
	public void printGameInstructions() {
		String title = "JumpIN Instructions: \n\n";
		String obstacles = "\tThe obstacles are: Mushroom, Fox, Rabbit, Hole.\n\n"
						 + "\tFoxes take up two spaces, head and tail. "
						 + "All other obstacles occupy one square.\n\n";
		
		String movements = "The following explains how the obstacles move around the board:\n\n"
				
						 + "\tRabbit:\n"
				
						 + "\t\tRabbits can only move by jumping over one adjacent obstacle, empty holes are NOT obstacles.\n"
						 + "\t\tOnce a rabbit is in a hole, it can be jumped over by other rabbits.\n"
						 + "\t\tSide note: Rabbits can jump out of their holes to faciliate another rabbit's path.\n\n"
						 + "\t\tRabbits can jump over a fox's waist, or from its head to tail or tail to head.\n\n"
						 
						 + "\tFoxes: \n"
						 
						 + "\t\tFoxes can slide depending on their initial direction, however many spots needed.\n\n"
						
						 + "\tMushrooms and holes are stationary.\n\n";
		
		String objective = "The objective of the game is to move the rabbits and foxes, through a series of movements\n"
				 		 + "around the obstacles untill all the rabbits are safely in their hole.\n";
		String abbreviations = "In this game, the following abbreviations are used to represent each object on the board: " +
				 		 "\n\tR1 - Rabbit 1\n\tR2 - Rabbit 2\n\tR3 - Rabbit 3\n\tF1 - Fox 1\n\tF2 - Fox 2\n\tMS - Mushroom\n\t O - hole \n\t" +
				 		 "When a rabbit has an asterisk beside it means that it is in a hole";
		
		
		System.out.println(title + obstacles + movements  + objective + abbreviations);
		
	}

	
	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}


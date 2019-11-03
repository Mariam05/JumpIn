
/**
 * This is the main class for the JumpIn came. 
 * Acts as the controller
 * @author Mariam Almalki
 * @version 2.0 of JumpIn
 *
 */
public class Game {

    private Parser parser;
    private CommandWord commandWords;
    private Board board;
    private Piece fox1, fox2, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3, hole1, hole2, hole3, hole4, hole5;
    
    /**
     * 
     */
    public Game(){
    	parser = new Parser();
    	commandWords = new CommandWord();
    	
    	//instantiate the pieces on the board
    	fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL);
    	fox2 = new Fox("F2", Fox.FoxType.VERTICAL);
    	mushroom1 = new Mushroom("MS");
    	mushroom2 = new Mushroom("MS");
    	rabbit1 = new Rabbit("R1");
    	rabbit2 = new Rabbit("R2");
    	rabbit3 = new Rabbit("R3");
    	
    	//add the pieces to the board
    	board.addPiece(fox1,1 , 2);
    	board.addPiece(fox2, 4, 3);
    	board.addPiece(rabbit1, 3, 0);
    	board.addPiece(rabbit2, 4, 2);
    	board.addPiece(rabbit3, 1, 4);
    }
    
    public void handleMove(Piece piece) {
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
}
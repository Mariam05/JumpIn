/**
 * This class holds information about a command that was issued by the user.
 * A command currently consists of three parts: 
 * (1) the command word 
 * (2) the piece to move
 * (3) the location to move it.
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 * @author Mariam Almalki
 *
 */
public class Command {
	private String commandWord;
	private int piece;
	private int destinationPos;
	
	
    /**
     * Create a command object. The fields must be supplied but can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param piece The piece on the board selected
     * @param destinationPos The position to move the piece. 
     */
    public Command(String firstWord, int piece, int destinationPos)
    {
        commandWord = firstWord;
        this.piece = piece;
        this.destinationPos = destinationPos;
    }
}

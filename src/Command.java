/**
 * This class holds information about a command that was issued by the user. A
 * command currently consists of three parts: (1) the command word (2) the piece
 * to move (3) the location to move it.
 * 
 * The way this is used is: Commands are already checked for being valid command
 * words. If the user entered an invalid command (a word that is not known) then
 * the command word is <null>.
 * 
 * @author Mariam Almalki
 *
 */
public class Command {
	private String commandWord;
	private String piece;
	private String destinationPos;
	private int xPos, yPos;

	/**
	 * Create a command object. The fields must be supplied but can be null.
	 * 
	 * @param firstWord      The first word of the command. Null if the command was
	 *                       not recognised.
	 * @param piece          The piece on the board selected
	 * @param destinationPos The position to move the piece.
	 */
	public Command(String firstWord, String piece, String destinationPos) {
		commandWord = firstWord;
		this.piece = piece;
		this.destinationPos = destinationPos;
		splitDestination();
	}

	/**
	 * Get the command word
	 * 
	 * @return String of command word
	 */
	public String getCommandWord() {
		return commandWord;
	}

	/**
	 * Return the command word (the first word) of this command. If the command was
	 * not understood, the result is null.
	 * 
	 * @return The command word.
	 */
	public String getPiece() {
		return piece;
	}

	/**
	 * @return The second word of this command. Returns null if there was no second
	 *         word.
	 */
	public String getDestination() {
		return destinationPos;
	}

	/**
	 * Get the x position that the player wants to go to
	 * 
	 * @return int x (column) pos
	 */
	public int getX() {
		return xPos;
	}

	/**
	 * Get the Y position that the player wants to go to
	 * 
	 * @return int y (row) pos
	 */
	public int getY() {
		return yPos;
	}

	/**
	 * Split the destination, which is in the form of Col Row, into x and y
	 * positions
	 */
	private void splitDestination() {
		if (hasDestination()) {
			int destination = Integer.parseInt(destinationPos);
			xPos = destination / 10; // the column
			yPos = destination % 10;
		}
	}

	/**
	 * @return true if this command was not understood.
	 */
	public boolean isUnknown() {
		return (commandWord == null);
	}

	/**
	 * @return true if the command has a second word.
	 */
	public boolean hasPiece() {
		return (piece != null);
	}

	public boolean hasDestination() {
		return destinationPos != null;
	}
}
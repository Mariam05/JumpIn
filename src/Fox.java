<<<<<<< HEAD
/**
 * 
 * @author Nazifa Tanzim
 *
 */
public class Fox extends Piece{
	/**
	 * 
	 * @param FoxName
	 * @param type
	 */
	public Fox(String FoxName) {
		super(FoxName);
	}
	
	/**
	 * Make sure that the fox's move is valid, and if yes then move it.
	 * 
	 * @param animal the fox to move
	 * @param newX   the new X position (row) to go to
	 * @param newY   the new Y position (column) to go to
	 */
	@Override
	protected boolean validateMove(Board board, Piece animal, int currPos, int destinationPos) {
		int currX = animal.getPosition() / 10;
		int currY = animal.getPosition() % 10;
		int newX = destinationPos / 10;
		int newY = destinationPos % 10;

		Direction d = getDirection(currX, currY, newX, newY);

		// If it is fox1
		if (animal.getName().equals("F1")) {
			return this.handleFox1Move(board, animal, currX, currY, newX, d);
		}

		// If it is fox2
		else if (animal.getName().equals("F2")) {
			return this.handleFox2Move(board, animal, currY, currX, newY, d);
		}
		else {
			return false;
		}
	}

	/**
	 * Move fox1 (which goes vertically) to the desired location
	 * @param animal fox1 
	 * @param currX it's current row position
	 * @param currY it's current column position
	 * @param newX the new row position
	 * @param d the direction it's going (either up or down)
	 */
	private boolean handleFox1Move(Board board, Piece animal, int currX, int currY, int newX, Direction d) {
		// Ensure that the player isn't asking us to move it horizontally
		if (d.compareTo(Direction.RIGHT) == 0 || d.compareTo(Direction.LEFT) == 0) {
			//invalidDirectionMessage();
			return false;
		}

		if (d.compareTo(Direction.UP) == 0) {
			int headPos = currX - 1;
			// Make sure that every square in between is empty before moving the fox
			for (int i = newX; i < headPos; i++) {
				// Returns false if the path is not clear
				if (!(board.getSquare(i, currY).isEmpty())) {
					//invalidDirectionMessage();
					return false;
				}
			}
			// Moves the fox up if the path is clear
			board.getSquare(newX, currY).addAnimal(animal); // fox's tail
			board.getSquare(newX + 1, currY).addAnimal(animal); // fox's head. This will be set as f1's position

		} 
		else if (d.compareTo(Direction.DOWN) == 0) { // otherwise the fox is sliding down
			// Make sure that every square in between is empty before moving the fox
			for (int i = currX + 1; i <= newX; i++) {
				// Returns false if the path is not clear
				if (!(board.getSquare(i, currY).isEmpty())) { 
					//invalidDirectionMessage();
					return false;
				}
			}
			// Moves fox down if the path is clear
			board.getSquare(newX - 1, currY).addAnimal(animal); // fox's tail
			board.getSquare(newX, currY).addAnimal(animal); // fox's head. This will be set as f1's position
		}
		// Removes fox from original spot
		board.getSquare(currX, currY).removeAnimal();
		board.getSquare(currX - 1, currY).removeAnimal();
		
		// Returns true if fox is moved successfully
		return true;
	}

	/**
	 * Move fox2 (which goes horizontally) to the desired location
	 * @param animal fox2
	 * @param currY it's current column position
	 * @param currX it's current row position
	 * @param newY the new column position
	 * @param d the direction it's going (either right or left)
	 */
	private boolean handleFox2Move(Board board, Piece animal, int currY, int currX, int newY, Direction d) {

		// Ensure that the player isn't asking us to move it vertically
		if (d.compareTo(Direction.UP) == 0 || d.compareTo(Direction.DOWN) == 0 || d.compareTo(Direction.INVALID) == 0 ) {
			//invalidDirectionMessage();
			return false;
		}

		if (d.compareTo(Direction.LEFT) == 0) {
			int headPos = currY - 1;
			// Make sure that every square in between is empty before moving the fox
			for (int i = newY; i < headPos; i++) {
				if (!(board.getSquare(currX, i).isEmpty())) {
					//invalidDirectionMessage();
					return false;
				}
			}
			// Update position
			board.getSquare(currX, newY).addAnimal(animal); // fox's tail
			board.getSquare(currX, newY + 1).addAnimal(animal); // fox's head. This will be set as f1's position
			// Removing from old position
			board.getSquare(currX, currY).removeAnimal();
			board.getSquare(currX, currY - 1).removeAnimal();
		} 
		else if (d.compareTo(Direction.RIGHT) == 0) { // otherwise the fox is sliding down

			for (int i = currY + 1; i <= newY; i++) {
				if (!(board.getSquare(currX, i).isEmpty())) {
					//invalidDirectionMessage();
					return false;
				}
			}
			// Moving fox right
			board.getSquare(currX, newY - 1).addAnimal(animal); // fox's tail
			board.getSquare(currX, newY).addAnimal(animal); // fox's head. This will be set as f1's position
			// Removing from old position
			board.getSquare(currX, currY).removeAnimal(); // fox's head
			board.getSquare(currX, currY - 1).removeAnimal(); // fox's tail
		}		
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
}
=======

public class Fox extends Piece{

	protected Fox(String pieceName) {
		super(pieceName, Type.FOX);
	}

	@Override
	public boolean validateMove(int currPos, int destinationPos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
>>>>>>> 2f7569ce2c651fd6d6eea382a42f46946cfba70e

/**
 * 
 * @author Nazifa Tanzim
 *
 */
public class Rabbit extends Piece{
	// TODO fix rabbit functionality
	// still moving to a position even if there is an empty space
	private boolean inHole;
	
	/**
	 * 
	 * @param RabbitName
	 */
	public Rabbit(String RabbitName) {
		super(RabbitName);
		this.inHole = false;
	}
	
	/**
	 * Returns true if the rabbit is in a hole
	 * 
	 * @return
	 */
	@Override
	public boolean isInHole() {
		return this.inHole;
	}
	/**
	 * Moves the rabbit to the desired location
	 * @param animal the rabbit to move
	 * @param newX its new row position
	 * @param newY its new column position
	 * @param currX its current row position
	 * @param currY its current column position
	 */
	private boolean handleRabbitMove(Board board, Piece animal, int newX, int newY, int currX, int currY) {
		
		//Check if the rabbit is currently in a hole
		if (board.getSquare(currX, currY).isHole()) {
			this.inHole = false;
		}
		
		//If new position is a hole
		if (board.getSquare(newX, newY).isHole()) {
			this.inHole = true;
			System.out.println("You got a rabbit in a hole!");
		}
		
		//Remove the animal from it's current position and move it to new one
		board.getSquare(currX, currY).removeAnimal();
		board.getSquare(newX, newY).addAnimal(animal);
		
		return true;
	}

	/**
	 * Ensures that the desired position to go to is valid
	 * @param animal the rabbit to be moved
	 * @param currPos the current position
	 * @param destinationPos the new position to move it to
	 */
	@Override
	protected boolean validateMove(Board board, Piece animal, int currPos, int destinationPos) {
		int currX = animal.getPosition() / 10;
		int currY = animal.getPosition() % 10;
		int newX = destinationPos / 10;
		int newY = destinationPos % 10;

		// If destination is already filled
		if (!(board.getSquare(newX, newY).isEmpty())) {
			System.out.println("Selected position is filled");
			//this.invalidDirectionMessage();
			return false;
		}
		
		// If destination is empty, check if the path rabbit takes is full of obstacles
		else {
			// Getting the proposed direction
			Direction d = getDirection(currX, currY, newX, newY);
			if (d.compareTo(Direction.INVALID) == 0) {
				//this.invalidDirectionMessage();
				return false;
			}
			// Checking of the paths are filled for each direction
			if (d.compareTo(Direction.UP) == 0) {
				for (int k = currY; k < newY; k++) {
					if (board.getSquare(currX, k).isEmpty()) {
						//this.invalidDirectionMessage();
						return false;
					} 
				}
			} else if (d == Direction.DOWN) {
				for (int k = currY; k > newY; k--) {
					if (board.getSquare(currX, k).isEmpty()) {
						System.out.println("current square " + currX + "" + k);
						//this.invalidDirectionMessage();
						return false;
					}
				}
			} else if (d == Direction.LEFT) {
				for (int k = currX; k > newX; k--) {
					if (board.getSquare(k, currY).isEmpty()){
						//this.invalidDirectionMessage();
						return false;
					} 
				}
			} else if (d == Direction.RIGHT) {
				for (int k = currX; k < newX; k++) {
					if (board.getSquare(k, currY).isEmpty()) {
						//this.invalidDirectionMessage();
						return false;
					} 
				}
			}
		}

		return this.handleRabbitMove(board, animal, newX, newY, currX, currY);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
}

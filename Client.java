package project_M1;

import java.util.*;

public class Client {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		
		
		// creating a rabbit and fox game
		Game game = new Game();
		
		while(~(game.isWinner())) {
			//print board
			game.printBoard();
			
			// ask the user which rabbit or fox the user wants to move
			System.out.println("Which rabbit or fox would you like to move? Choose between R1, R2, R3, F1, F2.");
			
			// input rabbit
			String animal = input.nextLine();
			
			while(~(game.getAnimals().contains(animal))) {
				System.out.println("Please enter valid rabbit or fox");
			}
			
			// input which square  
			System.out.println("To which square?");
			String coordinates = (input.nextLine()).replace(" ","");
			int x = Integer.parseInt(coordinates.substring(0, 1));
			int y = Integer.parseInt(coordinates.substring(1, 2));
			
			//check if path is valid			
			if(game.isValidPath(animal,x,y)) {
				//if yes then move animal
				game.move(animal,x,y);
								
			}else {
				//if not valid
				System.out.println("Invalid move, try again");
			}
					
			
			
		}
		
		System.out.println("Conragulations, you have won the completed the game");
		

	}

}

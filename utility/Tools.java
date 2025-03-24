package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import card.*;

/*
 * This class contains all our utility functions.
 */
public class Tools {

	/**
     * This method takes user input from the console until they enter a valid character and returns it.
     * 
     * @return The character that the user has enter
     */	
	public static char choice(){
		
        Scanner input = new Scanner(System.in);
        char choice;
        
        // The loop runs until a letter is entered by the user.
		do {
            choice = input.next().charAt(0);
            
            // if statement to verify if it's a letter.
            if (!Character.isLetter(choice)) {
                System.out.println("You must enter a letter.");
            }
        } while (!Character.isLetter(choice));
		
        return choice;
    }
	
	/**
     * This method determines who starts the game by selecting the player
	 * with the lowest sum of two initial cards.
     * 
     * @param players An array of all the players currently in the game
     * @return The player with the lowest sum which starts the game
     */
	public static int whoStart(PlayerGrid[] players) {
		
		Card c1,c2;
		int score = -4;
		int leCommenceur = -1;
		
		// for loop running through the array of players.
		for (int i = 0 ; i < players.length ; i++) {
			
			System.out.println("\nPLAYER "+ players[i].getPlayerNumber() +", PICK TWO CARDS :");
			
            // Player i selects the first card and makes it visible.
			c1 = players[i].selection();
			c1.returnVisible();

            // Player i selects the second card that should not be the first card selected and makes it visible.
			while(true){
				c2 = players[i].selection();
				
				if (c2.isVisible())
					System.out.println("Choose a non visible card.");
				else
					break;
			}
			c2.returnVisible();

		// If the sum of the two cards of the player is superior or equals to the variable score. (at first -4 the minimum you can have)
			if (c1.getValue() + c2.getValue() >= score){
				
				// The sum of the two card become the new highest score for the next player in the array.
				score = c1.getValue() + c2.getValue();
				
				// This player is stored in the variable leCommenceur.
				leCommenceur = players[i].getPlayerNumber();
			}

			// It prints the Grid of the player.
			players[i].printGrid();
			
			// Wait for 1 seconds.
			try {
	            Thread.sleep(1000);
	        }catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}

		System.out.println("\nPlayer " + leCommenceur + " start.\n");
		
		// Return the index, adjusted for 0-based indexing.
		return leCommenceur-1;
	}

	/**
     * This method checks if the user entered index is valid.
     * 
     * @param index The index of the card to check
     * @param nbColumn The number of columns of the grid
     * @return True if the index is valid and false if it isn't 
     */
	public static boolean isValid(int index, int nbColumn){
		if ( index < 1 || index > (3 * nbColumn) ) {
			System.out.println("Enter a valid index.");
			return false;
		}
		return true;
	}
	
	/*
	 * This method prints the rules of the game by reading them from a file named "rules.txt".
	 * It prints an error if it doesn't find the file.
	 */
	public static void printRules() {
		
	    // Create a new File object representing the rules.txt file.
        File fichier = new File("rules.txt");
		
		try {
			
	        // Create a new Scanner object to read from the rules.txt file.
            Scanner scanner = new Scanner(fichier);

            // Read each line of the file and print it to the console.
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                System.out.println(ligne);
            }
            scanner.close();
        }catch (FileNotFoundException e) {
        	
            // If the file is not found, print an error message to the console.
            System.out.println("The file hasn't been found !");
        }
	}
}

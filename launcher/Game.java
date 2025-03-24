package launcher;

import java.util.Scanner;
import card.*;
import utility.*;

/*
 * A class representing the game, it contains the main function.
 */
public class Game {
    public static void main(String[] args){
    	
    	/*
    	 * MENU
    	 */
    	
    	// Variable that stores the choice of the user in the menu.
    	char menu;
    	
    	// Loop to print the menu. The menu can show us the rules, begin a game or quit.
    	do {
    		
    		System.out.println("---------------------** Welcome to Skyjo ! **----------------------");
            System.out.println("");
            System.out.println("(R) Rules");
            System.out.println("(P) Play");
            System.out.println("(E) Exit");
            System.out.println("");
    		System.out.println("-------------------------------------------------------------------");

            
            // Loop to verify if the user enter a correct character.
        	do {
            	menu = Tools.choice();
            }while(menu != 'R' && menu != 'P' && menu != 'E');

        	// If the user enter an R, it prints the rules.
        	if (menu == 'R') {
        		Tools.printRules();
        	}
        	
        	// If the user enter an E, it exits the program.
        	if ( menu == 'E') {
        		System.exit(0);
        	}
        	
            System.out.println("");

    	}while(menu != 'P');

		
		

		
		
    	/*
    	 * GLOBAL PARAMETERS INITIALIZATION
		 */
    	
    	// The variable that contains Y or N if the user wants to restart the game.
        char restart;
    	// The variable that contains the number of player in the current game.
        int nbPlayers = 0;
        // The variable used to stop the game once a player reach 100 points.
        boolean theEnd = false;
        // The variable that contains the absolute winner of the game.
        int leGagneur = 1;
        // The variable used to count rounds.
        int round = 1;
    	
        // Ask the user for the number of players, between 2 and 8.
		Scanner input = new Scanner(System.in);
        System.out.println("How many players ? (between 2 and 8) :");
		do{
			while (!input.hasNextInt()) {
      			System.out.println("This is not an integer. Enter an integer : ");
      			input.next();
	    	}
	    	nbPlayers = input.nextInt();
		}while(nbPlayers < 2 || nbPlayers > 8);
    	
		// Create an array of PlayerGrid objects.
        PlayerGrid[] players = new PlayerGrid[nbPlayers];
        
        // Initialize each player's grid with their player number.
        for (int i = 0 ; i < nbPlayers ; i++){
            players[i] = new PlayerGrid(i+1);
        }
        
        
        // Loop of the entire game until the variable theEnd is true.
		do {
			
			System.out.println("\n★★★★★★★★★★★★★★★★★★★★★★★★★★★ ROUND "+ round + " ★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
			


			/*
			 * LOCAL PARAMETERS INITIALIZATION
			 */

			// Create a deck and a discard pile.
			Deck deck = new Deck();
			DiscardPile discardPile = new DiscardPile();

			// Boolean to start the last turn of the round when someone's returned all of their cards.
			boolean lastTurn = false;
			// Variable that contains the number of the player who finished his grid first.
			int leTermineur = 1;
			// The variable that contains the number of the player currently playing.
			int isPlaying;
			// The variable that contains the winner of this round.
			int winner = 1;

			// Initialize and shuffle the deck, then discard the top card.
			deck.initialization();
			deck.shuffle();
			discardPile.discard(deck.takeCard());

			// Initialize each player's grid and print it to the console.
			for (int i = 0; i < nbPlayers; i++) {
				players[i].initialization(deck);
				players[i].printGrid();
			}

			// Determine which player will start the game.
			isPlaying = Tools.whoStart(players);

			// Wait for 2 seconds.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			/*
			 * GAME PROCEDURE
			 */

			// Continue playing until a player has won the round.
			while (!players[isPlaying].haveFinished()) {

				// Start of a turn.
				
				// Print the current player's grid and the top card of the discard pile.
				System.out.println("\n/************************* PLAYER " + players[isPlaying].getPlayerNumber()
						+ " TURN *************************\\");
				players[isPlaying].printGrid();
				System.out.print("Discard pile : ");
				discardPile.getTop().printCard();
				System.out.println();

				// Allow the player to play their turn.
				players[isPlaying].playTurn(deck, discardPile);

				// Refill the deck if empty.
				deck.refillDeck(discardPile);
				
				// Determines if a player have finished their grid and starts the last turn.
				if (players[isPlaying].haveFinished() && !lastTurn) {
					lastTurn = true;
					leTermineur = players[isPlaying].getPlayerNumber();
					System.out.println("Player " + players[isPlaying].getPlayerNumber() + " has finished.\nStart of the last round.\n");
				}

				// Update the isPlaying variable to change the player currently playing.
				if (isPlaying == (nbPlayers - 1))
					isPlaying = 0;
				else
					isPlaying++;

				// Wait for 2 seconds.
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}



			/*
			 * SCORE COUNTING
			 */

			// Print the score of everyone while determining the winner of this round.
			for (int j = 0; j < nbPlayers; j++) {

				System.out.println("Player " + players[j].getPlayerNumber() + " scored " + players[j].countPoints()
						+ " points this round.\n");

				// If the player has less points than the current winner.
				if (players[j].countPoints() < players[winner-1].countPoints())
					// Winner takes the number of this player.
					winner = players[j].getPlayerNumber();
			}



			/*
			 * POINTS DISTRIBUTION
			 */

			// If the player who finished their grid first is the winner of the round.
			if (leTermineur == winner) {
				
				// All the players get their points attribute updated.
				for (int j = 0; j < nbPlayers; j++) {
					players[j].setPoints(players[j].getPoints() + players[j].countPoints());
				}
			}
			
			// Else if the player who finished their grid first isn't the winner of the round.
			else {
				System.out.println("Player " + leTermineur + " has finished first and isn't the winner. Their points are doubled for this round.\n");

				// The points of this player are doubled and the other get their normal points.
				for (int j = 0; j < nbPlayers; j++) {
					if (j == (leTermineur - 1))
						players[j].setPoints(players[j].getPoints() + players[j].countPoints() * 2);
					else
						players[j].setPoints(players[j].getPoints() + players[j].countPoints());
				}
			}

			// Wait for 2 seconds.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Loop that prints the total points of all the player.
			for (int j = 0; j < nbPlayers; j++) {

				System.out.println("Player " + players[j].getPlayerNumber() + " has now " + players[j].getPoints()
						+ " points in total.\n");
				
				// If a player has at least 100 points, it stops the game by passing the boolean theEnd at true. 
				if (players[j].getPoints() >= 100) {
					theEnd = true;
				}
			}
			
			// Add 1 to the round counter.
			round++;

		} while (!theEnd);
        
        // Loop in the players table to determine which player win the game.
		for (int j = 0; j < nbPlayers; j++) {

			if (players[j].getPoints() < players[leGagneur-1].getPoints())
				// leGagneur takes the number of this player.
				leGagneur = players[j].getPlayerNumber();
			
		}
        
    	// Print the winner and their score to the console.
    	System.out.println("Player " + leGagneur + " win with " + players[leGagneur-1].getPoints() + " points.\n");

        // Ask the user if they want to restart the game.
        System.out.println("Restart ? (Y/N) :");
        do {
        	restart = Tools.choice();
        }while(restart != 'Y' && restart != 'N');
        
        // If yes restart a game by 0.
        if (restart == 'Y') {
        	main(args);
        }
    }
}

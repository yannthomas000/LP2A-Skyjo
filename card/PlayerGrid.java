package card;

import java.util.Scanner;
import utility.*;

/**
 * The PlayerGrid class extends the CardPile class and represents a grid of
 * cards held by a player.
 */
public class PlayerGrid extends CardPile {

	private int playerNumber; // The player number.
	private int nbColumn; // The number of columns of the grid.
	private int points; // The number of total points.

	/**
	 * The default constructor sets the player number and number of columns to 0.
	 */
	public PlayerGrid() {
		this.playerNumber = 0;
		this.nbColumn = 0;
		this.points = 0;
	}

	/**
	 * The constructor sets the player number.
	 *
	 * @param playerNumber_ the player number
	 */
	public PlayerGrid(int playerNumber_) {
		this.nbColumn = 0;
		this.points = 0;
		this.playerNumber = playerNumber_;
	}

	/**
	 * This method initializes the grid by clearing the current pile, drawing 12
	 * cards from the deck pile and adding them to the grid, and setting the number
	 * of columns to 4.
	 * 
	 * @param deck The deck of cards from which to draw new cards
	 */
	public void initialization(Deck deck) {
		// Clear the current grid.
		this.pile.clear();

		// Draw 12 cards from the deck pile and add them to the grid.
		for (int i = 0; i < 12; i++) {
			this.addCard(deck.takeCard());
		}

		// Set the number of columns to 4.
		this.nbColumn = 4;
	}

	/**
	 * A getters for the points.
	 *
	 * @return the number of points of the player
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * A setters for the points.
	 */
	public void setPoints(int points_) {
		this.points = points_;
	}

	/**
	 * A getters for the player number.
	 *
	 * @return the player number
	 */
	public int getPlayerNumber() {
		return this.playerNumber;
	}

	/**
	 * Returns whether the player has finished. A player has finished if all of
	 * their cards are visible.
	 *
	 * @return true if the player has all of their cards visible, false otherwise
	 */
	public boolean haveFinished() {

		// Iterate over each card in the player's grid.
		for (Card element : this.pile) {

			// If a card is still not visible, return false.
			if (!element.isVisible()) {
				return false;
			}
		}
		// If all cards are visible, return true.
		return true;
	}

	/**
	 * Prints the player grid.
	 */
	public void printGrid() {

		// Sets the size of a card.
		int CardWidth = 15;
		System.out.println("\n------------------------** Player " + this.getPlayerNumber() + " grid **------------------------");
		int i = 0;
		
		for (Card element : this.pile) {

			// If we reach the end of a line, we print a newline character.
			if (i % this.nbColumn == 0)
				System.out.print("\n");

			// If the card is visible, print the card's string representation.
			if (element.isVisible()) {
				System.out.printf("|%-" + CardWidth + "s|", element);

			// Otherwise, print an empty card with its index.
			} else {
				System.out.printf("|%-" + CardWidth + "s|", i + 1);
			}
			i++;
		}
		System.out.println("\n\n-------------------------------------------------------------------\n");

	}

	/**
	 * Prompts the user to select a card and returns the selected card.
	 *
	 * @return the selected card
	 */
	public Card selection() {
		int index = 0;

		// Create a new Scanner object to read input from the console.
		Scanner input = new Scanner(System.in);

		System.out.println("Enter a card index :");

		// Use a do-while loop to read user input and validate it.
		do {

			// Use a while loop to ensure that the user enters an integer.
			while (!input.hasNextInt()) {
				System.out.println("This is not an integer. Enter an integer : ");
				input.next();
			}
			index = input.nextInt();

		// Continue prompting the user until a valid integer index is entered.
		} while (!Tools.isValid(index, this.nbColumn));

		// Return the card at the selected index, adjusted for 0-based indexing.
		return this.getCard(index - 1);
	}

	/**
	 * Exchanges the specified taken card with a card from the player grid, and
	 * discards the traded card to the discard pile.
	 *
	 * @param cardTaken   the card to trade
	 * @param discardPile the discard pile to discard the traded card to
	 */
	public void exchangeCard(Card cardTaken, DiscardPile discardPile) {

		Card gridCard;
		System.out.println("With which card do you want to trade it ?");

		// Call the selection method to get the selected card from the grid and store it in gridCard.
		gridCard = this.selection();

		// Make the selected card visible in order to print it.
		System.out.print("You've discarded the card : ");
		gridCard.returnVisible();
		gridCard.printCard();

		// Swap and discard the selected card with the taken card.
		this.pile.set(this.pile.indexOf(gridCard), cardTaken);
		discardPile.discard(gridCard);
	}

	/**
	 * This method is responsible for playing a turn in the game, where a player
	 * chooses a card from the deck or the discard pile. If he chose to draw a card,
	 * he exchanges it with a card from his grid or discards it. If he chose to pick
	 * a card from the discard pile, he must exchanges it with a card from his grid.
	 *
	 * @param deck        The deck of cards in the game
	 * @param discardPile The pile of discarded cards in the game
	 */
	public void playTurn(Deck deck, DiscardPile discardPile) {
		Card cardTaken;
		char choice;

		// Start a do-while loop that will run as long as the player has not made a valid choice.
		do {
			System.out.println("Take a card from the deck or the discard pile (D/P) :");

			// Call the choice() function from the Tools class to get the player's choice.
			choice = Tools.choice();

			// Continue running the loop as long as the player's choice is not 'D' or 'P'.
		} while ((choice != 'D' && choice != 'P'));

		// If the player chose to take a card from the deck.
		if (choice == 'D') {

			// Take a card from the deck and store it in cardTaken.
			cardTaken = deck.takeCard();
			System.out.print("You drew the card : ");

			// Make it visible in order to print it.
			cardTaken.returnVisible();
			cardTaken.printCard();
			System.out.println();

			// Asking if the player wants to keep the card.
			do {
				System.out.println("Do you want to keep it ? (Y/N) :");
				choice = Tools.choice();
			} while ((choice != 'Y' && choice != 'N'));

			// If the player chose to keep the card.
			if (choice == 'Y') {
				// Call the exchangeCard() method on this object.
				this.exchangeCard(cardTaken, discardPile);

			} else {
				// Or discard the card.
				discardPile.discard(cardTaken);
			}

		// If the player chose to take a card from the discard pile.
		} else {

			// Take the top card of the discard pile and store it in cardTaken.
			cardTaken = discardPile.takeCard();
			System.out.print("You've chosen the card : ");

			// Print it.
			cardTaken.printCard();
			System.out.println();

			// Call the exchangeCard() method on this object.
			this.exchangeCard(cardTaken, discardPile);
		}

		// Print the updated grid.
		this.printGrid();

		// Call the deleteColumn() method on this object at the end of the turn.
		this.deleteColumn(discardPile);
	}

	/**
	 * Verifies if the cards at positions a, b, and c form a column with the same
	 * value and all cards visible. If the condition is true, discards the column
	 * and removes the cards from the grid, and decreases the number of columns by
	 * 1.
	 * 
	 * @param a           the position of the first card in the column to verify
	 * @param b           the position of the second card in the column to verify
	 * @param c           the position of the third card in the column to verify
	 * @param discardPile the discard pile where the cards will be moved
	 */
	public boolean verifyColumn(int a, int b, int c, DiscardPile discardPile) {

		/* This line of code checks if the cards at indices 'a', 'b', and 'c' in the
		   current column have the same value and are visible. */
		if (this.getCard(a).getValue() == this.getCard(b).getValue()
				&& this.getCard(b).getValue() == this.getCard(c).getValue() && this.getCard(a).isVisible()
				&& this.getCard(b).isVisible() && this.getCard(c).isVisible()) {

			// If yes, it will discard the three cards.
			discardPile.discard(this.getCard(a));
			discardPile.discard(this.getCard(b));
			discardPile.discard(this.getCard(c));

			// And remove them from the Grid.

			// Remove the card with index a.
			this.pile.remove(a);
			// Remove the card with index b-1 because we removed a so the index moved by one.
			this.pile.remove(b - 1);
			// Remove the card with index c-2 because we removed a and b so the index moved by two.
			this.pile.remove(c - 2);

			// Reduce the attribute nbColumn by one to print the grid correctly.
			this.nbColumn -= 1;

			System.out.println("Well done ! You've aligned a column, this column is now discarded.\n");

			// Return true if a column has been removed succesfully.
			return true;
		}
		// Return false otherwise.
		return false;
	}

	/**
	 * Deletes columns of cards that are formed by the pile. For each possible
	 * column configuration, calls the verifyColumn function with the appropriate
	 * positions of cards. If a column is found, it stops breaks.
	 *
	 * @param discardPile the discard pile where the cards will be moved if a column
	 *                    is found
	 */
	public void deleteColumn(DiscardPile discardPile) {

		// switch statement based on the number of columns present.
		switch (this.nbColumn) {

		/* Call the verifyColumn() method for each column with the index of the cards
		   when there are 4 columns. */
		case 4:
			// Call the verifyColumn() method for the first column.
			if (this.verifyColumn(0, 4, 8, discardPile))
				break;
			// Call the verifyColumn() method for the second column.
			if (this.verifyColumn(1, 5, 9, discardPile))
				break;
			// Call the verifyColumn() method for the third column.
			if (this.verifyColumn(2, 6, 10, discardPile))
				break;
			// Call the verifyColumn() method for the fourth column.
			if (this.verifyColumn(3, 7, 11, discardPile))
				break;
			break;

		/* Call the verifyColumn() method for each column with the index of the cards
		   when there are 3 columns. */
		case 3:
			// Call the verifyColumn() method for the first column.
			if (this.verifyColumn(0, 3, 6, discardPile))
				break;
			// Call the verifyColumn() method for the second column.
			if (this.verifyColumn(1, 4, 7, discardPile))
				break;
			// Call the verifyColumn() method for the third column.
			if (this.verifyColumn(2, 5, 8, discardPile))
				break;
			break;

		/* Call the verifyColumn() method for each column with the index of the cards
		   when there are 2 columns. */
		case 2:
			// Call the verifyColumn() method for the first column.
			if (this.verifyColumn(0, 2, 4, discardPile))
				break;
			// Call the verifyColumn() method for the second column.
			if (this.verifyColumn(1, 3, 5, discardPile))
				break;
			break;

		/* Call the verifyColumn() method for each column with the index of the cards
		   when there is only 1 columns. */
		case 1:
			// Call the verifyColumn() method for the first column.
			this.verifyColumn(0, 1, 2, discardPile);
			break;
		}
	}

	/**
	 * Calculates the total points of the player grid by adding up the values of all
	 * cards in it, no matter if it's visible or not.
	 *
	 * @return an integer representing the total points of the grid
	 */
	public int countPoints() {

		int points = 0;

		// Loop through each card in the pile.
		for (Card element : this.pile) {

			// Add the value of the card to the points.
			points += element.getValue();
		}

		return points;
	}
}
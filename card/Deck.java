package card;

/*
 * This class represents a deck which is a pile of Cards created from the CardPile class
 * thanks to inheritance.
 */
public class Deck extends CardPile {

	/*
	 * This function initialize the deck pile with 150 personalized cards.
	 */
	public void initialization() {

		// We have 5 cards with a value of -2.
		for (int i = 0; i < 5; i++) {
			this.addCard(new Card("Girardot", -2, false));
		}

		// We have 15 cards with a value of 0.
		for (int i = 0; i < 15; i++) {
			this.addCard(new Card("Mauffrey", 0, false));
		}

		// We have 10 cards with a value of -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12.
		for (int i = 0; i < 10; i++) {
			this.addCard(new Card("Godey", -1, false));
			this.addCard(new Card("Gaud", 1, false));
			this.addCard(new Card("Flesh", 2, false));
			this.addCard(new Card("Lassabe", 3, false));
			this.addCard(new Card("Koukam", 4, false));
			this.addCard(new Card("El Kedim", 5, false));
			this.addCard(new Card("Triponey", 6, false));
			this.addCard(new Card("Zullo", 7, false));
			this.addCard(new Card("Iovleff", 8, false));
			this.addCard(new Card("Lannuzel", 9, false));
			this.addCard(new Card("Meyer", 10, false));
			this.addCard(new Card("Turbergue", 11, false));
			this.addCard(new Card("Lapostolle", 12, false));
		}
	}

	/**
	 * Refills the deck with cards from the discard pile if it's empty.
	 * 
	 * @param discardPile the discard pile to refill the deck from
	 */
	public void refillDeck(DiscardPile discardPile) {
		// Check if the deck is empty.
		if (this.pile.size() == 0) {
			// If the deck is empty, create a temporary discard pile.
			DiscardPile temp = new DiscardPile();

			// Add the top card of the discard pile to the temporary pile.
			temp.addCard(discardPile.takeCard());

			// Loop through all the remaining cards in the discard pile.
			for (Card element : discardPile.pile) {
				// Hide all the cards.
				element.returnHide();
			}

			// Shuffle the discard pile after hiding the cards.
			discardPile.shuffle();

			// Add all the cards in the discard pile to the deck.
			this.pile.addAll(discardPile.pile);

			// Clear the discard pile.
			discardPile.pile.clear();

			// Add the top card (which was previously removed) back to the empty discard pile.
			discardPile.pile.addAll(temp.pile);
		}
	}
}

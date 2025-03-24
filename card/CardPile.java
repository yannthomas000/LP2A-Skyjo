package card;

import java.util.ArrayList;
import java.util.Random;

/**
 * The CardPile class represents a pile of cards that can be manipulated.
 */
public class CardPile {
	
    protected ArrayList<Card> pile; // An ArrayList that stores the cards in the pile.
    
    /**
     * Constructor for the CardPile class that creates a new empty pile.
     */
    public CardPile() {
    	pile = new ArrayList<Card>();
    }
    
    /**
     * A getters for the top card of the pile.
     *
     * @return The top card of the pile
     */
    public Card getTop(){
        return this.pile.get(this.pile.size() - 1);
    }

    /**
     * A getters for the card at a specified index.
     *
     * @param index The index of the card to retrieve
     * @return The card at the specified index
     */
    public Card getCard(int index){
        return this.pile.get(index);
    }

    
    /**
     * Removes and returns the top card on the pile.
     *
     * @return The top card on the pile
     */
    public Card takeCard(){
        Card taken = this.getTop();
        if (this.pile.size() > 0)
            this.pile.remove(this.pile.size() - 1);
        return taken;
    }
        
    /**
     * Adds a card to the pile.
     *
     * @param card The card to add to the pile
     */
    public void addCard(Card card) {
    	this.pile.add(card);
    }
    
    /**
     * Shuffles the cards in the pile .
     */
    public void shuffle() {
    	
    	// Get the size of the pile.
    	int size = this.pile.size();
    	
    	// Create a new random number generator.
        Random rand = new Random();
        
        // Loop over each card in the pile.
        for (int i = 0; i < size; i++) {
            // Generate a random index within the range of cards left in the pile.
            int r = i + rand.nextInt(size - i);
            
            // Swap the card at index i with the card at index r.
            Card temp = this.pile.get(i);
            this.pile.set(i, this.pile.get(r));
            this.pile.set(r, temp);
        }
    }
}
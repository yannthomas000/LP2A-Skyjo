package card;

/*
 * This class represents a pile of discarded Cards created from the CardPile class
 * thanks to inheritance.
 */
public class DiscardPile extends CardPile{
    
    /**
     * This method makes the card visible then adds it to the discard pile.
     */
    public void discard(Card card){
        card.returnVisible();
        this.pile.add(card);
    }
}
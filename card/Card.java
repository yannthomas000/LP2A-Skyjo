package card;

/**
 * This class represents a playing card.
 */
public class Card {
	
	private String name; // The name of the card. (ex: "Godey", "Lassabe")
    private int value; // The value of the card. (ex: -2, 8)
    private boolean visible; // Whether or not the card is visible to the player.
    
    /**
     * Constructor for an empty card.
     */
    public Card() {
    	this.name = "empty";
		this.value = 0;
		this.visible = false;
    }
    
    /**
     * Constructor for a card with a given name, value, and visibility.
     *
     * @param name_ the name of the card
     * @param value_ the value of the card
     * @param visible_ whether or not the card is visible
     */
    public Card(String name_, int value_, boolean visible_){
		this.name = name_;
		this.value = value_;
		this.visible = visible_;
	}
    
    /**
     * Returns whether or not the card is visible to the player.
     *
     * @return true if the card is visible, false otherwise
     */
    public boolean isVisible() {
    	return this.visible;
    }

    /**
	 * A getters for the value of the card.
	 *
	 * @return the value of the card
	 */
	public int getValue(){
		return this.value;
	}

	/**
	 * A getters for the name of the card.
	 *
	 * @return the name of the card
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Makes the card visible to the player.
	 */
	public void returnVisible() {
    	this.visible = true;
	}
    
	/**
     * Hides the card from the player.
     */
    public void returnHide() {
    	this.visible = false;
    }

    /**
	 * Returns a string representation of the card.
	 *
	 * @return a string in the format "|name value|"
	 */
	public String toString(){
		return this.name+" "+"("+this.value+")";
	}
    
	/**
     * Prints the card to the console.
     */
    public void printCard() {
    	System.out.println(this);
    }
}
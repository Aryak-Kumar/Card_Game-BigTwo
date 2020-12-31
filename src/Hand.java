/**
 * 
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards. 
 * It has a private instance variable for storing the player who plays this hand. 
 * It also has methods for getting the player of this hand, checking if it is a valid hand, getting the type of this hand, getting the top card of this hand, and checking if it beats a specified hand.
 * 
 * @author Aryak Kumar
 *
 */
public abstract class Hand extends CardList {
	
	private CardGamePlayer player; //the player who plays this hand.
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the hand
	 * 
	 */
	public abstract boolean isValid();

	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand
	 * 
	 */	
	public abstract String getType();

	
	/**
	 * 
	 * a constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param cards, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		for (int i = 0; i < cards.size(); i++)//Loop to traverse through the cards
			addCard(cards.getCard(i)); //Add the cards in the hand 
		this.player = player; //create the specified player
	}
	
	
	/**
	 * 
	 * a method for retrieving the player of this hand.
	 * 
	 * @return player, a CardGamePlayer type variable containing the player of this hand
	 * 
	 */
	public CardGamePlayer getPlayer() {
		return player; //We return the player as it is a private instance variable
	}

	
	/**
	 * 
	 * a method for retrieving the top card of this hand.
	 * 
	 * @return cd, a Card variable storing the top card of this hand
	 * 
	 */
	public Card getTopCard() {
		if (size()<=0)
			return null;
		
		else{
		Card cd = getCard(0);//To store the top card, starting with the first 
		for(int i=0;i<size();i++){//Loop to go through all cards
			Card current = getCard(i);
			if(cd.compareTo(current)==-1) //Compare cd with all the other cards to see which is on top
				cd=current; //Update cd with the top card
		return cd;
		}
		
		}
		return null;
	}

	/**
	 * 
	 * a method for checking if this hand beats a specified hand.
	 * 
	 * @param hand, a Hand type variable to store the hand
	 * 
	 * @return bool, a boolean value which tells if this hand has beaten the specified hand
	 * 
	 */
	public boolean beats(Hand hand) {
		Boolean bool; //variable to declare if this hand won
		if(hand==null) //if the hand is empty, we set bool as false
			bool=false;
		else {
		if(getType()==hand.getType()) { //otherwise we check if the hand type are same or not
			if(hand.getTopCard()==null) //if the top card in hand is null that means that this hand won
				bool = true;
			else if(getTopCard()==null)// if the top card in this hand is null that means that this hand lost
				bool = false;
			else if(getTopCard().compareTo(hand.getTopCard())==1) // if this hand has a better top card that the hand specified, this hand wins
				bool = true;
			else
				bool = false;
		}
		else
			bool = false;
	}
	return bool;
	}
}

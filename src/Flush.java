/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of flush in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Flush extends Hand {

	/**
	 * 
	 * a constructor for building a hand of flush with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Flush(CardGamePlayer player, CardList card) {
		super(player, card); }// To call the constructor from Hand class
	


	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the straight hand
	 * 
	 */
	public boolean isValid() {
		boolean flag = true;
		if(this.size()!=5)//if the size is not equal to 5 then this hand is not a flush
			flag = false;
		else { //otherwise
			for (int i=0;i<4;i++){
				if(this.getCard(i).suit!=this.getCard(i+1).suit) //we check for each card and if there is a suit that doesn't match
					flag = false; //we return false
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * a method for retrieving the top card of this hand.
	 * 
	 * @return cd, a Card variable storing the top card of this hand
	 * 
	 */
	public Card getTopCard() {
		sort();//sorting the hand
		return (this.getCard(4));
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
		if(hand==null)//if the hand is null, the hand does not win
			return false;
		else {//in other cases
				String hand_played = hand.getType();//we get the string of the previous hand to check
				
				if(hand_played=="FullHouse" || hand_played=="Quad" || hand_played=="StraightFlush")//if these are the hand played previously, this hand looses
					return false;
				
				else if(hand_played=="Straight")//else this hand wins in case of straight hand
					return true;
				else {
					if(this.getTopCard().getSuit()>hand.getTopCard().getSuit())
						return true;
					else if(this.getTopCard().compareTo(hand.getTopCard())==1) //if the hand is equal to flush, we check if the top card is better ranked
						return true;
					else 
						return false;
				}
		}
	}

	
	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand as straight
	 * 
	 */	
	public String getType() {
		return "Flush";}
}


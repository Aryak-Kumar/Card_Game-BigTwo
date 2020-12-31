/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of quad in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Quad extends Hand{
	
	int notquad;
	
	/**
	 * 
	 * a constructor for building a hand of quad with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Quad(CardGamePlayer player, CardList card) {
		super(player, card);//calling the super constructor
	}
	
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the quad hand
	 * 
	 */
	public boolean isValid() {
		sort();//sorting the cards
		if(this.size()!=5)//if the size is not equal to 5, we return false
			return false;
		else{//otherwise
			if((this.getCard(1).rank==this.getCard(2).rank) && (this.getCard(1).rank==this.getCard(3).rank) && (this.getCard(1).rank==this.getCard(4).rank)) {
				notquad=0;
				return true;
				}
			else if((this.getCard(0).rank==this.getCard(1).rank) && (this.getCard(0).rank==this.getCard(2).rank) && (this.getCard(0).rank==this.getCard(3).rank)) {
				notquad =4;
				return true;
				}
			else
				return false;
			}
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
		if(notquad==0)
			return this.getCard(4);
		else if(notquad==4)
			return this.getCard(3);
		else
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
	public boolean beats(Hand hand)
	{
		if(hand==null)//if the hand is null, the hand does not win
			return false;
		else {//in other cases
				String hand_played = hand.getType();//we get the string of the previous hand to check
				
				if(hand_played=="StraightFlush")//if these are the hand played previously, this hand looses
					return false;
				
				else if(hand_played=="Flush" || hand_played=="Straight" || hand_played=="FullHouse")//else this hand wins
					return true;
				else {//if the hands are both fullHouse
					if(this.getTopCard().compareTo(hand.getTopCard())==1)//then we check who has a higher top card
						return true;//if this hand wins 
					else 
						return false;//if this hand looses
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
		return "Quad";}
}
	
	
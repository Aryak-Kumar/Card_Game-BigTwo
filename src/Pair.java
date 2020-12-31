/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of pair in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Pair extends Hand{
	

	/**
	 * 
	 * a constructor for building a hand of pair with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Pair(CardGamePlayer player,CardList card) {
		super(player,card);}// To call the constructor from Hand class
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the single hand
	 * 
	 */
	public boolean isValid() {
		if (this.size()!=2)//if the size is not equal to 2 then it is not a pair
			return false;
		else {
		if (this.getCard(1).rank == this.getCard(0).rank) //if the size is 2 then we check if both the cards have the same rank
			return true;
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
		return (this.getCard(1));
	}
	
	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand as single
	 * 
	 */	
	public String getType() {
		return "Pair";}
}

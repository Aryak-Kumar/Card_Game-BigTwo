/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of single in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Single extends Hand{
	
	/**
	 * 
	 * a constructor for building a hand of single with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Single(CardGamePlayer player,CardList card) {
		super(player,card);}// To call the constructor from Hand class
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the single hand
	 * 
	 */
	public boolean isValid() {
		if(this.size()!=1) //if it is not a single, return false
			return false;
		else //else return true
			return true;}	
	
	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand as single
	 * 
	 */	
	public String getType() {
		return "Single";}
}

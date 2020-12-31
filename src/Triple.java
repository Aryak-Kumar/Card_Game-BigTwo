/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of triple in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Triple extends Hand {
	
	/**
	 * 
	 * a constructor for building a hand of triple with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Triple(CardGamePlayer player,CardList card) {
		super(player,card);}// To call the constructor from Hand class
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the triple hand
	 * 
	 */
	public boolean isValid() {
		if (this.size()!=3)//if the size is not 3 then it is not a triplet
			return false;

		else {
			for(int i=0;i<2;i++) {
				if (this.getCard(i).rank != this.getCard(i+1).rank)
					return false;
			}
			return true;
	}
	}
		
	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand as triple
	 * 
	 */	
	public String getType() {

		return "Triple";
	}
}

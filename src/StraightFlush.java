import java.util.Arrays;

/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of straight flush in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class StraightFlush extends Hand {

	/**
	 * 
	 * a constructor for building a hand of straight flush with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public StraightFlush(CardGamePlayer player,CardList card) {
		super(player,card); //calling the super constructor
	}
	
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the straight flush hand
	 * 
	 */
	public boolean isValid() {
		int fl=1;
		if(size()!=5)
			return false;
		else {
			for (int i=0;i<4;i++){
				if(this.getCard(i).suit!=this.getCard(i+1).suit)
					fl=0;
			}
			if(fl==1) {
				int a[]=new int[5]; //we initialize an array of size 5 to store the sorted ranks of the cards
				for(int i=0;i<5;i++)
					a[i]=this.getCard(i).rank;
				for (int i=0;i<5;i++) {
					if (this.getCard(i).rank==0)
						a[i]=13;
					if (this.getCard(i).rank==1)
						a[i]=14;
					}
				
				Arrays.sort(a);
				
				int f=-1;
				for(int i=0;i<4;i++)
					if(a[i+1]-a[i]!=1)
						f=0;
				
				if(f==-1)
					return true;
				else 
					return false;
				
			}
			else
				return false;
				
		}
		
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
		if(hand==null){ //If the hand is null this hand looses
			return false;
		}
		else {
				if(hand.getType()!="StraightFlush")
					return true;
				else {
				if(this.getCard(4).compareTo(hand.getCard(4))==1)// To check if both are StraightFlush who beats the other
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
	 * @return String value specifying the type of hand as straight flush
	 * 
	 */	
	public String getType() {

		return "StraightFlush";
	}
	
}

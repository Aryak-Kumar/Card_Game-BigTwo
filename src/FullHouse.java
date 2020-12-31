import java.util.Arrays;
/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of full house in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class FullHouse extends Hand{
	
	
	int side;//the rank which comes under the triplet 
	
	/**
	 * 
	 * a constructor for building a hand of full house with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public FullHouse(CardGamePlayer player, CardList card) {
		super(player, card);}// To call the constructor from Hand class
	
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the full house hand
	 * 
	 */
	public boolean isValid() {
		if(this.size()!=5)//if the size is not equal to 5, we return false
		return false;
	
		else {//otherwise
			int all_ranks[]=new int[5];//we create an array of size 5 to store the ranks of all the cards
				for(int i=0;i<5;i++)
					all_ranks[i]=this.getCard(i).getRank(); //we store the ranks of all the cards in the new array
				Arrays.sort(all_ranks); //we sort the array
				int rank1=this.getCard(0).getRank();//the first rank would be one rank
				int rank2=this.getCard(4).getRank();//while the last would be another
				
				if ( all_ranks[1] != rank1 || all_ranks[3]!=rank2)//the 2nd and 4th rank do not match the 1st or 5th rank,
					return false;//we return false
				
				if (all_ranks[1]==all_ranks[2] || all_ranks[3]==all_ranks[2]){//if the 2nd rank matches one of the first or last, 
					if(all_ranks[1]==all_ranks[2])
						side=0;
					if(all_ranks[3]==all_ranks[2])
						side=1;
					return true;
				}
				else 
					return false;//else it is false
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
		this.sort();
		if(side==0)
			return(this.getCard(2));
		else if(side==1)
			return(this.getCard(4));
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
	public boolean beats(Hand hand) {
		if(hand==null)//if the hand is null, the hand does not win
			return false;
		else {//in other cases
				String hand_played = hand.getType();//we get the string of the previous hand to check
				
				if( hand_played=="Quad" || hand_played=="StraightFlush")//if these are the hand played previously, this hand looses
					return false;
				
				else if(hand_played=="Flush" || hand_played=="Straight")//else this hand wins
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
		return "FullHouse";}	

}

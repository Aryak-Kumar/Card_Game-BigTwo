import java.util.Arrays;

/**
 * 
 * This class is a subclass of the Hand class, and is used to model a hand of straight in a Big Two card game.
 * 
 * @author Aryak Kumar
 *
 */
public class Straight extends Hand{
	
	/**
	 * 
	 * a constructor for building a hand of single with the specified player and list of cards.
	 * 
	 * @param player, CardGamePlayer type variable to store the specified player
	 * 
	 * @param card, CardList type variable to store the list of cards of the specified player
	 * 
	 */
	public Straight(CardGamePlayer player,CardList card) {
		super(player,card);}// To call the constructor from Hand class
	
	
	/**
	 * 
	 *  a method for checking if this is a valid hand.
	 *  
	 * @return Boolean value which gives the validity of the straight hand
	 * 
	 */
	public boolean isValid() {
		
		if(this.size()!=5)//if the number of cards is not 5, we return false
			return false;
		
		else{ //else we check for the validity
		int a[]=new int[5]; //we initialize an array of size 5 to store the sorted ranks of the cards
		for(int i=0;i<5;i++)
			a[i]=this.getCard(i).getRank(); 
		for (int i=0;i<5;i++) {
		if (this.getCard(i).rank==0)
			a[i]=13;
		if (this.getCard(i).rank==1)
			a[i]=14;
		}
		Arrays.sort(a);//we sort the ranks of all the 5 cards
		
		int f=-1;
		for(int i=0;i<4;i++)
			if(a[i+1]-a[i]!=1) //if the difference between two cards is not 1, then we lower the flag
				f=0;
		
		if(f==-1)
			return true;//if the flag is raised, we return true 
		else 
			return false;//else we return false
					
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
		return (this.getCard(4));
	}
	
	/**
	 * 
	 * a method for returning a string specifying the type of this hand.
	 * 
	 * @return String value specifying the type of hand as straight
	 * 
	 */	
	public String getType() {
		return "Straight";}
}


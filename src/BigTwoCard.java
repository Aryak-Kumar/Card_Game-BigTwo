/**
 * 
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game.
 * It will override the compareTo() method it inherits from the Card class to reflect the ordering of cards used in a Big Two card game. 
 * 
 * @author Aryak Kumar
 *
 */
public class BigTwoCard extends Card {
	
	/**
	 * 
	 * a constructor for building a card with the specified suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and 12.
	 * 
	 * @param suit, Integer type variable to store the suit of the card
	 * 
	 * @param rank, Integer type variable to store the rank of the card
	 * 
	 */
	BigTwoCard(int suit, int rank) {
		super(suit, rank);}
	
	/**
	 * 
	 * a method for comparing the order of this card with the specified card. 
	 * Returns a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card.
	 * 
	 * @param card A Card type variable to compare the order
	 * 
	 * @return Integer value which is a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card.
	 * 
	 */
	public int compareTo(Card card) {
		int r1 = this.rank; //to store the rank of this card
		int r2 = card.rank; //to store the rank of the card given
		int s1 = this.suit; //to store the suit of this card
		int s2 = card.suit; //to store the suit of the card given
		
		if(r1 == 0) //if the rank of the card is A, 
			r1 = 13;// then we place it after K
		if(r2 == 0)//if the rank of the card is A, 
			r2 = 13;// then we place it after K
		if(r1 == 1)//if the rank of the card is 2,
			r1 = 14;// then we place it after K and A
		if(r2 == 1)//if the rank of the card is 2,
			r2 = 14;// then we place it after K and A
		
		if (r1 > r2) //if the rank is bigger, the first card wins
			return 1;
		
		else if (r1 == r2){ //if the ranks are same
			if (s1 == s2) //and the suits are same, they are the same card
				return 0;
			else if (s1 > s2) //if the suit of the card is bigger, it is a bigger card
				return 1;
			else
				return -1;}//else the other card is a better card
		else 
			return -1;
	}
}

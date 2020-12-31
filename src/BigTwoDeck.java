/**
 * The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game. 
 * It will override the initialize() method it inherits from the Deck class to create a deck of Big Two cards. 
 * 
 * @author Aryak Kumar
 *
 */
public class BigTwoDeck extends Deck {

/**
 * 
 * a method for initializing a deck of Big Two cards. It should remove all cards from the deck, create 52 Big Two cards and add them to the deck.
 * 
 */
	public void initialize() {
		removeAllCards(); //We remove the cards from the deck.
		
		for(int i=0;i<4;i++)//Loop for the suits 
			for(int j=0;j<13;j++)//Loop for the ranks
				addCard(new BigTwoCard(i,j));//We create 52 Big Two Cards and add them to the deck
			
		}
}



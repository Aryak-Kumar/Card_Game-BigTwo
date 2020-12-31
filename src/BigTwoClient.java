import java.io.*;
import java.util.ArrayList;
import java.net.Socket;
import javax.swing.*;


/**
 * 
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. 
 * It is used to model a Big Two card game that supports 4 players playing over the internet.
 * 
 * @author Aryak Kumar
 *
 */
public class BigTwoClient implements CardGame, NetworkGame {
	private int numOfPlayers; //an integer specifying the number of players.
	private Deck deck; //a deck of cards.
	private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>(); //a list of players.
	private ArrayList<Hand> handsOnTable = new ArrayList<Hand>(); //a list of hands played on the table.
	private int playerID; //an integer specifying the playerID (i.e., index) of the local player.
	private String playerName; //a string specifying the name of the local player.
	private String serverIP; //a string specifying the IP address of the game server.
	private int serverPort; //an integer specifying the TCP port of the game server.
	private Socket sock; //a socket connection to the game server.
	private ObjectOutputStream oos; //an ObjectOutputStream for sending messages to the server.
	private int currentIdx; //an integer specifying the index of the player for the current turn.
	private BigTwoTable table; //a Big Two table which builds the GUI for the game and handles all user actions.
	public Card diamond_3 = new Card(0,2); //the card 3 of Diamonds
	
	
	/**
	 * 
	 * a constructor for creating a Big Two client. We should :
	 * (i) create 4 players and add them to the list of players;
	 * (ii) create a Big Two table which builds the GUI for the game and handles user actions;
	 * (iii) make a connection to the game server by calling the makeConnection() method from the NetworkGame interface.
	 * 
	 */
	public BigTwoClient(){	
		this.playerID=-1;
		for (int i=1;i<5;i++) 
			this.playerList.add(new CardGamePlayer());
		playerName=JOptionPane.showInputDialog(null,"Enter Player's Name: ", "Player Name!", JOptionPane.PLAIN_MESSAGE);
		if(playerName.isEmpty()) 
			System.exit(0);
		else {
			this.table = new BigTwoTable(this);
			makeConnection();
			table.repaint();
		}
	}
	
	/**
	 * 
	 * a method for getting the playerID (i.e., index) of the local player.
	 * 
	 * @return i, a variable of type integer storing the playerID
	 * 
	 */
	public int getPlayerID() {
		int i = this.playerID;
		return (i);}
	
	/**
	 * 
	 * A method for getting the number of players.
	 * 
	 * @return num, an integer type variable containing the number of players
	 * 
	 */
	public int getNumOfPlayers() {
		int num = playerList.size();
		return (num);
	}
	
	/**
	 * 
	 * a method for getting the name of the local player.
	 * 
	 * @return s, a string specifying the name of the local player.
	 * 
	 */
	public String getPlayerName() {
		String s = playerName;
		return (s);}
	
	/**
	 * 
	 * a method for setting the playerID (i.e., index) of the local player.
	 * 
	 * @param playerID, an integer specifying the playerID (i.e., index) of the local player to be set.
	 * 
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;}
	
	/**
	 * 
	 * a method for getting the IP address of the game server.
	 * 
	 * @return s, a string specifying the IP address of the game server.
	 */
	public String getServerIP() {
		String s = serverIP;
		return (s);}
	
	/**
	 * 
	 * a method for setting the IP address of the game server.
	 * 
	 * @param serverIP, a string specifying the IP address to be set of the game server.
	 * 
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;}
	
	/**
	 * 
	 * a method for setting the name of the local player.
	 * 
	 * @param playerName a string specifying the name to be set of the local player.
	 * 
	 */
	public void setPlayerName(String playerName) {
		CardGamePlayer p = playerList.get(playerID);
		p.setName(playerName);}
	
	/**
	 * 
	 * a method for getting the TCP port of the game server.
	 * 
	 * @return i, an integer specifying the TCP port of the game server.
	 * 
	 */
	public int getServerPort() {
		int i = serverPort;
		return (i);}
	
	/**
	 * 
	 * a method for retrieving the deck of cards being used.
	 * 
	 * @return dk, a Deck type variable storing the deck of cards being used.
	 * 
	 */
	public Deck getDeck(){
		Deck dk = this.deck;
		return (dk);}
	
	/**
	 * 
	 * a method for setting the TCP port of the game server.
	 * 
	 * @param serverPort, an integer specifying the TCP port to be set of the game server.
	 * 
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;}
	
	/**
	 * 
	 * a method for retrieving the list of players.
	 * 
	 * @return plist, an ArrayList storing the list of players.
	 * 
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		ArrayList<CardGamePlayer> plist = this.playerList;
		return (plist);}
	
	
	/**
	 * 
	 * a method for retrieving the list of hands played on the table.
	 * 
	 * @return hont, an ArrayList storing the list of hands played on the table.
	 *
	 */
	public ArrayList<Hand> getHandsOnTable(){
		ArrayList<Hand> hont = this.handsOnTable;
		return (hont);}
	
	
	/**
	 * 
	 * a method for retrieving the index of the current player.
	 * 
	 * @return current, an integer variable storing the index of current player.
	 *
	 */
	public int getCurrentIdx(){
		int current = this.currentIdx;
		return (current);}
	
	/**
	 * 
	 * a method for starting/restarting the game with a given shuffled deck of cards.
	 * (i) remove all the cards from the players as well as from the table; 
	 * (ii) distribute the cards to the players; 
	 * (iii) identify the player who holds the 3 of Diamonds;
	 * (iv) set both the currentIdx of the BigTwo instance and the activePlayer of the BigTwoTable instance to the index of the player who holds the 3 of Diamonds.
	 * 
	 * @param deck, a Deck type variable for storing the shuffled deck
	 * 
	 */
	public void start(Deck deck) {		
		for (int i=0;i<4;i++) {
			CardGamePlayer player = playerList.get(i); //We extract the player
			player.removeAllCards(); //And remove all the cards
		}
		handsOnTable.clear(); //And we also remove all the cards on the table
		for (int i=0;i<4;i++) { //We traverse through all the players
			CardGamePlayer player = playerList.get(i); //We set the players
			for (int j=0;j<13;j++) //and distribute 13 cards
				player.addCard(deck.getCard((i*13)+j)); //We add cards to the deck
		}
		table.reset();//to reset the entire table
		for(int i = 0; i < 4;i++){
			CardGamePlayer player = playerList.get(i); //We set the players
			CardList cinh = player.getCardsInHand();
			player.sortCardsInHand();//To organize the cards that each player has
			if(cinh.contains(diamond_3)){// We start with the player who has the Three of Diamonds
				table.setActivePlayer(i);//We set the active player
				currentIdx = i;//We set the current player as i, whoever has the diamond of 3
				CardGamePlayer p = playerList.get(i);
				String st = p.getName();
				table.printMsg(st+ "'s turn: \n"); //We print on the console that it is the player's turn
				}
			table.enable(); //To enable the entire table
			table.repaint(); //We repaint the table
			}
		}
	
	
	/**
	 * 
	 * a method for making a move by a player with the specified playerID using the cards specified by the list of indices.
	 * This method should be called from the BigTwoTable when the local player presses either the “Play” or “Pass” button.
	 * 
	 * @param playerID, an integer specifying the playerID (i.e., index) of the local player.
	 * @param cardIdx, a regular array of integers specifying the indices of the selected cards.
	 * 
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		sendMessage(new CardGameMessage(6,playerID,cardIdx));}
	
	
	/**
	 * 
	 * a method for checking a move made by a player.
	 *  This method should be called from the parseMessage() method from the NetworkGame interface when a message of the type MOVE is received from the game server. 
	 * 
	 * @param playerID, an integer specifying the playerID (i.e., index) of the local player.
	 * @param cardIdx, a regular array of integers specifying the indices of the selected cards.
	 * 
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		CardGamePlayer player = playerList.get(playerID);
		CardList cards = new CardList(); //We make a new card list to store all the cards played by the player
		CardList cinh = new CardList(); //We make a new card list to store all the cards in the hand of the player
		cards = player.play(cardIdx); //to store all the cards played by the player
		cinh = player.getCardsInHand(); //to store all the cards in the hand of the player
		
		if(cinh.contains(diamond_3) == true) { //if the player has the diamond 3 then
			if(cardIdx != null) { //the cards played must not be null	
				Hand hand = composeHand(player, cards); //We compose the hand and set in the variable hand to store and compare for future 
				if(hand == null)
					table.printMsg(cards + " <== Not a legal move!!!\n");
				else if(hand.contains(diamond_3)) { //if the hand contains the diamond 3
					handsOnTable.add(hand); //We add the hand to the table
					hand.sort(); //We sort all the cards in the hand
					table.printMsg("{" + hand.getType() +   "} " + cards + "\n");
					for(int i = 0; i < hand.size(); i++) 
						cinh.removeCard(hand.getCard(i));//We remove all the cards played from the hand
					currentIdx = (currentIdx+1)% 4; //Since there are only 4 players, we update the id and the current ID can not exceed 4
					table.resetSelected();//This is to reset all the cards selected by the player 
					table.repaint(); //We then repaint the table for the next move
					table.setActivePlayer(currentIdx);//We set the new player as the current ID
					table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n"); //We print the player whose move it is next 
				}
				else //if the cards played are not valid
					table.printMsg(cards + " <== Not a legal move!!!\n"); //The move made by the player is illegal and not valid
			}
			else //if the cards played are null i.e. passed
				table.printMsg("{pass} <== Not a legal move!!!  \n"); //if the player tries to pass, it is illegal move
		}
		else {
			Hand ex_hand = handsOnTable.get(handsOnTable.size() - 1); //to find the previous player who made the move and store his hand 
			if(cardIdx == null) { //if he has not played any cards i.e. he passed
				if(playerList.get(currentIdx) == ex_hand.getPlayer()) //If this is the same player as the last played player
					table.printMsg("{pass} <== Not a legal move!!!  \n"); //Pass is not a legal move
				
				else { //else, if it is a player different to the current player
					currentIdx = (currentIdx+1)% 4; //Since there are only 4 players, we update the id and the current ID can not exceed 4
					table.resetSelected();//This is to reset all the cards selected by the player 
					table.printMsg("{pass}\n"); //The player passed
					table.repaint(); //This is to repaint the entire board
					table.setActivePlayer(currentIdx);//We set the new player as the current ID
					table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n"); //We print the player whose move it is next 
				}
			}
			
			
			else { //if the player has played cards and not passed	
				Hand hand = composeHand(player, cards); //We compose the hand and set in the variable hand to store and compare for future 
				if(playerList.get(currentIdx) != ex_hand.getPlayer()) {//if this is not the player who moved last	
					if(hand == null) //if the hand that is played is null then
						table.printMsg(cards + " <== Not a legal move!!!  \n");//the move is invalid and the cards don't form any hand
					else if(!hand.beats(ex_hand))// if the player is not able to beat the previous player
						table.printMsg("{" + hand.getType() +   "} " + cards + " <== Not a legal move!!!  \n"); //then we print a message stating that the given hand is not legal
					else if(hand.beats(ex_hand)==true){ //in the other case where the player beats the previous hand
						handsOnTable.add(hand);//We update the hand that is played by adding it on the table
						hand.sort();//We sort the cards played in this hand
						table.printMsg("{" + hand.getType() +   "} " + cards + "\n"); //We print the cards and the type of hand it is
						for(int i = 0; i < hand.size(); i++) //We traverse through all the cards picked
							cinh.removeCard(hand.getCard(i)); //and remove them from the cards in hand of the player
						currentIdx = (currentIdx+1)% 4; //Since there are only 4 players, we update the id and the current ID can not exceed 4
						table.resetSelected();//This is to reset all the cards selected by the player 
						table.repaint(); //This is to repaint the entire board
						table.setActivePlayer(currentIdx);//We set the new player as the current ID
						table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n"); //We print the player whose move it is next 
					}
					else//If nothing matches,
						table.printMsg(cards + " <== Not a legal move!!!  \n"); //The move is illegal and not valid
					}
				else{ //in the scenario that the player is the same player who made the last move
					if(hand != null) { //if the hand played is not null, i.e. there are cards
						table.printMsg("{" + hand.getType() +   "} " + cards + "\n"); //We print the hand type and the cards that are played
						hand.sort();//We sort all the cards in the hand played
						handsOnTable.add(hand);//We add these cards as the hand on the table 
						for(int i = 0; i < hand.size(); i++)//We traverse through all the cards that are played
							cinh.removeCard(hand.getCard(i)); //And remove them from the cards in hand of the player
						currentIdx = (currentIdx+1)% 4; //Since there are only 4 players, we update the id and the current ID can not exceed 4
						table.resetSelected();//This is to reset all the cards selected by the player 
						table.repaint(); //This is to repaint the entire board
						table.setActivePlayer(currentIdx);//We set the new player as the current ID
						table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n"); //We print the player whose move it is next 
					}
					else//If nothing matches,
						table.printMsg(cards + " <== Not a legal move!!!  \n"); //The move is illegal and not valid
				}	
			}
		}
	
		if(endOfGame()){ //in the condition that the game ends
			String message = "GAME OVER! \n"; //We make a string to store all message and print it at the end
			for(int i = 0; i <= 3; i++) {//We traverse through all the players
				CardGamePlayer p = playerList.get(i);
				int num = p.getNumOfCards();
				if( num!=0) //If a player has cards left,
					message +=  p.getName() + " has " + num + " cards in hand. \n"; //This player has not won and the message is stored
				else //in the case that the player has no card left
					message += p.getName() + " wins the game.\n"; //He wins the game and we store this message
			} 
			table.printMsg(message); //We print this message on the bigTwoTable text area as per the requirements 
			table.disable(); //Finally we disable the bigTwoTable so that further game can not be played
			JOptionPane.showMessageDialog(null, message); //We make a dialog box with the same message so that the players can see	
		}
	}
	/**
	 * 
	 * a method for checking if the game ends.
	 * 
	 * @return won, a boolean variable, which specifies if the game has ended or not.
	 * 
	 */
	public boolean endOfGame() {
		boolean won = false; //a boolean value to store if a player won
		for(int i = 0; i < 4; i++) {
			CardGamePlayer p = playerList.get(i);
			int num = p.getNumOfCards();
			if(num == 0)//The player with no cards left wins
				won = true; //then won becomes true	
		}
		return won; //we return won
	}
	
	
	/**
	 * 
	 * a method for making a socket connection with the game server. Upon successful connection, We should :
	 * (i) create an ObjectOutputStream for sending messages to the game server; 
	 * (ii) create a thread for receiving messages from the game server.
	 * 
	 */
	public void makeConnection() {
		setServerIP("127.0.0.1"); //We hard code the server IP to the device we are working on.
		setServerPort(2396); //We hard code the server TCP port to the given number.
		try { //We make the try block for the code
			sock = new Socket(serverIP, serverPort); //We create a socket to create the serverIP and serverPort
			oos = new ObjectOutputStream(sock.getOutputStream()); //We set the object output stream using the socket
			(new Thread(new ServerHandler())).start(); //We create a Thread to handle the server requests
			sendMessage(new CardGameMessage(1, -1, playerName)); //When a player sends a message we output it along with the player name
			sendMessage(new CardGameMessage(4, -1, null)); } 
		catch (IOException e) { //To catch the exceptions made
			e.printStackTrace(); //We print all the exceptions
		}
	}
	
	/**
	 * 
	 * a method for parsing the messages received from the game server. 
	 * This method should be called from the thread responsible for receiving messages from the game server.
	 * 
	 * @param message, a GameMessage type variable for the message that is sent and will be printed
	 * 
	 */
	public void parseMessage(GameMessage message) {
		
		switch (message.getType()) {
		
		/**
		 * Sent by the server to a client when a connection is established. In this
		 * message, playerID specifies the playerID of the local player, and data is
		 * a reference to a regular array of strings specifying the names of the
		 * players
		 */
		case 0: //if the message type is 0
			setPlayerID(message.getPlayerID()); //We set the player id of the player
			for(int i=0;i<4;i++)
				if(null!=((String[])message.getData())[i]) //if the data we receive is not null
					playerList.get(i).setName(((String[])message.getData())[i]); //we set the name of the player as given
			break;
		/**
		 * Sent by a client to the server when a connection is established. In this
		 * message, playerID specifies the playerID of the player, and data is
		 * simply null (not being used).
		 */	
		case 1:
			numOfPlayers += 1; //We increase the number of players
			playerList.get(message.getPlayerID()).setName((String)message.getData()); //We set the name of the player as given
			table.printMsg(playerList.get(message.getPlayerID()).getName() + " has entered the room! \n"); //when the player enters 
			break;
		/**
		 * Sent by the server to a client after a connection is established but the
		 * server is not able to serve this client because it is full. In this
		 * message, playerID is -1 (not being used) and data is simply null (not
		 * being used).
		 */	
		case 2:
			table.printMsg("Server is not able to serve because it is full !");
			break;
		/**
		 * Broadcast by a server when a client loses connection to the server. In this message,
		 * playerID specifies the player who loses the connection to the server, and data is a string
		 * representation of the IP address and TCP port of this player.
		 */			
		case 3:
			numOfPlayers -= 1; //the number of players decrease by one
			table.printMsg(playerList.get(message.getPlayerID()).getName()+" has exited the room! \n"); //We print the message that the player has left
			playerList.get(message.getPlayerID()).setName("Player "+message.getPlayerID());	//We change the name of the player who left
			if(false==endOfGame()) { //if the game has not ended
				table.disable(); //We  disable the table so that no one can use it
				table.enableMsgArea();
				for(int i=0;i<playerList.size();i++) //For all the players that are left																				
					playerList.get(i).removeAllCards();//We remove all the player's cards
				table.setActivePlayer(-1); //we set the active player as -1 i.e. no one
				handsOnTable=new ArrayList<Hand>();	
			}
			break;
		/**
		 * Sent by a client to the server to indicate it is ready for a new game. The server will also
		 * broadcast this message upon receiving it. In this message, playerID specifies the player who
		 * becomes ready for a new game (for the message broadcast by the server) or -1 (for the message
		 * sent by a client), and data is simply null (not being used).
		 */	
		case 4:
			table.printMsg(playerList.get(message.getPlayerID()).getName()+" is ready! \n"); //playerID specifies the player who becomes ready for a new game (for the message broadcast by the server) or -1 (for the message
			break;
			
		/**
		 * Broadcast by the server when all clients are ready for a new game. In this message, playerID is 
		 * -1 (no being used), and data is a reference to a Deck object (a shuffled deck for the new game).
		 */	
		case 5:
			start((BigTwoDeck)message.getData()); //playerID is -1 (no being used), and data is a reference to a Deck object 
			break;
		/**
		 * Sent by a client when the local player makes a move. The server will broadcast this message upon
		 * receiving it. In this message, playerID specifies the player who makes the move, and data is a
		 * reference to an array of int specifying the indices of the cards being played.
		 */	
		case 6:
			checkMove(message.getPlayerID(),(int[])message.getData()); //Sent by a client when the local player makes a move. The server will broadcast this message upon receiving it. 
			break;
			
		/**
		 * Sent by a client to the server when the local player press [ENTER] in the chat input field.
		 * The server will first add the name, IP address and TCP port of the player into the chat
		 * message, and then broadcast the message. In this message, playerID specifies the player who
		 * sent this chat message, and data is a reference to a string containing a formated chat message.
		 *			 
		 */	
		case 7:
			table.printMsgChat(((String)(message.getData()))+"\n"); //we print the message about this player
			break;
			
		default:
			break;		
		}	
	}
	
	
	/**
	 * 
	 * a method for sending the specified message to the game server. 
	 * 
	 * @param message, a GameMessage type variable for the message that is sent and will be printed
	 */
	public void sendMessage(GameMessage message) {
		try { //first we enter the try block
			oos.flush(); //we flush the object output stream
			oos.writeObject(message);} //we write an object of message using the object output stream
			
		catch (IOException e) {
			e.printStackTrace();} //if there is any exception, we print it
	}
	
	
	/**
	 * 
	 * an inner class that implements the Runnable interface.
	 * 
	 * @author Aryak Kumar
	 *
	 */
	private class ServerHandler implements Runnable {
		/**
		 * 
		 * create a thread with an instance of this class as its job in the makeConnection() method
		 * 
		 */
		public void run() {
			try { //we enter the try block 
				InputStream s = sock.getInputStream(); //we create a new input stream type object socket
				ObjectInputStream inps = new ObjectInputStream(s); //we make an object input stream of the socket
				for (;;) { //we create an infinite loop 
					Object c = inps.readObject(); //create an object of the read object
					CardGameMessage cg = (CardGameMessage)(c); //we cast this object to type of CardGameMessage 
					parseMessage(cg); //we then parse this message
					if(null == playerName ) //if the player name is empty or null
						break; //then we break the for loop
				}
			} catch (Exception e) {//in case of an exception
				e.printStackTrace();} //we print all the mistakes
		}	
	}
	
	
	/**
	 * 
	 * a method for creating an instance of BigTwoClient.
	 * 
	 * @param args,String array which is not used
	 */
	public static void main(String args[]){
		BigTwoClient obj = new BigTwoClient();}
	
	
	/**
	 * 
	 * a method for returning a valid hand from the specified list of cards of the player.
	 * Returns null is no valid hand can be composed from the specified list of cards.
	 * 
	 * @param player, A cardGamePlayer type variable that stores the current player.
	 * 
	 * @param cards, A CardList type variable that stores all the current player's cards . 
	 * 
	 * @return Returns the hand from the specified list of cards of the player or null if no valid hand can be composed from the specified list of cards.
	 * 
	 */
	public Hand composeHand(CardGamePlayer player, CardList cards){
		
		Triple triple =new Triple(player,cards);//Object created for if the hand is a triple
		if(triple.isValid()==true) //If the set of cards given form a triple
			return triple; //We return triple as the hand
		
		Pair pair = new Pair(player,cards);//Object created for if the hand is a pair
		if(pair.isValid()==true) //If the set of cards given form a pair
			return pair; //We return a pair
		
		Single single = new Single(player,cards); //Object created for if the hand is a single
		if(single.isValid()==true)//If the set of cards given form a single
			return single; //We return a single
		
		StraightFlush straightFlush=new StraightFlush(player,cards);//Object created for if the hand is a straight flush
		if(straightFlush.isValid()==true)//If the set of cards given form a straight flush
			return straightFlush; //We return a straight flush
		
		Quad quad=new Quad(player,cards);//Object created for if the hand is a quad
		if(quad.isValid()==true)//If the set of cards given form a quad
			return quad; //We return a quad
		
		FullHouse fullHouse=new FullHouse(player,cards);//Object created for if the hand is a full House
		if(fullHouse.isValid()==true)//If the set of cards given form a full house
			return fullHouse; //We return a full house
		
		Flush flush=new Flush(player,cards);//Object created for if the hand is a flush
		if(flush.isValid()==true)//If the set of cards given form a flush
			return flush; //We return a flush
		
		Straight straight=new Straight(player,cards);//Object created for if the hand is a straight
		if(straight.isValid()==true)//If the set of cards given form a straight
			return straight; //We return a straight	
	
		return null;}//If it doesn't form any known hands, we return null
}
	
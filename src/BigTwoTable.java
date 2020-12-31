import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * 
 * @author Aryak Kumar
 * 
 *  The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI for the Big Two card game and handle all user actions. 
 * 
 */
public class BigTwoTable implements CardGameTable
{
	
	private BigTwoClient game; //a card game associates with this table.
	private boolean[] selected; //a boolean array indicating which cards are being selected.
	private int activePlayer; //an integer specifying the index of the active player.
	private JFrame frame; //the main window of the application.
	private JPanel bigTwoPanel; //a panel for showing the cards of each player and the cards played on the table.
	private JButton playButton; //a “Play” button for the active player to play the selected cards.
	private JButton passButton; //a “Pass” button for the active player to pass his/her turn to the next player.
	private JButton sendButton; //a “Send” button for any player to send a message in the chat area.
	private JTextArea msgArea; //a text area for showing the current game status as well as end of game messages.
	private Image [][] cardImages; //a 2D array storing the images for the faces of the cards.
	private Image cardBackImage; //an image for the backs of the cards.
	private Image [] avatars; //an array storing the images for the avatars.
	private boolean mouse = true; //a boolean type variable to store the click ability of the mouse
	private JTextArea message_receive;
	private JTextField message_send;
	
	
	
	/**
	 *
	 * a constructor for creating a BigTwoTable
	 * 
	 * @param game is a reference to a card game associates with this table.
	 */
	public BigTwoTable(BigTwoClient game){
		this.game = game;
		createBigTwo();} //we call the setup function to establish the game	
	
	
	/**
	 *
	 * a method to set up the entire BigTwoTable
	 * 
	 */	
	public void createBigTwo() {
		frame = new JFrame();//First we make a new java Frame
		bigTwoPanel = new BigTwoPanel(); //We create an object for the big Two Panel
		cardBackImage = new ImageIcon("src/cards/b.gif").getImage(); //card back image stored
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//On closing the java frame, the game stops running
		avatars = new Image[4];//We create the image icons for all players
		frame.setLayout(new BorderLayout()); //We set the layout of the frame as BorderLayout
		cardImages = new Image[4][13]; //We create a 2D array to store all the cards
		playButton = new JButton("Play");//play button created
		JMenuBar menuBar = new JMenuBar();//Creating a JMenu Bar
		passButton = new JButton("Pass"); //pass button created
		JPanel play_pass = new JPanel(); //We create a panel to hold the play and pass buttons
		JPanel text_area = new JPanel(); //We create a panel to hold the text box and chat box buttons
		JMenu menu = new JMenu("Game"); //Menu bar with new object named Game
		JMenu mesg = new JMenu("Message"); //Menu bar with new object named Game
		menu = new JMenu("Game"); //Menu bar with new object named Game
		mesg = new JMenu("Message"); //Menu bar with new object named Message
		menuBar.add(menu); //We add the menu item to the menu bar
		menuBar.add(mesg); //We add the menu item to the menu bar
		msgArea = new JTextArea(); //We create a Java text area to print all the messages that come through
		msgArea.setEditable(false); //Since this should not be changed by the user, we make the editable as false
		JScrollPane messagearea = new JScrollPane(msgArea); //Java Scrollable plane to keep track of all messages and display them accordingly
		frame.setSize(1150, 1000); //we set the size of the frame
		
		avatars[0] = new ImageIcon("src/avatar/BobaFett.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); //We set the image and the size of the image for the first player
		avatars[1] = new ImageIcon("src/avatar/DarthMaul.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); //We set the image and the size of the image for the second player
		avatars[2] = new ImageIcon("src/avatar/DarthVader.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); //We set the image and the size of the image for the third player
		avatars[3] = new ImageIcon("src/avatar/R2D2.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH); //We set the image and the size of the image for the fourth player
		
		
		char suits[] = {'d', 'c', 'h', 's'}; //character array to store the suits of all the cards
		char ranks[] = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'}; //character array to store all the ranks of all the cards
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 13; j++)
				cardImages[i][j] = new ImageIcon("src/cards/"+ ranks[j] + suits[i] + ".gif").getImage(); //We set the image of the front face of all the cards
		
		JMenuItem restart = new JMenuItem("Connect"); //A new item in the java menu named Restart
		JMenuItem clear = new JMenuItem("Clear");
		restart.addActionListener(new ConnectMenuItemListener()); //add an event listener to see when this menu item is clicked
		menu.add(restart); //We add this item in the menu
		play_pass.add(passButton); //add the pass button to the play pass pane at the bottom of the screen 
		playButton.addActionListener(new PlayButtonListener()); //add an event listener to see when this button item is clicked
		JMenuItem quit = new JMenuItem("Quit"); //A new item in the java menu named Quit
		quit.addActionListener(new QuitMenuItemListener()); //add an event listener to see when this menu item is clicked
		menu.add(quit); //We add this item in the menu
		clear.addActionListener(new ClearChatListener()); //add an event listener to see when this button item is clicked
		passButton.addActionListener(new PassButtonListener()); //add an event listener to see when this button item is clicked
		mesg.add(clear);//We add this item in the menu
		play_pass.add(playButton); //add the play button to the play pass pane at the bottom of the screen 
		JScrollPane scrollPane = new JScrollPane(msgArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel inputArea = new JPanel(); //We create a new panel for taking input of text
		scrollPane.setPreferredSize(new Dimension(399, 399)); //We create the preferred size of the dimensions
		text_area.setLayout(new BoxLayout(text_area, BoxLayout.Y_AXIS)); //We set the text area layout and its position
		message_receive = new JTextArea(16, 25); //We set the message_receive text area size
		message_receive.setLineWrap(true); //The line wrap set to true so that the text can be wrapped
		message_receive.setEditable(false); //the message_receive text area for the messages is not editable
		message_send = new JTextField(20); //We create a text field area for the message_send messages
		sendButton =  new JButton("Send"); //We add the send button on the frame
		sendButton.addActionListener(new SendButtonListener()); //For sending the messages, we add the send button
		message_send.addKeyListener(new EnterListener()); //we add an event listener for the keys
		inputArea.add(sendButton); //we add the send button in the text chat area
		inputArea.add(message_send); //we add the outbox message area in the chat area
		text_area.add(scrollPane); //we add the scroll pane in the text chat area
		JScrollPane Scrol = new JScrollPane(message_receive); //we create a scroller for the chat box
		Scrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //Create the scroll bar for the vertical direction in the text area
		Scrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//Create the scroll bar for the horizontal direction in the text area
		text_area.add(Scrol); //we add the scroll bar in the text area
		text_area.add(inputArea); //we ad the input area in the text area
		frame.add(menuBar, BorderLayout.NORTH); //We add the menuBar in the top of the frame
		frame.add(bigTwoPanel); //We add the Big Two Panel in the frame
		frame.add(text_area, BorderLayout.EAST); //We add the text area in the right side of the frame
		frame.add(play_pass, BorderLayout.SOUTH); //We add the play and pass button in the bottom of the frame
		
		frame.setVisible(true);// We set the visibility of the frame as true
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * An inner class that extends the JPanel class and implements theMouseListener interface. 
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * 
	 */
	private class BigTwoPanel extends JPanel implements MouseListener {
		
		/**
		 *
		 * constructor used to set the array of selected cards and setting value
		 * 
		 */
		public BigTwoPanel()
		{
			selected = new boolean[13];
			this.addMouseListener(this);
		}
		
		
		/**
		 * 
		 * a function used to paint and set the components of the game
		 * 
		 * @param draw,Graphics type variable used to generate graphics for the entire table
		 * 
		 */
		public void paintComponent(Graphics draw) {
			this.setBackground(new Color(0,153,0)); //We set the color we need to paint the playing board
			draw.setFont(new Font("SansSerif", Font.BOLD, 15)); //We set the font for the playing board
			setActivePlayer(game.getCurrentIdx()); //We set the active player with the one who has currentIDx
			super.paintComponent(draw);	//We paint the component with draw
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //We set the layout of this as Box Layout
			
			ArrayList<Hand> all_hands = game.getHandsOnTable(); //to store all the hands on the table
			int len = all_hands.size(); //to store the length of all the hands that have been played
			if(len != 0) { //if this is not the first hand that is played
					Hand ex_hand = all_hands.get(len-1);
					draw.drawString("Played by "+ex_hand.getPlayer().getName(),20,700); //we show the last hand that is played and the player who played it
					for(int j = 0; j < all_hands.get(len-1).size(); j++) {
						int suit = ex_hand.getCard(j).getSuit(); //we get the suit of the card 
						int rank = ex_hand.getCard(j).getRank(); //we get the rank of the card
						Image cd = cardImages[suit][rank]; //we create an image for drawing
						draw.drawImage(cd,200+(45*j),690, this);
					}		
			}
			else //if this is the first player making a move
				draw.drawString("Select Cards",20,20+(170*4)); //we prompt him to select cards
			
			for(int i=0;i<4;i++) {
				CardGamePlayer player = game.getPlayerList().get(i); //For each player, we get the player
				int pid = game.getPlayerID();
				CardList hand = player.getCardsInHand(); //and store his cards in hand
				draw.drawImage(avatars[i],20,50+(170*i),this); //then we print the avatars of the players
				draw.setColor(Color.BLACK); //We set the color of lines as black
				draw.drawLine(0,170+(170*i),1200,170+(170*i));
				if (i == pid) {
					for(int k=0;k<hand.size();k++) {
						int suit = hand.getCard(k).getSuit(); //we get the suit of the card 
						int rank = hand.getCard(k).getRank(); //we get the rank of the card
						Image cd = cardImages[suit][rank]; //we create an image for drawing
						if(selected[k]) //if the card is selected, then
							draw.drawImage(cd,200+(45*k),50+(160*i),this); //we raise the card than the normal position
						else
							draw.drawImage(cd,200+(45*k),65+(160*i),this); // else we print it as it is, i.e. in a normal position
					}
				}
				else {
					for(int k=0;k<player.getNumOfCards();k++)
						draw.drawImage(cardBackImage,200+(45*k),65+(160*i),this); //we print all the cards, with their back image
				}
				
				if(i == activePlayer){ //if this is the active player
					draw.setColor(Color.BLUE); //We set the color of text as blue
					draw.drawString(player.getName(),20,20+(170 * i));
				}
					
				else {
					if (i ==pid)
						draw.setColor(Color.WHITE); //We set the color of text as white
					else
						draw.setColor(Color.BLACK); //we set the color as black
				draw.drawString(player.getName(), 20, 20 + (170 * i)); //we print the name of the players
				}
			}
		}
		
		
		/**
		 * 
		 * Invoked when the mouse exits a component.
		 * 
		 * @param event, MouseEvent type variable initiated on mouse exit
		 * 
		 */
		public void mouseExited(MouseEvent event) {}
		
		/**
		 * 
		 * Invoked when the mouse enters a component.
		 * 
		 * @param event, MouseEvent type variable initiated on mouse enter
		 * 
		 */
		public void mouseEntered(MouseEvent event) {}
		
		/**
		 * 
		 * Invoked when a mouse button has been released on a component.
		 * 
		 * @param event, MouseEvent type variable initiated on mouse release
		 * 
		 */
		public void mouseReleased(MouseEvent event) {}
		
		/**
		 * 
		 * Invoked when a mouse button has been pressed on a component.
		 * 
		 * @param event, MouseEvent type variable initiated on mouse press
		 * 
		 */
		public void mousePressed(MouseEvent event) {}
		
		/**
		 * 
		 * Invoked when the mouse button has been clicked (pressed and released) on a component.
		 * 
		 * @param event, MouseEvent type variable initiated on mouse click
		 * 
		 */
		public void mouseClicked(MouseEvent event) {
			if(mouse){
				CardGamePlayer player = game.getPlayerList().get(activePlayer); //we set the active player in a player variable
				int k = activePlayer; //and the ID number of the active player
				int hand_size = player.getCardsInHand().size(); //we find and store the size of the cards in hand
				if(event.getY()>((k*160)+50)&&event.getX()>((45*(hand_size-1))+200)&&event.getY()<((k*160)+161)&&event.getX()<((45*(hand_size-1))+278)) {//If we click the mouse between these coordinates
					if (!selected[(hand_size-1)]) //and the card is not selected
						selected[(hand_size-1)] = true; //it gets selected
					else //else if it is already selected
						selected[(hand_size-1)] = false; //it becomes unselected			
				}
				
				for(int i=0;i<hand_size-1; i++) { //we traverse for all the cards other than the last card
					if(!selected[i]) { //if the card is not selected
						if(event.getY()>((k*160)+65)&&event.getX()>((45*i)+200)&&event.getY()<((k*160)+161)&&event.getX()<((45*i)+245)) //and a coordinate between these is clicked
									selected[i] = true; //the card then becomes selected whose image lies between these coordinates
						}
					
					else { //else if the card was not selected initially
						if(event.getY()>((k*160)+50)&&event.getX()>((45*i)+200)&&event.getY()<((k*160)+161)&&event.getX()<((45*i)+245)) //and a coordniate between these is clicked
									selected[i] = false; //the card becomes unselected
					}}
				this.repaint(); //finally we repaint the entire page after selection or de-selection of a card so that the updated table can be seen
			}}}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button. 
	 *
	 */
	public class PlayButtonListener implements ActionListener {
		/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button.
		 * 
		 * @param event, ActionEvent type variable which is activated to pass the move
		 *  
		 */
		public void actionPerformed(ActionEvent event) {
			game.makeMove(activePlayer, getSelected());} //We make move of the player with the selected cards
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button. 
	 *
	 */
	public class PassButtonListener implements ActionListener {
		/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button.
		 * 
		 * @param event, ActionEvent type variable which is activated to pass the move
		 *  
		 */
		public void actionPerformed(ActionEvent event) {
			game.makeMove(activePlayer, null);} //If the player passes the move the cards have null as value
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Send” button. 
	 *
	 */
	public class SendButtonListener implements ActionListener {
		/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Send” button.
		 * 
		 * @param event, ActionEvent type variable which is activated to send the message
		 *  
		 */
		public void actionPerformed(ActionEvent event) {
			String msg = message_send.getText(); //We make a string variable to store the message that is to be sent by the player
			CardGameMessage m = new CardGameMessage(7, -1, msg); //We set the message along with the name of the player and his TCP address
			game.sendMessage(m); //We finally call the sendMessage function and pass the message to be printed
			message_send.setText(null);} //We set the message_send text as null since the message has been sent
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle key pressing events. 
	 *
	 */
	private class EnterListener implements KeyListener {
		
		/**
		 * 
		 * Invoked when a key button has been released.
		 * 
		 * @param event, KeyEvent type variable initiated on key press
		 * 
		 */
		public void keyReleased(KeyEvent event) {}
		
		/**
		 * 
		 * Invoked when a key button has been pressed.
		 * 
		 * @param event, KeyEvent type variable initiated on key press
		 * 
		 */
		public void keyPressed(KeyEvent event) {
			int c = event.getKeyCode(); 
			int k = KeyEvent.VK_ENTER;
			if(c==k){ //we check if the key entered is same as the key event
				String m = message_send.getText(); // them we set the message
				CardGameMessage msg = new CardGameMessage(7, -1, m); //and the cardGameMessage
				game.sendMessage(msg);//and print it on the console chat area
				message_send.setText(null);} //then we set the message_send as null
		}
		
		/**
		 * 
		 * Invoked when a key button has been typed.
		 * 
		 * @param event, KeyEvent type variable initiated on key press
		 * 
		 */
		public void keyTyped(KeyEvent event) {}
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the Menu button. 
	 *
	 */
	private class ConnectMenuItemListener implements ActionListener
	{	/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the MENU button.
		 * 
		 * @param event, ActionEvent type variable which is activated on pressing the menu button
		 *  
		 */
		public void actionPerformed(ActionEvent event) {
			int gpid = game.getPlayerID(); //we get the player id of the player 
			if(-1 != gpid) //we check if this player is already playing
				printMsg("Game Ongoing ! \n"); //we print the message
			else //else
				game.makeConnection();} //we create the connection
	}
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the clear button. 
	 *
	 */
	private class ClearChatListener implements ActionListener
	{	/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the clear button.
		 * 
		 * @param event, ActionEvent type variable which is activated on pressing the clear button
		 *  
		 */
		public void actionPerformed(ActionEvent event) {
			message_receive.setText(null);} //We clear the chat area of the GUI
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface.
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item. 
	 *
	 */
	public class RestartMenuItemListener implements ActionListener{
		/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item.
		 * 
		 * @param event, ActionEvent type variable which is activated to restart the game
		 * 
		 */
		public void actionPerformed(ActionEvent event) {
			BigTwoDeck obj = new BigTwoDeck(); //create a new BigTwoDeck object and call its shuffle() method; 
			obj.shuffle(); //shuffle the deck before starting the game
			game.start(obj); //call the start() method of your CardGame object 
		}
	}
	
	
	/**
	 * 
	 * @author Aryak Kumar
	 * 
	 * an inner class that implements the ActionListener interface.
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item.
	 * 
	 */
	public class QuitMenuItemListener implements ActionListener {
		/**
		 * 
		 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item. 
		 * 
		 * @param event, ActionEvent type variable which is activated to quit the game
		 * 
		 */
		public void actionPerformed(ActionEvent event) {
			System.exit(0);} //To mark the successful termination of the game and the program		
	}
	
	
	/**
	 * 
	 * a method for setting the index of the active player
	 * 
	 * @param activePlayer, Integer type variable which stores the index of current player
	 * 
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer; } //We set the active player to the given index
	
	/**
	 * 
	 * a method for getting an array of indices of the cards selected
	 * 
	 * @return selected_cards, Array of integers to store the indices of cards
	 * 
	 */
	public int[] getSelected() {
		int len = selected.length, arr_size = 0; //len for the length of the selected array and arr_size for the size of selected cards
		for (int i = 0; i < len; i++) //We traverse through the length
			if (selected[i])//And check if that card is selected or not
				arr_size += 1; //if it is, we increment the size
		int [] selected_cards = null;	//we set the variable to store the selected cards
		if(arr_size!=0) { //we check if the size of array is zero or not
			selected_cards = new int[arr_size]; //we set the size same as the size number of cards selected
			int j = 0; //a variable counter for indices in the array
			for (int i = 0; i < len; i++)
				if (selected[i]) { //if the card is selected 
					selected_cards[j] = i; //we add the card to our array
					j++; //and also increment the pointer
				}
		}
				return selected_cards; //we finally return the cards selected
	}
	
	/**
	 * 
	 * a method for resetting the list of selected cards
	 * 
	 */
	public void resetSelected() {	
		int len = selected.length,i=0;
		for (i = 0; i < len; i++)
			if (selected[i]==true) //if the card is selected
			selected [i] = false; //for all the selected cards, we remove them 
	}
	
	/**
	 * 
	 *  a method for repainting the GUI
	 *  
	 */
	public void repaint() {
		bigTwoPanel.repaint(); //We repaint the GUI
	}
	
	/**
	 * 
	 * a method for printing the specified string to the message area of the GUI
	 * 
	 * @param val, String type variable to store the message that is given.
	 * 
	 */
	public void printMsg(String val) {
		String txt = msgArea.getText(); //the text printed on the message area
		msgArea.append(val); //We print the message
		int len = txt.length(); //The length of all the text 
		msgArea.setCaretPosition(len); // We set the position of the caret according to the length		
	}
	
	
	/**
	 * 
	 * a method for printing the specified string to the chat area of the GUI
	 * 
	 * @param msg, String type variable to store the message that is given.
	 * 
	 */
	public void printMsgChat(String msg) {
		message_receive.append(msg); //We append the given message
		String inc = message_receive.getText(); //get all the chats made 
		int l = inc.length(); //find the total length of the chats
		message_receive.setCaretPosition(l); //we set the caret position at the end
	}
	
	
	/**
	 * 
	 * a method for enabling chat box in the GUI. You should 
	 * (i) enable the “Send” button (i.e., making them click-able); and 
	 * (ii) enable the chat message area for selection of cards through mouse clicks.
	 *
	 */
	public void enableMsgArea(){
		sendButton.setEnabled(true); //We enable the send button
		message_receive.setEnabled(true); //We enable the message_receive message feature
		msgArea.setEnabled(true);} //We enable the message chat area
	
	/**
	 * 
	 * a method for disabling chat box in the GUI. You should 
	 * (i) disable the “Send” button (i.e., making them click-able); and 
	 * (ii) disable the chat message area for selection of cards through mouse clicks.
	 *
	 */
	public void disableMsgArea(){
		sendButton.setEnabled(false); //We disable the send button
		message_receive.setEnabled(false); //We disable the message_receive message feature
		msgArea.setEnabled(false);} //We disable the message chat area
	
	/**
	 * 
	 * a method for clearing the chat area of the GUI.
	 *  
	 */
	public void clearChatArea() {
		message_receive.setText(null);} //We clear the chat area of the GUI
	
	
	/**
	 * 
	 * a method for clearing the message area of the GUI.
	 *  
	 */
	public void clearMsgArea() {
		msgArea.setText(null); //We clear the message area of the GUI
	}

	
	/**
	 * 
	 *  a method for resetting the GUI. We should
	 * (i) reset the list of selected cards using resetSelected() method from the CardGameTable interface; 
	 * (ii) clear the message area using the clearMsgArea() method from the CardGameTable interface; and 
	 * (iii) enable user interactions using the enable() method from the CardGameTable interface.
	 *  
	 */
	public void reset() {
		resetSelected(); //reset the list of selected cards
		clearMsgArea(); //clear the message area
		enable(); //enable user interactions
	}
	
	/**
	 * 
	 * a method for enabling user interactions with the GUI. You should 
	 * (i) enable the “Play” button and “Pass” button (i.e., making them clickable); and 
	 * (ii) enable the BigTwoPanel for selection of cards through mouse clicks.
	 *
	 */
	public void enable() {
		playButton.setEnabled(true); //We enable the Play button
		passButton.setEnabled(true); //We enable the Pass button
		mouse = true; //We enable mouse clicks 
		enableMsgArea();
		bigTwoPanel.setEnabled(true); //We enable the big panel
		resetSelected(); //We reset all the selected cards
		
		
	}
	
	/**
	 * 
	 * a method for disabling user interactions with the GUI. You should 
	 * (i) disable the “Play” button and “Pass” button (i.e., making them not clickable); and 
	 * (ii) disable the BigTwoPanel for selection of cards through mouse clicks.
	 * 
	 */
	public void disable() {
		playButton.setEnabled(false); //We disable the Play button
		passButton.setEnabled(false); //We disable the Pass button
		mouse = false; //We disable mouse clicks 
		disableMsgArea();
		bigTwoPanel.setEnabled(false); //We disable the big panel
		
	}
	
}
 
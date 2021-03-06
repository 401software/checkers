/**
*This class is the main class in the back end of the checkers game. It Implements ControlLogic and TransmitData. It also inherits from Thread.
*@version 3
*@author Dan Martineau
*/

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class ControlUnit extends Thread
{
	/*FIELDS*/
	private ControlLogic control;
	private TransmitData network;
	private String myID;
	private String oppID;
	private boolean engaged;
	private int myScore;
	private boolean mustJump;
        private boolean anotherJump;
	private String prevString;		//string previously recieved
	private Queue out;                      //Queue for outgoing Strings
	private Queue status;			//Queue for status updates
	private Queue chat;			//Queue for chat messages
	private Queue oppMoves;			//Queue for opponent moves
	private boolean gameOver;		//flag if the game is over
        private String myUsername;              //username of current player
        private String oppUsername;             //username of opponent
	private JFrame frame;			//parent frame from calling class
        protected boolean ready;                //whether or not we are ready to being netowrking
        private boolean initialized;            //whether or not the sever has responded
	
	/**
	*OVERLOADED
	*Constructor
	*@param the DCHP of the game server
	*/
	public ControlUnit(String ip) throws Exception
	{
		network = new TransmitData(ip);
		myScore = 0;
		mustJump = false;
                anotherJump = false;
		out = new Queue();
		status = new Queue();
		chat = new Queue();
		oppMoves = new Queue();
		engaged = false;
		gameOver = false;
                myUsername = "John Doe";
                oppUsername = "";
                initialized = false;
                
		//makes sure that TransmitData is fully initialized so that our data will be accurate
		do
		{
                    myID = network.getMyID();
                    oppID = network.getOppID();
		}while(!network.initialized());

		//decide who begins
		if(oppID == null || Integer.parseInt(network.getMyID()) < Integer.parseInt(network.getOppID()))
                    control = new ControlLogic(true);
		else
                    control = new ControlLogic(false);
                
                ready = true;

		//begin thread
		start();
	}
	
	/**
	*OVERLOADED
	*Constructor
	*@param the DCHP of the game server
	*@param parent frame from calling class
	*/
	public ControlUnit(String ip, JFrame frame) throws Exception
	{
		this.frame = frame;
		myScore = 0;
		mustJump = false;
                anotherJump = false;
		out = new Queue();
		status = new Queue();
		chat = new Queue();
		oppMoves = new Queue();
		engaged = false;
		gameOver = false;
                myUsername = "John Doe";
                oppUsername = "";
                
                //get player's username
                String user = JOptionPane.showInputDialog(frame, "Please input your desired username: ", myUsername);
                
                //fail safe if user hits the cancel option
                if(user!=null)
                    myUsername = user;
                
                network = new TransmitData(ip);
                
		//makes sure that TransmitData is fully initialized so that our data will be accurate
		do
		{
			myID = network.getMyID();
			oppID = network.getOppID();
		}while(!network.initialized());

		//decide who begins
		if(oppID == null || Integer.parseInt(network.getMyID()) < Integer.parseInt(network.getOppID()))
			control = new ControlLogic(true);
		else
			control = new ControlLogic(false);
                
                ready = true;

		//begin thread
		start();
	}

	/**
	*Method will move a piece--USE EXTERNALLY FROM THIS CLASS (in this class use control.move() only)
	*@param previous row coordinate
	*@param previous coloumn coordinate
	*@param current row coordinate
	*@param current coloumn coordinate
	*@return true if piece was moved
	*/
	protected boolean move(byte prevA, byte prevB, byte currA, byte currB)
	{
		//don't allow a move if it's not player's turn, not engaged, or if the game is over--this is one reason not to use this method internally
		if(!control.getMyTurn() || !engaged || gameOver)
			return false;

		boolean move = false;
		boolean jump = false;

		//set jump to true if the next move a jump
		jump = control.isJump(prevA, prevB, currA, currB, false);

		//move the piece
		move = control.move(prevA, prevB, currA, currB, true);

		//find out if player must jump again
                if(mustJump)
                    anotherJump = true;
                
                mustJump = control.getJumpStatus();


		//send the move command to the opponent's instance--yet another reason not to call this method internally
		byte[] prevByte = {prevA, prevB}; String prev = new String(prevByte);
		byte[] currByte = {currA, currB}; String curr = new String(currByte);

		if(jump && mustJump)
                {
			out.enque("c " + myID + " c " + oppID + " j " + control.getOppCoordinates(prev) + " " + control.getOppCoordinates(curr) + " f");
                        status.enque("You must jump again.");
                }
		else if(!jump && mustJump && !anotherJump)
                {
			status.enque("You must jump your opponent's piece.");
                        JOptionPane.showMessageDialog(frame, "You must jump your opponent's piece.", "Czechers", JOptionPane.ERROR_MESSAGE);
                }
		else if(jump && !mustJump)
			out.enque("c " + myID + " c " + oppID + " j " + control.getOppCoordinates(prev) + " " + control.getOppCoordinates(curr) + " t");
		else if(move && !jump)
			out.enque("c " + myID + " c " + oppID + " m " + control.getOppCoordinates(prev) + " " + control.getOppCoordinates(curr));
		else if(!move && !jump && !mustJump)
                        JOptionPane.showMessageDialog(frame, "Invalid Move!", "Czechers", JOptionPane.ERROR_MESSAGE);
                else if(anotherJump && !jump && mustJump)
                {
                        JOptionPane.showMessageDialog(frame, "You must jump again!", "Czechers", JOptionPane.ERROR_MESSAGE);
                        anotherJump = false;
                        //mustJump = control.getJumpStatus();
                        mustJump = false;
                }

		//see if there are anhy moves left
		if(!control.movesLeft())
		{
			out.enque("c " + myID + " c " + oppID + " l");
			gameOver = true;
		}

		return move;
	}
        
        /**
         * Returns whether or not a piece is a king
         * @param prev x
         * @param prev y
         * @param cuur x
         * @param curr y
         * @return king status
         */
        protected boolean getKingStatus(int curra, int currb)
        {
            return control.getKingStatus(curra, currb);
        }

	/**
	*Find a new opponent
	*/
	protected void newOpponent()
	{
		network.newOpponent();
	}
	
	/**
	*Offer to start a new game with the current opponent if the game is over
	*@return true if the current game is over and there is still an opponent
	*/
	protected boolean newGame()
	{
		boolean newGame = false;
		
		if(engaged && gameOver)
		{
			out.enque("c " + myID + " c " + oppID + " g");
			newGame = true;
		}
		
		return newGame;
	}

	/**
	*Offer a draw with the current opponent
	*/
	protected void draw()
	{
		if(!gameOver)
			out.enque("c " + myID + " c " + oppID + " d");
	}
	
	/**
	*Passthrough to ControlLogic()--returns score of player
	*@return the score
	*/
	protected int getMyScore()
	{
		return control.getMyScore();
	}
        
        /**
         * Accessor for player username
         * @reutrn username of player
         */
        protected String getMyUsername()
        {
            return myUsername;
        }
        
        /**
         * Accessor for oppnent username
         * @reutrn username of opponent
         */
        protected String getOppUsername()
        {
            return oppUsername;
        }
	
	/**
	*Returns true if the game is over
	*@return true or false
	*/
	protected boolean gameOver()
	{
		return gameOver;
	}
	
	/**
	*Resign from a game
	*/
	protected void resign()
	{
		out.enque("c " + myID + " c " + oppID + " r");
		gameOver = true;
	}

	/**
	*Terminate connection and end program.
	*/
	protected void endAndExit()
	{
		network.terminateConn();
		System.exit(0);
	}

	/**
	*Return whether or not it is player's turn
	*@return player's turn
	*/
	protected boolean getMyTurn()
	{
		return control.getMyTurn();
	}

	/**
	*Tells whether or not player is engaged
	*@return engagement status
	*/
	protected boolean engaged()
	{
		return engaged;
	}

	/**
	*Sends a chat message to opponent
	*@param message string
	*/
	protected void sendMessage(String message)
	{
		if(engaged)
			out.enque("c " + myID + " c " + oppID + " c " + message);
	}

	/**
	*Fetches chat messages
	*@return message or null
	*/
	protected String getChat()
	{
		return chat.deque();
	}

	/**
	*Fetches status updates
	*@return update or null
	*/
	protected String getStatus()
	{
		return status.deque();
	}

	/**
	*Fetches the coordinates of opponent's latest move
	*@return latest move or array of -1 sentinals
	*/
	protected byte[] getMove()
	{
		byte[] move = new byte[4];
		String coordinates = oppMoves.deque();

		if(coordinates==null)
		{
			for(int i = 0; i < 4; i++)
			{
				move[i] = -1;
			}
		}
		else
		{
			for(int i = 0; i < 4; i++)
			{
				move[i] = Byte.parseByte(coordinates.substring(i, i+1));
			}
		}

		return move;
	}

	/**
	*Returns the player's ID
	*@return myID
	*/
	protected String getMyID()
	{
            return myID;
	}

	/**
	*Returns the opponent's ID
	*@return oppID
	*/
	protected String getOppID()
	{
            return oppID;
	}
        
        /**
         * Accessor for initizalized
         * @return you know what it returns, you cheeky sonofagun
         */
        protected boolean getInitialization()
        {
            return initialized;
        }

	/**
	*Run method from Thread
	*/
	@Override
	public void run()
	{
		String hold = null;

		for(;;)
		{
			try
			{
				//check for incoming data
				hold = network.getData();
			}
			catch(Exception e)
			{
				System.out.println("Exception recieving in ControlUnit(): " + e);
			}
                        
			decode(hold);

			//make sure we have opponent
			network.engagementStatus();
                        
                        //brief pause for program to catch up with thread
                        try{TimeUnit.MILLISECONDS.sleep(95);}
                        catch(Exception e){System.out.println(e);}

			try
			{
				if(engaged && out.getLength() > 0)
					network.sendData(out.deque());
			}
			catch(Exception x)
			{
				System.out.println("Exception sending in ControlUnit(): " + x);
			}

			//reset hold
			hold = null;
		}
	}
	
	/**
	*Method will handle invites for a new game from opponent
	*/
	private void newGameRequest()
	{
		int result = JOptionPane.showConfirmDialog(frame, "Your opponent would like to begin a new game. Would you?", "Accept Invite", JOptionPane.YES_NO_OPTION);
		
		//set up new game if desired and send accept or reject string to opponent
		if(result==JOptionPane.YES_OPTION)
		{
			out.enque("c " + myID + " c " + oppID + " g a");
			status.enque("New game has begun.");
			
			gameOver = false;
			
			//decide who begins
			if(Integer.parseInt(myID) < Integer.parseInt(oppID))
				control = new ControlLogic(true);
			else
				control = new ControlLogic(false);
		}
		else if(result==JOptionPane.NO_OPTION)
		{
			out.enque("c " + myID + " c " + oppID + " g r");
			status.enque("You have rejected the new game invite.");
		}
	}
	
	/**
	*Method will handle draw offers from opponent
	*/
	private void drawRequest()
	{
		int result = JOptionPane.showConfirmDialog(frame, "Your opponent has offered a draw; do you accept?", "Accept Draw", JOptionPane.YES_NO_OPTION);
		
		//send accept or reject string to opponent
		if(result==JOptionPane.YES_OPTION)
		{
			out.enque("c " + myID + " c " + oppID + " d a");
                        
                        status.enque("Game has been drawn...and quartered.");
			
			gameOver = true;
		}
		else if(result==JOptionPane.NO_OPTION)
		{
			out.enque("c " + myID + " c " + oppID + " d r");
		}
	}
	
	/**
	*Method Decodes Recieved Strings and preforms actions based on their meanings
	*@param recieved String
	*/
	private void decode(String data)
	{
		//ensure data was intended for client
		if(data==null || data.length() < 6 || !(data.substring(0,1).equals("c")))
			return;
		else
		{
			if(data.substring(6,7).equals("d"))
			{
				//player has been disconnected from opponent
				engaged = false;
				gameOver = true;
				oppID = null;
				status.enque("Opponent has left the game...It's okay, at least we still have each other :)");
			}
			else if(data.substring(6,7).equals("c"))
			{
				if(data.substring(12,13).equals("l"))
				{
					status.enque("You've won the game!");
                                        JOptionPane.showMessageDialog(frame, "You've won the game!", "Czechers", JOptionPane.WARNING_MESSAGE);
					gameOver = true;
				}
				
				else if(data.substring(12,13).equals("g") && gameOver)
				{
					if(data.length() <= 13)
					{
                                            status.enque("Opponent has requested new game.");
                                            newGameRequest();
					}
					else if(data.substring(14).equals("a"))
					{
						status.enque("Opponent has accepted your new game invite!");
						
						gameOver = false;
						
						//decide who begins
						if(Integer.parseInt(myID) < Integer.parseInt(oppID))
							control = new ControlLogic(true);
						else
							control = new ControlLogic(false);
					}
					else if(data.substring(14).equals("r"))
					{
						status.enque("Opponent has declined to start a new game :(");
					}
				}
				
				else if(data.substring(12,13).equals("d")  && !gameOver)
				{
					if(data.length() <= 13)
					{
						drawRequest();
					}
					else if(data.substring(14).equals("a"))
					{
						status.enque("Opponent has accepted your draw.");
						
						gameOver = true;
					}
					else if(data.substring(14).equals("r"))
					{
						status.enque("Opponent has declined your draw offer.");
					}
				}
				
				else if(data.substring(12,13).equals("r"))
				{
					status.enque("Opponent has resigned. You won!");
                                        JOptionPane.showMessageDialog(frame, "Opponent has resigned. You won!", "Czechers", JOptionPane.WARNING_MESSAGE);
					gameOver = true;
				}

				else if(data.substring(12,13).equals("c"))
				{
					chat.enque(data.substring(14));
					status.enque("Opponent has slid into your DMs. #Slay");
				}
                                
                                else if(data.substring(12,13).equals("u"))
                                {
                                    oppUsername = data.substring(14);
                                }

				else
				{
					//extract coordinates
					String hold = data.substring(14,15);
					boolean moved = false;
					byte r1 = Byte.parseByte(hold);
					hold = data.substring(15,16);
					byte r2 = Byte.parseByte(hold);
					hold = data.substring(17,18);
					byte c1 = Byte.parseByte(hold);
					hold = data.substring(18,19);
					byte c2 = Byte.parseByte(hold);

					if(data.substring(12,13).equals("m") && !gameOver)
					{
						moved = control.move(r1, r2, c1, c2, false);

						//it's my turn again
						control.setMyTurn();

						if(moved)
							oppMoves.enque("" + r1 + r2 + c1 + c2);
					}
					else if(data.substring(12,13).equals("j") && !gameOver)
					{
						if(data.substring(20).equals("t"))
						{
							moved = control.move(r1, r2, c1, c2, false);

							//it's my turn again
							control.setMyTurn();

							if(moved)
								oppMoves.enque("" + r1 + r2 + c1 + c2);
						}
						else if(data.substring(20).equals("f"))
						{
							moved = control.move(r1, r2, c1, c2, false);

							if(moved)
								oppMoves.enque("" + r1 + r2 + c1 + c2);
						}
					}
					else
					{
						status.enque("Your opponent tried to move though the game is over. Be suspicious!");
					}

					//see if there are anhy moves left for current player
					if(!control.movesLeft() && !gameOver)
					{
						network.sendData("c " + myID + " c " + oppID + " l");
                                                
						gameOver = true;
						status.enque("Opponent wins :(");
                                                status.enque("Opponent wins :(");
                                                JOptionPane.showMessageDialog(frame, "Opponent has won the game.", "You Failed", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if(data.substring(6,7).equals("o"))
			{
				//player has opponent
				if(data.substring(8,9).equals("t"))
				{
					engaged = true;
					oppID = data.substring(10,13);
                                        
                                        //send player's username to opponent
                                        out.enque("c " + myID + " c " + oppID + " u " + myUsername);
                                        
                                        if(getMyTurn())
                                        {
                                            status.enque("You are now paired and it is your turn.");
                                        }
                                        else
                                        {
                                            status.enque("You are paired. Opponent will go first.");
                                        }
				}
				//player has no opponent
				else if(data.substring(8,9).equals("f"))
				{
					engaged = false;
					oppID = null;
					status.enque("You are not paired with any opponent.");
				}
			}
			else if(data.substring(6,7).equals("n"))
			{
				//new opponent has been found
				if(data.substring(8,9).equals("t"))
				{
					engaged = true;
					gameOver = false;
					oppID = data.substring(10,13);
                                        
                                        //send player's username to opponent
                                         out.enque("c " + myID + " c " + oppID + " u " + myUsername);

					boolean gotNew = false;

					//decide who begins
					if(oppID == null || Integer.parseInt(myID) < Integer.parseInt(oppID))
                                        {
						control = new ControlLogic(true);
                                                status.enque("Opponent has been found and it is your turn.");
                                        }
					else
                                        {
						control = new ControlLogic(false);
                                                status.enque("Opponent has been found and has the first turn.");
                                        }
                                        
                                        initialized =  true;
				}
				//no opponents available
				else if(data.substring(8,9).equals("f"))
				{
                                    if(!engaged)
                                    {
					engaged = false;
					gameOver = true;
					oppID = null;
                                    }
                                    
                                    status.enque("There are no new opponents available at this time. #ForeverAlone");

                                    initialized =  true;
				}
			}
			else if(data.substring(6,7).equals("i"))
			{
				//player has been initialized in the server
				myID = data.substring(2,5);
                                initialized =  true;
			}
			else
			{
				System.out.println("Unknown string arrived from server: " + data);
                                initialized =  true;
			}
		}
	}

	//for testing purposes only
	public void printBoard()
	{
		control.printBoard();
	}

	//for testing purposes only
	public String toString()
	{
		return control.toString();
	}
}
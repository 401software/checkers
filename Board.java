import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.net.*;


public class Board extends JComponent
{
   // dimension of checkerboard square (25% bigger than checker)

   private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

   // dimension of checkerboard (width of 8 squares)

   private final int BOARDDIM = 8 * SQUAREDIM;

   // preferred size of Board component

   private Dimension dimPrefSize;

   // dragging flag -- set to true when user presses mouse button over checker
   // and cleared to false when user releases mouse button

   private boolean inDrag = false;

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;

   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;

   //INSTANCE OF CONTROLUNIT
   protected ControlUnit control;

   //flag for move
   private boolean move;

   //flag for opponent move
   private boolean moveOpp;

   //flag for player's turn
   private boolean myTurn;

   private String status;
   private String oldStatus;
   private byte[] oppMove;
   private boolean complete;
   private boolean connected;
   private int score;
   private String chat;
   private String currOpp;
   private String prevOpp;
   private boolean wasDisengaged;
   private boolean boardEmpty;

   public Board(JFrame frame) throws Exception
   {
      posChecks = new ArrayList<>();
      dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);
      prevOpp = "";
      boardEmpty = true;

      String dhcp = JOptionPane.showInputDialog(frame, "Please input the DHCP address of GameServer: ", "localhost");
	  if(dhcp!=null && !dhcp.equals("") && !dhcp.equals(" "))
	  {
		  try
		  {
			control = new ControlUnit(dhcp, frame);
                        frame.setTitle("Checkerz  |  " + control.getMyUsername());
                        
                        System.out.print("\nWaiting for ControlUnit");
                        while(!control.ready)
                        {
                            System.out.print(".");
                        }
		  }
		  catch(UnknownHostException e)
		  {
			  JOptionPane.showMessageDialog(frame, "Could not connect to the server. Program will now exit.", "Error!", JOptionPane.INFORMATION_MESSAGE);
			  System.exit(0);
		  }
	  }
	  else
          {
                  JOptionPane.showMessageDialog(frame, "Valid DHCP was not entered. Program will now exit.", "Not Connected", JOptionPane.INFORMATION_MESSAGE);
		  System.exit(0);
          }
	  
      move = true;
      oldStatus = null;
      oppMove = new byte[4];
      oppMove[0] = (byte)-1;
	  complete = true;
	  
	  while(status==null)
	  {
		  status = control.getStatus();
		  if(status != null)
		  {
			  connected = true;
                          
			  //JOptionPane.showMessageDialog(frame, status, "Status Update", JOptionPane.INFORMATION_MESSAGE);
                          MainWindow.updateStatus(status);
		  }
		  
		  try
			{
				TimeUnit.MILLISECONDS.sleep(500);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	  }

	  Thread appThread = new Thread(new Runnable() {
	          @Override public void run() {

	              for(;;)
                              {
                                oppMove = control.getMove();
                                status = control.getStatus();
                                score = control.getMyScore();
                                chat = control.getChat();
                                
                                if(control.engaged() && control.gameOver())
                                {
                                    gameOver();
                                    wasDisengaged = true;
                                    clearBoard();
                                }
                                
                                if(control.engaged() && control.getMyTurn() && !control.gameOver())
                                {
                                    MainWindow.updateTurn(control.getMyUsername());
                                }
                                else if(control.engaged() && !control.getMyTurn() && !control.gameOver())
                                {
                                    MainWindow.updateTurn(control.getOppUsername());
                                }
                                else
                                    MainWindow.updateTurn("<game over>");
                                
                                if(status != null)
                                {
                                        //JOptionPane.showMessageDialog(frame, status, "Status Update", JOptionPane.INFORMATION_MESSAGE);
                                        MainWindow.updateStatus(status);
                                        oldStatus = status;
                                        
                                        if(!control.gameOver())
                                            engaged();
                                        
                                        if(status.equals("Opponent has requested new game."))
                                            MainWindow.updateStatus("New game has begun.");
                                }
                                
                                if(oppMove[0] != -1)
                                {
                                        move();
                                        System.out.print("\nWaiting for move to complete");
                                        while(!complete)
                                        {
                                                System.out.print(".");
                                        }
                                        System.out.println();
                                }
                                
                                if(chat!=null && !chat.equals(""))
                                {
                                    MainWindow.chat(chat, control.getOppUsername(), false);
                                }
                                
                                
                                MainWindow.updateScore(score);
                                MainWindow.updateOpp(control.getOppUsername());



                                currOpp = control.getOppID();

                                if(!prevOpp.equals(currOpp) && control.engaged())
                                {
                                    try { MainWindow.clearChat();}
                                    catch(Exception  e){System.out.println(e);}
                                    
                                    engaged();

                                    //clear and repopulate the board
                                    clearBoard();
                                    fillBoard();
                                }
                                else if(!prevOpp.equals(currOpp) && !control.engaged())
                                {
                                    disengaged();
                                    wasDisengaged = true;
                                    try { MainWindow.clearChat();}
                                    catch(Exception  e){System.out.println(e);}
                                    //clear the board
                                    clearBoard();
                                }
                                else if(wasDisengaged && control.engaged() && !control.gameOver())
                                {
                                    wasDisengaged = false;
                                    engaged();
                                    clearBoard();
                                    fillBoard();
                                    try { MainWindow.clearChat();}
                                    catch(Exception  e){System.out.println(e);}
                                    MainWindow.opponentLabel.setText(control.getOppUsername());
                                }

                                if(control.engaged() && !prevOpp.equals(currOpp))
                                {
                                    engaged();
                                    clearBoard();
                                    fillBoard();
                                    try { MainWindow.clearChat();}
                                    catch(Exception  e){System.out.println(e);}
                                    prevOpp = currOpp;
                                }


                                try
                                {
                                        TimeUnit.MILLISECONDS.sleep(500);
                                }
                                catch(Exception e)
                                {
                                        System.out.println(e);
                                }
                          }
                      }
	          });

		appThread.start();

      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent me)
                          {
                             // Obtain mouse coordinates at time of press.

                             int x = me.getX();
                             int y = me.getY();

                             // Locate positioned checker under mouse press.

                             for (PosCheck posCheck: posChecks)
                                if (Checker.contains(x, y, posCheck.cx, posCheck.cy))
                                {
                                   Board.this.posCheck = posCheck;
                                   oldcx = posCheck.cx;
                                   oldcy = posCheck.cy;
                                   deltax = x - posCheck.cx;
                                   deltay = y - posCheck.cy;
                                   inDrag = true;
                                   return;
                                }
                          }

                          @Override
                          public void mouseReleased(MouseEvent me)
                          {
                             // When mouse released, clear inDrag (to
                             // indicate no drag in progress) if inDrag is
                             // already set.

                             if (inDrag)
                                inDrag = false;
                             else
                                return;

                             // Snap checker to center of square.

                             int x = me.getX();
                             int y = me.getY();

                             posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM +
                                           SQUAREDIM / 2;
                             posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM +
                                           SQUAREDIM / 2;

							
							//convert coordinates
                             byte newX = 0;
                             byte newY = 0;
                             switch(posCheck.cx)
                             {
                                     case 31:
                                            newX = 0;
                                            break;
                                     case 93:
                                            newX = 1;
                                            break;
                                     case 155:
                                            newX = 2;
                                            break;
                                     case 217:
                                            newX = 3;
                                            break;
                                     case 279:
                                            newX = 4;
                                            break;
                                     case 341:
                                            newX = 5;
                                            break;
                                     case 403:
                                            newX = 6;
                                            break;
                                     case 465:
                                            newX = 7;
                             }

                             switch(posCheck.cy)
                             {
                                     case 31:
                                            newY = 0;
                                            break;
                                     case 93:
                                            newY = 1;
                                            break;
                                     case 155:
                                            newY = 2;
                                            break;
                                     case 217:
                                            newY = 3;
                                            break;
                                     case 279:
                                            newY = 4;
                                            break;
                                     case 341:
                                            newY = 5;
                                            break;
                                     case 403:
                                            newY = 6;
                                            break;
                                     case 465:
                                            newY = 7;
                             }

                              byte oldX = 0;
                              byte oldY = 0;
                              switch(oldcx)
                              {
                                     case 31:
                                            oldX = 0;
                                            break;
                                     case 93:
                                            oldX = 1;
                                            break;
                                     case 155:
                                            oldX = 2;
                                            break;
                                     case 217:
                                            oldX = 3;
                                            break;
                                     case 279:
                                            oldX = 4;
                                            break;
                                     case 341:
                                            oldX = 5;
                                            break;
                                     case 403:
                                            oldX = 6;
                                            break;
                                     case 465:
                                            oldX = 7;
                             }

                             switch(oldcy)
                             {
                                     case 31:
                                            oldY = 0;
                                            break;
                                     case 93:
                                            oldY = 1;
                                            break;
                                     case 155:
                                            oldY = 2;
                                            break;
                                     case 217:
                                            oldY = 3;
                                            break;
                                     case 279:
                                            oldY = 4;
                                            break;
                                     case 341:
                                            oldY = 5;
                                            break;
                                     case 403:
                                            oldY = 6;
                                            break;
                                     case 465:
                                            oldY = 7;
                             }

                             move = control.move(oldY, oldX, newY, newX);
                             isJump(oldY, oldX, newY, newX);

                            for (PosCheck posCheck: posChecks)
                                    if (!move)
                                    {
                                       Board.this.posCheck.cx = oldcx;
                                       Board.this.posCheck.cy = oldcy;
                                       move = true;
                                       
                                       if(!control.getMyTurn())
                                           MainWindow.updateStatus("It's not thy turn!");
                                    }
                                    else
                                    {
                                        MainWindow.updateStatus(" ");
                                    }
                             posCheck = null;
                             repaint();
                          }
                       });


      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
     {
        @Override
        public void mouseDragged(MouseEvent me)
        {
           if (inDrag)
           {
              // Update location of checker center.


              posCheck.cx = me.getX() - deltax;
              posCheck.cy = me.getY() - deltay;

              repaint();
           }
        }
     });

   }
   
   protected void fillBoard()
    {
        if(connected)
          {
              add(new Checker(CheckerType.RED_REGULAR), 5, 1);
              add(new Checker(CheckerType.RED_REGULAR), 5, 3);
              add(new Checker(CheckerType.RED_REGULAR), 5, 5);
              add(new Checker(CheckerType.RED_REGULAR), 5, 7);
              add(new Checker(CheckerType.RED_REGULAR), 6, 0);
              add(new Checker(CheckerType.RED_REGULAR), 6, 2);
              add(new Checker(CheckerType.RED_REGULAR), 6, 4);
              add(new Checker(CheckerType.RED_REGULAR), 6, 6);
              add(new Checker(CheckerType.RED_REGULAR), 7, 1);
              add(new Checker(CheckerType.RED_REGULAR), 7, 3);
              add(new Checker(CheckerType.RED_REGULAR), 7, 5);
              add(new Checker(CheckerType.RED_REGULAR), 7, 7);

              add(new Checker(CheckerType.BLACK_REGULAR), 0, 0);
              add(new Checker(CheckerType.BLACK_REGULAR), 0, 2);
              add(new Checker(CheckerType.BLACK_REGULAR), 0, 4);
              add(new Checker(CheckerType.BLACK_REGULAR), 0, 6);
              add(new Checker(CheckerType.BLACK_REGULAR), 1, 1);
              add(new Checker(CheckerType.BLACK_REGULAR), 1, 3);
              add(new Checker(CheckerType.BLACK_REGULAR), 1, 5);
              add(new Checker(CheckerType.BLACK_REGULAR), 1, 7);
              add(new Checker(CheckerType.BLACK_REGULAR), 2, 0);
              add(new Checker(CheckerType.BLACK_REGULAR), 2, 2);
              add(new Checker(CheckerType.BLACK_REGULAR), 2, 4);
              add(new Checker(CheckerType.BLACK_REGULAR), 2, 6);
          }
    }
   
    /**
     * Clears the board...obvs
     */
    protected void clearBoard()
    {
        //PosCheck posCheckA: posChecks)
        
        posChecks.clear();
    }
        
        /**
         * Call if opponent is lost
         */
        protected void disengaged()
        {
            MainWindow.newGame.setEnabled(false);
            MainWindow.drawGame.setEnabled(false);
            MainWindow.resignGame.setEnabled(false);
            MainWindow.sendChat.setEnabled(false);
        }
        
        /**
         * Call if opponent is found
         */
        protected void engaged()
        {
            MainWindow.newGame.setEnabled(false);
            MainWindow.drawGame.setEnabled(true);
            MainWindow.resignGame.setEnabled(true);
            MainWindow.sendChat.setEnabled(true);
        }
        
        /**
         * Call if game is over
         */
        protected void gameOver()
        {
            MainWindow.newGame.setEnabled(true);
            MainWindow.drawGame.setEnabled(false);
            MainWindow.resignGame.setEnabled(false);
            MainWindow.sendChat.setEnabled(true);
        }
		
	protected String getMyID()
	{
            return control.getMyID();
	}
        
        protected String getPrevOpp()
        {
            return prevOpp;
        }
        
        protected String getCurrOpp()
        {
            return currOpp;
        }

	protected void formWindowClosing(java.awt.event.WindowEvent evt) {
	       control.endAndExit();
    }

   public void add(Checker checker, int row, int col)
   {
      if (row < 0 || row > 7)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 0 || col > 7)
         throw new IllegalArgumentException("col out of range: " + col);
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;

      posCheck.cx = (col) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row) * SQUAREDIM + SQUAREDIM / 2;
      for (PosCheck _posCheck: posChecks)
         if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
            throw new AlreadyOccupiedException("square at (" + row + "," +
                                               col + ") is occupied");
      posChecks.add(posCheck);
   }
   
   protected void removePiece(Checker check, int row, int col)
   {
      int oldX = 0;
      int oldY = 0;
      switch(col)
      {
         case 0:
                oldX = 31;
                break;
         case 1:
                oldX = 93;
                break;
         case 2:
                oldX = 155;
                break;
         case 3:
                oldX = 217;
                break;
         case 4:
                oldX = 279;
                break;
         case 5:
                oldX = 341;
                break;
         case 6:
                oldX = 403;
                break;
         case 7:
                oldX = 465;
     }

     switch(row)
     {
         case 0:
                oldY = 31;
                break;
         case 1:
                oldY = 93;
                break;
         case 2:
                oldY = 155;
                break;
         case 3:
                oldY = 217;
                break;
         case 4:
                oldY = 279;
                break;
         case 5:
                oldY = 341;
                break;
         case 6:
                oldY = 403;
                break;
         case 7:
                oldY = 465;
     }

      for (PosCheck posCheckA: posChecks)
      {
            if (posCheckA.cx == oldX && posCheckA.cy == oldY)
            {
               posChecks.remove(posCheckA);
               break;
            }
      }
   }

    public boolean move()
    {
        boolean m = false;
        complete = false;
        MainWindow.updateStatus(" ");

        if (oppMove[0]==-1)
            return m;


        int newX = 0;
        int newY = 0;
        switch((int)oppMove[3])
        {
             case 0:
                    newX = 31;
                    break;
             case 1:
                    newX = 93;
                    break;
             case 2:
                    newX = 155;
                    break;
             case 3:
                    newX = 217;
                    break;
             case 4:
                    newX = 279;
                    break;
             case 5:
                    newX = 341;
                    break;
             case 6:
                    newX = 403;
                    break;
             case 7:
                    newX = 465;
        }

        switch((int)oppMove[2])
        {
             case 0:
                    newY = 31;
                    break;
             case 1:
                    newY = 93;
                    break;
             case 2:
                    newY = 155;
                    break;
             case 3:
                    newY = 217;
                    break;
             case 4:
                    newY = 279;
                    break;
             case 5:
                    newY = 341;
                    break;
             case 6:
                    newY = 403;
                    break;
             case 7:
                    newY = 465;
     }

      int oldX = 0;
      int oldY = 0;
      switch((int)oppMove[1])
      {
         case 0:
                oldX = 31;
                break;
         case 1:
                oldX = 93;
                break;
         case 2:
                oldX = 155;
                break;
         case 3:
                oldX = 217;
                break;
         case 4:
                oldX = 279;
                break;
         case 5:
                oldX = 341;
                break;
         case 6:
                oldX = 403;
                break;
         case 7:
                oldX = 465;
     }

     switch(oppMove[0])
     {
         case 0:
                oldY = 31;
                break;
         case 1:
                oldY = 93;
                break;
         case 2:
                oldY = 155;
                break;
         case 3:
                oldY = 217;
                break;
         case 4:
                oldY = 279;
                break;
         case 5:
                oldY = 341;
                break;
         case 6:
                oldY = 403;
                break;
         case 7:
                oldY = 465;
     }
     
     boolean thejump = false;
     if(oppMove[0] != -1)
        thejump = isJump(oppMove[0], oppMove[1], oppMove[2], oppMove[3]);
     
     System.out.println("The jump: " + thejump);

      for (PosCheck posCheckA: posChecks)
            if (posCheckA.cx == oldX && posCheckA.cy == oldY)
            {
               posChecks.remove(posCheckA);
               break;
            }

      PosCheck posCheck2 = new PosCheck();
      posCheck2.checker = new Checker(CheckerType.BLACK_REGULAR);

      posCheck2.cx = newX;
      posCheck2.cy = newY;

      posChecks.add(posCheck2);
      m = true;

      repaint();

      complete = true;

      return m;
   }
    
    
    protected boolean isJump(int prevA, int prevB, int currA, int currB)
	{
		boolean jump = false;
                
             
             System.out.println("prevA " + prevA + " prevB " + prevB + "currA " + currA + " currB " + currB);
             
            if(currA==prevA+2)
            {   
                    if(currB==prevB+2)
                    {
                        removePiece((new Checker(CheckerType.BLACK_REGULAR)), (prevA + 1), (prevB + 1));
                        jump = true;
                    }	
                    else if(currB==prevB-2)
                    {
                        removePiece((new Checker(CheckerType.BLACK_REGULAR)), (prevA + 1), (prevB - 1));
                        jump = true;
                    }
            }

            else if(currA==prevA-2)
            {
                    if(currB==prevB+2)
                    {
                        removePiece((new Checker(CheckerType.RED_REGULAR)), (prevA - 1), (prevB + 1));
                        jump = true;
                    }
                    else if(currB==prevB-2)
                    {
                        removePiece((new Checker(CheckerType.RED_REGULAR)), (prevA - 1), (prevB - 1));
                        jump = true;
                    }
            }

		return jump;
	}
    
    
   @Override
   public Dimension getPreferredSize()
   {
      return dimPrefSize;
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (PosCheck posCheck: posChecks)
         if (posCheck != Board.this.posCheck)
            posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

      // Draw dragged checker last so that it appears over any underlying
      // checker.

      if (posCheck != null && move)
         posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
   }

   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard.

      for (int row = 0; row < 8; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
         for (int col = 0; col < 8; col++)
         {
            g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
            g.setColor((g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
         }
      }
   }

   // positioned checker helper class

   private class PosCheck
   {
      public Checker checker;
      public int cx;
      public int cy;
   }
}
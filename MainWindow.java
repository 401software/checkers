import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class MainWindow extends JFrame {
    
    private static Board board;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        
        this.setTitle("Checkerz");
        initComponents();
        
        remove_title_bar();
        setVisible(true);
        
        try
        {
            board = new Board(this);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        boardFrame.setContentPane(board);
    }
    
    void remove_title_bar(){
        boardFrame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI)boardFrame.getUI()).setNorthPane(null);
        boardFrame.setBorder(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        newOpp = new javax.swing.JButton();
        newGame = new javax.swing.JButton();
        drawGame = new javax.swing.JButton();
        resignGame = new javax.swing.JButton();
        NEW_OPP_LABEL = new javax.swing.JLabel();
        NEW_GAME_LABEL = new javax.swing.JLabel();
        DRAW_LABEL = new javax.swing.JLabel();
        RESIGN_LABEL = new javax.swing.JLabel();
        SCORE_LABEL = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        OPPONENT_LABEL = new javax.swing.JLabel();
        opponentLabel = new javax.swing.JLabel();
        TURN_LABEL = new javax.swing.JLabel();
        turnLabel = new javax.swing.JLabel();
        chatPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        titleBar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatDisplay = new javax.swing.JTextArea();
        CHAT_LABEL = new javax.swing.JLabel();
        chatType = new javax.swing.JTextField();
        sendChat = new javax.swing.JButton();
        boardFrame = new javax.swing.JInternalFrame();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        buttonPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(199, 11, 11)));

        newOpp.setText("Get");
        newOpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOppActionPerformed(evt);
            }
        });

        newGame.setText("Request");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        drawGame.setText("Draw");
        drawGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawGameActionPerformed(evt);
            }
        });

        resignGame.setText("Resign");
        resignGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resignGameActionPerformed(evt);
            }
        });

        NEW_OPP_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NEW_OPP_LABEL.setText("New Opponent");

        NEW_GAME_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NEW_GAME_LABEL.setText("New Game");

        DRAW_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DRAW_LABEL.setText("Offer Draw");

        RESIGN_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RESIGN_LABEL.setText("Resign from Game");

        SCORE_LABEL.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        SCORE_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SCORE_LABEL.setText("Your Score");

        scoreLabel.setFont(new java.awt.Font("Noto Sans", 2, 12)); // NOI18N
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("0");

        OPPONENT_LABEL.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        OPPONENT_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OPPONENT_LABEL.setText("Your Opponent");

        opponentLabel.setFont(new java.awt.Font("Noto Sans", 2, 10)); // NOI18N
        opponentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opponentLabel.setText("<not paired>");

        TURN_LABEL.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        TURN_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TURN_LABEL.setText("-Player's Turn-");

        turnLabel.setFont(new java.awt.Font("Noto Sans", 2, 10)); // NOI18N
        turnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnLabel.setText("<game over>");
        turnLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NEW_GAME_LABEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(DRAW_LABEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(RESIGN_LABEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NEW_OPP_LABEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TURN_LABEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newOpp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(drawGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resignGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SCORE_LABEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OPPONENT_LABEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(opponentLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(TURN_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(turnLabel)
                .addGap(18, 18, 18)
                .addComponent(SCORE_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreLabel)
                .addGap(18, 18, 18)
                .addComponent(OPPONENT_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opponentLabel)
                .addGap(33, 33, 33)
                .addComponent(NEW_OPP_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newOpp)
                .addGap(8, 8, 8)
                .addComponent(NEW_GAME_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newGame)
                .addGap(11, 11, 11)
                .addComponent(DRAW_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawGame)
                .addGap(9, 9, 9)
                .addComponent(RESIGN_LABEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resignGame)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        titlePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(196, 26, 26)));

        statusLabel.setBackground(new java.awt.Color(252, 28, 28));
        statusLabel.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(21, 9, 9));
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Currently Disconnected from the Game Server.");
        statusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        titleBar.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        titleBar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleBar.setText("The Game of Czechers");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel))
        );

        chatDisplay.setEditable(false);
        chatDisplay.setColumns(20);
        chatDisplay.setRows(5);
        chatDisplay.setWrapStyleWord(true);
        chatDisplay.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(chatDisplay);

        CHAT_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CHAT_LABEL.setText("Chat");

        chatType.setText("<Chat with your opponent>");
        chatType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                chatTypeFocusGained(evt);
            }
        });
        chatType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatTypeActionPerformed(evt);
            }
        });

        sendChat.setText("Send");
        sendChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendChatActionPerformed(evt);
            }
        });

        boardFrame.setVisible(true);

        javax.swing.GroupLayout boardFrameLayout = new javax.swing.GroupLayout(boardFrame.getContentPane());
        boardFrame.getContentPane().setLayout(boardFrameLayout);
        boardFrameLayout.setHorizontalGroup(
            boardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        boardFrameLayout.setVerticalGroup(
            boardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chatType, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(CHAT_LABEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boardFrame))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CHAT_LABEL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chatType)
                            .addComponent(sendChat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chatTypeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chatTypeFocusGained
        chatType.selectAll();
    }//GEN-LAST:event_chatTypeFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        board.formWindowClosing(evt);
    }//GEN-LAST:event_formWindowClosing

    private void chatTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatTypeActionPerformed
        
        String message;
        
        if(board.control.engaged())
        {
            message = chatType.getText();
            chat(message, "Me", true);
        }
    }//GEN-LAST:event_chatTypeActionPerformed

    private void sendChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendChatActionPerformed
        chatTypeActionPerformed(evt);
    }//GEN-LAST:event_sendChatActionPerformed

    private void newOppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOppActionPerformed
        board.control.newOpponent();
    }//GEN-LAST:event_newOppActionPerformed

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        board.control.newGame();
    }//GEN-LAST:event_newGameActionPerformed

    private void drawGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawGameActionPerformed
        board.control.draw();
        statusLabel.setText("You have offered a draw to opponent.");
    }//GEN-LAST:event_drawGameActionPerformed

    private void resignGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resignGameActionPerformed
        board.control.resign();
        statusLabel.setText("You have resigned from the game. Opponent winz. #NotLit");
    }//GEN-LAST:event_resignGameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
    
    /**
     * Accessor that allows outside classes to clear the chat if paired with a new opponent 
     */
    protected static void clearChat()
    {
        if(!board.getPrevOpp().equals(board.getCurrOpp()))
            {
                chatDisplay.selectAll();
                chatDisplay.setText("");
                
                if(!chatType.getText().equals("<Chat with your opponent>"))
                {
                    chatType.selectAll();
                    chatType.setText("<Chat with your opponent>");
                }
            }
    }
    
    /**
     * Allows an outside class to append text to the chat display
     * @param text to append
     * @param name of chatter
     * @param true if sending false if recieving 
     */
    protected static void chat(String message, String name, boolean send)
    {
        if(!message.equals("") && message != null && !message.equals("<Chat with your opponent>"))
        {
            String history = chatDisplay.getText();
            String display = history + "\n" + name + ": " + message;
            
            if(chatDisplay.getText().equals("") || chatDisplay.getText().equals(" "))
                display = name + ": " + message;

            chatDisplay.selectAll();
            chatDisplay.setText(display);
            chatDisplay.setCaretPosition(chatDisplay.getText().length());

            if(send)
            {
                board.control.sendMessage(message);
                chatType.selectAll();
                chatType.setText("");
            }
        }
    }
    
    /**
     * Accessor to update the status bar from outside of this class
     * @param status update string
     */
    protected static void updateStatus(String update)
    {
        statusLabel.setText(update);
    }
    
    /**
     * Accessor to update the turn status from outside of the class
     * @param true if current player's turn, false if opponent's turn
     */
    protected static void updateTurn(String turn)
    {
        turnLabel.setText(turn);
    }
    
    /**
     * Accessor for outside classes to update score
     * @param the score 
     */
    protected static void updateScore(int score)
    {
        scoreLabel.setText("" + score);
    }
    
    /**
     * Accessor for outside classes to update the opponent ID
     * @param opponent id
     */
    protected static void updateOpp(String id)
    {
        opponentLabel.setText(id);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CHAT_LABEL;
    private javax.swing.JLabel DRAW_LABEL;
    private javax.swing.JLabel NEW_GAME_LABEL;
    private javax.swing.JLabel NEW_OPP_LABEL;
    private javax.swing.JLabel OPPONENT_LABEL;
    private javax.swing.JLabel RESIGN_LABEL;
    private javax.swing.JLabel SCORE_LABEL;
    private javax.swing.JLabel TURN_LABEL;
    private javax.swing.JInternalFrame boardFrame;
    private javax.swing.JPanel buttonPanel;
    private static javax.swing.JTextArea chatDisplay;
    private javax.swing.JPanel chatPanel;
    private static javax.swing.JTextField chatType;
    protected static javax.swing.JButton drawGame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    protected static javax.swing.JButton newGame;
    private javax.swing.JButton newOpp;
    protected static javax.swing.JLabel opponentLabel;
    protected static javax.swing.JButton resignGame;
    protected static javax.swing.JLabel scoreLabel;
    protected static javax.swing.JButton sendChat;
    private static javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleBar;
    private javax.swing.JPanel titlePanel;
    private static javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class tttGUISettings extends JFrame implements ActionListener{
	/**
	 * Class variables
	 */
	//the actual game
	public static ttt game = new ttt();
	
	//current scores
	private int scoreX = 0, scoreY = 0;
	
	//grid of buttons
	private JButton [][] buttons = new JButton[3][3];
	
	//other buttons
	private JButton restart, mainmenu;
	
	//score and player text area
	private JTextPane curScore;
	
	/**
	 * Class constructor
	 */
	public tttGUISettings(){
		//set up the grid layout
		setLayout(new GridLayout(4, 3));
		
		//set up the buttons and action events
		initButtons();
		
		//show the current player and score
		curScore = new JTextPane();
		curScore.setText("X's turn\n\nScore\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
		curScore.setEditable(false);
		add(curScore);
		
		//restart the game
		restart = new JButton("Restart game (HvH)");
		restart.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	//restart of the same type of game (HvH in this case)
            	//and reset all the buttons
            	game = new ttt();
            	resetButtons();
            }
        });
		add(restart);
		
		//return to main menu (tttRunner)
		//to choose between HvH and HvC
		mainmenu = new JButton("<< Back to main menu");
		mainmenu.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	tttRunner menu = new tttRunner();
            	menu.showMenu();
            }
        });
		add(mainmenu);
	}
	
	/** 
	 * Override actionPerformed()
	 * ==========================
	 * Assigns all methods and events that must be completed 
	 * upon clicking a button on the playing grid.
	 * 
	 * @param	ActionEvent	e
	 * @return	void
	 */
	public void actionPerformed(ActionEvent e)
	{
		//loop through the buttons to determine which was clicked
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				//buttons[i][j] was clicked
				if(e.getSource()==buttons[i][j])
				{
					//play the move
					game.playMove(i, j);
					
					//update the GUI to display the user's token
					buttons[i][j].setText(String.valueOf(game.getCurPlayer()));
					
					//swap to the next player
					game.swapPlayer();
					
					//update the label showing the current player
					String label = curScore.getText();
					label = String.valueOf(game.getCurPlayer()) + "'s turn " + label.substring(8);
					curScore.setText(String.valueOf(label));
					
					//disable the button, not allowing the user to play this cell again
					buttons[i][j].setEnabled(false);
					
					//check for a winner (X or O)
					if(game.hasWinner() != game.EMPTY){
						//update scoreboard
						if(game.hasWinner() == game.X){
							scoreX++;
						}
						else scoreY++;
						
						//alert the winner
						game.printWinner();
						
						//remove the current player and just show the score since the game is over
						curScore.setText("Score\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
						
						//disable all the buttons to indicated game over
						disableAllButtons();
						
						//swapped before, so need to undo swap to print the correct winner's name
						game.swapPlayer();
						
						//set the winner on the screen
						label = curScore.getText();
						label = String.valueOf("Player " + game.getCurPlayer()) + " won this round! " + label.substring(8);
						curScore.setText(String.valueOf(label));
					}
					//check for a tie
					else if(game.hasWinner() == game.EMPTY && !game.hasEmpty()){
						//alert the winner
						game.printWinner();
						
						//remove the current player and just show the score since the game is over
						curScore.setText("Score\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
						
						//disable all the buttons to indicated game over
						disableAllButtons();
						
						//set the winner on the screen
						label = curScore.getText();
						label = "Tie!\n" + label.substring(8);
						curScore.setText(String.valueOf(label));
					}
				}
			}
		}
	}
	
	/** 
	 * initButtons()
	 * ==========================
	 * Create an array of all the buttons and add action listeners to them
	 * 
	 * @param	none
	 * @return	void
	 */
	public void initButtons(){
		//loop through 3x3 array
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				//create button, add to GUI, add action listener
				buttons[i][j] = new JButton("");
				add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
			}
		}
	}
	
	/** 
	 * resetButtons()
	 * ==========================
	 * Reset all buttons to default state by:
	 * 		- setting the text to empty
	 * 		- enabling clicking
	 * 		- defaulting the scoreboard to be X's turn
	 * 
	 * @param	none
	 * @return	void
	 */
	public void resetButtons(){
		//reset text
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				buttons[i][j].setText("");
			}
		}
		
		//make buttons clickable again
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				buttons[i][j].setEnabled(true);
			}
		}
		
		//reset scoreboard text
		curScore.setText("X's turn\n\nScore\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
	}
	
	/** 
	 * disableAllButtons()
	 * ==========================
	 * Disable all buttons on the 3x3 game grid
	 * Used when game has been won
	 * 
	 * @param	none
	 * @return	void
	 */
	public void disableAllButtons(){
		//disable all buttons
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				buttons[i][j].setEnabled(false);
			}
		}
	}
}

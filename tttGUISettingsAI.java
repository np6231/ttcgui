

//import java library
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class tttGUISettingsAI extends JFrame implements ActionListener{
	/** 
	 * Class variables 
	 */
	//the actual game
	public static tttBT game = new tttBT();
	
	//current scores accumulator
	private int scoreX = 0, scoreY = 0;
	
	//array of buttons
	private JButton [][] buttons = new JButton[3][3];
	
	//buttons and texts for other GUI components
	private JButton restart, mainmenu;
	private JTextPane curScore;
	
	/** 
	 * Class constructor 
	 */
	public tttGUISettingsAI(){
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
		restart = new JButton("Restart game");
		restart.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
            	//restart of the same type of game (AI in this case)
            	//and reset all the buttons
            	game = new tttBT();
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
					//play the user move
					game.makeMove(i, j, game.XAI);
					
					//update the GUI to display the user's token (always an X)
					buttons[i][j].setText("X");
					
					//update the label showing the current player
					String label = curScore.getText();
					label = String.valueOf("X's turn " + label.substring(8));
					curScore.setText(String.valueOf(label));
					
					//disable the button, not allowing the user to play this cell again
					buttons[i][j].setEnabled(false);
					
					//check for a winner (at this point, would be the user/X)
					boolean finishedGame = determineWinner();

					//no winner means the computer can make its move
					if(!finishedGame){
						//determine where to play
						spot compMove = game.getCompMove(game.OAI);

						//play the determined move
						game.makeMove(compMove.x,compMove.y, game.OAI);
						
						//show the move in the GUI
						showAIMove(compMove.x, compMove.y);
						
						//check again for winner
						if(game.winner() != game.EMPTYAI){
							determineWinner();
						}
					}
				}
			}
		}
	}
	
	/** 
	 * determineWinner()
	 * ==========================
	 * Determine if there is a winner, and who that player is
	 * 
	 * @param	ActionEvent	e
	 * @return	boolean		true if there has been a winner or a tie
	 * 						false otherwise
	 */
	public boolean determineWinner(){
		String label;
		//user winner
		if(game.winner() == game.XAI){
			//update scoreboard
			scoreX++;
			label = curScore.getText();
			
			//set the winner on the screen
			label = String.valueOf("You won this round! " + label.substring(8));
			curScore.setText(String.valueOf(label));
			
			//alert the winner
			game.printWinner();
			
			//remove the current player and just show the score since the game is over
			curScore.setText("Score\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
			
			//disable all the buttons to indicated game over
			disableAllButtons();
			
			return true;
		}
		//computer winner
		else if(game.winner() == game.OAI){
			//update scoreboard
			scoreY++;
			label = curScore.getText();
			
			//set the winner on the screen
			label = String.valueOf("The computer won this round! " + label.substring(8));
			curScore.setText(String.valueOf(label));
			
			//alert the winner
			game.printWinner();
			
			//remove the current player and just show the score since the game is over
			curScore.setText("Score\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
			
			//disable all the buttons to indicated game over
			disableAllButtons();
			
			return true;
		}
		//tie
		else if(game.getNumMoves() == 9){
			label = curScore.getText();
			
			//set the winner on the screen
			label = String.valueOf("This round was a tie! " + label.substring(8));
			curScore.setText(String.valueOf(label));
			
			//alert the winner
			game.printWinner();
			
			//remove the current player and just show the score since the game is over
			curScore.setText("Score\n============\nX:\t " + scoreX + "\nO:\t " + scoreY);
			
			//disable all the buttons to indicated game over
			disableAllButtons();
			
			return true;
		}
		
		return false;
	}

	/** 
	 * initButtons()
	 * ==========================
	 * Assigns all methods and events that must be completed 
	 * upon clicking a button on the playing grid.
	 * 
	 * @param	ActionEvent	e
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
	
	/** 
	 * showAIMove()
	 * ==========================
	 * Disable all buttons on the 3x3 game grid
	 * Used when game has been won
	 * 
	 * @param	int		x
	 * 			int		y
	 * @return	void
	 */
	public void showAIMove(int x, int y){
		//set the computer's move in the GUI
		buttons[x][y].setText("O");
		
		//disable the button
		buttons[x][y].setEnabled(false);
	}
}


import java.io.*;
import java.util.*;
import javax.swing.*;

public class ttt {
	/** 
	 * Class variables 
	 */
    
	//store the game board
	private int [][] board = new int[3][3];
	
    //the following three variables (X, O, and EMPTY) are public for
    //access in the tester client GUI
	public static final int X = 1;
	public static final int O = 0;
	public static final int EMPTY = -1;
	
    //this stores the current player, used to print
	private char CURPLAYER = 'X';
    
    //size of the game board is 3x3
	private static final int SIZE = 3;
    
	/** 
	 * Class constructor 
	 */
	public ttt(){
		//initialize the board to be empty
		board = initBoard();
	}
	
	/** 
	 * initBoard()
	 * ==========================
	 * Initializes a 3x3 playing grid, represented by
	 * a 2d int array, to be empty 
	 * 
	 * @param	none
	 * @return	int [][]	board
	 */
	public int [][] initBoard(){
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				board[i][j] = EMPTY;
		
		return board;
	}
	
	/** 
	 * hasEmpty()
	 * ==========================
	 * Determines whether or not there are any possible moves left
	 * Used in combination with hasWinner() to determine if gameplay should continue
	 * 
	 * @param	none
	 * @return	boolean	true if board has at least one empty cell
	 * 					false otherwise
	 */
	public boolean hasEmpty(){
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				//if there's at least one empty cell left, the game can't be over yet
				if(board[i][j] == EMPTY) return true;
		return false;
	}
	
	/** 
	 * printWinner()
	 * ==========================
	 * Prints the winner in proper formatting in an alert window
	 * 
	 * @param	none
	 * @return	void
	 */
	public void printWinner(){
		if(hasWinner() == X){
			JOptionPane.showMessageDialog(null, "Player X wins!");
		}
		else if(hasWinner() == O){
			JOptionPane.showMessageDialog(null, "Player O wins!");
		}
		else{
			JOptionPane.showMessageDialog(null, "Tie!");
		}
	}
	
	/** 
	 * hasWinner()
	 * ==========================
	 * Determines who has won the game at this point in time
	 * 
	 * @param	none
	 * @return	int		the constant representing the current winner 
	 * 					(X, O, or EMPTY)
	 */
	public int hasWinner()
	{
		//check rows
		for (int i=0; i<SIZE; i++)
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTY)
				return board[i][0];
				
		//check cols
		for (int i=0; i<SIZE; i++)
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTY)
				return board[0][i];
				
		//check diags
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTY)
			return board[0][0];
		if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != EMPTY)
			return board[2][0];
			
		//else no winner yet or tie
		return EMPTY;
	}
	
	/** 
	 * swapPlayer()
	 * ==========================
	 * Swaps between players X and O
	 * 
	 * @param	none
	 * @return	void
	 */
	public void swapPlayer(){
		if(CURPLAYER == 'O') CURPLAYER = 'X';
		else CURPLAYER = 'O';
	}

	/** 
	 * getCurPlayer()
	 * ==========================
	 * Returns the value of the current player in the game
	 * 
	 * @param	none
	 * @return	char	CURPLAYER
	 */
	public char getCurPlayer(){
		return CURPLAYER;
	}
	
	/** 
	 * playMove()
	 * ==========================
	 * Actually stores the chosen play in the board
	 * 
	 * @param	int		row
	 * @param	int		col
	 * @return	void
	 * 
	 * @pre		row and col represents a valid cell, as determined by validMove()
	 */
	public void playMove(int row, int col){
		if(CURPLAYER == 'X') board[row][col] = X;
		else board[row][col] = O;
	}
	
	
	/* The following methods are used for debugging or console purposes */
	
	/** 
	 * printBoard()
	 * ==========================
	 * Prints the gameboard in proper formatting
	 * Not used for actual GUI game, but useful in debugging gameplay
	 * 
	 * @param	none
	 * @return	void
	 */
	public void printBoard(){     
        //segment 1
		printDiv(true);
		printSpacer();
		printRow(0);
		
        //segment 2
		printDiv(false);
		printSpacer();
		printRow(1);
		
        //segment 3
		printDiv(false);
		printSpacer();
		printRow(2);
		printDiv(false);
		
	}
	
	/** 
	 * printDiv()
	 * ==========================
	 * Prints the divider between segments of the board
	 * If it's the top row, print the labels for easy reference
	 * Not used for actual GUI game, but useful in debugging gameplay
	 * 
	 * @param	int		true if printing the very first line of the board
	 * 					false otherwise
	 * @return	void
	 */
	private void printDiv(boolean top){
        //print column labels for first set
		if(top) System.out.println("\n" + "     0          1          2      \n" + "----------------------------------");
		else System.out.println("|----------|----------|----------|");
	}
	
	/** 
	 * printSpacer()
	 * ==========================
	 * Prints the spaces on empty lines of the board
	 * Not used for actual GUI game, but useful for debugging gameplay
	 * 
	 * @param	none
	 * @return	void
	 */
	private void printSpacer(){
		System.out.println("|          |          |          |");
	}
	
	/** 
	 * printRow()
	 * ==========================
	 * Prints the actual pieces currently on the board
	 * Not used for actual GUI game, but useful for debugging gameplay
	 * 
	 * @param	int		row number
	 * @return	void
	 */
	private void printRow(int rowNum){
		//every row starts off with a divider
		System.out.print("|");
		for(int i = 0; i < SIZE; i++)
		{
			//then a spacer
			System.out.print("    ");
			
			//print out the correct character for each piece in the row
			if(board[rowNum][i] == X) System.out.print("X");
			else if(board[rowNum][i] == O) System.out.print("O");
			else System.out.print(" ");

			//finish the cell with space and a divider
			System.out.print("     |");
		}
		//finish each segment with the row's number for easy reference
		System.out.println(" " + rowNum);
	}

	/** 
	 * validMove()
	 * ==========================
	 * Determines whether or not a move is valid.
	 * Not used for actual GUI game, but useful for debugging game play via console
	 * 
	 * @param	int		row
	 * @param	int		col
	 * @return	boolean	true if the cell [x][y] is inbounds and empty
	 * 					false otherwise
	 */
	public boolean validMove(int row, int col){
		if(row < 0 || col < 0 || row > SIZE || col > SIZE || board[row][col] != EMPTY) {
			return false;
		}
		else return true;
	}
}


/*

GAME BOARD SETUP
   
   0   1   2
0 [], [], []
1 [], [], []
2 [], [], []

*/
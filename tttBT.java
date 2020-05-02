//import java libraries
import java.io.*;
import java.util.*;
import javax.swing.*;

public class tttBT {
	/** 
	 * Class variables 
	 */
    //store the game board
    private int[][] board = new int[3][3];
    
    //keep track of how many moves have been played (9 means the game is over)
	private int numMoves;
	
    //the following three variables (XAI, OAI, and EMPTYAI) are public for access in the tester client GUI
	//these are different than in the HvH version because of the implementation of min-max trees and backtracking
    public static final int XAI = -1;
    public static final int OAI = 1;
	public static final int EMPTYAI = 0;
    
    //size of the game board is 3x3
	private static final int SIZE = 3;
	
	/** 
	 * Class constructor 
	 */
	public tttBT()
	{
		//initialize board to be empty
		board = initBoard();
		//initialize moves to be zero
        numMoves = 0;
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
				board[i][j] = EMPTYAI;
		
		return board;
	}

	/** 
	 * makeMove()
	 * ==========================
	 * Determines if a given move is valid and, if so, plays that move
	 * 
	 * @param	int		r
	 * @param	int		c
	 * @param	int		move
	 * @return	boolean	true if the move was made
	 * 					false otherwise
	 */
	public boolean makeMove(int r, int c, int move)
	{
		if(!validMove(r, c)) return false;
        board[r][c] = move;
		numMoves++;
		return true;
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
		if(row < 0 || col < 0 || row > 2 || col > 2 || board[row][col] != EMPTYAI) {
			return false;
		}
		else return true;
	}
    
	/** 
	 * undoMove()
	 * ==========================
	 * Undoes the most recent move, in order to step back into the backtracking
	 * 
	 * @param	int		row
	 * @param	int		col
	 * @return	void
	 */
	public void undoMove(int r, int c)
	{
		numMoves--;
		board[r][c] = EMPTYAI;
	}
    
	/** 
	 * getNumMoves()
	 * ==========================
	 * Returns how many moves completed in the game thus far
	 * (also known as the number of pieces on the board)
	 * 
	 * @param	none
	 * @return	int		numMoves
	 */
	public int getNumMoves()
	{
		return numMoves;
	}
	
	/** 
	 * getCompMove()
	 * ==========================
	 *Determines if chosen cell [x][y] is a valid move (ie: both inbounds and empty)
	 * 
	 * @param	int		cp		the computer's play
	 * @return	spot	an object containing the [x][y] coordinates of the cell
	 */
	public spot getCompMove(int cp)
	{
		int min = Integer.MIN_VALUE;
		spot nextMove;
		spot bestMove = new spot(-1,-1,-1);
		
		//try each possible move from this state of the board
        //always start at (1, 1) since it is the most valuable cell
		for (int i=1; i<=SIZE; i++)
			for (int j=1; j<=SIZE; j++)
			{
				//if this cell isn't a valid next move, try the next
				if (!makeMove(i%SIZE,j%SIZE,cp))
					continue;
				
				//figure out who would win if we decided to make this move
				int result = winner();
				
				//if this results in a draw or a win for the current player
                //it's the best possible move from this location
				if (result==cp || numMoves==SIZE*SIZE)
				{
					undoMove(i%SIZE,j%SIZE);
					return new spot(i%SIZE,j%SIZE,result);
				}
				
				//figure out what the opponent's (human's) best case could be
                //from this point in the game
				nextMove = getCompMove(-cp); //*-1 because min-max tree is opposite for opponent
				
				//if this is the best situation for us so far, we'll take it
				if (nextMove.score*cp > min)
				{
					min = nextMove.score*cp;
					bestMove = new spot(i%SIZE,j%SIZE,min);
				}
                //undo the move we just played so we get back to the original posed game state
				undoMove(i%SIZE,j%SIZE);
			}
			
			//return the determined cell
			return new spot(bestMove.x,bestMove.y,min*cp);	
	}
	
	/** 
	 * winner()
	 * ==========================
	 * Determines who has won the game at this point in time
	 * 
	 * @param	none
	 * @return	int		the constant representing the current winner (XAI, OAI, or EMPTYAI)
	 */
	public int winner()
	{
		//check rows
		for (int i=0; i<3; i++)
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != EMPTYAI)
				return board[i][0];
        
		//check cols
		for (int i=0; i<3; i++)
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != EMPTYAI)
				return board[0][i];
        
		//check diags
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != EMPTYAI)
			return board[0][0];
		if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != EMPTYAI)
			return board[2][0];
        
		//else no winner yet or tie
		return EMPTYAI;
	}
	
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
			if(board[rowNum][i] == XAI) System.out.print("X");
			else if(board[rowNum][i] == OAI) System.out.print("O");
			else System.out.print(" ");
			
			//finish the cell with space and a divider
			System.out.print("     |");
		}
		//finish each segment with the row's number for easy reference
		System.out.println(" " + rowNum);
        
	}
	
	/** 
	 * getPieceName()
	 * ==========================
	 * Maps the constants of each piece to a character
	 * 
	 * @param	int		piece
	 * @return	String	the character representation of a piece
	 */
	public static String getPieceName(int piece)
	{
		if (piece == EMPTYAI)
			return " ";
		else if (piece == XAI)
			return "X";
		return "O";
		
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
		if(winner() == XAI){
			JOptionPane.showMessageDialog(null, "You win!");
		}
		else if(winner() == OAI){
			JOptionPane.showMessageDialog(null, "The computer wins!");
		}
		else if(winner() == EMPTYAI && numMoves == 9){
			JOptionPane.showMessageDialog(null, "Tie!");
		}
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
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				//if there's at least one move left, the game can't be over yet
				if(board[i][j] == EMPTYAI) return true;
		return false;
	}

}

/**
 * Spot Class
 * ==========
 * Allows for easy storage of a cell with
 * corresponding scores for the AI at a particular state
 */
class spot {
	/** 
	 * Class variables: public for easier access in the runner
	 */
	public int x, y, score;
	
	/** 
	 * Class constructor: default
	 */
	public spot (int xx, int yy, int r)
	{
		x = xx;
		y = yy;
		score = r;
	}

	/** 
	 * Class constructor: overloaded - defaults score to zero
	 */
	public spot(int xx, int yy)
	{
		this(xx,yy,0);
	}
}
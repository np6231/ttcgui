import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class tttRunner extends JFrame {
	/** 
	 * Class variables 
	 */
	//menu
	public static tttRunner menu = new tttRunner();
	
	//buttons to choose the HvH or HvC game
	public JButton hvhB, hvcB;
	
	/** 
	 * Class constructor 
	 */
	public tttRunner(){
		//set up the grid layout
		setLayout(new GridLayout(2, 1));
		
		//set up the buttons for HvH and HvC play
		hvhB = new JButton("Play Human v Human");
		hvcB = new JButton("Play Human v Computer");
		
		//add action listener for HvH button
		hvhB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	//set up new HvH game
            	tttGUISettings hvh = new tttGUISettings();
            	
            	//set frame attributes
    			hvh.setTitle("Tic Tac Toe  |  Human v Human");
    			hvh.setSize(500, 600);
    			hvh.setLocationRelativeTo(null);
    			hvh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			hvh.setVisible(true);
            }
        });
		add(hvhB);
		
		//add action listener for HvC button
		hvcB.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	//set up new HvC game
            	tttGUISettingsAI hvc = new tttGUISettingsAI();
            	
            	//set frame attributes
    			hvc.setTitle("Tic Tac Toe  |  Human v AI");
    			hvc.setSize(500, 600);
    			hvc.setLocationRelativeTo(null);
    			hvc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			hvc.setVisible(true);
            }
        });
		add(hvcB);
	}
	
	/** 
	 * showMenu()
	 * ==========================
	 * Show the tttRunner GUI, which is a menu to
	 * choose between HvH gameplay and HvC gameplay
	 * 
	 * @param	none
	 * @return	void
	 */
	public void showMenu(){
		//set frame attributes
		menu.setTitle("Tic Tac Toe");
		menu.setSize(500, 600);
		menu.setLocationRelativeTo(null);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setVisible(true);
	}
    
	/** 
	 * main()
	 * ==========================
	 * Initialize the tttRunner menu
	 */
	public static void main(String [] args) throws IOException{
		//show the menu to choose between HvH gameplay and HvC gameplay
		menu.showMenu();
	}
}

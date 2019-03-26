package com.kueche.start;
/**
 * Bild Quellen http://clipart-library.com
 */
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import com.kueche.GameBoard.GameBoard;

public class StartGame extends JFrame{

	public StartGame() {
		this.add(new GameBoard());
        this.setResizable(false);	//Sets whether this frame is resizable by the user.
        this.pack();				// The pack() method causes this window to be sized to fit the preferred size and layouts of its children
        this.setTitle("Escape Küche");
        this.setLocationRelativeTo(null); 
        this.setPreferredSize(new Dimension(1024, 768));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EventQueue.invokeLater(() -> {
        	StartGame gameFrame = new StartGame();
        	gameFrame.setVisible(true);
        });
	}

}

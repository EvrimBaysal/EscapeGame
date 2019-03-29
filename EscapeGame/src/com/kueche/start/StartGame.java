package com.kueche.start;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.kueche.GameBoard.GameBoard;
import com.kueche.persistenz.MyInterface;
import com.kueche.sachen.Gegenstand;
import com.kueche.sachen.Tuer;

/**
 *  @author Evrim Baysal
 * <pre>
 *
 * 
 * 03.2019
 * Abschlussprojekt Alfatraining Java Kurs
 * Projekt ist ein Escape Game in Java geschrieben
 * um das Spiel gewonnen, mas muss 7 verstekte Schlüssel finden und der Tür öffnen.
 * in GameKonfig.txt kann man Positionen und bilder von Schlüssel bearbeiten.
 * 
 * Arrays = class GameBoard -- private boolean[] istInventarFrei 
 * 							-- public void initGegenstande() -- new Tuer(new String[]
 * 
 * dynamischen Arrays = class GameBoard -- private List[Gegenstand] gegenstande
 * 				        public class Tuer -- private List[BufferedImage] bilder
 * 
 * Vererbung = public class Kasten extends Gegenstand
 * 
 * Polymorphismus Beispiel = Kasten Objekte in dynamischen Array List[Gegenstand] gegenstande geschrieben
 * 
 *  Interface = public interface MyInterface
 *  				if(ae.getSource() == hilfeButton) {
 *						for(MyInterface blink : gegenstande) {
 *							blink.blinken();
 *						}
 *					}
 *					MyInterface blink = tuer;
 *					blink.blinken();
 *					
 * Überschreiben der aus „Object“ geerbten Methoden = public class Gegenstand -- return name
 *																public class Kasten -- return "Kasten" 
 * 
 * Verwendung der Persistenzschicht = class Leser liest die Datei GameKonfig.txt
 * 
 * Verwendung der Präsentationsschicht = public class GameBoard extends JPanel
 * 
 * Verwendung von Enums = public enum Inventar 
 * 
 * Verwendung von geeigneten eigenen Exceptions = public class SacheNichtInBoardException
 * 
 * Bild Quellen http://clipart-library.com und selbst erstellt
 * </pre>
 */

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

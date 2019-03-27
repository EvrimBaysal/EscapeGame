package com.kueche.sachen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import com.kueche.persistenz.MyInterface;

/**
 * 
 * @author Evrim Baysal
 * Klasse Gegenstand ist die Ursprüngliche Klasse für die Sachen in das Spiel
 * Es enthält die Informationen Position, Masse und Bild
 *
 */
public class Gegenstand extends JButton implements MyInterface{

	protected int posX;	//X-Werte der Position von Gegenstand
	protected int posY;	//Y-Werte der Position von Gegenstand
	protected int hoehe;	//Höhe von Gegenstand
	protected int breite;	//Breite von Gegenstand
	protected boolean sichtbar;	//Sichtbarkeit von Gegenstand
	protected BufferedImage bild;	//Bild von Gegenstand
	protected BufferedImage ausgewaehlteBild;	//Bild von Gegenstand wenn ausgewählt
	protected BufferedImage abgewaehlteBild;	//Bild von Gegenstand wenn ausgewählt
	protected String name;
	protected int inventarNr = 0;
	//private Timer timer;
	
	//private final int DELAY = 25;
	//protected String bildPfad;



	public Gegenstand(int x, int y, String bildPfad, String bildPfadAusgewaehlt, String name) {

		this.posX = x;
		this.posY = y;
		this.sichtbar = true;
		this.name = name;
		//this.bildPfad = bildPfad;
		//ImageIcon ii = new ImageIcon(bildPfad);
		try {
			this.abgewaehlteBild = ImageIO.read(new File(bildPfad));
			this.ausgewaehlteBild = ImageIO.read(new File(bildPfadAusgewaehlt));
			bild = abgewaehlteBild;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(bildPfad + " nicht lesbar!"); 
			this.abgewaehlteBild = null;
			this.ausgewaehlteBild = null;
		}
		this.breite = this.bild.getWidth();
		this.hoehe = this.bild.getHeight();
		
		//Transparente bilder
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
	}

	//Wenn ein Gegenstand gewählt bild ändern
	public void auswaehlen() {
		bild = ausgewaehlteBild;
		repaint();
	}
	
	//Wenn ein Gegenstand gewählt bild ändern
	public void abwaehlen() {
		bild = abgewaehlteBild;
		repaint();
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bild, 0, 0, this);           
    }
	
	//A Rectangle specifies an area in a coordinate space that is enclosed by the Rectangle object's upper-left point (x,y)
	//in the coordinate space, its width, and its height. 
	//getGrenzen gibt ein Rechteck zurück wo das Gegenstand steht ins Board. 
	public Rectangle getGrenzen() {
		return new Rectangle(this.posX, this.posY, this.breite, this.hoehe);
	}
	
	@Override
	public void blinken() {
		//this.setText(this.name); 
		this.auswaehlen();
		//TODO
	}
	
	@Override
	public void blinkenAus() {
		//this.setText(this.name); 
		this.abwaehlen();
		//TODO
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
	//Get und set Methoden
	public int getInventarNr() {return inventarNr;}
	public void setInventarNr(int inventarNr) {this.inventarNr = inventarNr;}
	
	public int getPosX() {return posX;}
	public void setPosX(int posX) {this.posX = posX;}

	public int getPosY() {return posY;}
	public void setPosY(int posY) {this.posY = posY;}

	public int getHoehe() {return hoehe;}
	public void setHoehe(int hoehe) {this.hoehe = hoehe;}

	public int getBreite() {return breite;}
	public void setBreite(int breite) {this.breite = breite;}

	public boolean getSichtbar() {return sichtbar;}
	public void setSichtbar(boolean sichtbar) {this.sichtbar = sichtbar;}

	public BufferedImage getBild() {return bild;}
	public void setBild(BufferedImage bild) {this.bild = bild;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}


	
}

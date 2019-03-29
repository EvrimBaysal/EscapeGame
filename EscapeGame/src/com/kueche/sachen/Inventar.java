package com.kueche.sachen;

import java.awt.Component;
import java.awt.Rectangle;

/**
 * @author Evrimpc
 * <pre>
 * Enum Inventar ein Rechteck behaltet Gegenstande
 *</pre>
 */
public enum Inventar  {
	INVENTAR1(25, 640), INVENTAR2(150, 640), INVENTAR3(280, 640), INVENTAR4(405, 640),
	INVENTAR5(530, 640), INVENTAR6(660, 640), INVENTAR7(785, 640);
	private final int posX;
	private final int posY;
	private final int hoehe = 105;	
	private final int breite = 105;
	//private boolean istFrei;
	
	Inventar(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public Rectangle getRechteck() {
		return new Rectangle(posX,posY,hoehe,breite);
	}
	
	public int getPosX() {return posX;}
	public int getPosY() {return posY;}
	public int getHoehe() {return hoehe;}
	public int getBreite() {return breite;}	
}

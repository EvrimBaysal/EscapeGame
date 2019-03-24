package com.kueche.organisation;

import java.awt.Component;
import java.awt.Rectangle;

public class Inventar  {
	private int posX;
	private int posY;
	private int hoehe;	
	private int breite;
	private boolean istFrei;
	
	public Inventar(int posX, int posY, int hoehe, int breite) {
		this.posX = posX;
		this.posY = posY;
		this.hoehe = hoehe;
		this.breite = breite;
		istFrei = true;
	}
	
	public Rectangle getRechteck() {
		return new Rectangle(posX,posY,hoehe,breite);
	}
	
	public int getPosX() {return posX;}
	public void setPosX(int posX) {this.posX = posX;}
	
	public int getPosY() {return posY;}
	public void setPosY(int posY) {this.posY = posY;}
	
	public int getHoehe() {return hoehe;}
	public void setHoehe(int hoehe) {this.hoehe = hoehe;}
	
	public int getBreite() {return breite;}
	public void setBreite(int breite) {this.breite = breite;}	
}

package com.kueche.sachen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import com.kueche.persistenz.MyInterface;

public class Tuer extends JButton implements MyInterface {

	private int anzahlSchloss;
	private int offeneSchloss;
	private BufferedImage bild;
	private List<BufferedImage> bilder = new ArrayList<BufferedImage>();
	protected int hoehe; // Höhe von Gegenstand
	protected int breite; // Breite von Gegenstand

	public Tuer(String[] pfads) {
		anzahlSchloss = pfads.length;
		try {
			bild = ImageIO.read(new File(pfads[0]));
			bilder.add(bild);
			offeneSchloss = 0;
			for (int i = 1; i < anzahlSchloss; i++) {
				bilder.add(ImageIO.read(new File(pfads[i])));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.breite = this.bild.getWidth();
		this.hoehe = this.bild.getHeight();

		// Transparente bilder
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}

	public boolean schlossOffnen() {
		++offeneSchloss;
		if (offeneSchloss < anzahlSchloss) {
			bild = bilder.get(offeneSchloss);
			this.repaint();
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild, 0, 0, this);
	}

	@Override
	public void blinken() {
		bild = bilder.get(anzahlSchloss-1);
		this.repaint();

	}

	@Override
	public void blinkenAus() {
		// TODO Auto-generated method stub

	}

	public int getHoehe() {
		return hoehe;
	}

	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}

	public int getBreite() {
		return breite;
	}

	public void setBreite(int breite) {
		this.breite = breite;
	}
}

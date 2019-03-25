package com.kueche.sachen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import com.kueche.organisation.Inventar;
import com.kueche.organisation.Leser;


public class GameBoard extends JPanel implements ActionListener {

	private final int GB_BREITE = 1024;
	private final int GB_HOEHE = 768;
	private Gegenstand teekanne;
	private Gegenstand toaster;
	private Gegenstand ausgewaehlteGegenstand;
	//private Inventar[] inventar = new Inventar[7];
	private List<Gegenstand> gegenstande;
	private boolean[] istInventarFrei = new boolean[7];

	private BufferedImage grundBild;

	//Konstruktor
	public GameBoard() {
		setInventar();
		setPreferredSize(new Dimension(GB_BREITE, GB_HOEHE));
		try {
			this.grundBild = ImageIO.read(new File("../EscapeGame/src/com/kueche/bilder/KucheGrundMitInventar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addMouseListener(new MeinMouseInputAdapter());
		this.setLayout(null); // If you call setLayout(null),then it's your responsibility to set the location
								// and
								// dimensions of every component; this is often done using "setBounds()"

		// Add Teekanne
		teekanne = new Gegenstand(0, 0, "../EscapeGame/src/com/kueche/bilder/demlik100.png",
				"../EscapeGame/src/com/kueche/bilder/demlik100_i.png", "Teekanne");
		// teekanne.setPreferredSize(new Dimension(teekanne.getBreite(),
		// teekanne.getHoehe()));
		teekanne.setBounds(100, 100, teekanne.getBreite(), teekanne.getHoehe());
		this.add(teekanne);
		teekanne.addActionListener(this);

		// Add Toaster
		toaster = new Gegenstand(0, 0, "../EscapeGame/src/com/kueche/bilder/toaster.png",
				"../EscapeGame/src/com/kueche/bilder/toaster_i.png", "Teekanne");
		// teekanne.setPreferredSize(new Dimension(teekanne.getBreite(),
		// teekanne.getHoehe()));
		toaster.setBounds(100, 500, toaster.getBreite(), toaster.getHoehe());
		this.add(toaster);
		toaster.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		System.out.println(ae.getSource());
		if (ae.getSource() == teekanne) {
			// System.out.println("Button geklickt!");
			if (ausgewaehlteGegenstand != teekanne) {
				ausgewaehlteGegenstand = teekanne;
				teekanne.blinken();
				teekanne.auswaehlen();

				toaster.abwaehlen();
			} else {
				ausgewaehlteGegenstand = null;
				teekanne.abwaehlen();
			}
		}

		if (ae.getSource() == toaster) {
			// System.out.println("Button geklickt!");
			ausgewaehlteGegenstand = toaster;
			toaster.auswaehlen();
			teekanne.abwaehlen();
			// teekanne.setBorder(new LineBorder(Color.WHITE, 12));
		}

		// if(ae.)

	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(grundBild, 0, 0, this);
		// g.drawImage(teekanne.getBild(),0,0,this);
	}

	

	public class MeinMouseInputAdapter extends MouseInputAdapter {

		@Override
		public void mousePressed(MouseEvent me) {
			super.mousePressed(me);

			for (int i = 0; i < 7; i++) {
				Rectangle r = Inventar.values()[i].getRechteck();
				//Wenn ein Gegenstand ausgewält , auf ein freie Inventar gedrückt, verschiebe Gegenstand in Inventar
				if ((me.getX() >= r.getMinX() && me.getX() <= r.getMaxX()) // check if X is within range
						&& (me.getY() >= r.getMinY() && me.getY() <= r.getMaxY())
						&& (ausgewaehlteGegenstand != null)
						&& (istInventarFrei[i] == true)) {
					//Verschibe das ausgewaehlte Gegenstand in inventar
					ausgewaehlteGegenstand.setBounds(r.x, r.y, r.width, r.height);
					istInventarFrei[i] = false;	//Inventar ist nicht mehr frei
					if(ausgewaehlteGegenstand.getInventarNr() != 0) {	//Wenn Gegenstandt war schon in einem Inventar, set es frei
						istInventarFrei[ausgewaehlteGegenstand.getInventarNr() - 1] = true;
					}
					ausgewaehlteGegenstand.setInventarNr(i+1);	//Gegenstandt inventarnr
				}
			}
		}

	}
	
	public void setInventar() {
		for(int i = 0; i < 7; i++) {
			istInventarFrei[i] = true;
		}
	}

}

/*
public void setInventar() {
	int breite = 0;
	int hoehe = 0;
	Leser leser = new Leser("../EscapeGame/src/GameKonfig.txt");
	try {
		// System.out.println(leser.werteLesenInt("Inventar3_PosY"));
		breite = leser.werteLesenInt("Inventar_Breite");
		hoehe = leser.werteLesenInt("Inventar_Hoehe");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	inventar[0] = new Inventar(22, 640, 111, 109);
	for (int i = 1; i <= inventar.length; i++) {
		int posX;
		int posY;
		String sucheX = "Inventar" + (i) + "_PosX";
		String sucheY = "Inventar" + (i) + "_PosY";
		try {
			posX = leser.werteLesenInt(sucheX);
			posY = leser.werteLesenInt(sucheY);
			inventar[i - 1] = new Inventar(posX, posY, breite, hoehe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// inventar[0].setBounds(new Rectangle(22,640,111,109));
}
*/

package com.kueche.sachen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import com.kueche.organisation.Inventar;
import com.kueche.organisation.Leser;
import com.kueche.organisation.MyInterface;


public class GameBoard extends JPanel implements ActionListener {

	private final int GB_BREITE = 1024;
	private final int GB_HOEHE = 768;
	private Gegenstand teekanne;
	private Gegenstand toaster;
	private Gegenstand ausgewaehlteGegenstand;
	//private Inventar[] inventar = new Inventar[7];
	private List<Gegenstand> gegenstande = new ArrayList<Gegenstand>();
	private boolean[] istInventarFrei = new boolean[7];
	int anzahlGegenstande;
	JButton hilfeButton;

	private BufferedImage grundBild;

	//Konstruktor
	public GameBoard() {
		initIstInventarFrei();
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
		initGegenstande();
		
		hilfeButton = new JButton("?");
		hilfeButton.setFont(new Font("Arial", Font.PLAIN, 40));
		hilfeButton.setBounds(910, 640, 100, 100);
		hilfeButton.addActionListener(this);
		this.add(hilfeButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println(ae.getSource());
		//**********Gegenstande ausw�hlen oder abw�hlen************************
		for(Gegenstand g : gegenstande) {
			if (ae.getSource() == g) {
				if (ausgewaehlteGegenstand != g) {	//Wenn Gegenstand wurde vorher nicht ausgew�hlt
					ausgewaehlteGegenstand = g;		//Gegenstand ausw�hlen
					g.auswaehlen();
					for(Gegenstand g2 : gegenstande) {	//und alle andere Gegenstande abw�hlen
						if(g != g2) {
							g2.abwaehlen();
						}
					}
				} else {	//Wenn Gegenstand wurde vorher ausgew�hlt
					ausgewaehlteGegenstand = null;
					g.abwaehlen();	//Gegenstand abw�hlen
				}
			}
		}
		// **********Ende Gegenstande ausw�hlen oder abw�hlen************************
		if(ae.getSource() == hilfeButton) {
			for(MyInterface blink : gegenstande) {
				blink.blinken();
			}
		}
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
			try {
				if((ausgewaehlteGegenstand.toString() != "Kasten") && (ausgewaehlteGegenstand.isVisible() == true)) {
					gegenstandInIventar(me);	//ausgew�hlte Gegenstand in Inventar verschieben
				}
			} catch(NullPointerException e) {
				
			}
		}

	}
	
	//*********ausgew�hlte Gegenstand in inventar verschieben********************************************
	public void gegenstandInIventar(MouseEvent me) {
		for (int i = 0; i < 7; i++) {
			Rectangle r = Inventar.values()[i].getRechteck();
			//Wenn ein Gegenstand ausgew�lt , auf ein freie Inventar gedr�ckt, verschiebe Gegenstand in Inventar
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
	
	public void initGegenstande() {
		
		Gegenstand newGegenstand;
		String name;
		String bild;
		String bild_i;
		int posX;
		int posY;
		int sichtbar;
		
		Leser gameKonfig = new Leser("../EscapeGame/src/GameKonfig.txt");
		anzahlGegenstande = gameKonfig.werteLesenInt("AnzahlGegenstande");
		for(int i = 0; i < anzahlGegenstande ; i++ ) {
			posX = gameKonfig.werteLesenInt("Gegenstand" + (i+1) + "_PosX");
			posY = gameKonfig.werteLesenInt("Gegenstand" + (i+1) + "_PosY");
			bild = ".." + gameKonfig.werteLesenString("Gegenstand" + (i+1) + "_Bild");
			bild_i = ".." + gameKonfig.werteLesenString("Gegenstand" + (i+1) + "_Bild_i");
			name = gameKonfig.werteLesenString("Gegenstand" + (i+1) + "_name");
			sichtbar = gameKonfig.werteLesenInt("Gegenstand" + (i+1) + "_Sichtbar");
			newGegenstand = new Gegenstand(posX, posY, bild, bild_i, name);
			newGegenstand.setBounds(posX, posY, newGegenstand.getBreite(), newGegenstand.getHoehe());
			if(sichtbar == 0) {
				newGegenstand.setVisible(false);
			} 
			this.add(newGegenstand);
			newGegenstand.addActionListener(this);
			gegenstande.add(newGegenstand);
		}
		
		Kasten schrank = new Kasten(50,400,
				"../EscapeGame/src/com/kueche/bilder/schrank.png",
				"../EscapeGame/src/com/kueche/bilder/schrankOffen.png",
				"Schrank");
		schrank.setBounds(50, 40, schrank.getBreite(), schrank.getHoehe());
		this.add(schrank);
		schrank.addActionListener(this);
		schrank.setGegenstandInKasten(gegenstande.get(1));
		gegenstande.get(1).setVisible(false); 
		gegenstande.add(schrank);
	}
	
	//alle istInventarFrei variable auf true setzen
	public void initIstInventarFrei() {
		for(int i = 0; i < 7; i++) {
			istInventarFrei[i] = true;
		}
	}

}

// Alte Codes 
/*
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
 */

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

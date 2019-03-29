package com.kueche.GameBoard;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import com.kueche.persistenz.Leser;
import com.kueche.persistenz.MyInterface;
import com.kueche.persistenz.SacheNichtInBoardException;
import com.kueche.sachen.Gegenstand;
import com.kueche.sachen.Inventar;
import com.kueche.sachen.Kasten;
import com.kueche.sachen.Tuer;

/**
 * @author Evrim Baysal
 * <pre> 
 * class GameBoard ist die Haupt Fenster für das Spiel
 *</pre>
 */
public class GameBoard extends JPanel implements ActionListener {
	/** Game Board Breite  */
	private final int GB_BREITE = 1024;
	/** Game Board Höhe  */
	private final int GB_HOEHE = 768;
	/** Ausgewählte Gegenstand für die Interaktionen zwischen Sachen */
	private Gegenstand ausgewaehlteGegenstand;
	//private Inventar[] inventar = new Inventar[7];
	/**Dynamische Array Speicher alle Gegenstande */
	private List<Gegenstand> gegenstande = new ArrayList<Gegenstand>();
	/** Array für die Kontrolle jede Inventar kann nur eine Sache halten */
	private boolean[] istInventarFrei = new boolean[7];
	/** Anzahl Gegenstande wird gelesen von GameKonfig*/
	private int anzahlGegenstande;
	private JButton hilfeButton;
	private Tuer tuer;
	private JLabel gameText;

	private BufferedImage grundBild;

	//Konstruktor
	public GameBoard() {
		initIstInventarFrei();
		setPreferredSize(new Dimension(GB_BREITE, GB_HOEHE));
		try {
			this.grundBild = ImageIO.read(new File("../EscapeGame/src/com/kueche/bilder/KucheGrundMitInventarV1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addMouseListener(new MeinMouseInputAdapter());
		this.setLayout(null); // If you call setLayout(null),then it's your responsibility to set the location
								// and
								// dimensions of every component; this is often done using "setBounds()"
		gameText = new JLabel("Finde 7 Schlüssel und öffne die Tür", SwingConstants.CENTER); 
		gameText.setBounds(10, 10, 1024, 50);
		gameText.setFont(new Font("Serif", Font.BOLD, 30));
		gameText.setForeground(Color.BLUE);
		this.add(gameText);
		
		initGegenstande();
		
		hilfeButton = new JButton("?");
		hilfeButton.setFont(new Font("Arial", Font.PLAIN, 40));
		hilfeButton.setBounds(910, 640, 100, 100);
		hilfeButton.addActionListener(this);
		this.add(hilfeButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		gegenstandeAusAbWaehlen(ae); //Gegenstande auswählen oder abwählen. ausgewaehlteGegenstand bestimmen
		
		//***********HilfeButton*****************************************************
		if(ae.getSource() == hilfeButton) {
			for(MyInterface blink : gegenstande) {
				blink.blinken();
			}
			
			MyInterface blink = tuer;
			blink.blinken();
		}
		//***********Ende HilfeButton*****************************************************
		
		if (ae.getSource() == tuer) {
			try {
			if(ausgewaehlteGegenstand.getName().indexOf("Key") > -1) {
				boolean weiter = tuer.schlossOffnen();
				//System.out.println("InventarNr :" + ausgewaehlteGegenstand.getInventarNr()); 
				if(ausgewaehlteGegenstand.getInventarNr() != 0) {
					istInventarFrei[ausgewaehlteGegenstand.getInventarNr()-1] = true;
				}
				gegenstande.remove(ausgewaehlteGegenstand);
				this.remove(ausgewaehlteGegenstand);
				ausgewaehlteGegenstand = null;
				repaint();
				if(weiter == false) {
					gameText.setText("Gewonnen!");
					System.out.println("Gewonnen!");
					this.repaint();
				}
				//else
					//System.out.println("Weiter");
			}
			}
			catch(NullPointerException e) {
				
			}
		}
		
	}
	
	/**Gegenstande auswählen oder abwählen. ausgewaehlteGegenstand bestimmen
	 * @param ae ist actionPerformed(ActionEvent ae)
	 * */
	public void gegenstandeAusAbWaehlen(ActionEvent ae) {
		for(Gegenstand g : gegenstande) {
			if (ae.getSource() == g) {
				if (ausgewaehlteGegenstand != g) {	//Wenn Gegenstand wurde vorher nicht ausgewählt
					ausgewaehlteGegenstand = g;		//Gegenstand auswählen
					g.auswaehlen();
					for(Gegenstand g2 : gegenstande) {	//und alle andere Gegenstande abwählen
						if(g != g2) {
							g2.abwaehlen();
						}
					}
				} else {	//Wenn Gegenstand wurde vorher ausgewählt
					ausgewaehlteGegenstand = null;
					g.abwaehlen();	//Gegenstand abwählen
				}
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
					gegenstandInIventar(me);	//ausgewählte Gegenstand in Inventar verschieben
				}
			} catch(NullPointerException e) {
				
			}
		}

	}
	
	//*********ausgewählte Gegenstand in inventar verschieben********************************************
	/** ausgewählte Gegenstand in einem leeren Inventar verschieben */
	public void gegenstandInIventar(MouseEvent me) {
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
	
	/** Initialisiert Gegenstande in Gameboard */
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
			try {
				posX = gameKonfig.werteLesenInt("Gegenstand" + (i+1) + "_PosX");
				posY = gameKonfig.werteLesenInt("Gegenstand" + (i+1) + "_PosY");
				if((posX < 0) || (posX > GB_BREITE) || (posY < 0) || (posY > GB_HOEHE) ) {
					throw new SacheNichtInBoardException();
				}
			} catch(SacheNichtInBoardException e) {
				posX = 0;
				posY = 0;
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
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
		
		Kasten schrank1 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/schrank.png",
				"../EscapeGame/src/com/kueche/bilder/schrankOffen.png",
				"Schrank1");
		schrank1.setBounds(556, 426, schrank1.getBreite(), schrank1.getHoehe());
		this.add(schrank1);
		schrank1.addActionListener(this);
		schrank1.setGegenstandInKasten(gegenstande.get(0));
		gegenstande.get(0).setVisible(false); 
		gegenstande.add(schrank1);
		
		//2. Scrank 837,426
		Kasten schrank2 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/schrank.png",
				"../EscapeGame/src/com/kueche/bilder/schrankOffen.png",
				"Schrank2");
		schrank2.setBounds(837, 426, schrank2.getBreite(), schrank2.getHoehe());
		this.add(schrank2);
		schrank2.addActionListener(this);
		schrank2.setGegenstandInKasten(gegenstande.get(1));
		gegenstande.get(1).setVisible(false); 
		gegenstande.add(schrank2);
		
		//3. Schrank Mikrowelle 553,341
		Kasten schrank3 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/mikroWelle.png",
				"../EscapeGame/src/com/kueche/bilder/mikroWelle_i.png",
				"Schrank3");
		schrank3.setBounds(553, 341, schrank3.getBreite(), schrank3.getHoehe());
		this.add(schrank3);
		schrank3.addActionListener(this);
		schrank3.setGegenstandInKasten(gegenstande.get(2));
		gegenstande.get(2).setVisible(false); 
		gegenstande.add(schrank3);
		
		//4. Schrank Teekanne 903,345
		Kasten schrank4 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/teekanne.png",
				"../EscapeGame/src/com/kueche/bilder/teekanne_i.png",
				"Schrank3");
		schrank4.setBounds(903, 345, schrank4.getBreite(), schrank4.getHoehe());
		this.add(schrank4);
		schrank4.addActionListener(this);
		schrank4.setGegenstandInKasten(gegenstande.get(3));
		gegenstande.get(3).setVisible(false); 
		gegenstande.add(schrank4);
		
		//5. Schrank Schrank-Oben 556,186
		Kasten schrank5 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/schrankOben.png",
				"../EscapeGame/src/com/kueche/bilder/schrankOben_i.png",
				"Schrank3");
		schrank5.setBounds(556, 186, schrank5.getBreite(), schrank5.getHoehe());
		this.add(schrank5);
		schrank5.addActionListener(this);
		schrank5.setGegenstandInKasten(gegenstande.get(4));
		gegenstande.get(4).setVisible(false); 
		gegenstande.add(schrank5);
		
		//6. Schrank Schrank-Oben 836,186
		Kasten schrank6 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/schrankOben.png",
				"../EscapeGame/src/com/kueche/bilder/schrankOben_i.png",
				"Schrank3");
		schrank6.setBounds(836, 186, schrank6.getBreite(), schrank6.getHoehe());
		this.add(schrank6);
		schrank6.addActionListener(this);
		schrank6.setGegenstandInKasten(gegenstande.get(5));
		gegenstande.get(5).setVisible(false); 
		gegenstande.add(schrank6);
		
		//7. Schrank Kühlschrank 730,297
		Kasten schrank7 = new Kasten(0,0,
				"../EscapeGame/src/com/kueche/bilder/Kuehlschrank.png",
				"../EscapeGame/src/com/kueche/bilder/Kuehlschrank_i.png",
				"Schrank3");
		schrank7.setBounds(730, 297, schrank7.getBreite(), schrank7.getHoehe());
		this.add(schrank7);
		schrank7.addActionListener(this);
		schrank7.setGegenstandInKasten(gegenstande.get(6));
		gegenstande.get(6).setVisible(false); 
		gegenstande.add(schrank7);
		
		// Tür mit 7 Schloss. pos = 15, 260
		tuer = new Tuer(new String[] {"../EscapeGame/src/com/kueche/bilder/tuer7.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_1G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_2G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_3G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_4G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_5G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_6G.png",
										"../EscapeGame/src/com/kueche/bilder/tuer7_7G.png",
		});
		tuer.setBounds(15, 260, tuer.getBreite(), tuer.getHoehe());
		this.add(tuer);
		tuer.addActionListener(this);

	}
	
	/** alle istInventarFrei variable auf true setzen */
	public void initIstInventarFrei() {
		for(int i = 0; i < 7; i++) {
			istInventarFrei[i] = true;
		}
	}

}

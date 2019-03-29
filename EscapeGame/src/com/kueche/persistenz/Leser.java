package com.kueche.persistenz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Evrim Baysal
 * <pre>
 * Leser liest GameKonfig und gibt zurück die gesuchte Werte
 *</pre>
 */
public class Leser {
	/** Pfad zu Datei zu lesen */
	private String filePfad;
	BufferedReader reader = null;

	public Leser(String pfad) {
		filePfad = pfad;
	}

	/** Findet gesuchte String in Datei und gibt seine Werte zurück.
	 *  Werte soll eine Integer sein.
	 *  @param String suche : Gesuchte Text in Konfig. Z.B.: "Gegenstand1_PosX"
	 *  @return int werte. z.B.: Gegenstand1_PosX=250. werte = 250
	 */
	public int werteLesenInt(String suche) {
		int werte;
		int gefunden;
		String line;
		try {
			reader = new BufferedReader(new FileReader(filePfad));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("FileNotFoundException : " + filePfad + " kann nicht gelesen");
		}
		try {
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				if (line.contains(suche)) {
					gefunden = line.indexOf('=');
					werte = Integer.parseInt(line.substring(gefunden + 1));
					return werte;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/** Findet gesuchte String in Datei und gibt seine Werte zurück.
	 *  Werte ist ein String
	 *  @param String suche : Gesuchte Text in Konfig. Z.B.: "Gegenstand1_name"
	 *  @return int werte. z.B.: Gegenstand1_name=Key1. werte = Key1
	 */
	public String werteLesenString(String suche) {
		String werte;
		int gefunden;
		String line;
		try {
			reader = new BufferedReader(new FileReader(filePfad));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("FileNotFoundException : " + filePfad + " kann nicht gelesen");
		}
		
	    
		try {
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				if(line.contains(suche)) {
					gefunden = line.indexOf('=');
					werte = line.substring(gefunden+1);
				    return werte;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return "";
	}
}

package com.kueche.organisation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Leser {
	private String filePfad;
	BufferedReader reader = null;

	public Leser(String pfad) {
		filePfad = pfad;
	}

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
				System.out.println(line);
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
				System.out.println(line);
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

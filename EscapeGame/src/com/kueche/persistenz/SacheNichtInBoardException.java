package com.kueche.persistenz;
/**
 * 
 * @author Evrim Baysal
 * Exception tritt auf, wenn die Positionen von eingelesene Gegenstande in GameKonfig.txt 
 * überschreitet da Game Board grenze 
 *
 */
public class SacheNichtInBoardException extends Exception {

	public SacheNichtInBoardException() {
		super("Das eingelesene Objekt befindet nicht in Board! Das wert auf 0 gesetzt!");
	}
	
}

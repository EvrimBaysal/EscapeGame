package com.kueche.sachen;

public class Kasten extends Gegenstand {
	
	private Gegenstand gegenstandInKasten = null;
	
	public Kasten(int x, int y, String bildPfad, String bildPfadAusgewaehlt, String name) {
		super(x, y, bildPfad, bildPfadAusgewaehlt, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Kasten";
	}
	
	

	@Override
	public void auswaehlen() {
		super.auswaehlen();
		if(gegenstandInKasten != null) {
			gegenstandInKasten.setVisible(true);
		}
	}

	@Override
	public void abwaehlen() {	
		//TODO: BUG:Box kann nur geöffnet werden, sonst gegenstandInKasten kann nicht ausgewählt werden.
	}

	public Gegenstand getGegenstandInKasten() {
		return gegenstandInKasten;
	}

	public void setGegenstandInKasten(Gegenstand gegenstandInKasten) {
		this.gegenstandInKasten = gegenstandInKasten;
	}
}

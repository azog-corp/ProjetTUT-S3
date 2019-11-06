package thermometre.outils;


public class Temperature {
	
	private int date;
	
	private String heure;
	
	private String temp;
		
	public Temperature(String temp) {
		this.date = temp.charAt(0)+temp.charAt(1);
	}

	public String getDate() {
		return this.date;
	}
	
	public String getHeure() {
		return this.heure;
	}

	public String getTemp() {
		return this.temp;
	}
	
	public String toString() {
		return date + " " + heure + " " + temp;
	}
}

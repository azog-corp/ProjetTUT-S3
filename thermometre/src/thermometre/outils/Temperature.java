package thermometre.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Temperature {
	
	private Date date;
	
	private int time;
	
	private float temp;
		
	public Temperature(String temp) {
		String ligne= "yyyy/mm/dd hh:mm:ss temp.erature";
		String[] donnees = ligne.split(" ");
		donnees[0].split("/");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			this.date = formatter.parse(donnees[0] + donnees[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.temp = Float.parseFloat(donnees[2]);
	}

	public Date getDate() {
		return this.date;
	}
	
	public int getTime() {	
		return this.getTime();
	}

	public float getTemp() {
		return this.temp;
	}
	
	public String toString() {
		return date + " " + time + " " + temp;
	}
}

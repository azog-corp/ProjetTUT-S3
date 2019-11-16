package thermometre.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Temperature {
	
	/**
	 * Date de l'instance au format jj/mm/aaa hh:mm;ss
	 */
	private String date;
	
	/**
	 * temperature
	 */
	private double temp;
		
	public Temperature(String newTemp) {
		String[] decomposition = newTemp.split(" ");
		this.date = decomposition[0] + " " + decomposition[1];
		this.temp = Double.parseDouble(decomposition[2]);
	}

	public String getDate() {
		return this.date;
	}

	public double getTemp() {
		return this.temp;
	}
	
	public String toString() {
		return date + " " + temp;
	}
}

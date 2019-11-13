package thermometre.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Temperature {
	
	/**
	 * Date de l'instance au format jj/mm/aaa
	 */
	private String date;
	
	/**
	 * Temps de l'instance au format hh:mm:ss 
	 */
	private String time;
	
	/**
	 * 
	 */
	private String temp;
		
	public Temperature(String newTemp) {
		String[] decomposition = newTemp.split(newTemp, ' ');
		this.date = decomposition[0];
		this.time = decomposition[1];
		this.temp = decomposition[2];
	}

	public String getDate() {
		return this.date;
	}
	
	public int getTime() {	
		return this.getTime();
	}

	public String getTemp() {
		return this.temp;
	}
	
	public String toString() {
		return date + " " + time + " " + temp;
	}
}

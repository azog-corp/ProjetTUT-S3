package projet.src.thermometres3.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Objet temp�rature correspondant � un objet Date cr�er � partir d'une string au format 
 * jj/MM/yyyy HH:mm:ss et d'un double temp�rature contenu lui aussi dans la chaine
 *
 */
public class Temperature {
	
	/**
	 * Date de l'instance au format jj/mm/aaa hh:mm:ss
	 */
	private Date date;
	
	/**
	 * temp�rature recup�r�e
	 */
	private double temp;
	
	/**
	 * format de la Date, pour pouvoir cr�er un objet Date � partir de la string
	 */
	private final SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	/**
	 * Prend une string ayant la forme : jj/mm/yyyy hh:mm:ss temp pour la split en diff�rent morceaux
	 * @param newTemp la string contenant la temp�rature et la date
	 * @throws ParseException 
	 */
	public Temperature(String newTemp) {
		
		String[] decomposition = newTemp.split(" ");
		
		/* cr�ation de l'objet date grace au parser et formatter  */
		try {
			this.date = format.parse(decomposition[0] + " " + decomposition[1]);
			
		} catch (ParseException e) {
			// ce cas ne devrais pas arriver les temperatures dans le fichier sont inscrites par nous
			// elle ne pourront pas avoir un mauvais format
		}
		
		this.temp = Double.parseDouble(decomposition[2]);
	}

	@Override
	public String toString() {
		return format.format(date) +" " + temp; // afichage de la date
	}

	/**
	 * @return un objet Date correspondant � la String jj/MM/yyyy HH:mm:ss
	 * de l'instance courante
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @return un double contenant la temp�rature de l'instance courante
	 */
	public double getTemp() {
		return this.temp;
	}
}

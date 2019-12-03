package thermometre.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * classe utilitaire sur les température et les dates
 */
public class OutilsTemperature {
	
	/**
	 * Permet de convertir une date (jj/mm/aaa hh:mm:ss) en objet Date
	 * @param aConvertir la string contenant la date et l'horaire à convertir
	 * @return l'objet Date créée
	 * @throws ParseException
	 */
	public static Date sToDate(String aConvertir) throws ParseException {
		Date convertie = new Date();
		
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			convertie = format.parse(aConvertir);
			
		} catch (ParseException e) {
			throw new ParseException("Erreur lors du formattage de la date " 
				+ aConvertir, 0);
		}
		return convertie;
	}
	
	/**
	 * Si date 1 est plus récent que date2, renvoie true, sinon false
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2) {
		return date1.compareTo(date2) > 0;
	}
	
	/**
	 * Determine si une chaîne de caractères contient bien une date dans le format
	 * "hh:mm:ss"
	 * De plus, l'horaire doit etre un horaire de la journee (donc heure compris entre
	 * 0 et 23 et minute entre 0 et 59)
	 * @param aTester  chaîne contenant l'horaire a tester
	 * @return un booleen egal a vrai si l'horaire teste est valide
	 */
	public static boolean estValide(String horaire) {
		Date dateActuelle = new Date();
		Date dateAVerif;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			dateAVerif = format.parse(horaire);
			return dateAVerif.before(dateActuelle);
		} catch (ParseException e) {
			return false;
		}
	}
	
	

}

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
		
		SimpleDateFormat format=new SimpleDateFormat("jj/MM/yyyy HH:mm:ss");
		try {
			convertie = format.parse(aConvertir);
			
		} catch (ParseException e) {
			throw new ParseException("Erreur lors du formattage de la date " 
										+ aConvertir, 0);
		}
		return convertie;
	}
	
	
	/**
	 * Convertit l'horaire argument en entier (resultat en minutes)
	 * Si l'horaire argument n'est pas valide, c'est l'entier -1 qui sera renvoye
	 * @param horaire  chaîne contenant l'horaire a convertir
	 * @return un entier egal a la conversion en minutes de l'horaire argument
	 *                   ou bien a -1 si l'horaire argument n'est pas valide
	 */
	public static double convertir(String horaire) {
		float conversion = -1,
		heure,
		minute,
		seconde;

		if (estValide(horaire)) {
			heure = horaire.charAt(0) - '0';
			heure *= 10;
			heure +=(horaire.charAt(1) - '0');
			minute = horaire.charAt(3) - '0';
			minute *= 10;
			minute += (horaire.charAt(4) - '0');
			seconde = ((horaire.charAt(6) - '0') + (horaire.charAt(7) - '0'))/100;
			conversion = heure * 60 + minute + seconde;
		}
		return conversion;
	}
	
	/**
	 * Determine si une chaîne de caractères contient bien un horaire dans le format
	 * "hh:mm:ss"
	 * De plus, l'horaire doit etre un horaire de la journee (donc heure compris entre
	 * 0 et 23 et minute entre 0 et 59)
	 * @param aTester  chaîne contenant l'horaire a tester
	 * @return un booleen egal a vrai si l'horaire teste est valide
	 */
	public static boolean estValide(String horaire) {
		boolean resultat = false; // vrai si l'horaire a tester est valide
		if (horaire.length() == 5) {
			if(horaire.charAt(2) == ':' && horaire.charAt(5) == ':') {
				switch (horaire.charAt(0)) {
					case '1':
					resultat = horaire.charAt(1) >= '0' && horaire.charAt(1) <= '9' 
					&& horaire.charAt(3) >= '0' && horaire.charAt(3) <= '5' 
					&& horaire.charAt(4) >= '0' && horaire.charAt(4) <= '9'
					&& horaire.charAt(6) >= '0' && horaire.charAt(6) <= '5'
					&& horaire.charAt(7) >= '0' && horaire.charAt(7) <= '9';
					break;
					case '2':
					resultat = horaire.charAt(1) >= '0' && horaire.charAt(1) <= '3' 
					&& horaire.charAt(3) >= '0' && horaire.charAt(3) <= '5' 
					&& horaire.charAt(4) >= '0' && horaire.charAt(4) <= '9'
					&& horaire.charAt(6) >= '0' && horaire.charAt(6) <= '5'
					&& horaire.charAt(7) >= '0' && horaire.charAt(7) <= '9';
					break;
					default:
					break;
				}
			}
		}
		return resultat;
	}
	
	

}

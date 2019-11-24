package thermometre.outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Objet temperature correspondant a un objet Date creer a partir d'une string au format
 * jj/MM/yyyy HH:mm:ss et d'un double temperature contenu lui aussi dans la chaine
 *
 */
public class Temperature {
	
	/**
	 * Date de l'instance au format jj/mm/aaa hh:mm:ss
	 */
	private Date date;
	
	/**
	 * temp�rature recuperee
	 */
	private double temp;
	
	/**
	 * format de la Date, pour pouvoir creer un objet Date a partir de la string
	 */
	private final SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	/**
	 * Prend une string ayant la forme : jj/mm/yyyy hh:mm:ss temp pour la split en different morceaux
	 * @param newTemp la string contenant la temperature et la date
	 * @throws ParseException 
	 */
	public Temperature(String newTemp) throws ParseException {
		
		String[] decomposition = newTemp.split(" ");
		
		/* cr�ation de l'objet date grace au parser et formatter  */
		try {
			this.date = format.parse(decomposition[0] + " " + decomposition[1]);
			
		} catch (ParseException e) {
			// on fait remonter l'erreur de format, ce cas ne devrais pas arriver
			throw new ParseException("Erreur lors du formattage de la date " 
										+ decomposition[0] + " " + decomposition[1], 0);
			
		}
		
		this.temp = Double.parseDouble(decomposition[2]);
	}

	/**
	 * @return un objet Date correspondant a la String jj/MM/yyyy HH:mm:ss
	 * de l'instance courante
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @return un double contenant la temperature de l'instance courante
	 */
	public double getTemp() {
		return this.temp;
	}
	
	public String toString() {
		return date.toString() + " " + temp;
	}
}

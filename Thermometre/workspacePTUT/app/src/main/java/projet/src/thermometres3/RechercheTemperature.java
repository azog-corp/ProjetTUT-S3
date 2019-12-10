package projet.src.thermometres3;



import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurFichier;
import projet.src.thermometres3.outils.Temperature;

public class RechercheTemperature {

	/**
	 * Liste contenant toutes les instances de température
	 */
	private static ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();

	/**
	 * format prédéfinis pour les Dates
	 */
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private final static String NOM_FICHIER = "fichierTemp.txt";

	private final static String NOUVELLE_TEMP = "nouvellesTemperature.txt";

    public static String getNomFichier() {
        return NOM_FICHIER;
    }

    public static String getNouvelleTemp() {
        return NOUVELLE_TEMP;
    }

    /**
     * revoie la liste des températures
     * @return
     */
    public static ArrayList<Temperature> getListTemp() {
        return listeTemp;
    }

    /**
     * Renvoie la derniÃ¨re température
     * de la liste de températures
     */
    public double getDerniereTemp() {
        return listeTemp.get(listeTemp.size()-1).getTemp();
    }

	/**
	 * Fonction qui lit un fichier texte contenant
	 * des dates liées Ã  des températures et créé une
	 * instance de Temperature pour chacune des lignes
	 * pour ensuite les enregistrer dans une arrayList
	 */
	public static void editTemp(Context myContext) {

		String ligne;    // ligne lue dans le fichier

		try { // déclaration et création de l'objet fichier
			BufferedReader fichier = new BufferedReader(new InputStreamReader(myContext.getAssets().open(NOUVELLE_TEMP)));

			while (((ligne = fichier.readLine()) != null)) {

				try {
					listeTemp.add(new Temperature(ligne));
				} catch (ParseException e) {
					System.out.println(e +" problème création liste de températures");
				}
			}
			fichier.close();
			// fermeture du fichier automatique avec try-with-ressource
		} catch (IOException ex) {
			//TODO faire une exception pour que linterface puisse savoir
			System.out.println("Problème avec l'ouverture du fichier fichierTemp.txt");
			// problème d'accès au fichier
		}
	}

	/**
	 * Supprime toutes les températures
	 * @return
	 */
	public static boolean supprimerTemp(Context myContext) {
		try {
			//TODO utliser une constante global plutot
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(myContext.openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
			/*lorsque l'application se charge les températures sont automatiquement chargées
            Pour les faire disparaitre et ne plus etre valable on  reinitialise la liste des temperatures en memoire*/
			RechercheTemperature.listeTemp = new ArrayList<Temperature>();
		}
		catch (Exception ex) {
			System.out.println("Error clear file fichierTemp.txt");
			return false;
		}
		return true;
	}

	/**
	 * écrit dans le fichier fichierTemp les données contenu dans listeTemp
	 */
	public static void saveTemp(Context myContext) throws ErreurFichier {
		System.out.println("je sauvegarde");
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(myContext.openFileOutput("fichierTemp.txt", Context.MODE_PRIVATE));
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				outputStreamWriter.write(listeTemp.get(x).toString());
			}
			outputStreamWriter.close();
		} catch (Exception ex) {
			throw new ErreurFichier();
		}
	}

	/**
	 * Ajoute les nouvelle températures dans listeTemp et les enregistre
	 * dans le fichier listeTemp
	 */
	public static void addTemp(Context myContext) throws ErreurFichier  {
		editTemp(myContext);
		saveTemp(myContext);
	}

	/**
	 * Vérifie si une date se trouve entre 2019 et 2020
	 * @param date
	 * @return
	 * @throws ErreurDate
	 */
	public static boolean dateOk(String date) throws ErreurDate  {
		try {
			Date dateMin = conversion("01/01/2019 00:00:00");
			Date dateMax = new Date();
			format.format(dateMax);
			Date aVerifier = conversion(date);
            // si aVerifier est entre dateMin et dateMax
            return (dateMin.getTime() < aVerifier.getTime() && dateMax.getTime() > aVerifier.getTime());
		} catch (ParseException e) {
			throw new ErreurDate();
		}
	}

	/**
	 * Vérifie si deux date sont chronologique avec deux jour d'écart
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean intervalleOk(String date1, String date2) throws ErreurDate  {
		try {
			Date date1formate = conversion(date1);
			Date date2formate = conversion(date2);
            long diff = ((date2formate.getTime() - date1formate.getTime()) / (1000*60*60*24));
            return diff < 2 && diff > 0;
		} catch (ParseException e) {
			throw new ErreurDate();
		}

	}

	/**
	 * Créer une ArrayList contenant les instances de température conpris entre 2 intervalles
	 * @param date1 premiere date de l'intervalle
	 * @param date2 deuxieme date de l'intervalle
	 * @return liste des temperatures comprises dans l'intervalle
	 */
	public static ArrayList<Temperature> dateIntervalle(String date1, String date2) throws ErreurDate {

		ArrayList<Temperature> tempIntervalle = new ArrayList<Temperature>();
        try {
        	/* Conversion des dates */
            Date date1formate = conversion(date1);
            Date date2formate = conversion(date2);

		System.out.println("Date 1 " + date1formate.toString() + " date 2 " + date2formate.toString()); //-- debug
		/* Lecture fichier et ajout dans la liste temperature
		* des temperatures dans l'intervalle */
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (listeTemp.get(x).getDate().getTime() >= date1formate.getTime()
					&& listeTemp.get(x).getDate().getTime() <= date2formate.getTime()) {
				//TODO des que date > date2 stop NOK arrete la lecture
				tempIntervalle.add(listeTemp.get(x));
				System.out.println("C'est OK : " + listeTemp.get(x).getDate());
			} else {
				System.out.println("C'est NOK : " + listeTemp.get(x).getDate());
			}
		}
		for(int x = 0 ; x < tempIntervalle.size() ; x++) {
			System.out.println(tempIntervalle.get(x).getDate());
		}
		return tempIntervalle;
        } catch(ParseException e) {
            // l'une des dates n'est pas correcte
            throw new ErreurDate();
        }
	}

	/** TODO refaire plus tard mais necessite context donc risque erreur
	 * Suprime de listeTemp toutes les température contenu dans un intervalle
	 * @param date1
	 * @param date2

	public static void supprimerIntervalle(String date1, String date2) {

	Date date1formate = conversion(date1);
	Date date2formate = conversion(date2);
	for (int x = 0 ; x < listeTemp.size() ; x++) {
	if (listeTemp.get(x).getDate().getTime() >= date1formate.getTime()
	&& listeTemp.get(x).getDate().getTime() <= date2formate.getTime()) {
	listeTemp.remove(listeTemp.get(x));
	}
	}
	saveTemp();
	}*/

	/**
	 * Vérifie si une date existe dans listeTemp
	 * @param date
	 * @return

	public static boolean dateExiste(String date) {
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (listeTemp.get(x).getDate().toString() == date) {
				return true;
			}
		}
		return false;
	}*/

	/**
	 * Convertie un date en String Ã  une date en Date
	 * @param date
	 * @return la date formate avec le format dd/MM/yyyy hh:mm:ss
	 * @throws ErreurDate si le format de la date n'est pas valide
	 */
	public static Date conversion(String date) throws ParseException {
		Date dateFormate;
		dateFormate  = format.parse(date);
		return dateFormate;
	}
}

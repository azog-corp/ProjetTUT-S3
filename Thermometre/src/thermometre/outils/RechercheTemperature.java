package thermometre.outils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import thermometre.outils.Temperature;

public class RechercheTemperature {

	/**
	 * Liste contenant toutes les instances de température
	 */
	private static ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();

	/**
	 * Fonction qui lit un fichier texte contenant
	 * des dates liées à des températures et créé une 
	 * instance de Temperature pour chacune des lignes
	 * pour ensuite les enregistrer dans une arrayList
	 */
	public static void editTemp(String file) {

		String ligne;    // ligne lue dans le fichier

		try ( // déclaration et création de l'objet fichier
				BufferedReader fichier = new BufferedReader(new FileReader(file))) {

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
			System.out.println("Problème avec l'ouverture du fichier fichierTemp.txt");
			// problème d'accès au fichier
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Temperature> getListTemp() {
		return listeTemp;
	}

	/**
	 * Renvoie la dernière température 
	 * de la liste de températures
	 */
	public double getDerniereTemp() {
		return listeTemp.get(listeTemp.size()-1).getTemp();
	}
	
	/**
	 * Supprime toutes les température
	 * @return
	 */
	public static boolean supprimerTemp() {
		try {

			PrintWriter printwriter = new PrintWriter(new FileOutputStream("fichierTemp.txt"));
			printwriter.println("");
			printwriter.close();
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
	public static void saveTemp() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileOutputStream("fichierTemp.txt"));
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				printwriter.println(listeTemp.get(x).toString());
			}
			printwriter.close();
		} catch (Exception ex) {
			System.out.println("Error save file fichierTemp.txt");
		}
	}
	
	/**
	 * Ajoute les nouvelle températures dans listeTemp et les enregistre
	 * dans le fichier listeTemp
	 */
	public static void addTemp() {
		editTemp("nouvellesTemperature.txt");
		saveTemp();
	}
	
	/**
	 * Vérifie si une date se trouve entre 2019 et 2020
	 * @param date
	 * @return
	 */
	public static boolean dateOk(String date) {
		Date dateMin = conversion("01/01/2019 00:00:00");
		Date dateMax = conversion("30/12/2020 00:00:00");
		Date aVerifier = conversion(date);
		// si aVerifier est entre dateMin et dateMax
		return compareDate(aVerifier, dateMin) && compareDate(dateMax, aVerifier);
	}

	/**
	 * Vérifie si deux date sont chronologique avec deux jour d'écart
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean intervalleOk(String date1, String date2) {

			Date date1formate = conversion(date1);
			Date date2formate = conversion(date2);
			double diff = (date1formate.getTime() - date2formate.getTime() / (1000*60*60*24));
			if (diff <= 2 && diff >= 0) {
				return true;
			}
		return false;
	}
	
	/**
	 * Créer une ArrayList contenant les instances de température conpris entre 2 intervalles
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static ArrayList<Temperature> dateIntervalle(String date1, String date2) {
		
		ArrayList<Temperature> tempIntervalle = new ArrayList<Temperature>();

			Date date1formate = conversion(date1);
			Date date2formate = conversion(date2);
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				if (listeTemp.get(x).getDate().getTime() >= date1formate.getTime() 
						&& listeTemp.get(x).getDate().getTime() <= date2formate.getTime()) {
					tempIntervalle.add(listeTemp.get(x));
				}
			}
		return tempIntervalle;
	}
	
	/**
	 * Suprime de listeTemp toutes les température contenu dans un intervalle
	 * @param date1
	 * @param date2
	 */
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
	}
	
	/**
	 * Vérifie si une date existe dans listeTemp
	 * @param date
	 * @return
	 */
	public static boolean dateExiste(String date) {
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (listeTemp.get(x).getDate().toString() == date) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Convertie un date en String à une date en Date
	 * @param date
	 * @return
	 */
	public static Date conversion(String date) {
		
	SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    try {
		Date dateFormate  = format.parse(date);
		return dateFormate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    return null;
	}
}

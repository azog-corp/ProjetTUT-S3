package thermometre.outils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
	public static void rechercheTemperature() {

		String ligne;    // ligne lue dans le fichier

		try ( // déclaration et création de l'objet fichier
				BufferedReader fichier = new BufferedReader(new FileReader("fichierTemp.txt"))) {

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
	
	public static void saveTemp() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileOutputStream("fichierTemp.txt"));
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				printwriter.println(listeTemp.get(x).toString());
			}
			printwriter.close();
		} catch (Exception ex) {
			System.out.println("Error clear file fichierTemp.txt");
		}
	}
	
	public static void addTemp() {
		
	}
	
	public static boolean dateOk(Date date) {
		return true;
	}

	public static boolean intervalleOk(Date date1, Date date2) {
		return true;
	}
	
	public static ArrayList dateIntervalle(String date1, String date2) {
		
	}
	
	public static void supprimerIntervalle(Date date1, Date date2) {
		
	}
	
	public static boolean dateExiste(Date date) {
		return true;
	}
	
	
}

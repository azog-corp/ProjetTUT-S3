package thermometre.outils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
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
			System.out.println("Error save file fichierTemp.txt");
		}
	}
	
	public static void addTemp() {
		editTemp("nouvellesTemperature.txt");
		saveTemp();
	}
	
	public static boolean dateOk(String date) {
		return true;
	}

	public static boolean intervalleOk(String date1, String date2) {
		
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
			Date date1formate  = format.parse(date1);
			Date date2formate = format.parse(date2);
			double diff = (date1formate.getTime() - date2formate.getTime() / (1000*60*60*24));
			if (diff <= 2 && diff >= 0) {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<Temperature> dateIntervalle(String date1, String date2) {
		
		ArrayList<Temperature> tempIntervalle = new ArrayList<Temperature>();
		
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
			Date date1formate  = format.parse(date1);
			Date date2formate = format.parse(date2);
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				if (listeTemp.get(x).getDate().getTime() >= date1formate.getTime() 
						&& listeTemp.get(x).getDate().getTime() <= date2formate.getTime()) {
					tempIntervalle.add(listeTemp.get(x));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempIntervalle;
	}
	
	public static void supprimerIntervalle(String date1, String date2) {
		
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    try {
			Date date1formate  = format.parse(date1);
			Date date2formate = format.parse(date2);
			for (int x = 0 ; x < listeTemp.size() ; x++) {
				if (listeTemp.get(x).getDate().getTime() >= date1formate.getTime() 
						&& listeTemp.get(x).getDate().getTime() <= date2formate.getTime()) {
					listeTemp.remove(listeTemp.get(x));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean dateExiste(String date) {
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (listeTemp.get(x).getDate().toString() == date) {
				return true;
			}
		}
		return false;
	}
	
	
}

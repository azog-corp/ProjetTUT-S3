package thermometre.outils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import thermometre.outils.Temperature;

public class RechercheTemperature {

	/**
	 * Liste contenant toutes les instances de temp�rature
	 */
	private ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();

	/**
	 * Fonction qui lit un fichier texte contenant
	 * des dates li�es � des temp�ratures et cr�� une 
	 * instance de Temperature pour chacune des lignes
	 * pour ensuite les enregistrer dans une arrayList
	 */
	public RechercheTemperature() {   

		String ligne;    // ligne lue dans le fichier

		try ( // d�claration et cr�ation de l'objet fichier
				BufferedReader fichier = new BufferedReader(new FileReader("fichierTemp.txt"))) {

			while (((ligne = fichier.readLine()) != null)) {
				
				try {
					listeTemp.add(new Temperature(ligne));
				} catch (ParseException e) {
					System.out.println(e +" probl�me cr�ation liste de temp�ratures");
				}
			} 
			fichier.close();
			// fermeture du fichier automatique avec try-with-ressource          
		} catch (IOException ex) {      
			System.out.println("Probl�me avec l'ouverture du fichier fichierTemp.txt");
			// probl�me d'acc�s au fichier
		}
	}
	
	public ArrayList<Temperature> getListTemp() {
		return listeTemp;
	}

	/**
	 * Renvoie la derni�re temp�rature 
	 * de la liste de temp�ratures
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

	public static boolean dateValide(String date) {
		
		date.split("/");
	}

}
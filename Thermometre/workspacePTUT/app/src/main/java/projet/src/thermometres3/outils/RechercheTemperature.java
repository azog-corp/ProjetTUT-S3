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
	 * Liste contenant toutes les instances de temperature
	 */
	private ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();

	/**
	 * Fonction qui lit un fichier texte contenant
	 * des dates liees et des temperatures et cree une
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
					System.out.println(e +" probleme creation liste de temperatures");
				}
			} 
			fichier.close();
			// fermeture du fichier automatique avec try-with-ressource          
		} catch (IOException ex) {      
			System.out.println("Probleme avec l'ouverture du fichier fichierTemp.txt");
			// probleme d'acces au fichier
		}
	}
	
	public ArrayList<Temperature> getListTemp() {
		return listeTemp;
	}

	/**
	 * Renvoie la derniere temperature
	 * de la liste de temperatures
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
		return true;//todo stub
	}

}

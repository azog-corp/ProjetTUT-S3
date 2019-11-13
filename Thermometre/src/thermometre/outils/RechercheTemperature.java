package thermometre.outils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import thermometre.outils.Temperature;
import thermometre.outils.OutilsTemperature;

public class RechercheTemperature {
	
	private static ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();
	
	private static Scanner entree = new Scanner(System.in);
	
	private static void initTemp() {    
		
		String ligne;    // ligne lue dans le fichier

		try ( // déclaration et création de l'objet fichier
			BufferedReader fichier = new BufferedReader(new FileReader("fichierTemp.txt"))) {

			while (((ligne = fichier.readLine()) != null)) {
				listeTemp.add(new Temperature(ligne));
		} 

			// fermeture du fichier automatique avec try-with-ressource          
	} catch (IOException ex) {      
		System.out.println("Bonjour");
			// problème d'accès au fichier
	}
		return;
	}
	
	private static void tempActuelle() {
		System.out.println(listeTemp.get(listeTemp.size()-1).toString());
	}
	
	private static void tempAnterieure(String date) {
		
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			
			if (listeTemp.get(x).getDate() == date) {
				System.out.println(listeTemp.get(x).toString());
			}
		}
	}
	
	private static void diffTemp(String date1, String date2) {
		
		double intervalle1 = 0,
		intervalle2 = 0;
		
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (date1 == listeTemp.get(x).getDate()) {
				intervalle1 = x;
				break;
			} else if (date2 == listeTemp.get(x).getDate()) {
				intervalle1 = x;
				break;
			}
		}
		
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (date1 == listeTemp.get(x).getDate()) {
				intervalle2 = x;
				break;
			} else if (date2 == listeTemp.get(x).getDate()) {
				intervalle2 = x;
				break;
			}
		}
		
		System.out.println(intervalle1 - intervalle2);
		
	}
	
	


	public static void main(String[] args) {
		initTemp();
		int choix = -1;
		do {
			entree.hasNextLine();
			System.out.println("1 : Température actuelle\n2 : Température antérieure\n3 : différence de température\n0 : Quitter");
			choix = entree.hasNextInt() ? entree.nextInt() : -1;
			if (choix == 1) {
				tempActuelle();
			} else if (choix == 2) {
				tempAnterieure("01/11/2019 10:11:12 14,5");
			} else if (choix == 3) {
				diffTemp("01/11/2019 10:11:12 14,5", "04/11/2019 20:11:12 1,5");
			} else {
				System.out.println("Entrée incorecte");
			}
		} while (choix != 0);
	}

}

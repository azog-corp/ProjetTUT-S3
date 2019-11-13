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
			BufferedReader fichier = new BufferedReader(new FileReader("thermometre/outils/fichierTemp.txt"))) {

			while (((ligne = fichier.readLine()) != null)) {
				listeTemp.add(new Temperature(ligne));
				System.out.println(listeTemp.get(listeTemp.size()));
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
	
	


	public static void main(String[] args) {
		System.out.println("Bonjour");
		initTemp();
		listeTemp.add(new Temperature("23/05/1999 13:23:34 8,5"));
		tempActuelle();
		int choix = -1;
		do {
			entree.hasNextLine();
			System.out.println("1 : Température actuelle\n2 : Température antérieure\n3 : différence de température\n0 : Quitter");
			choix = entree.hasNextInt() ? entree.nextInt() : -1;
			if (choix == 1) {
				tempActuelle();
			} else if (choix == 2) {
				// tempAnterieure();
			} else if (choix == 3) {
				// diffTemp();
			} else {
				System.out.println("Entrée incorecte");
			}
		} while (choix != 0);
	}

}

package thermometre.outils;

import java.util.ArrayList;
import java.util.Scanner;
import thermometre.outils.Temperature;

public class OutilsTemperature {
	
	private static ArrayList<Temperature> listeTemp = new ArrayList<Temperature>();
	
	private static Scanner entree = new Scanner(System.in);
	
	private static void initTemp() {
		
	}
	
	private static void tempActuelle() {
		System.out.println(listeTemp.get(listeTemp.size()-1).toString());
	}

	private static void tempAnterieure() {
		System.out.println("Entrer un date et une heure sous la forme jj/mm");
		String date = entree.nextLine();
		String heure = entree.nextLine();
		
		for (int x = 0 ; x < listeTemp.size() ; x++) {
			if (date == listeTemp.get(x).getDate() && heure == listeTemp.get(x).getHeure()) {
				System.out.println(listeTemp.get(x).toString());
				return;
			}
		}
		System.out.println("Données innexistantes");
		
	}
	
	private static void diffTemp() {
		System.out.println("Entrer un date et une heure sous la forme jj/mm");
		String date = entree.nextLine();
		String heure = entree.nextLine();
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
				tempAnterieure();
			} else if (choix == 3) {
				diffTemp();
			} else {
				System.out.println("Entrée incorecte");
			}
		} while (choix != 0);
	}

}

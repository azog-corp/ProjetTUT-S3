/*
 * 
 */

package projet.src.thermometres3.outils;

import java.util.ArrayList;

/**
 * Classe de test permettant de v�rifier le bon fonctionnement des classes 
 * RechercheTemperature Temperature et OutilsTemperature
 */
public class TestRecupTemp {
	
	
	
	
	public static void main(String[] args) {
		
		RechercheTemperature test = new RechercheTemperature();
		ArrayList<Temperature> testListe = test.getListTemp();

		
		for (int i = 0; i < testListe.size(); i++) {
			System.out.println(testListe.get(i).toString());
			
		}
		System.out.println("verif date estValide : 01/11/2019 10:11:12");
		System.out.println(OutilsTemperature.estValide("01/11/2019 10:11:12"));
		System.out.println("verif date estValide : 01/11/2029 00:11:00");
		System.out.println(OutilsTemperature.estValide("01/11/2029 00:11:00"));
	} 
	
	
}

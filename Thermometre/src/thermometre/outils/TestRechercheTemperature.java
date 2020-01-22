package thermometre.outils;

import thermometre.outils.Temperature;

import java.util.ArrayList;
import java.util.Date;

import thermometre.outils.RechercheTemperature;

public class TestRechercheTemperature {
	
	private static ArrayList<Temperature> testTemp = new ArrayList<Temperature>();
	
	private static void testEditTemp() {
		RechercheTemperature.editTemp("fichierTempTest.txt");
		if (RechercheTemperature.getListTemp() != null) {
			System.out.println("Test OK pour editTemp");
		} else {
			System.out.println("Test pas OK");
		}
	}
	
	private static void testSupprimerTemp() {
		if (RechercheTemperature.supprimerTemp()) {
			System.out.println("Test OK pour supprimerTemp");
		} else {
			System.out.println("Test pas OK");
		}
	}
	
	private static void testSaveTemp() {
		
	}
	
	public static void testGetDerniereTemp() {
		Date test = RechercheTemperature.conversion("09/11/2019 20:11:12");
		if (testTemp.get(testTemp.size()-1).getDate().getTime() == test.getTime()) {
			System.out.println("La dernière date est bonne");
		} else {
			System.out.println("Problème avec la fonction");
		}
	}
	
	private static void testIntervalleOk() {
		String date1 = "01/11/2019 10:11:12 14.5";
		String date2 = "02/11/2019 20:11:12 15.5";
		String date3 = "03/11/2019 20:11:12 16.5";
		String date4 = "04/11/2019 20:11:12 10.4";
		String date5 = "05/11/2019 20:11:12 13.5";
		String date6 = "05/11/2019 20:11:12 10";
		
		if (RechercheTemperature.intervalleOk(date1, date2)) {
			System.out.println("Test avec intervalle valide OK");
		}
		if (!RechercheTemperature.intervalleOk(date3, date6)) {
			System.out.println("Test avec intervalle > 2 jours");
		}
		if (!RechercheTemperature.intervalleOk(date5, date4)) {
			System.out.println("Test avec intervalle négatif OK");
		}
	}
	
	private static void TestDateOk() {
		String date1 = "01/11/2019 10:11:12 14.5";
		String date2 = "02/11/2018 20:11:12 15.5";
		String date3 = "03/11/2022 20:11:12 16.5";
		
		if (RechercheTemperature.dateOk(date1)) {
			System.out.println("Test avec date correct OK");
		}
		if (!RechercheTemperature.dateOk(date2)) {
			System.out.println("Test avec date trop petite OK");
		}
		if (!RechercheTemperature.dateOk(date3)) {
			System.out.println("Test avec date trop grande OK");
		}
	}
	
	private static void testDateIntervalle() {
		ArrayList<Temperature> testIntervalle = new ArrayList<Temperature>();
		testIntervalle.add(testTemp.get(3));
		testIntervalle.add(testTemp.get(4));
		testIntervalle.add(testTemp.get(5));
		testIntervalle.add(testTemp.get(6));
		testIntervalle.add(testTemp.get(7));
		int dateBonne = 0;
		
		ArrayList<Temperature> intervalle = RechercheTemperature.dateIntervalle("03/11/2019 20:11:12 16.5", "06/11/2019 20:11:12 9");
		
			for (int x = 1 ; x < intervalle.size() ; x++) {
				System.out.println(testIntervalle.get(x-1).toString() + intervalle.get(x).toString());
				if (testIntervalle.get(x) == intervalle.get(x)) {
					dateBonne++;
				} else {
					System.out.println("test raté");
				}
			}
		if (dateBonne == 5) {
			System.out.println("test OK");
		}
	}
	
	private static void testSupprimerIntervalle() {
		
	}
	
	private static void testDateExiste() {
		String[] dateTest = {"03/11/2019 20:11:12", "03/11/2099 20:11:12"};
		
		if (RechercheTemperature.dateExiste(dateTest[0])) {
			System.out.println("dateExiste OK avec date valides");
		}
		if (!RechercheTemperature.dateExiste(dateTest[1])) {
			System.out.println("dateExiste OK avec date invalide");
		}
	}

	public static void main(String[] args) {
		RechercheTemperature.editTemp("fichierTempTest.txt");
		testTemp = RechercheTemperature.getListTemp();
		// testEditTemp();
		// testSupprimerTemp();
		// testSaveTemp();
		// TestDateOk();
		// testIntervalleOk();
		// testGetDerniereTemp();
		// testDateIntervalle(); // marche pas
		// testSupprimerIntervalle(); // Pas fait
		// testDateExiste(); // marche pas
	}

}

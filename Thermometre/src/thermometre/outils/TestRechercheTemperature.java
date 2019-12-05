package thermometre.outils;

import thermometre.outils.Temperature;

import java.util.ArrayList;
import java.util.Date;

import thermometre.outils.RechercheTemperature;

public class TestRechercheTemperature {
	
	private static ArrayList<Temperature> testTemp = new ArrayList<Temperature>();
	
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
	
	private static void TestDateIntervalle() {
		
	}

	public static void main(String[] args) {
		RechercheTemperature.editTemp("fichierTempTest.txt");
		testTemp = RechercheTemperature.getListTemp();
		// testGetDerniereTemp();
		// testIntervalleOk();
		TestDateOk();
		// TestDateIntervalle();
	}

}

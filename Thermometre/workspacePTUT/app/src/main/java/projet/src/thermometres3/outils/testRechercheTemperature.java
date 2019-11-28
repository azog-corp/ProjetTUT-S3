package projet.src.thermometres3.outils;



import java.util.ArrayList;
import java.util.Date;
import projet.src.thermometres3.outils.Temperature;
import projet.src.thermometres3.outils.RechercheTemperature;

public class testRechercheTemperature {
	
	private static ArrayList<Temperature> testTemp = new ArrayList<Temperature>();
	
	public static void testGetDerniereTemp() {
		Date test = RechercheTemperature.conversion("09/11/2019 20:11:12");
		if (testTemp.get(testTemp.size()-1).getDate().getTime() == test.getTime()) {
			System.out.println("La derni�re date est bonne");
		} else {
			System.out.println("Probl�me avec la fonction");
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
			System.out.println("Test avec intervalle n�gatif OK");
		}
	}

	public static void main(String[] args) {
		RechercheTemperature.editTemp("fichierTempTest.txt");
		testTemp = RechercheTemperature.getListTemp();
		// testGetDerniereTemp();
		testIntervalleOk();
	}

}

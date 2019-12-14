package projet.src.thermometres3;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurIntervalle;
import projet.src.thermometres3.outils.Temperature;

import static projet.src.thermometres3.RechercheTemperature.listeTemp;

/**
 * Classe de test de la méthode intervalleOK
 * @author Mathieu Capo
 *
 */
public class TestRechercheTemperature {
		private static ArrayList<Temperature> testTemp = new ArrayList<Temperature>();

		public static void testEditTemp() {
			System.out.println("TEST EDIT TEMP");
			for (int i = 0; i < listeTemp.size(); i++) {
				System.out.println(listeTemp.get(i));
			}
		}

		public static void testSupprimerTemp(Context myContext) {
			String ligne;
			try (BufferedReader fic = new BufferedReader(new FileReader(new File(
					myContext.getFilesDir() + "/fichierTemp.txt")))) { // Lecture du fichier
				System.out.println("DEBUT TEST supprimerTemp : si rien de s'affiche test concluant");
				while ((ligne = fic.readLine()) != null) {
					System.out.println(ligne);
				}
				System.out.println("FIN TEST supprimerTemp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		private static void testIntervalleOk() {
			String[][] dates = {
					{"02/01/2019 23:59:59","01/01/2019 00:00:00"},
					{"04/01/2019 00:00:00","01/01/2019 00:00:00"},
					{"03/02/2019 00:00:00","01/01/2019 00:00:00"},
					{"03/01/2029 00:00:00","01/01/2019 00:00:00"},
					{"03/01/2019 00:00:00","01/01/2019 00:00:00"},
					{"03/01/2019 00:00:00","01/01/2019 00:00:15"},
					{"03/01/2019 00:00:00","03/01/2019 00:00:00"},
			};
			// tableau contenant les résultats attendu pour les tests effectués
			boolean[] results = {true, false, false, false, false, true, true};

			// affichage des résultats
			System.out.print("Résultats obtenus : ");
			for (int i = 0; i < dates.length; i++) {
				try {
					System.out.print(RechercheTemperature.intervalleOk(dates[i][0],dates[i][1]) + " ");
				} catch (ErreurIntervalle e) {
					System.out.println("testNOK");
				}
			}
			System.out.print("\nrésultats supposés : ");
			for(int y = 0; y < results.length; y++) {
				System.out.print(results[y] + " ");
			}
		}

		private static void TestDateOk() {
			String date1 = "01/11/2019 10:11:12 14.5";
			String date2 = "02/11/2018 20:11:12 15.5";
			String date3 = "03/11/2022 20:11:12 16.5";
			try {
				if (RechercheTemperature.dateOk(date1)) {
					System.out.println("Test avec date correct OK");
				}
			} catch (ErreurDate e) {
				System.out.println("Test avec date correct NOK");
			}

			try {
				if (!RechercheTemperature.dateOk(date2)) {
					System.out.println("Test avec date trop petite OK");
				}
			} catch (ErreurDate e) {
				System.out.println("Test avec date trop petite NOK");
			}

			try {
				if (!RechercheTemperature.dateOk(date3)) {
					System.out.println("Test avec date trop grande OK");
				}
			} catch (ErreurDate e) {
				System.out.println("Test avec date trop grande NOK");
			}
		}

		private static void testDateIntervalle() {
			ArrayList<Temperature> testIntervalle = new ArrayList<Temperature>();
			Temperature[] dates = {
					new Temperature("02/01/2019 17:59:59 -3"),
					new Temperature("02/01/2019 18:00:00 2"),
					new Temperature("02/01/2019 19:00:00 4"),
					new Temperature("02/01/2019 20:00:00 5"),
					new Temperature("03/01/2019 02:00:00 6"),
					new Temperature("03/01/2019 11:10:00 7"),
					new Temperature("04/01/2019 00:00:00 8")
			};
			for (int i = 0; i < dates.length; i++) {
				listeTemp.add(dates[i]);
			}

			int dateRecup = 0;
			ArrayList<Temperature> intervalle = new ArrayList<Temperature>();
			try {
				intervalle = RechercheTemperature.dateIntervalle("02/01/2019 18:00:00", "03/01/2019 11:10:00");
			} catch (ErreurDate e) {
				//bouchon
			}
			for (int x = 0 ; x < intervalle.size() ; x++) {
				System.out.println(intervalle.get(x));
				dateRecup++;
			}
			if (dateRecup == 5) {
				System.out.println("Test OK : Bon nombre de date recupere");
			} else {
				System.out.println("Test NOK : pas assez de ou trop de date " + dateRecup + " recupere sur 5 attendues");
			}
		}

		public static void main(String[] args) {
			testEditTemp();
			TestDateOk();
			testIntervalleOk();
			testDateIntervalle(); // marche pas
		}
}

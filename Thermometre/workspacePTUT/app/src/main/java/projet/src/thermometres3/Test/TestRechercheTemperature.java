package projet.src.thermometres3.Test;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurIntervalle;
import projet.src.thermometres3.outils.RechercheTemperature;
import projet.src.thermometres3.outils.Temperature;
import static projet.src.thermometres3.outils.RechercheTemperature.listeTemp;

/**
 * Classe de test de la méthode intervalleOK
 * @author Mathieu Capo
 *
 */
public class TestRechercheTemperature {
	/**TEST de la fonction edit temp
	 * affiche toutes les temperatures cree apres lexecution de la fonction
	 * pour les comparer avec celles comprises dans le fichier
	 * Pour verifier que la fonction recupere bien toutes les temperatures
	 */
	public static void testEditTemp() {
		System.out.println("TEST EDIT TEMP");
		for (int i = 0; i < listeTemp.size(); i++) {
			System.out.println(listeTemp.get(i));
		}
	}

	/**
	 * TEST de la fonction supprimer
	 * Verifie que la fonction supprime bien toutes les lignes
	 * Lit le fichier apres la suppression pour verifier que auncune ligne n'est encore presente
	 * @param myContext contexte de l'application au lancement de la fonction
	 */
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


	/**
	 * TEST qui verifie le fonctionnement de la fonction avec des cas d'erreurs
	 * Fonction: intervalleOk
	 */
	private static void testIntervalleOk() {
			String[][] dates = {
					{"02/01/2019 23:59:59","01/01/2019 00:00:00"},
					{"04/02/2019 00:00:00","01/01/2019 00:00:00"},
					{"03/01/2029 00:00:00","01/01/2019 00:00:00"},
					{"03/01/2019 00:00:00","03/01/2019 00:00:00"},
			};
			int testOk = 0;
			System.out.println("TEST INTERVALLE VALIDE");
			try {
				RechercheTemperature.intervalleOk(dates[0][1],dates[0][0]);
				testOk++;
				System.out.println(dates[0][0] + " et " + dates[0][1] + " represente bien un intervalle valide TEST OK");
			} catch (ErreurIntervalle e) {
				System.out.println(dates[0][0] + " et " + dates[0][1] + " represente un intervalle valide TEST NOK");
			}
			// affichage des résultats
			System.out.println("TEST INTERVALLE NON VALIDE");
			System.out.println("Résultats obtenus : ");

			for (int i = 1; i < dates.length; i++) {
				try {
					RechercheTemperature.intervalleOk(dates[i][1],dates[i][0]);
					System.out.println(dates[i][0] + " et " + dates[i][1] + " ne represente pas un intervalle valide TEST NOK");
				} catch (ErreurIntervalle e) {
					System.out.println(dates[i][0] + " et " + dates[i][1] + " represente bien un intervalle non valide TEST OK");
					testOk++;
				}
			}
			System.out.println(testOk + " tests reussi sur " + dates.length);
		}

	/**
	 * TEST de la fonction dateOk verifie tous les cas d'erreurs
	 * En testant avec des dates correctes , trop grandes, trop petites
	 */
	private static void TestDateOk() {
		String date1 = "01/11/2019 10:11:12 14.5";
		String date2 = "02/11/1900 20:11:12 15.5";
		String date3 = "03/11/2022 20:11:12 16.5";
		/* Test avec une date si la fonction throw une ErreurDate cela signifie que la fonction
		* ne marche pas correctement*/
		int nbTestOk = 0;
		try {
			RechercheTemperature.dateOk(date1);
			System.out.println("Test avec date correct OK -> date : "+ date1);
			nbTestOk++;
		} catch (ErreurDate e) {
			System.out.println("Test avec date correct NOK");
		}
		/* Test avec une date trop petite si la fonction throw une ErreurDate cela signifie
		 * Que la fonction marche correctement est a bien vu l'erreur
		 */
		try {
			RechercheTemperature.dateOk(date2);
			System.out.println("Test avec date trop petite NOK");
		} catch (ErreurDate e) {
			System.out.println("Test avec date trop petite OK ->" + date2);
			nbTestOk++;
		}

		/* Test avec une date trop grande si la fonction throw une ErreurDate cela signifie
		 * Que la fonction marche correctement est a bien vu l'erreur
		 */
		try {
			RechercheTemperature.dateOk(date3);
			System.out.println("Test avec date trop grande NOK");
		} catch (ErreurDate e) {
			System.out.println("Test avec date trop grande OK ->" + date3);
			nbTestOk++;
		}
		System.out.println(nbTestOk + " reussi sur " + 3);
	}


	/**
	 * TEST dateIntervalle
	 * verifie que la fonction ne renvoi que les dates comprises dans l'intervalle
	 * Test visuel
	 * affiche toutes temperatures et verifie que la fonction de renvoi que les dates voulues
	 */
	private static void testDateIntervalle() {
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
		 TestDateOk();
		 testIntervalleOk();
		 testDateIntervalle();
	}
}

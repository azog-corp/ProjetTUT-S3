package projet.src.thermometres3.outils;

import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurIntervalle;

import static projet.src.thermometres3.RechercheTemperature.intervalleOk;

/**
 * Classe de test de la méthode intervalleOK
 * @author Mathieu Capo
 *
 */
public class TestRechercheTemperature {

	public static void main(String args[]) {
		// ensemble de données à tester
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
				System.out.print(intervalleOk(dates[i][0],dates[i][1]) + " ");
			} catch (ErreurIntervalle e) {
				System.out.println("testNOK");
			}
		}
		System.out.print("\nrésultats supposés : ");
		for(int y = 0; y < results.length; y++) {
			System.out.print(results[y] + " ");
		}
	}
}
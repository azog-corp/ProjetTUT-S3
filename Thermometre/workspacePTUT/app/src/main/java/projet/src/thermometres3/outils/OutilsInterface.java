package projet.src.thermometres3.outils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import projet.src.thermometres3.Erreur.ErreurIntervalle;

import static projet.src.thermometres3.outils.RechercheTemperature.conversion;
import static projet.src.thermometres3.outils.RechercheTemperature.getNouvelleTemp;

public class OutilsInterface {

    /**
     * Lit le fichier derniereCo.txt
     * @param myContext Contexte de l'application au moment de l'execution
     * @returnretourne la ligne en String de la derniere connexion
     */
    public static String getLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt";
        try (BufferedReader fic = new BufferedReader(new FileReader(new File(derniereCo)))) { // Lecture du fichier
            return fic.readLine(); // retourne la ligne date
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ""; //bouchon la date ne peut theoriquement pas etre fausse
    }

    /**
     * Fonction qui permet d'obtenir la date a un instant
     * @return la date en String avec le format dd/MM/yyyy HH:mm:ss
     */
    public static String getDateActuelle() {
        Date date = new Date(); // Recupere la date Actuelle
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // defini le format de la date
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // Defini la zone de la date pour que l'heure soit correcte
        return sdf.format(date);
    }
    /**
     * Fonction qui permet d'obtenir la date 2 jours avant
     * @return la date en String avec le format dd/MM/yyyy HH:mm:ss
     */
    public static String getDate2JoursPrec(){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // defini le format de la date
        Date date = new Date(System.currentTimeMillis() - (2 * DAY_IN_MS));
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // Defini la zone de la date pour que l'heure soit correcte
        return sdf.format(date);
    }
    /**
     * Fonction qui permet d'obtenir la date 1 jour avant
     * @return la date en String avec le format dd/MM/yyyy HH:mm:ss
     */
    public static String getDate1JourPrec() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // defini le format de la date
        Date date = new Date(System.currentTimeMillis() - (1 * DAY_IN_MS));
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // Defini la zone de la date pour que l'heure soit correcte
        return sdf.format(date);
    }

}

package projet.src.thermometres3;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static projet.src.thermometres3.RechercheTemperature.NOM_FICHIER;
import static projet.src.thermometres3.RechercheTemperature.getNomFichier;
import static projet.src.thermometres3.RechercheTemperature.getNouvelleTemp;

public class OutilsInterface {
    public static String getLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt";
        try (BufferedReader fic = new BufferedReader(new FileReader(new File(derniereCo)))) { // Lecture du fichier
            return fic.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ""; //stub la date ne peut theoriquement pas etre fausse / lue
    }

    /**
     * Creer le fichier derniere Connexion/ ou le met a jour
     */
    public static void creerFichierLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) { // Lecture du fichier
            System.out.println(getDateActuelle()); // affichage debug
            fic.write(getDateActuelle()); // ecrit dans le fichier la date
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*test -- test visuel
        System.out.println("TEST creerFichierLastCo");
        TestOutilsInterface.testLastCo(getApplicationContext());
         */
    }

    /**
     * Creer le fichier des temperatures
     * Lors du lancement de l'application pour la premiere fois
     * Lit le fichier des temperatures dans le dossier assets
     * Creer le fichier fichierTem.txt et y place les temperatures
     * Fonctionnement necessaire car il est impossible de modifier des fichiers present
     * dans le dossier assets. On creer donc les fichiers dans la memoire de l'appareil
     * android
     */
    public static void creerFichierTemperatures(Context myContext) {
        String ligne;
        try (BufferedWriter fichEcri = new BufferedWriter(new FileWriter(new File( // Ouvre / creer le fichier fichierTemp.txt
                myContext.getFilesDir() + "/fichierTemp.txt")))) {
            BufferedReader fichLir = new BufferedReader(new InputStreamReader( // Ouvre le fichier fichierTemp dans le dossier assets
                    myContext.getAssets().open(getNomFichier())));
            while (((ligne = fichLir.readLine()) != null)) { // Lecture des temperatures
                System.out.println(ligne); // Inutile pour debug
                fichEcri.write(ligne + "\n"); //Ecriture dans le fichier en memoire
            }
            fichLir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui permet de mettre a jour le fichier des temperatures
     * Pour cela ouvre le fichier contenant les nouvelles temperatures et met a jour
     * le fichier des temperatures
     */
    public static void majFichierTemp(Context myContext) {
        String ligne;
        try (BufferedWriter fichEcri= new BufferedWriter(new FileWriter(new File(
                myContext.getFilesDir()+"/fichierTemp.txt")))) { // ouverture fichier temperatures
            BufferedReader fichLir = new BufferedReader(new InputStreamReader(
                    myContext.getAssets().open(getNouvelleTemp()))); // ouverture fichier Nouvelles temperatures
            while ((ligne = fichLir.readLine()) != null) { // Lecture fichier nouvellesTemperatures
                fichEcri.write(ligne + "\n"); // ecrit dans le fichier temperature
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDateActuelle() {
        Date date = new Date(); // Recupere la date Actuelle
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // defini le format de la date
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // Defini la zone de la date pour que l'heure soit correcte
        return sdf.format(date);
    }
}

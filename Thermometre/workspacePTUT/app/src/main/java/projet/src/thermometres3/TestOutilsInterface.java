package projet.src.thermometres3;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestOutilsInterface {
    /**
     * TEST qui verifie que l'ecriture dans le fichier c'est bien produite
     * @param myContext
     */
    public static void testLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedReader fic = new BufferedReader(new FileReader(new File(derniereCo)))) { // Lecture du fichier
            String ligne;
            while((ligne = fic.readLine()) != null) {
                System.out.println(ligne);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TEST qui lit le fichier gichierTemp.txt et verifie que le fichier
     * a bien ecrit les temperatures
     * @param myContext
     */
    public static void testFichierTemp(Context myContext){
        System.out.println("Test fichier temp");
        String fichTemp = myContext.getFilesDir()+"/fichierTemp.txt"; // defini le chemin du fichier
        try (BufferedReader fic = new BufferedReader(new FileReader(new File(fichTemp)))) { // Lecture du fichier
            String ligne;
            while((ligne = fic.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testGetDateActuelle() {
        Date test = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date res = null;
        try {
            res = sdf.parse(OutilsInterface.getDateActuelle());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(res + " " + test + " leger decallage du au temps d'execution");
    }

    private static void testDate2joursPrec() {
        Date test = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(sdf.format(test) + " -> 2 jours avant :" + OutilsInterface.getDate2JoursPrec());
    }

    public static void main(String[] args) {
        testGetDateActuelle();
        testDate2joursPrec();
    }


}

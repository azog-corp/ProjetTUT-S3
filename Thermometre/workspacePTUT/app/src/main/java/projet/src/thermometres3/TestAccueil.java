package projet.src.thermometres3;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import projet.src.thermometres3.outils.RechercheTemperature;

public class TestAccueil {
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


}

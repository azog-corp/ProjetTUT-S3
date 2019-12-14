package projet.src.thermometres3;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TestOutilsInterface {
    /**
     * TEST qui verifie que l'ecriture dans le ficheir c'est bien produite
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


}

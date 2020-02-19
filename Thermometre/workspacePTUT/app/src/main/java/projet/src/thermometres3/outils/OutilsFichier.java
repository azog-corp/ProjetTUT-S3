package projet.src.thermometres3.outils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static projet.src.thermometres3.outils.RechercheTemperature.conversion;

public class OutilsFichier {

    private final static String NOM_FICHIER = "fichierTemp.txt";

    public static void ecrireFinFichier(Context myContext, ArrayList<String> tempAEcrire) {
        System.out.println("ECRITURE");
        for(int i = 0; i < tempAEcrire.size();i++) {
            // Positionnement en fin de fichier pour ne pas écraser les températures déjà présentes
            try (BufferedWriter fichier = new BufferedWriter(new FileWriter(
                    myContext.getFilesDir() + "/fichierTemp.txt", true))) {
                String[] temp = OutilsCommunication.decoupageRep(tempAEcrire.get(i));
                for(int j = 0; j < temp.length-1; j++) {
                    System.out.println(temp[j]);
                    fichier.write(temp[j] + "\n");
                }
            } catch (IOException e) {
                System.err.println("ERREUR ECRITURE FIN FICHIER");
                e.printStackTrace();
            }
        }

    }


    public static boolean supprimerIntervalle(Context myContext,String dateInf, String dateSup) {
        try {
            Date dateInfformate = conversion(dateInf);
            Date dateSupformate = conversion(dateSup);
            ArrayList<String> lignehorsintervalle = new ArrayList<String>();
            String ligne;
            Temperature atester;
            BufferedReader fichier = new BufferedReader(new FileReader( myContext.getFilesDir() + "/fichierTemp.txt"));
            while (((ligne = fichier.readLine()) != null)) {
                atester = new Temperature(ligne);
                if(!(atester.getDate().compareTo(dateInfformate) >= 0 && atester.getDate().compareTo(dateSupformate) <= 0)) {
                    lignehorsintervalle.add(ligne);
                }
            }
            ecrireFinFichier(myContext,lignehorsintervalle); // on reecrit els lignes correctes
            return true;
        } catch(ParseException e) {
            //stub impossible verifie avant
        } catch (IOException e) {
            //stub impossible verifie avant
        }
        return false;
    }


    /**
     * Supprime toutes les températures dans le fichier fichierTemp
     * @return true si le fichier a bien ete supprimee
     * 			false si erreur lors de la suppression
     */
    public static boolean supprimerTemp(Context myContext) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(myContext.openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
			/*lorsque l'application se charge les températures sont automatiquement chargées
            Pour les faire disparaitre et ne plus etre valable on  reinitialise la liste des temperatures en memoire*/
            RechercheTemperature.listeTemp = new ArrayList<Temperature>();
        }
        catch (Exception ex) {
            System.out.println("Error clear file fichierTemp.txt");
            return false;
        }
        return true;
    }
}

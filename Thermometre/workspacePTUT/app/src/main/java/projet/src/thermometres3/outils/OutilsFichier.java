package projet.src.thermometres3.outils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static projet.src.thermometres3.outils.RechercheTemperature.conversion;

public class OutilsFichier {

    private final static String NOM_FICHIER = "fichierTemp.txt";

    public static void ecrireFinFichier(Context myContext, ArrayList<String> paquets) {
        System.out.println("ECRITURE");
            // Positionnement en fin de fichier pour ne pas écraser les températures déjà présentes
            try (BufferedWriter fichier = new BufferedWriter(new FileWriter(
                    myContext.getFilesDir() + "/fichierTemp.txt", true))) {
                for(int j = 0; j < paquets.size(); j++) {
                    System.out.println("ecrit : " + paquets.get(j));
                    fichier.write(paquets.get(j) + "\n");
                }
            } catch (IOException e) {
                System.err.println("ERREUR ECRITURE FIN FICHIER");
                e.printStackTrace();
            }
    }


    /**
     * Supprime toutes les températures comprises dans l'intervalle formé par les deux dates entrées
     * dans le fichier fichierTemp
     * @param myContext
     * @param dateInf première date
     * @param dateSup seconde date
     * @return
     */
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
                } else { // la ligne doit etre supprimée. On ajoute une ligne avec la temperature a -300 pour ne plus l'afficher et ainsi faire un trou dans la courbe
                    lignehorsintervalle.add(atester.getDate() + " -300");
                }
            }
            ecrireFinFichier(myContext,lignehorsintervalle); // on reecrit les lignes correctes
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


    /**
     * Creer le fichier des temperatures
     * Lors du lancement de l'application pour la premiere fois
     * Lit le fichier des temperatures dans le dossier assets
     * Creer le fichier fichierTem.txt et y place les temperatures
     * Fonctionnement necessaire car il est impossible de modifier des fichiers present
     * dans le dossier assets. On creer donc les fichiers dans la memoire de l'appareil
     * android
     * @param myContext Contexte de l'application au moment de l'execution
     */
    public static void creerFichierTemperatures(Context myContext) {
        String ligne;
        try (BufferedWriter fichEcri = new BufferedWriter(new FileWriter(new File( // Ouvre / creer le fichier fichierTemp.txt
                myContext.getFilesDir() + "/fichierTemp.txt")))) {
            fichEcri.write(  ""); //Ecriture dans le fichier en memoire
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creer le fichier derniere Connexion / ou le met a jour
     * @param myContext Contexte de l'application au moment de l'execution
     */
    public static void majFichierLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) { // Lecture du fichier
            System.out.println("MAJ DATE"+OutilsInterface.getDateActuelle()); // affichage debug
            fic.write(OutilsInterface.getDateActuelle()); // ecrit dans le fichier la date
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creer le fichier derniere Connexion / ou le met a jour
     * @param myContext Contexte de l'application au moment de l'execution
     */
    public static void creerFichierLastCo(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) { // Lecture du fichier
            System.out.println("CREATION LAST CO " + OutilsInterface.getDate1JourPrec());
            fic.write(OutilsInterface.getDate1JourPrec());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*test -- test visuel
        System.out.println("TEST creerFichierLastCo");
        TestOutilsInterface.testLastCo(getApplicationContext());
         */
    }

    /**Permet de changer la date de derniere connexion a une date egale a : 1jour avant
     * Pour permettre a l'utilisateur de lancer la fonction derniere connexion.
     */
    public static void majDateCo1J(Context myContext) {
        String derniereCo = myContext.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) { // Lecture du fichier
            System.out.println("MAJ DATE"+OutilsInterface.getDate1JourPrec()); // affichage debug
            fic.write(OutilsInterface.getDate1JourPrec()); // ecrit dans le fichier la date
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

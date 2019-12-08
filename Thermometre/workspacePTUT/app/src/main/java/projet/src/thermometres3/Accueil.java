package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import projet.src.thermometres3.outils.Temperature;

import static projet.src.thermometres3.RechercheTemperature.NOM_FICHIER;
import static projet.src.thermometres3.RechercheTemperature.NOUVELLE_TEMP;


public class Accueil extends AppCompatActivity {
    /**
     * A la creation du fichier
     * initialise les boutons
     * et met a jour le fichier temperatures avec les nouvelles
     * temperatures disponibles
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button boutonMenu = (Button) findViewById(R.id.btnMenu);

        boutonMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openMenu();
            }
        });

        /* Initialisation du fichier */
        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt";
        File f = new File(derniereCo);

        /* On verifie que le fichier derniereCo existe
         * S' il n'existe pas cela signifie que c'est le premiere lancement de l'application
         * Il faut donc recuperer les temperatures et creer le fichier de temperatures
         */
        if(!f.exists()) { // Si le fichier n'existe pas
            creerFichierLastCo(); // creer fichier derniereConnexion
            creerFichierTemperatures(); // creer fichier Temperature
        } else {
            /*Si le fichier existe alors on recupere
             * les nouvelles temperatures
             */
            majFichierTemp();

        }
        RechercheTemperature.editTemp(getApplicationContext());//Ajoute les nouvelles temperatures
    }

    /**
     * A la fermeture de l'application met a jour
     * le fichier derniereConnexion
     */
    public void finish () {
        System.out.println("MAJ");
        creerFichierLastCo();
    }

    /**
     * Fonction qui permet de mettre a jour le fichier des temperatures
     * Pour cela ouvre le fichier contenant les nouvelles temperatures et met a jour
     * le fichier des temperatures
     */
    private void majFichierTemp() {
        String ligne;
        try (BufferedWriter fichEcri= new BufferedWriter(new FileWriter(new File(
                Accueil.this.getFilesDir()+"/fichierTemp.txt")))) { // ouverture fichier temperatures
            BufferedReader fichLir = new BufferedReader(new InputStreamReader(
                    Accueil.this.getAssets().open(NOUVELLE_TEMP))); // ouverture fichier Nouvelles temperatures
            while ((ligne = fichLir.readLine()) != null) { // Lecture fichier nouvellesTemperatures
                fichEcri.write(ligne + "\n"); // ecrit dans le fichier temperature
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void creerFichierTemperatures() {
        String ligne;
        try (BufferedWriter fichEcri = new BufferedWriter(new FileWriter(new File( // Ouvre / creer le fichier fichierTemp.txt
                Accueil.this.getFilesDir()+"/fichierTemp.txt")))) {
            BufferedReader fichLir = new BufferedReader(new InputStreamReader( // Ouvre le fichier fichierTemp dans le dossier assets
                    Accueil.this.getAssets().open(NOM_FICHIER)));
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
     * Creer le fichier derniere Connexion/ ou le met a jour
     *
     */
    private void creerFichierLastCo() {
        Date date = new Date(); // Recupere la date Actuelle
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // defini le format de la date
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // Defini la zone de la date pour que l'heure soit correcte

        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt"; // defini le chemin du fichier
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) { // Lecture du fichier
            System.out.println(sdf.format(date)); // affichage debug
            fic.write(sdf.format(date)); // ecrit dans le fichier la date
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre la page menu
     */
    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent); // ouvre la page menu
    }
}

package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt";
        File f = new File(derniereCo);

        /* On verifie que le fichier derniereCo existe
         * S' il n'existe pas cela signifie que c'est le premiere lancement de l'application
         * Il faut donc recuperer les temperatures et creer le fichier de temperatures
         */
        if(!f.exists()) { // Si le fichier n'existe pas
            creerFichierLastCo();
            creerFichierTemperatures();
        } else {
            /*Si le fichier existe alors on recupere
             * les nouvelles temperatures
             */
            majFichierTemp();
            System.out.println("MAJ");
            creerFichierLastCo();
        }
        String ligne;
        try {
            BufferedReader fichier = new BufferedReader(new FileReader(Accueil.this.getFilesDir()+"/derniereCo.txt"));
            while((ligne = fichier.readLine()) != null) {
                System.out.println(ligne);
            }
            fichier.close();
        } catch (FileNotFoundException e) {
            System.out.println("il est ou le fichier frere");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RechercheTemperature.editTemp(getApplicationContext());//Ajoute les nouvelles temperatures
    }

    /**
     * Methode qui met a jour le fichier derniereCo.txt
     * Pour cela ecrase l'ancienne dater de derniere connexion par la nouvelle
     * correspondant a la dater actuelle
     *
    private void majFichierDerniereCo() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt";
        File f = new File(derniereCo);
        String ligne;

        try (BufferedReader fichier = new BufferedReader(new FileReader(new File(
                Accueil.this.getFilesDir()+"/derniereCo.txt")))) {
            FileOutputStream fos = new FileOutputStream(f);
            //Lit la ligne deja inscrite et
            while((ligne = fichier.readLine()) != null) {
                System.out.println("INITIAL");
                System.out.println(ligne);
                fos.write((sdf.format(date)+ "\n").getBytes() );
            }
        } catch (IOException e) {
            System.out.println("IO");
        }
    }*/

    private void majFichierTemp() {
        String ligne;
        try (BufferedWriter fichEcri= new BufferedWriter(new FileWriter(new File(Accueil.this.getFilesDir()+"/fichierTemp.txt")))) {
            BufferedReader fichLir = new BufferedReader(new InputStreamReader(
                    Accueil.this.getAssets().open(NOUVELLE_TEMP)));
            while ((ligne = fichLir.readLine()) != null) {
                fichEcri.write(ligne + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creerFichierTemperatures() {
        String ligne;
        try (BufferedWriter fichEcri = new BufferedWriter(new FileWriter(new File(Accueil.this.getFilesDir()+"/fichierTemp.txt")))) {
            BufferedReader fichLir = new BufferedReader(new InputStreamReader(
                    Accueil.this.getAssets().open(NOM_FICHIER)));
            while (((ligne = fichLir.readLine()) != null)) {
                System.out.println(ligne);
                fichEcri.write(ligne + "\n");
            }
            fichLir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creerFichierLastCo() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt";
        File f = new File(derniereCo);
        try (BufferedWriter fic = new BufferedWriter(new FileWriter(new File(derniereCo)))) {
                fic.write(sdf.format(date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}

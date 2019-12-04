package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import projet.src.thermometres3.outils.Temperature;


public class Accueil extends AppCompatActivity {

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

        System.out.println(Accueil.this.getFilesDir() + " " + getAssets().toString());
        System.out.println(getPackageName());
        RechercheTemperature.nouvellesTemps = Accueil.this.getFilesDir().getAbsolutePath()+ "/" + RechercheTemperature.NOUVELLE_TEMP;

        String ligne;
        try {
            BufferedReader fichier = new BufferedReader(new FileReader(Accueil.this.getFilesDir()+"/text.txt"));
            System.out.println(Accueil.this.getFilesDir()+"/text.txt");
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
        try (FileOutputStream fos = new FileOutputStream( new File(Accueil.this.getFilesDir()+"/text.txt"))) {
            System.out.println("fichier trouve");
            for (int x = 0 ; x < 4 ; x++) {
                fos.write("test".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RechercheTemperature.addTemp(getApplicationContext());//Ajoute les nouvelles temperatures
        /*try { // déclaration et création de l'objet fichier
            System.out.println(System.getProperty("user.dir"));
            BufferedReader fichier = new BufferedReader(new FileReader("projet.src.thermometres3/files/fichierTemp.txt"));
            while (((ligne = fichier.readLine()) != null)) {
                System.out.println(ligne);
            }
            fichier.close();
            // fermeture du fichier automatique avec try-with-ressource
        } catch (IOException ex) {
            //TODO faire une exception pour que linterface puisse savoir
            System.out.println("Problème fichierTemp.txt");
            // problème d'accès au fichier
        }*/
    }

    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}

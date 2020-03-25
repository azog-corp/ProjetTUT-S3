package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Test.TestRechercheTemperature;
import projet.src.thermometres3.outils.OutilsCommunication;
import projet.src.thermometres3.outils.OutilsFichier;
import projet.src.thermometres3.outils.OutilsInterface;
import projet.src.thermometres3.outils.RechercheTemperature;

public class Menu extends AppCompatActivity {

    public static String texteRecu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /* Definitions des boutons */
        Button btnGraphe = (Button) findViewById(R.id.btnGraph);
        Button btnAccueil = (Button) findViewById(R.id.btnAccueil);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        /* Lorsque l'utilisateur clique sur le bouton Graphe
         * Ouvre la page graphique
         */
        btnGraphe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openGraphe();
            }
        });

        /* Lorsque l'utilisateur clique sur le bouton Supprimer les temperatures
         * Affiche une boite de dialogue qui demande a l'utilisateur si celui
         * Souhaite supprimer les temperatures
         */
        btnDelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openDelete();
            }
        });

        /* Lorsque l'utilisateur clique sur le bouton Accueil
         * Ouvre la page d'accueil
         */
        btnAccueil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openAccueil();
            }
        });
    }

    /**
     * Ouvre la page supprimer
     */
    private void openDelete() {
        Intent intent = new Intent(this, Supprimer.class);
        startActivity(intent);
    }

    /**
     * Ouvre la page accueil
     */
    private void openAccueil(){
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }

    /**
     * Ouvre la page graphe
     */
    private void openGraphe() {
        Intent intent = new Intent(this, Graphe.class);
        startActivity(intent);
    }
    //TODO RESEAU faire bouton actualisation + lien vers page supprimer
}

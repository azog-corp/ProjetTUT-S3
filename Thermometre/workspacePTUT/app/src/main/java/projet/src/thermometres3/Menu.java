package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.outils.RechercheTemperature;

public class Menu extends AppCompatActivity {

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
     * Methode qui ouvre une boite de dialogue demandant a l'utilisateur
     * si celui ci souhaite supprimer les temperatures de l'application
     */
    private void openDelete() {
        /* Affichage de la boite de dialogue */
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Etes vous sur ?")
                .setContentText("Vous ne pourrez pas recuperer les temperatures apres suppression")
                .setConfirmText("Supprimer")
                // definition des actions apres appui sur le bouton pour de confirmation
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        /* Supprime les temperatures */
                        RechercheTemperature.supprimerTemp(getApplicationContext());
                        /* Affiche message pour confirmer suppression */
                        new SweetAlertDialog(Menu.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Confirmation")
                                .setContentText("Temperature supprimees")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation(); // ferme la boite de dialog
                                    }
                                }).show();
                                sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Annuler", new SweetAlertDialog.OnSweetClickListener() { // definition des actions apres appui sur le bouton pour annuler
                    /* Fermeture de la boite de dialog */
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
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
}

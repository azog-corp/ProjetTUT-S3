package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnGraphe = (Button) findViewById(R.id.btnGraph);
        Button btnAccueil = (Button) findViewById(R.id.btnAccueil);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        btnGraphe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openGraphe();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openDelete();
            }
        });

        btnAccueil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openAccueil();
            }
        });
    }

    private void openDelete() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Etes vous sur ?")
                .setContentText("Vous ne pourrez pas recuperer les temperatures apres suppression")
                .setConfirmText("Supprimer")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //TODO rajouter suppression
                        RechercheTemperature.supprimerTemp();
                        new SweetAlertDialog(Menu.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Confirmation")
                                .setContentText("Temperature supprimees")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                                sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Annuler", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
    private void openAccueil(){
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }

    private void openGraphe() {
        Intent intent = new Intent(this, Graphe.class);
        startActivity(intent);
    }
}

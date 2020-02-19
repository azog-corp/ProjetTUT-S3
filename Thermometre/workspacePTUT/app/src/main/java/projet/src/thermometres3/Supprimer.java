package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.outils.OutilsFichier;
import projet.src.thermometres3.outils.OutilsInterface;

public class Supprimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer);
    }

    public void toutSupprimer(View view) {
        String message;
        if(OutilsFichier.supprimerTemp(getApplicationContext())) {
            message = "Succès : Les températures ont bien été supprimées";
        } else {
            message = "Erreur : Impossible de supprimer les températures";
        }
        alert(message);
    }

    public void supprimerPartie(View view) {
        String message;
        TextView dateDebut = findViewById(R.id.borneSUP);
        TextView dateFin = findViewById(R.id.borneINF);
        if(OutilsFichier.supprimerIntervalle(getApplicationContext(),
                dateDebut.toString(),
                dateFin.toString())) {
            message = "Succès : Les températures ont bien été supprimées";
        } else {
            message = "Erreur : Impossible de supprimer les températures";
        }
        alert(message);

    }

    public void alert(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Date")
                .setContentText(message)
                .setConfirmText("OK").show();
    }

    //TODO RESEAU
}

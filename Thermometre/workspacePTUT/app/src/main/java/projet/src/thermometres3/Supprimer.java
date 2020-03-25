package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurIntervalle;
import projet.src.thermometres3.outils.OutilsFichier;
import projet.src.thermometres3.outils.RechercheTemperature;

import static projet.src.thermometres3.outils.RechercheTemperature.dateOk;
import static projet.src.thermometres3.outils.RechercheTemperature.intervalleOk;

public class Supprimer extends AppCompatActivity {

    Button btnToutSupp;
    Button btnSuppInter;
    EditText borneInf;
    EditText borneSup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer);

        btnToutSupp = (Button) findViewById(R.id.toutSupprimer);
        btnSuppInter = (Button) findViewById(R.id.partSupprimer);
        borneInf = (EditText) findViewById(R.id.borneINF);
        borneSup = (EditText) findViewById(R.id.borneSUP);

    }


    public void toutSupprimer(View view) {

        //Si des temperatures existes
        if (RechercheTemperature.getListTemp().size() != 0) {
            OutilsFichier.supprimerTemp(getApplicationContext());
        } else { //sinon message erreur
            messageErreurListeDate();
        }
    }

    public void supprimerPartie(View view) {
        String dateInf = borneInf.getText().toString();
        String dateSup = borneSup.getText().toString();

        try {
            //Verification validite des dates
            dateOk(dateInf);
            dateOk(dateSup);
            //Verification intervalle valide
            intervalleOk(dateInf,dateSup);

            //Si des temperatures existes
            if (RechercheTemperature.getListTemp().size() != 0) {
                OutilsFichier.supprimerIntervalle(getApplicationContext(), dateInf, dateSup);
            } else { //sinon message erreur
                messageErreurListeDate();
            }
        }catch (ErreurIntervalle e) { //l'intervalle n'est pas valide
            messageErreurIntervalle();
        }catch(ErreurDate e) {//les dates ne sont pas valide
            messageErreurDate();
        }
    }

    /**
     * Message d'erreur affiche quand la date est incorrecte -> format non respecte
     * ou date impossible
     */
    public void messageErreurDate() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Date")
                .setContentText("Erreur: Dates non valides, celles-ci doivent respecter le format suivant" +
                        "(format: dd/MM/yyyy HH:mm:ss) et doivent être inférieures à la date actuelle et supérieures" +
                        " au 01/01/2000 ")
                .setConfirmText("OK").show();
    }

    /**
     * Message d'erreur affiche quand l'intervalle est incorrect
     */
    public void messageErreurIntervalle() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Intervalle")
                .setContentText("Erreur: L'intervalle entré n'est pas valide, il " +
                        "doit être inférieur à 2 jours. Les 2 dates doivent être différentes " +
                        "et ordonnées")
                .setConfirmText("OK").show();

    }

    /**
     * Message d'erreur affiche quand aucune temperature n'existe dans l'intervalle
     */
    public void messageErreurListeDate() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Liste Températures")
                .setContentText("Erreur: Aucune température existante dans l'intervalle saisi")
                .setConfirmText("OK")
                .show();

    }
}

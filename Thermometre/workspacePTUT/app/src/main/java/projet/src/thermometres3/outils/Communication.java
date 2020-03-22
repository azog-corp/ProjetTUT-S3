package projet.src.thermometres3.outils;

import android.content.Context;
import android.os.AsyncTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurConnexion;

public class Communication extends AsyncTask<Context,Integer,Void> {
    boolean acrasher;

    @Override
    protected Void doInBackground(Context... myContext) {
        System.out.println("RUN");
        String dateDernCo = OutilsInterface.getLastCo(myContext[0]);
        try {
            OutilsFichier.ecrireFinFichier(myContext[0],OutilsCommunication.comRasp(dateDernCo)); // communique avec las rasp recuperre les temp puis les ecrit dans le fichier
            OutilsFichier.majFichierLastCo(myContext[0]);//mettre a jour fichier Derniere co
            this.cancel(true);
        } catch(ErreurConnexion e) {
            System.err.println("Erreur connexion");
            // messageErreurReception(getApplicationContext()); todo corriger cette merde
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //afficher msg erreur
        super.onPostExecute(aVoid);
    }

    /**
     * Message d'erreur affiche quand aucune temperature n'existe dans l'intervalle
     */
    public static void messageErreurReception(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: réseau")
                .setContentText("Erreur lors du transfert de données, veuillez réessayer.")
                .setConfirmText("OK")
                .show();

    }
}

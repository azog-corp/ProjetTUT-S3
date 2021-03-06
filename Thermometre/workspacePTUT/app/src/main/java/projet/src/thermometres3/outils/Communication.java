package projet.src.thermometres3.outils;

import android.content.Context;
import android.os.AsyncTask;

import java.net.DatagramSocket;
import java.net.SocketException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurConnexion;

public class Communication extends AsyncTask<Context,Integer,Context> {
    boolean acrasher;
    public DatagramSocket dSocket;

    @Override
    protected Context doInBackground(Context... myContext) {
        System.out.println("RUN");
        try {
             String dateDernCo = OutilsInterface.getLastCo(myContext[0]);
            OutilsFichier.ecrireFinFichier(myContext[0],OutilsCommunication.comRasp(dateDernCo,dSocket)); // communique avec las rasp recuperre les temp puis les ecrit dans le fichier
            OutilsFichier.majFichierLastCo(myContext[0]);//mettre a jour fichier Derniere co
            this.cancel(true);
        } catch(ErreurConnexion e) {
            System.err.println("Erreur connexion");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Context myContext) {
        //afficher msg erreur
        super.onPostExecute(myContext);
        messageErreurReception(myContext);
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

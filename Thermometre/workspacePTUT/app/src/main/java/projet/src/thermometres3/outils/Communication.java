package projet.src.thermometres3.outils;

import android.content.Context;
import android.os.AsyncTask;

import projet.src.thermometres3.Erreur.ErreurConnexion;

public class Communication extends AsyncTask<Context,Integer,Void> {

    @Override
    protected Void doInBackground(Context... myContext) {
        System.out.println("RUN");
        String dateDernCo = OutilsInterface.getLastCo(myContext[0]);
        try {
            RechercheTemperature.ecrireFinFichier(myContext[0],OutilsCommunication.comRasp(dateDernCo)); // communique avec las rasp recuperre les temp puis les ecrit dans le fichier
            OutilsInterface.creerFichierLastCo(myContext[0]);//mettre a jour fichier Derniere co
            this.cancel(true);
        } catch(ErreurConnexion e) {
            System.err.println("Erreur connexion");
        }
        return null;
    }
}

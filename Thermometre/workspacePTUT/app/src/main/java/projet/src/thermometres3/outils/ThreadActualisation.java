package projet.src.thermometres3.outils;

import android.content.Context;
import android.os.AsyncTask;

import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Graphe;

import static projet.src.thermometres3.outils.OutilsInterface.getDateActuelle;
import static projet.src.thermometres3.outils.OutilsInterface.getLastCo;
import static projet.src.thermometres3.outils.RechercheTemperature.dateIntervalle;

public class ThreadActualisation extends Thread {

    String dateDebut;

    Context contexte;

    @Override
    public void run() {
        //String debutContinu = getLastCo(getApplicationContext());
        while (true) {
            // try {
            //Thread.sleep(20000);
            //TimeUnit.MINUTES.sleep(1);
           // Graphe.majGrapheContinu(debutContinu);
           /* } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //appendData
            //}
        }
    }

}

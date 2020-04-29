package projet.src.thermometres3.outils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurConnexion;
import projet.src.thermometres3.R;

import static projet.src.thermometres3.outils.OutilsInterface.getDateActuelle;
import static projet.src.thermometres3.outils.OutilsInterface.getLastCo;
import static projet.src.thermometres3.outils.RechercheTemperature.conversion;
import static projet.src.thermometres3.outils.RechercheTemperature.editTemp;
import static projet.src.thermometres3.outils.RechercheTemperature.getListTemp;

public class ThreadActualisation extends AsyncTask<Context,String,Void> {

    public GraphView graphe;

    public EditText dateFin;

    Context contextAppli;

    public DatagramSocket dSocket;

    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    protected Void doInBackground(Context... myContext) {
        System.out.println("CONTINU");
        contextAppli = myContext[0];
        //mettre a jour les temperature depuis derniere connexion
        while(true) {
            if(isCancelled()) {
                System.out.println("CANCEL");
                break;
            }
            System.out.println("RUN");
            String dateDernCo = OutilsInterface.getLastCo(myContext[0]);
            try {
                OutilsFichier.ecrireFinFichier(myContext[0],OutilsCommunication.comRasp(dateDernCo,dSocket));// communique avec las rasp recuperre les temp puis les ecrit dans le fichier
                OutilsFichier.majFichierLastCo(myContext[0]);//mettre a jour fichier Derniere co
            } catch(ErreurConnexion e) { System.err.println("Erreur connexion"); }
            publishProgress( "Update" );
            if(isCancelled()) {
                System.out.println("CANCEL");
                break;
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return null; // bouchon
    }

    @Override
    protected void onProgressUpdate(String... values) {
        System.out.println("PROGRESS");
        editTemp(contextAppli);
        ArrayList<Temperature> temp = getListTemp();
        /* Définition des propriétés du graph */
        graphe.removeAllSeries(); // enleve les données precendentes si deja un graph affiché
        /*Tableau necessaire car LineGraphSeries necessite un tableau en argument
         * Tableau qui contient les point du graphe*/
        DataPoint[] pointGraphe =  new DataPoint[temp.size()];//initialisation par defaut
        /*Array list qui récupère les points
         * Creer a cause de null exception dans le tableau définit trop grand*/
        ArrayList<DataPoint> listePoints = new ArrayList<DataPoint>();
        LineGraphSeries<DataPoint> series;
        boolean donneeOk = false;

        for(int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getTemp() == -300.0) { // Si temperature invalide
                // on definit la  taille du tableau a la taille de listePoints ( qui contient tous les points récupérer )
                pointGraphe = new DataPoint[listePoints.size()];
                // On transfert tous les points dans le tableau
                for(int j = 0; j < listePoints.size(); j++) {
                    pointGraphe[j] = listePoints.get(j);
                }
                /*Puis ajoute la series de point au graph si notre tableau a une taille > a 0
                 Ce cas d'erreur peut arriver si 2 températures invalide a la suite */
                if(listePoints.size() > 0) {
                    //Definition de l'apparence de la serie que l'on ajoute
                    series = new LineGraphSeries<>(pointGraphe);
                    series.setDataPointsRadius(10);
                    series.setThickness(8);
                    series.setDrawDataPoints(true);
                    donneeOk = true;
                    graphe.addSeries(series); // ajout au graph
                }
                listePoints = new ArrayList<DataPoint>(); // on redéfinit l'arraylist pour ne pas garder les points déja entre
            } else { // si la température est valide
                //on ajoute le point a la liste
                listePoints.add(new DataPoint(temp.get(i).getDate(), temp.get(i).getTemp()));
                System.out.println("Ajout point graphe" + temp.get(i).getDate() + " " + temp.get(i).getTemp());
            }
        }
        if (temp.size() > 0) { // si il ya eu des températures
            /* si il reste des températures a ajouter
             * Ajoute au graph
             * Vérification nécessaire car si la dernière température inscrite était invalide
             * alors ajoute un tableau vide
             */
            if (listePoints.size() > 0) {
                pointGraphe = new DataPoint[listePoints.size()];
                for (int j = 0; j < listePoints.size(); j++) {
                    pointGraphe[j] = listePoints.get(j);
                }
                //Definition de l'apparence de la serie que l'on ajoute
                series = new LineGraphSeries<>(pointGraphe);
                series.setDataPointsRadius(10);
                series.setThickness(8);
                series.setDrawDataPoints(true);
                donneeOk = true;
                graphe.addSeries(series);
            }
        }
        System.out.println("Données ? " +donneeOk);
        if(!donneeOk) {
            System.out.println("Pas de données");
            //messageErreurListeDate(contextAppli);
        }

        /* Modification interface graph */
        graphe.getViewport().setXAxisBoundsManual(true);
        graphe.getGridLabelRenderer().setNumHorizontalLabels(2);//fait disparaitre les labels temp
        graphe.getGridLabelRenderer().setNumVerticalLabels(2);
        graphe.getGridLabelRenderer().setHumanRounding(false);
        graphe.getViewport().setScalable(true);
        graphe.getViewport().setScrollable(true);
        GridLabelRenderer gridLabel = graphe.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Température (°C)");
        gridLabel.setHorizontalAxisTitle("Date");
        graphe.getGridLabelRenderer().reloadStyles();
        /*Code permettant de mettre des dates en label X*/
        graphe.getGridLabelRenderer().setTextSize(20f);
        graphe.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long)value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        dateFin.setText(getDateActuelle());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        OutilsCommunication.fermerContinu();
    }

    /**
     * Message d'erreur affiche quand aucune temperature n'existe dans l'intervalle
     */
    public void messageErreurListeDate(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Liste Températures")
                .setContentText("Erreur: Aucune température existante dans l'intervalle saisi")
                .setConfirmText("OK")
                .show();

    }



}
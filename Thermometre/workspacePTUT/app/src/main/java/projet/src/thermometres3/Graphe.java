package projet.src.thermometres3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.outils.RechercheTemperature;
import projet.src.thermometres3.outils.Temperature;

import static projet.src.thermometres3.outils.RechercheTemperature.dateIntervalle;
import static projet.src.thermometres3.outils.RechercheTemperature.dateOk;

//TODO creer boutons last connexion
//
public class Graphe extends AppCompatActivity {

    final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

    /**
     * Fonction execute au lancement de la page Graphe
     * Initialise les actions des boutons de la page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphe);
        //Definition du bouton afficher
        Button btnAfficher = (Button) findViewById(R.id.btnAfficher);
        //Definition des actions a effectuer lors du clic sur le bouton afficher
        btnAfficher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creerGraph(); //appel la creation du graph
            }
        });

        //Definition du bouton derniere connexion
        Button btnLastCo = (Button) findViewById(R.id.btnLastCo);
        //Definition des actions a effectuer lors du clic sur le bouton derniere connexion
        btnLastCo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creerGraph();//appel la creation du graph
            }
        });

    }

    /**
     * Fonction qui recupere les entrees de l'utilisateur
     * Puis verifie leur validite
     * Si celles ci sont valides appel la methode conversionGraph
     * Sinon affiche un mesasge d'erreur a l'utilisateur
     */
    public void creerGraph() {
        //Definition des entrees de l'utilisateur
        TextView tvDebut = (TextView) findViewById(R.id.dateDebut);
        TextView tvFin = (TextView) findViewById(R.id.dateFin);
        //recuperation des entrees de l'utilisateur
        String sDebut = tvDebut.getText().toString();
        String sFin = tvFin.getText().toString(); //
        System.out.println("Debut " + sDebut + " Fin " + sFin);
            if (dateOk(sDebut) && dateOk(sFin)) {
                System.out.println("Date OK taille :" + RechercheTemperature.getListTemp().size());
                //if (intervalleOk(sDebut,sFin)) {
                    if(RechercheTemperature.getListTemp().size() != 0) {
                        conversionGraph(dateIntervalle(sDebut, sFin));
                    } else {
                        messageErreurListeDate();
                    }
                /*} else {
                    messageErreurIntervalle();
                }*/
            } else {
                messageErreurDate();
            }
    }

    /**
     * Fonction qui convertit une liste de température en point sur le graph
     * @param temp liste des températures dans l'intervalle entré par l'utilisateur
     *///TODO attention si intervalle exemple verifier que intervalle ok significatif
    //TODO essayer de grossir les points + faire les labels
    //TODO plus faire des tests pour intervalle significatif (intervalle Ok)
    public void conversionGraph(ArrayList<Temperature> temp) {
        GraphView graphView = (GraphView) findViewById(R.id.graphique);
        graphView.removeAllSeries(); // enleve les données precendentes si deja un graph affiché
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
                // On transfere toutes les points dans le tableau
                //TODO mettre dans une fct a part ?
                for(int j = 0; j < listePoints.size(); j++) {
                    pointGraphe[j] = listePoints.get(j);
                    System.out.println("-----" + pointGraphe[j]);
                }
                /*Puis ajoute la series de point au graph si notre tableau a une taille > a 0
                 Ce cas d'erreur peut arriver si 2 températures invalide a la suite */
                if(listePoints.size() > 0) {
                    series = new LineGraphSeries<>(pointGraphe);
                    series.setDataPointsRadius(10);
                    series.setThickness(8);
                    series.setDrawDataPoints(true);
                    donneeOk = true;
                    graphView.addSeries(series); // ajout au graph
                }
                listePoints = new ArrayList<DataPoint>(); // on redéfinit l'arraylist pour ne pas garder les points déja entre
            } else { // si la température est valide
                //on ajoute le point a la liste
                listePoints.add(new DataPoint(temp.get(i).getDate(), temp.get(i).getTemp()));
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
                series = new LineGraphSeries<>(pointGraphe);
                series.setDataPointsRadius(10);
                series.setThickness(8);
                series.setDrawDataPoints(true);
                donneeOk = true;
                graphView.addSeries(series);
            }

            System.out.println("Donnees ? " +donneeOk);
            if(!donneeOk) {
                messageErreurListeDate();
            }

            /* Défninition des propriétés du graph */
            graphView.getGridLabelRenderer().setTextSize(40f);
            graphView.getGridLabelRenderer().reloadStyles();
            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        return sdf.format(new Date((long)value));
                    } else {
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        }

    }

    /**
     * Fonction qui permet d'aficher les températures depuis la dernière connexion
     * //TODO ajouter verif pour intervalle valide ( pas trop petit et pertinent)
     * et inferieur a 2 jours
     * @param

    //public void lastCo(ArrayList<Temperature> temp) {
        try{
            BufferedReader fichier = new BufferedReader(new InputStreamReader(getResources().getAssets().open("derniereCo.txt")));
            String sDebut = fichier.readLine();
            String sFin = Date.;
            //todo convertir
            //todo appel fonction intervalle entr date
            //recuperer arraylist et appeller conversionGraph
            //todo verifier que si plsu de 2 jours etc prendre que les deux derniers jour
                // todo faire fonction qui recupere le jour actuellement et ils ya
                if (dateOk(sDebut,sFin)) {
                    if (intervalleOk(sDebut,sFin)) {
                        conversionGraph(dateIntervalle(sDebut,sFin));
                    } else {
                        messageErreurIntervalle();
                    }
                } else {
                    messageErreurDate();
                }
            } catch(IOException e) {
            //TODO si se cas intervient initialiser la date a maintenant
            messageErreurLastCo();
        }
    }*/

    public void messageErreurLastCo() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR")
                .setContentText("Impossible de lire le fichier dernière connexion")
                .setConfirmText("OK").show();
    }

    public void messageErreurDate() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Date")
                .setContentText("Erreur: date non valide(format: dd/MM/yyyy HH:mm:ss) ou non ordonnees")
                .setConfirmText("OK").show();
    }


    public void messageErreurIntervalle() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Intervalle")
                .setContentText("Erreur: Intervalle non valide")
                .setConfirmText("OK").show();

    }

    public void messageErreurListeDate() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Liste Températures")
                .setContentText("Erreur: Pas de date disponible")
                .setConfirmText("OK")
                .show();

    }
}

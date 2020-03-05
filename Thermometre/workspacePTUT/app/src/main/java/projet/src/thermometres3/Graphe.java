package projet.src.thermometres3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurDate;
import projet.src.thermometres3.Erreur.ErreurIntervalle;
import projet.src.thermometres3.outils.Communication;
import projet.src.thermometres3.outils.OutilsCommunication;
import projet.src.thermometres3.outils.OutilsInterface;
import projet.src.thermometres3.outils.RechercheTemperature;
import projet.src.thermometres3.outils.Temperature;
import projet.src.thermometres3.outils.ThreadActualisation;

import static projet.src.thermometres3.outils.OutilsInterface.getDate2JoursPrec;
import static projet.src.thermometres3.outils.OutilsInterface.getDateActuelle;
import static projet.src.thermometres3.outils.OutilsInterface.getLastCo;
import static projet.src.thermometres3.outils.RechercheTemperature.conversion;
import static projet.src.thermometres3.outils.RechercheTemperature.dateIntervalle;
import static projet.src.thermometres3.outils.RechercheTemperature.dateOk;
import static projet.src.thermometres3.outils.RechercheTemperature.intervalleOk;

public class Graphe extends AppCompatActivity {

    ThreadActualisation t;

    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Fonction execute au lancement de la page Graphe
     * Initialise les actions des boutons de la page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphe);
        lancementDefaut();
        //Definition du bouton afficher
        Button btnAfficher = findViewById(R.id.btnAfficher);
        //Definition des actions a effectuer lors du clic sur le bouton afficher
        btnAfficher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creerGraph(); //appel la creation du graph
            }
        });

        //Definition du bouton derniere connexion
        Button btnLastCo = findViewById(R.id.btnLastCo);
        //Definition des actions a effectuer lors du clic sur le bouton derniere connexion
        btnLastCo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lastCo();//appel la creation du graph
            }
        });
        Button btnArretContinu = findViewById(R.id.btnFinContinu);
        btnArretContinu.setVisibility(View.INVISIBLE);
    }

    /**
     * Fonction lancee automatiquement au lancement de la page Graphe
     * apppel la fonction conersionGraph() qui affichera les temperatures
     * sur le graph s'il y en a
     */
    public void lancementDefaut() {
        /* Recuperation de la date actuelle et de la date de 2 jours avant*/
        String sDebut = getDate2JoursPrec();
        String sFin = getDateActuelle();


        /* On ecrit dans les textView les deux dates */
        TextView tvDebut = findViewById(R.id.dateDebut);
        TextView tvFin = findViewById(R.id.dateFin);
        tvDebut.setText(sDebut);
        tvFin.setText(sFin);
        try {
            //Verification inutile des dates celles si ont ete ecrite par nous
            //Si des temperatures existes
            if (RechercheTemperature.getListTemp().size() != 0) {
                conversionGraph(dateIntervalle(sDebut, sFin));
            } else { //sinon message erreur
                messageErreurListeDate();
            }
        } catch(ErreurDate e) {//les dates ne sont pas valide
            messageErreurDate();
        }
    }

    /**
     * Fonction qui recupere les entrees de l'utilisateur
     * Puis verifie leur validite
     * Si celles ci sont valides appel la methode conversionGraph
     * Sinon affiche un mesasge d'erreur a l'utilisateur
     */
    public void creerGraph() {
        //Definition des entrees de l'utilisateur
        TextView tvDebut = findViewById(R.id.dateDebut);
        TextView tvFin = findViewById(R.id.dateFin);
        //recuperation des entrees de l'utilisateur
        String sDebut = tvDebut.getText().toString();
        String sFin = tvFin.getText().toString(); //
        System.out.println("Debut " + sDebut + " Fin " + sFin);
        try {
            //Verification validite des dates
            dateOk(sDebut);
            dateOk(sFin);
            //Verification intervalle valide
            intervalleOk(sDebut,sFin);
            //Si des temperatures existes
            if (RechercheTemperature.getListTemp().size() != 0) {
                conversionGraph(dateIntervalle(sDebut, sFin));
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
     * Fonction qui permet d'aficher les températures depuis la dernière connexion
     * et inferieur a 2 jours
     */
    public void lastCo() { //TODO RESEAU
        /*Recupere la date de derniere connexion et la date actuelle */
        String sDebut = OutilsInterface.getLastCo(getApplicationContext());
        String sFin = getDateActuelle();
        //OutilsCommunication.majDerniereConnexion(getApplicationContext());
        Communication test = new Communication();
        test.execute(getApplicationContext());
        System.out.println("Thread continu");
        System.out.println("Debut " + sDebut);
        while(test.getStatus() != AsyncTask.Status.FINISHED && !test.isCancelled()){
            //Boucle infinie pour empecher le programme de continuer
        }
        System.out.println("Thread FIN");
        RechercheTemperature.editTemp(getApplicationContext());
        /* On ecrit dans les textView les deux dates */
        TextView tvDebut = findViewById(R.id.dateDebut);
        TextView tvFin = findViewById(R.id.dateFin);
        tvDebut.setText(sDebut);
        tvFin.setText(sFin);
        try {
            //Verification inutile des dates celles si ont ete ecrite par nous
            //Si des temperatures existes
            //intervalleOk(sDebut,sFin); TODO REMETTRE ENLEVER SECU 2 JOURS
            if (RechercheTemperature.getListTemp().size() != 0) {
                conversionGraph(dateIntervalle(sDebut, sFin));
            } else { //sinon message erreur
                messageErreurListeDate();
            }
        /*}catch (ErreurIntervalle e) { //l'intervalle n'est pas valide
            messageErreurCo();
            lancementDefaut();*/
        }catch(ErreurDate e) {//les dates ne sont pas valide
            messageErreurDate();
        }
    }

    public void connexionContinu(View view) {
        Button btnLast = findViewById(R.id.btnLastCo);
        Button btnAfficher = findViewById(R.id.btnAfficher);
        Button btnActualisationContinu = findViewById(R.id.btnContinu);
        Button btnArretContinu = findViewById(R.id.btnFinContinu);
        btnArretContinu.setVisibility(View.VISIBLE);
        btnLast.setVisibility(View.INVISIBLE);
        btnAfficher.setVisibility(View.INVISIBLE);
        btnActualisationContinu.setVisibility(View.INVISIBLE);
        lastCo();
        t = new ThreadActualisation();
        t.graphe = findViewById(R.id.graphique);
        t.dateFin = findViewById(R.id.dateFin);
        t.execute(getApplicationContext());
    }

    /**
     * Fonction qui convertie une liste de température en point sur le graph
     * @param temp liste des températures dans l'intervalle */
    public void conversionGraph(ArrayList<Temperature> temp) {
        //Definition du graph
        GraphView graphView = findViewById(R.id.graphique);
        //Definition des entrees de l'utilisateur
        TextView tvDebut = findViewById(R.id.dateDebut);
        TextView tvFin = findViewById(R.id.dateFin);
        //recuperation des entrees de l'utilisateur
        String sDebut = tvDebut.getText().toString();
        String sFin = tvFin.getText().toString();
        System.err.println(sDebut + " " + sFin);

        /* Définition des propriétés du graph */
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
                    graphView.addSeries(series); // ajout au graph
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
                graphView.addSeries(series);
            }
        }
        System.out.println("Données ? " +donneeOk);
        if(!donneeOk) {
            messageErreurListeDate();
        }

        /* Modification interface graph */
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(2);//fait disparaitre les labels temp
        graphView.getGridLabelRenderer().setNumVerticalLabels(2);
        /*try { //TODO tester
            graphView.getViewport().setMinX(conversion(sDebut).getTime());
            graphView.getViewport().setMaxX(conversion(sFin).getTime());
        } catch (ParseException e) {
            //impossible
        }*/

        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Température (°C)");
        gridLabel.setHorizontalAxisTitle("Date");
        graphView.getGridLabelRenderer().reloadStyles();
        /*Code permettant de mettre des dates en label X*/
        graphView.getGridLabelRenderer().setTextSize(20f);
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

    public void finContinu(View vue) {
        Button btnLast = findViewById(R.id.btnLastCo);
        Button btnAfficher = findViewById(R.id.btnAfficher);
        Button btnActualisationContinu = findViewById(R.id.btnContinu);
        Button btnArretContinu = findViewById(R.id.btnFinContinu);
        btnArretContinu.setVisibility(View.INVISIBLE);
        btnLast.setVisibility(View.VISIBLE);
        btnAfficher.setVisibility(View.VISIBLE);
        btnActualisationContinu.setVisibility(View.VISIBLE);
        t.cancel(true);
    }

    @Override
    protected void onDestroy() {
        t.cancel(true);
        super.onDestroy();
    }


    /**
     * Message d'erreur affiche quand la date de connexion est trop ancienne
     */
    public void messageErreurCo() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR")
                .setContentText("La dernière connexion est trop ancienne." +
                        "Affichage des températures inférieures à 2 jours")
                .setConfirmText("OK").show();
    }

    /**
     * Message d'erreur affiche quand la date est incorrect -> format non respecte
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

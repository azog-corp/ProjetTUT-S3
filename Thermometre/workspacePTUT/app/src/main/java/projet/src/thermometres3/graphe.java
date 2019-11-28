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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.outils.Temperature;

//TODO creer boutons last connexion
//
public class Graphe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphe);
        Button btnAfficher = (Button) findViewById(R.id.btnAfficher);
        btnAfficher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                creerGraph();
            }
        });

    }

    public void creerGraph() {
        TextView tvDebut = (TextView) findViewById(R.id.dateDebut);
        TextView tvFin = (TextView) findViewById(R.id.dateFin);

        String sDebut = tvDebut.getText().toString();
        String sFin = tvFin.getText().toString();
            /* todo rajouter fct mael
            try {
                if (dateOk(sDebut,sFin)) {
                    if (intervalleOk(sDebut,sFin)) {
                        conversionGraph(dateIntervalle(sDebut,sFin));
                    }
                }
            } catch(ErreurDate e) {
                messageErreurDate();
            } catch(ErreurIntervalle e) {
                messageErreurIntervalle();
            }
            */
            ArrayList<Temperature> temp = new ArrayList<Temperature>();
            try {
                temp.add(new Temperature("19/01/2019 22:20:50 -3"));
                temp.add(new Temperature("19/01/2019 22:21:00 -5"));
                temp.add(new Temperature("19/01/2019 22:21:10 3"));
                temp.add(new Temperature("19/01/2019 22:21:20 -3"));
                temp.add(new Temperature("19/01/2019 22:21:30 10"));
                temp.add(new Temperature("19/01/2019 22:21:40 -3"));
            }catch(ParseException e) {
                //todo
            }
            conversionGraph(temp);

    }

    public void conversionGraph(ArrayList<Temperature> temp) {
        GraphView graphView = (GraphView) findViewById(R.id.graphique);
        DataPoint[] pointGraphe = new DataPoint[temp.size()];
        LineGraphSeries<DataPoint> series;
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");


        for(int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getTemp() == -300.0) {
                //TODO verif si vide crash ???
                series = new LineGraphSeries<>(pointGraphe);
                graphView.addSeries(series);
                int tailleNew = temp.size() - pointGraphe.length;
                pointGraphe = new DataPoint[tailleNew]; //faux
            } else {
                System.out.println(temp.get(i).getDate());
                pointGraphe[i] = new DataPoint(temp.get(i).getDate(), temp.get(i).getTemp());
            }
        }
        if (temp.size() > 0) {
            for (int i = 0 ; i < pointGraphe.length; i++) {
                System.out.println(pointGraphe[i]);
            }
            series = new LineGraphSeries<>(pointGraphe);
            graphView.addSeries(series); // TODO verif si pas vide crash ?
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

    public void lastCo(String nomFich,ArrayList<Temperature> temp) {
        try(BufferedReader fichier = new BufferedReader((new FileReader(nomFich)))) {
            String derniereConnexion = fichier.readLine();
            //todo convertir
            //todo appel fonction intervalle entr date
            //recuperer arraylist et appeller conversionGraph
            /*try { //todo verifier que si plsu de 2 jours etc prendre que les deux derniers jour
                // todo faire fonction qui recupere le jour actuellement et ils ya
                if (dateOk(derniereConnexion,Date.Now())) {
                    if (intervalleOk(sDebut,sFin)) {
                        conversionGraph(dateIntervalle(sDebut,sFin));
                    }
                }
            } catch(ErreurDate e) {
                messageErreurDate();
            } catch(ErreurIntervalle e) {
                messageErreurIntervalle();
            }
            */
        } catch(IOException e) {
            //TODO si se cas intervient initialiser la date a maintenant
            messageErreurLastCo();
        }
    }

    public void messageErreurLastCo() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR")
                .setContentText("Impossible de lire le fichier dernière connexion")
                .setConfirmText("OK");
    }

    public void messageErreurDate() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Date")
                .setContentText("Erreur: date non valide(format: dd/MM/yyyy HH:mm:ss) ou non ordonnees")
                .setConfirmText("OK");
    }


    public void messageErreurIntervalle() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Intervalle")
                .setContentText("Erreur: Intervalle non valide")
                .setConfirmText("OK");

    }
}

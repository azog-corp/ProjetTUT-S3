package projet.src.thermometres3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.ArrayList;
import thermometre.outils.Temperature;


public class graphe extends AppCompatActivity {

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
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pointGraphe);


        for(int i = 0; i < temp.size(); i++) {
            if(temp.get(i).getTemp() == -300.0) {
                //TODO verif si vide crash ???
                series = new LineGraphSeries<>(pointGraphe);
                graphView.addSeries(series);
                int tailleNew = temp.size() - pointGraphe.length;
                pointGraphe = new DataPoint[tailleNew];
            } else {
                pointGraphe[i] = new DataPoint(temp.get(i).getDate(), temp.get(i).getTemp());
            }
        }
        if (temp.size() > 0) {
            graphView.addSeries(series); // TODO verif si pas vide crash ?
        }

    }
}

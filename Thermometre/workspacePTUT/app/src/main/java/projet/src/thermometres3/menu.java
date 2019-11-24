package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnGraphe = (Button) findViewById(R.id.btnGraph);
        Button btnAccueil = (Button) findViewById(R.id.btnAccueil);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        btnGraphe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openGraphe();
            }
        });
    }

    private void openGraphe() {
        Intent intent = new Intent(this, Graphe.class);
        startActivity(intent);
    }
}

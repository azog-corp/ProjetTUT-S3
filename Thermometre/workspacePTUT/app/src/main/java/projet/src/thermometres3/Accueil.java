package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static projet.src.thermometres3.RechercheTemperature.addTemp;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button boutonMenu = (Button) findViewById(R.id.btnMenu);

        boutonMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openMenu();
            }
        });
        RechercheTemperature.addTemp(getApplicationContext());//Ajoute les nouvelles temperatures
    }

    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}

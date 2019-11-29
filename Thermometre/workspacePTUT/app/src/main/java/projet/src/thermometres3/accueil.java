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
        String curDir = System.getProperty("user.dir");
        System.out.println ("Le répertoire courant est: "+curDir);
        try {
            InputStream iS = getResources().getAssets().open("fichierTemp.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
            System.out.println(reader.readLine());
        } catch (FileNotFoundException e) {
            System.out.println("test nok");
        } catch (IOException e) {
            e.printStackTrace();
        }
        RechercheTemperature.addTemp(getApplicationContext());//Ajoute les nouvelles temperatures
    }

    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}

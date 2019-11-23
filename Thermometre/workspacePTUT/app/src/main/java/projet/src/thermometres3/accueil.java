package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class accueil extends AppCompatActivity {

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
    }

    private void openMenu() {
        Intent intent = new Intent(this,menu.class);
        startActivity(intent);
    }
}

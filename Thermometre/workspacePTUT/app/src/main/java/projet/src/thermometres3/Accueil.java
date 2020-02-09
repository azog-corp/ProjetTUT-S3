package projet.src.thermometres3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.File;
import projet.src.thermometres3.Test.TestOutilsInterface;
import projet.src.thermometres3.outils.OutilsInterface;
import projet.src.thermometres3.outils.RechercheTemperature;
import static projet.src.thermometres3.outils.OutilsInterface.creerFichierLastCo;

public class Accueil extends AppCompatActivity {
    /**
     * A la creation du fichier
     * initialise les boutons
     * et met a jour le fichier temperatures avec les nouvelles
     * temperatures disponibles
     */
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

        /* Initialisation du fichier */
        String derniereCo = Accueil.this.getFilesDir()+"/derniereCo.txt";
        File f = new File(derniereCo);

        /* On verifie que le fichier derniereCo existe
         * S' il n'existe pas cela signifie que c'est le premiere lancement de l'application
         * Il faut donc recuperer les temperatures et creer le fichier de temperatures
         */
        //TODO RESEAU changer fonctionnement enlever les asssets
        if(!f.exists()) { // Si le fichier n'existe pas
            creerFichierLastCo(getApplicationContext()); // creer fichier derniereConnexion
            OutilsInterface.creerFichierTemperatures(getApplicationContext()); // creer fichier Temperature
            //TestOutilsInterface.testFichierTemp(getApplicationContext());
        }
        RechercheTemperature.editTemp(getApplicationContext());//Ajoute les nouvelles temperatures
        //TestRechercheTemperature.testEditTemp(); // test
    }

    /**
     * A la fermeture de l'application met a jour
     * le fichier derniereConnexion
     */
    protected void onDestroy(){
        System.out.println("MAJ");
        creerFichierLastCo(getApplicationContext());
        super.onDestroy();
    }

    /**
     * Ouvre la page menu
     */
    private void openMenu() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent); // ouvre la page menu
    }
}

package projet.src.thermometres3.Erreur;

import android.content.Context;
import android.widget.Toast;
import projet.src.thermometres3.Accueil;

public class ErreurConnexion extends Exception{
    public ErreurConnexion() {
        super("Erreur lors du transfert de données, veuillez réessayer.");
    }
}

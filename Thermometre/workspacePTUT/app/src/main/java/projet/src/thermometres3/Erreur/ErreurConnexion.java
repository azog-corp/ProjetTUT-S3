package projet.src.thermometres3.Erreur;

public class ErreurConnexion extends Exception{
    public ErreurConnexion() {
        super("Erreur: connexion impossible");
    }
}

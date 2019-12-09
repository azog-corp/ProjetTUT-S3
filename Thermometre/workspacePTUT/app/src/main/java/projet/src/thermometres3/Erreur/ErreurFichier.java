package projet.src.thermometres3.Erreur;

public class ErreurFichier extends Exception{

    public ErreurFichier() {
        super("Acces fichier impossible");
    }
}

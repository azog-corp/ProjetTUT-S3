package projet.src.thermometres3.Erreur;

public class ErreurDate extends Exception{
    public ErreurDate() {
        super("Erreur: date non valide(format: dd/MM/yyyy HH:mm:ss) ou non ordonnees");
    }
}

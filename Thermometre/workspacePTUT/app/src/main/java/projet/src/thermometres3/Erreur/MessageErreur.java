package projet.src.thermometres3.Erreur;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MessageErreur {
    public static void messageErreurLastCo(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR")
                .setContentText("Impossible de lire le fichier dernière connexion")
                .setConfirmText("OK").show();
    }

    public static void messageErreurDate(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Date")
                .setContentText("Erreur: date non valide(format: dd/MM/yyyy HH:mm:ss) ou non ordonnees")
                .setConfirmText("OK").show();
    }


    public static void messageErreurIntervalle(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Intervalle")
                .setContentText("Erreur: Intervalle non valide")
                .setConfirmText("OK").show();

    }

    public static void messageErreurListeDate(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Liste Températures")
                .setContentText("Erreur: Pas de date disponible")
                .setConfirmText("OK")
                .show();

    }

    public static void messageErreurFichier(Context myContext) {
        new SweetAlertDialog(myContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERREUR: Lecture Fichier")
                .setContentText("Oups une erreur c'est produite lors de la lecture du fichier")
                .setConfirmText("OK")
                .show();

    }
}

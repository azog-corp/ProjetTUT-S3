package projet.src.thermometres3.outils;

import android.app.AlertDialog;
import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Menu;

public class OutilsCommunication {
    static DatagramSocket dSocket;

    public static ArrayList<String> comRasp(String date) {
        try {
            ArrayList<String> temperatures = new ArrayList<String>();
            int nbTemp = 0;
            int portServeur = 4523;
            byte[] buffer = date.getBytes();
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1"); //todo modifier
            System.out.println("Avant socket");
            dSocket = new DatagramSocket(4523);//todo modifier pas verifier
            System.out.println("Avant socket");
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));
            //dSocket.setSoTimeout(4000); // Temps d'attente rÃ©ception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));
           /* nbTemp = Integer.parseInt(new String(buffer));
            for (int i = 0; i < nbTemp; i++) {
                dSocket.receive(new DatagramPacket(buffer, 100));
                temperatures.add(new String(buffer));
            }*/
            return temperatures;
        } catch(SocketException e) {
            System.out.println("erreur socket");
        } catch (IOException e) {
            System.out.println("erreur connexion");
        }

        return null;
    }

    /**
     * Ajoute a la fin du fichier
     * @param temperatures
     */
    public static void ajouterFichier(ArrayList<String> temperatures) {
        // TODO Auto-generated method stub

    }

    public static void majDerniereConnexion(Context myContext) {
        new Communication().execute(myContext);

    }
}

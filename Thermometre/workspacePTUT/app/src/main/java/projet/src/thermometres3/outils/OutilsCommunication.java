package projet.src.thermometres3.outils;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class OutilsCommunication {
    public static ArrayList<String> comRasp(String date) {
        try {
            ArrayList<String> temperatures = new ArrayList<String>();
            int nbTemp = 0;
            byte[] buffer = new byte[100]; // message sous forme de tableau d'octet
            buffer = date.getBytes();
            InetAddress iPserveur = InetAddress.getByName("10.0.0.0"); //todo modifier
            DatagramSocket dSocket = new DatagramSocket();//todo modifier pas verifier
            int portServeur = 423; //todo modifier
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));
            dSocket.setSoTimeout(4000); // Temps d'attente rÃ©ception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer,100));
            nbTemp = Integer.parseInt(new String(buffer));
            for(int i =0; i < nbTemp; i++) {
                dSocket.receive(new DatagramPacket(buffer,100));
                temperatures.add(new String(buffer));
            }
            return temperatures;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String dateDernCo = OutilsInterface.getLastCo(myContext);
        OutilsCommunication.ajouterFichier(OutilsCommunication.comRasp(dateDernCo)); // communique avec las rasp recuperre les temp puis les ecrit dans le fichier
        OutilsInterface.creerFichierLastCo(myContext);//mettre a jour fichier Derniere co
    }
}

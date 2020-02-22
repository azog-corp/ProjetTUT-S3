package projet.src.thermometres3.outils;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import projet.src.thermometres3.Erreur.ErreurConnexion;

public class OutilsCommunication {
    static DatagramSocket dSocket;

    public static ArrayList<String> comRasp(String date) throws ErreurConnexion {
        try {
            ArrayList<String> temperatures = new ArrayList<String>();
            int nbTemp = 0;
            int portServeur = 4523;
            byte[] buffer = date.getBytes();
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1"); //todo modifier
            System.out.println("Avant socket");
            dSocket = new DatagramSocket(4523);//todo modifier pas verifier
            System.out.println("Avant socket");
            //TODO ajoute verif + ack
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));
            dSocket.setSoTimeout(4000); // Temps d'attente rÃ©ception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));
            System.out.println("recu : " + new String(buffer));
            byte[] buffer2 = new byte[99999];
            System.out.println("new : " + new String(buffer));
            dSocket.setSoTimeout(60000);
            dSocket.receive(new DatagramPacket(buffer2, buffer2.length));
            System.out.println("recu : " + new String(buffer2));
            temperatures.add(new String(buffer2));
            dSocket.close();
            return temperatures;
        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }


    public static String[] decoupageRep(String rep) {
            return rep.split("\\|");
        }

}

package projet.src.thermometres3.outils;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import projet.src.thermometres3.Erreur.ErreurConnexion;

public class OutilsCommunication {

    static DatagramSocket dSocket;
    static int nbPaquets, nbTemp;
    static ArrayList<String> temperatures;

    public static ArrayList<String> comRasp(String date) throws ErreurConnexion {
        try {

            byte[] buffer = "".getBytes();
            temperatures = new ArrayList<String>();

            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            dSocket = new DatagramSocket(portServeur);

            System.out.println("Premier envoi");

            String stringPaquetInit = premierEnvoi(date);
            String[] tabPaquetInit = decoupageRep(stringPaquetInit);
            nbPaquets = /*tabPaquetInit[0].charAt(0)*/ 1;
            nbTemp = tabPaquetInit[1].charAt(0);

            buffer = "r".getBytes();
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

            buffer = "a".getBytes();
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

            dSocket.setSoTimeout(4000); // Temps d'attente réception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));



            // Récupération des temp
            System.out.println("Recup temp");
            recupTemp();

            while (!verifTemp(temperatures)) {
                envoiErreur();
                recupTemp();
            }

            envoiOk();
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

    public static ArrayList<String> comRaspContinu(String date,DatagramSocket dSocket) throws ErreurConnexion {
        try {
            ArrayList<String> temperatures = new ArrayList<String>();
            int nbTemp = 0;
            int portServeur = 65230;
            byte[] buffer = date.getBytes();
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1"); //todo modifier
            System.out.println("Envoi");
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
            return temperatures;
        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }

    public static void fermerContinu() {
        dSocket.close();
    }


    public static String[] decoupageRep(String rep) {
            return rep.split("\\|");
    }



    public static String premierEnvoi(String date) throws ErreurConnexion {
        try {
            byte[] buffer = ("i|" + date).getBytes();
            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");

            System.out.println("Envoi");
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

            dSocket.setSoTimeout(4000); // Temps d'attente réception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));
            System.out.println("recu : " + new String(buffer));

            return new String(buffer);

        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }


    public static void envoiErreur() throws ErreurConnexion {
        try {
            byte[] buffer = "r".getBytes();
            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");

            System.out.println("Envoi Erreur");
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }


    public static void envoiOk() throws ErreurConnexion {
        try {
            byte[] buffer = "a".getBytes();
            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");

            System.out.println("Envoi Ok");
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }


    public static void recupTemp() throws ErreurConnexion {
        try {
            byte[] buffer = "".getBytes();
            for (int i = 0; i < nbPaquets; i++) {
                dSocket.setSoTimeout(5000); // Temps d'attente réception max en millisecondes
                dSocket.receive(new DatagramPacket(buffer, buffer.length));
                System.out.println("Recu : " + new String(buffer));
                temperatures.add(new String(buffer));
                envoiOk();
            }
        } catch (SocketTimeoutException e) {
            envoiErreur();
            recupTemp();
        } catch (SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }


    public static boolean verifTemp(ArrayList<String> temp) {
        String[] tabString = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            tabString[i] = temp.get(i);
        }

        /*
        if

         */

        // bouchon
        return true;
    }
}

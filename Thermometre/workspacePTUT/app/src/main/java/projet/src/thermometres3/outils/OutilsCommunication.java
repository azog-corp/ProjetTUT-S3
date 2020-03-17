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
    static String testPremierOk;
    static char charPremierOk;
    static int indexFirstDateLastPaquet;




    public static ArrayList<String> comRasp(String date) throws ErreurConnexion {
        try {

            byte[] buffer;
            temperatures = new ArrayList<String>();

            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            dSocket = new DatagramSocket(portServeur);

            System.out.println("Premier envoi");

            String stringPaquetInit = premierEnvoi(date);
            String[] tabPaquetInit = decoupageRep(stringPaquetInit);
            nbPaquets = Integer.parseInt(tabPaquetInit[1]);


            // envoi "p" pour dire "c'est bon j'ai bien mon paquetInit envoie les dates", le serv passe au premier paquet, "p" sert juste pour le paquetInit
            // si le serv n'a pas recu le "p" il va envoyer "p" au client, il faudra analyser la 1ère lettre (p ou date ?) et répondre en conséquence (p ou a)
            // envoi "a" lorque paquet reçu, le serveur attends de recevoir "r" pour passer au paquet suivant
            // si le serv ne recoit pas le "a" (donc recoit un "r" à un moment) il va envoyer le même paquet
            // dans ce cas il faut regarder le premier caractère et savoir si on a déjà le paquet ou pas
            // on positionnera un index pour se referrer à la première date du dernier paquet, histoire de s'y référer plus vite
            // envoi "r" pour demander au serveur où il en est, cad toutes les 5sec, le serveur reagit à ce moment-là

            // on a reçu le paquet init
            buffer = "p".getBytes();
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));

            do {
                byte[] bufferTest = "".getBytes();
                dSocket.setSoTimeout(5000);
                dSocket.receive(new DatagramPacket(bufferTest, bufferTest.length));
                testPremierOk = new String(bufferTest);
                charPremierOk = testPremierOk.charAt(0);
                envoiOkPremier();
            } while (charPremierOk == 'p');


            // Ajout de la temp à la ArrayList
            String[] aAjouter = decoupageRep(testPremierOk);
            for (int i = 0; i < aAjouter.length; i++)
                temperatures.add(aAjouter[i]);
            envoiOk();
            envoiRetry();

            // Récupération des temp restantes
            System.out.println("Recup temp");
            recupTempEnveloppe();

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

            dSocket.setSoTimeout(5000); // Temps d'attente réception max en millisecondes
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


    public static void envoiRetry() throws ErreurConnexion {

        try {
            byte[] buffer = "r".getBytes();
            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            System.out.println("Envoi Retry");
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


    public static void envoiOkPremier() throws ErreurConnexion {

        try {
            byte[] buffer = "p".getBytes();
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


    public static void recupTempEnveloppe() throws ErreurConnexion {

        byte[] buffer = "".getBytes();
        for (int i = 0; i < nbPaquets - 1; i++) {
            recupTemp(buffer);
        }
    }

    public static void recupTemp(byte[] buffer) throws ErreurConnexion {
        try {
            dSocket.setSoTimeout(5000); // Temps d'attente réception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));
        } catch (SocketTimeoutException e) {
            envoiRetry();
            recupTemp(buffer);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Recu : " + new String(buffer));
        if(verifPaquet(new String(buffer))) {
            String[] aAjouter = decoupageRep(new String(buffer));
            for (int i = 0; i < aAjouter.length; i++)
                temperatures.add(aAjouter[i]);
            envoiOk();
        } else {
            envoiRetry();
            recupTemp(buffer);
        }

    }



    public static boolean verifPaquet(String temp) {
        // doit vérifier que la première date du paquet :
        // si c'est la même que la première date du dernier paquet reçu, on retourne false
        // ou une nouvelle date, auquel cas on return true
        // on fait ce test pour savoir si un paquet ne nous a pas été envoyé en double

        String aTester = decoupageRep(temp)[0];
        if (aTester.equals(temperatures.get(indexFirstDateLastPaquet)))
            return false;

        return true;
    }

}

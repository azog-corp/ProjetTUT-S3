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


    /**
     * Fait toute la communication avec le Raspberry
     * C'est-à-dire envoie une requête, récupère les températures, gère tous les cas d'erreur et
     * retourne une ArrayList contenant les températures reçues
     * @param date date de dernière connexion de l'appareil
     * @param socket
     * @return la ArrayList des températures récupérées
     * @throws ErreurConnexion
     */
    public static ArrayList<String> comRasp(String date,DatagramSocket socket) throws ErreurConnexion {
        dSocket = socket;
        System.out.println("COMRASP");
        try {
            byte[] bufferTest;
            temperatures = new ArrayList<String>();
            dSocket.setSoTimeout(5000);
            System.out.println("Premier envoi");
            String stringPaquetInit = premierEnvoi(date);

            if (stringPaquetInit.equals("e")) {
                return temperatures; // erreur, on renvoie la liste vide
            }
            // envoi "p" pour dire "c'est bon j'ai bien mon paquetInit envoie les dates", le serv passe au premier paquet, "p" sert juste pour le paquetInit
            // si le serv n'a pas recu le "p" il va envoyer "p" au client, il faudra analyser la 1ère lettre (p ou date ?) et répondre en conséquence (p ou a)
            // envoi "a" lorque paquet reçu, le serveur attends de recevoir "r" pour passer au paquet suivant
            // si le serv ne recoit pas le "a" (donc recoit un "r" à un moment) il va envoyer le même paquet
            // dans ce cas il faut regarder le premier caractère et savoir si on a déjà le paquet ou pas
            // on positionnera un index pour se referrer à la première date du dernier paquet, afin de s'y référer plus vite
            // envoi "r" pour demander au serveur où il en est, cad toutes les 5sec, le serveur reagit à ce moment-là

            dSocket.setSoTimeout(2000);
            System.out.println("ENVOI P");
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[55000];
                    System.out.println("receive : p ");
                    dSocket.receive(new DatagramPacket(bufferTest, bufferTest.length));
                    testPremierOk = new String(bufferTest);
                    System.out.println("recu : p " + new String(bufferTest));
                    charPremierOk = testPremierOk.charAt(0);
                    envoiOkPremier();
                } catch(SocketException e) {
                    System.out.println("Erreur reception p");
                } catch (IOException e) {
                    System.out.println("erreur connexion p");
                }
            } while (charPremierOk != 'p');
            System.out.println("SORTI P");

            // verifier si = 0 si oui arreter /!\ si le serveur ne recoit pas la deuxieme partie alors que il ya 0 paquets envoyer f si possible pour le supprimer
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[55000];
                    dSocket.receive(new DatagramPacket(bufferTest, bufferTest.length));
                    testPremierOk = new String(bufferTest);
                    System.out.println("recu : " + new String(bufferTest));
                    charPremierOk = testPremierOk.charAt(0);
                    if(charPremierOk == 'p') {
                        System.out.println("RENVOI P");
                        envoiOkPremier();
                    }
                } catch(SocketException e) {
                    System.out.println("Erreur reception 2");
                } catch (IOException e) {
                    System.out.println("erreur connexion 2");
                }
            } while (charPremierOk == 'p');
            // Récupération des temps
            System.out.println("Recup temp");
            recupTempEnveloppe();
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

    /**
     * Envoie une première requête, attends la réponse et initialise les valeurs des variables qui
     * serviront dans les tests
     * @param date
     * @return
     * @throws ErreurConnexion
     */
    public static String premierEnvoi(String date) throws ErreurConnexion {
        System.out.println("PREMIER ENVOI");
        boolean sort = false;
        int essais = 0;
        while(!sort) {
            // 10 essais
            // todo remettre à 3 essais
            essais++;
            if (essais == 10) {
                sort = true;
            }
            try {
                byte[] buffer = ("i|" + date).getBytes();
                System.out.println("Envoi " + essais);
                int portServeur = 65230;
                InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
                dSocket.send(new DatagramPacket(buffer, buffer.length,
                        iPserveur, portServeur));
                dSocket.setSoTimeout(3000); // Temps d'attente réception max en millisecondes
                dSocket.receive(new DatagramPacket(buffer, buffer.length));
                System.out.println("recu : " + new String(buffer));
                return new String(buffer);
            } catch (SocketException e) {
                System.out.println("erreur socket : Premier envoi");
            } catch (IOException e) {
                System.out.println("erreur connexion : Premier envoi");
            }
        }
        return "e"; // erreur
    }


    /**
     * Envoie un "r" pour signaler au serveur que le paquet a bien été reçu
     * @throws ErreurConnexion
     */
    public static void envoiRetry() throws ErreurConnexion {

        try {
            byte[] buffer = "r".getBytes();
            System.out.println("Envoi Retry");
            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));
        } catch (SocketException e) {
            System.out.println("erreur socket RETRY");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion RETRY");
            throw new ErreurConnexion();
        }


    }


    /**
     * Envoie un "a" pour signaler au serveur que le paquet a bien été reçu
     * @throws ErreurConnexion
     */
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


    /**
     * Envoie un "p" pour signaler au serveur que le paquet d'initialisetion a bien été reçu
     * @throws ErreurConnexion
     */
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


    /**
     * Méthode enveloppe de la méthode recupTemp, stocke la première température reçue
     * @throws ErreurConnexion
     */
    public static void recupTempEnveloppe() throws ErreurConnexion {
        // Ajout de la temp à la ArrayList
        String[] aAjouter = decoupageRep(testPremierOk);
        for (int i = 0; i < aAjouter.length; i++)
            temperatures.add(aAjouter[i]);
        indexFirstDateLastPaquet = 0;
        envoiOk();
        byte[] buffer = "".getBytes();
        for (int i = 0; i < nbPaquets - 1; i++) {
            recupTemp(buffer);
        }
    }

    /**
     * Récupère en continu les températures et les stocke dans la ArrayList si l'on ne les a pas déjà
     * @param buffer
     * @throws ErreurConnexion
     */
    public static void recupTemp(byte[] buffer) throws ErreurConnexion {
        try {
            envoiRetry();
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
            indexFirstDateLastPaquet = temperatures.size();
            String[] aAjouter = decoupageRep(new String(buffer));
            for (int i = 0; i < aAjouter.length; i++)
                temperatures.add(aAjouter[i]);
            envoiOk();
        } else {
            envoiRetry();
            recupTemp(buffer);
        }
    }


    /**
     * Vérifie que le paquet n'a pas déjà été reçu
     * @param temp
     * @return false si l'on a déjà le paquet
     *         true sinon
     */
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

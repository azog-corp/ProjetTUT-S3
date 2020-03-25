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
        System.out.println("COMRASP");
        try {
            byte[] buffer;
            byte[] bufferTest;
            temperatures = new ArrayList<String>();

            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            dSocket = new DatagramSocket(portServeur);
            dSocket.setSoTimeout(5000);
            System.out.println("Premier envoi");
            String stringPaquetInit = premierEnvoi(date);

            if (stringPaquetInit.equals("e")) {
                dSocket.close();
                return temperatures; // erreur, on renvoie la liste vide
            }
            // envoi "p" pour dire "c'est bon j'ai bien mon paquetInit envoie les dates", le serv passe au premier paquet, "p" sert juste pour le paquetInit
            // si le serv n'a pas recu le "p" il va envoyer "p" au client, il faudra analyser la 1ère lettre (p ou date ?) et répondre en conséquence (p ou a)
            // envoi "a" lorque paquet reçu, le serveur attends de recevoir "r" pour passer au paquet suivant
            // si le serv ne recoit pas le "a" (donc recoit un "r" à un moment) il va envoyer le même paquet
            // dans ce cas il faut regarder le premier caractère et savoir si on a déjà le paquet ou pas
            // on positionnera un index pour se referrer à la première date du dernier paquet, histoire de s'y référer plus vite
            // envoi "r" pour demander au serveur où il en est, cad toutes les 5sec, le serveur reagit à ce moment-là

            dSocket.setSoTimeout(2000);
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[9999];
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

            // verifier si = 0 si oui arreter /!\ si le serveur nme recoit pas la deuxieme partie alors que il ya 0 paquets envoyer f si possible pour le supprimer
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[9999];
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
        System.out.println("COMRASP CONTINU");

        try {
            byte[] buffer;
            byte[] bufferTest;
            temperatures = new ArrayList<String>();

            int portServeur = 65230;
            InetAddress iPserveur = InetAddress.getByName("10.3.141.1");
            dSocket = new DatagramSocket(portServeur);
            dSocket.setSoTimeout(5000);
            System.out.println("Premier envoi");
            String stringPaquetInit = premierEnvoi(date);

            if (stringPaquetInit.equals("e")) {
                dSocket.close();
                return temperatures; // erreur, on renvoie la liste vide
            }
            // envoi "p" pour dire "c'est bon j'ai bien mon paquetInit envoie les dates", le serv passe au premier paquet, "p" sert juste pour le paquetInit
            // si le serv n'a pas recu le "p" il va envoyer "p" au client, il faudra analyser la 1ère lettre (p ou date ?) et répondre en conséquence (p ou a)
            // envoi "a" lorque paquet reçu, le serveur attends de recevoir "r" pour passer au paquet suivant
            // si le serv ne recoit pas le "a" (donc recoit un "r" à un moment) il va envoyer le même paquet
            // dans ce cas il faut regarder le premier caractère et savoir si on a déjà le paquet ou pas
            // on positionnera un index pour se referrer à la première date du dernier paquet, histoire de s'y référer plus vite
            // envoi "r" pour demander au serveur où il en est, cad toutes les 5sec, le serveur reagit à ce moment-là

            dSocket.setSoTimeout(2000);
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[9999];
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

            // verifier si = 0 si oui arreter /!\ si le serveur nme recoit pas la deuxieme partie alors que il ya 0 paquets envoyer f si possible pour le supprimer
            do {
                envoiRetry();
                try {
                    bufferTest = new byte[9999];
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

            // on ne ferme pas le socket vu que l'on va réutiliser tout de suite
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
                int portServeur = 65230;
                InetAddress iPserveur = InetAddress.getByName("10.3.141.1");

                System.out.println("Envoi " + essais);
                dSocket.send(new DatagramPacket(buffer, buffer.length,
                        iPserveur, portServeur));

                dSocket.setSoTimeout(3000); // Temps d'attente réception max en millisecondes
                dSocket.receive(new DatagramPacket(buffer, buffer.length));
                System.out.println("recu : " + new String(buffer));
                return new String(buffer);
            } catch (SocketException e) {
                System.out.println("erreur socket : Premier envoi");
                throw new ErreurConnexion();
            } catch (IOException e) {
                System.out.println("erreur connexion : Premier envoi");
            }
        }
        return "e"; // erreur
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
            System.out.println("erreur socket RETRY");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion RETRY");
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

package projet.src.thermometres3.outils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projet.src.thermometres3.Erreur.ErreurConnexion;
import projet.src.thermometres3.Menu;
import projet.src.thermometres3.R;

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
            dSocket.send(new DatagramPacket(buffer, buffer.length,
                    iPserveur, portServeur));
            dSocket.setSoTimeout(4000); // Temps d'attente rÃ©ception max en millisecondes
            dSocket.receive(new DatagramPacket(buffer, buffer.length));
            String s = new String(buffer, StandardCharsets.UTF_8);
            System.out.println("Recu" + s);
           /* nbTemp = Integer.parseInt(new String(buffer));
            for (int i = 0; i < nbTemp; i++) {
                dSocket.receive(new DatagramPacket(buffer, 100));
                temperatures.add(new String(buffer));
            }*/
            dSocket.close();
            return temperatures;
        } catch(SocketException e) {
            System.out.println("erreur socket");
            throw new ErreurConnexion();
        } catch (IOException e) {
            System.out.println("erreur connexion");
            throw new ErreurConnexion();
        }
    }

    /**
     * Ajoute a la fin du fichier
     * @param temperatures
     */
    public static void ajouterFichier(ArrayList<String> temperatures) {
        System.out.println("Recu");
        for(int i =0; i < temperatures.size(); i++) {
            System.out.println("Lu :"+temperatures.get(i));
        }
    }

    public static void majDerniereConnexion(Context myContext) {
        new Communication().execute(myContext);

    }
}

/*


public ArrayList<String> lectureFichier(Date date) throws ParseException{
		//CONVERSION = fct convertir date en string
		try {
			String ligne;
			ArrayList<String> temp = new ArrayList<String>();
			BufferedReader fic = new BufferedReader(new FileReader(new File("temperatures.txt")));
			while((ligne = fic.readLine()) != null) {
				if(date.compareTo(conversion(ligne)) < 0) {
					temp.add(ligne);
				}
			}
			return temp;
		}catch(IOException e) {
			System.out.println(e);
		}
		return null;
	}


	public String preparerRqt(ArrayList<String> temperature) {
	//TODO verif taille 2600 max
		StringBuilder chaine;
		for(int i =0; i < temperature.size(); i++) {
			chaine.append("|"+temperature.get(i));
		}
		return chaine.toString();
	}


	public String[] decoupageRep(String rep) {
		return rep.split("|",9999);

	}

	/**
     * Ajoute a la fin du fichier
     * @param temperatures
     */
    public static void ajouterFichier(String temperatures) {
        System.out.println("Recu");
        decoupageRep(temperatures);
        for(int i =0; i < temperatures.size(); i++) {
            System.out.println("Lu :"+temperatures.get(i));
        }
    }


    private static void ecrireFinFichier(Context myContext, String[] tempAEcrire) {
		// Positionnement en fin de fichier pour ne pas écraser les températures déjà présentes
		try (BufferedWriter fichier = new BufferedWriter(new FileWriter(myContext.getFilesDir() + "/fichierTemp.txt", true))) {
			// Lecture de la ArrayList et écriture
			for(int i = 0; i < tempAEcrire.length; i++) {
				fichier.write(tempAEcrire[i] + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


//retracer graphe   graph.redrawAll();


05/05/2000 11:11:11 -300
    */

package projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Communication {

	private DatagramSocket dSocket ;
	private InetAddress iPserveur;
	private int portServeur;

	public void transfert(String date,Context myContext) {
		ajouterFichier(comRasp(date)); // ajoute les temp a la fin du fichier
	}
	/**
	 * Ajoute a la fin du fichier
	 * @param temperatures
	 */
	private void ajouterFichier(ArrayList<String> temperatures) {
		// TODO Auto-generated method stub

	}

	public void remiseAzero(Context myContext) {
		RechercheTemperature.supprimerTemp(myContext);
		transfert("01/01/1900",myContext);
	}

	public ArrayList<String> comRasp(String date) {
		try {
			ArrayList<String> temperatures = new ArrayList<String>();
			int nbTemp = 0;
			byte[] buffer = new byte[100]; // message sous forme de tableau d'octets
			dSocket.send(new DatagramPacket(buffer, buffer.length, 
					iPserveur, portServeur));
			dSocket.setSoTimeout(4000); // Temps d'attente réception max en millisecondes 
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
	
	public majDerniereConnexion(Context myContext) {
		//lire fichier derniere co dans date
		transfert(date,myContext);
		//mettre a jour fichier connexion avec date actuelle
	}
}

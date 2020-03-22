package projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {
	/** Liste desclients a traiter */
	static ArrayList<Client> listeClient = new ArrayList<Client>();
	static DatagramSocket socket; 
	static byte[] buffer;
	static String message;
	static DatagramPacket paquet;
	static int port = 65230;

	public static ArrayList<Client> getListeClient() {
		return listeClient;
	}

	public static void setListeClient(ArrayList<Client> listeClient) {
		Serveur.listeClient = listeClient;
	}
	public static void setClient(int index,Client element) {
		Serveur.listeClient.set(index, element);
	}


	public static void main(String[] args) {
		System.out.println("Serveur lancé !");
		int compteur = 0;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e1) {
		} 
		traiterClient();
		while(true) {
			try {
				buffer = new byte[100];
				System.out.println("INIT BUFFER" + new String(buffer));
				paquet = new DatagramPacket(buffer,100);
				System.out.println("ATTENTE MESSAGE");
				socket.receive(paquet);
				message = new String(buffer);
				System.out.println("Message :"+message);
				int index = getIndexClient();
				switch(message.charAt(0)) {
				case 'p':
					nbPaquetRecu(index);
					break;
				case 'i':
					inscription();
					break;
				case 'a':
					acquittement(message,index);
					break;
				case 'r': // r = retry rappel au serveur
					if(listeClient.get(index).getNbPaquetRecu()) {
						verifierPretAEnvoyer(index);
					} else {
						envoyerNbPaquet(index);
					}
					break;
				}
				compteur++;
				affichageEtat();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void envoyerNbPaquet(int index) throws IOException {
		Client client = listeClient.get(index);
		String msg = "p|"+client.getPaquets().size();
		System.out.println("ENVOI NBPAQUET : " +  msg);
		socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
				client.getAdresseIp(), port));
	}

	public static int getIndexClient() {
		for(int i=0; i < listeClient.size(); i++) {
			if(listeClient.get(i).getAdresseIp().equals(paquet.getAddress())) {
				return i;
			}
		}
		return -1;
	}

	private static void nbPaquetRecu(int index) {
		listeClient.get(index).setNbPaquetRecu(true);
	}

	private static void affichageEtat() {
		for(int i =0; i <listeClient.size(); i++) {
			System.out.println("Client : numero :" + i + listeClient.get(i).getAdresseIp()
					+ " Etat Temp traiter :" +listeClient.get(i).isTempTraiter());
		}
	}

	private static void verifierPretAEnvoyer(int index) throws IOException{
		System.out.println("Verification pret a envoyer");
		if(listeClient.get(index).isTempTraiter()) {
			System.out.println("Pret a envoyer :" + listeClient.get(index).getAdresseIp());
			envoyer(listeClient.get(index),listeClient.get(index).getAdresseIp());
		}		
	}

	private static void envoyer(Client client,InetAddress adresseIp)  throws IOException{
		String msg = client.paquet();
		System.out.println("ENVOI MESSAGE : " + msg);
		socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
				adresseIp, port));
	}

	private static void traiterClient() {
		System.out.println("Creation thread");
		new Service("Thread");
	}


	public static void inscription() {
		boolean ajouter = true;
		System.out.println("Message recu :" + message);
		String[] decomp = message.split("\\|");
		System.out.println("Message apres decomposition :" + decomp[1]);
		for(int i = 0; i < listeClient.size();i++) {
			if(listeClient.get(i).getAdresseIp().equals(paquet.getAddress())) {
				if(listeClient.get(i).envoiFini()) {
					System.out.println("Client oublié :" + listeClient.get(i).getAdresseIp());
					listeClient.remove(i);
				} else {
					ajouter = false;
				}
				
			}
		}
		if(ajouter) {
			listeClient.add(new Client(paquet.getAddress(),decomp[1]));
			System.out.println("AJOUT CLIENT TAILLE ARRAYLIST :" +listeClient.size() );
		}
		ack(paquet.getAddress());
	}

	private static void ack(InetAddress adresseIp) {
		String msg = "ack";
		try {
			socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
					adresseIp, port));
		} catch (IOException e) {
			System.out.println("Erreu ACK");
		}
	}

	public static void acquittement(String message,int index) throws IOException{
		System.out.println("ACK");
		System.out.println("PAQUET BON");
		listeClient.get(index).paquetOk();
		if(listeClient.get(index).envoiFini()) {
			System.out.println("TOUS LES PAQUETS ACK");
			System.out.println("Client fini :" + listeClient.get(index).getAdresseIp());
			listeClient.remove(index);
		}

	}

}

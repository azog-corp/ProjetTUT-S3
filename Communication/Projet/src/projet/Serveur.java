package projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {
	/** Liste des clients a traiter */
	static ArrayList<Client> listeClient = new ArrayList<Client>();
	/** Socket �cout�*/
	static DatagramSocket socket; 
	/** Buffer contenant le message recu */
	static byte[] buffer;
	/** Message recu */
	static String message;
	/** Paquet recu */
	static DatagramPacket paquet;
	/** Port a ecouter */
	static int port = 65230;
	
	/**
	 * Getter de la liste de client
	 * @return listeClient
	 */
	public static ArrayList<Client> getListeClient() {
		return listeClient;
	}

	/**
	 * Met a jour la liste de client
	 * @param listeClient la nouvelle liste de client
	 */
	public static void setListeClient(ArrayList<Client> listeClient) {
		Serveur.listeClient = listeClient;
	}
	
	/**
	 * Met ajour le client a un index
	 * @param index index du client a modifier
	 * @param element nouveau client
	 */
	public static void setClient(int index,Client element) {
		Serveur.listeClient.set(index, element);
	}


	public static void main(String[] args) {
		System.out.println("Serveur lancé !");
		/* Initialisation du socket */
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e1) {
		} 
		creerThread(); //Creation du thread
		while(true) {
			try {
				/* Initialisation du buffer */
				buffer = new byte[100];
				System.out.println("INIT BUFFER" + new String(buffer));
				/* Initialisation du paquet */
				paquet = new DatagramPacket(buffer,100);
				System.out.println("ATTENTE MESSAGE");
				socket.receive(paquet); //attente de message
				message = new String(buffer); //Converti le buffer du message recu en string
				System.out.println("Message :"+message);
				int index = getIndexClient(); //recupere l'index du client si celui existe
				switch(message.charAt(0)) {
				case 'p': //si 'p' alors le client a bien recu le nombre de paquet
					nbPaquetRecu(index); //on place le boolean a true pour le client
					break;
				case 'i': // si i alors on inscrit le client
					inscription();
					break;
				case 'a': //si a alors le client a recu le dernier paquet
					acquittement(index);
					break;
				case 'r': // r = retry rappel au serveur
					if(listeClient.get(index).getNbPaquetRecu()) { //si le client a deja recu le nombre de paquet
						verifierPretAEnvoyer(index);
					} else { //sinon envoi le nombre de paquet
						envoyerNbPaquet(index);
					}
					break;
				}
				affichageEtat(); //affiche l'etat des clients
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Envoi le nombre de pqauet a envoyer
	 * @param index index du client trait�
	 * @throws IOException
	 */
	private static void envoyerNbPaquet(int index) throws IOException {
		Client client = listeClient.get(index);
		String msg = "p|"+client.getPaquets().size();
		System.out.println("ENVOI NBPAQUET : " +  msg);
		socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
				client.getAdresseIp(), port));
	}

	/**
	 * Recupere l'index du client qui correspond a l'addresse IP 
	 * @return l'index lorsque celui ci existe
	 * sinon -1
	 */
	public static int getIndexClient() {
		for(int i=0; i < listeClient.size(); i++) {
			if(listeClient.get(i).getAdresseIp().equals(paquet.getAddress())) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Place le boolean nbpaquetrecu a true pour le client a l'index index
	 * @param index index du client a modifier
	 */
	private static void nbPaquetRecu(int index) {
		listeClient.get(index).setNbPaquetRecu(true);
	}

	/**Affiche l'etat de tous les clients */
	private static void affichageEtat() {
		for(int i =0; i <listeClient.size(); i++) {
			System.out.println("Client : numero :" + i + listeClient.get(i).getAdresseIp()
					+ " Etat Temp traiter :" +listeClient.get(i).isTempTraiter());
		}
	}

	/**
	 * Verifie si le client a l'index est pret a envoyer
	 * @param index index du Client
	 * @throws IOException
	 */
	private static void verifierPretAEnvoyer(int index) throws IOException{
		System.out.println("Verification pret a envoyer");
		if(listeClient.get(index).isTempTraiter()) {
			System.out.println("Pret a envoyer :" + listeClient.get(index).getAdresseIp());
			envoyer(listeClient.get(index),listeClient.get(index).getAdresseIp());
		}		
	}

	/**
	 * Envoi un paquet
	 * @param client Client a trqiter pour lequel on recupere le paquet
	 * @param adresseIp adresseIp du client
	 * @throws IOException si l'envoi echoue
	 */
	private static void envoyer(Client client,InetAddress adresseIp)  throws IOException{
		String msg = client.paquet();
		System.out.println("ENVOI MESSAGE : " + msg);
		socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
				adresseIp, port));
	}

	/**
	 * Creer un thread qui s'occupe de traiter les clients
	 */
	private static void creerThread() {
		System.out.println("Creation thread");
		new Service("Thread");
	}

	/**
	 * Inscrit le client a la liste des clients
	 * Recupere la date envoy� par le client
	 */
	public static void inscription() {
		boolean ajouter = true;
		System.out.println("Message recu :" + message);
		String[] decomp = message.split("\\|");
		System.out.println("Message apres decomposition :" + decomp[1]);
		for(int i = 0; i < listeClient.size();i++) { //on verifie que le client n'existe pas deja dans la liste
			if(listeClient.get(i).getAdresseIp().equals(paquet.getAddress())) { // si le client existe
				if(listeClient.get(i).envoiFini()) { //mais qu'il est fini
					System.out.println("Client oublié :" + listeClient.get(i).getAdresseIp());
					listeClient.remove(i); //on le supprime c'est un client oublie pour lequel le dernier ack n'est pas arrive
				} else { //sinon le client est deja en traitement
					ajouter = false;
				}
				
			}
		}
		if(ajouter) { //S'il faut bien l'ajouter alors le programme l'ajoute
			listeClient.add(new Client(paquet.getAddress(),decomp[1]));
			System.out.println("AJOUT CLIENT TAILLE ARRAYLIST :" +listeClient.size() );
		}
		ack(paquet.getAddress()); // renvoi un ack au client pour confirmer l'inscription
	}
	
	/**
	 * Envoi un ack au client
	 * @param adresseIp adresse ip du client
	 */
	private static void ack(InetAddress adresseIp) {
		String msg = "ack";
		try {
			socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length,
					adresseIp, port));
		} catch (IOException e) {
			System.out.println("Erreu ACK");
		}
	}

	/**
	 * Quand le serveur recoit un ack il supprime le dernier paquet envoy�
	 * pour le client correspondant
	 * @param index index du client
	 * @throws IOException
	 */
	public static void acquittement(int index) throws IOException{
		System.out.println("ACK");
		System.out.println("PAQUET BON");
		listeClient.get(index).paquetOk();
		if(listeClient.get(index).envoiFini()) { //Si il n'y a plus de paquet supprime le client
			System.out.println("TOUS LES PAQUETS ACK");
			System.out.println("Client fini :" + listeClient.get(index).getAdresseIp());
			listeClient.remove(index);
		}

	}

}

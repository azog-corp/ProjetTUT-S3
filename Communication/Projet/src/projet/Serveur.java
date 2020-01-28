package projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import projet.Service;

public class Serveur {
   
  public static void main(String[] args) {
    
    try {
        DatagramSocket socket = new DatagramSocket(4523); 
        byte[] buffer;
        DatagramPacket paquet;
        System.out.println("Serveur lancé !");
        int compteur = 0;
         // Boucle sans fin pour lire un message et l'afficher
        // puis renvoyer un accusé réception
        while(true) {
        	buffer = new byte[100];
        	paquet = new DatagramPacket(buffer,100);
            socket.receive(paquet);
            new Service(""+compteur,socket,paquet,buffer);
            compteur++;
        }
      
    } catch(SocketException e) {
        System.out.println("Impossible de lancer le serveur :"+e);
    } catch(IOException e) {
        //stub
    }
  }

}

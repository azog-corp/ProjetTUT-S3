/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierremarie.combalbe
 */
public class Service extends Thread{
	byte[] buffer;

	DatagramPacket paquet;

	DatagramSocket socket;

	Thread t;

	public Service(String name,DatagramSocket socket,DatagramPacket paquet, byte[] buffer){
		super(name);
		this.socket = socket;
		this.paquet = paquet;
		this.buffer = buffer;
		System.out.println("statut du thread " + name + " = " +this.getState());
		this.start();
		System.out.println("statut du thread " + name + " = " +this.getState());

	}



	public void run(){
		System.out.println("RUN");
		String msg, ar; // Message reçu et accusé réception
		InetAddress ipClient;
		int portClient;
		try {
			System.out.println("Boucle paquet");

			msg = new String(buffer);
			msg = msg.trim();
			System.out.println(msg);
			System.out.println("te");
			// Lire un paquet
			ipClient = paquet.getAddress();
			portClient = paquet.getPort();
			System.out.println("Recu : " + msg +" Thread : " + this.getName() 
			+"IP : " + ipClient + " PORT : " + portClient);

			ar = "Recu : "+msg;
			/*socket.send(new DatagramPacket(ar.getBytes(), ar.getBytes().length,
					ipClient, portClient));*/
			for(int i=0; i < 100; i++) {
				ar = "Recu : " + i;
				socket.send(new DatagramPacket(ar.getBytes(), ar.getBytes().length,
						ipClient, portClient));
			}
			ar = "Fin";
			socket.send(new DatagramPacket(ar.getBytes(), ar.getBytes().length,
					ipClient, portClient));
			
			
		} catch (IOException e) {
			System.out.println("nsm" + e);
		}
	}

}


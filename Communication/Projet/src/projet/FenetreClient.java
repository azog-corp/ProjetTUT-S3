package projet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class FenetreClient extends javax.swing.JFrame {
	private DatagramSocket dSocket ;
	private InetAddress iPserveur;
	private int portServeur;
	/**
	 * Creates new form FenetreClient
	 */
	public FenetreClient() {
		initComponents();  
		txtAccuseReception.setEditable(false);
		txtMessage.setEnabled(false);
		boutonEnvoyer.setEnabled(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		txtPort = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtMessage = new javax.swing.JTextArea();
		boutonEnvoyer = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		txtAccuseReception = new javax.swing.JTextArea();
		txtIPServeur = new javax.swing.JTextField();
		boutonValider = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jLabel1.setText("IP Serveur");

		jLabel2.setText("Port");

		jLabel3.setText("Message");

		txtMessage.setColumns(20);
		txtMessage.setRows(5);
		txtMessage.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				txtMessageMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(txtMessage);

		boutonEnvoyer.setText("Envoyer");
		boutonEnvoyer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boutonEnvoyerAction(evt);
			}
		});

		jLabel4.setText("Accusé réception serveur");

		txtAccuseReception.setColumns(20);
		txtAccuseReception.setRows(5);
		jScrollPane2.setViewportView(txtAccuseReception);

		boutonValider.setText("Valider");
		boutonValider.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boutonValiderAction(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(19, 19, 19)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(boutonEnvoyer))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1)
												.addComponent(jLabel2))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(txtIPServeur, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
												.addComponent(txtPort))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(boutonValider, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel4)
												.addComponent(jLabel3))
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(txtIPServeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel2)
												.addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup()
										.addGap(23, 23, 23)
										.addComponent(boutonValider)))
						.addGap(18, 18, 18)
						.addComponent(jLabel3)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
										.addGap(22, 22, 22)
										.addComponent(boutonEnvoyer)))
						.addGap(18, 18, 18)
						.addComponent(jLabel4)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addContainerGap())
				);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void boutonEnvoyerAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonEnvoyerAction
		try {
			// Envoyer message au serveur

			byte[] buffer; // message sous forme de tableau d'octets
			buffer = txtMessage.getText().getBytes();
			dSocket.send(new DatagramPacket(buffer, buffer.length, 
					iPserveur, portServeur));

			// Lire et afficher accusé réception du serveur
			buffer = new byte[100];
 // Temps d'attente réception max en millisecondes 
			/*dSocket.receive(new DatagramPacket(buffer,2000));
			String reponse = new String(buffer);
			System.out.println(reponse);
			txtAccuseReception.append(reponse.trim()+"\n");*/

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Envoi impossible :"+e);
		}
	}//GEN-LAST:event_boutonEnvoyerAction

	private void txtMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMessageMouseClicked
		txtMessage.setText("");
	}//GEN-LAST:event_txtMessageMouseClicked

	private void boutonValiderAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonValiderAction
		try {
			iPserveur = InetAddress.getByName(txtIPServeur.getText());
			portServeur = Integer.parseInt(txtPort.getText());
			dSocket = new DatagramSocket(); // Lié à un n'importe quel port local disponible
			txtIPServeur.setEnabled(false);
			txtPort.setEnabled(false);
			boutonValider.setEnabled(false);
			txtMessage.setEnabled(true);
			boutonEnvoyer.setEnabled(true);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,"Erreur : "+ex);
		}
	}//GEN-LAST:event_boutonValiderAction

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(FenetreClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(FenetreClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(FenetreClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FenetreClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FenetreClient().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton boutonEnvoyer;
	private javax.swing.JButton boutonValider;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea txtAccuseReception;
	private javax.swing.JTextField txtIPServeur;
	private javax.swing.JTextArea txtMessage;
	private javax.swing.JTextField txtPort;
	// End of variables declaration//GEN-END:variables
}

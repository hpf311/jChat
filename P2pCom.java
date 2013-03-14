package jChat;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * P2P Logik
 * 
 * @author Thomas Traxler
 * @version 2013-02-22
 * 
 */
// TODO: Kommando verwaltung nur Serverseitig Client-Objekte mit weiteren Daten
// ausstatten
public class P2pCom extends Thread implements JChatCom {
	private JChatAuthenticator jca;
	private JChatGUI jcg;
	private String peerName;
	private int[] cc;
	private Hashtable peers;
	private boolean overhead;
//TODO TU WAS
	public P2pCom(P2pAuth jca, JChatGUI jcg) {

		this.jca = jca;
		// try {
		// this.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		this.jcg = jcg;
		cc = new int[3];
		cc[0]=0;
		cc[1]=0;
		cc[2]=0;
		jcg.AddChatListener(new SrvChatListener());
		jcg.addMessage("Peer gestartet", cc);
		this.start();
		jcg.open();
	}

	
	public void run() {
		StringBuilder message;
		while (true) {
			try {
				message = new StringBuilder();
				if (jca.ready(message)) {
					if (overhead) {// Wenn es nicht User Kommunikation ist
						if (message.equals("end")) {// Overhead ende
							overhead = false;
						} else if (true) {// Nicht User Kommunikation
						}
					} else {
						if (!message.equals("")) {
							message.append( " ");
							String[] splitM = message.toString().split(" ", 5);
							if (splitM[0].equals("/msg")) {
								jcg.addMessage(peerName + ":" + splitM[4], cc);// fuegt eingehende Nachricht lokal hinzu
							} else {
								if (splitM[0].equals("/error")){
									System.out.println("Error: " + splitM[1]);
								}else if (splitM[0].equals("/overhead")) {
									overhead = true;
								}
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Server ChatListener Leitet ausgehende nachrichten an sendMessage weiter
	 * und fuegt sie der lokalen Anzeige hinzu Leitet Beenden ein.
	 * 
	 * @author Thomas Traxler
	 * 
	 */
	public class SrvChatListener implements ChatListener {

		// public void actionPerformed(ActionEvent e) {
		// String message = jcg.equalsChatLine(e.getSource());
		// if ( !message.equals("")) {
		// sendMessage("SERVER: "+message);
		// jcg.addMessage("SERVER: "+message, null);
		// }
		// if ( jcg.equalsDisconnect(e.getSource()))
		// stopConnection();
		// }

		@Override
		public void handleEvent(Event e) {
			System.out.println("A");
			String message = "";
			if (e.character == '\b') {

			} else {

				message = jcg.equalsChatLine(e.widget);
				if (!message.equals("")) {
					if(message.charAt(0)=='/'){
						
					}else{
						
						sendMessage("/msg " +Integer.toHexString(cc[0])+" "+Integer.toHexString(cc[1]) +" "+Integer.toHexString(cc[2])+" "+ message + "\n");
						jcg.addMessage(peerName+": " + message, cc);
					}
				}
			}

		}
	}

	/**
	 * Listenerthread für einen Clientinput
	 * 
	 * @author Thomas Traxler
	 * 
	 */
	// public class P2pList extends Properties implements APeer{
	// private String peerName;
	// private Color c = new Color (0,0,0);
	//
	// public P2pList(String name){
	// this.peerName = name;
	// }
	//
	// public void updated (PrintWriter pw){
	// this.list(pw);
	// }
	//
	// public void update (BufferedReader br) throws IOException{
	// this.load(br);
	// }
	//
	//
	// // public void addSocket( Socket s) throws IOException{
	// // this.s=s;
	// // this.br = new BufferedReader(new
	// InputStreamReader(s.getInputStream()));
	// // this.pw = new PrintWriter(s.getOutputStream(), true);
	// //
	// // pw.print("/name "+peerName+"\n");
	// // }
	//
	// // public void run(){
	// // while(true){
	// // try {
	// // this.sleep(100);
	// // } catch (InterruptedException e1) {
	// // e1.printStackTrace();
	// // }
	// // try {
	// // if(br.ready()){
	// // String message =br.readLine() ;
	// // if(message != ""){
	// // if(message.charAt(0)!='/'){
	// // int[] a = {c.getRed(),c.getGreen(),c.getBlue()};
	// // jcg.addMessage(peerName+":"+message, a );//fuegt eingehende Nachricht
	// lokal hinzu
	// // sendMessage(peerName+":"+message+"\n");//leitet eingehende Nachricht
	// an alle Clients weiter.
	// // }else{
	// // message = message+" ";
	// // String [] splitM = message.split(" ",2);
	// // if (splitM[0].equals("/error"))
	// // System.out.println("Error: "+splitM[1]);
	// // else if (splitM[0].equals("/name")){
	// // if(nl.changeName(peerName, splitM[1])){
	// // peerName = splitM[1];
	// // pw.print("/name "+splitM[1]+"\n");
	// // pw.flush();
	// // jcg.setNameList(nl);
	// // sendMessage("/namelist ");
	// // ArrayList n = nl.getNames();
	// // sendMessage(""+n.get(0));
	// // for (int i = 1;i<nl.getNames().size();i++){
	// // sendMessage(";"+n.get(i));
	// // }
	// // sendMessage("\n");
	// // }
	// // }
	// // else if(splitM[0].equals("/col")){
	// //// c = InReader.getColor(splitM[1]);
	// // pw.print(message+"\n");
	// // }
	// // }
	// // }
	// // }
	// // } catch (IOException e) {
	// // e.printStackTrace();
	// // }
	// // }
	// // }
	//
	// public String getPeerName() {
	// return peerName;
	// }
	//
	// public void setPeerName(String name) {
	// this.peerName = name;
	// }
	//
	// }

	@Override
	public void sendMessage(String message) {

		try {
			jca.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stopConnection() {
		jca.stopConnection();

	}

}

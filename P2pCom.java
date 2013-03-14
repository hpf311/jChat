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
	private BetterGUI jcg;
	private String peerName;
	private int[] cc;
	private ArrayList<String> peers= new ArrayList<String>();;
	private boolean overhead;
//TODO TU WAS
	public P2pCom(P2pAuth jca, BetterGUI jcg) {

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
		peerName="Peer_"+(Math.random()*10000)+1000;
		
		peers.add(peerName);
	}

	
	public void run() {
		StringBuilder message;
		while (true) {
			try {
				message = new StringBuilder();
				if (jca.ready(message)) {
					if(jcg.isDisposed()){
						stopConnection();
						System.exit(0);
						//FEATURE: Programmschluss erst bei neuer eingehender Nachricht :)
					}
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
								int[] c = {Integer.parseInt(splitM[1]),Integer.parseInt(splitM[2]),Integer.parseInt(splitM[3])};
								jcg.addMessage(peerName + ":" + splitM[4], c);// fuegt eingehende Nachricht lokal hinzu
							} else {
								if (splitM[0].equals("/error")){
									System.out.println("Error: " + splitM[1]);
								}else if (splitM[0].equals("/overhead")) {
									overhead = true;
								}else if (splitM[0].equals("/namelist")){
									peers.clear();
									String m = message.toString().split(" ",2)[1].substring(1);
									for(int i = 0; i < m.split(" ").length;i++){
										peers.add(m.split(" ")[i].substring(0, m.split(" ")[i].length()-1));
									}
									if(peers.indexOf(peerName)==-1){
										peers.add(peerName);
										
										sendMessage("/namelist "+peers.toString());
										
									}
									jcg.setNameList(peers);
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


		@Override
		public void handleEvent(Event e) {
			String message = "";
			
			if ((e.keyCode<13||e.keyCode>13)&&e.keyCode!=0) {
			} else {
				message = jcg.equalsChatLine(e.widget);
				if (!message.equals("")) {
					//Nicht Chatnachricht
					if(message.charAt(0)=='/'){
						String[] splitM = message.split(" ",5);
						//Farbenaenderung
						if(splitM[0].equals("/col")){
							try{
								cc[0]=Integer.parseInt(splitM[1]);
								if(cc[0]<0 || cc[0]>255)
									throw new NumberFormatException();
								cc[1]=Integer.parseInt(splitM[2]);
								if(cc[0]<0 || cc[0]>255)
									throw new NumberFormatException();
								cc[2]=Integer.parseInt(splitM[3]);
								if(cc[0]<0 || cc[0]>255)
									throw new NumberFormatException();
							}catch(NumberFormatException nfe){
								int a[] = {255,0,0};
								jcg.addMessage("Invalid arguments", a);
								cc[0]=0;
								cc[1]=0;
								cc[2]=0;
							}
							//Namensaenderung
						}else if(splitM[0].equals("/name")){
							if(peers.indexOf(splitM[1])!=-1){
								int[] c = {255,0,0};
								jcg.addMessage("NAME VERGEBEN", c);
							}else{

								peers.remove(peerName);
								peerName = splitM[1];
								peers.add(peerName);
								sendMessage("/namelist "+peers.toString());
							}
						}
					}else{
						sendMessage("/msg " +cc[0]+" "+cc[1] +" "+cc[2]+" "+ message);
					}
				}
			}

		}
	}

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
		peers.remove(peerName);
		peerName=null;
		sendMessage("/namelist "+peers.toString());
		jca.stopConnection();

	}

}

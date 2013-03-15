package jChat;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Event;

/**
 * P2P Logik
 * 
 * @author Thomas Traxler
 * @version 2013-02-22
 * 
 */


public class P2pCom extends Thread implements JChatCom {
	private JChatAuthenticator jca;
	private BetterGUI jcg;
	private String peerName;
	private int[] cc;//Farbeinstellung
	private ArrayList<String> peers= new ArrayList<String>();//Liste aller peers
	private boolean overhead;//Nicht in verwendung. Fuer weitere Funktionen hilfreich.

	/**
	 * Einzig zulaessiger Konstruktor mit kompatibler Gui und Authschicht
	 * @param jca
	 * @param jcg
	 */
	public P2pCom(P2pAuth jca, BetterGUI jcg) {

		this.jca = jca;
		this.jcg = jcg;
		//Defaultfarbe Schwarz
		cc = new int[3];
		cc[0]=0;
		cc[1]=0;
		cc[2]=0;
		jcg.AddChatListener(new PeerChatListener());
		//Default'Name
		peerName="Peer_"+(int)(Math.random()*10000);
		
		peers.add(peerName);
		jcg.setAName(peerName);
		jcg.addMessage("Peer gestartet", cc);
		this.start();
		jcg.open();
	}

	
	public void run() {
		StringBuilder message;
		//Namelist update
		sendMessage("/namelist "+peers.toString());
		while (true) {
			try {
				message = new StringBuilder();
				if (jca.ready(message)) {
					if(jcg.isDisposed()){
						stopConnection();
						System.exit(0);
						//FEATURE: Programmschluss erst bei neuer eingehender Nachricht :)
					}
					
					//Noch nicht verwendete Funktion. 
					if (overhead) {// Wenn es nicht User Kommunikation ist
						if (message.equals("end")) {// Overhead ende
							overhead = false;
						} else if (true) {// Nicht User Kommunikation
						}
						
					} else {
						if (!message.equals("")) {
							//Wichtig, Schutz vor zu wenig Textteilen.
							message.append(" ");
							String[] splitM = message.toString().split(" ", 5);
							if (splitM[0].equals("/msg")) {//Ist eingehende Nachricht von User
								int[] c = {Integer.parseInt(splitM[1]),Integer.parseInt(splitM[2]),Integer.parseInt(splitM[3])};//Farbaufschluesselung der eingehenden Nachricht.
								jcg.addMessage(splitM[4], c);// fuegt eingehende Nachricht lokal hinzu
							} else {//Ist eingehende Nachricht von keinem User sondern Programm
								
								if (splitM[0].equals("/error")){
									System.out.println("Error: " + splitM[1]);
								}else if (splitM[0].equals("/overhead")) {
									overhead = true;
								}else if (splitM[0].equals("/namelist")){//Eingehende Namensliste
									//Uebernehmen
									peers.clear();
									String m = message.toString().split(" ",2)[1].substring(1);
									for(int i = 0; i < m.split(" ").length;i++){
										peers.add(m.split(" ")[i].substring(0, m.split(" ")[i].length()-1));
									}
									//Falls eigener Name bei eingegangener Liste nicht vorhanden, hinzufuegen und eigene Liste zurueckschicken.
									if(peers.indexOf(peerName)==-1){
										peers.add(peerName);
										
										sendMessage("/namelist "+peers.toString());
										
									}
									//Gui-update
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
	 * Peer ChatListener Leitet ausgehende nachrichten an sendMessage weiter. Anzeige erfolgt durch emofangen der eigenenen Nachricht.
	 * 
	 * @author Thomas Traxler
	 * 
	 */
	public class PeerChatListener implements ChatListener {


		@Override
		public void handleEvent(Event e) {
			String message = "";
			
			if (e.keyCode==13 || e.keyCode==0){ //Wenn enter gedrueckt oder nicht Tastaturevent.
				message = jcg.equalsChatLine(e.widget);//Wenn Event von dieser Gui kommt)
				if (!message.equals("")) {
					//Nicht Chatnachricht
					if(message.charAt(0)=='/'){
						String[] splitM = (message+" ").split(" ",5);
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
								sendMessage ("/msg 0 125 50 "+peerName+ " changed his name to "+splitM[1]);
								peerName = splitM[1];
								peers.add(peerName);
								jcg.setAName(peerName);
								sendMessage("/namelist "+peers.toString());
							}
						}else {
							int c[] = {255, 0, 0};
							jcg.addMessage("Command not found.", c);
						}
					}else{//if(message.charAt(0)=='/')
						//Nachricht senden. Form:
						// /msg Red Green Blue peername:message
						sendMessage("/msg " +cc[0]+" "+cc[1] +" "+cc[2]+" "+ peerName + ":" +message);
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
			e.printStackTrace();
		}

	}
	
	/**
	 * Peer beenden
	 */
	@Override
	public void stopConnection() {
		peers.remove(peerName);
		sendMessage("/msg 0 125 50 "+peerName+" left the chat");
		peerName=null;
		sendMessage("/namelist "+peers.toString());
		jca.stopConnection();

	}

}

package jChat;

import jChat.SrvCom.Peer;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Serververbindung nach aussen.
 * @author Thomas Traxler
 *
 */
public class SrvSocket extends Thread implements JChatSocket
{
	
	private ServerSocket srvS;
	private List<Peer> clients = Collections.synchronizedList(new ArrayList());
	
	public SrvSocket(){
		try {
			srvS = new ServerSocket(11492);//Verbindung auf Port 1492
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	/**
	 * Sended eine Message an alle outgoing streams
	 * @param message
	 */
	@Override
	public void sendMessage (String message){
		for(int i = 0;i < clients.size();i++){
			clients.get(i).getPw().print(message);
			clients.get(i).getPw().flush();
		}
	}
	
	public List<Peer> getPeer (){
		return clients;
	}
	
	public void accept (APeer p) throws IOException {
		
		p.addSocket(srvS.accept());//Gibt auf Anfrage Socket zurueck, blockiert bis zur Anfrage 
		
	}
	
	/**
	 * Fiegt Client und passende input und output streams den Listen hinzu
	 * @param s
	 * @throws IOException
	 */
	public void addClient( Peer p) throws IOException{
		
			clients.add(p);
			
	}
	
	@Override
	public void stopConnection() {
		// TODO Auto-generated method stub
		
	}
	


}

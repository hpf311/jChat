package jChat;

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
	private List<Socket> clients = Collections.synchronizedList(new ArrayList());
	private List<PrintWriter> outgoing = Collections.synchronizedList(new ArrayList());
	private List<BufferedReader> incomming = Collections.synchronizedList(new ArrayList());
	
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
		for(int i = 0;i < outgoing.size();i++){
			outgoing.get(i).print(message);
			outgoing.get(i).flush();
		}
	}
	
	public List<BufferedReader> getReader (){
		return incomming;
	}
	
	public Socket accept () throws IOException {
		
		return srvS.accept();//Gibt auf Anfrage Socket zurueck, blockiert bis zur Anfrage 
		
	}
	
	/**
	 * Fiegt Client und passende input und output streams den Listen hinzu
	 * @param s
	 * @throws IOException
	 */
	public void addClient( Socket s) throws IOException{
		
			clients.add(s);
			incomming.add(new BufferedReader(new InputStreamReader(s.getInputStream())));
			outgoing.add( new PrintWriter(s.getOutputStream(), true));
			
	}
	
	@Override
	public void stopConnection() {
		// TODO Auto-generated method stub
		
	}
	


}

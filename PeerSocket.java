package jChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;



public class PeerSocket implements JChatSocket
{

	private InetSocketAddress isa;
	private Socket cltS;
	private PrintWriter outgoing;
	private BufferedReader incomming;
	
	public PeerSocket (InetSocketAddress isa){
		this.isa = isa;
	}
	
	
	public boolean connect (){
		try {
			cltS = new Socket (isa.getAddress(),isa.getPort());
			incomming = new BufferedReader(new InputStreamReader(cltS.getInputStream()));
		    outgoing = new PrintWriter(cltS.getOutputStream(), true);
		} catch (IOException e) {
			return false;
		}
		return true;
		
	}
	

	public BufferedReader getReader (){
		return incomming;
	}

	
	@Override
	public void sendMessage(String message) {
		
		outgoing.print(message);
		outgoing.flush();
		
	}

	
	@Override
	public void stopConnection() {
		// TODO Auto-generated method stub
		
	}


}

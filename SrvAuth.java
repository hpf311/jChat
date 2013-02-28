package jChat;

import jChat.SrvCom.Peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Authentifizierungsebene des Servers
 * @author Thomas Traxler
 * @version
 */

public class SrvAuth extends Thread implements JChatAuthenticator
{
	private SrvSocket jcs; //Socket

    public SrvAuth(SrvSocket jcs) 
    {
        this.jcs=jcs;
        this.start();
    }
    
    /**
     * Auhtentifizieren neuer Clients
     */
//    public void run(){
//    	while(true){
//    		try {
//    			jcs.addClient(jcs.accept());//Jeder Client wird akzeptiert
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//    	}
//    }
    
    public void accept (APeer p) throws IOException{
    	jcs.accept(p);
    }

	@Override
	public void sendMessage(String message) {
		jcs.sendMessage(message);
		
	}

	@Override
	public void stopConnection() {
		jcs.stopConnection();
		
	}
	
	public List<Peer> getPeer (){
		return jcs.getPeer();
	}
    
    

}

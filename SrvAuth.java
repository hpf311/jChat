package jChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;



public class SrvAuth extends Thread implements JChatAuthenticator
{
	private SrvSocket jcs;

    public void SrvAuth(SrvSocket jcs) 
    {
        this.jcs=jcs;
        this.start();
    }
    
    /**
     * Auhtentifizieren neuer Clients
     */
    public void run(){
    	while(true){
    		try {
    			jcs.addClient(jcs.accept());//Jeder Client wird akzeptiert
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }

	@Override
	public void sendMessage(String message) {
		jcs.sendMessage(message);
		
	}

	@Override
	public void stopConnection() {
		jcs.stopConnection();
		
	}
	
	public List<BufferedReader> getReader (){
		return jcs.getReader();
	}
    
    

}

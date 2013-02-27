package jChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serverlogik
 * @author Thomas Traxler
 * @version 2013-02-22
 *
 */

public class SrvCom extends Thread implements JChatCom
{
	private JChatAuthenticator jca;
    private JChatGUI jcg;
    private SrvChatListener scl;
    private List<BufferedReader> lbr;//Clientinputs
    private List<Listener> ll;//Threads zu entprechenden Clientinputs

    public SrvCom(SrvAuth jca, JChatGUI jcg) 
    {
    	lbr = new ArrayList<BufferedReader>();
    	ll = new ArrayList<Listener>();
        this.jca=jca;
        this.jcg=jcg;
        this.lbr=jca.getReader();
        scl = new SrvChatListener();
        jcg.AddChatListener(scl);
        jcg.addMessage("Server gestartet");
        this.start();
    }
    
    /**
     * Ueberprueft ob alle Clients einen Thread haben, bzw. erstellt fuer jeden Client einen
     */
    public void run(){
    	while(true){
    		try {
    			this.sleep(1000);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		if(lbr.size()>ll.size()){
    			for (int i = ll.size();i < lbr.size();i++){
    				ll.add(new Listener(lbr.get(i)));
    				ll.get(i).start();
    			}
    		}
    	}
    }
    
    /**
     * Server ChatListener
     * Leitet ausgehende nachrichten an sendMessage weiter und fuegt sie der lokalen Anzeige hinzu
     * Leitet Beenden ein.
     * @author Thomas Traxler
     *
     */
    private class SrvChatListener implements ChatListener {

    	public void actionPerformed(ActionEvent e) {	
    		String message = jcg.equalsChatLine(e.getSource());
    		if ( !message.equals("")) {
    			sendMessage(jcg.getName()+":"+message);
    			jcg.addMessage(jcg.getName()+":"+message);
    		}
    		if ( jcg.equalsDisconnect(e.getSource()))
    			stopConnection();
    	}
    }
    /**
     * Listenerthread für einen Clientinput
     * @author Thomas Traxler
     *
     */
    private class Listener extends Thread {
    	private BufferedReader br;
    	
    	private Listener(BufferedReader br){
    		this.br = br;    	
    	}
    	
    	public void run(){
    		while(true){
    			try {
					this.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
    			try {
    				if(br.ready()){
    					String message =br.readLine() ;
    					if(message != ""){
    						jcg.addMessage(message);//fuegt eingehende Nachricht lokal hinzu
    						sendMessage(message);//leitet eingehende Nachricht an alle Clients weiter.
    					}
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
    		}
    	}
    }

	@Override
	public void sendMessage(String message) {
		jca.sendMessage(message+"\n");
		
	}


	@Override
	public void stopConnection() {
		jca.stopConnection();
		
	}
    
    
}

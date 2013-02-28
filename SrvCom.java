package jChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serverlogik
 * @author Thomas Traxler
 * @version 2013-02-22
 *
 */
//TODO: Kommando verwaltung nur Serverseitig Client-Objekte mit weiteren Daten ausstatten
public class SrvCom extends Thread implements JChatCom
{
	private JChatAuthenticator jca;
    private JChatGUI jcg;
    private SrvChatListener scl;
    private List<Peer> ll;//Threads zu entprechenden Clientinputs

    public SrvCom(SrvAuth jca, JChatGUI jcg) 
    {
    	ll = new ArrayList<Peer>();
        this.jca=jca;
        this.jcg=jcg;
        this.ll=jca.getPeer();
        scl = new SrvChatListener();
        jcg.AddChatListener(scl);
        jcg.addMessage("Server gestartet");
        this.start();
    }
    
    /**
     * Ueberprueft ob alle Clients einen Thread haben, bzw. erstellt fuer jeden Client einen
     */
    public void run(){
    	Peer p;
    	while(true){
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		p=new Peer("init");
    		try {
				jca.accept(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		ll.add(p);
    		p.start();
    		
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
    public class Peer extends Thread implements APeer{
    	private BufferedReader br;
    	private PrintWriter pw;
    	private String peerName;
    	private Socket s;
    	
    	public Peer(String name){
    		this.peerName = name;    	
    	}
    	
    	public void addSocket( Socket s) throws IOException{
    		this.s=s;
    		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		this.pw = new PrintWriter(s.getOutputStream(), true);
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
    						if(message.split(":",2)[1].charAt(0)!='/'){
    							jcg.addMessage(message);//fuegt eingehende Nachricht lokal hinzu
    							sendMessage(message);//leitet eingehende Nachricht an alle Clients weiter.
    						}else{
    							message = message+" \\";
    							String [] splitM = message.split(":",2)[1].split(" ",2);
    							if (splitM[0].equals("/error"))
    								System.out.println("Error: "+splitM[1]);
    						}
    					}
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
    		}
    	}

		public String getPeerName() {
			return peerName;
		}

		public void setPeerName(String name) {
			this.peerName = name;
		}

		public BufferedReader getBr() {
			return br;
		}

		public PrintWriter getPw() {
			return pw;
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

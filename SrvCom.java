package jChat;

import java.awt.Color;
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

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

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
    private List<Peer> ll;//Threads zu entprechenden Clientinputs
    private NameList nl = new NameList();

    public SrvCom(SrvAuth jca, JChatGUI jcg) 
    {

	
    	ll = new ArrayList<Peer>();
        this.jca=jca;
//        try {
//    			this.sleep(1000);
//    		} catch (InterruptedException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
        this.jcg=jcg;
        this.ll=jca.getPeer();
        int[] cc ={0,0,0};
        jcg.AddChatListener(new SrvChatListener());
        jcg.addMessage("Server gestartet",cc);
        this.start();
        jcg.open();
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
    public class SrvChatListener implements ChatListener {

//    	public void actionPerformed(ActionEvent e) {	
//    		String message = jcg.equalsChatLine(e.getSource());
//    		if ( !message.equals("")) {
//    			sendMessage("SERVER: "+message);
//    			jcg.addMessage("SERVER: "+message, null);
//    		}
//    		if ( jcg.equalsDisconnect(e.getSource()))
//    			stopConnection();
//    	}
    	

		@Override
		public void handleEvent(Event e) {
			System.out.println("A");
			String message = ""; 
			if (e.character=='\b'){
				
			}else{
				
				message = jcg.equalsChatLine(e.widget);
				if (!message.equals("")){
					sendMessage("SERVER: "+message+"\n");
	    			jcg.addMessage("SERVER: "+message, null);
				}
			}
			
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
    	private Color c = new Color (0,0,0);
    	
    	public Peer(String name){
    		this.peerName = name; 
    	}
    	
    	public void addSocket( Socket s) throws IOException{
    		this.s=s;
    		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    		this.pw = new PrintWriter(s.getOutputStream(), true);
    		
    		pw.print("/name "+peerName+"\n");
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
    						if(message.charAt(0)!='/'){
    							int[] a = {c.getRed(),c.getGreen(),c.getBlue()};
    							jcg.addMessage(peerName+":"+message, a );//fuegt eingehende Nachricht lokal hinzu
    							sendMessage(peerName+":"+message+"\n");//leitet eingehende Nachricht an alle Clients weiter.
    						}else{
    							message = message+" ";
    							String [] splitM = message.split(" ",2);
    							if (splitM[0].equals("/error"))
    								System.out.println("Error: "+splitM[1]);
    							else if (splitM[0].equals("/name")){
    								if(nl.changeName(peerName, splitM[1])){
    									peerName = splitM[1];
    									pw.print("/name "+splitM[1]+"\n");
    									pw.flush();
    									jcg.setNameList(nl);
    									sendMessage("/namelist ");
    									ArrayList n = nl.getNames();
    									sendMessage(""+n.get(0));
    									for (int i = 1;i<nl.getNames().size();i++){
    										sendMessage(";"+n.get(i));
    									}
    									sendMessage("\n");
    								}
    							}
    							else if(splitM[0].equals("/col")){
//    								c = InReader.getColor(splitM[1]);
    								pw.print(message+"\n");
    							}
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
		
		jca.sendMessage(message);
		
	}


	@Override
	public void stopConnection() {
		jca.stopConnection();
		
	}
    
    
}

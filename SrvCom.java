package jChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class SrvCom extends Thread implements JChatCom
{
	private JChatAuthenticator jca;
    private JChatGUI jcg;
    private List<BufferedReader> lbr;
    private List<Listener> ll;
    
	private List<BufferedReader> incomming = Collections.synchronizedList(new ArrayList());

    public void SrvCom(SrvAuth jca, JChatGUI jcg) 
    {
        this.jca=jca;
        this.jcg=jcg;
        this.incomming=jca.getReader();
        this.lbr=jca.getReader();
        this.start();
    }
    
    
    public void run(){
    	while(true){
    		try {
    			this.wait(1000);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		if(lbr.size()>ll.size()){
    			for (int i = ll.size()-1;i < lbr.size();i++){
    				ll.add(new Listener(lbr.get(i)));
    				ll.get(i).start();
    			}
    		}
    	}
    }

    private class SrvChatListener implements ChatListener {

    	public void actionPerformed(ActionEvent e) {	
    		String message = jcg.equalsChatLine(e.getSource());
    		if ( !message.equals("") ) {
    			jca.sendMessage(jcg.getName()+":"+message + "\n");
    			jcg.addMessage(jcg.getName()+":"+message);
    		}
    		if ( jcg.equalsDisconnect(e.getSource()))
    			jca.stopConnection();
    	}
    }
    
    private class Listener extends Thread {
    	private BufferedReader br;
    	
    	private Listener(BufferedReader br){
    		this.br = br;    	
    	}
    	
    	public void run(){
    		while(true){
    			try {
					this.wait(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
    			try {
    				if(br.ready())
    					jcg.addMessage(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
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

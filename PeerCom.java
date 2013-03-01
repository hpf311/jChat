package jChat;


import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;



public class PeerCom extends Thread implements JChatCom
{

	private PeerAuth jca;
	private JChatGUI jcg;
	private BufferedReader br;
	
	
    public PeerCom(PeerAuth jca, JChatGUI jcg) 
    {
        this.jca=jca;
        this.jcg=jcg;
        jcg.AddChatListener(new PeerChatListener());
        jca.connect();
        br = jca.getReader();
        this.start();
    }
    
    
    
    
    /**
     * Client ChatListener
     * Leitet ausgehende nachrichten an sendMessage weiter
     * Leitet Beenden ein.
     * @author Thomas Traxler
     *
     */
    private class PeerChatListener implements ChatListener {

    	public void actionPerformed(ActionEvent e) {	
    		String message = jcg.equalsChatLine(e.getSource());
    		if ( !message.equals("") ) {
    			sendMessage(message + "\n");
    		}
    		if ( jcg.equalsDisconnect(e.getSource()))
    			stopConnection();
    	}
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
						if(message.charAt(0)!='/')
							jcg.addMessage(message,null);//fuegt eingehende Nachricht lokal hinzu
						else {
							if(message.split(" ")[0].equals("/name")){
								jcg.setName(message.split(" ",2)[1]);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
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

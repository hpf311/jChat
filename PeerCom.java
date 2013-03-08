package jChat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;

import org.eclipse.swt.widgets.Event;

public class PeerCom extends Thread implements JChatCom {

	private PeerAuth jca;
	private JChatGUI jcg;
	private BufferedReader br;
	private NameList nl = new NameList();
	private Color c = new Color(0, 0, 0);

	public PeerCom(PeerAuth jca, JChatGUI jcg) {
		this.jca = jca;
		this.jcg = jcg;
		jcg.AddChatListener(new PeerChatListener());
		jca.connect();
		br = jca.getReader();
		this.start();

		jcg.open();

	}

	/**
	 * Client ChatListener Leitet ausgehende nachrichten an sendMessage weiter
	 * Leitet Beenden ein.
	 * 
	 * @author Thomas Traxler
	 * 
	 */
	private class PeerChatListener implements ChatListener {

		// public void actionPerformed(ActionEvent e) {
		// String message = jcg.equalsChatLine(e.getSource());
		// if ( !message.equals("") ) {
		// sendMessage(message + "\n");
		// }
		// if ( jcg.equalsDisconnect(e.getSource()))
		// stopConnection();
		// }

		@Override
		public void handleEvent(Event e) {
			if (e.character == '\b') {

			} else {
				String message = "";
				message = jcg.equalsChatLine(e.widget);
				if (!message.equals("")) {
					sendMessage(message + "\n");
				}
			}

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
						if(message.charAt(0)!='/'){
							int[] a = {c.getRed(),c.getGreen(),c.getBlue()};
							jcg.addMessage(message,a);//fuegt eingehende Nachricht lokal hinzu
						}
						else {
							if(message.split(" ")[0].equals("/name")){
								jcg.setAName(message.split(" ",2)[1]);
							}
							if(message.split(" ")[0].equals("/namelist")){
								System.out.println(message);
								String[] h = message.split(" ",2)[1].split(";");
								nl= new NameList();
								for (int i = 0;i<h.length;i++)
									nl.changeName("Server", h[i]);
								jcg.setNameList(nl);
							}else if(message.split(" ")[0].equals("/col")){
//								c = InReader.getColor(splitM[1]);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			jcg.addWriter(this);
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

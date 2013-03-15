package jChat;

import java.io.IOException;


/**
 * uthentifizierungsschicht, noch funtkionsfrei.
 * @author Thomas Traxler
 *
 */
public class P2pAuth implements JChatAuthenticator
{
	P2pSocket p2ps;
	
	public P2pAuth(P2pSocket p2ps){
		this.p2ps = p2ps;
	}

	@Override
	public void sendMessage(String message) throws IOException {
		p2ps.sendMessage(message);
	}

	@Override
	public void stopConnection() {
		try {
			p2ps.stopConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@Override
	public boolean ready(StringBuilder msg) throws IOException{
		return p2ps.ready(msg);
	}


}

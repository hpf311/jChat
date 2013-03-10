package jChat;

import java.io.IOException;



public class P2pAuth implements JChatAuthenticator
{
	P2pSocket p2ps;
	
	public P2pAuth(P2pSocket p2ps){
		this.p2ps = p2ps;
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(APeer p) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public boolean ready(String msg) throws IOException{
		return p2ps.ready(msg);
	}


}

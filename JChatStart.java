package jChat;

import java.net.UnknownHostException;


public class JChatStart
{


    public static void main(String[] args) throws UnknownHostException {
    	new P2pCom(new P2pAuth(new P2pSocket()), new BetterGUI());
       
    }

}

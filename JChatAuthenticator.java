package jChat;

import java.io.IOException;


/**
 * Interface fuer JChatAuthtenticator, legt fest ob eine Verbindung erlaubt ist. 
 * @author Paradox
 *
 */

public interface JChatAuthenticator
{

    
    public void sendMessage (String message) throws IOException;
    public void stopConnection();
	boolean ready(StringBuilder msg) throws IOException;


}
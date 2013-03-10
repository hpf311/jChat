package jChat;

import java.io.IOException;


/**
 * Interface fuer JChatAuthtenticator, legt fest ob eine Verbindung erlaubt ist. 
 * @author Paradox
 *
 */

public interface JChatAuthenticator
{

    
    public void sendMessage (String message);
    public void stopConnection();
    public void accept( APeer p) throws IOException;
    public boolean ready(String msg) throws IOException;


}
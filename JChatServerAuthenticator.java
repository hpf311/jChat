package jChat;

import java.io.BufferedReader;
import java.util.List;

/**
 * Interface fuer JChatServerAuthtenticator, legt fest ob eine Verbindung mit diesem Server erlaubt ist. 
 * @author Paradox
 *
 */
public interface JChatServerAuthenticator extends JChatAuthenticator{
	public List<BufferedReader> getReader ();

}

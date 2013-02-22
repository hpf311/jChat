package jChat;

import javax.swing.JButton;
import javax.swing.JTextField;



public interface JChatGUI
{

	  public void addMessage( String inMessage );
	  public boolean equalsDisconnect (Object button);
	  public String equalsChatLine(Object source);
	  public String getName();
}
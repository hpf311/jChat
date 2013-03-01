package jChat;

import javax.swing.JButton;
import javax.swing.JTextField;



public interface JChatGUI
{

	  public void addMessage( String inMessage , int[] args);
	  public boolean equalsDisconnect (Object button);
	  public String equalsChatLine(Object source);
	  public void AddChatListener(ChatListener cl);
	  public void setName (String name);
}
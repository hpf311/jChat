package jChat;



import javax.swing.JButton;
import javax.swing.JTextField;



public interface JChatGUI
{

	  public void addMessage( String inMessage , int[] args);
	  public boolean equalsDisconnect (Object button);
	  public String equalsChatLine(Object source);
	  public void AddChatListener(ChatListener scl);
	  public void setAName (String name);
	  public void setNameList (NameList nl);
	  public void open();
	  public void addWriter (Runnable r);
}
package jChat;



import java.util.ArrayList;




public interface JChatGUI
{

	  public void addMessage( String inMessage , int[] args);
	  public boolean equalsDisconnect (Object button);
	  public String equalsChatLine(Object source);
	  public void AddChatListener(ChatListener scl);
	  public void setAName (String name);
	  public void setNameList (ArrayList<String> nl);
}
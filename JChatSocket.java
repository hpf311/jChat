package jChat;

import java.io.IOException;



public interface JChatSocket
{
	public void startConnection() throws IOException;
	public void stopConnection() throws IOException;
	public void sendMessage (String message) throws IOException;

}
package jChat;

import java.io.IOException;
import java.net.Socket;



public interface JChatSocket
{
	public void stopConnection();
	public void sendMessage (String message);

}
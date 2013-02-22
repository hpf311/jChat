package jChat;



public interface JChatAuthenticator
{

    public JChatSocket jcs = null;
    
    public void sendMessage (String message);
    public void stopConnection();


}
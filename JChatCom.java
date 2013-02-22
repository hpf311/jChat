package jChat;



public interface JChatCom
{

    public JChatAuthenticator jca = null;

    public JChatGUI jcg = null;
    public void sendMessage (String message);
    public void stopConnection();

}
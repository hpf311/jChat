package jChat;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class JChatStart
{
    public static void createAndShowGUI(SimpleGUI sg) {
    JFrame frame = new JFrame("jChat");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JComponent newContentPane = sg;
    newContentPane.setOpaque(true);
    frame.setContentPane(newContentPane);

    frame.pack();
    frame.setVisible(true);
    }


    public static void main(String[] args) throws UnknownHostException {
    	
       
    }

}

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
    	if(InReader.chooser()!=true){
    	     BetterGUI sg2 = new BetterGUI();
    	     System.out.println("Test");
    	     SrvCom sc = new SrvCom(new SrvAuth(new SrvSocket()), sg2);
    	}else{
    		
    		InetSocketAddress isa = new InetSocketAddress(InReader.ipReader(), 1492);
    		PeerSocket ps = new PeerSocket(isa);
    		PeerAuth pa = new PeerAuth(ps);
    		BetterGUI sg1 = new BetterGUI();
    		PeerCom pc = new PeerCom(pa, sg1);
    	}
       
    }

}

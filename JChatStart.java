package jChat;

import java.net.InetSocketAddress;

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


    public static void main(String[] args) {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 1492);
        PeerSocket ps = new PeerSocket(isa);
        PeerAuth pa = new PeerAuth(ps);
        SimpleGUI sg1 = new SimpleGUI();
        PeerCom pc = new PeerCom(pa, sg1);

        SrvSocket ss = new SrvSocket();
        SrvAuth sa = new SrvAuth(ss);
        SimpleGUI sg2 = new SimpleGUI();
        SrvCom sc = new SrvCom(sa, sg2);
        createAndShowGUI(sg2);
        createAndShowGUI(sg1);
    }

}

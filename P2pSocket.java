package jChat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class P2pSocket implements JChatSocket {
	String MULTICASTADDRESS = "228.5.6.7";
	int MULTICASTPORT = 6789;
	
	InetAddress group;
	MulticastSocket s;
	
	
	@Override
	public void startConnection() throws IOException {
		group = InetAddress.getByName(MULTICASTADDRESS);
		s = new MulticastSocket(MULTICASTPORT);
		s.joinGroup(group);
	}

	
	@Override
	public void stopConnection() throws IOException {
		s.leaveGroup(group);

	}

	
	@Override
	public void sendMessage(String msg) throws IOException {
		DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
				group, MULTICASTPORT);
		s.send(hi);
	}

}

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
	
	public P2pSocket(){
		try {
			startConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Starts connection
	 */
	@Override
	public void startConnection() throws IOException {
		group = InetAddress.getByName(MULTICASTADDRESS);
		s = new MulticastSocket(MULTICASTPORT);
		s.joinGroup(group);
	}

	/**
	 * Stops Connection
	 */
	@Override
	public void stopConnection() throws IOException {
		s.leaveGroup(group);

	}

	/**
	 * Sends message
	 * @param msg Messge to send
	 */
	@Override
	public void sendMessage(String msg) throws IOException {
		DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
				group, MULTICASTPORT);
		s.send(hi);
	}

	/**
	 * Waits until it receives a message, writes the message to a String and
	 * returns true
	 * @param msg String to write the message to
	 */
	@Override
	public boolean ready(StringBuilder msg) throws IOException {
		byte[] buf = new byte[1000];
		DatagramPacket recv = new DatagramPacket(buf, buf.length);
		s.receive(recv);
		msg.append( new String(buf, 0, recv.getLength()));
		if (msg.length() > 0)
			return true;
		else
			return false;
	}

}

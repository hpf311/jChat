package jChat;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class InReader {
	
	
	public static boolean chooser (){
		  String[] a = {"Server", "Client"};
		  
		  if(JOptionPane.showOptionDialog(null, "Test", "Titel", 1, 1, null, a , "Server") == 1)
			  return true;
		  return false;
		  
	  }
	
	public static String ipReader () throws UnknownHostException{
		InetAddressValidator ipVal = new InetAddressValidator();
		String ip = JOptionPane.showInputDialog("IP");
		if(ipVal.isValidInet4Address(ip) == true){
			return ip;
		} else {
			System.out.println("Ungueltige IP eingegeben");
			ipReader();
		}
		return "";
		
	}
	
	

}

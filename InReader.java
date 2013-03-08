package jChat;

import java.awt.Color;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class InReader {
	
	
	public static boolean chooser (){
		  String[] a = {"Server", "Client"};
		  
		  if(JOptionPane.showOptionDialog(null, "Test", "Titel", 1, 1, null, a , "Server") == 1)
			  return true;
		  return false;
		  
	  }
	/**
	 * Reads IP Adress and validates it
	 * @return valid IP Address
	 * @throws UnknownHostException
	 */
	public static String ipReader () throws UnknownHostException{
		InetAddressValidator ipVal = new InetAddressValidator();
		String ip = JOptionPane.showInputDialog("IP");
		if(ipVal.isValidInet4Address(ip) || ip == "localhost"){
			return ip;
		} 
		JOptionPane.showMessageDialog(null, "Ungueltige IP eingegeben");		
		return ipReader();
	}
	
	/**
	 * Converts hexadecimal String to Color Object
	 * @param colossalColorString Color in Hex value (eg. #AB12FF)
	 * @return
	 */
	public static Color getColor(String colossalColorString){
		return Color.decode(colossalColorString);
	}
	
	

}

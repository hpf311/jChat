package jChat;

import java.awt.Color;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class InReader {

	public static boolean chooser() {
		String[] a = { "Server", "Client" };

		if (JOptionPane.showOptionDialog(null, "Test", "Titel", 1, 1, null, a,
				"Server") == 1)
			return true;
		return false;

	}

	/**
	 * Reads IP Adress and validates it
	 * 
	 * @return valid IP Address
	 * @throws UnknownHostException
	 */
	public static String ipReader() throws UnknownHostException {
		InetAddressValidator ipVal = new InetAddressValidator();
		String ip = JOptionPane.showInputDialog("IP");
		if (ipVal.isValidInet4Address(ip) || ip.equals("localhost")) {
			return ip;
		}
		JOptionPane.showMessageDialog(null, "Ungueltige IP eingegeben");
		return ipReader();
	}

	/**
	 * Converts hexadecimal String to Color Object
	 * 
	 * @param colossalColorString
	 *            Color in Hex value (eg. #AB12FF)
	 * @return Color Object
	 */
	public static Color getColor(String colossalColorString) {
		return Color.decode(colossalColorString);
	}

	/**
	 * Reads in a password
	 * 
	 * @return the password
	 */
	public static String getPass() {
		JPasswordField pw = new JPasswordField(10);
		int action = JOptionPane.showConfirmDialog(null, pw, "Enter Password",
				JOptionPane.OK_CANCEL_OPTION);
		if (action < 0) {
			JOptionPane.showMessageDialog(null,
					"Cancel, X or escape key selected");
			return getPass();
		} else {
			return new String(pw.getPassword());
		}

	}
}

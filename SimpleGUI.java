package jChat;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.lang.*;
import java.util.*;
/**
 * GUI, aus Beispiel angepasst 
 * @author Thomas Traxler
 * @version 2013-02-22
 */
public class SimpleGUI extends JPanel  implements JChatGUI{
 
  public JTextArea chatText = null;
  public JTextField chatLine = null;
  public JTextArea myName = null;
  public JTextField statusColor = null;
  public JButton disconnectButton = null;

  private ChatListener chatListener = null;
  private String name;

  public SimpleGUI(){
    this(("Client_"+(int)(Math.random()*9999+1)));
  }
  
  public SimpleGUI(String name){
	  super(new BorderLayout());
	  this.name = name;
  }

  public void AddChatListener(ChatListener cl) {
    chatListener = cl;

    JPanel optionsPane = new JPanel(new GridLayout(3, 1));

    JPanel pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    pane.add(new JLabel("My Name:"));
    myName = new JTextArea(1,24); 
    myName.setText(name);
    pane.add( myName );
    optionsPane.add(pane);

    
    JPanel buttonPane = new JPanel(new GridLayout(1, 2));
    disconnectButton = new JButton("Disconnect");
    disconnectButton.setMnemonic(KeyEvent.VK_D);
    disconnectButton.setActionCommand("disconnect");
    disconnectButton.addActionListener( chatListener );
    disconnectButton.setEnabled(true);
    buttonPane.add(disconnectButton);
    optionsPane.add(buttonPane);

    JPanel chatPane = new JPanel(new BorderLayout());
    chatText = new JTextArea(10, 20);
    chatText.setLineWrap(true);
    chatText.setEditable(false);
    chatText.setForeground(Color.blue);
    JScrollPane chatTextPane = new JScrollPane(chatText,
  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    chatLine = new JTextField();
    chatLine.setEnabled(true);
    chatLine.addActionListener( chatListener );

    chatPane.add(chatLine, BorderLayout.SOUTH);
    chatPane.add(chatTextPane, BorderLayout.CENTER);
    chatPane.setPreferredSize(new Dimension(200, 250));

    this.add(optionsPane, BorderLayout.SOUTH);
    this.add(chatPane, BorderLayout.NORTH);
  }

  // public void createAndShowGUI() {
  //   comChat = inComChat;

  //   JFrame frame = new JFrame("TCPChat");
  //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  //   JComponent newContentPane = new TCPChatGUI();
  //   newContentPane.setOpaque(true);
  //   frame.setContentPane(newContentPane);

  //   frame.pack();
  //   frame.setVisible(true);
  // }
/**
 * Zeigt neue Message an.
 */
  public void addMessage( String inMessage, int[] args) {
    chatText.append( inMessage + "\n" );
  }
  
  public String equalsChatLine (Object chatline){
	  if(this.chatLine == chatline){
		  String c = chatLine.getText(); 
		  chatLine.setText("");
		  return c;
	  }
	  return "";
  }
  
  public boolean equalsDisconnect (Object button){
	  if(this.disconnectButton == button)
		  return true;
	  return false;
  }

  public String getName(){
	  return name;
  }
  
  public void setName(String name){
	  this.name=name;
	  this.myName.setText(name);
  }
  
  

  
}

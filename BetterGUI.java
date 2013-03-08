package jChat;

import java.util.ArrayList;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.*;

import swing2swt.layout.BorderLayout;

//TODO SWT SWT SWT SWT SWT SWT SWT SWT SWT SWT SWT SWT

public class BetterGUI extends Thread implements  JChatGUI {

	private Shell s;
	private ScrolledComposite scrolledComposite;
	private Table nametable;
	private Composite composite;
	private Composite composite_1;
	private ScrolledComposite scrolledComposite_1;
	private Text text;
	private Button btnNewButton;
	private Table chatText;
	private Integer tablecount, namecount;
	private Display d;
	private boolean isNewText=false, isNewName= false;
	private String newText;
	private int[] args ={0,0,0};
	private ArrayList<String> namelist = new ArrayList<String>();
	private Label lblInit;

	public BetterGUI() {
		d = new Display();
		s = new Shell(d);
		s.setSize(450, 320);
		s.setLayout(new BorderLayout(0, 0));

		Menu menu = new Menu(s, SWT.BAR);
		s.setMenuBar(menu);

		MenuItem mntmDatei = new MenuItem(menu, SWT.NONE);
		mntmDatei.setText("Datei");

		MenuItem mntmSettings = new MenuItem(menu, SWT.CASCADE);
		mntmSettings.setText("Settings");

		Menu menu_1 = new Menu(mntmSettings);
		mntmSettings.setMenu(menu_1);

		MenuItem mntmSetName = new MenuItem(menu_1, SWT.NONE);
		mntmSetName.setText("set Name");

		scrolledComposite = new ScrolledComposite(s, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		scrolledComposite.setLayoutData(BorderLayout.EAST);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		nametable = new Table(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(nametable);
		scrolledComposite.setMinSize(nametable
				.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		composite_1 = new Composite(s, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new BorderLayout(0, 0));

		text = new Text(composite_1, SWT.BORDER);

		btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.setLayoutData(BorderLayout.EAST);
		btnNewButton.setText("Send");
		
		lblInit = new Label(composite_1, SWT.NONE);
		lblInit.setLayoutData(BorderLayout.WEST);
		lblInit.setText("init");

		scrolledComposite_1 = new ScrolledComposite(s, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		scrolledComposite_1.setLayoutData(BorderLayout.CENTER);

		chatText = new Table(scrolledComposite_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		scrolledComposite_1.setContent(chatText);
		scrolledComposite_1.setMinSize(chatText.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		tablecount = 0;
//		while (tablecount < 100) {
//
//			chatText.setItemCount(tablecount+1);
//			chatText.getItems()[tablecount].setForeground(new Color(d, tablecount*2,100,0));
//			chatText.getItems()[tablecount].setText("a" + tablecount);
//			tablecount++;
//			
//		}
		s.open();
//		notifyAll();
	}

	
	public void run (){
		while(true){
			
				
			
		}
		
	}
	@Override
	public void addMessage(String inMessage, int[] args) {
		// TODO set Color
		//chatText.getItems()[tablecount].setForeground(new Color(d, tablecount*20,100,0));
		newText=inMessage;
		this.args=args;
		isNewText=true;

	}

	public void addWriter (Runnable r){
//		this.start();
//		d.syncExec(this);
	}
	@Override
	public boolean equalsDisconnect(Object button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String equalsChatLine(Object source) {
		String s = "";
		if (text.equals(source) || btnNewButton.equals(source)) {//TODO remove true
			s = text.getText();
			text.setText("");
		}
		return s;
	}

	@Override
	public void AddChatListener(ChatListener cl) {
		
//		text.addListener(SWT.MouseDown,cl);
		btnNewButton.addListener(SWT.MouseDown, cl);
		
		
	}

	@Override
	public void setAName(String name) {
		// TODO Tu was

	}
	public void open (){
		while (!s.isDisposed()) {
			if (!d.readAndDispatch()) {
				d.sleep();
			}
			if(isNewText){
				chatText.setItemCount(tablecount+1);
				
//				chatText.getItems()[tablecount].setForeground(new Color(d, args[0], args[1], args[2]));
				chatText.getItems()[tablecount].setText(newText);
				chatText.showItem(chatText.getItems()[tablecount]);
				tablecount++;
				isNewText=false;
			}
			if(isNewName){
				nametable.setItemCount(0);
				for (namecount = 0;namecount < namelist.size();namecount++){
					nametable.setItemCount(namecount+1);
					nametable.getItems()[namecount].setText(namelist.get(namecount));
				}
				isNewName=false;
			}
		}
	}


	@Override
	public void setNameList(NameList nl) {
		namelist = nl.getNames();
		isNewName = true;

	}
}

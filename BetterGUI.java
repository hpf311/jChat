package jChat;

import java.util.ArrayList;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.*;

import swing2swt.layout.BorderLayout;

/**
 * SWT-GUI fuer jChat
 * @author Thomas Traxler
 *
 */

public class BetterGUI extends Thread implements  JChatGUI {

	private Shell s;
	private ScrolledComposite scrolledComposite;
	private Table nametable;
	private Composite composite_1;
	private ScrolledComposite scrolledComposite_1;
	private Text text;
	private Button btnNewButton;
	private Table chatText;
	private Integer tablecount, namecount, maxMessageBuffer;
	private Display d;
	private boolean isNewText=false, isNewName= false;
	private String[] newText;//Buffer fuer neue texte
	private int[][] args;//Buffer fuer zu text gehoerende Argumente (Farbe)
	private ArrayList<String> namelist = new ArrayList<String>();
	private Label lblInit;
	private String peerName;

	public BetterGUI() {
		maxMessageBuffer=25;
		newText = new String[maxMessageBuffer];
		args = new int[maxMessageBuffer][3];//Umstellen bei aenderung der Arguemntsanzahl!
		for(int i = 0;i<3;i++){
			for (int ii=0;ii<maxMessageBuffer;ii++){
				newText[ii]="";
				args[ii][i]=0;
			}
		}
		
		d = new Display();
		s = new Shell(d);
		s.setSize(450, 320);
		s.setLayout(new BorderLayout(0, 0));

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
		s.open();
	}

	
	
	@Override
	public void addMessage(String inMessage, int[] args) {
		int i =0;
		for(;i<maxMessageBuffer&&!newText[i].equals("");i++);
		if(i==maxMessageBuffer){
			System.exit(99);//Bufferoverflow beendet Programm zum Schutz vor floating
			//Hoffentlich passiert das nie....
		}
		//Nachricht im Buffer hinzufuegen
		newText[i]=inMessage;
		this.args[i]=args;
		isNewText=true;

	}

	/**
	 * Disconnect Button nicht implementiert
	 */
	@Override
	public boolean equalsDisconnect(Object button) {
		return false;
	}

	/**
	 * Liefert Text im Eingabefeld zurueck wenn source dem TExtfeld oder Button entspricht.
	 */
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
		
		text.addListener(SWT.KeyDown,cl);
		btnNewButton.addListener(SWT.MouseDown, cl);
		
		
		
	}

	/**
	 * Setzt den lokal angezeigten namen
	 */
	@Override
	public void setAName(String name) {
		this.peerName=name;
		this.isNewName=true;

	}
	/**
	 * Setzt die angezeigte Namensliste
	 */
	@Override
	public void setNameList(ArrayList<String> names){
		this.namelist=names;
		this.isNewName=true;
	}
	
	
	public void open (){
		while (!s.isDisposed()) {
			if (!d.readAndDispatch()) {
				d.sleep();
			}
			
			if(isNewText){
				//Buffer leeren und anzeigen
				for (int i =0;newText[i]!="";i++){

					chatText.setItemCount(tablecount+1);
					chatText.getItems()[tablecount].setForeground(new Color(d, args[i][0], args[i][1], args[i][2]));
					chatText.getItems()[tablecount].setText(newText[i]);
					chatText.showItem(chatText.getItems()[tablecount]);
					tablecount++;
					newText[i]="";
					
				}
				
				isNewText=false;
			}
			if(isNewName){
				nametable.setItemCount(0);
				lblInit.setText(peerName);
				for (namecount = 0;namecount < namelist.size();namecount++){
					nametable.setItemCount(namecount+1);
					nametable.getItems()[namecount].setText(namelist.get(namecount));
				}
				isNewName=false;
			}
		}
		
	}
	


	public boolean isDisposed() {
		return s.isDisposed();
	}
}

package jChat;

import java.util.ArrayList;
import java.util.List;

public class NameList {
	
	private ArrayList<String> names = new ArrayList<String>();
	
	public boolean changeName (String oldName, String name){
		
		if(!names.contains(name)){
			if(!names.contains(oldName))
				names.add(name);
			else {
				names.remove(oldName);
				names.add(name);
			}
			return true;
		}
		return false;
	}

	public ArrayList<String> getNames() {
		return names;
	}
	
	

}

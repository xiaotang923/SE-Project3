package priori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ItemSet {
	private HashMap<String,Integer> itemMap;
	private ArrayList<String> itemList;
	
	public ItemSet(String ItemsFile) {
		itemMap = new HashMap<String, Integer>();
		itemList = new ArrayList<String>();
		try {
			Scanner items = new Scanner(new File(ItemsFile));
			Pattern tokenPattern = Pattern.compile(",");
			items.useDelimiter(tokenPattern);
			
			int index = 0;
			while(items.hasNext()) {
				String key = items.next().toUpperCase();
				itemMap.put(key, index);
				itemList.add(key);
				index++;
			}
			items.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int getKey(String skey) {
		return itemMap.get(skey);
	}
	
	public String getName(int key) {
		return itemList.get(key);
	}
	
	public boolean checkExistence(String testee) {
		// use testee as the hash key, and then check if the exist in the itemMap
		return itemMap.containsKey(testee.toUpperCase());
	}
	
	public static void main(String [] args) {
		new ItemSet("./Items.txt");
	}
}
package apriori;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemSet {
	private HashMap<String,Integer> itemMap;
	private ArrayList<String> itemList;
	
	public ItemSet(ArrayList<String[]> itemsArray) {
		itemMap = new HashMap<String, Integer>();
		itemList = new ArrayList<String>();
		/*
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
		*/
		
		int index = 0;
		for(String[] trans : itemsArray) {
			for(String item : trans) {
				String key = item.toUpperCase();
				if(itemMap.get(key) == null) {
					itemMap.put(key, index);
					itemList.add(key);
					index++;
				}
			}
		}
	}
	
	public int getKey(String skey) {
		return itemMap.get(skey.toUpperCase());
	}
	
	public String getName(Integer key) {
		return itemList.get(key);
	}
	
	public int getSize() {
		return itemList.size();
	}
	
	public boolean checkExistence(String testee) {
		// use testee as the hash key, and then check if the exist in the itemMap
		return itemMap.containsKey(testee.toUpperCase());
	}
	
	public void printMap() {
		System.out.println(itemMap);
	}
	
}

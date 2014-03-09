package priori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Generator {
	private ArrayList<String[]> transactionData;
	private ArrayList<HashMap<String, Integer>> combinations;
	private ItemSet itemSet;
	private int level;

	public Generator(String transactionFile, String itemsFile) {
		TransactionSet transactions = new TransactionSet(transactionFile);
		this.transactionData = transactions.getItemsArray();
		this.itemSet = new ItemSet(itemsFile);
		this.combinations = new ArrayList<HashMap<String, Integer>>();
		this.level = 0;
		this.transactionValidation();
	}

	private void transactionValidation() {
		// Check for the existence of items in transactions
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i = 0; i < transactionData.size(); i++) {
			for (String item : transactionData.get(i)) {
				if (!itemSet.checkExistence(item)) {
					index.add(i);
				}
			}
		}
		// Drop transactions corresponding to the index #s recorded in index array
		for (Integer i : index) {
			transactionData.remove(i);
		}
	}

	public RuleSet generate(double minConfidenceLev, double minSupportLev) {
		int minSupportCount = (int) Math.round(transactionData.size() * minSupportLev);
		ArrayList<String> combo1copy = new ArrayList<String>();
		// Generating 1-itemSet Frequency table
		{	
			// constructing a hashMap that contains each item and count number
			combinations.add(new HashMap<String, Integer>());
			for (String[] trans : transactionData) {
				for (int i = 1; i < trans.length; i++) {
					Integer count = combinations.get(level).get(trans[i]);
					if (count != null) {
						combinations.get(level).put(trans[i], count + 1);
					} else {
						combinations.get(level).put(trans[i], 1);
					}
				}
			}

			// Delete items that didn't meet the minimum support count
			Iterator<Entry<String, Integer>> it = combinations.get(level).entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> item = it.next();
				if (item.getValue() < minSupportCount) {
					it.remove();
					combo1copy.remove(item.getKey());
				}
				combo1copy.add(item.getKey());
			}
			
			// Increment the level
			level++;
		}


		combinations.add(new HashMap<String,Integer>());
		Iterator<Entry<String, Integer>> it = combinations.get(level-1).entrySet().iterator();
		int n=0;
		while(it.hasNext()) {
			Entry<String, Integer> item = it.next();
			for(int i=0; i<combo1copy.size(); i++) {
				if(!combo1copy.get(i).equals(item.getKey())) {
					boolean add = true;
					String key = item.getKey() + "," + combo1copy.get(i);
					Iterator<Entry<String, Integer>> it2 = combinations.get(level).entrySet().iterator();
					while(it2.hasNext()) {
						Entry<String, Integer> entry = it2.next();
						String[] itemList = entry.getKey().split(",");
					}
					if(add) {
						combinations.get(level).put(key, 1);
					}
				}
			}
		}
		
		
		System.out.println(combinations.get(0));
		return null;
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.start();

		Generator generator = new Generator("./Transactions.txt", "./Items.txt");
		RuleSet rules = generator.generate(0.5, 0.5);

		timer.stop();
	}
}

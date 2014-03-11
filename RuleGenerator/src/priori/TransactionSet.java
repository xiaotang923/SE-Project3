package priori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TransactionSet {
	
	private ArrayList<String[]> itemsArray;
	
	public TransactionSet(String transactionFile) {
			itemsArray = new ArrayList<String[]>();
			Pattern splitPattern = Pattern.compile("([!,\\{]+)\\s*");
			try {
				Scanner transactions = new Scanner(new File(transactionFile));
				
				//Skip the meta info
				transactions.useDelimiter("\\{");
				if(transactions.hasNext()) {
					transactions.next();
				}
				
				// read in each transactions and store as a string array
				transactions.useDelimiter("\\}");
				while(transactions.hasNext()) {
					itemsArray.add(splitPattern.split(transactions.next()));
				}
				transactions.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		new TransactionSet("./Transactions.txt");
	}
	
	public ArrayList<String[]> getItemsArray() {
		return itemsArray;
	}
	
	public void remove(int index) {
		itemsArray.remove(index);
	}
}

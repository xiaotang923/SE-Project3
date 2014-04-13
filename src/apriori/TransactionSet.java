package apriori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TransactionSet {
	
	private ArrayList<String[]> itemsArray;
	
	public TransactionSet(String transactionFile) {
			itemsArray = new ArrayList<String[]>();
			Pattern splitPattern = Pattern.compile("([!,\\{]+)");
			try {
				Scanner transactions = new Scanner(new File(transactionFile));
				
				//Skip the meta info
				for(int i=0; i<3; i++) {
					if(transactions.hasNextLine()) {
						transactions.nextLine();
					}
				}
				
				// read in each transactions and store as a string array
				while(transactions.hasNextLine()) {
					String[] temp = splitPattern.split(transactions.nextLine().replaceAll("[\\{\\}\\s]", ""));
					if(!temp[0].equals("")) {
						itemsArray.add(temp);
					}
				}
				transactions.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	public ArrayList<String[]> getItemsArray() {
		return itemsArray;
	}
	
	public void remove(int index) {
		itemsArray.remove(index);
	}
}

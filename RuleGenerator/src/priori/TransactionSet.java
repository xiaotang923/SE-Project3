package priori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TransactionSet {
	
	private ArrayList<String[]> itemsArray;
	
	public TransactionSet(String transactionFile, String itemsFile) {
			ArrayList<String> transactionArray = new ArrayList<String>();
			itemsArray = new ArrayList<String[]>();
			Pattern splitPattern = Pattern.compile("\\s*([!,{}]+)\\s*");
			try {
				Scanner transactions = new Scanner(new File(transactionFile));
				while(transactions.hasNextLine()) {
					transactionArray.add(transactions.nextLine());
				}
				transactions.close();
				
				for(String x : transactionArray) {
					itemsArray.add(splitPattern.split(x));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String [] args) {
		new TransactionSet("./Transactions.txt", "./Items.txt");
	}
	
	public ArrayList<String[]> getItemsArray() {
		return itemsArray;
	}
}

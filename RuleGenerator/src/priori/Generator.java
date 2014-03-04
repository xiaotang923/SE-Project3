package priori;

import java.util.ArrayList;

public class Generator {
	private ArrayList<String[]> transactionData;
	
	public Generator(String transactionFile, String itemsFile) {
		TransactionSet transactions = new TransactionSet(transactionFile, itemsFile);
		this.transactionData = transactions.getItemsArray();
	}
	
	public RuleSet generate() {
		// all the apriori algorithm code goes here
		return null;
	}
	
	public void main(String[][] argv) {
		Generator generator = new Generator("./Transactions.txt", "./Items.txt");
		generator.generate();
	}
}

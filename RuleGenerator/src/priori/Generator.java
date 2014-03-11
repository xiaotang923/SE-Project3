package priori;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Generator {
	private TransactionSet transactions;
	private ArrayList<ArrayList<Integer>> transactionData;
	private ArrayList<ArrayList<ArrayList<Integer>>> combinations;
	private ArrayList<ArrayList<Integer>> counts;
	private ItemSet itemSet;
	private int level;

	public Generator(String transactionFile, String itemsFile) {
		this.initialize();
		this.itemSet = new ItemSet(itemsFile);
		this.transactions = new TransactionSet(transactionFile);
//	 	this.transactionValidation(transactions.getItemsArray());
		this.constructTransactionMap(transactions.getItemsArray());
		
	}
	
	public void initialize() {
		this.counts = new ArrayList<ArrayList<Integer>>();
		this.combinations = new ArrayList<ArrayList<ArrayList<Integer>>>();
		this.level = 0;
	}
	
	private void constructTransactionMap(ArrayList<String[]> transactionString) {
		this.transactionData = new ArrayList<ArrayList<Integer>>();
		for (String[] trans : transactionString) {
			this.transactionData.add(new ArrayList<Integer>());
			for (int i=1; i < trans.length; i++) {
				this.transactionData.get(this.transactionData.size() - 1).add(itemSet.getKey(trans[i]));
			}
		}
	}

	private void transactionValidation(ArrayList<String[]> transactionString) {
		// Check for the existence of items in transactions
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (String[] trans : transactionString) {
			for (int i=1; i<trans.length; i++) {
				if (!itemSet.checkExistence(trans[i])) {
					index.add(i);
				}
			}
		}
		// Drop transactions corresponding to the index #s recorded in index array
		for (Integer i : index) {
			transactions.remove(i);
		}
	}

	public ArrayList<Rule> generate(double minSupportLev, double minConfidenceLev) {
		// Check boundaries
		if(minSupportLev > 1.0 && minSupportLev < 0.0) {
			System.out.println("Please enter a valid 'Minimum Support Level' (0.00~1.00)!");
			return null;
		}
		if(minConfidenceLev > 1.0  && minConfidenceLev < 0.0) {
			System.out.println("Please enter a valid 'Minimum Confidence Level' (0.00~1.00)!");
			return null;
		}
		
		int minSupportCount = (int) Math.round(transactionData.size() * minSupportLev);
		ArrayList<Integer> toBeDel = new ArrayList<Integer>();
		
		// Generating 1-itemSet Frequency table
		{	
			// Constructing the first level of combination array, and store the update counts array
			combinations.add(new ArrayList<ArrayList<Integer>>());
			combinations.get(level).add(new ArrayList<Integer>());
			counts.add(new ArrayList<Integer>());
			
			for (ArrayList<Integer> trans : transactionData) {
				for (int i=0; i < trans.size(); i++) {
					Integer key = trans.get(i);
					boolean done = false;
					for(int j=0; j<combinations.get(level).size() && !done; j++) {
						if (combinations.get(level).get(j).contains(key)) {
							counts.get(level).set(j, counts.get(level).get(j) + 1);
							done = true;
						}
					}
					if(!done) {
						combinations.get(level).get(combinations.get(level).size() - 1).add(key);
						combinations.get(level).add(new ArrayList<Integer>());
						counts.get(level).add(1);
					}
				}
			}
			combinations.get(level).remove(combinations.get(level).size() - 1);
			
			// Delete transactions that didn't meet the minimum support count
			for(int i=0; i<counts.get(level).size(); i++) {
				if(counts.get(level).get(i) < minSupportCount) {
					toBeDel.add(i);
				}
			}
			
			Collections.sort(toBeDel);
			for(int i=toBeDel.size()-1; i>=0; i--) {
				combinations.get(level).remove(toBeDel.get(i).intValue());
				counts.get(level).remove(toBeDel.get(i).intValue());
			}
			
			// Increment the level
			level++;
		}
		
		
		 while(combinations.get(level-1).size() > 0) {
			// Construct a combination table for the new level
			// Make lists for new level on the heap for both combinations and counts
			combinations.add(new ArrayList<ArrayList<Integer>>());
			counts.add(new ArrayList<Integer>());
			// go through each combination at the previous level
			for(int i=0; i<combinations.get(level-1).size(); i++) {
				// go through the first level and add to the current level
				for(int j=0; j<combinations.get(0).size(); j++) {
					if(!combinations.get(level-1).get(i).contains(combinations.get(0).get(j).get(0))) {
						combinations.get(level).add(new ArrayList<Integer>());
						
						// construct combinations for current level by adding the previous level items and a new item
						for(int k=0; k<combinations.get(level-1).get(i).size(); k++) {
							combinations.get(level).get(combinations.get(level).size()-1).add(combinations.get(level-1).get(i).get(k));
						}
						combinations.get(level).get(combinations.get(level).size()-1).add(combinations.get(0).get(j).get(0));
					
						// (In ascending order) Sort the new combination added to prepare for direct ArrayList comparison
						Collections.sort(combinations.get(level).get(combinations.get(level).size()-1));
					
						// compare the new combination to all other ones in the current level, and remove duplicates
						boolean done = false;
						for(int l=0; l<combinations.get(level).size() - 1 && !done; l++) {
							if(combinations.get(level).size() - 1 != l && combinations.get(level).get(combinations.get(level).size()-1).equals(combinations.get(level).get(l))) {
								combinations.get(level).remove(combinations.get(level).size()-1);
								done = true;
							}
						}
					}
				}
			}
		
			// update count for the new level combinations	
			for(int i=0; i<combinations.get(level).size(); i++) {
				// initialize counts to be 0
				counts.get(level).add(0);
				
				for (ArrayList<Integer> trans : transactionData) {
					boolean contain = true;
					boolean largeEnough = trans.size() >= combinations.get(level).get(i).size();
					
					for(int j=0;  largeEnough && j<combinations.get(level).get(i).size() && contain; j++) {
//						System.out.println(trans + ": " + combinations.get(level).get(i).get(j));
						if (!trans.contains(combinations.get(level).get(i).get(j))) {
							contain = false;
						}
					}
					if(contain && largeEnough) {
						counts.get(level).set(i, counts.get(level).get(i) + 1);
					}
				}
			}
			
			// Check if the count is less than the threshold
			toBeDel.clear();
			for(int i=0; i<counts.get(level).size(); i++) {
				if(counts.get(level).get(i) < minSupportCount) {
					toBeDel.add(i);
				}
			}
			
			// Delete the combinations that didn't meet the threshold
			Collections.sort(toBeDel);
			for(int i=toBeDel.size()-1; i>=0; i--) {
				combinations.get(level).remove(toBeDel.get(i).intValue());
				counts.get(level).remove(toBeDel.get(i).intValue());
			}
			
			// Increment the level
			level++;
		}
		// Take out the empty slots in the lists, and correct level
		level--;
		combinations.remove(level);
		counts.remove(level);
		
//		System.out.println(combinations.get(1));
//		System.out.println(counts);
//		// The level after the whole process will show the absolute value of the number of current levels
//		System.out.println(level);
		
		return ruleGeneration(minConfidenceLev);
	}

	private ArrayList<Rule> ruleGeneration(double minConfidenceLev) {
		ArrayList<Rule> result = new ArrayList<Rule>();
		ArrayList<Integer> ant = new ArrayList<Integer>();
		ArrayList<Integer> antRev = new ArrayList<Integer>();
		ArrayList<Integer> con = new ArrayList<Integer>();
		ArrayList<Integer> conRev = new ArrayList<Integer>();
		
		for(int i=1; i<combinations.size(); i++) {
			for(int j=0; j<combinations.get(i).size(); j++) {
				for(int k=1; k<combinations.get(i).get(j).size(); k++) {
					// Reinitialize the ArrayLists for storing antecedents and consequents
					ant.clear();
					antRev.clear();
					con.clear();
					conRev.clear();
					
					for(int l=0; l<k; l++) {
						Integer item = combinations.get(i).get(j).get(l);
						ant.add(item);
						conRev.add(item);
					}
					for(int m=k; m<combinations.get(i).get(j).size(); m++) {
						Integer item = combinations.get(i).get(j).get(m);
						con.add(item);
						antRev.add(item);
					}
				
					// If the two rules generated meet the minimum confidence level,
					// then add them to the result rule list.
					double conf = calcConfidence(ant,con);
					if(conf > minConfidenceLev) {
						result.add(new Rule(convert(ant),convert(con),conf));
					}
					
					double confRev = calcConfidence(antRev,conRev);
					if(confRev > minConfidenceLev) {
						result.add(new Rule(convert(antRev),convert(conRev),confRev));
					}
				}
			}
		}
		
		
		return result;
	}
	
	private double calcConfidence(ArrayList<Integer> ant, ArrayList<Integer> con) {
		int hitCount = 0;
		int totalCount = 0;
		for (ArrayList<Integer> trans : transactionData) {
			boolean contain = true;
			boolean largeEnough = trans.size() >= ant.size();
			boolean largeEnough2 = trans.size() >= (ant.size() + con.size());
			
			for(int i=0; largeEnough && i<ant.size() && contain; i++) {
				if (!trans.contains(ant.get(i))) {
					contain = false;
				}
			}
			
			if(largeEnough && contain) {
				totalCount += 1;
				
				boolean contain2 = true;
				for(int i=0; largeEnough2 && i<con.size() && contain2; i++) {
					if(!trans.contains(con.get(i))) {
						contain2 = false;
					}
				}
			
				if(largeEnough2 && contain2) {
					hitCount += 1;
				}
			}
		}
		
		return hitCount*1.0/totalCount;
	}
	
	private ArrayList<String> convert(ArrayList<Integer> intList) {
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0; i<intList.size(); i++) {
			result.add(itemSet.getName(intList.get(i)));
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.start();

		Generator generator = new Generator("./Transactions.txt", "./Items.txt");
		ArrayList<Rule> rules = generator.generate(0.5, 0.5);
		for(Rule rule : rules) {
			System.out.println(rule);
		}
		
		timer.stop();
	}
}

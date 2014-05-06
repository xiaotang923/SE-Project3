package apriori.server;

import apriori.shared.Rule;
import apriori.shared.RuleSet;
import apriori.shared.ItemSet;
import apriori.shared.TransactionSet;
import java.util.ArrayList;
import java.util.Collections;

public class Generator {

    private ArrayList<ArrayList<Integer>> transactionData;
    private final ArrayList<ArrayList<ArrayList<Integer>>> combinations;
    private final ArrayList<ArrayList<Integer>> counts;
    private final ItemSet itemSet;
    private int level;

    public Generator(TransactionSet ts) {
        this.counts = new ArrayList<>();
        this.combinations = new ArrayList<>();
        this.itemSet = new ItemSet(ts.getItemsArray());
        this.constructTransactionMap(ts.getItemsArray());
    }

    // To initialize necessary variables to get ready for next generation
    public void initialize() {
        this.counts.clear();
        this.combinations.clear();
        this.level = 0;
    }

    // To convert every item of type String in the transactionSet to a integer, 
    // so it's faster in all operations.  (This is done by using the hashmap in ItemSet Class)
    private void constructTransactionMap(ArrayList<String[]> transactionString) {
        this.transactionData = new ArrayList<>(4096);
        for (String[] trans : transactionString) {
            this.transactionData.add(new ArrayList<Integer>());
            for(int i = 0; i < trans.length; i++) {
                this.transactionData.get(this.transactionData.size() - 1).add(itemSet.getKey(trans[i]));
            }
        }
    }

    // Uses apriori algorithm to generate the rules and return as an ArrayList
    public RuleSet generate(double minSupportLev, double minConfidenceLev) {
        int minSupportCount = (int) Math.round(transactionData.size() * minSupportLev);
        ArrayList<Integer> toBeDel = new ArrayList<>();

        // Generating 1-itemSet Frequency table
        {
            // Constructing the first level of combination array, and store the update counts array
            combinations.add(new ArrayList<ArrayList<Integer>>());
            combinations.get(level).add(new ArrayList<Integer>());
            counts.add(new ArrayList<Integer>());

            for (ArrayList<Integer> trans : transactionData) {
                for (int i = 0; i < trans.size(); i++) {
                    Integer key = trans.get(i);
                    boolean done = false;
                    for (int j = 0; j < combinations.get(level).size() && !done; j++) {
                        if (combinations.get(level).get(j).contains(key)) {
                            counts.get(level).set(j, counts.get(level).get(j) + 1);
                            done = true;
                        }
                    }
                    if (!done) {
                        combinations.get(level).get(combinations.get(level).size() - 1).add(key);
                        combinations.get(level).add(new ArrayList<Integer>());
                        counts.get(level).add(1);
                    }
                }
            }
            combinations.get(level).remove(combinations.get(level).size() - 1);

            // Delete transactions that didn't meet the minimum support count
            for (int i = 0; i < counts.get(level).size(); i++) {
                if (counts.get(level).get(i) < minSupportCount) {
                    toBeDel.add(i);
                }
            }

            Collections.sort(toBeDel);
            for (int i = toBeDel.size() - 1; i >= 0; i--) {
                combinations.get(level).remove(toBeDel.get(i).intValue());
                counts.get(level).remove(toBeDel.get(i).intValue());
            }

            // Increment the level
            level++;
        }
        // Finished generating 1-itemSet Frequency table

        while (combinations.get(level - 1).size() > 0) {
            // Construct a combination table for the new level
            // Make lists for new level on the heap for both combinations and counts
            combinations.add(new ArrayList<ArrayList<Integer>>());
            counts.add(new ArrayList<Integer>());
            // go through each combination at the previous level
            for (int i = 0; i < combinations.get(level - 1).size(); i++) {
                // go through the first level and add to the current level
                for (int j = 0; j < combinations.get(0).size(); j++) {
                    if (!combinations.get(level - 1).get(i).contains(combinations.get(0).get(j).get(0))) {
                        combinations.get(level).add(new ArrayList<Integer>());

                        // construct combinations for current level by adding the previous level items and an item from level 1
                        for (int k = 0; k < combinations.get(level - 1).get(i).size(); k++) {
                            combinations.get(level).get(combinations.get(level).size() - 1).add(combinations.get(level - 1).get(i).get(k));
                        }
                        combinations.get(level).get(combinations.get(level).size() - 1).add(combinations.get(0).get(j).get(0));

                        // (In ascending order) Sort the new combination added to prepare for direct ArrayList comparison
                        Collections.sort(combinations.get(level).get(combinations.get(level).size() - 1));

                        // compare the new combination to all other ones in the current level, and remove duplicates
                        boolean done = false;
                        for (int l = 0; l < combinations.get(level).size() - 1 && !done; l++) {
                            if (combinations.get(level).size() - 1 != l && combinations.get(level).get(combinations.get(level).size() - 1).equals(combinations.get(level).get(l))) {
                                combinations.get(level).remove(combinations.get(level).size() - 1);
                                l--; // Correct the index after deleting one item, because the item at 'l' is now a different item
                                done = true;
                            }
                        }
                    }
                }
            }

            // update count for the new level combinations	
            for (int i = 0; i < combinations.get(level).size(); i++) {
                // initialize counts to be 0
                counts.get(level).add(0);

                for (ArrayList<Integer> trans : transactionData) {
                    boolean contain = true;
                    boolean largeEnough = trans.size() >= combinations.get(level).get(i).size();

                    for (int j = 0; largeEnough && j < combinations.get(level).get(i).size() && contain; j++) {
                        if (!trans.contains(combinations.get(level).get(i).get(j))) {
                            contain = false;
                        }
                    }
                    if (contain && largeEnough) {
                        counts.get(level).set(i, counts.get(level).get(i) + 1);
                    }
                }
            }

            // Check if the count is less than the threshold
            toBeDel.clear();
            for (int i = 0; i < counts.get(level).size(); i++) {
                if (counts.get(level).get(i) < minSupportCount) {
                    toBeDel.add(i);
                }
            }

            // Delete the combinations that didn't meet the threshold
            Collections.sort(toBeDel);
            for (int i = toBeDel.size() - 1; i >= 0; i--) {
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
        
        return ruleGeneration(minConfidenceLev);
    }

    // To actually use the combinations generated and construct rules
    private RuleSet ruleGeneration(double minConfidenceLev) {
        RuleSet result = new RuleSet();
        ArrayList<Integer> ant = new ArrayList<>();
        ArrayList<Integer> con = new ArrayList<>();
        
        // Go through the last level of combinations
        for (int i = 0; i < combinations.get(combinations.size() - 1).size(); i++) {
            // All possible combinations without the case that includes every item
            // because neither antecedent and consequent should contain all items
            // in the set, that makes it not a rule anymore
            ArrayList<Integer> finalList = combinations.get(combinations.size() - 1).get(i);
            int comboNum = (int) Math.pow(2, finalList.size()) - 1;

            for (int j = 1; j < comboNum; j++) { // Antecedent loop
                int mask = 1 << (finalList.size() - 1);
                for (int p = 0; p < finalList.size(); p++) {
                    if ((j & mask) != 0) { // means that the pth item is present
                        ant.add(finalList.get(p));
                    }
                    // Shift the 1 in the mask
                    mask = mask >> 1;
                }

                // Each possible antecedent combines with all possible consequents
                for (int k = 1; k < comboNum; k++) { // Consequent loop
                    int mask2 = 1 << (finalList.size() - 1);
                    for (int q = 0; q < finalList.size(); q++) {
                        // Make sure there is no duplicate item from antecedent in the consequent
                        if (!ant.contains(finalList.get(q))) {
                            if ((k & mask2) != 0) { // means that the rth item is present
                                con.add(finalList.get(q));
                            }
                        }
                        // Shift the 1 in the mask
                        mask2 = mask2 >> 1;
                    }

                    if (!con.isEmpty()) {
                        double conf = calcConfidence(ant, con);
                        Rule newRule = new Rule(convert(ant), convert(con), conf);
                        
                        // If the rule generated meet the minimum confidence level,
                        // then add them to the result rule list.
                        if (conf >= minConfidenceLev && !result.contains(newRule)) {
                            result.add(newRule);
                        }
                        con.clear();
                    }
                }
                ant.clear();
            }
        }
        // Sort the rules
        result.sort();
        
        return result;
    }

    // Go through all transactions, and counts the times they appeared, and then 
    // calculate the confidence level
    private double calcConfidence(ArrayList<Integer> ant, ArrayList<Integer> con) {
        int hitCount = 0;
        int totalCount = 0;
        for (ArrayList<Integer> trans : transactionData) {
            boolean contain = true;
            boolean largeEnough = trans.size() >= ant.size();
            boolean largeEnough2 = trans.size() >= (ant.size() + con.size());

            for (int i = 0; largeEnough && i < ant.size() && contain; i++) {
                if (!trans.contains(ant.get(i))) {
                    contain = false;
                }
            }

            if (largeEnough && contain) {
                totalCount += 1;

                boolean contain2 = true;
                for (int i = 0; largeEnough2 && i < con.size() && contain2; i++) {
                    if (!trans.contains(con.get(i))) {
                        contain2 = false;
                    }
                }

                if (largeEnough2 && contain2) {
                    hitCount += 1;
                }
            }
        }

        return hitCount * 1.0 / totalCount;
    }

    // To conver the the items of type integers back to String form using the
    // hashmap in ItemSet Class again
    private ArrayList<String> convert(ArrayList<Integer> intList) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < intList.size(); i++) {
            result.add(itemSet.getName(intList.get(i)));
        }

        return result;
    }

}

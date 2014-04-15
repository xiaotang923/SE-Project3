package apriori;

import java.util.ArrayList;

public class Rule implements Comparable<Rule> {
	
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double confidencLev;
	
	public Rule(ArrayList<String> antecedent, ArrayList<String> consequent, double confidencLev) {
		this.antecedent = antecedent;
		this.consequent = consequent;
		this.confidencLev = confidencLev;
	}
	
        @Override
	public String toString() {
		return antecedent + "  ==>  " + consequent + " | (Confidence: " + (double)Math.round(confidencLev*10000)/100 + "%)";
	}
	
	@Override
	public int compareTo(Rule rule) {
		int result = this.antecedent.size() - rule.antecedent.size();
		if(result == 0) {
			result = this.consequent.size() - rule.consequent.size();
			if(result == 0) {
				result = (int)((this.confidencLev - rule.confidencLev) * 100);
			}
		}
		return result;
	}
        
        @Override
        public boolean equals(Object obj) {
            boolean result = true;
            Rule other = (Rule)obj;
            
            for(String item : other.getAntecedent()) {
                if(!this.antecedent.contains(item)) {
                    result = false;
                }
            }
            
            if(result) {
                for(String item : other.getConsequent()) {
                    if(!this.getConsequent().contains(item)) {
                        result =false;
                    }
                }
            }
            
            return result;
        }
        
        public double getConfidencLev() {
            return this.confidencLev;
        }
        
        public ArrayList<String> getAntecedent() {
            return this.antecedent;
        }
        
        public ArrayList<String> getConsequent() {
            return this.consequent;
        }
        
}

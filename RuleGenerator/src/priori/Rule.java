package priori;

import java.util.ArrayList;

public class Rule {
	
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double confidencLev;
	
	public Rule(ArrayList<String> antecedent, ArrayList<String> consequent, double confidencLev) {
		this.antecedent = antecedent;
		this.consequent = consequent;
		this.confidencLev = confidencLev;
	}
	
	public String toString() {
		return antecedent + "  ==>  " + consequent + " | (Confidence: " + (double)Math.round(confidencLev*10000)/100 + "%)";
	}
}

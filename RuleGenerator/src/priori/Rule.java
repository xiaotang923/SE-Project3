package priori;

import java.util.ArrayList;

public class Rule {
	
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double confidencLev;
	private double supportLev;
	
	public Rule(ArrayList<String> antecedent, ArrayList<String> consequent, double confidencLev, double supportLev) {
		this.antecedent = antecedent;
		this.consequent = consequent;
		this.confidencLev = confidencLev;
		this.supportLev = supportLev;
	}
}
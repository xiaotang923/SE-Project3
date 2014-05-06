package apriori.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("EqualsAndHashcode")
public class Rule implements Comparable<Rule>, Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<String> antecedent;
    private final ArrayList<String> consequent;
    private final Double confidencLev;

    public Rule(ArrayList<String> antecedent, ArrayList<String> consequent, double confidencLev) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.confidencLev = confidencLev;
    }

    @Override
    public String toString() {
        return antecedent + "  ==>  " + consequent + "  |  (Confidence: " + ((int) Math.round(confidencLev * 10000)) / 10000.0 + ")";
    }

    @Override
    public int compareTo(Rule rule) {
        int result = this.antecedent.size() - rule.antecedent.size();
        if (result == 0) {
            result = this.consequent.size() - rule.consequent.size();
            if (result == 0) {
                result = (int) ((this.confidencLev - rule.confidencLev) * 100);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = true;
        Rule other = (Rule) obj;
        
        if (!this.confidencLev.equals(other.confidencLev) || this.antecedent.size() != other.antecedent.size() || this.consequent.size() != other.consequent.size()) {
            result = false;
        } else if (this.antecedent.size() == 1 && this.consequent.size() == 1) {
            if (!(this.antecedent.toString() + this.consequent.toString()).equals(other.antecedent.toString() + other.consequent.toString())) {
                result = false;
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

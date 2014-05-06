/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apriori.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RuleSet implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public ArrayList<Rule> rules;
    
    public RuleSet() {
        rules = new ArrayList<>();
    }

    public Rule get(int i) {
        return rules.get(i);
    }

    public boolean isEmpty() {
        return rules.isEmpty();
    }

    public int size() {
        return rules.size();
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void sort() {
        Collections.sort(rules);
    }

    public void add(Rule newRule) {
        rules.add(newRule);
    }

    public boolean contains(Rule newRule) {
        return rules.contains(newRule);
    }
    
    public void clear() {
        rules.clear();
    }
    
    @Override
    public String toString() {
        return rules.toString();
    }
}

package apriori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseControl {

    protected Connection cn;

    public boolean open() {
        boolean result = true;
        try {
            if (cn == null || cn.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                cn = DriverManager.getConnection("jdbc:mysql://dario.cs.uwec.edu/cs355group15", "WEIRC", "W633212$");
            }
        } catch (Exception e) {
            System.out.println("got here");
            result = false;
        }
        return result;
    }

    public void close() {
        if (cn == null) {
            return;
        }
        try {
            cn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cn = null;
        }
    }

    // Dump all transaction records into the database, and return the transactionset id
    public int transactionSetInsert(ArrayList<String> transactions) {
        int index = -1;
        ArrayList<Integer> keys = new ArrayList<Integer>();
        try {
            // Add all transaction records into transaction table, and
            // store their primary keys to associate with transactionSet later
            PreparedStatement ps = cn.prepareStatement("INSERT INTO SE_TRANSACTION(TRANSACTION_ITEMS) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            for (String trans : transactions) {
                ps.clearParameters();
                ps.setString(1, trans);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    keys.add(rs.getInt(1));
                }
            }

            // combine all keys for transactions inserted into 1 string and store into 1 transactionSet
            // and then return the index value
            ps = cn.prepareStatement("INSERT INTO SE_TRANSACTIONSET(TRANSSET_SET) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            String trans_set = keys.get(0).toString();
            for (int i = 1; i < keys.size(); i++) {
                trans_set += "," + keys.get(i).toString();
            }
            ps.setString(1, trans_set);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                index = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return index;
    }

    // Dump all rules generated into the database
    public boolean ruleSetInsert(ArrayList<Rule> rules, int transID) {
        boolean result = true;
        ArrayList<Integer> keys = new ArrayList<Integer>();
        try {
            // Add all rules into rule table, and store their primary keys to associate with RuleSet later
            PreparedStatement ps = cn.prepareStatement("INSERT INTO SE_RULE(RULE_ANTECEDENT, RULE_CONSEQUENT) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            for (Rule rule : rules) {
                ps.clearParameters();
                ps.setString(1, rule.getAntecedent().toString());
                ps.setString(2, rule.getConsequent().toString());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    keys.add(rs.getInt(1));
                }
            }

            // combine all keys for rules inserted into 1 string and store into 1 RuleSet
            ps = cn.prepareStatement("INSERT INTO SE_RULESET(RULESET_DATE,TRANSSET_ID,RULES_ID) VALUES(?,?,?)");
            String rule_set = keys.get(0).toString();
            for (int i = 1; i < keys.size(); i++) {
                rule_set += "," + keys.get(i).toString();
            }
            
            ps.setTimestamp(1, new Timestamp(new Date().getTime()));
            ps.setInt(2, transID);
            ps.setString(3, rule_set);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //(RULE_ID,RULE-ANTECEDEND,RULE_CONSEQUENT,RULESET_ID)
        return result;
    }
}

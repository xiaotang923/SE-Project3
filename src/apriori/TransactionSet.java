package apriori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class TransactionSet {

    private ArrayList<String[]> itemsArray;
    private int transID;
    
    public TransactionSet(String transactionFile) {
        itemsArray = new ArrayList<String[]>();
        transID = -1;
        Pattern splitPattern = Pattern.compile("([!,\\{]+)");
        try {
            Scanner transactions = new Scanner(new File(transactionFile));

            //Skip the meta info
            for (int i = 0; i < 3; i++) {
                if (transactions.hasNextLine()) {
                    transactions.nextLine();
                }
            }

            // read in each transactions and split and store as a string array,
            // also store the transactions into the database as Strings
            ArrayList<String> trans = new ArrayList<String>();
            DatabaseControl dbc = new DatabaseControl();
            
            while (transactions.hasNextLine()) {
                String transaction = transactions.nextLine().replaceAll("[\\{\\}\\s]", "");
                // record each transaction for later database operations
                trans.add(transaction);
                String[] temp = splitPattern.split(transaction);
                
                // When the transaction contains more than 25 items, it will be dropped
                boolean toAdd = true;
                if(temp.length > 25) {
                    String items = temp[0];
                    for(int i=1; i<temp.length; i++) {
                        items += "," + temp[i].toString();
                    }
                    items += "\n";
                    JOptionPane.showMessageDialog(null, "Warning: The following transaction dropped due to containing over 25 items:\n" + items);
                    toAdd = false;
                }
                
                if (!temp[0].equals("") && toAdd) {
                    itemsArray.add(temp);
                }
            }        
            
            if(dbc.open()) {
                transID = dbc.transactionSetInsert(trans);
                dbc.close();
            } else {
                JOptionPane.showMessageDialog(null, "Database connection has failed, get your shit together!!!");
            }
            
            transactions.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getItemsArray() {
        return itemsArray;
    }

    public void remove(int index) {
        itemsArray.remove(index);
    }
    
    public int getTransactionID() {
        return transID;
    }
}

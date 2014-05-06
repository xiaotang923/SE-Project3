package apriori.shared;

import apriori.server.TransDump;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TransactionSet implements Serializable {

    private final ArrayList<String[]> itemsArray;
    private ArrayList<String> trans;
    private int transID;
    private TransDump transDump;
    private final ArrayList<String> malTrans;

    @SuppressWarnings("ConvertToTryWithResources")
    public TransactionSet(String transactionFile) {
        itemsArray = new ArrayList<>();
        malTrans = new ArrayList<>();
        transID = -1;

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
            trans = new ArrayList<>();

            while (transactions.hasNextLine()) {
                String transaction = transactions.nextLine();
                if (transaction.matches("^\\{[a-zA-Z\\d,\\s]*\\}")) {
                    transaction = transaction.replaceAll("[\\{\\}\\s]", "");
                    
                    if (transaction.matches("[a-zA-Z\\d,]+")) {
                        // record each transaction for later database operations
                        trans.add(transaction);
                        String[] temp = transaction.split(",");

                        // When the transaction contains more than 25 items, it will be dropped

                        if (temp.length > 25) {
                            String items = "{" + temp[0];
                            for (int i = 1; i < temp.length; i++) {
                                items += "," + temp[i].toString();
                            }
                            items += "}\n";
                            malTrans.add("Warning: The following transaction dropped due to containing over 25 items:\n" + items);
                        } else {
                            if (!temp[0].equals("")) {
                               itemsArray.add(temp);
                            }
                        }
                    } else {
                        malTrans.add(transaction);
                    }
                } else {
                    malTrans.add(transaction);
                }
            }

            // Check if it has gone into the while loop
            if (trans.isEmpty()) {
                malTrans.add("There has to be at least 1 transaction!");
            }

            transactions.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "The specified transaction file doesn't exist!");
        }
    }

    public ArrayList<String[]> getItemsArray() {
        return itemsArray;
    }

    public void InsertTransactionSet() {
        // Insert all transaction records into the database
        if (trans != null) {
            transDump = new TransDump(this, trans);
            transDump.start();
        }
    }

    public void remove(int index) {
        itemsArray.remove(index);
    }

    public Integer getTransactionID() {
        try {
            transDump.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(TransactionSet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.transID;
    }

    public void setTransactionID(int transID) {
        this.transID = transID;
    }

    public ArrayList<String> getMalTransactions() {
        return this.malTrans;
    }
}

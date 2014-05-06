package apriori.server;

import apriori.shared.TransactionSet;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TransDump extends Thread implements Runnable, Serializable {
    
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> trans;
    private TransactionSet ts;

    public TransDump(TransactionSet ts, ArrayList<String> trans) {
        this.ts = ts;
        this.trans = trans;
    }

    @Override
    public void run() {
        DatabaseControl dbc = new DatabaseControl();
        if (dbc.open()) {
            ts.setTransactionID(dbc.transactionSetInsert(trans));
            dbc.close();
        } else {
            JOptionPane.showMessageDialog(null, "Database connection has failed!");
        }
    }
}

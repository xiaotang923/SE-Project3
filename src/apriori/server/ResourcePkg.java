package apriori.server;

import apriori.shared.TransactionSet;
import java.io.Serializable;

public class ResourcePkg  implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Double minConLev;
    private final Double minSupLev;
    private final TransactionSet transSet;

    public ResourcePkg (Double minSupport, Double minConf, TransactionSet transSet) {
        this.minSupLev = minSupport;
        this.minConLev = minConf;
        this.transSet = transSet;
    }
    
    public Double getMinSupport() {
        return this.minSupLev;
    }
    
    public Double getMinConfidence() {
        return this.minConLev;
    }
    
    public TransactionSet getTransactionSet() {
        return this.transSet;
    }
    
    public void startInsertion() {
        transSet.InsertTransactionSet();
    }
    
    public int getTransactionID() {
        return this.transSet.getTransactionID();
    }
}

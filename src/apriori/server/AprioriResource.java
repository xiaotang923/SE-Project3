package apriori.server;


import apriori.shared.RuleSet;
import org.restlet.resource.Put;
import org.restlet.resource.Get;

public interface AprioriResource {
    @Put
    public RuleSet generateRules(ResourcePkg rp);
    
    @Get
    public int getTransactionSetID();
    
    @Get
    public int getTime();
}

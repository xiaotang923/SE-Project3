package apriori.server;

import apriori.shared.RuleSet;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import org.restlet.resource.ServerResource;

public class AprioriServerResource extends ServerResource implements AprioriResource {

    private static ResourcePkg rp;
    private static int time;
    
    @Override
    public RuleSet generateRules(ResourcePkg resp) {
        rp = resp;
        rp.startInsertion();
        
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long start = bean.getCurrentThreadCpuTime();

        // Start rule generation
        Generator generator = new Generator(rp.getTransactionSet());
        RuleSet rs = generator.generate(rp.getMinSupport(), rp.getMinConfidence());
        
        // Get the total time took on this thread
        time = (int)((bean.getCurrentThreadCpuTime() - start) / 1e5);
        
        return rs;
    }

    @Override
    public int getTransactionSetID() {
        if (rp != null) {
            return rp.getTransactionID();
        }

        return -1;
    }
    
    @Override
    public int getTime() {
        return time;
    }
}

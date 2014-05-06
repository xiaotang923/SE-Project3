package apriori.server;

import javax.swing.JOptionPane;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class AprioriServer {

    public static void main(String[] args) {
        Server aprioriServer = new Server(Protocol.HTTP, 8111, AprioriServerResource.class);
        try {
            aprioriServer.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Server startup failure!");
        }
    }
}

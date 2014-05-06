package apriori.client;

import apriori.server.AprioriResource;
import apriori.server.DatabaseControl;
import apriori.server.ResourcePkg;
import apriori.shared.RuleSet;
import apriori.shared.TransactionSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.restlet.resource.ClientResource;

public class GUI extends javax.swing.JFrame {

    private RuleSet rules;
    private String transactionPath = null;
    private double minConLev = 0.00;
    private double minSupLev = 0.00;

    public GUI() {
        initComponents();
        this.setVisible(true);
    }

    // Generate the rules and record the time elapsed
    @SuppressWarnings({"SleepWhileInLoop", "ConvertToTryWithResources"})
    public void generate() {
        // Read the transaction file in and make a transactionSet object
        TransactionSet transSet = new TransactionSet(this.transactionPath);
        
        // Retrieve all the transactions that are inproperly formated
        if(transSet.getMalTransactions().isEmpty()) {
        // Make a clent object that connects to the server and retrieve rules generated
        ClientResource clientResource = new ClientResource("http://localhost:8111/");
        AprioriResource proxy = clientResource.wrap(AprioriResource.class);
        ResourcePkg rp = new ResourcePkg(this.minSupLev, this.minConLev, transSet);
        rules = proxy.generateRules(rp);

        // Combine all rules into one single string with elapsed time
        String ruleString = constructRuleString() + "\n\n\nTime elapsed is: " + proxy.getTime()/10000.0 + " Seconds";

        // Display the result of the generation
        displayArea.setText(ruleString);

        // Ask the user to confirm whether he wants to output the rules to a file
        // and then if yes, show a SaveDialog;
        if (JOptionPane.showConfirmDialog(this, "Do you want to output all the rules to a text file?") == JOptionPane.YES_OPTION) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.showSaveDialog(this);
            File f = chooser.getSelectedFile();
            try {
                PrintWriter out = new PrintWriter(f);
                out.write(ruleString);
                out.close();

                DatabaseControl dbc = new DatabaseControl();
                if(dbc.open()) {
                    // Dump the rules generated into the database
                    dbc.ruleSetInsert(rules.getRules(), proxy.getTransactionSetID());
                    dbc.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Database connection has failed!");
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found!");
            }
        }
        } else {
            String error = "The following transactions are improperly formatted:\n";
            for(String s : transSet.getMalTransactions()) {
                error += s + "\n";
            }
            displayArea.setText(error);
        }
    }

    // To combine all rules (in string form) into one string object
    public String constructRuleString() {
        String result = "No rules generated!";

        if (!rules.isEmpty()) {
            result = "Rule 1:  " + rules.get(0).toString();
            for (int i = 1; i < rules.size(); i++) {
                result += "\n" + "Rule " + (i + 1) + ":   " + rules.get(i);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        minConfLev = new javax.swing.JLabel();
        minSupportLev = new javax.swing.JLabel();
        MSLtxt = new javax.swing.JTextField();
        MCLtxt = new javax.swing.JTextField();
        subtitle = new javax.swing.JLabel();
        generate = new javax.swing.JButton();
        lowerPanel = new javax.swing.JScrollPane();
        displayArea = new javax.swing.JTextArea();
        title = new javax.swing.JTextField();
        transactionFile = new javax.swing.JTextField();
        browse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(700, 700));
        setResizable(false);

        contentPanel.setBackground(new java.awt.Color(51, 0, 0));
        contentPanel.setFocusable(false);

        minConfLev.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        minConfLev.setForeground(new java.awt.Color(255, 204, 102));
        minConfLev.setText("Minimum Confidence Level:");

        minSupportLev.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        minSupportLev.setForeground(new java.awt.Color(255, 204, 102));
        minSupportLev.setText("Minimum Support Level:");

        MSLtxt.setBackground(new java.awt.Color(193, 178, 178));

        MCLtxt.setBackground(new java.awt.Color(193, 178, 178));

        subtitle.setFont(new java.awt.Font("Tempus Sans ITC", 1, 20)); // NOI18N
        subtitle.setForeground(new java.awt.Color(255, 204, 102));
        subtitle.setText("Display Panel:");

        generate.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        generate.setForeground(new java.awt.Color(0, 51, 51));
        generate.setText("Generate");
        generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateActionPerformed(evt);
            }
        });

        displayArea.setBackground(new java.awt.Color(75, 65, 65));
        displayArea.setColumns(20);
        displayArea.setForeground(new java.awt.Color(255, 255, 255));
        displayArea.setRows(5);
        displayArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        lowerPanel.setViewportView(displayArea);

        title.setBackground(new java.awt.Color(0, 51, 51));
        title.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 204, 102));
        title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        title.setText("Apriori Rule Generator");

        transactionFile.setEditable(false);
        transactionFile.setBackground(new java.awt.Color(193, 178, 178));
        transactionFile.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N

        browse.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        browse.setForeground(new java.awt.Color(0, 51, 51));
        browse.setText("Browse");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addComponent(minSupportLev, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MSLtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addComponent(minConfLev, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MCLtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(transactionFile, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generate)
                .addGap(188, 188, 188))
            .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contentPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                        .addComponent(lowerPanel, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addContainerGap()))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minSupportLev, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MSLtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minConfLev, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MCLtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(90, 90, 90))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transactionFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addComponent(generate)
                        .addGap(25, 25, 25)))
                .addComponent(subtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(409, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(contentPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                    .addComponent(lowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("Generate a set of rules with a transaction set");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // When "Generate" button is clicked, check if every required information is valid,
    // and then proceed to generation
    private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
        if (this.transactionPath == null) {
            JOptionPane.showMessageDialog(this, "Please select your transaction file before generating rules!");
        } else if (MCLtxt.getText().equals("") || MSLtxt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please specify the minimum confidence and support level!");
        } else {
            try {
                // Parse minimum support level
                this.minSupLev = Double.parseDouble(this.MSLtxt.getText());
                if (minSupLev > 1.0 || minSupLev < 0.0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid 'Minimum Support Level' (0.00~1.00)!");
                    return;
                }

                // Parse minimum confidence level
                this.minConLev = Double.parseDouble(this.MCLtxt.getText());
                if (minConLev > 1.0 || minConLev < 0.0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid 'Minimum Confidence Level' (0.00~1.00)!");
                    return;
                }
                generate();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Please correct the number formats (0.00~1.00)!");
            }
        }
    }//GEN-LAST:event_generateActionPerformed

    // If the users click "Browse" button, the GUI pops up a file choosing window for 
    // users to specify which transaction file they want to open
    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(this);
        File f = chooser.getSelectedFile();
        if (f != null) {
            transactionPath = f.getAbsolutePath();
            transactionFile.setText(transactionPath);
        }
    }//GEN-LAST:event_browseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MCLtxt;
    private javax.swing.JTextField MSLtxt;
    private javax.swing.JButton browse;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JTextArea displayArea;
    private javax.swing.JButton generate;
    private javax.swing.JScrollPane lowerPanel;
    private javax.swing.JLabel minConfLev;
    private javax.swing.JLabel minSupportLev;
    private javax.swing.JLabel subtitle;
    private javax.swing.JTextField title;
    private javax.swing.JTextField transactionFile;
    // End of variables declaration//GEN-END:variables
}

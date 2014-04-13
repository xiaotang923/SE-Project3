/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GUI extends javax.swing.JFrame {

    private ArrayList<Rule> rules;
    private String transactionPath = null;
    private double minConLev = 0.00;
    private double minSupLev = 0.00;

    public GUI() {
        initComponents();
    }

    public void generate() {
        Timer timer = new Timer();
        timer.start();

        Generator generator = new Generator(transactionPath);
        rules = generator.generate(minSupLev, minConLev);
        Collections.sort(rules);

        displayArea.setText(constructRuleString());
    }

    public String constructRuleString() {
        String result = null;
        
        if (!rules.isEmpty()) {
            result = "Rule 1:  " + rules.get(0).toString();
            for (int i = 1; i < rules.size(); i++) {
                result += "\n" + "Rule " + (i+1) + ":   " + rules.get(i);
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

    private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
        if (this.transactionPath == null) {
            JOptionPane.showMessageDialog(this, "Please select your transaction file before generating rules!");
        } else if (MCLtxt.getText().equals("") || MSLtxt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please specify the minimum confidence and support level!");
        } else {
            // Parse minimum support level
            this.minSupLev = Double.parseDouble(this.MSLtxt.getText());
            if (minSupLev > 1.0 && minSupLev < 0.0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 'Minimum Support Level' (0.00~1.00)!");
                return;
            }
        
            // Parse minimum confidence level
            this.minConLev = Double.parseDouble(this.MCLtxt.getText());
            if (minConLev > 1.0 && minConLev < 0.0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 'Minimum Confidence Level' (0.00~1.00)!");
                return;
            }
            generate();
        }
    }//GEN-LAST:event_generateActionPerformed

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(this);
        File f = chooser.getSelectedFile();
        if(f != null) {
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

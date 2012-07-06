/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import scheduling.algorithms.Scheduler.aperiodicGenerationMode;

/**
 *
 * @author Miguel
 */
public class mainWindow extends javax.swing.JFrame {
    
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    System.out.println("aaaaaaaaaaaaaaaaaaaa");
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // 
                // 
                JOptionPane.showMessageDialog(button, label + ": Ouch!");
                // System.out.println(label + ": Ouch!");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    private ButtonGroup rButtonGroup;

    public static String newline = System.getProperty("line.separator");
    /**
     * Creates new form mainWindow
     */
    public mainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tasksWindow = new javax.swing.JFrame();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelOfPeriodic = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelOfAperiodic = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        resultWindow = new javax.swing.JFrame();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        fileImportData = new javax.swing.JMenuItem();
        fileSaveResults = new javax.swing.JMenuItem();

        javax.swing.GroupLayout panelOfPeriodicLayout = new javax.swing.GroupLayout(panelOfPeriodic);
        panelOfPeriodic.setLayout(panelOfPeriodicLayout);
        panelOfPeriodicLayout.setHorizontalGroup(
            panelOfPeriodicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        panelOfPeriodicLayout.setVerticalGroup(
            panelOfPeriodicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panelOfPeriodic);

        jTabbedPane2.addTab("Periódicas", jScrollPane2);

        javax.swing.GroupLayout panelOfAperiodicLayout = new javax.swing.GroupLayout(panelOfAperiodic);
        panelOfAperiodic.setLayout(panelOfAperiodicLayout);
        panelOfAperiodicLayout.setHorizontalGroup(
            panelOfAperiodicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        panelOfAperiodicLayout.setVerticalGroup(
            panelOfAperiodicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(panelOfAperiodic);

        jTabbedPane2.addTab("Aperiódicas", jScrollPane3);

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save changes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tasksWindowLayout = new javax.swing.GroupLayout(tasksWindow.getContentPane());
        tasksWindow.getContentPane().setLayout(tasksWindowLayout);
        tasksWindowLayout.setHorizontalGroup(
            tasksWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tasksWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tasksWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2)
                    .addGroup(tasksWindowLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        tasksWindowLayout.setVerticalGroup(
            tasksWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tasksWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout resultWindowLayout = new javax.swing.GroupLayout(resultWindow.getContentPane());
        resultWindow.getContentPane().setLayout(resultWindowLayout);
        resultWindowLayout.setHorizontalGroup(
            resultWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        resultWindowLayout.setVerticalGroup(
            resultWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SchedulingAlgorithms");

        jButton1.setText("Plan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        menuFile.setText("File");

        fileImportData.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        fileImportData.setText("Import data...");
        fileImportData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileImportDataActionPerformed(evt);
            }
        });
        menuFile.add(fileImportData);

        fileSaveResults.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSaveResults.setText("Save results...");
        fileSaveResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveResultsActionPerformed(evt);
            }
        });
        menuFile.add(fileSaveResults);

        jMenuBar2.add(menuFile);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileImportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileImportDataActionPerformed
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        //In response to a button click:
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Opening
            Scheduler.importTaskSets(fc.getSelectedFile().getAbsolutePath());
       
            //Scheduler.getTaskSets().size();
            if (Scheduler.getTaskSets() != null){
                int numOfSets = Scheduler.getTaskSets().size();

                jPanel1.setLayout( new GridLayout(6,0));

                rButtonGroup = new ButtonGroup();
                JRadioButton radioButton[] = new JRadioButton[numOfSets];
                for (int i = 0; i < numOfSets; i++){
                    radioButton[i] = new JRadioButton("Set " + i, false);
                    radioButton[i].setActionCommand(Integer.toString(i));
                    jPanel1.add(radioButton[i]);
                    rButtonGroup.add(radioButton[i]);
                }
                JButton taskButton = new JButton("tasks...");
                taskButton.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (rButtonGroup.getSelection() != null){
                            System.out.println(rButtonGroup.getSelection().getActionCommand());
                            int selectedSet = Integer.parseInt(rButtonGroup.getSelection().getActionCommand());



                            panelOfPeriodic.removeAll();
                            panelOfAperiodic.removeAll();

                            // TODO construir la finestra de tasques
                            //Scheduler.getTaskSets().get(selectedSet).
                            int numberOfGroups = 2;

                            panelOfPeriodic.setLayout(new GridLayout(numberOfGroups,0));

                            String[] periodicColumnNames = {"Task", "Phase", "Computation time", "Period"};

                            for (int i = 0; i < numberOfGroups; i++){
                                int numberOfTasksCurrentGroup = 10;
                                Object[][] data = new Object[numberOfTasksCurrentGroup][periodicColumnNames.length];
                                for (int j = 0; j < numberOfTasksCurrentGroup; j++){
                                    for (int k = 0; k < periodicColumnNames.length; k++){
                                        data[j][k] = new Object();
                                        data[j][k] = "texte de proba";
                                    }
                                }
                                JTable table = new JTable(data, periodicColumnNames);
                                JScrollPane scrollPane = new JScrollPane(table);
                                scrollPane.setPreferredSize(new Dimension(100,100));
                                table.setFillsViewportHeight(true);
                                panelOfPeriodic.add(scrollPane);
                            }
                            
                            // Aperiòdiques
                            //if (Scheduler.getAperiodicInfo() != null){
                                int numberOfAperiodicGroups = 1;//Scheduler.getAperiodicInfo().getAperiodicTaskGroups().length;
                                panelOfAperiodic.setLayout(new GridLayout(numberOfAperiodicGroups + 1, 0));
                                // +1 per afegir el panell de botons a la capçalera
                                boolean automatic = false, manual = false;
                                if (Scheduler.getAperiodicInfo().getMode().equals(aperiodicGenerationMode.AUTO))
                                    automatic = true;
                                else if (Scheduler.getAperiodicInfo().getMode().equals(aperiodicGenerationMode.MANUAL))
                                    manual = true;
                                ButtonGroup aperiodicButtonGroup = new ButtonGroup();
                                JRadioButton automaticRadioButton = new JRadioButton("Automatic", automatic);
                                automaticRadioButton.setActionCommand("Automatic");
                                JRadioButton manualRadioButton = new JRadioButton("Manual", manual);
                                manualRadioButton.setActionCommand("Manual");

                                aperiodicButtonGroup.add(automaticRadioButton);
                                aperiodicButtonGroup.add(manualRadioButton);

                                JButton genButton = new JButton("Gen");
                                JPanel genPanel = new JPanel();
                                genPanel.setLayout(new GridLayout(2,0));
                                
                                JPanel radioButtonsPanel = new JPanel();
                                radioButtonsPanel.setLayout(new GridLayout(0,2));

                                radioButtonsPanel.add(automaticRadioButton);
                                radioButtonsPanel.add(manualRadioButton);
                                genPanel.add(radioButtonsPanel);
                                genPanel.add(genButton);
                                
                                panelOfAperiodic.add(genPanel);
                                
                                String[] aperiodicColumnNames = {"Task", "Arrival", "Computation time", "End", "Delete"};

                                for (int i = 0; i < numberOfAperiodicGroups; i++){
                                    int numberOfTasksCurrentGroup = 10;
                                    Object[][] data = new Object[numberOfTasksCurrentGroup][aperiodicColumnNames.length];
                                    for (int j = 0; j < numberOfTasksCurrentGroup; j++){
                                        for (int k = 0; k < aperiodicColumnNames.length; k++){
                                            data[j][k] = new Object();
                                            data[j][k] = "texte de proba";
                                        }
                                        data[j][aperiodicColumnNames.length - 1] = new Object();
                                        data[j][aperiodicColumnNames.length - 1] = "X";
                                    }
                                    JTable table = new JTable(data, aperiodicColumnNames);
                                    table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
                                    table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
                                    JScrollPane scrollPane = new JScrollPane(table);
                                    scrollPane.setPreferredSize(new Dimension(100,100));
                                    table.setFillsViewportHeight(true);
                                    panelOfAperiodic.add(scrollPane);
                                }
                                //}
                            tasksWindow.setMinimumSize(new Dimension(400,300));
                            tasksWindow.setVisible(true);
                        }
                    }
                });
                jPanel1.add(taskButton);
                jPanel1.validate();
            }
         } else {
            // cancelled by the user. Nothing to do.
        }
    }//GEN-LAST:event_fileImportDataActionPerformed

    private void fileSaveResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveResultsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveResultsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO llençar el planificador i construir la finestra de resultats
        resultWindow.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tasksWindow.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO desar canvis de les taules
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new mainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem fileImportData;
    private javax.swing.JMenuItem fileSaveResults;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenu menuFile;
    private javax.swing.JPanel panelOfAperiodic;
    private javax.swing.JPanel panelOfPeriodic;
    private javax.swing.JFrame resultWindow;
    private javax.swing.JFrame tasksWindow;
    // End of variables declaration//GEN-END:variables
}
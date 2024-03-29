/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel
 */
public class mainWindow extends javax.swing.JFrame {
    

    private ButtonGroup rButtonGroup;
    ButtonGroup aperiodicButtonGroup;
    private AperiodicInfo aperiodicInfo;
    private int selectedSet;
    private AperiodicTask defaultAperiodicTask = new AperiodicTask("AT", 0.0, 0.0);
    JTable table;    
    
    
    


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
        btSave = new javax.swing.JButton();
        resultWindow = new javax.swing.JFrame();
        textPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        progressBar = new javax.swing.JFrame();
        barra = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        fileImportData = new javax.swing.JMenuItem();
        fileSaveResults = new javax.swing.JMenuItem();

        tasksWindow.setTitle("Tasks");

        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane2StateChanged(evt);
            }
        });

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

        btSave.setText("Save");
        btSave.setEnabled(false);
        btSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSaveMouseClicked(evt);
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
                        .addComponent(btSave)
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
                    .addComponent(btSave))
                .addContainerGap())
        );

        resultWindow.setTitle("Results");
        resultWindow.setMinimumSize(new java.awt.Dimension(600, 600));
        resultWindow.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        resultWindow.setPreferredSize(new java.awt.Dimension(600, 600));
        resultWindow.setType(java.awt.Window.Type.POPUP);

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 447, Short.MAX_VALUE)
        );

        saveButton.setLabel("Guardar");
        saveButton.setName("");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout resultWindowLayout = new javax.swing.GroupLayout(resultWindow.getContentPane());
        resultWindow.getContentPane().setLayout(resultWindowLayout);
        resultWindowLayout.setHorizontalGroup(
            resultWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        resultWindowLayout.setVerticalGroup(
            resultWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        progressBar.setTitle("Planificando...");
        progressBar.setMinimumSize(new java.awt.Dimension(225, 100));
        progressBar.setResizable(false);

        javax.swing.GroupLayout progressBarLayout = new javax.swing.GroupLayout(progressBar.getContentPane());
        progressBar.getContentPane().setLayout(progressBarLayout);
        progressBarLayout.setHorizontalGroup(
            progressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressBarLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        progressBarLayout.setVerticalGroup(
            progressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressBarLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
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
       
            buildMainWindow();
         } else {
            // cancelled by the user. Nothing to do.
        }
    }//GEN-LAST:event_fileImportDataActionPerformed

    void buildMainWindow(){
        
        Scheduler.setResultWindow(resultWindow, textPanel, progressBar,barra);
                    
            if (Scheduler.getTaskSets() != null){
                jPanel1.removeAll();
                int numOfSets = Scheduler.getTaskSets().size();

                jPanel1.setLayout( new GridLayout(6,0));

                rButtonGroup = new ButtonGroup();
                JRadioButton radioButton[] = new JRadioButton[numOfSets];
                for (int i = 0; i < numOfSets; i++){
                    radioButton[i] = new JRadioButton("Set " + i + ": " + Scheduler.getTaskSets().get(i).getTotalPeriodicLoad() * 100 + "%", false);
                    radioButton[i].setActionCommand(Integer.toString(i));
                    jPanel1.add(radioButton[i]);
                    rButtonGroup.add(radioButton[i]);
                }

                radioButton[0].setSelected(true);
                
                JButton taskButton = new JButton("tasks...");
                taskButton.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (rButtonGroup.getSelection() != null){
                            
                            buildPeriodicTab();
                            buildAperiodicTab(false);
                            int tab = jTabbedPane2.getSelectedIndex();
                                
                            tasksWindow.setMinimumSize(new Dimension(500,420));
                            tasksWindow.setVisible(true);
                        }
                    }
                });
                JPanel panelOfTaskButton = new JPanel();
                panelOfTaskButton.add(taskButton);
                jPanel1.add(panelOfTaskButton);
                jPanel1.validate();
            }
    }
    
    void buildPeriodicTab(){                
        System.out.println(rButtonGroup.getSelection().getActionCommand());
        selectedSet = Integer.parseInt(rButtonGroup.getSelection().getActionCommand());

        panelOfPeriodic.removeAll();
                
        int numberOfGroups = TaskSet.GROUPS_PER_SET;        
        
        panelOfPeriodic.setLayout(new GridLayout(numberOfGroups,0));

        String[] periodicColumnNames = {"Task", "Period", "Computation time", "Phase"};

        for (int i = 0; i < numberOfGroups; i++){
            int numberOfTasksCurrentGroup = Scheduler.getTaskSets().get(selectedSet).getGroup(i).getNumTasks();
            Object[][] data = new Object[numberOfTasksCurrentGroup][periodicColumnNames.length];
            for (int j = 0; j < numberOfTasksCurrentGroup; j++){
                data[j][0] = ((PeriodicTask)Scheduler.getTaskSets().get(selectedSet).getGroup(i).getTask(j)).getName();
                data[j][1] = ((PeriodicTask)Scheduler.getTaskSets().get(selectedSet).getGroup(i).getTask(j)).getPeriod();
                data[j][2] = ((PeriodicTask)Scheduler.getTaskSets().get(selectedSet).getGroup(i).getTask(j)).getComputationTime();
                data[j][3] = ((PeriodicTask)Scheduler.getTaskSets().get(selectedSet).getGroup(i).getTask(j)).getPhase();
            }
            DefaultTableModel model = new DefaultTableModel(data, periodicColumnNames);
            table = new JTable(model) {
                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return false;
                }
            };
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(100,100));
            table.setFillsViewportHeight(true);
            panelOfPeriodic.add(scrollPane);
            panelOfAperiodic.validate();
        }
    }
    void buildAperiodicTab(boolean generated){     
        
        
        btSave.setEnabled(generated);
        final int selectedSet = Integer.parseInt(rButtonGroup.getSelection().getActionCommand());        
        // Aperiòdiques
        panelOfAperiodic.removeAll();        
            
        panelOfAperiodic.setLayout(new BorderLayout());

        aperiodicButtonGroup = new ButtonGroup();
        JRadioButton automaticRadioButton = new JRadioButton("Automatic", false);
        automaticRadioButton.setActionCommand("Automatic");
        JRadioButton manualRadioButton = new JRadioButton("Manual", false);
        manualRadioButton.setActionCommand("Manual");


        aperiodicButtonGroup.add(automaticRadioButton);
        aperiodicButtonGroup.add(manualRadioButton);
        

        JButton genButton = new JButton("Gen");
        JPanel genPanel = new JPanel();

        JPanel radioButtonsPanel = new JPanel();

        radioButtonsPanel.add(automaticRadioButton);
        radioButtonsPanel.add(manualRadioButton);
        genPanel.add(radioButtonsPanel);
        genPanel.add(genButton);
        genButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {                    
                generateAperiodicTasks(selectedSet);
            }
        });

        panelOfAperiodic.add(genPanel, BorderLayout.PAGE_START);

        String[] aperiodicColumnNames = {"Task", "Arrival", "Computation time"};

        if(!generated)
            aperiodicInfo = new AperiodicInfo(Scheduler.getAperiodicInfo());
        
        switch (aperiodicInfo.getMode()){
            case MANUAL:
                manualRadioButton.setSelected(true);
                btSave.setEnabled(false);
                break;
            case NONE:
                automaticRadioButton.setSelected(true);
                btSave.setEnabled(false);
                break;
            case AUTO:
                automaticRadioButton.setSelected(true);
                btSave.setEnabled(true);
                break;
        }

        switch(aperiodicInfo.getMode()){
            case AUTO:
                JPanel groupsPanel = new JPanel();
                groupsPanel.setLayout(new GridLayout(aperiodicInfo.getAperiodicLoads().length * aperiodicInfo.getAperiodicMeanServiceTimes().length,0));
                AperiodicTask aperiodicTask;
                for (int i = 0; i < aperiodicInfo.getAperiodicLoads().length; i++){
                    for(int j = 0; j < aperiodicInfo.getAperiodicMeanServiceTimes().length; j++){
                        int numberOfTasksCurrentGroup = aperiodicInfo.getAperiodicTaskGroups()[i][j].getNumTasks();
                        Object[][] data = new Object[numberOfTasksCurrentGroup][aperiodicColumnNames.length];
                        for (int k = 0; k < numberOfTasksCurrentGroup; k++){
                            aperiodicTask = (AperiodicTask) aperiodicInfo.getAperiodicTaskGroups()[i][j].getTask(k);
                            for (int l = 0; l < aperiodicColumnNames.length; l++){
                                data[k][l] = new Object();                                    
                                switch(aperiodicColumnNames[l]){
                                    case("Task"):
                                        data[k][l] = aperiodicTask.getName();
                                        break;
                                    case("Arrival"):
                                        data[k][l] = String.valueOf(aperiodicTask.getArrivalTime());
                                        break;    
                                    case("Computation time"):
                                        data[k][l] = String.valueOf(aperiodicTask.getComputationTime());
                                        break;
                                    /*case("End"):
                                        data[k][l] = String.valueOf(aperiodicTask.getDeadline());
                                        break;*/
                                }
                            }
                        }
                        DefaultTableModel model = new DefaultTableModel(data, aperiodicColumnNames);
                        table = new JTable(model) {
                            @Override
                            public boolean isCellEditable(int rowIndex, int colIndex) {
                                return false;
                            }
                        };
                        JScrollPane scrollPane = new JScrollPane(table);
                        scrollPane.setPreferredSize(new Dimension(100,100));
                        table.setFillsViewportHeight(true);                        
                        groupsPanel.add(scrollPane);
                        panelOfAperiodic.add(groupsPanel, BorderLayout.CENTER);
                    }
                }
                break;
            case MANUAL:
                //Hay un solo grupo y está en la posición [0][0] del array de grupos
                int numberOfTasksCurrentGroup = aperiodicInfo.getAperiodicTaskGroups()[0][0].getNumTasks();
                    Object[][] data = new Object[numberOfTasksCurrentGroup][aperiodicColumnNames.length];
                    for (int j = 0; j < numberOfTasksCurrentGroup; j++){
                        aperiodicTask = (AperiodicTask) aperiodicInfo.getAperiodicTaskGroups()[0][0].getTask(j);
                        for (int k = 0; k < aperiodicColumnNames.length; k++){
                            data[j][k] = new Object();
                            switch(aperiodicColumnNames[k]){
                                case("Task"):
                                    data[j][k] = aperiodicTask.getName();
                                    break;
                                case("Arrival"):
                                    data[j][k] = String.valueOf(aperiodicTask.getArrivalTime());
                                    break;    
                                case("Computation time"):
                                    data[j][k] = String.valueOf(aperiodicTask.getComputationTime());
                                    break;
                                /*case("End"):
                                    data[j][k] = String.valueOf(aperiodicTask.getDeadline());
                                    break;*/
                            }
                        }
                    }
                    DefaultTableModel model = new DefaultTableModel(data, aperiodicColumnNames);
                    model.addTableModelListener(new TableModelListener() {

                        @Override
                        public void tableChanged(TableModelEvent e) {
                            btSave.setEnabled(true);
                        }
                    });
                    table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(100,200));
                    table.setFillsViewportHeight(true);
                    panelOfAperiodic.add(scrollPane, BorderLayout.CENTER);

                    JPanel OptionsPanel = new JPanel();

                    JButton addTaskButton = new JButton("Add task");
                    JButton deleteTaskButton = new JButton("Delete task");                        

                    addTaskButton.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            addTask();
                        }
                    });
                    deleteTaskButton.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            deleteTask();
                        }
                    });                        

                    OptionsPanel.add(addTaskButton);
                    OptionsPanel.add(deleteTaskButton);                        

                    panelOfAperiodic.add(OptionsPanel, BorderLayout.PAGE_END);
                break;
            case NONE:
                JLabel label = new JLabel("No tasks");
                label.setHorizontalAlignment(SwingConstants.CENTER);                
                panelOfAperiodic.add(label, BorderLayout.CENTER);
                break;
        }
        panelOfAperiodic.validate();

    }
    
    private void addTask(){
        System.out.println("Afegir tasca aperiodica");                                
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] rowToAdd = {defaultAperiodicTask.getName(), defaultAperiodicTask.getArrivalTime(), defaultAperiodicTask.getComputationTime(), defaultAperiodicTask.getDeadline()};
        model.addRow(rowToAdd);
        btSave.setEnabled(true);
    }
    
    private void deleteTask(){
        System.out.println("Eliminar tasca aperiodica" + table.getSelectedRow());                                
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            model.removeRow(rows[i]-i);
        }
        btSave.setEnabled(true);
    }
    
    private void generateAperiodicTasks(int selectedSet){          
        if (aperiodicButtonGroup.getSelection() != null){
            System.out.println(aperiodicButtonGroup.getSelection().getActionCommand());
            
            int aperiodicTasksPerGroup;
            int numAperiodicLoads;
            double[] aperiodicLoads;                        
            int numAperiodicMeanServiceTimes;
            double[] aperiodicMeanServiceTimes;
            
            switch (aperiodicButtonGroup.getSelection().getActionCommand()) {
                case "Automatic":                    
                    aperiodicTasksPerGroup = 10;                                
                    numAperiodicLoads = 5;
                    aperiodicLoads = new double[numAperiodicLoads];
                    double residualAperiodicLoad = Scheduler.getMAX_CPU_UTILIZATION() - Scheduler.getTaskSets().get(selectedSet).getTotalPeriodicLoad();
                    double aperiodicLoadIntervalSize = residualAperiodicLoad / (int)numAperiodicLoads;
                    numAperiodicMeanServiceTimes = 4;
                    aperiodicMeanServiceTimes = new double[numAperiodicMeanServiceTimes];
                    aperiodicMeanServiceTimes[0] = 0.55;
                    aperiodicMeanServiceTimes[1] = 1.10;
                    aperiodicMeanServiceTimes[2] = 2.75;
                    aperiodicMeanServiceTimes[3] = 5.5;
                    AperiodicTaskGroup[][] aperiodicTaskGroups = new AperiodicTaskGroup[numAperiodicLoads][numAperiodicMeanServiceTimes];
                    for(int i = 0; i < numAperiodicLoads; i++){
                        aperiodicLoads[i] = aperiodicLoadIntervalSize * (i + 1);
                        for(int j = 0; j < numAperiodicMeanServiceTimes; j++){                        
                            aperiodicTaskGroups[i][j] = new AperiodicTaskGroup(aperiodicMeanServiceTimes[j], aperiodicLoads[i], Scheduler.getTaskSets().get(selectedSet).calculateMaxHiperperiod() * Scheduler.HIPERPERIOD_TIMES_TO_SCHEDULE);                
                        }                    
                    }
                    aperiodicInfo = new AperiodicInfo(aperiodicTaskGroups, Scheduler.aperiodicGenerationMode.AUTO, aperiodicMeanServiceTimes, aperiodicLoads);                    
                    break;
                case "Manual":
                    numAperiodicLoads = 1;
                    aperiodicLoads = new double[numAperiodicLoads];
                    numAperiodicMeanServiceTimes = 1;
                    aperiodicMeanServiceTimes = new double[numAperiodicMeanServiceTimes];                                        
                    aperiodicTaskGroups = new AperiodicTaskGroup[1][1];
                    aperiodicTaskGroups[0][0] = new AperiodicTaskGroup();
                    //aperiodicTaskGroups[0][0].addTask(defaultAperiodicTask);
                    aperiodicInfo = new AperiodicInfo(aperiodicTaskGroups, Scheduler.aperiodicGenerationMode.MANUAL, aperiodicMeanServiceTimes, aperiodicLoads);
                    break;
            }
            buildAperiodicTab(true);
        }
    }
    
    private void fileSaveResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveResultsActionPerformed
        if(Scheduler.getResult() == null)
            JOptionPane.showMessageDialog(null, "No hay resultados que mostrar.", "NO RESULTADOS", JOptionPane.INFORMATION_MESSAGE);
        else            
        resultWindow.setVisible(true);
    }//GEN-LAST:event_fileSaveResultsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(Scheduler.getAperiodicInfo().getMode() == Scheduler.aperiodicGenerationMode.NONE)
            JOptionPane.showMessageDialog(null, "No hay tareas aperiódicas creadas.", "NO TAREAS APERIÓDICAS", JOptionPane.ERROR_MESSAGE);
        else 
            Scheduler.scheduleTaskSet(selectedSet);        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tasksWindow.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Result result = Scheduler.getResult();
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        //In response to a button click:
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                Exporter.exportResultToOds(fc.getSelectedFile().getAbsolutePath(), result);
            } catch (IOException ex) {

            }
        }





    }//GEN-LAST:event_saveButtonActionPerformed

    private void btSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSaveMouseClicked
        System.out.println("Guardar cambios");        
        switch(aperiodicInfo.getMode()){
            case AUTO:
                Scheduler.setAperiodicInfo(aperiodicInfo);
                break;
            case MANUAL:                
                aperiodicInfo.getAperiodicTaskGroups()[0][0] = new AperiodicTaskGroup();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++){
                    String name = (String) model.getValueAt(i, 0);
                    Object o = model.getValueAt(i, 1);
                    String s = o.toString();
                    Integer arrival = Integer.valueOf(s);                    
                    o = model.getValueAt(i, 2);
                    s = o.toString();
                    int computationTime = Integer.valueOf(s);                                        
                    aperiodicInfo.getAperiodicTaskGroups()[0][0].addTask(new AperiodicTask(name, arrival, computationTime));
                    System.out.println("Tarea: "+arrival + " " + computationTime);
                }
                
                if (model.getRowCount()==0){
                    aperiodicInfo = new AperiodicInfo();
                    Scheduler.setAperiodicInfo(aperiodicInfo);
                    
                }
                else{    
                    aperiodicInfo.getAperiodicMeanServiceTimes()[0] = 0;
                    aperiodicInfo.getAperiodicLoads()[0] = 0;                        
                    Scheduler.setAperiodicInfo(aperiodicInfo);                    
                }
                break;
        }
        btSave.setEnabled(false);
    }//GEN-LAST:event_btSaveMouseClicked

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged
        JTabbedPane tp = (JTabbedPane)evt.getSource();
        int index = tp.getSelectedIndex();
        if(index == 0)
            btSave.setVisible(false);
        else
            btSave.setVisible(true);
    }//GEN-LAST:event_jTabbedPane2StateChanged

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
    private javax.swing.JProgressBar barra;
    private javax.swing.JButton btSave;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JMenuItem fileImportData;
    private javax.swing.JMenuItem fileSaveResults;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenu menuFile;
    private javax.swing.JPanel panelOfAperiodic;
    private javax.swing.JPanel panelOfPeriodic;
    private javax.swing.JFrame progressBar;
    private javax.swing.JFrame resultWindow;
    private javax.swing.JButton saveButton;
    private javax.swing.JFrame tasksWindow;
    private javax.swing.JPanel textPanel;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa el planificador de los conjuntos de tareas. Planifica el conjunto que seleccionemos 
 * y genera los datos que se esperan: tiempo medio de respuesta en función de carga total y servidor y número de tareas periódicas que no cumplen
 * su terminio de todos los grupos.
 * @author Juanito
 */
public class Scheduler {

    /**
     * @return the result
     */
    public static Result getResult() {
        return result;
    }
    
    public enum aperiodicGenerationMode{
        NONE, AUTO, MANUAL;
    }
    
    public static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 1;    
    
    private static JProgressBar barra;
    public static final int NUM_SERVERS = 5;
    private static AperiodicInfo aperiodicInfo = new AperiodicInfo();
    private static int numAperiodicMeanServiceTimes;
    private static JFrame progressBar;
    //private static int[] aperiodicMeanServiceTimes;
    private static int numAperiodicLoads;
    //private static int[] aperiodicLoads;
    private static ArrayList<Server[]> servers;    
    //private static AperiodicTaskGroup[][] aperiodicTaskGroups;    
    private static Summary[][][][] summaries;  
    private static ArrayList<TaskSet> taskSets;        
    private static int numTaskSetToSchedule;
    private static final double MAX_CPU_UTILIZATION = 0.9;    
    private static Result result;
    
    //Chapuza al canto:
    private static JFrame resultWindow;
    private static JPanel resultsPanel;                
    
    private static Runnable runnable = new Runnable() {

        @Override
        public void run() {            
            barra.getModel().setMaximum(NUM_SERVERS*TaskSet.GROUPS_PER_SET*numAperiodicMeanServiceTimes*numAperiodicLoads);
            barra.getModel().setMinimum(0);
            progressBar.setVisible(true);
            int timeToSchedule;
            TaskSet taskSetToSchedule = taskSets.get(numTaskSetToSchedule);
            PeriodicTaskGroup periodicTaskGroupToSchedule;
            AperiodicTaskGroup aperiodicTaskGroup;
            result = new Result();
            double responseTimeByMeanServiceTime = 0;
            double responseTimeByGroup = 0;
            
            int iterations = 0;
            barra.setValue(iterations);
            for(int i = 0; i < NUM_SERVERS; i++){                                                                                                        
                for(int j = 0; j < numAperiodicLoads; j++){
                    responseTimeByGroup = 0;
                    for(int k = 0; k < TaskSet.GROUPS_PER_SET; k++){                        
                        periodicTaskGroupToSchedule = taskSetToSchedule.getGroup(k);
                        timeToSchedule = HIPERPERIOD_TIMES_TO_SCHEDULE*periodicTaskGroupToSchedule.calculateHiperperiod();
                        responseTimeByMeanServiceTime = 0;                    
                            for(int l = 0; l < numAperiodicMeanServiceTimes; l++){                                
                                aperiodicTaskGroup = aperiodicInfo.getAperiodicTaskGroups()[j][l];
                                Server.setAperiodicTaskGroup(aperiodicTaskGroup);                                
                                summaries[i][k][l][j] = new Summary(timeToSchedule);                
                                summaries[i][k][l][j].schedulePeriodicTaskGroup(periodicTaskGroupToSchedule, servers.get(numTaskSetToSchedule)[i]);  
                                responseTimeByMeanServiceTime += summaries[i][k][l][j].getAverageAperiodicResponseTime();
                                iterations++;
                                barra.setValue(iterations);
                            }                                                
                            responseTimeByMeanServiceTime /= numAperiodicMeanServiceTimes;
                            responseTimeByGroup += responseTimeByMeanServiceTime;
                    }
                    responseTimeByGroup /= TaskSet.GROUPS_PER_SET;
                    result.addData(taskSetToSchedule.getTotalPeriodicLoad() + aperiodicInfo.getAperiodicLoads()[j], servers.get(numTaskSetToSchedule)[i].getName(), responseTimeByGroup);
                }                                
            }
                        
            JTable table;            
            ArrayList<String> serverNamesInResult = result.getServerNamesInResult();
            ArrayList<Double> loadsInResult       = result.getLoadsInResult();

            String[] columnNames = new String[serverNamesInResult.size() + 1];
            columnNames[0] = "Aperiodic Load";
            Object[][] data = new Object[loadsInResult.size()][serverNamesInResult.size() + 1];
            
            for (int i=0;i<serverNamesInResult.size();i++){
                columnNames[i+1] = serverNamesInResult.get(i);
            }
            
            for(int i = 0; i <loadsInResult.size(); i++)
                for(int j = 0; j < serverNamesInResult.size() + 1; j++) {
                    data[i][j] = new Object();
                    if(j == 0)
                        data[i][j] = DoubleToString(loadsInResult.get(i));
                    else
                        data[i][j] = DoubleToString(result.getData(loadsInResult.get(i), serverNamesInResult.get(j-1)));
                }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table = new JTable(model) {
                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return false;
                }
            };
            JScrollPane scrollPane = new JScrollPane(table);            
            table.setFillsViewportHeight(true);                
            
            resultsPanel.setLayout(new BorderLayout());
            resultsPanel.add(scrollPane, BorderLayout.CENTER);            
            resultsPanel.validate();
                
        progressBar.setVisible(false);
        resultWindow.setVisible(true);
        }
    };
    
    private static String DoubleToString(double d){
        String decimals = Integer.toString(Math.abs((int)(d*100)%100));
        String intpart = Integer.toString((int) d);
        return intpart + "," + decimals;       
    }
    
    /**
     * Método que carga los datos del fichero de entrada. 
     * Separa las tareas periódicas y los servidores, los cuales mete en el array de servidores. 
     * Tener en cuenta que el Servidor Background no está en el fichero porque sus parámetros (capacidad y periodo) son constantes.
     * @param urlFile Url del fichero con las tareas periódicas a importar.
     */
    public static void importTaskSets(String urlFile){     
        taskSets = new ArrayList<>();
        servers  = new ArrayList<>();
        Importer.importTaskSets(urlFile, taskSets, servers);
        
    }
    
    /**
     * Método con el cual se realiza la planificación de todos los grupos de tareas del conjunto especificado.
     * @param taskSetToSchedule Índice del conjunto de tareas a planificar.
     * @param numAperiodicTasks El número de tareas aperiódicas a crear
     * @mode Modo de creación de las tareas aperiódicas: manual o automática
     */
    public static void scheduleTaskSet(int taskSetToSchedule){
        /*if(aperiodicInfo.getMode().equals(aperiodicGenerationMode.NONE))
            throw new IllegalStateException("No hay tareas aperiódicas creadas.");*/
        summaries = new Summary[NUM_SERVERS][TaskSet.GROUPS_PER_SET][numAperiodicMeanServiceTimes][numAperiodicLoads];
        Scheduler.numTaskSetToSchedule = taskSetToSchedule;
        Thread thread = new Thread(runnable);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
        
    
    /**
     * @return the taskSets
     */
    public static ArrayList<TaskSet> getTaskSets() {
        return taskSets;
    }

    /**
     * @param aTaskSets the taskSets to set
     */
    public static void setTaskSets(ArrayList<TaskSet> aTaskSets) {
        taskSets = aTaskSets;
    }

    /**
     * @return La info aperiódica de la simulación.
     */
    public static AperiodicInfo getAperiodicInfo() {
        return aperiodicInfo;
    }

    /**
     * @param aperiodicInfo La info aperiódica a asignar para la simulación.
     */
    public static void setAperiodicInfo(AperiodicInfo aperiodicInfo) {
        Scheduler.aperiodicInfo = aperiodicInfo;
        if(aperiodicInfo.getAperiodicLoads() != null && aperiodicInfo.getAperiodicMeanServiceTimes() != null && aperiodicInfo.getAperiodicTaskGroups() != null){          
            Scheduler.numAperiodicMeanServiceTimes = aperiodicInfo.getAperiodicMeanServiceTimes().length;
            Scheduler.numAperiodicLoads = aperiodicInfo.getAperiodicLoads().length;
        }
        else{
            Scheduler.numAperiodicMeanServiceTimes = 0;
            Scheduler.numAperiodicLoads = 0;
        }
    }
    /**
     * @return La utilización máxima de la CPU.
     */
    public static double getMAX_CPU_UTILIZATION() {
        return MAX_CPU_UTILIZATION;
    }
    
    //Chapuza al canto:
    public static void setResultWindow(JFrame window, JPanel panel, JFrame progressBar, JProgressBar barra){
        resultWindow = window;
        resultsPanel = panel;
        Scheduler.progressBar = progressBar;
        Scheduler.barra       = barra;        
    }

}

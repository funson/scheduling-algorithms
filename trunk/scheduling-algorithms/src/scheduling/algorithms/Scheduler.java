/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
    
    private static final int NUM_SERVERS = 5;
    private static AperiodicInfo aperiodicInfo = new AperiodicInfo();
    private static int numAperiodicMeanServiceTimes;
    //private static double[] aperiodicMeanServiceTimes;
    private static int numAperiodicLoads;
    //private static double[] aperiodicLoads;
    private static ArrayList<Server[]> servers;    
    //private static AperiodicTaskGroup[][] aperiodicTaskGroups;
    private static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 20;    
    private static Summary[][][][] summaries;  
    private static ArrayList<TaskSet> taskSets;        
    private static int numTaskSetToSchedule;
    private static final double MAX_CPU_UTILIZATION = 0.9;
    //Chapuza al canto:
    private static JFrame resultWindow;
    private static JTextArea resultsTextArea;
    
    private static Result result;
    
    private static Runnable runnable = new Runnable() {

        @Override
        public void run() {        
            float timeToSchedule;
            TaskSet taskSetToSchedule = taskSets.get(numTaskSetToSchedule);
            PeriodicTaskGroup periodicTaskGroupToSchedule;
            AperiodicTaskGroup aperiodicTaskGroup;
            result = new Result();
            float responseTimeByMeanServiceTime = 0;
            float responseTimeByGroup = 0;            
            for(int i = 0; i < NUM_SERVERS; i++){                                                                                        
                responseTimeByGroup = 0;
                for(int j = 0; j < numAperiodicLoads; j++){
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
                            }                                                
                            responseTimeByMeanServiceTime /= numAperiodicMeanServiceTimes;
                            responseTimeByGroup += responseTimeByMeanServiceTime;
                    }
                    responseTimeByGroup /= TaskSet.GROUPS_PER_SET;
                    getResult().addData(taskSetToSchedule.getTotalPeriodicLoad() + aperiodicInfo.getAperiodicLoads()[j], servers.get(numTaskSetToSchedule)[i].getName(), responseTimeByGroup);
                }                
            }
            
            Result result = getResult();

            String output = "";
            ArrayList<String> serverNamesInResult = result.getServerNamesInResult();
            ArrayList<Double> loadsInResult       = result.getLoadsInResult();

            for (int i=0;i<serverNamesInResult.size();i++){
                    output+= " \t" + serverNamesInResult.get(i);
            }
            output+="\n";

        for (int i=0;i<loadsInResult.size();i++){
                    output+= Double.toString(loadsInResult.get(i));
                    for (int j=0;j<serverNamesInResult.size();j++){
                        output+="\t" + result.getData(loadsInResult.get(i), serverNamesInResult.get(j));
                    }
                    output+="\n";
        }       
        resultWindow.setVisible(true);
        resultsTextArea.setText(output);
        }
    };
    
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
     * Método PROVISIONAL para generar tareas aperiódicas automáticamente, para poder hacer pruebas mientras la GUI Tareas no esté terminada.
     * @param aperiodicTasksPerGroup
     * @param taskSet
     */
    public static void generateAperiodicTaskGroups(int aperiodicTasksPerGroup, int taskSet){
        numAperiodicLoads = 5;
        double[] aperiodicLoads = new double[numAperiodicLoads];
        double residualAperiodicLoad = MAX_CPU_UTILIZATION - taskSets.get(taskSet).getTotalPeriodicLoad();
        double aperiodicLoadIntervalSize = residualAperiodicLoad / (double)numAperiodicLoads;
        numAperiodicMeanServiceTimes = 4;
        double[] aperiodicMeanServiceTimes = new double[numAperiodicMeanServiceTimes];
        aperiodicMeanServiceTimes[0] = 0.55;
        aperiodicMeanServiceTimes[1] = 1.10;
        aperiodicMeanServiceTimes[2] = 2.75;
        aperiodicMeanServiceTimes[3] = 5.5;
        AperiodicTaskGroup[][] aperiodicTaskGroups = new AperiodicTaskGroup[numAperiodicLoads][numAperiodicMeanServiceTimes];
        for(int i = 0; i < numAperiodicLoads; i++){
            aperiodicLoads[i] = aperiodicLoadIntervalSize * i;
            for(int j = 0; j < numAperiodicMeanServiceTimes; j++){                        
                aperiodicTaskGroups[i][j] = new AperiodicTaskGroup(aperiodicTasksPerGroup, aperiodicMeanServiceTimes[j], aperiodicLoads[i]);                
            }                    
        }
        aperiodicInfo = new AperiodicInfo(aperiodicTaskGroups, aperiodicGenerationMode.AUTO, aperiodicMeanServiceTimes, aperiodicLoads);
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
        
        new Thread(runnable).start();
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
    public static void setResultWindow(JFrame window, JTextArea txtArea){
        resultWindow = window;
        resultsTextArea = txtArea;
    }

}

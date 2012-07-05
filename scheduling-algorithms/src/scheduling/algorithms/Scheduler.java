/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que representa el planificador de los conjuntos de tareas. Planifica el conjunto que seleccionemos 
 * y genera los datos que se esperan: tiempo medio de respuesta y número de tareas periódicas que no cumplen 
 * su terminio de todos los grupos.
 * @author Juanito
 */
public class Scheduler {
    
    public enum aperiodicGenerationMode{
        NONE, AUTO, MANUAL;
    }
    
    private static final int NUM_SERVERS = 5;    
    private static int numAperiodicMeanServiceTimes;
    private static double[] aperiodicMeanServiceTimes;    
    private static int numAperiodicLoads;
    private static double[] aperiodicLoads;
    private static ArrayList<Server[]> servers;    
    private static AperiodicTaskGroup[][] aperiodicTaskGroups;
    private static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 20;    
    private static Summary[][][][] summaries;  
    private static ArrayList<TaskSet> taskSets;        
    private static int numTaskSetToSchedule;
    private static final double MAX_CPU_UTILIZATION = 0.9;
    private static aperiodicGenerationMode mode = aperiodicGenerationMode.NONE;
    
    private static Runnable runnable = new Runnable() {

        @Override
        public void run() {        
            float timeToSchedule;
            TaskSet taskSetToSchedule = taskSets.get(numTaskSetToSchedule);
            PeriodicTaskGroup periodicTaskGroupToSchedule;
            AperiodicTaskGroup aperiodicTaskGroup;
            Result result = new Result();
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
                                aperiodicTaskGroup = aperiodicTaskGroups[j][l];                                
                                Server.setAperiodicTaskGroup(aperiodicTaskGroup);                                
                                summaries[i][k][l][j] = new Summary(timeToSchedule);                
                                summaries[i][k][l][j].schedulePeriodicTaskGroup(periodicTaskGroupToSchedule, servers.get(numTaskSetToSchedule)[i]);  
                                responseTimeByMeanServiceTime += summaries[i][k][l][j].getAverageAperiodicResponseTime();
                            }                                                
                            responseTimeByMeanServiceTime /= numAperiodicMeanServiceTimes;
                            responseTimeByGroup += responseTimeByMeanServiceTime;
                    }
                    responseTimeByGroup /= TaskSet.GROUPS_PER_SET;
                    result.addData(taskSetToSchedule.getTotalPeriodicLoad() + aperiodicLoads[j], servers.get(numTaskSetToSchedule)[i].getName(), responseTimeByGroup);
                }                
            }                        
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
    
    public static void generateAperiodicTaskGroups(int aperiodicTasksPerGroup, int taskSet){
        numAperiodicLoads = 5;
        aperiodicLoads = new double[numAperiodicLoads];
        double residualAperiodicLoad = MAX_CPU_UTILIZATION - taskSets.get(taskSet).getTotalPeriodicLoad();
        double aperiodicLoadIntervalSize = residualAperiodicLoad / (double)numAperiodicLoads;
        numAperiodicMeanServiceTimes = 4;
        aperiodicMeanServiceTimes = new double[numAperiodicMeanServiceTimes];
        aperiodicMeanServiceTimes[0] = 0.55;
        aperiodicMeanServiceTimes[1] = 1.10;
        aperiodicMeanServiceTimes[2] = 2.75;
        aperiodicMeanServiceTimes[3] = 5.5;
        aperiodicTaskGroups = new AperiodicTaskGroup[numAperiodicLoads][numAperiodicMeanServiceTimes];
        for(int i = 0; i < numAperiodicLoads; i++){
            aperiodicLoads[i] = aperiodicLoadIntervalSize * i;
            for(int j = 0; j < numAperiodicMeanServiceTimes; j++){                        
                aperiodicTaskGroups[i][j] = new AperiodicTaskGroup(aperiodicTasksPerGroup, aperiodicMeanServiceTimes[j], aperiodicLoads[i]);                
            }                    
        } 
    }
    
    public static void setAperiodicTaskGroup(AperiodicTaskGroup aperiodicTaskGroup){
        aperiodicTaskGroups[0][0] = aperiodicTaskGroup;
        numAperiodicLoads = 1;
        aperiodicLoads = new double[numAperiodicLoads];
        numAperiodicMeanServiceTimes = 1;
        aperiodicMeanServiceTimes = new double[numAperiodicMeanServiceTimes];            
        AperiodicTask currentAperiodicTask;
        AperiodicTask previousAperiodicTask = (AperiodicTask) aperiodicTaskGroups[0][0].getTask(0);
        aperiodicMeanServiceTimes[0] = 0;
        double aperiodicMeanTimeBetweenArrivals = 0;
        int manualNumAperiodicTasks = aperiodicTaskGroups[0][0].taskGroup.size();
        for(int i = 1; i < manualNumAperiodicTasks; i++){
            currentAperiodicTask = (AperiodicTask) aperiodicTaskGroups[0][0].getTask(i);
            aperiodicMeanServiceTimes[0] += currentAperiodicTask.getComputationTime() / manualNumAperiodicTasks;
            aperiodicMeanTimeBetweenArrivals += (currentAperiodicTask.getArrivalTime() - previousAperiodicTask.getArrivalTime()) / (manualNumAperiodicTasks - 1);
            previousAperiodicTask = currentAperiodicTask;
        }
        aperiodicLoads[0] = aperiodicMeanServiceTimes[0] / aperiodicMeanTimeBetweenArrivals;
    }
    
    /**
     * Método con el cual se realiza la planificación de todos los grupos de tareas del conjunto especificado.
     * @param taskSetToSchedule Índice del conjunto de tareas a planificar.
     * @param numAperiodicTasks El número de tareas aperiódicas a crear
     * @mode Modo de creación de las tareas aperiódicas: manual o automática
     */
    public static void scheduleTaskSet(int taskSetToSchedule){                             
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
     * @return the aperiodicTaskGroups
     */
    public static AperiodicTaskGroup[][] getAperiodicTaskGroups() {
        return aperiodicTaskGroups;
    }

    /**
     * @param aAperiodicTaskGroups the aperiodicTaskGroups to set
     */
    public static void setUp(AperiodicTaskGroup[][] aperiodicTaskGroups, aperiodicGenerationMode mode, double[] aperiodicMeanServiceTimes, double[] aperiodicLoads) {
        Scheduler.aperiodicTaskGroups = aperiodicTaskGroups;
        Scheduler.mode = mode;
        Scheduler.aperiodicMeanServiceTimes = aperiodicMeanServiceTimes;
        Scheduler.numAperiodicMeanServiceTimes = aperiodicMeanServiceTimes.length;
        Scheduler.aperiodicLoads = aperiodicLoads;
        Scheduler.numAperiodicLoads = aperiodicLoads.length;
    }
        
}

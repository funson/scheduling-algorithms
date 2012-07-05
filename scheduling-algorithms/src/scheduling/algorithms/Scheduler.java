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
    
    public enum AperiodicTaskCreationMode {
        MANUAL, AUTO;
    }
    
    private static final int NUM_SERVERS = 5;
    private static int NUM_APERIODIC_MEAN_SERVICE_TIMES;
    private static double[] APERIODIC_MEAN_SERVICE_TIMES;
    private static int NUM_APERIODIC_LOADS;
    private static double[] APERIODIC_LOADS;
    private static ArrayList<Server[]> servers;
    private static AperiodicTaskCreationMode mode;
    private static AperiodicTaskGroup[][] aperiodicTaskGroups;    
    private static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 20;    
    private static Summary[][][][] summaries;  
    private static ArrayList<TaskSet> taskSets;        
    private static int numTaskSetToSchedule;
    private static final double MAX_CPU_UTILIZATION = 0.9;
    
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
                for(int j = 0; j < NUM_APERIODIC_LOADS; j++){
                    for(int k = 0; k < TaskSet.GROUPS_PER_SET; k++){                        
                        periodicTaskGroupToSchedule = taskSetToSchedule.getGroup(k);
                        timeToSchedule = HIPERPERIOD_TIMES_TO_SCHEDULE*periodicTaskGroupToSchedule.calculateHiperperiod();
                        responseTimeByMeanServiceTime = 0;                    
                            for(int l = 0; l < NUM_APERIODIC_MEAN_SERVICE_TIMES; l++){                                
                                aperiodicTaskGroup = aperiodicTaskGroups[j][l];                                
                                Server.setAperiodicTaskGroup(aperiodicTaskGroup);                                
                                summaries[i][k][l][j] = new Summary(timeToSchedule);                
                                summaries[i][k][l][j].schedulePeriodicTaskGroup(periodicTaskGroupToSchedule, servers.get(numTaskSetToSchedule)[i]);  
                                responseTimeByMeanServiceTime += summaries[i][k][l][j].getAverageAperiodicResponseTime();
                            }                                                
                            responseTimeByMeanServiceTime /= NUM_APERIODIC_MEAN_SERVICE_TIMES;
                            responseTimeByGroup += responseTimeByMeanServiceTime;
                    }
                    responseTimeByGroup /= TaskSet.GROUPS_PER_SET;
                    result.addData(taskSetToSchedule.getTotalPeriodicLoad() + APERIODIC_LOADS[j], servers.get(numTaskSetToSchedule)[i].getName(), responseTimeByGroup);
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
        Importer.importTaskSets(urlFile, taskSets, servers);
    }    
    
    /**
     * Método con el cual se realiza la planificación de todos los grupos de tareas del conjunto especificado.
     * @param taskSetToSchedule Índice del conjunto de tareas a planificar.
     * @param numAperiodicTasks El número de tareas aperiódicas a crear
     * @mode Modo de creación de las tareas aperiódicas: manual o automática
     */
    public static void scheduleTaskSet(int taskSetToSchedule, int numAperiodicTasks, AperiodicTaskCreationMode mode){
        Scheduler.mode = mode;
        if(mode.equals(AperiodicTaskCreationMode.AUTO)){
            NUM_APERIODIC_LOADS = 5;
            APERIODIC_LOADS = new double[NUM_APERIODIC_LOADS];
            double residualAperiodicLoad = MAX_CPU_UTILIZATION - taskSets.get(taskSetToSchedule).getTotalPeriodicLoad();
            double aperiodicLoadIntervalSize = residualAperiodicLoad / (double)NUM_APERIODIC_LOADS;
            NUM_APERIODIC_MEAN_SERVICE_TIMES = 4;
            APERIODIC_MEAN_SERVICE_TIMES = new double[NUM_APERIODIC_MEAN_SERVICE_TIMES];
            APERIODIC_MEAN_SERVICE_TIMES[0] = 0.55;
            APERIODIC_MEAN_SERVICE_TIMES[1] = 1.10;
            APERIODIC_MEAN_SERVICE_TIMES[2] = 2.75;
            APERIODIC_MEAN_SERVICE_TIMES[3] = 5.5;
            aperiodicTaskGroups = new AperiodicTaskGroup[NUM_APERIODIC_LOADS][NUM_APERIODIC_MEAN_SERVICE_TIMES];
            for(int i = 0; i < NUM_APERIODIC_LOADS; i++){
                APERIODIC_LOADS[i] = aperiodicLoadIntervalSize * i;
                for(int j = 0; j < NUM_APERIODIC_MEAN_SERVICE_TIMES; j++){                        
                    aperiodicTaskGroups[i][j] = new AperiodicTaskGroup(numAperiodicTasks, APERIODIC_MEAN_SERVICE_TIMES[i], APERIODIC_LOADS[j]);                
                }                    
            }                
        } else {
            //Si la creación del grupo de aperiódicas es manual, el grupo ya debe estar en la posición aperiodicTaskGroups[0][0]
            NUM_APERIODIC_LOADS = 1;
            APERIODIC_LOADS = new double[NUM_APERIODIC_LOADS];
            NUM_APERIODIC_MEAN_SERVICE_TIMES = 1;
            APERIODIC_MEAN_SERVICE_TIMES = new double[NUM_APERIODIC_MEAN_SERVICE_TIMES];            
            AperiodicTask currentAperiodicTask;
            AperiodicTask previousAperiodicTask = (AperiodicTask) aperiodicTaskGroups[0][0].getTask(0);
            APERIODIC_MEAN_SERVICE_TIMES[0] = 0;
            double aperiodicMeanTimeBetweenArrivals = 0;
            int manualNumAperiodicTasks = aperiodicTaskGroups[0][0].taskGroup.size();
            for(int i = 1; i < manualNumAperiodicTasks; i++){
                currentAperiodicTask = (AperiodicTask) aperiodicTaskGroups[0][0].getTask(i);
                APERIODIC_MEAN_SERVICE_TIMES[0] += currentAperiodicTask.getComputationTime() / manualNumAperiodicTasks;
                aperiodicMeanTimeBetweenArrivals += (currentAperiodicTask.getArrivalTime() - previousAperiodicTask.getArrivalTime()) / (manualNumAperiodicTasks - 1);
                previousAperiodicTask = currentAperiodicTask;
            }
            APERIODIC_LOADS[0] = APERIODIC_MEAN_SERVICE_TIMES[0] / aperiodicMeanTimeBetweenArrivals;
        }                                
        summaries = new Summary[NUM_SERVERS][TaskSet.GROUPS_PER_SET][NUM_APERIODIC_MEAN_SERVICE_TIMES][NUM_APERIODIC_LOADS];
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
        
}

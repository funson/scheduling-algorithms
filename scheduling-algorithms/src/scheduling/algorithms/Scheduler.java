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
    private static final double[] APERIODIC_MEAN_SERVICE_TIMES = {0.55, 1.10, 2.75, 5.5};
    private static final int NUM_APERIODIC_LOADS = 5;
    private static float[] APERIODIC_LOADS = new float[NUM_APERIODIC_LOADS];
    private static Server[] servers = new Server[NUM_SERVERS];
    private static int numAperiodicTaskGroups;
    private static AperiodicTaskGroup[] aperiodicTaskGroups = new AperiodicTaskGroup[APERIODIC_MEAN_SERVICE_TIMES.length];
    private static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 20;    
    private static Summary[][][][] summaries = new Summary[NUM_SERVERS][TaskSet.GROUPS_PER_SET][APERIODIC_MEAN_SERVICE_TIMES.length][NUM_APERIODIC_LOADS];  
    private static ArrayList<TaskSet> taskSets;        
    private static int numTaskSetToSchedule;
    
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
                for(int j = 0; j < NUM_APERIODIC_LOADS; j++){
                    servers[i].setLoad(APERIODIC_LOADS[j]);
                    responseTimeByGroup = 0;
                    for(int k = 0; k < TaskSet.GROUPS_PER_SET; k++){                        
                        periodicTaskGroupToSchedule = taskSetToSchedule.getGroup(k);
                        timeToSchedule = HIPERPERIOD_TIMES_TO_SCHEDULE*periodicTaskGroupToSchedule.calculateHiperperiod();
                        responseTimeByMeanServiceTime = 0;
                        for(int l = 0; l < numAperiodicTaskGroups; l++){
                            aperiodicTaskGroup = aperiodicTaskGroups[l];
                            Server.setAperiodicTaskGroup(aperiodicTaskGroup);                                
                            summaries[i][k][l][j] = new Summary(timeToSchedule);                
                            summaries[i][k][l][j].schedulePeriodicTaskGroup(periodicTaskGroupToSchedule, servers[i]);  
                            responseTimeByMeanServiceTime += summaries[i][k][l][j].getAverageAperiodicResponseTime();
                        }                                                
                        responseTimeByMeanServiceTime /= numAperiodicTaskGroups;
                        responseTimeByGroup += responseTimeByMeanServiceTime;
                    }
                    responseTimeByGroup /= TaskSet.GROUPS_PER_SET;
                    result.addData(taskSetToSchedule.getTotalPeriodicLoad() + APERIODIC_LOADS[j], servers[i].getName(), responseTimeByGroup);
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
        Importer.importTaskSets(urlFile, taskSets,servers);
    }    
    
    /**
     * Método con el cual se realiza la planificación de todos los grupos de tareas del conjunto especificado.
     * @param taskSetToSchedule Índice del conjunto de tareas a planificar.
     * @param numAperiodicTasks El número de tareas aperiódicas a crear
     * @mode Modo de creación de las tareas aperiódicas: manual o automática
     */
    public static void scheduleTaskSet(int taskSetToSchedule, int numAperiodicTasks, AperiodicTaskCreationMode mode){
        switch(mode){
            case AUTO:
                numAperiodicTaskGroups = APERIODIC_MEAN_SERVICE_TIMES.length;
                for(int i = 0; i < APERIODIC_MEAN_SERVICE_TIMES.length; i++)
                    aperiodicTaskGroups[i] = new AperiodicTaskGroup(numAperiodicTasks, APERIODIC_MEAN_SERVICE_TIMES[i]);                
                break;
            case MANUAL:
                numAperiodicTaskGroups = 1;
                break;
        }
       Scheduler.numTaskSetToSchedule = taskSetToSchedule;
       new Thread(runnable).start();
    }
        
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que representa el planificador de los conjuntos de tareas. Planifica el conjunto que seleccionemos y genera los datos que se esperan: tiempo medio de respuesta de todos los grupos y número de tareas periódicas que no cumplen su terminio.
 * @author Juanito
 */
public class Scheduler {

    private static final int NUM_SERVERS = 5;
    private static Server[] servers = new Server[NUM_SERVERS];
    private static final int HIPERPERIOD_TIMES_TO_SCHEDULE = 20;    
    private static Summary[][] summaries = new Summary[NUM_SERVERS][TaskSet.GROUPS_PER_SET];  
    private static ArrayList<TaskSet> taskSets;        
    private static int taskSetToSchedule;
    private static Runnable runnable = new Runnable() {

        @Override
        public void run() {        
            float timeToSchedule;          
            for(int i = 0; i < NUM_SERVERS; i++){
                for(int j = 0; j < TaskSet.GROUPS_PER_SET; j++){
                    PeriodicTaskGroup periodicTaskGroupToSchedule = taskSets.get(taskSetToSchedule).getGroup(j);                
                    timeToSchedule = HIPERPERIOD_TIMES_TO_SCHEDULE*periodicTaskGroupToSchedule.calculateHiperperiod();
                    summaries[i][j] = new Summary(timeToSchedule);                
                    summaries[i][j].schedulePeriodicTaskGroup(periodicTaskGroupToSchedule, servers[i]);
                }
            }
            
        }
    };
    
    /**
     * Método que carga los datos del fichero de entrada. Separa las tareas periódicas y los servidores, los cuales mete en el array de servidores. Tener en cuenta que el Servidor Background no está en el fichero porque sus parámetros (capacidad y periodo) son constantes.
     * @param urlFile 
     */
    public static void importTaskSets(String urlFile){
        servers[0] = new BackgroundServer();
        throw new UnsupportedOperationException("Import task sets not supported yet.");
        
    }
    
    public void scheduleTaskSet(int taskSetToSchedule){
       Scheduler.taskSetToSchedule = taskSetToSchedule;
       new Thread(runnable).start();
    }
    
}

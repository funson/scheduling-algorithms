/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que define un grupo de tareas aperiódicas.
 * Permite añadir, eliminar y obtener una tarea aperiódica del grupo que define;
 * así como ordenarlas por orden ascendente según su tiempo de llegada.
 * @author Juanito
 */
public class AperiodicTaskGroup extends TaskGroup {
    
    /**
     * Permite añadir una tarea aperiódica en el grupo.
     * En el caso de que pasasemos una tarea periódica, esta función lanzaría una Excepción.
     * @param task Tarea Aperiódica a añadir
     */
    @Override
    public void addTask(Task task){
        if(!(task instanceof AperiodicTask)){
            throw new UnsupportedOperationException("The task you are adding is not an aperiodic task.");
        }
        super.addTask(task);            
    }
    
    /**
     * Constructor para crear un grupo de tareas aperiódicas a partir de los parámetros especificados
     * @param numTasks El tamaño del grupo de tareas aperiódicas
     * @param aperiodicMeanServiceTime El tiempo medio de servicio, que repercute en el tiempo de comutación de la tarea
     */
    public AperiodicTaskGroup(int numTasks, double aperiodicMeanServiceTime, double aperiodicLoad){
        float timeBetweenArrivals, computationTime;
        AperiodicTask previousTask = new AperiodicTask("", 0, 0);
        AperiodicTask currentTask;
        for(int i = 0; i < numTasks; i++){
            timeBetweenArrivals = mathOperation.getPoisson(aperiodicMeanServiceTime * (1 / aperiodicLoad));
            computationTime = mathOperation.getExponential(aperiodicMeanServiceTime);            
            currentTask = new AperiodicTask("AP"+i, previousTask.getArrivalTime() + timeBetweenArrivals, computationTime);
            this.taskGroup.add(currentTask);
            previousTask = currentTask;
        }        
    }
    
    public AperiodicTaskGroup(){
        super();
    }
    
    /**
     * Método que ordena el grupo según su tiempo de llegada de forma ascendente.
     */
    public void sortByArrivalTime(){        
        Collections.sort(this.taskGroup);
    }
    
    /**
     * Método para eliminar la Tarea Aperiódica cuyo índice es el introducido por parámetro.
     * @param index Índice de la Tarea aperiódica a eliminar.
     */
    public void removeAperiodicTask(int index){
        taskGroup.remove(index);
    }
    
    /**
     * Método que devuelve una copia del grupo.
     * Se puede usar con las clases {@link PeriodicTaskGroup} y {@link AperiodicTaskGroup}, 
     * haciendo el correspondiente casting.
     * @return La copia del grupo.
     */

    @Override
    public AperiodicTaskGroup clone(){        
        AperiodicTaskGroup clone = new AperiodicTaskGroup();
        clone.taskGroup = (ArrayList<Task>)taskGroup.clone();
        //AperiodicTaskGroup clone = (AperiodicTaskGroup) super.clone();
        return clone;
    }
}

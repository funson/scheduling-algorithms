/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;

/**
 * Clase que define un grupo de tareas, tanto periódicas como aperiódicas.
 * @author Miguel
 */
public class TaskGroup {
        
    protected ArrayList<Task> taskGroup;        
    /**
     * 
     */
    public TaskGroup(){
        this.taskGroup = new ArrayList();        
    }
    
    /**
     * Método que devuelve la tarea que se encuentra en el índice especificado del grupo.     
     * @param numTask
     * @return 
     */
    public Task getTask(int numTask) {
        return this.taskGroup.get(numTask);
    }

    /**
     * Método para insertar una tarea en el grupo.
     * @param taskToInsert  La tarea a insertar.       
     */
    public void addTask(Task taskToInsert) {
        this.taskGroup.add(taskToInsert);
    }
    
    /**
     * Método que devuelve una copia del grupo.
     * Se puede usar con las clases {@link PeriodicTaskGroup} y {@link AperiodicTaskGroup}, 
     * haciendo el correspondiente casting.
     * @return La copia del grupo.
     */

    @Override
    public TaskGroup clone(){        
        TaskGroup clone = new TaskGroup();
        clone.taskGroup = (ArrayList<Task>)taskGroup.clone();
        return clone;
    }
}

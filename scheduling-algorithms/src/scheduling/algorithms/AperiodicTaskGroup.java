/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

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
}

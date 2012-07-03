/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Clase que describe un grupo de tareas periódicas.
 * Esta clase define los métodos para calcular el hiperperiodo de las tareas, 
 * para ordenar las tareas según su periodo y para obtener una copia.
 * @author Juanito
 */
public class PeriodicTaskGroup extends TaskGroup {
    
    /**
     * Permite añadir una tarea periódica en el grupo.
     * En el caso de que passasemos una tarea aperiódica, esta función lanzaría una Excepción.
     * @param task Tarea Periódica a añadir
     */
    @Override
    public void addTask(Task task){
        if(!(task instanceof PeriodicTask)){
            throw new UnsupportedOperationException("The task you are adding is not a periodic task.");
        }
        super.addTask(task);
            
    }
    
    /**
     * Función que calcula el hiperperiodo de todas las tareas periódicas del grupo.
     * Para su cálculo excluye a los servidores que haya en el grupo.
     * @return El hiperperiodo del conjunto de tareas periódicas. 
     */
    public float calculateHiperperiod(){
        final int MAX_DECIMAL_DIGITS = 5;        
        float T_i;
        int T_iFtoI;
        int hiperPeriod = 1;
        PeriodicTask periodicTask;
        Iterator<Task> taskIterator = taskGroup.iterator();
        while(taskIterator.hasNext()){
            periodicTask = (PeriodicTask) taskIterator.next();
            
            if (!(periodicTask instanceof Server)){
                T_i = periodicTask.getPeriod();
                T_iFtoI = (int) (T_i * Math.pow(10, MAX_DECIMAL_DIGITS));
                hiperPeriod = mathOperation.mcm(hiperPeriod, T_iFtoI);
            }
        }
        return (float)(hiperPeriod) / (float)(Math.pow(10, MAX_DECIMAL_DIGITS));
    }
    
    /**
     * Método que ordena el grupo de forma ascendente según el periodo de la tareas.
     * De esta manera tenemos el grupo ordenado de la misma manera en que estaría con Rate Monotonic.
     */
    public void sortByPeriod(){        
        Collections.sort(this.taskGroup);
    }
    
    /**
     * Devuelve un iterador para recorrer secuencialmente el grupo de Tareas Periódicas.
     * @return Iterador del grupo
     */
    public Iterator<Task> getPeriodicTaskIterator(){
        return taskGroup.iterator();
    }
    
}

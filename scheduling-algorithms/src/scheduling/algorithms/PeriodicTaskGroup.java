/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Juanito
 */
public class PeriodicTaskGroup extends TaskGroup {
    
    @Override
    public void addTask(Task task){
        if(!(task instanceof PeriodicTask)){
            throw new UnsupportedOperationException("The task you are adding is not a periodic task.");
        }
        super.addTask(task);
            
    }
    
    /**
     * 
     * @return El hiperperiodo del conjunto de tareas periódicas
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
            T_i = periodicTask.getPeriod();
            T_iFtoI = (int) (T_i * Math.pow(10, MAX_DECIMAL_DIGITS));
            hiperPeriod = mathOperation.mcm(hiperPeriod, T_iFtoI);
        }
        return hiperPeriod / 1000;
    }
    
    public void sortByPeriod(){        
        Collections.sort(this.taskGroup);
    }
    
    public Iterator<Task> getPeriodicTaskIterator(){
        return taskGroup.iterator();
    }
    
    @Override
    public PeriodicTaskGroup clone(){        
        PeriodicTaskGroup clone = new PeriodicTaskGroup();
        clone.taskGroup = (ArrayList<Task>)taskGroup.clone();
        return clone;
    }
    
}

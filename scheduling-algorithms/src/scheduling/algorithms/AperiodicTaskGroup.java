/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.Collections;

/**
 *
 * @author Juanito
 */
public class AperiodicTaskGroup extends TaskGroup {
    @Override
    public void addTask(Task task){
        if(!(task instanceof AperiodicTask)){
            throw new UnsupportedOperationException("The task you are adding is not an aperiodic task.");
        }
        super.addTask(task);
            
    }
    
    public void sortByArrivalTime(){        
        Collections.sort(this.taskGroup);
    }
    
    public void addAperiodicTask(AperiodicTask aperiodicTask){
        this.taskGroup.add(aperiodicTask);
    }
    
    //REMOVEEE
}

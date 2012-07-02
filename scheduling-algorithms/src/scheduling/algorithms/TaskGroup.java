/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;

/**
 *
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
     *      
     * @param numTask
     * @return 
     */
    public Task getTask(int numTask) {
        return this.taskGroup.get(numTask);
    }

    /**
     * 
     * @param taskToInsert         
     */
    public void addTask(Task taskToInsert) {
        this.taskGroup.add(taskToInsert);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class TaskSet {
    public static int GROUPS_PER_SET;
    
    private PeriodicTaskGroup[] group;
    
    private int firstFreePosition;
    
    public TaskSet(){
        group = new PeriodicTaskGroup[GROUPS_PER_SET];
        firstFreePosition = 0;
    }
    
    public PeriodicTaskGroup getGroup(int numGroup){
        return group[numGroup];
    }
    
    /**
     * 
     * @param groupToInsert         
     */
    public void addGroup(PeriodicTaskGroup groupToInsert) {
        if(firstFreePosition == GROUPS_PER_SET){
            //llançar excepcio
        }
        this.group[firstFreePosition] = groupToInsert;
        firstFreePosition++;
    }
}

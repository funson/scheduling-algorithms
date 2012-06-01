/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Miguel
 */
public class TaskGroup {
    private Task tastGroup[][];
    
    /**
     * 
     */
    public TaskGroup(){
        this.tastGroup = new Task[10][10];
    }
    
    /**
     * 
     * @param absolutePath 
     */
    public TaskGroup(String absolutePath){
        // TODO importar des del fitxer i carregar a la classe.
    }

    /**
     * 
     * @param group
     * @param task
     * @return 
     */
    public Task getGrupTasca(int group, int task) {
        return this.tastGroup[group][task];
    }

    /**
     * 
     * @param taskToInsert
     * @param group
     * @param task 
     */
    public void setGrupTasca(Task taskToInsert, int group, int task) {
        this.tastGroup[group][task] = taskToInsert;
    }
}

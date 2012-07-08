/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que representa una tarea.
 * @author Miguel
 */
public abstract class Task  implements Comparable<Task> {
    
    private String name;
    protected int computationTime;
    

    protected int period;
    
    /**
     * El constructor de la tarea. Crea una nueva tarea a partir de los parámetros especificados.     
     * @param name El nombre de la tarea
     * @param computationTime   El tiempo de computación de la tarea     
     * @param period   El periodo de la tarea  
     */
    public Task(String name, double computationTime, double period){ 
        this.name = name;
        this.computationTime = (int) Math.ceil(computationTime);    
        this.period =  (int) Math.ceil(period);
    }

    /**
     * @return El tiempo de computación
     */
    public int getComputationTime() {
        return computationTime;
    }

    /**
     * @param computationTime El tiempo de computación a asignar
     */
    public void setComputationTime(double computationTime) {
        this.computationTime = (int) Math.ceil(computationTime);
    }  
    
    @Override
    public int compareTo(Task o) {
        Integer f = new Integer (this.period);
        
        if (this instanceof Server && ! (this instanceof BackgroundServer)){
            if (f.compareTo(o.period)==0){
                return -1;
            }
        }
        else if  (this instanceof BackgroundServer)
            return 1;
        return f.compareTo(o.period);
    }

    /**
     * @return El nombre de la tarea
     */
    public String getName() {
        return name;
    }

    /**
     * @param name El nombre de la tarea a asignar.
     */
    public void setName(String name) {
        this.name = name;
    }
           
}

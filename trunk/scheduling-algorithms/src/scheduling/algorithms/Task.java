/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que representa una tarea
 * @author Miguel
 */
public abstract class Task  implements Comparable<Task> {
    
    private float computationTime;    
    protected float period;
    
    /**
     * El constructor de la tarea. Crea una nueva tarea a partir de los parámetros especificados.     
     * @param computationTime   El tiempo de computación de la tarea     
     * @param period   El periodo de la tarea  
     */
    public Task(float computationTime, float period){        
        this.computationTime = computationTime;    
        this.period = period;
    }

    /**
     * @return El tiempo de computación
     */
    public float getComputationTime() {
        return computationTime;
    }

    /**
     * @param computationTime El tiempo de computación a asignar
     */
    public void setComputationTime(float computationTime) {
        this.computationTime = computationTime;
    }  
    
    @Override
    public int compareTo(Task o) {
        Float f = new Float (this.period);
        return f.compareTo(o.period);
    }
           
}

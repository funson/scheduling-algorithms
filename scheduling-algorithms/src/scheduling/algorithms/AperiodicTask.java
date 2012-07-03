/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que define una tarea aperiódica como una tárea que tiene un tiempo de llegada, 
 * un tiempo de computación y un deadline.
 * @author Juanito
 */
public class AperiodicTask extends Task {   
    private float arrivalTime;
    
    /**
     * El constructor de la tarea aperiódica. Crea una nueva {@link Task tarea} aperiódica a partir de los parámetros especificados.     
     * @param name El nombre de la tarea
     * @param arrivalTime El instante de llegada de la tarea
     * @param computationTime   El tiempo de computación de la tarea     
     */
    public AperiodicTask(String name, float arrivalTime, float computationTime){
        super(name,computationTime, Float.MAX_VALUE);        
        this.arrivalTime = arrivalTime;                
    }
    
    /**
     * @param name El nombre de la tarea
     * @param arrivalTime El instante de llegada de la tarea
     * @param computationTime   El tiempo de computación de la tarea 
     * @param deadline El terminio de la tarea
     */
    public AperiodicTask(String name, float arrivalTime, float computationTime, float deadline){
        super(name, computationTime, deadline);
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return El instante de llegada
     */
    public float getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime El instante de llegada a asignar
     */
    public void setArrivalTime(float arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    /**
     * @return El periodo
     */
    public float getDeadline() {
        return period;
    }

    /**
     * @param period El periodo a asignar
     */
    public void setDeadline(float period) {
        super.period = period;
    }
    
    @Override
    public int compareTo(Task o){
        AperiodicTask o2 = (AperiodicTask) o;
        Float f = new Float (this.arrivalTime);
        return f.compareTo(o2.arrivalTime);
    }
    
}

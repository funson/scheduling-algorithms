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
    private int arrivalTime;
    
    /**
     * El constructor de la tarea aperiódica. Crea una nueva {@link Task tarea} aperiódica a partir de los parámetros especificados.     
     * @param name El nombre de la tarea
     * @param arrivalTime El instante de llegada de la tarea
     * @param computationTime   El tiempo de computación de la tarea     
     */
    public AperiodicTask(String name, double arrivalTime, double computationTime){
        super(name,computationTime, Integer.MAX_VALUE);        
        this.arrivalTime = (int) Math.ceil(arrivalTime);                
    }
    
    /**
     * @param name El nombre de la tarea
     * @param arrivalTime El instante de llegada de la tarea
     * @param computationTime   El tiempo de computación de la tarea 
     * @param deadline El terminio de la tarea
     */
    public AperiodicTask(String name, double arrivalTime, double computationTime, double deadline){
        super(name, computationTime, deadline);
        this.arrivalTime = (int) Math.ceil(arrivalTime);
    }

    /**
     * @return El instante de llegada
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime El instante de llegada a asignar
     */
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = (int) Math.ceil(arrivalTime);
    }
    
    /**
     * @return El periodo
     */
    public int getDeadline() {
        return period;
    }

    /**
     * @param period El periodo a asignar
     */
    public void setDeadline(double period) {
        super.period = (int) Math.ceil(period);
    }
    
    @Override
    public int compareTo(Task o){
        AperiodicTask o2 = (AperiodicTask) o;
        Integer f = new Integer (this.arrivalTime);
        return f.compareTo(o2.arrivalTime);
    }
    
}

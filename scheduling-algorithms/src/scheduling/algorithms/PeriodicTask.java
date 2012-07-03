/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que define una tarea periódica como una tarea que pasado un tiempo (periodo) se vuelve a repetir.
 * Otra característica importante de este tipo de tarea es su fase. La fase es el tiempo en que la tarea llega por primera vez.
 * Como toda tarea, también tiene un tiempo de computación.
 * 
 * @author Juanito
 */
public class PeriodicTask extends Task {    
    private float phase;
    
    /**
     * El constructor de la tarea periódica. Crea una nueva {@link Task tarea} periódica a partir de los parámetros especificados.
     * @param name El nombre de la tarea
     * @param period   El periodo de la tarea     
     * @param phase La fase de la tarea     
     * @param computationTime   El tiempo de computación de la tarea     
     */
    public PeriodicTask(String name, float period, float phase, float computationTime){
        super(name, computationTime, period);
        this.period    = period;
        this.phase  = phase;                
    }

    /**
     * @return El periodo
     */
    public float getPeriod() {
        return period;
    }

    /**
     * @param period El periodo a asignar
     */
    public void setPeriod(float period) {
        super.period = period;
    }

    /**
     * @return La fase
     */
    public float getPhase() {
        return phase;
    }

    /**
     * @param phase La fase a asignar
     */
    public void setPhase(float phase) {
        this.phase = phase;
    }
    
}

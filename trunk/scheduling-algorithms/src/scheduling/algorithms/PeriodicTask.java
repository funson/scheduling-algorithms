/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class PeriodicTask extends Task {    
    private float phase;
    
    /**
     * El constructor de la tarea periódica. Crea una nueva {@link Task tarea} periódica a partir de los parámetros especificados.
     * @param period   El periodo de la tarea     
     * @param phase La fase de la tarea     
     * @param computationTime   El tiempo de computación de la tarea     
     */
    public PeriodicTask(float period, float phase, float computationTime){
        super(computationTime, period);
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public abstract class Server extends PeriodicTask {
    
    private static AperiodicTaskGroup aperiodicTaskGroup;
    
    public Server(float period, float capacity){
        super(period, 0, capacity);        
    }
    
    /**
     * Método abstracto para planificar las tareas. Cada tipo de servidor debe implementar esta función según su política de planificación.
     * Hint: Al recorrer el resumen, utilizar el getSummaryListIterator de la clase Summary.
     * @param summary El resumen de la planificación. Este método lo actualizará con las tareas aperiódicas planificadas.
     * @return 
     */
    public abstract float scheduleAperiodicTaskGroup(Summary summary);

    /**
     * @return the aperiodicTaskGroup
     */
    public AperiodicTaskGroup getAperiodicTaskGroup() {
        return aperiodicTaskGroup;
    }
    
}

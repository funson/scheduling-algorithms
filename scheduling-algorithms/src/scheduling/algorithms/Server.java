/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que define un servidor como una tarea periódica que tiene la capacidad de
 * planificar tareas aperiódicas.
 * @author Juanito
 */
public abstract class Server extends PeriodicTask {
    
    private static AperiodicTaskGroup aperiodicTaskGroup;

    
    public Server(String name, float period, float load){
        super(name, period, 0, load*period);        
    }
    
    /**
     * Método abstracto para planificar las tareas. Éste método lo llama el {@link Scheduler}.
     * Cada tipo de servidor debe implementar esta función según su política de planificación; 
     * debe rellenar el Summary y devolver el tiempo de respuesta medio de las tareas aperiódicas.
     * Hint: Al recorrer el resumen, utilizar el getSummaryListIterator de la clase {@link Summary}.
     * @param summary El resumen de la planificación. Este método lo actualizará con las tareas aperiódicas planificadas.
     * @return Tiempo de respuesta medio de las tareas aperiódicas
     */
    public abstract float scheduleAperiodicTaskGroup(Summary summary);

    /**
     * @return El grupo de tareas aperiódicas que debe planificar el Servidor.
     */
    public static AperiodicTaskGroup getAperiodicTaskGroup() {
        return aperiodicTaskGroup;
    }
    
    /** Método para asignar un grupo de tareas aperiódicas al servidor.
     * @param aAperiodicTaskGroup El grupo de tareas aperiódicas que debe planificar el Servidor
     */
    public static void setAperiodicTaskGroup(AperiodicTaskGroup aAperiodicTaskGroup) {
        aperiodicTaskGroup = aAperiodicTaskGroup;
    }
    public float getLoad(){
        return computationTime/period;
    }
    
    public void setLoad(float load){
        computationTime = period * load;
    }
    
}

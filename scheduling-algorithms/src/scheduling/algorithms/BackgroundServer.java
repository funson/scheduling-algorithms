/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class BackgroundServer extends Server {
    /**
     * El servidor de background es un servidor con capacidad igual a su período y período infinito; es decir, es la tarea periódica menos prioritaria y en cualquier momento es capaz de atender una tarea aperiódica.
     */
    public BackgroundServer(){
        super(Float.MAX_VALUE, Float.MAX_VALUE);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class DeferrableServer extends Server {
    
    public DeferrableServer(float period, float capacity){
        super("Deferrable Server", period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

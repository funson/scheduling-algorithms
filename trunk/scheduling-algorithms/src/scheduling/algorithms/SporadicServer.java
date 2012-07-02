/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class SporadicServer extends Server {
    
    public SporadicServer(float period, float capacity){
        super(period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}

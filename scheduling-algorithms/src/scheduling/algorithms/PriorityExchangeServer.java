/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class PriorityExchangeServer extends Server {
    public PriorityExchangeServer(float period, float capacity){
        super(period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

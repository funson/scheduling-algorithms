/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class PollingServer extends Server {
    public PollingServer(float period, float capacity){
        super("Polling Server", period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

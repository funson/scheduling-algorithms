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
    public PriorityExchangeServer(double period, double capacity){
        super("PE", period, capacity);
    }

    @Override
    public int scheduleAperiodicTaskGroup(Summary summary) {
        return -1;
    }
}

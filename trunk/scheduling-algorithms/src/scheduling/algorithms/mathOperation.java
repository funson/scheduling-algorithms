/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Miguel
 */
public class mathOperation {
    /**
     * Get from wikipedia: http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
     * @param lambda
     * @return 
     */
    public static int getPoisson(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
}
}

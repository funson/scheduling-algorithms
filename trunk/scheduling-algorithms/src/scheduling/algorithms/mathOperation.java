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
     * Source: http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
     * @param lambda
     * @return a Poisson-distributed variable's value
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
    
    /**
     * Source: http://en.wikipedia.org/wiki/Exponential_distribution#Generating_exponential_variates
     * @param lambda
     * @return an Exponential-distributed random variable's value
     */
    public static float getExponential(double lambda){
        return (float)(-Math.log(Math.random())/lambda);        
    }
    
    /**
     * Source: http://latecladeescape.com/algoritmos/1115-algoritmo-de-euclides-mcd-y-mcm
     * @param a
     * @param b
     * @return MCD(a,b)
     */
    public static int mcd(int a, int b){
        int iaux;
        a = Math.abs(a);
        b = Math.abs(b);
        int i1 = Math.max(a,b);
        int i2 = Math.min(a,b);
        do
        {
            iaux = i2;
            i2 = i1 % i2;
            i1 = iaux;
        } while (i2 != 0);
        return i1;
    }
    
    /**
     * Source: http://latecladeescape.com/algoritmos/1115-algoritmo-de-euclides-mcd-y-mcm
     * @param a
     * @param b
     * @return MCM(a,b)
     */
    public static int mcm(int a, int b){
        return (a / mcd(a, b)) * b;
    }
}

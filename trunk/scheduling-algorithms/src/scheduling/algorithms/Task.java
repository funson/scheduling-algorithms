/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Miguel
 */
public class Task {
    private float T_i;
    private float C_i;
    private float Phi_i;
    private float U_p;
    
    /**
     * 
     */
    public Task(){
        this.T_i    = 0;
        this.C_i    = 0;
        this.Phi_i  = 0;
        this.U_p    = 0;
    }
    /**
     * 
     * @param T_i
     * @param C_i
     * @param Phi_i
     * @param U_p 
     */
    public Task( float T_i, float C_i, float Phi_i, float U_p){
        this.T_i    = T_i;
        this.C_i    = C_i;
        this.Phi_i  = Phi_i;
        this.U_p    = U_p;
    }

    /**
     * @return the T_i
     */
    public float getT_i() {
        return T_i;
    }

    /**
     * @param T_i the T_i to set
     */
    public void setT_i(float T_i) {
        this.T_i = T_i;
    }

    /**
     * @return the C_i
     */
    public float getC_i() {
        return C_i;
    }

    /**
     * @param C_i the C_i to set
     */
    public void setC_i(float C_i) {
        this.C_i = C_i;
    }

    /**
     * @return the Phi_i
     */
    public float getPhi_i() {
        return Phi_i;
    }

    /**
     * @param Phi_i the Phi_i to set
     */
    public void setPhi_i(float Phi_i) {
        this.Phi_i = Phi_i;
    }

    /**
     * @return the U_p
     */
    public float getU_p() {
        return U_p;
    }

    /**
     * @param U_p the U_p to set
     */
    public void setU_p(float U_p) {
        this.U_p = U_p;
    }
    
    
    
}

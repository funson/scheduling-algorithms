/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scheduling.algorithms;

import scheduling.algorithms.Scheduler.aperiodicGenerationMode;

/**
 * Clase con la información de las tareas aperiódicas para realizar la simulación.
 * @author LTIM
 */
public class AperiodicInfo {
    private AperiodicTaskGroup[][] aperiodicTaskGroups;
    private aperiodicGenerationMode mode;
    private double[] aperiodicMeanServiceTimes;
    private double[] aperiodicLoads;

    public AperiodicInfo(){
        this.mode = aperiodicGenerationMode.NONE;
    }

    public AperiodicInfo(AperiodicTaskGroup[][] aperiodicTaskGroups, aperiodicGenerationMode mode, double[] aperiodicMeanServiceTimes, double[] aperiodicLoads){
        this.aperiodicTaskGroups = aperiodicTaskGroups;
        this.mode = mode;
        this.aperiodicMeanServiceTimes = aperiodicMeanServiceTimes;
        this.aperiodicLoads = aperiodicLoads;
    }
    
    public AperiodicInfo(AperiodicInfo aperiodicInfo){
        this.mode = aperiodicInfo.mode;
        if(aperiodicInfo.aperiodicLoads != null && aperiodicInfo.aperiodicMeanServiceTimes != null && aperiodicInfo.aperiodicTaskGroups != null){
            this.aperiodicLoads = aperiodicInfo.aperiodicLoads.clone();
            this.aperiodicMeanServiceTimes = aperiodicInfo.aperiodicMeanServiceTimes.clone();        
            this.aperiodicTaskGroups = new AperiodicTaskGroup[this.aperiodicLoads.length][this.aperiodicMeanServiceTimes.length];
            for(int i = 0; i < aperiodicLoads.length; i++)
                for(int j = 0; j < aperiodicMeanServiceTimes.length; j++)
                    this.aperiodicTaskGroups[i][j] = (AperiodicTaskGroup) aperiodicInfo.aperiodicTaskGroups[i][j].clone();
        }
    }

    /**
     * @return the aperiodicTaskGroups
     */
    public AperiodicTaskGroup[][] getAperiodicTaskGroups() {
        return aperiodicTaskGroups;
    }

    /**
     * @param aperiodicTaskGroups the aperiodicTaskGroups to set
     */
    public void setAperiodicTaskGroups(AperiodicTaskGroup[][] aperiodicTaskGroups) {
        this.aperiodicTaskGroups = aperiodicTaskGroups;
    }

    /**
     * @return the mode
     */
    public aperiodicGenerationMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(aperiodicGenerationMode mode) {
        this.mode = mode;
    }

    /**
     * @return the aperiodicMeanServiceTimes
     */
    public double[] getAperiodicMeanServiceTimes() {
        return aperiodicMeanServiceTimes;
    }

    /**
     * @param aperiodicMeanServiceTimes the aperiodicMeanServiceTimes to set
     */
    public void setAperiodicMeanServiceTimes(double[] aperiodicMeanServiceTimes) {
        this.aperiodicMeanServiceTimes = aperiodicMeanServiceTimes;
    }

    /**
     * @return the aperiodicLoads
     */
    public double[] getAperiodicLoads() {
        return aperiodicLoads;
    }

    /**
     * @param aperiodicLoads the aperiodicLoads to set
     */
    public void setAperiodicLoads(double[] aperiodicLoads) {
        this.aperiodicLoads = aperiodicLoads;
    }
}

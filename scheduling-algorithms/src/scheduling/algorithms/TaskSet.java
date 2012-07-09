/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 * Clase que define un conjunto de tareas como un grupo formado por 10 grupos de tareas periodicas.
 * @author Juanito
 */
public class TaskSet {
    public final static int GROUPS_PER_SET =1;
    
    private PeriodicTaskGroup[] group;    
    private int firstFreePosition;
    private double totalPeriodicLoad;
    private int maxHiperperiod = 0;
    
    /**
     * Constructor de Conjunto de Tareas
     * @param totalPeriodicLoad Carga periódica que caracterizará al conjunto de tareas.
     */
    public TaskSet(double totalPeriodicLoad){
        group = new PeriodicTaskGroup[GROUPS_PER_SET];
        this.totalPeriodicLoad = totalPeriodicLoad;
        firstFreePosition = 0;
        maxHiperperiod = 0;
    }
    
    /**
     * Método que devuelve el grupo que se encuentra en el índice especificado.
     * @param numGroup Índice del grupo a obtener.
     * @return El grupo
     */
    public PeriodicTaskGroup getGroup(int numGroup){
        return group[numGroup];        
    }
    
    /**
     * Método para insertar un grupo de tareas periódicas en el conjunto.
     * En caso de que el conjunto ya tenga 10 grupos dentro lanzará una excepción.
     * @param groupToInsert  Grupo de tareas periódicas a insertar.
     */
    public void addGroup(PeriodicTaskGroup groupToInsert) {
        if(firstFreePosition == GROUPS_PER_SET){
            throw new UnsupportedOperationException("No caben más grupos de tareas en el conjunto.");
        }
        maxHiperperiod = Math.max(getMaxHiperperiod(), groupToInsert.calculateHiperperiod());
        this.group[firstFreePosition] = groupToInsert;
        firstFreePosition++;
    }
    
    public double getTotalPeriodicLoad(){
        return totalPeriodicLoad;
    }
    
    public int numOfGroups(){
        return this.group.length;
    }

    /**
     * @return the maxHiperperiod
     */
    public int getMaxHiperperiod() {
        return maxHiperperiod;
    }
    
    public int calculateMaxHiperperiod(){
        int hiperperiod = 0;
        for(int i = 0; i < GROUPS_PER_SET; i++){
            hiperperiod = Math.max(hiperperiod, group[i].calculateHiperperiod());
        }
        return hiperperiod;
    }
}

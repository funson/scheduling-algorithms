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
    public final static int GROUPS_PER_SET =10;
    
    private PeriodicTaskGroup[] group;    
    private int firstFreePosition;
    private float totalPeriodicLoad;
    
    
    public TaskSet(){
        group = new PeriodicTaskGroup[GROUPS_PER_SET];
        totalPeriodicLoad = 0;
        firstFreePosition = 0;
    }
    
    /**
     * Método que devuelve el grupo que se encuentra en el índice especificado.
     * @param numGroup Índice del grupo a obtener.
     * @param totalPeriodicLoad La utilización periódica de cada grupo (es la misma para todos los grupos)
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
        this.group[firstFreePosition] = groupToInsert;
        firstFreePosition++;
    }
    
    public float getTotalPeriodicLoad(){
        return totalPeriodicLoad;
    }
}

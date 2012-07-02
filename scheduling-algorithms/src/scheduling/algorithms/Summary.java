/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Juanito
 */
public class Summary {        
    
    private float averageAperiodicResponseTime;
    private ArrayList<Node> nodes;
    private int deadlinesNotMet;
    
    
    public Summary(float timeToSchedule){
        nodes = new ArrayList();
        nodes.add(new Node(0, timeToSchedule));  
        deadlinesNotMet = 0;
    }
    
    /**
    * @return El tiempo medio de respuesta de las tareas aperiódicas
    */
    public float getAverageAperiodicResponseTime() {
        return averageAperiodicResponseTime;
    }
    
    /**
     * 
     * @return El iterador de los nodos para una mayor eficiencia al recorrer el resumen. Devuelve un listIterator ya que este iterador permite modificar la colección subyacente mientras iteramos.
     */
    public ListIterator<Node> getSummaryListIterator(){
        return nodes.listIterator();
    }
    
    public void schedulePeriodicTaskGroup(PeriodicTaskGroup periodicTaskGroupToSchedule, Server server){
        boolean schedulable;
        PeriodicTaskGroup clonedPeriodicTaskGroup = periodicTaskGroupToSchedule.clone();
        clonedPeriodicTaskGroup.addTask(server);
        clonedPeriodicTaskGroup.sortByPeriod();        
        Iterator<Task> periodicTaskIterator = periodicTaskGroupToSchedule.getPeriodicTaskIterator();
        while(periodicTaskIterator.hasNext()){
            schedulable = schedulePeriodicTask((PeriodicTask) periodicTaskIterator.next());
            if(!schedulable)
                deadlinesNotMet++;
        }        
    }
    
    /**
     * Método para planificar una tarea periódica
     * @param periodicTask
     * @return Devuelve {@code false} si la tarea incumple alguna vez su deadline.
     */
    private boolean schedulePeriodicTask(PeriodicTask periodicTask) {        
        if(periodicTask instanceof Server){
            ((Server) periodicTask).scheduleAperiodicTaskGroup(this);
            return true;
        }
        else
            //Iterar, para todas las ocurrencias de la tarea periódica, los nodos libres y meter la tarea
            //Hint: para mayor eficiencia, USAR ITERATOR para recorrer el array de nodos
            throw new UnsupportedOperationException("Scheduling periodic task not supported yet. USE LIST ITERATOR!!");        
    }
}

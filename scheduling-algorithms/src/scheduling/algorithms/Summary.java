/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Clase que define el Resumen de la planificación de un grupo de tareas.
 * La información que incluye son los intervalos de tiempo en que se ejecutará cada tarea, o bién no se ejecuta ninguna;
 * el tiempo medio de respuesta de la tareas aperiódicas planificadas y el número de tareas aperiódicas que no han cumplido con su terminio.
 * En este clase también se encuentra el método para planificar el grupo de tareas periódicas que debe ejecutar el {@link Scheduler}.
 * 
 * @author Juanito
 */
public class Summary {        
    
    private float averageAperiodicResponseTime;
    private ArrayList<Node> nodes;
    private int deadlinesNotMet;
    
    /**
     * @param timeToSchedule Tiempo al que se parará de planificar.
     */
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
    
    /**
     * Método que realiza la planificación de un grupo de tareas periódicas y de un servidor.
     * @param periodicTaskGroupToSchedule Grupo de tareas periódicas a planificar. Sin contar el servidor.
     * @param server Servidor que se utilizará para planificar las tareas aperiódicas.
     */
    public void schedulePeriodicTaskGroup(PeriodicTaskGroup periodicTaskGroupToSchedule, Server server){
        boolean schedulable;
        PeriodicTaskGroup clonedPeriodicTaskGroup = (PeriodicTaskGroup) periodicTaskGroupToSchedule.clone();
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
     * Método que sirve para meter lo que quede para ejecutar de una tarea dentro de un Nodo libre del Summary.
     * @param iterator Meter el iterador apuntando a un nodo libre
     * @param task     Tarea a meter.
     * @param arrivalTime Tiempo de llegada de la tarea a meter.
     * @param remainingComputation Tiempo de computación que queda para acabar la Tarea
     * @param serverCapacity Capacidad actual del servidor.       
     * @return Tiempo del nodo asignado a la tarea. Imprescindible utilizarlo para calcular 
     * el remainingComputation y el Server Capacity después de ejecutar éste método.
     * Si devuelve -1 significa que el nodo ya está ocupado por una tarea.
     */
    public static float addTaskToFreeNode(ListIterator<Node> iterator, Task task, float arrivalTime, float remainingComputation, float serverCapacity){
        if (!iterator.hasNext())
            return -1;
        Node node = iterator.next();
        
        if (!node.isFree() || serverCapacity == 0)
            return -1;
        
        float stoptime = Math.min(remainingComputation,serverCapacity);
        
        if(node.getStartTime() < arrivalTime){
            iterator.add(new Node(arrivalTime, node.getStopTime()));
            node.setStopTime(arrivalTime);
            node = (Node) iterator.previous();
        }
        if(node.getStopTime() > node.getStartTime() + stoptime){
            iterator.add(new Node(node.getStartTime() + stoptime, node.getStopTime()));
            node.setStopTime(node.getStartTime() + stoptime);
            iterator.previous(); //lo dejamos apuntando a node
        }
        node.setTask(task);
        return node.getStopTime() - node.getStartTime();
    }
    
    /**
     * Método para planificar una tarea periódica
     * @param periodicTask
     * @return Devuelve {@code false} si la tarea incumple alguna vez su deadline.
     */
    private boolean schedulePeriodicTask(PeriodicTask periodicTask) {        
        if(periodicTask instanceof Server){
            averageAperiodicResponseTime = ((Server) periodicTask).scheduleAperiodicTaskGroup(this);
            return true;
        } else {            
            ListIterator iterator = getSummaryListIterator();
            Node node;
            float period = periodicTask.getPeriod();
            float phase = periodicTask.getPhase();
            float computation = periodicTask.getComputationTime();
            float remainingComputation;
            boolean deadlineMet = true;
            int numPeriod = 1;
            float nextAbsolutePeriod, currentAbsolutePeriod;
            while(iterator.hasNext()){
                nextAbsolutePeriod = period*numPeriod + phase;
                remainingComputation = computation;
                node = (Node) iterator.next();
                while(iterator.hasNext() && node.getStopTime() <= nextAbsolutePeriod){
                    node = (Node) iterator.next();
                }
                if(!iterator.hasNext())
                    return deadlineMet;
                currentAbsolutePeriod = nextAbsolutePeriod;
                nextAbsolutePeriod = period * (numPeriod + 1) + phase;
                while(remainingComputation > 0 && deadlineMet){
                    if(node.isFree()){
                        remainingComputation -= addTaskToFreeNode(iterator, periodicTask, currentAbsolutePeriod, remainingComputation, Float.MAX_VALUE);
                        if(remainingComputation > 0 && node.getStopTime() > currentAbsolutePeriod)
                            deadlineMet = false;
                    }                    
                    if(remainingComputation > 0 && node.getStartTime() >= nextAbsolutePeriod)
                        deadlineMet = false;
                    if(iterator.hasNext())
                        node = (Node) iterator.next();
                    else if(remainingComputation > 0)
                        deadlineMet = false;
                }                
                numPeriod++;
            }
            return deadlineMet;
        }
    }
}

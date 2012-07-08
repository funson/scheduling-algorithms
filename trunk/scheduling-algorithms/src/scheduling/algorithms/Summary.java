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
    
    private int averageAperiodicResponseTime;
    private ArrayList<Node> nodes;
    private int deadlinesNotMet;
    private int hiperperiod;
    
    /**
     * @param timeToSchedule Tiempo al que se parará de planificar.
     */
    public Summary(int timeToSchedule){
        nodes = new ArrayList();
        nodes.add(new Node(0, timeToSchedule));  
        deadlinesNotMet = 0;
        hiperperiod = timeToSchedule;
    }
    
    /**
    * @return El tiempo medio de respuesta de las tareas aperiódicas
    */
    public int getAverageAperiodicResponseTime() {
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
        Iterator<Task> periodicTaskIterator = clonedPeriodicTaskGroup.getPeriodicTaskIterator();
        while(periodicTaskIterator.hasNext()){
            schedulable = schedulePeriodicTask((PeriodicTask) periodicTaskIterator.next());
            if(!schedulable)
                deadlinesNotMet++;
        }        
    }
    
    /**
     * Método que sirve para meter lo que quede para ejecutar de una tarea dentro de un Nodo libre del Summary.
     * @param iterator Meter el iterador apuntando a un nodo libre.
     * @param task     Tarea a meter.
     * @param arrivalTime Tiempo de llegada de la tarea a meter.
     * @param remainingComputation Tiempo de computación que queda para acabar la Tarea.
     * @param serverCapacity Capacidad actual del servidor. No debemos olvidarnos de el momento en que la capacidad del servidor se regenera.
     *        Por tanto será mas útil si en éste parámetro metemos el valor de Min (currentServerCapacity, nextRegenerationTime - currentNode.startTime()).
     *        (El nombre de las variables es un ejemplo, usad las variables que hayáis creado para este propósito)
     * @return El nodo al cual se le ha asignado la tarea. Este nodo puede ser el mismo nodo libre (ahora ocupado) o un nuevo nodo contenido en el nodo libre inicial 
     * 
     * PRECONDICIÓN: una llamada a esta función siempre debe ir precedida de una llamada a iterateUntilFreeNode
     * IMPORTANTE:  como en ambos casos el return devolverá un int, para poder discriminar en cúal de los dos casos nos encontramos, el segundo caso se
     * multiplicará por -1 y así se podrá deducir: si el valor devuelto por el método es positivo estamos en el primer caso y si es negativo en el segundo.
     *            
     */
    public static Node addTaskToFreeNode(ListIterator<Node> iterator, Task task, int arrivalTime, int remainingComputation, int serverCapacity){
        Node node = iterator.next();
        
        if (!node.isFree() || serverCapacity <= 0 || remainingComputation<= 0)
            return null;
        
        int stoptime = Math.min(remainingComputation,serverCapacity);
        
        if(node.getStartTime() < arrivalTime){
            iterator.add(new Node(arrivalTime, node.getStopTime()));
            node.setStopTime(arrivalTime);
            node = (Node) iterator.previous();
            iterator.next();
        }
        if(node.getStopTime() > node.getStartTime() + stoptime){
            iterator.add(new Node(node.getStartTime() + stoptime, node.getStopTime()));
            node.setStopTime(node.getStartTime() + stoptime);
            iterator.previous(); //lo dejamos apuntando a node
            
        }
        node.setTask(task);
        return node;
    }
    
    public static void iterateUntilFreeNode(ListIterator<Node> inode) {
        if(!inode.hasNext())
            return;
        Node node = inode.next();
        while(inode.hasNext() && !node.isFree())
            node = inode.next();
        
        if(node.isFree())
            inode.previous();    
    }
    
    public static void iterateUntilTime(ListIterator<Node> inode, int time) {
        if(!inode.hasNext())
            return;
        Node node = inode.next();
        
        while(inode.hasNext() && node.getStopTime() <= time)
            node = inode.next();
        if(node.getStopTime() > time)
            inode.previous();
            if(node.isFree() && time != node.getStartTime()){
                inode.add(new Node(node.getStartTime(), time));
                node.setStartTime(time);
            }
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
            Node node = new Node(0, 0);
            int period = periodicTask.getPeriod();
            int phase = periodicTask.getPhase();
            int computation = periodicTask.getComputationTime();
            int remainingComputation;
            boolean deadlineMet = true;
            int pendingOcurrences = 0;
            int numPeriod = 0;
            int nextAbsolutePeriod, currentAbsolutePeriod;
            while(iterator.hasNext() && numPeriod < this.hiperperiod / periodicTask.getPeriod()){
                nextAbsolutePeriod = period*numPeriod + phase;
                remainingComputation = computation * (pendingOcurrences + 1);
                
                iterateUntilTime(iterator, nextAbsolutePeriod);
                if(!iterator.hasNext())
                    return deadlineMet;
                
                currentAbsolutePeriod = nextAbsolutePeriod;
                nextAbsolutePeriod = period * (numPeriod + 1) + phase;
                while(remainingComputation > 0){
                    iterateUntilFreeNode(iterator);
                    if(!iterator.hasNext())
                        return false;
                    
                    node = addTaskToFreeNode(iterator, periodicTask, currentAbsolutePeriod, remainingComputation, Integer.MAX_VALUE);

                    remainingComputation -= node.getStopTime()-node.getStartTime();

                    if(node.getStopTime() > nextAbsolutePeriod)
                        deadlineMet = false;    
                } 
                pendingOcurrences = (int) Math.floor((node.getStopTime() - currentAbsolutePeriod) / period);
                numPeriod++;
            }
            return deadlineMet;
        }
    }
}

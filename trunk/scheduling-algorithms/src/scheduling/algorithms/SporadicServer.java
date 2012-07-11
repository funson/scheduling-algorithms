/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 *
 * @author Juanito
 */
public class SporadicServer extends Server {
    
    private class RegenerationPoint{
        public int regenerationTime;
        public int capacityToRegenerate;
        public RegenerationPoint(int time, int capacity){
            this.regenerationTime = time;
            this.capacityToRegenerate = capacity;
        }
    }
    
            
    public SporadicServer(double period, double capacity){
        super("SS", period, capacity);
    }

    @Override
    public double scheduleAperiodicTaskGroup(Summary summary) {
        LinkedList<RegenerationPoint> regenerationpoints = new LinkedList<>();
        double trTotal = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = SporadicServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        Node nodo = new Node(0,0);
        boolean salir;
        AperiodicTask tarea;
        int tiempopendiente;
        RegenerationPoint nextRegenerationPoint = new RegenerationPoint(0, 0); 
        int numTasks = 0;
        boolean handled;
        int actualcapacity = this.getComputationTime();
       
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        AperiodicNodeIterator = summary.getSummaryListIterator();
        //
        //
        while (aperiodicTaskIterator.hasNext()){ //vamos recorriendo todas las tareas aperiódicas
            tarea = (AperiodicTask) aperiodicTaskIterator.next();
            tiempopendiente = tarea.getComputationTime();
            Summary.iterateUntilTime(AperiodicNodeIterator, tarea.getArrivalTime());
            handled = false;
            while (tiempopendiente > 0 || !handled){//metemos cada tarea aperiódica en el/los nodo/s libre/s necesarios.
                salir = false;
                handled = true;
                 
                //Restauramos tota la capacidad correspondiente hasta este punto
                while ((!regenerationpoints.isEmpty() && regenerationpoints.peek().regenerationTime <nodolibre.getStartTime()) || actualcapacity == 0){
                    nextRegenerationPoint = regenerationpoints.poll();
                    Summary.iterateUntilTime(AperiodicNodeIterator, nextRegenerationPoint.regenerationTime);
                    if (!AperiodicNodeIterator.hasNext())
                        return trTotal/ numTasks;
                    actualcapacity += nextRegenerationPoint.capacityToRegenerate;
                }
                
                //Buscamos el siguiente nodo libre
                Summary.iterateUntilFreeNode(AperiodicNodeIterator);
                if (!AperiodicNodeIterator.hasNext())
                    return (int) trTotal/ numTasks;
                nodolibre = AperiodicNodeIterator.next();
                AperiodicNodeIterator.previous();
                
                //
                nodo = Summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, actualcapacity);
                tiempopendiente -= nodo.getStopTime() - nodo.getStartTime();
                actualcapacity-=nodo.getStopTime() - nodo.getStartTime();
                
                regenerationpoints.offer(new RegenerationPoint(nodo.getStartTime()+period, nodo.getStopTime()-nodo.getStartTime()));
            }
             trTotal += nodo.getStopTime() - tarea.getArrivalTime();
             numTasks++;
        }
        return trTotal/ numTasks;
    }
    
    
}

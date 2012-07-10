/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Queue;

/**
 *
 * @author Juanito
 */
public class SporadicServer extends Server {
    
    ArrayList<Integer> regenerationpoints;
    ArrayList<Integer> capacitytoregenerate;
            
    public SporadicServer(double period, double capacity){
        super("SS", period, capacity);
        regenerationpoints=new ArrayList<>();
        capacitytoregenerate=new ArrayList<>();
    }

    @Override
    public int scheduleAperiodicTaskGroup(Summary summary) {
        int trTotal = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = SporadicServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        Node nodo = new Node(0,0);
        boolean salir;
        AperiodicTask tarea;
        int tiempopendiente;
        int nextTime = 0; 
        int nextCapacity = 0;
        int numTasks = 0;
        boolean handled;
       
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        AperiodicNodeIterator = summary.getSummaryListIterator();
        //
        this.SetServerCapacity(this.getComputationTime());
        //
        while (aperiodicTaskIterator.hasNext()){ //vamos recorriendo todas las tareas aperiódicas
            tarea = (AperiodicTask) aperiodicTaskIterator.next();
            tiempopendiente = tarea.getComputationTime();
            Summary.iterateUntilTime(AperiodicNodeIterator, tarea.getArrivalTime());
            handled = false;
            while (tiempopendiente > 0 || !handled){//metemos cada tarea aperiódica en el/los nodo/s libre/s necesarios.
                salir = false;
                handled = true;
                
                Summary.iterateUntilFreeNode(AperiodicNodeIterator);
                if (!AperiodicNodeIterator.hasNext())
                    return (int) trTotal/ numTasks;
                nodolibre = AperiodicNodeIterator.next();
                AperiodicNodeIterator.previous();
                
                if (!regenerationpoints.isEmpty()){
                    
                    while (this.regenerationpoints.get(0) <nodolibre.getStartTime() || actualcapacity == 0){
                        nextTime = this.regenerationpoints.remove(0);
                        nextCapacity = this.capacitytoregenerate.remove(0);
                        Summary.iterateUntilTime(AperiodicNodeIterator, nextTime);
                        if (!AperiodicNodeIterator.hasNext())
                            return (int) trTotal/ numTasks;
                        this.actualcapacity += nextCapacity;
                    }
                    Summary.iterateUntilFreeNode(AperiodicNodeIterator);   
                }
                
                //
                nodo = Summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, this.actualcapacity);
                tiempopendiente -= nodo.getStopTime() - nodo.getStartTime();
                this.actualcapacity-=nodo.getStopTime() - nodo.getStartTime();
                
                this.SetRegenerationPoint(((AperiodicTask) tarea).getArrivalTime(), nodo.getStartTime());
                this.capacitytoregenerate.add(nodo.getStopTime() - nodo.getStartTime());
            }
             trTotal += nodo.getStopTime() - tarea.getArrivalTime();
             numTasks++;
        }
        return (int) trTotal/ numTasks;
    }
    
    public void SetRegenerationPoint(int activationtime, int nodefreestarttime){
        int regenerationpoint;
        if (activationtime>nodefreestarttime){
            regenerationpoint = activationtime;
        }else{
            regenerationpoint = nodefreestarttime;
        }
        regenerationpoint+=this.getPeriod();
        //
        regenerationpoints.add(regenerationpoint);
    }
    
    int actualcapacity;
    public void SetServerCapacity(int capacity){
        this.actualcapacity=capacity;
    }

    public int GetServerCapacity(int starttime, int stoptime,int totaltasktime){
        int actual;
        for(int i=0;i<regenerationpoints.size();i++){
            actual = regenerationpoints.get(i);
            if(actual<stoptime){
                regenerationpoints.remove(i);
                this.actualcapacity=this.capacitytoregenerate.get(0);
                this.capacitytoregenerate.remove(0);
            }
            //
        }
        return this.actualcapacity;
    }
}

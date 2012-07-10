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
public class SporadicServer extends Server {
    
    ArrayList<Integer> regenerationpoints;
            
    public SporadicServer(double period, double capacity){
        super("SS", period, capacity);
        regenerationpoints=new ArrayList<>();
        this.lastcapacity=0;
    }

    @Override
    public int scheduleAperiodicTaskGroup(Summary summary) {
        
        
        
        ListIterator<Node> inode = summary.getSummaryListIterator();
        Server.getAperiodicTaskGroup().sortByArrivalTime();
        Iterator<Task> itask = Server.getAperiodicTaskGroup().taskGroup.iterator();
        Node node =new Node (0,0);
        AperiodicTask aperiodicTask;
        int remainingComputation;
        int totalResponseTime   = 0;
        int numtasques          = 0;
        int serverCapacity      = this.getComputationTime();
        Node potentialNode     = inode.next();
        inode.previous();
        int numperiod =0;
        int currentStartPeriodTime   = 0;
        int nextStartPeriodTime      = 0;
        boolean handled = false;
        
        
        
        /*int trTotal = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = SporadicServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        Node nodo = new Node(0,0);
        boolean salir;
        AperiodicTask tarea;
        int tiempopendiente;
        
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        //
        this.SetServerCapacity(this.getComputationTime());
        //
        while (aperiodicTaskIterator.hasNext()){ //vamos recorriendo todas las tareas aperiódicas
            tarea = (AperiodicTask) aperiodicTaskIterator.next();
            tiempopendiente = tarea.getComputationTime();
            while (tiempopendiente > 0){//metemos cada tarea aperiódica en el/los nodo/s libre/s necesarios.
                AperiodicNodeIterator = summary.getSummaryListIterator();
                salir = false;
                while (AperiodicNodeIterator.hasNext() && salir==false){//buscamos los nodos libres necesarios
                    nodolibre = AperiodicNodeIterator.next();
                    if (nodolibre.isFree() && nodolibre.getStopTime() > ((AperiodicTask) tarea).getArrivalTime()){
                        salir = true;
                    }
                }
                //
                this.SetRegenerationPoint(((AperiodicTask) tarea).getArrivalTime(), nodolibre.getStartTime());
                nodo = Summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, this.GetServerCapacity(nodolibre.getStartTime(),nodolibre.getStopTime(),((AperiodicTask) tarea).getComputationTime()));
                tiempopendiente -= nodo.getStopTime() - nodo.getStartTime();
            }
             trTotal += nodo.getStopTime() - tarea.getArrivalTime();
        }
        return (int) trTotal/ (int) SporadicServer.getAperiodicTaskGroup().getNumTasks();*/
        return -1;
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
    int lastcapacity;
    public void SetServerCapacity(int capacity){
        this.actualcapacity=capacity;
    }

    public int GetServerCapacity(int starttime, int stoptime,int totaltasktime){
        int actual;
        int regenerationpointstoremove=0;
        for(int i=0;i<regenerationpoints.size();i++){
            actual=regenerationpoints.get(i);
            //
            // Punts de regeneració abans de l'interval:
            //
            if (actual<starttime){
                //Regeneració:
                if (this.lastcapacity>this.actualcapacity){
                    this.actualcapacity+=(lastcapacity-this.actualcapacity);
                }
                this.lastcapacity=this.actualcapacity;
                //Fi regeneració
                regenerationpointstoremove++;
                
            }
            //
            // Punts de regeneració dins l'interval:
            //
            if((actual>=starttime)&&(actual<stoptime)){
                /*
                if (this.actualcapacity<actual){
                    this.actualcapacity=this.getComputationTime();
                }else{
                    this.actualcapacity=actual+this.getComputationTime();
                }
                */
                //Regeneració:
                if (this.lastcapacity>this.actualcapacity){
                    this.actualcapacity+=(lastcapacity-this.actualcapacity);
                }
                this.lastcapacity=this.actualcapacity;
                //Fi regeneració
                regenerationpointstoremove++;
            }
            //
            //
            //
        }
        //Eliminam els punts de regeneració que ja em passat:
        for(int j=0;j<regenerationpointstoremove;j++){
            this.regenerationpoints.remove(j);
        }
        if (this.actualcapacity>=totaltasktime){
            this.actualcapacity-=totaltasktime;
            return totaltasktime;
        }else{
            int aux = this.actualcapacity;
            this.actualcapacity = 0;
            return aux;
        }
    }
}

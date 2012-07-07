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
    
    public SporadicServer(float period, float capacity){
        super("Sporadic Server", period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        float trTotal = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = SporadicServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        boolean salir;
        Task tarea;
        float tiempopendiente;
        float trTarea = 0;
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        //
        this.SetServerCapacity(this.getComputationTime());
        //
        while (aperiodicTaskIterator.hasNext()){ //vamos recorriendo todas las tareas aperiódicas
            tarea = aperiodicTaskIterator.next();
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
                trTarea = summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, this.GetServerCapacity(nodolibre.getStartTime(),nodolibre.getStopTime(),((AperiodicTask) tarea).getComputationTime()));
                if (trTarea<0)//en este caso la tarea no ha finalizado y debemos calcular su tiempo pendiente.
                    tiempopendiente = tiempopendiente - (-1*trTarea);
                else{//como la tarea ya ha finalizado, obtenemos su tiempo de respuesta, lo sumamos al tiempo de respuesta total e indicamos que el tiempo pendiente es 0.
                    trTotal = trTotal + trTarea;
                    tiempopendiente = 0;
                }
            }   
        }
        return (float) trTotal/ (float) SporadicServer.getAperiodicTaskGroup().getNumTasks();
    }
    
    
    ArrayList<Float> regenerationpoints=new ArrayList<>();
    public void SetRegenerationPoint(float activationtime, float nodefreestarttime){
        float regenerationpoint;
        if (activationtime>nodefreestarttime){
            regenerationpoint = activationtime;
        }else{
            regenerationpoint = nodefreestarttime;
        }
        regenerationpoint+=this.getPeriod();
        //
        regenerationpoints.add(regenerationpoint);
    }
    
    float actualcapacity;
    public void SetServerCapacity(float capacity){
        this.actualcapacity=capacity;
    }
    public void SubServerCapacity(float capacity){
        this.actualcapacity=capacity;
    }
    public float GetServerCapacity(float starttime, float stoptime,float totaltasktime){
        float actual;
        for(int i=0;i<regenerationpoints.size();i++){   
            actual=regenerationpoints.get(i);
            if((actual>=starttime)&&(actual<stoptime)){
                if (this.actualcapacity<actual){
                    this.actualcapacity=this.getComputationTime();
                }else{
                    this.actualcapacity=actual+this.getComputationTime();
                }
            }
        }
        if (this.actualcapacity>=totaltasktime){
            return totaltasktime;
        }else{
            float aux = this.actualcapacity;
            this.actualcapacity = 0;
            return aux;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Juanito
 */
public class BackgroundServer extends Server {
    /**
     * El servidor de background es un servidor con capacidad igual a su período y período infinito; es decir, es la tarea periódica menos prioritaria y en cualquier momento es capaz de atender una tarea aperiódica.
     */
    public BackgroundServer(){
        super("BS", Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    @Override
    /**
     * Clase que se encarga de planificar un grupo de tareas aperiódicas
     */
    public int scheduleAperiodicTaskGroup(Summary summary) {
        //Versión Juanca:
//        int trTotal = 0;
//        Iterator<Task> aperiodicTaskIterator;
//        ListIterator<Node> AperiodicNodeIterator;
//        AperiodicTaskGroup clonedAperiodicTaskGroup = BackgroundServer.getAperiodicTaskGroup();    
//        clonedAperiodicTaskGroup.sortByArrivalTime();
//        Node nodolibre = null;
//        boolean salir;
//        Task tarea;
//        int tiempopendiente;
//        int trTarea = 0;
//        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
//        while (aperiodicTaskIterator.hasNext()){ //vamos recorriendo todas las tareas aperiódicas
//            tarea = aperiodicTaskIterator.next();
//            tiempopendiente = tarea.getComputationTime();
//            while (tiempopendiente > 0){//metemos cada tarea aperiódica en el/los nodo/s libre/s necesarios.
//                AperiodicNodeIterator = summary.getSummaryListIterator();
//                salir = false;
//                while (AperiodicNodeIterator.hasNext() && salir==false){//buscamos los nodos libres necesarios
//                    nodolibre = AperiodicNodeIterator.next();
//                    if (nodolibre.isFree() && nodolibre.getStopTime() > ((AperiodicTask) tarea).getArrivalTime()){
//                        salir = true;
//                    }
//                }
//                trTarea = summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, Integer.MAX_VALUE);
//                if (trTarea<0)//en este caso la tarea no ha finalizado y debemos calcular su tiempo pendiente.
//                    tiempopendiente = tiempopendiente - (-1*trTarea);
//                else{//como la tarea ya ha finalizado, obtenemos su tiempo de respuesta, lo sumamos al tiempo de respuesta total e indicamos que el tiempo pendiente es 0.
//                    trTotal = trTotal + trTarea;
//                    tiempopendiente = 0;
//                }
//            }   
//        }
//        return (int) trTotal/ (int) BackgroundServer.getAperiodicTaskGroup().getNumTasks();
        
        //Versión Luis:
        ListIterator<Node> inode = summary.getSummaryListIterator();
        Server.getAperiodicTaskGroup().sortByArrivalTime();
        Iterator<Task> itask = Server.getAperiodicTaskGroup().taskGroup.iterator();
        Node node =new Node (0,0);
        AperiodicTask aperiodicTask;
        int remainingComputation;
        int totalResponseTime   = 0;
        int numtasques          = 0;
        boolean handled = false;
        
        while (inode.hasNext() && itask.hasNext()){
            aperiodicTask = (AperiodicTask) itask.next();
            Summary.iterateUntilTime(inode,aperiodicTask.getArrivalTime());
            if (!inode.hasNext())
                if(numtasques == 0)
                    return 0;
            else
                return totalResponseTime/numtasques;
            remainingComputation = aperiodicTask.getComputationTime();
            handled = false;
            while(remainingComputation > 0 || !handled){
                handled = true;
                Summary.iterateUntilFreeNode(inode);
                if (!inode.hasNext())
                    return totalResponseTime/numtasques;
                node = Summary.addTaskToFreeNode(inode, aperiodicTask, aperiodicTask.getArrivalTime(), remainingComputation, Integer.MAX_VALUE);
                remainingComputation -= (node.getStopTime() - node.getStartTime()); 
            }            
            totalResponseTime += node.getStopTime() - aperiodicTask.getArrivalTime();
            numtasques++;  
        }
        if(numtasques == 0)
                    return 0;
            else
                return totalResponseTime/numtasques;
    }  
}

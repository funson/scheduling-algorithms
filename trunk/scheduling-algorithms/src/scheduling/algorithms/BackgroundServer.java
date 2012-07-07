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
        super("Background Server", Float.MAX_VALUE, Float.MAX_VALUE);
    }
    
    @Override
    /**
     * Clase que se encarga de planificar un grupo de tareas aperiódicas
     */
    public float scheduleAperiodicTaskGroup(Summary summary) {
        float trTotal = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = BackgroundServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        boolean salir;
        Task tarea;
        float tiempopendiente;
        float trTarea = 0;
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
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
                trTarea = summary.addTaskToFreeNode(AperiodicNodeIterator, tarea, ((AperiodicTask) tarea).getArrivalTime(), tiempopendiente, Float.MAX_VALUE);
                if (trTarea<0)//en este caso la tarea no ha finalizado y debemos calcular su tiempo pendiente.
                    tiempopendiente = tiempopendiente - (-1*trTarea);
                else{//como la tarea ya ha finalizado, obtenemos su tiempo de respuesta, lo sumamos al tiempo de respuesta total e indicamos que el tiempo pendiente es 0.
                    trTotal = trTotal + trTarea;
                    tiempopendiente = 0;
                }
            }   
        }
        return (float) trTotal/ (float) BackgroundServer.getAperiodicTaskGroup().getNumTasks();
    }
    
}

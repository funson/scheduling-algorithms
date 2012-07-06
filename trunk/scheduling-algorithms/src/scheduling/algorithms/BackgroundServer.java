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

    /**
     * Esta clase calcula el nombre de tareas aperiódicas de un grupo
     * @return 
     */
    public int NumberOfTasks(){
       int numberoftasks=0;
        Iterator<Task> aperiodicTaskIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup =  (AperiodicTaskGroup) BackgroundServer.getAperiodicTaskGroup();
        aperiodicTaskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        while (aperiodicTaskIterator.hasNext()){ 
            aperiodicTaskIterator.next();
            numberoftasks++;
        }
        return numberoftasks;
    }
    
    @Override
    /**
     * Clase que se encarga de planificar un grupo de tareas aperiódicas
     */
    public float scheduleAperiodicTaskGroup(Summary summary) {
        float [] tinicio = new float [NumberOfTasks()];
        float [] tfin = new float [NumberOfTasks()];
        for (int i=0; i<tinicio.length;i++){
            tinicio[i] = 0;
            tfin[i] = 0;
        }
        int contador = 0;
        Iterator<Task> aperiodicTaskIterator;
        ListIterator<Node> AperiodicNodeIterator;
        AperiodicTaskGroup clonedAperiodicTaskGroup = BackgroundServer.getAperiodicTaskGroup();    
        clonedAperiodicTaskGroup.sortByArrivalTime();
        Node nodolibre = null;
        boolean salir;
        Task tarea;
        float tiempopendiente, sumatr;
        float capacidadnodo;
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
                capacidadnodo = nodolibre.getStopTime() - nodolibre.getStartTime();
                if (((AperiodicTask) tarea).getArrivalTime()> nodolibre.getStartTime() &&//crea: nodo libre-nodo con tarea-nodo libre
                        (((AperiodicTask) tarea).getArrivalTime() + tarea.getComputationTime()) < nodolibre.getStopTime()){
                    AperiodicNodeIterator.add(new Node(((AperiodicTask) tarea).getArrivalTime(), ((AperiodicTask) tarea).getArrivalTime()
                            + tarea.getComputationTime(), tarea));
                    AperiodicNodeIterator.add(new Node(((AperiodicTask) tarea).getArrivalTime()
                            + tarea.getComputationTime(), nodolibre.getStopTime()));   
                    nodolibre.setStopTime(((AperiodicTask) tarea).getArrivalTime());
                    tiempopendiente = 0;
                    tinicio[contador] = ((AperiodicTask) tarea).getArrivalTime();
                    tfin[contador] = (((AperiodicTask) tarea).getArrivalTime() + tarea.getComputationTime());
                }else if (((AperiodicTask) tarea).getArrivalTime()<= nodolibre.getStartTime() &&//crea: nodo con tarea-nodo libre
                        (nodolibre.getStartTime() + tiempopendiente) < nodolibre.getStopTime()){
                    nodolibre.setTask(tarea);
                    if (nodolibre.getStartTime() + tiempopendiente < nodolibre.getStopTime())
                        AperiodicNodeIterator.add(new Node(nodolibre.getStartTime() + tiempopendiente, nodolibre.getStopTime()));
                    nodolibre.setStopTime(nodolibre.getStartTime() + tiempopendiente);
                    tinicio[contador] = ((AperiodicTask) tarea).getArrivalTime();
                    tfin[contador] = (nodolibre.getStartTime() + tiempopendiente);
                    tiempopendiente = 0; 
                }else if (((AperiodicTask) tarea).getArrivalTime()> nodolibre.getStartTime() &&//crea: nodo libre-nodo con tarea
                        (((AperiodicTask) tarea).getArrivalTime() + tarea.getComputationTime()) >= nodolibre.getStopTime()){
                    AperiodicNodeIterator.add(new Node(((AperiodicTask) tarea).getArrivalTime(), nodolibre.getStopTime(), tarea));
                    tinicio[contador] = ((AperiodicTask) tarea).getArrivalTime();
                    if ((((AperiodicTask) tarea).getArrivalTime() + tarea.getComputationTime()) == nodolibre.getStopTime()){
                        tiempopendiente = 0;
                        tfin[contador] = nodolibre.getStopTime();
                    }else{
                        tiempopendiente = tiempopendiente - (nodolibre.getStopTime() - ((AperiodicTask) tarea).getArrivalTime());
                    }
                    nodolibre.setStopTime(((AperiodicTask) tarea).getArrivalTime());
                }else{//modifica el nodo libre para que sea un nodo con tarea
                    if (((AperiodicTask) tarea).getArrivalTime()== nodolibre.getStartTime() &&
                        (((AperiodicTask) tarea).getArrivalTime() + tarea.getComputationTime()) == nodolibre.getStopTime()){//la tarea cabe en el nodo libre
                        nodolibre.setTask(tarea);
                        tiempopendiente = 0;
                        tinicio[contador] = nodolibre.getStartTime();
                        tfin[contador] = nodolibre.getStopTime();
                    }else{//el nodolibre no es suficiente para almacenar la tarea
                        nodolibre.setTask(tarea);
                        tiempopendiente = tiempopendiente - capacidadnodo;
                    }
                }
            }  
            contador++;
        }
        sumatr=0;
        for (int i=0; i<tinicio.length;i++){
            sumatr += (tfin[i] - tinicio[i]);
        }
        return (float) (sumatr/NumberOfTasks());
    }
    
}

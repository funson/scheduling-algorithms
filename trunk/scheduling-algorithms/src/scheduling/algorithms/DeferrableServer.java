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
public class DeferrableServer extends Server {
    
    public DeferrableServer(float period, float capacity){
        super("Deferrable Server", period, capacity);
    }

    @Override
    public float scheduleAperiodicTaskGroup(Summary summary) {
        //throw new UnsupportedOperationException("Not supported yet.");
        AperiodicTaskGroup cloneAperiodicTaskGroup = (AperiodicTaskGroup)DeferrableServer.getAperiodicTaskGroup();
        Iterator<Task> aIterator = cloneAperiodicTaskGroup.taskGroup.iterator();
        cloneAperiodicTaskGroup.sortByArrivalTime();
        float auxCapacity = this.getComputationTime();  //Tiempo restante de computación
        float auxPeriod = this.getPeriod();             //Tiempo restante de periodo
        int ciclo = 0;                                  //Ciclo Actual
        float tiempoActual=0;                           //tiempo Actual
        float tiempoPendiente=0;                        //Tiempo Pendiente
        float tiempoTotalTareas=0;                      //Tiempo total para calcular el tiempo de respuesta
        ListIterator<Node> listaNodos = summary.getSummaryListIterator();
        while (aIterator.hasNext()){
            AperiodicTask aTask = (AperiodicTask)aIterator.next();
            tiempoPendiente = aTask.getComputationTime();
            tiempoActual = aTask.getArrivalTime();
            //Calcular tiempo de respuesta
            while(tiempoPendiente != 0){
                if (listaNodos.hasNext()){
                    Node node = listaNodos.next();
                    if (node.isFree()){
                        //Calculamos ciclo por si se reinicia la capacidad y el tiempo de periodo restante
                        if (node.getStartTime()>tiempoActual){
                            tiempoActual = node.getStartTime();
                        }
                        if (ciclo != calcularCiclo(tiempoActual)){
                               ciclo = calcularCiclo(tiempoActual);
                               auxCapacity = this.getComputationTime();
                        }
                        auxPeriod = calcularPeriodoRestante(tiempoActual); 
                        if (auxCapacity==0){
                            ciclo = ciclo+1;
                            tiempoActual = ciclo*this.period;
                            auxCapacity = this.computationTime;
                            auxPeriod = this.period;
                        }
                        //Buscamos el caso
                        if (tiempoPendiente<=auxPeriod){
                           if (tiempoPendiente<=auxCapacity){
                               //En este caso tenemos una tarea que cabe en el nodo dentro del periodo y de su capacidad
                               insertarNodo(tiempoActual, tiempoPendiente, node, aTask, listaNodos);
                               auxPeriod = auxPeriod - (tiempoPendiente);
                               auxCapacity = auxCapacity - tiempoPendiente;
                               tiempoPendiente = 0;
                           }else{
                               //En este caso tenemos una tarea que no cabe por la capacidad
                               insertarNodo(tiempoActual, auxCapacity, node, aTask, listaNodos);
                               auxPeriod = auxPeriod - (auxCapacity);
                               tiempoPendiente = tiempoPendiente - auxCapacity;
                               auxCapacity = 0;
                           }
                        }else{
                           if (auxPeriod<auxCapacity){
                               //En este caso tenemos una tarea que no cabe por el periodo
                               insertarNodo(tiempoActual, auxPeriod, node, aTask, listaNodos);
                               tiempoPendiente = tiempoPendiente - auxPeriod;
                               auxPeriod = 0;                                                              
                           }else{
                               //En este caso tenemos una tarea que no cabe ni por el periodo y menos por la capacidad
                               insertarNodo(tiempoActual, auxCapacity, node, aTask, listaNodos);
                               tiempoPendiente = tiempoPendiente - auxCapacity;
                               auxPeriod = 0;
                               auxCapacity = 0;
                           }
                        }
                    }  
                    if (tiempoPendiente==0){
                        tiempoTotalTareas = tiempoTotalTareas + ((tiempoActual+1) - aTask.getArrivalTime());
                    }
                }
                
            }
        }
        //ListIterator<Node> aux2 = summary.getSummaryListIterator();
       // this.visualizarListaNodosFinal(aux2);
        return (tiempoTotalTareas/(float)cloneAperiodicTaskGroup.getNumTasks());
    }
    
    private void visualizarListaNodosFinal(ListIterator<Node> aux2){
        while (aux2.hasNext()){
            Node n = aux2.next();
            if (n.isFree()){
                System.out.println("Nodo libre"+
                    " "+n.getStartTime()+" "+
                    n.getStopTime());
            }else{
            System.out.println("Nodo "+n.getTask().getName()+
                    " "+n.getStartTime()+" "+
                    n.getStopTime());
            }
        }
    }
    
    private int calcularCiclo(float tiempoActual) {
        return (int)(tiempoActual / this.getPeriod());
    }
    
    private float calcularPeriodoRestante(float tiempoActual){
        int ciclo = calcularCiclo(tiempoActual);
        float aux = ((ciclo+1)*this.getPeriod())-tiempoActual;
        return ((ciclo+1)*this.getPeriod())-tiempoActual;
    }
    
    private void insertarNodo(float tActual, float duracion, Node nodo, Task t , ListIterator<Node> lNodos){
        //La tarea empieza igual que el nodo
        if (tActual == nodo.getStartTime()){
            float tfinal = nodo.getStopTime();
            nodo.setStopTime(tActual + duracion);
            nodo.setTask(t);
            Node nNode = new Node(tActual +duracion, tfinal);
            lNodos.add(nNode);
            lNodos.previous();
        } else {
            //La tarea no empieza cuando empieza el nodo
            float tfinal = nodo.getStopTime();
            nodo.setStopTime(tActual);
            Node nTarea = new Node(tActual, duracion+tActual, t);
            lNodos.add(nTarea);
            Node nResto = new Node(tActual+duracion, tfinal);
            lNodos.add(nResto);
            lNodos.previous();
        }
    }
}

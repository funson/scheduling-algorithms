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

    public DeferrableServer(double period, double capacity) {
        super("DS", period, capacity);
    }

    @Override
    public double scheduleAperiodicTaskGroup(Summary summary) {
        ListIterator<Node> inode = summary.getSummaryListIterator();
        Server.getAperiodicTaskGroup().sortByArrivalTime();
        Iterator<Task> aIterator = Server.getAperiodicTaskGroup().taskGroup.iterator();
        Node node = new Node(0, 0);
        
        int auxCapacity = this.getComputationTime();  //Tiempo restante de computación
        int auxPeriod = this.getPeriod();             //Tiempo restante de periodo
        int cicloactual = 0;                                  //Ciclo Actual
        int ultimociclo = 0;
        int tiempoActual = 0;                           //tiempo Actual
        int tiempoPendiente = 0;                        //Tiempo Pendiente
        double tiempoTotalTareas = 0;                      //Tiempo total para calcular el tiempo de respuesta
        int ntareas = 0;       
        boolean handled = false;
        
        while (aIterator.hasNext() && inode.hasNext()) {
            AperiodicTask aTask = (AperiodicTask) aIterator.next();
            node = Summary.iterateUntilTime(inode, aTask.getArrivalTime());
            if (!inode.hasNext()) {
                return tiempoTotalTareas / ntareas;
            }
            
            handled = false;
            tiempoPendiente = aTask.getComputationTime();
            while (tiempoPendiente != 0 || !handled) {
                handled = true;
                
                //Si el servidor no tiene capacidad va al siguiente periodo
                if (auxCapacity == 0){
                    cicloactual ++;
                    Summary.iterateUntilTime(inode, cicloactual*period); 
                    if (!inode.hasNext()) {
                        return tiempoTotalTareas / ntareas;
                    }
                }
                
                
                //Busca el siguiente nodo libre
                Summary.iterateUntilFreeNode(inode);
                if (!inode.hasNext()) {
                    return tiempoTotalTareas / ntareas;
                }
                
                node = inode.next();
                inode.previous();
                cicloactual = calcularCiclo(node.getStartTime());
                
                if (ultimociclo < cicloactual)
                    auxCapacity = this.getComputationTime();
                
                ultimociclo = cicloactual;
                
                node = Summary.addTaskToFreeNode(inode, aTask, aTask.getArrivalTime(), tiempoPendiente, auxCapacity);
                tiempoPendiente -= (node.getStopTime() - node.getStartTime());
                auxCapacity     -= (node.getStopTime() - node.getStartTime());
                
                cicloactual = calcularCiclo(node.getStopTime());               
                
                //Si hemos cambiado de ciclo durante la ejecuciónd e una tarea. No hay que restaurar completamente 
                // la capacidad. Ya que sería equivalente a que el periodo empieza en el momento de haber acabado la ejecución
                // cuando en realidad el periodo ha empezado antes.
                if (ultimociclo < cicloactual){
                    auxCapacity = this.getComputationTime()-(node.getStopTime() - cicloactual*period);
                }
            }
            
            tiempoTotalTareas = tiempoTotalTareas + (node.getStopTime() - aTask.getArrivalTime());
            ntareas++;
        }
        
        if(ntareas == 0)
            return 0;
        else
            return tiempoTotalTareas/ntareas;
        
        //throw new UnsupportedOperationException("Not supported yet.");
        /*ListIterator<Node> inode = summary.getSummaryListIterator();
        Server.getAperiodicTaskGroup().sortByArrivalTime();
        Iterator<Task> aIterator = Server.getAperiodicTaskGroup().taskGroup.iterator();
        Node node = new Node(0, 0);
        
        int auxCapacity = this.getComputationTime();  //Tiempo restante de computación
        int auxPeriod = this.getPeriod();             //Tiempo restante de periodo
        int ciclo = 0;                                  //Ciclo Actual
        int tiempoActual = 0;                           //tiempo Actual
        int tiempoPendiente = 0;                        //Tiempo Pendiente
        double tiempoTotalTareas = 0;                      //Tiempo total para calcular el tiempo de respuesta
        int ntareas = 0;       
        boolean handled = false;
        
        while (aIterator.hasNext() && inode.hasNext()) {
            AperiodicTask aTask = (AperiodicTask) aIterator.next();
            tiempoPendiente = aTask.getComputationTime();
            if (tiempoActual <= aTask.getArrivalTime()) {
                tiempoActual = aTask.getArrivalTime();
            }
            if (ciclo < calcularCiclo(tiempoActual)) {
                auxCapacity = this.getComputationTime();
                auxPeriod = this.getPeriod();
            }
            Summary.iterateUntilTime(inode, tiempoActual);
            if (!inode.hasNext()) {
                return tiempoTotalTareas / ntareas;
            }
            ntareas++;
            handled = false;
            while (tiempoPendiente != 0 || !handled) {
                handled = true;
                Summary.iterateUntilFreeNode(inode);
                if (!inode.hasNext()) {
                    return tiempoTotalTareas / ntareas;
                }
                node = Summary.addTaskToFreeNode(inode, aTask, tiempoActual, tiempoPendiente, Math.min(auxPeriod, auxCapacity));
                tiempoPendiente = tiempoPendiente - (node.getStopTime() - node.getStartTime());
                auxCapacity = auxCapacity - (node.getStopTime() - node.getStartTime());
                auxPeriod = calcularPeriodoRestante(node.getStopTime());
                tiempoActual = node.getStopTime();
                ciclo = calcularCiclo(node.getStopTime() - 1);
                if (auxCapacity == 0 || auxPeriod == 0) {
                    tiempoActual = (ciclo + 1) * this.getPeriod();
                    Summary.iterateUntilTime(inode, tiempoActual);
                    if (!inode.hasNext()) {
                        return tiempoTotalTareas / ntareas;
                    }
                    auxCapacity = this.getComputationTime();
                    auxPeriod = this.getPeriod();
                }
            }
            if (tiempoPendiente == 0) {
                tiempoTotalTareas = tiempoTotalTareas + (node.getStopTime() - aTask.getArrivalTime());
            }
        }
        
        if(ntareas == 0)
                    return 0;
            else
                return tiempoTotalTareas/ntareas;*/
    }

    private void visualizarinodeFinal(ListIterator<Node> aux2) {
        while (aux2.hasNext()) {
            Node n = aux2.next();
            if (n.isFree()) {
                System.out.println("Nodo libre"
                        + " " + n.getStartTime() + " "
                        + n.getStopTime());
            } else {
                System.out.println("Nodo " + n.getTask().getName()
                        + " " + n.getStartTime() + " "
                        + n.getStopTime());
            }
        }
    }

    private int calcularCiclo(int tiempoActual) {
        return (int) (tiempoActual / this.getPeriod());
    }

    private int calcularPeriodoRestante(int tiempoActual) {
        int ciclo = calcularCiclo(tiempoActual);
        return ((ciclo + 1) * this.getPeriod()) - tiempoActual;
    }
}

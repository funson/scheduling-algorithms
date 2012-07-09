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
    public int scheduleAperiodicTaskGroup(Summary summary) {
        //throw new UnsupportedOperationException("Not supported yet.");
        ListIterator<Node> inode = summary.getSummaryListIterator();
        Server.getAperiodicTaskGroup().sortByArrivalTime();
        Iterator<Task> aIterator = Server.getAperiodicTaskGroup().taskGroup.iterator();
        Node node = new Node(0, 0);
        
        int auxCapacity = this.getComputationTime();  //Tiempo restante de computación
        int auxPeriod = this.getPeriod();             //Tiempo restante de periodo
        int ciclo = 0;                                  //Ciclo Actual
        int tiempoActual = 0;                           //tiempo Actual
        int tiempoPendiente = 0;                        //Tiempo Pendiente
        int tiempoTotalTareas = 0;                      //Tiempo total para calcular el tiempo de respuesta
        int ntareas = 0;       
        
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
            while (tiempoPendiente != 0) {
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
        //ListIterator<Node> aux2 = summary.getSummaryListIterator();
        //this.visualizarinodeFinal(aux2);
        return (tiempoTotalTareas / (int) ntareas);
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

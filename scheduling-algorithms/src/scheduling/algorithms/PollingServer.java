/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *Clase que representa a un Servidor Interrogador
 * 
 * @author Juanito
 */
public class PollingServer extends Server {

    public PollingServer(double period, double capacity) {
        super("PS", period, capacity);
    }

    /**
     * 
     * @param summary
     * @return Tiempo medio de respuesa 
     */
    @Override
    public int scheduleAperiodicTaskGroup(Summary summary) {


        //Obtenemos el iterador de nodos sobre el cual insertaremos las tareas periodicas anteriores
        ListIterator<Node> nodeIterator = summary.getSummaryListIterator();


        //Primero  ordenamos las tareas aperiodicas por su tiempo de llegada
        AperiodicTaskGroup clonedAperiodicTaskGroup = (AperiodicTaskGroup) PollingServer.getAperiodicTaskGroup();
        clonedAperiodicTaskGroup.sortByArrivalTime();

        // Variable auxiliar para almacenar el tiempo restante que quede por ejecutar de un
        int remainingTimeTask;
        //Variable para almacenar el tiempo de  
        int acumulatedResponseTime = (int) 0.0;
        //Variable que marca el instante de finalización de una tarea
        int finishedTimeTask = (int) 0.0;
        int numTasks = 0;


        Iterator<Task> taskIterator = clonedAperiodicTaskGroup.taskGroup.iterator();
        if (taskIterator.hasNext()) {

            //Obtenemos el primer elemento para iterar
            AperiodicTask task = (AperiodicTask) taskIterator.next();
            remainingTimeTask = task.getComputationTime();



            while (nodeIterator.hasNext() && (taskIterator.hasNext() || remainingTimeTask != 0)) {
                Node node = nodeIterator.next();
                if (node.isFree()) {

                    int stopNodePeriod = beforePeriod(node, task);
                    if (stopNodePeriod != -1) {

                        int capacityNodeTime = stopNodePeriod - node.getStartTime();

                        //Ocupa el tiempo de capacidad Completo
                        if (capacityNodeTime == remainingTimeTask) {

                            //La tarea asignada no ocupa todo el nodo y hay que añadir uno nuevo
                            // con el tiempo no asignado
                            if (stopNodePeriod != node.getStopTime()) {
                                Node newNode = new Node(stopNodePeriod, node.getStopTime());
                                nodeIterator.add(newNode);

                                //Apuntamos al anterior elemento, a newNode
                                Node nodePrevious = nodeIterator.previous();
                            }
                            node.setStopTime(stopNodePeriod);
                            node.setTask(task);
                            remainingTimeTask = remainingTimeTask - capacityNodeTime;

                            if (remainingTimeTask == (int) 0.0) {
                                finishedTimeTask = stopNodePeriod;
                            }


                            //La tarea ocupa una parte y queda parte del tiempo sin asignar  
                        } else if (capacityNodeTime > remainingTimeTask) {

                            Node newNode = new Node(node.getStartTime() + remainingTimeTask, node.getStopTime());
                            nodeIterator.add(newNode);

                            //Apuntamos al anterior elemento, a newNode
                            Node nodePrevious = nodeIterator.previous();


                            node.setStopTime(node.getStartTime() + remainingTimeTask);
                            node.setTask(task);
                            remainingTimeTask = remainingTimeTask - remainingTimeTask;

                            if (remainingTimeTask == (int) 0.0) {
                                finishedTimeTask = node.getStartTime() + remainingTimeTask;
                            }


                            //Ocupa todo el tiempo de capacidad y aun que tiempo por asignar    
                        } else {

                            Node newNode = new Node(stopNodePeriod, node.getStopTime());
                            nodeIterator.add(newNode);

                            //Apuntamos al anterior elemento, a newNode
                            Node nodePrevious = nodeIterator.previous();

                            node.setStopTime(stopNodePeriod);
                            node.setTask(task);
                            remainingTimeTask = remainingTimeTask - (stopNodePeriod - node.getStartTime());
                        }

                    } else {
                        int startNodePeriod = nextPeriod(node, task);
                        if (startNodePeriod != -1) {

                            //Se puede añadir una tarea al inicio del nodo
                            if (node.getStartTime() == startNodePeriod) {

                                //La capacidad ocupa todo el nodo
                                if (node.getStartTime() + computationTime == node.getStopTime()) {

                                    //Toda la tarea ocupa todo el tiempo
                                    if (computationTime == remainingTimeTask) {
                                        node.setTask(task);
                                        remainingTimeTask = remainingTimeTask - computationTime;
                                    } else {
                                        Node newNode = new Node(node.getStartTime() + remainingTimeTask, node.getStopTime());
                                        nodeIterator.add(newNode);

                                        //Apuntamos al anterior elemento, a newNode
                                        Node nodePrevious = nodeIterator.previous();

                                        node.setStopTime(node.getStartTime() + remainingTimeTask);
                                        node.setTask(task);
                                        remainingTimeTask = remainingTimeTask - remainingTimeTask;

                                    }

                                    if (remainingTimeTask == (int) 0.0) {
                                        finishedTimeTask = node.getStopTime();
                                    }


                                    //La capacidad no ocupa toda la parte del nodo    
                                } else {

                                    int stopComputationPeriod = startNodePeriod + computationTime;

                                    if (startNodePeriod + remainingTimeTask >= stopComputationPeriod) {
                                        Node newNode = new Node(startNodePeriod + computationTime, node.getStopTime());
                                        nodeIterator.add(newNode);

                                        //Apuntamos al anterior elemento, a newNode
                                        Node nodePrevious = nodeIterator.previous();

                                        node.setStopTime(node.getStartTime() + computationTime);
                                        node.setTask(task);
                                        remainingTimeTask = remainingTimeTask - computationTime;

                                    } else if (startNodePeriod + remainingTimeTask < stopComputationPeriod) {

                                        Node newNode = new Node(startNodePeriod + remainingTimeTask, node.getStopTime());
                                        nodeIterator.add(newNode);

                                        //Apuntamos al anterior elemento, a newNode
                                        Node nodePrevious = nodeIterator.previous();

                                        node.setStopTime(node.getStartTime() + remainingTimeTask);
                                        node.setTask(task);
                                        remainingTimeTask = remainingTimeTask - remainingTimeTask;

                                        if (remainingTimeTask == (int) 0.0) {
                                            finishedTimeTask = node.getStartTime() + remainingTimeTask;
                                        }

                                    }

                                }

                                //Se puede añadir una tarea al final del nodo    
                            } else if (startNodePeriod + this.computationTime == node.getStopTime()) {


                                if (remainingTimeTask >= computationTime) {

                                    Node newNode = new Node(startNodePeriod, node.getStopTime(), task);
                                    nodeIterator.add(newNode);

                                    node.setStopTime(startNodePeriod);
                                    remainingTimeTask = remainingTimeTask - computationTime;

                                } else {


                                    //AÑADIR 3 NODOS  VACIO | OCUPADO | VACIO

                                    Node newNode = new Node(startNodePeriod, startNodePeriod + remainingTimeTask, task);
                                    nodeIterator.add(newNode);

                                    Node newNode2 = new Node(startNodePeriod + remainingTimeTask, node.getStopTime());
                                    nodeIterator.add(newNode2);

                                    //Apuntamos al anterior elemento, a newNode
                                    Node nodePrevious = nodeIterator.previous();

                                    node.setStopTime(startNodePeriod);

                                    remainingTimeTask = remainingTimeTask - remainingTimeTask;

                                    if (remainingTimeTask == (int) 0.0) {
                                        finishedTimeTask = startNodePeriod + remainingTimeTask;
                                    }


                                }





                                //Se puede añadir una tarea en medio del nodo    
                            } else {


                                int startComputationTime;
                                int durationComputationTime;

                                if (task.getArrivalTime() > startNodePeriod) {
                                    startComputationTime = task.getArrivalTime();
                                    durationComputationTime = (startNodePeriod + computationTime) - task.getArrivalTime();
                                } else {
                                    startComputationTime = startNodePeriod;
                                    durationComputationTime = computationTime;
                                }


                                if (remainingTimeTask > durationComputationTime) {

                                    Node newNode = new Node(startComputationTime, startComputationTime + durationComputationTime, task);
                                    nodeIterator.add(newNode);

                                    Node newNode2 = new Node(startComputationTime + durationComputationTime, node.getStopTime());
                                    nodeIterator.add(newNode2);

                                    //Apuntamos al anterior elemento, a newNode
                                    Node nodePrevious = nodeIterator.previous();

                                    node.setStopTime(startComputationTime);

                                    remainingTimeTask = remainingTimeTask - durationComputationTime;


                                } else {

                                    //AÑADIR 3 NODOS  VACIO | OCUPADO | VACIO

                                    Node newNode = new Node(startComputationTime, startComputationTime + remainingTimeTask, task);
                                    nodeIterator.add(newNode);

                                    Node newNode2 = new Node(startComputationTime + remainingTimeTask, node.getStopTime());
                                    nodeIterator.add(newNode2);

                                    Node aux = nodeIterator.previous();

                                    //Apuntamos al anterior elemento, a newNode

                                    node.setStopTime(startComputationTime);

                                    finishedTimeTask = startComputationTime + remainingTimeTask;
                                    remainingTimeTask = remainingTimeTask - remainingTimeTask;

                                }

                            }
                        }



                    }


                }

                //Se ha computado toda la tarea y se ejecuta la siguiente
                if (remainingTimeTask == (int) 0.0) {
                    numTasks++;
                    acumulatedResponseTime += ((finishedTimeTask) - task.getArrivalTime());
                    if (taskIterator.hasNext()) {

                        //Obtenemos el primer elemento para iterar                        
                        task = (AperiodicTask) taskIterator.next();
                        remainingTimeTask = task.getComputationTime();

                    }
                }
            }

        }

        if(numTasks == 0)
            return 0;
        return (int) acumulatedResponseTime / (int) numTasks;
    }

    /**
     * Método que devuelve dado un nodo el siguiente periodo libre para ejecutar una tarea
     * @param node Nodo a ejecutar
     * @param task  Tarea a ejecutar
     * @return  Periodo libre
     */
    private int nextPeriod(Node node, AperiodicTask task) {

        int time = node.getStartTime();

        while (((time % period != 0) && (time < node.getStopTime())) || time <= (task.getArrivalTime() - this.computationTime)) {
            time++;
        }

        if (time % period == 0) {
            return time;
        } else {
            return -1;
        }

    }

    /**
     * Método que devuelve la último posición disponible de capacidad en la cual ejecutar
     * una tarea
     * @param node Nodo el cual se analiza
     * @param task Tarea ejecutar
     * @return 
     */
    private int beforePeriod(Node node, AperiodicTask task) {

        int time = node.getStartTime() - 1;
        while ((time % period != 0) && (time >= (node.getStartTime() - computationTime))) {
            time--;
        }

        if ((time % period == 0) && (task.getArrivalTime() <= node.getStartTime()) && ((time + computationTime) > node.getStartTime())) {
            return time + computationTime;
        } else {
            return -1;
        }
    }
}

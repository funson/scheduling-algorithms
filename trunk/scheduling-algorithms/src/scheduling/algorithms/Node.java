/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

/**
 *
 * @author Juanito
 */
public class Node {
    private Task    task;
    private float   startTime;
    private float   stopTime;
    
    /**
     * Constructor para crear un nodo libre. Representa un período de tiempo en el que no hay ninguna tarea planificada.
     * @param startTime El instante de tiempo inicial en que no hay ninguna tarea planificada.
     * @param stopTime  El instante de tiempo final en que no hay ninguna tarea planificada.
     */
    public Node(float startTime, float stopTime){
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.task = null;
    }
    
    /**
     * Constructor para crear un nodo ocupado por una tarea. Representa un periodo de tiempo durante el cual se ha planificado una tarea.
     * @param startTime El instante de tiempo inicial en el que se ha planificado la tarea.
     * @param stopTime  El instante de tiempo final en el que se ha planificado la tarea.
     * @param task      La tarea que se ha planificado.
     */
    public Node(float startTime, float stopTime, Task task){
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.task = task;
    }
    
    /**
     * Método para conocer si un nodo está libre, es decir, si no hay ninguna tarea planificada durante el intervalo de tiempo que representa.
     * @return Devuelve {@code true} si el nodo está libre.
     */
    public boolean isFree(){
        return getTask() == null;
    }

    /**
     * @return La tarea
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task the task to set
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return the startTime
     */
    public float getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stopTime
     */
    public float getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(float stopTime) {
        this.stopTime = stopTime;
    }
}

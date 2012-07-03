/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que almacena todos los tiempos de respuesta medios de cada servidor por cada carga total simulada.
 * @author Lluis
 */
public class Result {
    
    private class ResultData{
        float  totalLoad;
        String servername;
        float  meanResponseTime;
        
        public ResultData(float totalLoad, String servername, float meanResponseTime){
            this.totalLoad        = totalLoad;
            this.servername       = servername;
            this.meanResponseTime = meanResponseTime;
        }
    }
    
    
    
    private ArrayList<ResultData> resultTable;
    
    public Result(){
        resultTable = new ArrayList<>();
    }
    
    /**
     * Mete el tiempo medio de respuesta de un servidor a una cierta carga total.
     * @param total_load Carga total (carga periódica + carga aperiódica del servidor)
     * @param serverName Nombre del tipo del servidor.
     * @param meanResponseTime Tiempo medio de respuesta de las tareas aperiódicas planificadas por el servidor.
     */
    public void addData(float total_load, String serverName, float meanResponseTime){
        resultTable.add(new ResultData(total_load, serverName, meanResponseTime));
    }
    
    /**
     * Método que devuelve el tiempo de respuesta medio registrado en el Resultado, dado un servidor i una determinada carga total.
     * @param total_load Carga total a determinar
     * @param serverName Nombre del tipo de servidor
     * @return Tiempo de respuesta. Si no se ha encontrado devolverá -1. 
     */
    public float getData(float total_load,String serverName){
        Iterator<ResultData> i = resultTable.iterator();
        ResultData r = new ResultData(-1, serverName, -1);
        boolean found = false;
        while (i.hasNext() && ! found){
            r = i.next();
            found = r.servername.equals(serverName)&& r.totalLoad == total_load;
        }
        
        return found ? r.meanResponseTime : -1.0f;
        
    }
    
    /**
     * Guarda la información guardada en el Resultado, en un formato que sea fácil de leer por OpenOffice y
     * que permita, mediante pocos pasos, crear un gráfico.
     * @param url_file_name Url del fichero a guardar/crear.
     */
    public void saveResultsOdtFormat(String url_file_name){
        throw new UnsupportedOperationException("SaveResultsOdtFormat not implemented yet.");
    }
    
}

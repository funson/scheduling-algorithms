/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que almacena todos los tiempos de respuesta medios de cada servidor por cada carga total simulada.
 * @author Lluis
 */
public class Result {
    
    private class ResultData{
        double  totalLoad;
        String servername;
        double  meanResponseTime;
        
        public ResultData(double totalLoad, String servername, double meanResponseTime){
            this.totalLoad        = totalLoad;
            this.servername       = servername;
            this.meanResponseTime = meanResponseTime;
        }
    }        
    
    private ArrayList<ResultData> resultTable;
    private ArrayList<Double>  loadsInResult;
    private ArrayList<String> serverNamesInResult;
    
    public Result(){
        resultTable         = new ArrayList<>();
        loadsInResult       = new ArrayList<>();
        serverNamesInResult = new ArrayList<>();      
    }
    
    /**
     * Mete el tiempo medio de respuesta de un servidor a una cierta carga total.
     * @param total_load Carga total (carga periódica + carga aperiódica del servidor)
     * @param serverName Nombre del tipo del servidor.
     * @param meanResponseTime Tiempo medio de respuesta de las tareas aperiódicas planificadas por el servidor.
     */
    public void addData(double total_load, String serverName, double meanResponseTime){
        if (!isLoadInResult(total_load))
            getLoadsInResult().add(total_load);
        if (!isServerNameInResult(serverName))
            getServerNamesInResult().add(serverName);
        resultTable.add(new ResultData(total_load, serverName, meanResponseTime));
    }
    
    /**
     * Método que devuelve el tiempo de respuesta medio registrado en el Resultado, dado un servidor i una determinada carga total.
     * @param total_load Carga total a determinar
     * @param serverName Nombre del tipo de servidor
     * @return Tiempo de respuesta. Si no se ha encontrado devolverá -1. 
     */
    public double getData(double total_load, String serverName){
        Iterator<ResultData> i = resultTable.iterator();
        ResultData r = new ResultData(-1, serverName, -1);
        boolean found = false;
        while (i.hasNext() && ! found){
            r = i.next();
            found = r.servername.equals(serverName)&& r.totalLoad == total_load;
        }
        
        return found ? r.meanResponseTime : -1;
        
    }
    
    /**
     * Método que devuelve si un nombre de servidor se encuentra dentro del resultado.
     * @param servername Nombre del servidor a buscar
     * @return True si el nombre se ha encontrado
     */
    public boolean isServerNameInResult(String servername){
        return getServerNamesInResult().contains(servername);
    }
    
    /**
     * Método que devuelve si una carga total se encuentra dentro del resultado.
     * @param load Carga a buscar a buscar
     * @return True si la carga se ha encontrado
     */
    public boolean isLoadInResult(double load){
        return getLoadsInResult().contains(load);
    }
    
    /**
     * Devuelve la lista de cargas que hay registradas dentro del resultado
     * @return La lista de cargas
     */
    public ArrayList<Double> getLoadsInResult() {
        return loadsInResult;
    }

    /**
     * Devuelve la lista de nombres de servidors que hay dentro del resultado.
     * @return La lista de nombre de servidores.
     */
    public ArrayList<String> getServerNamesInResult() {
        return serverNamesInResult;
    }
    
    /**
     * Guarda la información guardada en el Resultado, en un formato que sea fácil de leer por OpenOffice y
     * que permita, mediante pocos pasos, crear un gráfico.
     * @param url_file_name Url del fichero a guardar/crear.
     */
    public void saveResultsOdsFormat(String urlFilename) throws IOException{
        Exporter.exportResultToOds(urlFilename, this);
    }
    
}

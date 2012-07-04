/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;

/**
 * Clase que gestiona la entrada de datos a la aplicación.
 * @author Juanito
 */
public class Importer {
    
    /**
     * Método que carga los datos del fichero de entrada. Separa las tareas periódicas y los servidores, los cuales mete en el array de servidores. Tener en cuenta que el Servidor Background no está en el fichero porque sus parámetros (capacidad y periodo) son constantes.
     * @param urlFile  Url del fichero a leer
     * @param taskSets Array de conjunto de tareas periódicas a crear e inicializar.
     * @param servers  Array de servidors a inicializar.
     */    
    public static void importTaskSets(String urlFile, ArrayList<TaskSet> taskSets, Server[] servers){
        taskSets   = new ArrayList<>();
        servers[0] = new BackgroundServer();
        throw new UnsupportedOperationException("Import task sets not supported yet.");        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static void importTaskSets(String urlFile, ArrayList<TaskSet> taskSets, ArrayList<Server[]> servers){
        taskSets   = new ArrayList<>();
        servers = new ArrayList<>();
        Server[] taskSetServers;
        
        // Informació de fitxer
        int currentSets = -1; // actual super conjunt de tasques
        // Utilització dels servidors
        float u;
        
        int taskGroup; // el grup de tasques actual, on s'han d'insertar les tasques (index)
        // Informació de la tasca actual
        String nom; 
        float t, c, ph;
        
        
        String sLine;
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader("C:/Users/Miguel/Desktop/Dades_generiques (1).txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while ((sLine = bf.readLine())!=null) {
                String aux[] = sLine.split("\t");
                int i = 0;
                while (i < aux.length){
                    if (!aux[i].equals("") ){
                        if (aux[i].contains("Up=")){
                            //System.out.println(aux[i].substring(3, aux[i].length()-1));
                            u = Integer.parseInt(aux[i].substring(3, aux[i].length()-1));
                            System.out.println(u);
                            currentSets++;
                            //Hem detectat un nou conjunt per tant inicialitzam el seu array de servidors i hi ficam el BS
                            taskSetServers = new Server[5];
                            taskSetServers[0] = new BackgroundServer();                            
                        }
                        else if (aux[i].contains("Grup tasques")){
                            //System.out.println(aux[i].charAt(aux[i].length()-1));
                            taskGroup = Integer.parseInt(aux[i].substring(aux[i].length()-1));
                            System.out.println(taskGroup);
                        }
                        else if (aux[i].contains("Px_")){
                            nom = aux[i];
                            i++; // Passam al seg item
                            while (aux[i].equals("\t") || aux[i].equals("") || aux[i].equals(" ")) i++;
                            t = Float.parseFloat(aux[i].replace(",", "."));
                            i++; // Passam al seg item
                            while (aux[i].equals("\t") || aux[i].equals("") || aux[i].equals(" ")) i++;
                            c = Float.parseFloat(aux[i].replace(",", "."));
                            i++; // Passam al seg item
                            while (aux[i].equals("\t") || aux[i].equals("") || aux[i].equals(" ")) i++;
                            ph = Float.parseFloat(aux[i].replace(",", "."));
                            
                            
                            System.out.println("Nom: " + nom);
                            System.out.println("Periode: " + t);
                            System.out.println("Temps de computació: " + c);
                            System.out.println("Fase: " + ph);
                            
                            
                            //arraydetasques.insert(taskGroup).
                        }
                        else if (aux[i].contains("U*s_DS")){
                            System.out.println("###############################");// a les dues linies segs tenim la informació dels servidors
                            // Lleguim la seg linia
                            sLine = bf.readLine();
                            aux = sLine.split("\t");
                            // Els tres primers son les dades dels servidor
                            int index = 0;
                            while (aux[index].equals("\t") || aux[index].equals("") || aux[index].equals(" ")) index++;
                            System.out.println(aux[index]);
                            index++;
                            while (aux[index].equals("\t") || aux[index].equals("") || aux[index].equals(" ")) index++;
                            System.out.println(aux[index]);
                            index++;
                            while (aux[index].equals("\t") || aux[index].equals("") || aux[index].equals(" ")) index++;
                            System.out.println(aux[index]);

                            // El 4t element es el primer de la seg linia
                            sLine = bf.readLine();
                            aux = sLine.split("\t");
                            // Els tres primers son les dades dels servidor
                            index = 0;
                            while (aux[index].equals("\t") || aux[index].equals("") || aux[index].equals(" ")) index++;
                            System.out.println(aux[index]);
                        }
                        /*A mesura que es recorre un conjunt, anar guardant els servidors a l'array:
                         * taskSetServers[numServer] = new ...
                         * en acabar de recorrer un conjunt, s'ha de ficar aquest array de servidors dins l'ArrayList de l'Scheduler:
                        servers.add(taskSetServers);*/
                    }
                    i++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

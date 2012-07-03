/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
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
    
    public void addData(float total_load, String serverName, float meanResponseTime){
        resultTable.add(new ResultData(total_load, serverName, meanResponseTime));
    }
    
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
    
    public void saveResultsOdtFormat(String url_file_name){
        throw new UnsupportedOperationException("SaveResultsOdtFormat not implemented yet.");
    }
    
}

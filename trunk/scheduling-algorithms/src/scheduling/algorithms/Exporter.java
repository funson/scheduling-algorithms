/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juanito
 */
public class Exporter {
    
    public static void exportResultToOds(String urlFile, Result result) throws IOException{
        ArrayList<String> serverNamesInResult = result.getServerNamesInResult();
        ArrayList<Double> loadsInResult       = result.getLoadsInResult();
        FileWriter fstream;
        String file = urlFile + ".ods";
        fstream = new FileWriter(file);
        try (BufferedWriter out = new BufferedWriter(fstream)) {
            for (int i=0;i<serverNamesInResult.size();i++){
                out.write(" \t" + serverNamesInResult.get(i));
            }
            out.write("\n");
            for (int i=0;i<loadsInResult.size();i++){
                out.write(Double.toString(loadsInResult.get(i)));
                for (int j=0;j<serverNamesInResult.size();j++){
                    out.write("\t" + result.getData(loadsInResult.get(i), serverNamesInResult.get(j)));
                }
                out.write("\n");
            }
        }
    }
}

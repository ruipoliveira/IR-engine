package deti.ir;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */

public class RIproject {
   
    public static void main(String[]args) throws FileNotFoundException, IOException, Exception{
       
       String pathDir = "/files-data/stack/"; 
       String pathStop = "/files-data/stopwords_en.txt"; 
       DocumentProcessor dp = new DocumentProcessor(System.getProperty("user.dir")+pathDir, //args[0]
       System.getProperty("user.dir")+pathStop); //args[1] 
       dp.start();
       
       //SearcherProcessor sp = new SearcherProcessor(System.getProperty("user.dir")+pathStop);
       //sp.start();

    }
    
    
    
        
     

    
    
}

package deti.ir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */

public class RIproject {
   
    public static void main(String[]args) throws FileNotFoundException, IOException, Exception{
       
    File fdir = new File(args[0]);
    File fstp = new File(args[1]);
    int memory = Integer.parseInt(args[2]); 

    if (fdir.exists() && fdir.isDirectory() && fstp.exists() && (memory >= 1 && memory <= 4096)) {
        DocumentProcessor dp = new DocumentProcessor(args[0], args[1], memory); 
        dp.start();
    }else{
        System.out.println("Parametros incorretos."); 
    }

    }
}

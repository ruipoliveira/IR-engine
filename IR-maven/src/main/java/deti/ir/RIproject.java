package deti.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */

public class RIproject {
    /**
     * funcao que inicia todo o processo
     * @param args args[0] para especificar o directorio onde se encontra o corpus a ler
     * args[1] para especificar em que directorio se enconra o ficheiro das stop words
     * args[2] memoria maxima que a JVM pode atingir durante o processo
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception 
     */
    public static void main(String[]args) throws FileNotFoundException, IOException, Exception{
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        
        File fdir = new File(args[0]);
        File fstp = new File(args[1]);
        int memory = Integer.parseInt(args[2]);
        
        System.out.println("1-indexer files\n2-search query\nInsira a sua opção: ");
        
        String op = br.readLine();
        
        
        if (Integer.parseInt(op) ==1){
            if (fdir.exists() && fdir.isDirectory() && fstp.exists() && (memory >= 1 && memory <= 4096)) {
                DocumentProcessor dp = new DocumentProcessor(args[0], args[1], memory); 
                dp.start();
            }else{
                System.out.println("Parametros incorretos."); 
            }
        }
        
       
        else if (Integer.parseInt(op) ==2){
            if (fdir.exists() && fdir.isDirectory() && fstp.exists() && (memory >= 1 && memory <= 4096)) {
                SearchProcessor search = new SearchProcessor(args[0], args[1]); 
                search.start();

            }else{
                System.out.println("Parametros incorretos."); 
            }
        }
        else{
            System.out.println("Opção inválida!"); 
        }
        
        
        
   
    }
}

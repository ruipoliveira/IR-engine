package deti.ir;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class RIproject {

    
    public static void main(String[]args){
       DocumentProcessor dp = new DocumentProcessor(2, "/home/gabriel/NetBeansProjects/IR-engine/IR-maven/src/main/java/Docs"); 
       dp.start();
        
    }
    
}

package deti.ir;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class RIproject {
    public static void main(String[]args){
        
       DocumentProcessor dp = new DocumentProcessor(2, System.getProperty("user.dir")+"/files-data/corpus", System.getProperty("user.dir")+"/files-data/stopwords_en.txt"); 
       dp.start();
    }
    
}

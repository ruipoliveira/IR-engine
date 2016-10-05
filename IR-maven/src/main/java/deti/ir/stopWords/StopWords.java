
package deti.ir.stopWords;

import java.util.List;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class StopWords {
    
    List<String> stopWords; // lista de stop words
    
    public StopWords(){
        
    }
    
            // verificar se e stop word
    public boolean isStopWord(String token){
        return stopWords.contains(token);
    }
}

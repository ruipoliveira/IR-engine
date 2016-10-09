package deti.ir.indexer;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Indexer {
    
    private final TokenFreqMap[] tokenFreq; 
    private final TokenIDDocFreqMap[] tokenIdDocFreq; 
    
    
    public Indexer(){
        tokenFreq = new TokenFreqMap[5]; 
        tokenIdDocFreq = new TokenIDDocFreqMap[5]; 
        
    }
    
    public void addTerm(int docID, String token){
        
    }
}

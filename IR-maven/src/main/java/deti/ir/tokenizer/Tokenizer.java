package deti.ir.tokenizer;

import deti.ir.corpusReader.CorpusReader;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Tokenizer {
    
    
    public Tokenizer(){
        
    }
    
    public String[] tokenizeDoc(String s) {
        return s.split("\n");
    }
    
    
    public String[] tokenizeTermo(String s) {
        return s.split(" ");
    }
        
   
    public boolean isValid(String t) {
        return t.length() > 2 && t.matches("(\\w+)");
    }
    
    
}

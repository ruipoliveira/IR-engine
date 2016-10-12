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
    
    public String[] tokenize(String s) {
        //return s.split(" "); 
        return s.split(" "); // replaceAll("\\b[a-z0-9]{2}\\b|\\W+", " ")
    }
    
    
    public boolean isDocID(String t) {
        String cenas[] = t.split(",");
        String id = cenas[0];
        
        /*String s = "";
        for(char c : t.toCharArray()){
            if(Character.isDigit(c))
                s = s + c;           
        }*/
        return  id.matches("[0-9]");
        //return t.split(",")[0].matches(t.) ;
    }
    
    

    public boolean isValid(String t) {
        return t.length() > 2 && t.matches("(\\w+)");
    }
    
    
}

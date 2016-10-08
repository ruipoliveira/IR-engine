package deti.ir.tokenizer;

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
        return s.replaceAll("\\b[a-z0-9]{2}\\b|\\W+", " ").split("(\\s)");
    }

    public boolean isValid(String t) {
        return t.length() > 2 && t.matches("(\\w+)");
    }
    

}

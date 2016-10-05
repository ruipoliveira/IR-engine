package deti.ir.tokenizer;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Tokenizer {
    
    
    public Tokenizer(){
        
    }
    
    public String[] tokenize(String s){
        String [] tkn = new String[443556]; // so para compilar
        return tkn;
    } 
    
    public boolean validate(String token) {
        //return t.length() > 2 && t.matches("(\\w+)");
        return true;
    }
}

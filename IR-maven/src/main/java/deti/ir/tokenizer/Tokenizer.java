package deti.ir.tokenizer;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Tokenizer {
    
    /**
     * Construtor do tokenizer
     */
    public Tokenizer(){
        
    }
    
    /**
     * Metodo que retorna um array de strings com todas os termos por linhas
     * @param s
     * @return 
     */
    public String[] tokenizeDoc(String s) {
        return s.split("\n");
    }
    
    /**
     * metodo que retorna um array de strings com cada um dos termos
     * @param s
     * @return 
     */
    public String[] tokenizeTermo(String s) {
        return s.split(" +");
    }
    
    /**
     * Metodo para verificar se um dado termo é valido ou nao
     * @param t
     * @return 
     */
    public boolean isValid(String t) {
        return t.length() > 2 && t.matches("(\\w+)");
    }
    
    
}

package deti.ir.stemmer;

import org.tartarus.snowball.*;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Stemmer {
    
    /**
     * Stemmer algorithm.
     */
    private final String algorithm;
    
    /**
     * Construtor do Stemmer que recebe o algoritmo a ser usado para o stemming dos termos
     * @param algorithm 
     */
    public Stemmer(String algorithm){
        this.algorithm = algorithm;  
    }
    
    /**
     * metodo que retorna o termo ja tokenizado
     * @param token
     * @return token
     */
    public String getStemmer(String token){
        Class stemClass;
        try {
            stemClass = Class.forName("deti.ir.stemmer." + algorithm);
            SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
            stemmer.setCurrent(token);
            if (stemmer.stem()){
                return stemmer.getCurrent();
            }
                
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {}
        return token;
    }
    
    /**
     * metodo que retorna o algoritmo de tokenizacao
     * @return 
     */
    public String getAlgorithm() {
        return algorithm;
    }
    
    
}

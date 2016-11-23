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
    
    private SnowballStemmer stemmer; 
    
    /**
     * Construtor do Stemmer que recebe o algoritmo a ser usado para o stemming dos termos
     * @param algorithm 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.lang.InstantiationException 
     * @throws java.lang.IllegalAccessException 
     */
    
    public Stemmer(String algorithm) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        this.algorithm = algorithm; 
        Class stemClass;
        stemClass = Class.forName("deti.ir.stemmer." + algorithm);
        stemmer = (SnowballStemmer) stemClass.newInstance();
            
    }
    
    /**
     * metodo que retorna o termo ja tokenizado
     * @param token
     * @return token
     */
    public String getStemmer(String token){
        stemmer.setCurrent(token);
        if (stemmer.stem()){
            return stemmer.getCurrent();
        }
                
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

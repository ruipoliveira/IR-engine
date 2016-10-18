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
    
    // podia ser passado por parametro mas apenas vai ser usado o english stemmer
    public Stemmer(){
        algorithm = "englishStemmer"; 
    }
    
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
}

package deti.ir;

import deti.ir.indexer.Indexer;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.io.BufferedReader;
import java.nio.file.Path;
import javax.management.Query;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class SearcherProcessor {
    private StopWords sw; 
    private Stemmer stemmer; 
    private Indexer indexer; 
    private Tokenizer tok; 
    private Path path;
    
    public SearcherProcessor() throws Exception{
        this.tok = new Tokenizer(); 
        this.indexer = new Indexer();
        this.stemmer = new Stemmer();
        this.sw = new StopWords(path); 
    }
    
    public void start(){
        
    }
    
    // buffRead to read user inputs
    private Query getQuery(BufferedReader buffRead){
        Query query = null;
        return query;
    }
}

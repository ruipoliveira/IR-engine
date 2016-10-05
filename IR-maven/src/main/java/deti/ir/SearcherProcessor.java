package deti.ir;

import deti.ir.indexer.Indexer;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import java.io.BufferedReader;
import javax.management.Query;

/**
 *
 * @author gabriel
 */

public class SearcherProcessor {
    private StopWords sw; 
    private Stemmer stemmer; 
    private Indexer indexer; 
    public SearcherProcessor(){
        
    }
    
    public void start(){
        
    }
    
    // buffRead to read user inputs
    private Query getQuery(BufferedReader buffRead){
        Query query = null;
        return query;
    }
}

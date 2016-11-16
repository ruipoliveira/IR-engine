package deti.ir;

import deti.ir.indexer.Indexer;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

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
    
    public SearcherProcessor(String pathStopwords) throws Exception{
        this.indexer = new Indexer();
        this.tok = new Tokenizer(); 
        this.stemmer = new Stemmer("englishStemmer");
        this.sw = new StopWords(Paths.get(pathStopwords)); 
    }
    
    public void start(){
        String search;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Searching...");
        System.out.println("Type @exit in order to stop the execution.");
        //Query query = getQuery(br);
        
        while(true) {
            search = getQueryContent(br);
            if(search.equals("@exit")) break;
            
            
            long start = System.currentTimeMillis(); 
            
            // Process query tokens 
            for (String queryTermo : tok.tokenizeTermo(search)){
                //System.out.println(termo); 
                if (tok.isValid(queryTermo)){
                    if(!sw.isStopWord(queryTermo)){ // se for stop word ignora, senao adiciona 
                       queryTermo = stemmer.getStemmer(queryTermo);
                       //System.out.println(queryTermo);
                    }   
                }
            }
        }
        //System.out.println(sw.getStopWords());
    }
    
    // buffRead to read user inputs
    /*private Query getQuery(BufferedReader buffRead){
        Query query = null;
        return query;
    }*/
    
    private String getQueryContent(BufferedReader br){
        String query = "";
        try {
            System.out.print("Type your query please: ");
            query = br.readLine().toLowerCase();
        } catch (IOException ex) {
        }
        
        return query;
    }
}

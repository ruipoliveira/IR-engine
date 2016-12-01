/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir;

import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import javax.management.Query;

/**
 *
 * @author gabriel
 */
public class SearchProcessor {
    private Stemmer stemmer;
    private StopWords sw;
    private final MemoryManagement memory;    
    private final int maxMem;
    private Tokenizer token;
    
    public SearchProcessor(String indexDirectory, String stopWords_dir, int maxMem) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        token = new Tokenizer(); 
        sw = new StopWords(Paths.get(stopWords_dir));
        stemmer = new Stemmer("englishStemmer");
        memory = new MemoryManagement(); 
        this.maxMem = maxMem; 
    }
    
    public void start() throws IOException{
        String querySearch;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type @exit in order to stop the search operation.");
        //String query = queryContent(br);
        while(true){
                querySearch = queryContent(br);
                if(querySearch.equals("@exit")) break;
            //QueryCompute queryComp = new QueryCompute(docCollection.getSize(), query);
                // Set timer
                long start = System.currentTimeMillis(); 
                // Make Pre Processing
                // Process query tokens
                for (String termo : token.tokenizeTermo(querySearch)){
                    if (token.isValid(termo)){
                        if(!sw.isStopWord(termo)){ // se for stop word ignora, senao adiciona
                            termo = stemmer.getStemmer(termo);
                            System.out.println("Termo: "+termo); 
                            //indexer.addTerm(termo, Integer.parseInt(i+""+idDoc),pos++);
                        }   
                    }
                }
        }
  
    }
    
    
    private String queryContent(BufferedReader br){
        String query = "";
        try {
            System.out.println("Type query: ");
            query = br.readLine().toLowerCase();
        } catch (IOException ex) {
        }
        return query;
}
}

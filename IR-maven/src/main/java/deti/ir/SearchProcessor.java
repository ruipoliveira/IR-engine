/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir;

import deti.ir.memory.MemoryManagement;
import deti.ir.searcher.IndexerResults;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import deti.ir.searcher.Query;
import deti.ir.searcher.QueryProcessing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private IndexerResults indexer; 
    
    public SearchProcessor(String indexDirectory, String stopWords_dir, int maxMem) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        token = new Tokenizer(); 
        sw = new StopWords(Paths.get(stopWords_dir));
        stemmer = new Stemmer("englishStemmer");
        memory = new MemoryManagement(); 
        this.maxMem = maxMem;
        indexer = new IndexerResults(); 
    }
    
    public void start() throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        Query typeQ = null; 
        String stringQ = ""; 
        while(true){
            System.out.println("Insert @exit to stop the search operation.");
            stringQ = queryString(br); // content
            typeQ = queryType(br, stringQ);

            QueryProcessing queryComp = new QueryProcessing(typeQ);
          
            
            System.out.print("Insert result limit, if \"all\" show everyone: "); 
            String limit_str = br.readLine(); 
            int limit;  
            if (limit_str.equalsIgnoreCase("all")){
                limit = 2147483647; 
            }else{
                limit = Integer.parseInt(limit_str); 
            }
            
            long start = System.currentTimeMillis(); 

            for (String termo : token.tokenizeTermo(stringQ)){
                if (token.isValid(termo)){
                    if(!sw.isStopWord(termo)){ // se for stop word ignora, senao adiciona
                        termo = stemmer.getStemmer(termo);
                        //System.out.println("Termo: "+termo); 
                        queryComp.addTerm(termo);
                    }   
                }
            }
            
            try{
                System.out.println("Searching...");
                System.out.println(queryComp.getQueryTerms()); 
                
                getResults(queryComp.computeScore(indexer.getPosting(queryComp.getQueryTerms(), typeQ)),limit );               
                long elapsedTime = System.currentTimeMillis() - start;
                System.out.println("Spent time: " + elapsedTime + "ms\n\n\n");
            }catch(NullPointerException ex){
                System.out.println("Not found");
            }
        }
    }
    
    
    private Query queryType(BufferedReader br, String stringQ){
        String in_str = "";
        int typeQ = 0; 
        Query query = null;
        try {
            
            System.out.print("0 -> Simple, 1 -> Phrase, 2 -> Proximity\nType query: ");
            in_str = br.readLine(); 
            if(in_str.equals("@exit")){
                System.exit(0);
            }
            
            try{
                typeQ = Integer.parseInt(in_str);
                if ((typeQ < 0) || (typeQ > 2)) {
                    System.out.println("Invalid type");
                    System.exit(0);
                }
            }catch(NumberFormatException e ){
                System.out.println("Invalid input"); 
            }
            
            switch(typeQ){
            case 0:
            case 1:
                query = new Query(typeQ, stringQ); // Simple, Phrase
                break;
            case 2:
                int proxVal = getQueryProximity(br);
                query = new Query(typeQ, stringQ, proxVal); //Proximity
                break;
            }

        } catch (IOException ex) {
            Logger.getLogger(SearchProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return query;
    }
    
    private int getQueryProximity(BufferedReader br){
        int maxProxVal = 0;
        System.out.print("Input a max proximity value -->  ");
        try{
            String value = br.readLine();
            maxProxVal = Integer.parseInt(value);
        }catch(NumberFormatException e){
            System.out.println("Invalid proximity! Selected value 1 by default.");
        }catch(IOException ex) {
            Logger.getLogger(SearchProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return maxProxVal;
    }
    
    private String queryString(BufferedReader br){
        String query = ""; 
        System.out.print("Insert your query: ");
        try {
            query = br.readLine().toLowerCase();
            
            if(query.equals("@exit")){
                System.exit(0);
            }
                        
        } catch (IOException ex) {
            Logger.getLogger(SearchProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return query; 
    }
    
    
    private void getResults(HashMap<Integer, String> score, int limit) {

        score.entrySet().stream()
            .limit(limit)
            .sorted(Entry.comparingByValue(Comparator.reverseOrder()))    
            .forEachOrdered((entry) -> {
                System.out.println("ID: " + entry.getKey() + "\t"
                    + "\t\tScore: " + entry.getValue());
            });
    }
}

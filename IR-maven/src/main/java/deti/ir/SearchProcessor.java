package deti.ir;

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
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class SearchProcessor {
    private final Stemmer stemmer;
    private final StopWords sw;
    private final Tokenizer token;
    private final IndexerResults indexer; 
    
    public SearchProcessor(String indexDirectory, String stopWords_dir) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        token = new Tokenizer(); 
        sw = new StopWords(Paths.get(stopWords_dir));
        stemmer = new Stemmer("englishStemmer");
        indexer = new IndexerResults(); 
    }
    
    /**
     * 
     * @throws IOException 
     */
    public void start() throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        Query typeQ = null; 
        String stringQ = "";
        int limit = 0;
        while(true){
            System.out.println("Insert @exit to stop the search operation.");
            stringQ = queryString(br); // content
            typeQ = queryType(br, stringQ);

            QueryProcessing queryComp = new QueryProcessing(typeQ);
          
            
            System.out.print("Insert result limit, if \"all\" shows everyone: "); 
            
            try{
                String limit_str = br.readLine(); 
                
                if (limit_str.equalsIgnoreCase("all")){
                    limit = 2147483647; 
                }else{
                    limit = Integer.parseInt(limit_str); 
                }
            }catch(NumberFormatException e){
                System.out.println("Invalid input!");
                System.exit(0);
                
            }
            long start = System.currentTimeMillis(); // to count time of execution

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
           
                printTopScores(queryComp.calculateScore(indexer.getPosting(queryComp.getQueryTerms(), typeQ)),limit );               
                long elapsedTime = System.currentTimeMillis() - start;
                System.out.println("Spent time: " + elapsedTime + "ms\n\n\n");
            }catch(NullPointerException ex){
                System.out.println("Not found");
            }
        }
    }
    
    /**
     * 
     * @param br
     * @param stringQ
     * @return 
     */
    private Query queryType(BufferedReader br, String stringQ){
        String in_str = "";
        int typeQ = 0; 
        Query query = null;
        try {
            
            System.out.println("0 -> Simple, 1 -> Phrase, 2 -> Proximity");
            System.out.print("Type query: ");
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
                System.exit(0);
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
    
    
    /**
     * 
     * @param br
     * @return 
     */
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
    
    /**
     * 
     * @param score
     * @param limit 
     */
    private void printTopScores(HashMap<Integer, String> score, int limit) {
         
        score.entrySet().stream()
            .limit(limit)
            .sorted(Entry.comparingByValue(Comparator.reverseOrder()))    
            .forEachOrdered((entry) -> {
                System.out.println("Doc ID: " + entry.getKey() + "    "
                    + "\tScore value: " + entry.getValue());
            });
        
        System.out.println("The query was found in "+score.size()+" documents."); 
        
    }
}

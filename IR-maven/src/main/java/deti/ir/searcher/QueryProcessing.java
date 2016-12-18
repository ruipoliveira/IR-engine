package deti.ir.searcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class QueryProcessing {
    
    private final Query query; 
    
    private final LinkedHashMap<String, Integer> termFreq;
    
    private double sumxi;
    
    /**
     * 
     * @param query 
     */
    public QueryProcessing(Query query){
        this.query = query;
        this.sumxi = 0.0;
        termFreq = new LinkedHashMap<>();
    }
    
    /**
     * 
     * @param token 
     */
    public void addTerm(String token){
        termFreq.merge(token, 1, (a, b) -> a + b);
    }
    
    /**
     * 
     * @return 
     */
    public List<String> getQueryTerms(){
        return termFreq.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
    
    /**
     * 
     * @param posting
     * @return 
     */
    public HashMap<Integer, String> calculateScore(HashMap<String, HashMap<Integer, String>> posting){
                
        // Compute term Weight
        HashMap<String, Double> termWeight = new HashMap<>();
        
        termFreq.entrySet().parallelStream()
            .forEach((entry) ->{
                double weight = (1 + Math.log10(entry.getValue())) * 
                    Math.log10( getNrDocuments() / posting.get(entry.getKey()).size()); // calcular IDF
                sumxi += Math.pow(weight, 2);
                termWeight.put(entry.getKey(), weight); // guardar todos os termos e seus pesos
            });
        
        // Normalize weight
        termWeight.replaceAll((k, v) -> {
            double norm = v/Math.sqrt(sumxi); 
            return norm;
        });
        sumxi = 0.0;        
                
        // Compute Score
        HashMap<Integer, String> final_score = new HashMap<>();
        posting.entrySet().stream()
                .forEach((entry)->{
                    entry.getValue().entrySet().stream().forEach((e) ->{
                        
                        final_score.merge(e.getKey(), 
                            String.valueOf((termWeight.get(entry.getKey()) * Double.valueOf(e.getValue().split("-")[0]))),
                            (a, b) -> (String.valueOf(Double.valueOf(a) + Double.valueOf(b))));
                    });
                });
        return final_score;
    }
       
    /**
     * 
     * @return 
     */
    private int getNrDocuments(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File("nrDocID.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QueryProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        int num;
        num = sc.nextInt();        
        return num;
    }
        
}

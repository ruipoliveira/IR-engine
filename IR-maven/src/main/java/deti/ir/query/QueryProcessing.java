/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.query;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author roliveira
 */
public class QueryProcessing {
    
    private Query query; 
    
    private final LinkedHashMap<String, Integer> termFreq;
    
    private double sumxi;
    
    
    public QueryProcessing(Query query){
        this.query = query;
        this.sumxi = 0.0;
        termFreq = new LinkedHashMap<>();
    }
    
    
    public void addTerm(String token){
        termFreq.merge(token, 1, (a, b) -> a + b);
    }
    
    
    public List<String> getQueryTerms(){
        return termFreq.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    
    
    public HashMap<Integer, String> computeScore(HashMap<String, HashMap<Integer, String>> posting){
                
        System.out.println(posting.toString()); 
        // Compute term Weight
        HashMap<String, Double> termWeight = new HashMap<>();
        termFreq.entrySet().parallelStream()
                .forEach((entry) ->{
                    double weight = (1 + Math.log10(entry.getValue())) * computeIDF(posting.get(entry.getKey()).size());
                    sumxi += Math.pow(weight, 2);
                    termWeight.put(entry.getKey(), weight);
                });
        
        
        // Normalize weight
        termWeight.replaceAll((k, v) -> normalization(v));
        sumxi = 0.0;
        
        
        System.out.println("--->"+termWeight.toString()); 

                
        
        // Compute Score
        HashMap<Integer, String> score = new HashMap<>();
        posting.entrySet().stream()
                .forEach((entry)->{
                    entry.getValue().entrySet().stream().forEach((e) ->{
                       
                        //System.out.println(Double.valueOf(e.getValue().split("-")[0])); 
                        
                        score.merge(e.getKey(), 
                               String.valueOf((termWeight.get(entry.getKey()) * Double.valueOf(e.getValue().split("-")[0]))),
                               (a, b) -> (String.valueOf(Double.valueOf(a) + Double.valueOf(b))));
                    });
                });
        return score;
    }
       
    
    
    private double computeIDF(int size) {
        return (Math.log10(3278732 / size));
    }
    
    private double normalization(double value) {
        //System.out.println("SUM"+sumxi); 
        //System.out.println("value"+value); 
        return (value / Math.sqrt(sumxi));
    }
        
}

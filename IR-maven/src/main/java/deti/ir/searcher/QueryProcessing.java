/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
                
        // Compute term Weight
        HashMap<String, Double> termWeight = new HashMap<>();
        
        termFreq.entrySet().parallelStream()
                .forEach((entry) ->{
                    double weight = (1 + Math.log10(entry.getValue())) * 
                            Math.log10( getNrDocuments() / posting.get(entry.getKey()).size()); // computeIDF
                    sumxi += Math.pow(weight, 2);
                    termWeight.put(entry.getKey(), weight);
                });
        
        // Normalize weight
        termWeight.replaceAll((k, v) -> {
            double norm = v/Math.sqrt(sumxi); 
            return norm;
        });
        sumxi = 0.0;        
                
        // Compute Score
        HashMap<Integer, String> score = new HashMap<>();
        posting.entrySet().stream()
                .forEach((entry)->{
                    entry.getValue().entrySet().stream().forEach((e) ->{
                     
                        score.merge(e.getKey(), 
                               String.valueOf((termWeight.get(entry.getKey()) * Double.valueOf(e.getValue().split("-")[0]))),
                               (a, b) -> (String.valueOf(Double.valueOf(a) + Double.valueOf(b))));
                    });
                });
        return score;
    }
       
    
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

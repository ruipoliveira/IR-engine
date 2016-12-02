/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.query;

import java.util.LinkedHashMap;

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
    
    
    private double computeIDF(int size) {
        return Double.valueOf(Math.log10(1.2 / size));
    }
    
    private double normalization(double value) {
        return Double.valueOf(value / Math.sqrt(sumxi));
    }
        
}

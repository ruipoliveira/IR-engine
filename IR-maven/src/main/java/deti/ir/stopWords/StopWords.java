/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.stopWords;

import java.util.List;

/**
 *
 * @author RuiOliveira
 */
public class StopWords {
    
    List<String> stopWords; // lista de stop words
    
    public StopWords(){
        
    }
    
            // verificar se e stop word
    public boolean isStopWord(String token){
        return stopWords.contains(token);
    }
}

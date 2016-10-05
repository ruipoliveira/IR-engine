/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.Indexer;
import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;

/**
 *
 * @author RuiOliveira
 */
public class DocumentProcessor {
    
    /**
    * Corpus Reader reference.
    */
    private final CorpusReader cr;
    
    
    /**
    * Indexer reference.
    */
    private final Indexer indexer;

    
    /**
    * Memory management reference.
    */
    private final MemoryManagement memory;
    
    
    /**
    * Stemmer reference.
    */
    private Stemmer stemmer;
    
    
    /**
    * StopWords reference.
    */
    private StopWords sw;
    
    public DocumentProcessor(){
        cr = new CorpusReader();
        indexer = new Indexer();
        memory = new MemoryManagement();
    }
    
    public void start(){
        
    }
}

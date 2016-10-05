package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.Indexer;
import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
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
    
    /**
    * Tokenizer reference.
    */
    private Tokenizer tok; 
    
    public DocumentProcessor(){
        this.cr = new CorpusReader();
        this.indexer = new Indexer();
        this.memory = new MemoryManagement();
        this.tok = new Tokenizer(); 
        
    }
    
    public void start(){
        
    }
}

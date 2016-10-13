package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.Indexer;
import deti.ir.tokenizer.Tokenizer;
import java.nio.file.Paths;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class DocumentProcessor {
    
    private final CorpusReader cr;
    
    private final Indexer indexer;
    /*
    private final MemoryManagement memory;

    private Stemmer stemmer;

    private StopWords sw;
    */ 
    private Tokenizer tok; 
    
    private String directory; 
    private int id; 

    
    public DocumentProcessor(int id, String directory){
        System.out.println(Paths.get(directory).toString()); 
        cr = new CorpusReader(Paths.get(directory));
        tok = new Tokenizer(); 
        indexer = new Indexer(); 
    }
    
    public void start(){
        String s, nrId; 
        System.out.println("Document Processor started...");
        
        for (int i = 0; i < cr.getNrCollections(); i++) {  
            s = cr.getText(i);
            System.out.println(s);
            nrId = cr.getDocID(i); 
            System.out.println("cee"+nrId); 
            
            
            
            
            for (String termo : tok.tokenize(s)) {

                //System.out.println(termo);
                if (tok.isDocID(termo)){
                    System.out.println(termo); 
                }/*else{
                    System.out.println("dasda"); 
                }
                  */  
                
                
                //if (tok.isValid(termo)) {
                //    //indexa todos os termos
                //}
            }
        }

    }
}

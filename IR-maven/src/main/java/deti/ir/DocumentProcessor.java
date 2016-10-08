package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import java.nio.file.Paths;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class DocumentProcessor {
    
    private final CorpusReader cr;
    /*
    private final Indexer indexer;

    private final MemoryManagement memory;

    private Stemmer stemmer;

    private StopWords sw;

    private Tokenizer tok; 
    */
    private String directory; 
    private int id; 
    
    
    public DocumentProcessor(int id, String directory){
        System.out.println(Paths.get(directory).toString()); 
        cr = new CorpusReader(Paths.get(directory));
        
        
    }
    
    public void start(){
        String s; 
        System.out.println("Document Processor started...");
        /*
        for (int i = 0; i < cr.getNrFiles(); i++) {
            s = cr.getText(i);
            System.out.println(s); 
        }
*/
    }
}

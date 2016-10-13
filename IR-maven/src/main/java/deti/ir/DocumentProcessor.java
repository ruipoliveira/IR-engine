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
        //System.out.println(Paths.get(directory).toString()); 
        cr = new CorpusReader(Paths.get(directory));
        tok = new Tokenizer(); 
        indexer = new Indexer(); 
    }
    
    public void start(){
        String collection, nrId; 
        System.out.println("Document Processor started...");
        
        for (int i = 0; i < cr.getNrCollections(); i++) {  
            collection = cr.getText(i);

            for(String doc : tok.tokenizeDoc(collection)){
                               
                int pos = doc.indexOf(",");
                if (pos != -1){
                    nrId = doc.substring(0, pos); 
                    //System.out.println("ID:"+doc.substring(0, pos)); 
                    doc = doc.substring(pos+1, doc.length()); // restante documento
                    //System.out.println(doc);
                    
                    // coloca apenas os termos do resto do documento e percorre-os todos
                    for (String termo : tok.tokenizeTermo(doc)){
                        System.out.println("ID #"+nrId+ " Termo: "+termo); 
                    }
                    
                }
                    
                
            }
            
     
              

        }

    }
}

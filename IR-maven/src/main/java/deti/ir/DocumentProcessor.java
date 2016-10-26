package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.DocIDPath;
import deti.ir.indexer.Indexer;
import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class DocumentProcessor {
    
    private final CorpusReader cr; 
    
    private final Indexer indexer;
    
    private Tokenizer tok;
    
    private StopWords sw;
    
    private final Stemmer stemmer;
    
    List<DocIDPath> docIdpath; 
    
    private final MemoryManagement memory;
       
    private String directory; 
    private int id; 
    /**
     * Construtor para o fluxo de processamento
     * @param directory
     * @param stopWords_dir 
     */
    public DocumentProcessor(String directory, String stopWords_dir) {
        //System.out.println(Paths.get(directory).toString()); 
        cr = new CorpusReader(Paths.get(directory));
        tok = new Tokenizer(); 
        indexer = new Indexer();
        sw = new StopWords(Paths.get(stopWords_dir));
        stemmer = new Stemmer("englishStemmer");
        memory = new MemoryManagement(); 
        docIdpath = new LinkedList<>(); 
    }
    /**
     * Método que inicia processamento
     */
    public void start(){
        String collection, idDoc; 
        System.out.println("Document Processor started...");
        
        for (int i = 0; i < cr.getNrCollections(); i++) {  
            collection = cr.getText(i);
            for(String doc : tok.tokenizeDoc(collection)){
                               
                int pos = doc.indexOf(" ");
                if (pos != -1){
                    idDoc = doc.substring(0, pos); 
                    //System.out.println("ID:"+doc.substring(0, pos)); 
                    doc = doc.substring(pos+1, doc.length()); // restante documento
                    //System.out.println(doc);
                    
                    //System.out.println(idDoc +"dds"+cr.getPath(i)); 
                    docIdpath.add(new DocIDPath(i, idDoc, cr.getPath(i))); 

                    // coloca apenas os termos do resto do documento e percorre-os todos
                    for (String termo : tok.tokenizeTermo(doc)){
                        if (tok.isValid(termo)){
                            if(!sw.isStopWord(termo)){ // se for stop word ignora, senao adiciona
                                termo = stemmer.getStemmer(termo);
                                //System.out.println("ID #"+i+idDoc+ " Termo: "+termo); 
                                indexer.addTerm(Integer.parseInt(i+idDoc), termo);
                                
                            }   
                        }
                    }
                }
            }
        }

        
        //System.out.println(memory.getCurrentMemory()); 
        
        indexer.generateFileTokenFreqDocs();
        //indexer.generateFileTokenFreq(); 
        //System.out.println(docIdpath.toString());
    }
    /**
     * Retorna lista de 
     * @return List<DocIDPath>
     */
    public List<DocIDPath> getListPath(){
        return docIdpath;
    }
}

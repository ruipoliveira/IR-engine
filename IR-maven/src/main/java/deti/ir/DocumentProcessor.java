package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.DocIDPath;
import deti.ir.indexer.Indexer;
import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.*;

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
     * @throws java.lang.ClassNotFoundException 
     * @throws java.lang.IllegalAccessException 
     * @throws java.lang.InstantiationException 
     */
    public DocumentProcessor(String directory, String stopWords_dir) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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
     * @throws java.io.IOException
     */
    public void start() throws IOException{
        
        String collection; 
        
        boolean newDoc = false;
        System.out.println("Document Processor started...");
        int position; 
        for (int i = 1; i <= cr.getNrCollections(); i++) {
            CSVParser parser = new CSVParser(new FileReader(cr.getPath(i-1)), CSVFormat.DEFAULT.withHeader());
            System.out.println(i); 
            String xpto; 
            int idDoc=0;
            
            for (CSVRecord record : parser) {
                    position=0;
                    try{
                        xpto = record.get("Title"); 
                    }catch(IllegalArgumentException ex){ xpto = ""; }
                    
                    String in1 = cr.processorBodyAndTitle(xpto+" "+record.get("Body")); 
                    //System.out.println(i+"->"+record.get("Id")+"->"+in1); 
                    //docIdpath.add(new DocIDPath(i, record.get("Id"), cr.getPath(i))); 
                    for (String termo : tok.tokenizeTermo(in1)){
                        if (tok.isValid(termo)){
                            newDoc = true;
                            if(!sw.isStopWord(termo)){ // se for stop word ignora, senao adiciona
                                termo = stemmer.getStemmer(termo);
                                //System.out.println("ID #"+i+""+idDoc+ " Termo: "+termo); 
                                indexer.addTerm(termo, Integer.parseInt(i+""+idDoc),position++);
                            }   
                        }
                        
                        if (memory.getCurrentMemory() >= (128*0.85)) {
                            System.out.println(idDoc);
                            System.out.println("Memory usage is high! Saving Indexer current state...");
                            indexer.freeRefMaps();
                            System.gc();
                            System.out.println("Processing...");
                        }
                    }
                    
            System.out.println("CRLHHHHHHH!"+i); 
            if (newDoc) {
                //System.out.println("idDoc->"+idDoc); 
                indexer.computeTF(Integer.parseInt(i+""+idDoc)); 
                idDoc++;
                newDoc = false;
            }   
                }  
            
         parser.close();
            
        }
        indexer.freeRefMaps();
        System.gc();
        indexer.joinRefMaps();
        
        //indexer.generateFileTokenFreqDocs();
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

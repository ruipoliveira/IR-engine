package deti.ir;

import deti.ir.corpusReader.CorpusReader;
import deti.ir.indexer.Indexer;
import deti.ir.memory.MemoryManagement;
import deti.ir.stemmer.Stemmer;
import deti.ir.stopWords.StopWords;
import deti.ir.tokenizer.Tokenizer;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
        
    private final MemoryManagement memory;
    
    private final int maxMem; 
    /**
     * Construtor para o fluxo de processamento
     * @param directory
     * @param stopWords_dir 
     * @param maxMem 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.lang.IllegalAccessException 
     * @throws java.lang.InstantiationException 
     */
    public DocumentProcessor(String directory, String stopWords_dir, int maxMem) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        cr = new CorpusReader(Paths.get(directory));
        tok = new Tokenizer(); 
        indexer = new Indexer();
        sw = new StopWords(Paths.get(stopWords_dir));
        stemmer = new Stemmer("englishStemmer");
        memory = new MemoryManagement(); 
        this.maxMem = maxMem; 
    }
    /**
     * Método que inicia processamento
     * @throws java.io.IOException
     */
    public void start() throws IOException{
        
        int pos; 
        for (int i = 1; i <= cr.getNrCollections(); i++) {
            CSVParser parser = new CSVParser(new FileReader(cr.getPath(i-1)), CSVFormat.DEFAULT.withHeader());
            System.out.println("Está a processar..."+cr.getPath(i-1)); 
            String title; 
            int idDoc=0;
            
            for (CSVRecord record : parser) {
                pos=0;
                try{
                    title = record.get("Title"); 
                }catch(IllegalArgumentException ex){
                    title = ""; 
                }
                String in1 = cr.filterBodyAndTitle(title+" "+record.get("Body")); 
                //System.out.println(i+""+idDoc+"->"+in1); 
                for (String termo : tok.tokenizeTermo(in1)){
                    if (tok.isValid(termo)){
                        if(!sw.isStopWord(termo)){ // se for stop word ignora, senao adiciona
                            termo = stemmer.getStemmer(termo);
                            //System.out.println("ID #"+i+""+idDoc+ " Termo: "+termo); 
                            indexer.addTerm(termo, Integer.parseInt(i+""+idDoc),pos++);
                        }   
                    }
                }
                
                indexer.calculateTF(Integer.parseInt(i+""+idDoc)); 
                idDoc++;
                
                if (memory.getCurrentMemory() >= (maxMem*0.75)) {
                    System.out.println("\nDocId #"+i+idDoc+"\nNovo ficheiro escrito em disco...");
                    indexer.freeRefMaps();
                    System.gc();
                }
                
            }  
            
        parser.close();
            
        }
        
        indexer.freeRefMaps();
        
        //System.gc();
        System.out.println("\nJuntar todos os ficheiros resultantes da indexação..."); 
        
        indexer.mergeFilesRefs();
        
        System.out.println("\nFim do processo de indexação e escrita em disco."); 
     
    }   
    
}

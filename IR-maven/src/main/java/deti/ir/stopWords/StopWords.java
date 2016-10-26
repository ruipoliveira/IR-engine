package deti.ir.stopWords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class StopWords {

    List<String> stopWords; // array list de stop words de stop words
    
    /**
     * Construtor das stopwords que recebe o caminho para a lista de stopwords a ser usada
     * @param dirPath 
     */
    public StopWords(Path dirPath) {
        stopWords = new ArrayList<>();
        
        // obter ficheiros de um dado directorio
        try(Stream<String> lines = Files.lines(dirPath)){
            lines.filter(line -> line.length() > 2).forEach(s -> stopWords.add(s));
        }catch(IOException e) {}
        
        //System.out.println("List of stop words:"+stopWords.toString());
    }
    
    /**
     * metodo que verifica se um termo (token) é stop word
     * @param token
     * @return 
     */
    public boolean isStopWord(String token){
        return stopWords.contains(token);
    }
    
    /**
     * metodo que retorna o tamanho da lista de stop words
     * @return 
     */
    public int getSize(){
        return stopWords.size();
    }
    
    /**
     * Metodo que verifica se a lista de stop words esta vazia (nao usado)
     * @return 
     */ 
    public boolean StopListEmpty(){
        return stopWords.isEmpty();
    }
    
    /**
     * Metodo que retorna a lista de stopwords
     * @return 
     */
    public List<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(List<String> stopWords) {
        this.stopWords = stopWords;
    }
    
    
}
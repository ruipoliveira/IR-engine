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
    
    public StopWords(Path dirPath) {
        stopWords = new ArrayList<>();
        
        // obter ficheiros de um dado directorio
        try(Stream<String> lines = Files.lines(dirPath)){
            lines.filter(line -> line.length() > 2).forEach(s -> stopWords.add(s));
        }catch(IOException e) {}
        
        //System.out.println("List of stop words:"+stopWords.toString());
    }
    
    // verificar se e stop word
    public boolean isStopWord(String token){
        return stopWords.contains(token);
    }
    
    public int getSize(){
        return stopWords.size();
    }
    
     
    public boolean StopListEmpty(){
        return stopWords.isEmpty();
    }
    
    public List<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(List<String> stopWords) {
        this.stopWords = stopWords;
    }
    
    
}
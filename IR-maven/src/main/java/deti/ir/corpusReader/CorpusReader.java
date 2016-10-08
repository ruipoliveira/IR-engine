package deti.ir.corpusReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class CorpusReader {
    
    List<Path> files;
    
    public CorpusReader(Path path){
        files = new ArrayList<>(); 
        // Get Files from directory
        try (Stream<Path> lines = Files.list(path)) {
            lines.forEach(s -> System.out.println(s));
            lines.forEach(s -> files.add(s));
        } catch (IOException ex) {}
    }
    
    public String getText(int position){
        return  "hey"; 
    }
    
    public int getNrFiles(){
        return files.size();
    }
        
}

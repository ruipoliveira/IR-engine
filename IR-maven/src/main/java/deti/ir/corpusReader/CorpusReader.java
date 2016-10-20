package deti.ir.corpusReader;

import deti.ir.indexer.DocIDPath;
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
            lines.forEach(s -> files.add(s));
        } catch (IOException ex) {}
        
       // System.out.println(files); 
    }
    
    public String getText(int position){
        String text = "";
        //System.out.println(position+"-->"+files.get(position)); 
        try (Stream<String> stream = Files.lines(files.get(position))){
            text = stream.parallel()
                    .filter(line -> line.length() > 0 && line.charAt(0) != '@' )
                    .map(line -> line.toLowerCase())
                  //  .map(line -> line.replaceAll(",", ""))
                    .map(line -> line.replaceAll("\\<.*?>", ""))
                    .map(line -> line.replaceAll("\\<.*?>", ""))
                    .map(line -> line.replaceAll(",m[0-9]", ""))
                    .map(line -> line.replaceAll("\"", ""))
                    .map(line -> line.replaceAll(",", " "))
                    .map(line -> line.replaceAll("[.!?\\-\\;\\:\\(\\)\\[\\]]", ""))
                    .collect(Collectors.joining("\n")
                    );
        } catch (IOException ex) {}
        return text;
    }

    public String getPath(int position){
        return files.get(position).toString(); 
    }
    
    public int getNrCollections(){
        return files.size();
    }   
}

package deti.ir.corpusReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class CorpusReader {
    
    List<Path> files;
    Document doc = null; 

    /**
     * Constructor do CorpusReader que recebe o caminho onde os ficheiros a serem lidos se situa
     * @param path 
     */
    public CorpusReader(Path path){
        //System.out.println(path); 
        // Get Files from directory
        files = new ArrayList<>(); 
        try (Stream<Path> lines = Files.list(path)) {
            lines.forEach(s -> files.add(s));
        } catch (IOException ex) {}
        System.out.println(files); 
    }
    

    /**
     * função para obter o texto de cada ficheiro
     * @param bodytitle
     * @return txt
     */
    public String processorBodyAndTitle(String bodytitle){
        doc = Jsoup.parse(bodytitle); 
        doc.select("pre").remove(); 
        return getText(Jsoup.parse(doc.toString()).text()); 
    }
    
    
    public String getText(String in){
        return in.toLowerCase().replaceAll(",", "").replaceAll("[.!?\\-\\;\\:\\(\\)\\[\\]]", ""); 
    }
    
    /**
     * retorna o caminho para cada ficheiro em forma de String
     * @param position
     * @return files.get(position).toString(); 
     */
    public String getPath(int position){
        return files.get(position).toString(); 
    }
  
    /**
     * retorna o numero total de ficheiros a serem lidos
     * @return files.size()
     */
    public int getNrCollections(){
        return files.size();
    }
    
    
}

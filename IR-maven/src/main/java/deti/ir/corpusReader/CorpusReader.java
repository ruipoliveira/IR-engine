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
     * @param position
     * @return txt
     */
    public String getText(int position) throws IOException{
        String txt = "";
        System.out.println(position+"-->"+files.get(position)); 
       
        
        CSVParser parser = new CSVParser(new FileReader(files.get(position).toString()), CSVFormat.DEFAULT.withHeader());
        
        System.out.print(parser);
        for (CSVRecord record : parser) {
        //System.out.println(record.get("Id")+"->"+record.get("Body"));
        }
        parser.close();

        System.out.println("LEU tudo!"); 

        /*
        try (Stream<String> stream = Files.lines(files.get(position))){
            txt = stream.parallel()
                    .filter(line -> line.length() > 0 )
                    .map(line -> { System.out.println(line);  return line.toLowerCase(); }) 
                    
                  //  .map(line -> line.replaceAll(",", ""))
                    .map(line -> line.replaceAll("\\<.*?>", ""))
                    .map(line -> line.replaceAll("\\<.*?>", ""))
                    .map(line -> line.replaceAll(",m[0-9]", ""))
                    .map(line -> line.replaceAll("\"", ""))
                    .map(line -> line.replaceAll(",", " "))
                    .map(line -> line.replaceAll("[.!?\\-\\;\\:\\(\\)\\[\\]]", ""))
                    

                    .collect(Collectors.joining("\n")
                    );
        } catch (Exception ex) {}*/

        return txt;
    }
    
    /**
     * função para obter o texto de cada ficheiro
     * @param bodytitle
     * @return txt
     */
    public String processorBodyAndTitle(String bodytitle){
        Document doc = Jsoup.parse(bodytitle); 
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

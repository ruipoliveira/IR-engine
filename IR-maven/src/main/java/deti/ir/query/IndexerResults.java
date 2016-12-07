/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.query;

import deti.ir.memory.MemoryManagement;
import static deti.ir.indexer.Indexer.findToken; 
import deti.ir.indexer.TokenPost;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 *
 * @author roliveira
 */
public class IndexerResults {


    private int counter;

    /**
     * Indexer constructor that creates an Inverted Index.
     */
    public IndexerResults() {

    }

    /**
     * Get posting of the valid documents where the terms were found.
     * @param terms terms list.
     * @param q query mande.
     * @return posting.
     */
    public HashMap<String, HashMap<Integer, String>> getPosting(List<String> terms, Query q) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        switch (q.getType()) {
            case 0:
                posting = getNormalQueryPosting(terms);
                break;
            /*case 1:
                posting = getPhraseQueryPosting(terms);
                break;
            case 2:
                posting = getProximityQueryPosting(terms, q.getProximity());
                break;
            case 3:
                posting = getFieldQueryPosting(terms, q.getFieldKey(), q.getFieldValue());
                break;*/
            default:
                break;
        }
        return posting;
    }

    /**
     * Simple query method.
     * @param terms query terms.
     * @return posting.
     */
    private HashMap<String, HashMap<Integer, String>> getNormalQueryPosting(List<String> terms) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        terms.forEach((term) -> {
            
            char l = findFile(term);
            HashMap<Integer, String> tmp = new HashMap<>();
            String line = getTermLine (l, term);
            System.out.println(line) ;
            if (!line.equals("")){
                String s1 = line.split (" - ")[1];
                String[] s2 = s1.split(", ");
                for (String s: s2){
                    String[] s3 = s.split("=");
                    tmp.put(Integer.parseInt(s3[0].trim()), s3[1].split("!")[0].trim());
                }
            }
            if (tmp != null) {
                posting.put(term, tmp);
            }
        });
        return posting;
    }

    
    /**
     * Get the term line indexed.
     * @param f file identifier.
     * @param term term to find.
     * @return indexed line.
     */
    public String getTermLine(char f, String term) {
        HashMap<Integer, String> hm;
        String s = "";
        Path file = Paths.get("outputs/tokenRef_" + String.valueOf(f));
        
        
        try (Stream<String> lines = Files.lines(file)) {
            Optional<String> tmp = lines.filter(line -> line.startsWith(term)).findFirst();
            if(tmp.isPresent()){
                s = tmp.get();
            }
        } catch (IOException ex) {}
        return s;
    }
    

    private char findFile(String term){
        char c = term.charAt(0);
        if(Character.isDigit(c)){
            return '0';
        }
        else return c;
    }



    
}

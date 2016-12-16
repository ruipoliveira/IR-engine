/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.searcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class IndexerResults {

    private int counter;

    public IndexerResults() {

    }

    public HashMap<String, HashMap<Integer, String>> getPosting(List<String> terms, Query q) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        switch (q.getType()) {
            case 0:
                posting = getNormalQueryPosting(terms);
                break;
            case 1:
                posting = getPhraseQueryPosting(terms);
                break;
            /*case 2:
                posting = getProximityQueryPosting(terms, q.getProximity());
                break;*/
            default:
                break;
        }
        return posting;
    }

    private HashMap<String, HashMap<Integer, String>> getPhraseQueryPosting(List<String> terms) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        Map<Integer, ArrayList<ScorePosition>> ocurrences = documentsOccurrence(terms);
        
        System.out.println(ocurrences.size());
        if (!ocurrences.isEmpty()) {
            ArrayList<Integer> docIDs = getDocIDsByDistance(ocurrences, 1, false);
            System.out.println(docIDs.size());
            if (!docIDs.isEmpty()) {
                posting = getPostingByDistance(terms, docIDs, ocurrences);
            } else {
                System.out.println("No documents found");
            }
        }
        return posting;
    }
    
    
    private Map<Integer, ArrayList<ScorePosition>> documentsOccurrence(List<String> terms) {
        Map<Integer, ArrayList<ScorePosition>> results = new HashMap<>();

        counter = 0;
        terms.forEach((String term) -> {
            char l = findFile(term);
            String line = getTermLine (l, term,1);
            HashMap<Integer, ArrayList<Integer>> tmp = new HashMap<>();
            HashMap<Integer, Double> tmp2 = new HashMap<>();
            
            if(!line.equals("")){
                String s1 = line.split (" - ")[1];
                for (String s: s1.split(", ")){
                    ArrayList<Integer> termPositions = new ArrayList<>();
                    String[] s3 = s.split("=");
                    for(String pos: s3[1].split("-")[1].split("!")){
                        termPositions.add(Integer.parseInt(pos));
                    }
                    tmp.put(Integer.parseInt(s3[0]), termPositions);
                    tmp2.put(Integer.parseInt(s3[0]), Double.valueOf(s3[1].split("-")[0]));
                }
                
                // First term found
                if (counter == 0){
                    counter++;
                    tmp.entrySet().stream().forEach((e) ->{
                        ArrayList<ScorePosition> termsPositions = new ArrayList<>();
                        ScorePosition dp = new ScorePosition(tmp2.get(e.getKey()), e.getValue());
                        termsPositions.add(dp);
                        results.put(e.getKey(), termsPositions);
                    });
                }
                // Update for new term.
                else{
                    counter++;
                    tmp.entrySet().stream().forEach((e) -> {
                        ArrayList<ScorePosition> aux;
                        if ((aux = results.get(e.getKey())) != null) {
                            // Only add the document if the previous document was also in this document.
                            if (aux.size() == counter - 1) {
                                ScorePosition dp = new ScorePosition(tmp2.get(e.getKey()), e.getValue());
                                aux.add(dp);
                                results.put(e.getKey(), aux);
                            }
                        }
                    });
                }
            }
        });
        
        /**
         * Filter the results to guarantee if all the terms were found in all the documents.
         */
        Map<Integer, ArrayList<ScorePosition>> occurrances;
        occurrances = results.entrySet().stream()
                .filter(e -> e.getValue().size() == counter)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return occurrances;
    }
    
    
    
    
    private HashMap<String, HashMap<Integer, String>> getNormalQueryPosting(List<String> terms) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();

        terms.forEach((term) -> {
            char l = findFile(term);
            HashMap<Integer, String> tmp = new HashMap<>();
            String line1 = getTermLine (l, term, 1);
            
            if (!line1.equals("")){
                String s1 = line1.split (" - ")[1];
                String[] s2 = s1.split(", ");
                for (String s: s2){
                    String[] s3 = s.split("=");
                    tmp.put(Integer.parseInt(s3[0].trim()), s3[1].split("!")[0].trim());
                }
            }
            
            String line2 = getTermLine (l, term, 2);
            if (!line2.equals("")){
                String s1 = line2.split (" - ")[1];
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

    
    private HashMap<String, HashMap<Integer, String>> getPostingByDistance(List<String> terms, ArrayList<Integer> docIDs, Map<Integer, ArrayList<ScorePosition>> initPosting) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        counter = 0;
        terms.forEach((term) ->{
            HashMap<Integer, String> tmp = new HashMap<>();
            initPosting.entrySet().forEach((entry)->{
                if (docIDs.contains(entry.getKey())) {
                    tmp.merge(entry.getKey(), String.valueOf(entry.getValue().get(counter).getScore()), (a, b) -> (String.valueOf(Double.valueOf(a) + Double.valueOf(b))));
                }
            });
            if (tmp != null) {
                posting.put(term, tmp);
            }
            counter++;
        });
        return posting;
    }
    
    private ArrayList<Integer> getDocIDsByDistance(Map<Integer, ArrayList<ScorePosition>> ocurrences, int distance, boolean proximity) {

        ArrayList<Integer> docIDs = new ArrayList<>();
        ocurrences.entrySet().stream().forEach(e -> {
            ArrayList<ScorePosition> terms = e.getValue();
            int counter = 0;
            // Verify terms distance.
            for (int i = 0; i < terms.size() - 1; i++) {
                ArrayList<Integer> term1 = terms.get(i).getPositions();
                ArrayList<Integer> term2 = terms.get(i + 1).getPositions();
                boolean found = false;
                for (Integer pos : term1) {
                    for (Integer pos2 : term2) {
                        if ((Math.abs(pos2 - pos) <= distance) && (proximity || (pos2 - pos > 0))) {
                            found = true;
                            break;
                        }
                        if(found){
                            break;
                        }
                    }
                }
                if (found == true) {
                    counter++;
                }
            }
            // Verify the maximum distance between all terms of the query.
            if (counter == terms.size() - 1) {
                docIDs.add(e.getKey());
            }
            counter = 0;
        });
        return docIDs;
    }
    
    
    private String getTermLine(char f, String term, int docId) {
        HashMap<Integer, String> hm;
        String s = "";
        Path file = Paths.get("outputs/tokenRef_" + String.valueOf(f)+docId);
        
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

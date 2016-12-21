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

    private int cont;

    public IndexerResults() {

    }

    /**
     * Método que obtém o posting dos documentos validos onde os termos foram encontrados
     * @param terms lista de termos
     * @param q
     * @return 
     */
    public HashMap<String, HashMap<Integer, String>> getPosting(List<String> terms, Query q) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        switch (q.getType()) {
            case 0:
                posting = getPostingNormal(terms);
                break;
            case 1:
                posting = getPostingPhrase(terms);
                break;
            /*case 2:
                posting = proximity(); // not implemeneted
                break;*/
            default:
                break;
        }
        return posting;
    }
    /**
     * Método de query phrase
     * @param terms lista de termos
     * @return 
     */
    private HashMap<String, HashMap<Integer, String>> getPostingPhrase(List<String> terms) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();
        Map<Integer, ArrayList<ScorePosition>> ocurrs = docsOccurrence(terms);
        
        System.out.println(ocurrs.size());
        if (!ocurrs.isEmpty()) {
            ArrayList<Integer> docIDs = getDocIDsDistance(ocurrs, 1, false);
            System.out.println(docIDs.size());
            if (!docIDs.isEmpty()) {
                posting = getPostingByDistance(terms, docIDs, ocurrs);
            } else {
                System.out.println("No documents found!");
            }
        }
        return posting;
    }
    
    /**
     * Metodo que obtem os documentos onde os termos da pesquisa foram econtrados
     * @param terms lista de termos
     * @return 
     */
    private Map<Integer, ArrayList<ScorePosition>> docsOccurrence(List<String> terms) {
        Map<Integer, ArrayList<ScorePosition>> score_results = new HashMap<>();

        cont = 0;
        terms.forEach((String term) -> {
            char c = findFile(term);
   
            String line = getLine (c, term,1);
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
                
                if (cont == 0){
                    cont++;
                    tmp.entrySet().stream().forEach((e) ->{
                        ArrayList<ScorePosition> termsPos = new ArrayList<>();
                        ScorePosition sp = new ScorePosition(tmp2.get(e.getKey()), e.getValue());
                        termsPos.add(sp);
                        score_results.put(e.getKey(), termsPos);
                    });
                }
                else{
                    cont++;
                    tmp.entrySet().stream().forEach((e) -> {
                        ArrayList<ScorePosition> score_aux;
                        if ((score_aux = score_results.get(e.getKey())) != null) {
                            // Only add the document if the previous document was also in this document.
                            if (score_aux.size() == cont - 1) {
                                ScorePosition dp = new ScorePosition(tmp2.get(e.getKey()), e.getValue());
                                score_aux.add(dp);
                                score_results.put(e.getKey(), score_aux);
                            }
                        }
                    });
                }
            }
        });

        
        Map<Integer, ArrayList<ScorePosition>> occurrances;
        occurrances = score_results.entrySet().stream()
                .filter(e -> e.getValue().size() == cont)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return occurrances;
    }
    
    
    
    /**
     * Método do tipo de query simples 
     * @param terms lista de termos
     * @return 
     */
    private HashMap<String, HashMap<Integer, String>> getPostingNormal(List<String> terms) {

        HashMap<String, HashMap<Integer, String>> posting = new HashMap<>();

        terms.forEach((term) -> {
            char c = findFile(term);
            HashMap<Integer, String> tmp = new HashMap<>();
            String line1 = getLine (c, term, 1);
            
            if (!line1.equals("")){
                String s1 = line1.split (" - ")[1];
                String[] s2 = s1.split(", ");
                for (String s: s2){
                    String[] s3 = s.split("=");
                    tmp.put(Integer.parseInt(s3[0].trim()), s3[1].split("!")[0].trim());
                }
            }
            
            String line2 = getLine (c, term, 2);
            if (!line2.equals("")){
                String s1 = line2.split (" - ")[1];
                String[] s2 = s1.split(", ");
                for (String s: s2){
                    String[] s3 = s.split("=");
                    tmp.put(Integer.parseInt(s3[0].trim()), s3[1].split("!")[0].trim());
                }
            }
     
            posting.put(term, tmp);       
        });
 
        return posting;
    }

    /**
     * Método que obtem os documentos validos por filtragem da maxima distancia
     * @param terms lista de termos
     * @param docIDs ID dos documentos
     * @param initPosting
     * @return 
     */
    private HashMap<String, HashMap<Integer, String>> getPostingByDistance(List<String> terms, ArrayList<Integer> docIDs, Map<Integer, ArrayList<ScorePosition>> initPosting) {

        HashMap<String, HashMap<Integer, String>> posts = new HashMap<>();
        cont = 0;
        terms.forEach((term) ->{
            HashMap<Integer, String> hash = new HashMap<>();
            initPosting.entrySet().forEach((entry)->{
                if (docIDs.contains(entry.getKey())) {
                    hash.merge(entry.getKey(), String.valueOf(entry.getValue().get(cont).getScore()), (a, b) -> (String.valueOf(Double.valueOf(a) + Double.valueOf(b))));
                }
            });

            posts.put(term, hash); 
            cont++;
        });
        
        return posts;
    }
    
    /**
     * Método que obtem o posting de documentos verificando se a sua distancia e valida
     * @param score_occurs ocorrencias do termo
     * @param dist distancia maxima de procura
     * @param proximity (nao usado)
     * @return 
     */
    private ArrayList<Integer> getDocIDsDistance(Map<Integer, ArrayList<ScorePosition>> score_occurs, int dist, boolean proximity) {

        ArrayList<Integer> docIDs = new ArrayList<>();
        score_occurs.entrySet().stream().forEach(e -> {
            ArrayList<ScorePosition> terms = e.getValue();
            int count = 0;
            // Verify terms distance.
            for (int i = 0; i < terms.size() - 1; i++) {
                ArrayList<Integer> term1 = terms.get(i).getPositions();
                ArrayList<Integer> term2 = terms.get(i + 1).getPositions();
                boolean founded = false;
                for (Integer pos : term1) {
                    for (Integer pos2 : term2) {
                        if ((Math.abs(pos2 - pos) <= dist) && (proximity || (pos2 - pos > 0))) {
                            founded = true;
                            break;
                        }
                        if(founded){
                            break;
                        }
                    }
                }
                if (founded == true) {
                    count++;
                }
            }

            if (count == terms.size() - 1) {
                docIDs.add(e.getKey());
            }
            count = 0;
        });
        return docIDs;
    }
    
    /**
     * Obtem a linha do termo indexado 
     * @param f identficador do ficheiro
     * @param term termo a procurar 
     * @param docId ID do documento onde procurar
     * @return 
     */ 
    private String getLine(char f, String term, int docId) {
        String w = "";
        Path file = Paths.get("outputs/tokenRef_" + String.valueOf(f)+docId);
        
        try (Stream<String> lines = Files.lines(file)) {
            Optional<String> tmp = lines.filter(line -> line.startsWith(term)).findFirst();
            if(tmp.isPresent()){
                w = tmp.get();
            }
        } catch (IOException ex) {}
        return w;
    }
    
    /**
     * Método que faz a procura pelo ficheiro de acordo com a primeira letra do termo
     * @param term termo a procurar
     * @return  
     */
    private char findFile(String term){
        char c = term.charAt(0);
        if(Character.isDigit(c)){
            return '0';
        }
        else return c;
    }

}

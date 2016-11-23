package deti.ir.indexer;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.regex.Pattern;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */

public class Indexer {
    
    private final TokenPost[] tokenRefs;
    
    private final Pattern pattern_ad = Pattern.compile("^[a-d]\\w*");
    private final Pattern pattern_el = Pattern.compile("[e-l]\\w*");
    private final Pattern pattern_mr = Pattern.compile("^[m-r]\\w*");
    private final Pattern pattern_sz = Pattern.compile("^[s-z]\\w*");

    private List<String> alph = new LinkedList<>(); 
    
    private double sumxi;

    private HashMap<String, Integer> tokenFreqDocMap;

    private HashMap<String, String> tokenPosDocMap;

    public Indexer() {
        sumxi = 0;
        // [a-d] [e-l] [m-r] [s-z] restante que nao são letras
        tokenRefs = new TokenPost[5];
        for (int i = 0; i < 5; i++) {
            tokenRefs[i] = new TokenPost(i, 0);
        }
        
        tokenFreqDocMap = new HashMap<>();
        tokenPosDocMap = new HashMap<>();
        
        String alf_str = "0 a b c d e f g h i j k l m n o p q r s t u v w x y z"; 
        alph.addAll(Arrays.asList(alf_str.split(" "))); 
        
    }


    public void addTerm(String token, int docID, int pos) {
       
        tokenPosDocMap.merge(token, pos + "!", (a, b) -> a + b);
        tokenFreqDocMap.merge(token, 1, (a, b) -> a + b);
       
    }

    public void calculateTF(int docID) {

        Map<String, String> tmp;
        tmp = tokenFreqDocMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> String.valueOf(computeValue(e.getValue()))));

        tokenFreqDocMap = new HashMap<>();
        HashMap<String, String> tmp2 = new HashMap<>(tmp);
         
        tmp2.entrySet().forEach((entry) -> {
            //System.out.println("getValue: "+sumxi); 
            tokenRefs[findToken(entry.getKey())].compute(entry.getKey(), (k, v) -> v == null ? getNewHM(docID, entry.getValue(), tokenPosDocMap.get(entry.getKey())) : updateHM(docID, entry.getValue(), v, tokenPosDocMap.get(entry.getKey())));
        });
        
        //System.out.println(tmp2.toString()) ;

        sumxi = 0;
        tokenPosDocMap = new HashMap<>();
    }

    private double computeValue(double value) {
        double tf = 1 + Math.log10(value);
        sumxi += Math.pow(tf, 2);
       // System.out.println("val:"+value+" TF:"+tf+ " root:"+sumxi); 
        return tf;
    }

    private HashMap<Integer, String> getNewHM(int docId, String value, String pos) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(docId, tfNormalization(Double.valueOf(value)) + "-" + pos);
        return map;
    }

    private HashMap<Integer, String> updateHM(int docId, String value, HashMap<Integer, String> hm, String pos) {
        //System.out.println("VALUE "+value); 
        hm.put(docId, tfNormalization(Double.valueOf(value)) + "-" + pos);
        return hm;
    }

    private double tfNormalization(double value) {
        return  value/Math.sqrt(sumxi);
    }

    
    public void freeRefMaps() {
        int i = 0;
        for (TokenPost tf : tokenRefs) {
            if (!tf.isEmpty()) {
                tf.storeTokenMap(tf.getSubID());
                tokenRefs[i] = new TokenPost(i, tf.getSubID() + 1);
            }
            i++;
        }

    }

    
    public void mergeFilesRefs() {

        for (String letra : alph) {
            System.out.println("Juntar tokens com letra:"+letra); 
            
            File directory = new File("outputs");
            File[] files = directory.listFiles();
            
            String pattern = "termRef_"+letra+"_[0-9]+";
            FileFilter filter = new RegexFileFilter(pattern);
            files = directory.listFiles(filter);
            
            TokenPost.storeFinalMap(files, letra);
            
            for(File f : files)
                f.delete();
            
        }
    }
    
   
    private int findToken(String term) {

        if (pattern_ad.matcher(term).matches()) {
            return 0;
        } else if (pattern_el.matcher(term).matches()) {
            return 1;
        } else if (pattern_mr.matcher(term).matches()) {
            return 2;
        } else if (pattern_sz.matcher(term).matches()) {
            return 3;
        } else {
            return 4;
        }
    }
        

}

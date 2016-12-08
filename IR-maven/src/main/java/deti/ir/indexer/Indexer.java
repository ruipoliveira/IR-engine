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
    
    public static  final Pattern pattern_ad = Pattern.compile("^[a-d]\\w*");
    public static final Pattern pattern_el = Pattern.compile("[e-l]\\w*");
    public static final Pattern pattern_mr = Pattern.compile("^[m-r]\\w*");
    public static final Pattern pattern_sz = Pattern.compile("^[s-z]\\w*");

    private List<String> alph = new LinkedList<>(); 
    
    private double sumxi;

    private HashMap<String, Integer> tokenFreqDocMap;

    private HashMap<String, String> tokenPosDocMap;
    
    final File folder = new File(""); 
    
    final File[] files = folder.listFiles( new FilenameFilter() {
    @Override
    public boolean accept( final File dir,
                           final String name ) {
        return name.matches("tokenRef_*");
    }
} );
    
    /**
     * construtor do indexer
     */
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

    /**
     * funcao que adiciona o token, docID e pos nas respectivas estruturas de dados
     * @param token termo do documento
     * @param docID numero de identificacao do documento a ser processado
     * @param pos posicao ou posicoes do token no documento
     */
    public void addTerm(String token, int docID, int pos) {
       
        tokenPosDocMap.merge(token, pos + "!", (a, b) -> a + b);
        tokenFreqDocMap.merge(token, 1, (a, b) -> a + b);
       
    }

    /**
     * funcao que obtem a frequencia com que um termo ocorre num documento e envia esse valor 
     * pra a funcao computeValue calcular o seu peso
     * @param docID numero de identificacao do documento a ser processado
     */
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
    
    /**
     * funcao que calcula o peso do termo no documento
     * @param value frequencia do termo no documento
     * @return 
     */
    
    private double computeValue(double value) {
        double tf = 1 + Math.log10(value);
        sumxi += Math.pow(tf, 2);
       // System.out.println("val:"+value+" TF:"+tf+ " root:"+sumxi); 
        return tf;
    }
    
    /**
     * funcao que armazena na estrutura o docID, o peso normalizado e a posicao no documento
     * de um dado token. A funcao envia o peso do termo para a funcao tfNormalization normalizar o peso
     * @param docId numero de identificacao do documento a ser processado
     * @param value peso do termo no documento ja calculado
     * @param pos posicao em que aparece o token no documento
     * @return 
     */
    private HashMap<Integer, String> getNewHM(int docId, String value, String pos) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(docId, tfNormalization(Double.valueOf(value)) + "-" + pos);
        return map;
    }
    
    /**
     * funcao que faz o mesmo que a anterior mas actualiza a estrutura quando aparece o mesmo termo
     * @param docId numero de identificacao do documento a ser processado
     * @param value peso do termo no documento ja calculado
     * @param hm estrutura que tem armazenado o docID, peso normalizado e posicao(oes) de cada token
     * @param pos posicao em que aparece o token no documento
     * @return 
     */
    private HashMap<Integer, String> updateHM(int docId, String value, HashMap<Integer, String> hm, String pos) {
        //System.out.println("VALUE "+value); 
        hm.put(docId, tfNormalization(Double.valueOf(value)) + "-" + pos);
        return hm;
    }
    
    /**
     * funcao que calcula a normalizacao do peso do termo no documento
     * @param value peso do termo no documento
     * @return 
     */
    private double tfNormalization(double value) {
        return  value/Math.sqrt(sumxi);
    }

    
    /**
     * quando uma certa percentagem da memoria atribuida inicialmente na execucao do programa e atingida,
     * consequentemente essa memoria e libertada. 
     */
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

    /**
     * funcao que junta num unico ficheiro o resultado dos diferentes ficheiros gerados pela funcao anterior
     */
    
    /*
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
    
    */
    
    
    public void joinRefMaps(int doc) {
        freeRefMaps();

        for (String letter : alph) {
            int numberOfFiles = tokenRefs[findToken(letter)].getSubID();
            TokenPost tr = new TokenPost(0, 0);
            for (int i = 0; i < numberOfFiles; i++) {
                TokenPost tri = new TokenPost(findToken(letter), i);
                tri.loadTermRefMap(letter, i);
                tr.mergeRefMap(tri);
                tri = null;
            }
            System.out.println(letter+" DONE!"); 

            tr.storeFinalMap(letter, doc);
            
        }
        
        for ( final File file : files ) {
            if ( !file.delete() ) {
                System.err.println( "Can't remove " + file.getAbsolutePath() );
            }
        }
        
        
    }
    
    
    
    
    
    /**
     * funcao que especifica em que mapa um termo deve ser guardado de acordo com a sua letra inicial
     * @param term token do documento
     * @return 
     */
    public static int findToken(String term) {

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

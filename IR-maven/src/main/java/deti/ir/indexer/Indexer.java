package deti.ir.indexer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Indexer {
    
    private final TokenIDDocFreqMap tokenIDDocFreq;
    private final TokenFreqMap tokenFreq;
    
    /**
     * Construtor do Indexer
     */
    public Indexer() {
        tokenFreq = new TokenFreqMap();
        tokenIDDocFreq = new TokenIDDocFreqMap();

    }
    
    /**
     * metodo que adiciona o termo
     * @param docID
     * @param token 
     */
    public void addTerm(int docID, String token) {
        tokenFreq.merge(token, 1, (a, b) -> a + b);
        tokenIDDocFreq.compute(token, (k, v) -> v == null ? getNewHM(docID) : updateHM(docID, v));
    }
    
    /**
     * metodo que obtém o value de uma key caso esta ainda não tenha nenhuma, ou seja, 
     * é o primeiro docID a ser adicionado à posting list de um dado termo
     * @param docId
     * @return map
     */
    private HashMap<Integer, Integer> getNewHM(int docId) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(docId, 1);
        return map;
    }

    /**
     * metodo que actualiza o value de uma dada key, ou seja, adiciona um docID à posting list
     * @param docId
     * @param hm
     * @return hm
     */
    private HashMap<Integer, Integer> updateHM(int docId, HashMap<Integer, Integer> hm) {
        hm.merge(docId, 1, (a, b) -> a + b);
        return hm;
    }
    
    /**
     * metodo que gera o ficheiro da frequencia de um dado token  
     */
    public void generateFileTokenFreq() {
        TokenFreqMap tk = new TokenFreqMap();
        tk.loadTokenFreq();
        tokenFreq.mergeTokenFreq(tk);
        tk.clear();
        try {
            Files.delete(FileSystems.getDefault().getPath("outputs/TokenFreq.txt"));
        } catch (IOException ex) {}

        tokenFreq.storeTokenFreq(true);
        tokenFreq.clear();
    }

    /**
     * metodo que gera o ficheiro com o resultado pretendido: (token, freqToken, Posting list)
     */
    public void generateFileTokenFreqDocs(){
        TokenIDDocFreqMap tk = new TokenIDDocFreqMap();
        tk.loadTokenIDDocFreq();
        tokenIDDocFreq.mergeTokenIDDocFreq(tk);
        tk.clear();
        try {
            Files.delete(FileSystems.getDefault().getPath("outputs/TokenFreqDocs.txt"));
        } catch (IOException ex) {}

        tokenIDDocFreq.storeTokenIDDocFreq(true);
        tokenIDDocFreq.clear();
    }
    
      
    /**
     * metodo que retorna o tokenIDDocFreq
     * @return 
     */
    public TokenIDDocFreqMap getTokenIDDocFreq() {
        return tokenIDDocFreq;
    }
    
    /**
     * Metodo para retorna o tokenFreq
     * @return 
     */
    public TokenFreqMap getTokenFreq() {
        return tokenFreq;
    }
    
}

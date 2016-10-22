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

    public Indexer() {
        tokenFreq = new TokenFreqMap();
        tokenIDDocFreq = new TokenIDDocFreqMap();

    }
    
    public void addTerm(int docID, String token) {
        tokenFreq.merge(token, 1, (a, b) -> a + b);
        tokenIDDocFreq.compute(token, (k, v) -> v == null ? getNewHM(docID) : updateHM(docID, v));
    }

    private HashMap<Integer, Integer> getNewHM(int docId) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(docId, 1);
        return map;
    }


    private HashMap<Integer, Integer> updateHM(int docId, HashMap<Integer, Integer> hm) {
        hm.merge(docId, 1, (a, b) -> a + b);
        return hm;
    }

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

    public TokenIDDocFreqMap getTokenIDDocFreq() {
        return tokenIDDocFreq;
    }

    public TokenFreqMap getTokenFreq() {
        return tokenFreq;
    }
    
}

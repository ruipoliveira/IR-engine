package deti.ir.indexer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class TokenIDDocFreqMap extends HashMap<String, HashMap<Integer, Integer>> {

    public TokenIDDocFreqMap(){
        super();
    }
    
    public void storeTokenIDDocFreq(boolean sortedStore){
        try {
            Properties p;
            if (sortedStore){
                p = new Properties() {
                    @Override
                    public synchronized Enumeration<Object> keys() {
                        return Collections.enumeration(new TreeSet<Object>(super.keySet()));
                    }
                };  
            }
            else{
                p = new Properties();
            } 
            for (Map.Entry<String, HashMap<Integer, Integer>> entry: this.entrySet()){
                String freqL = "";
                int total = 0 ; 
                
                for (Map.Entry<Integer, Integer> fl: entry.getValue().entrySet())
                {
                    total+=fl.getValue(); 
                    freqL += fl.getKey() + "-";
                }
                p.put(entry.getKey()+";"+total+";"+freqL,"");
            }
            
            p.store(new FileOutputStream("outputs/TokenFreqDocs.txt"), null);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {}
    }
    
    
    
    public void loadTokenIDDocFreq(){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("termRef"));
            for (String key: p.stringPropertyNames()) {
                HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
                String refs[] = p.getProperty(key).replaceAll("[^0-9]", " ").split("\\s+");
                for (int i = 1; i < refs.length-1; i+=2)
                {
                    hm.put(Integer.parseInt(refs[i]), Integer.parseInt(refs[i+1]));
                }
                this.put(key, hm);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {}
    }
    

    public void mergeTokenIDDocFreq(TokenIDDocFreqMap trm){
        for(String s : trm.keySet()){
            HashMap<Integer,Integer> temp = this.get(s);
            if(temp != null){
                HashMap<Integer,Integer> toMerge = trm.get(s);
                toMerge.forEach((k,v) -> temp.merge(k,v, (a,b) -> a+b));
                this.put(s, temp);
            }else{
                this.put(s, trm.get(s));
            }
        }
        
    }



        
}

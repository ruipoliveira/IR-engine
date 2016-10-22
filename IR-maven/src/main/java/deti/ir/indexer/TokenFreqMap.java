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
public class TokenFreqMap extends HashMap<String, Integer> {

    public TokenFreqMap() {
        super();
    }
    
    public void storeTokenFreq(boolean sortedStore){
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
            for (Map.Entry<String, Integer> entry: this.entrySet())
            {
                p.put(entry.getKey(), Integer.toString(entry.getValue()));
            }
            p.store(new FileOutputStream("outputs/TokenFreq.txt"), null);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {}
    }
    
    
    public void loadTokenFreq(){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("TokenFreq"));
            for (String key: p.stringPropertyNames()){
                this.put(key, Integer.parseInt(p.getProperty(key)));
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {}
    }
    
    public void mergeTokenFreq(TokenFreqMap tfm){
        tfm.forEach((k,v) -> this.merge(k,v, (a,b) -> a+b));
    }
    

}

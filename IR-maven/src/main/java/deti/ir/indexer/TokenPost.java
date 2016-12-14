package deti.ir.indexer;

import deti.ir.memory.MemoryManagement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */

/**
 * construtor da estrutura final usada na indexacao
 * @author gabriel
 */
public class TokenPost extends HashMap<String, HashMap<Integer, String>> {

    private final int id;
    private final int subID;
    private MemoryManagement x; 
    public TokenPost(int id, int subID) {
        super();
        this.id = id;
        this.subID = subID;
        this.x = new MemoryManagement(); 
    }
    
    /**
     * especifica onde sao escritos os ficheiros temporarios
     * @param subID 
     */
    public void storeTokenMap(int subID) {
        String[] groups = getChar(id);

        for (String group : groups) {
            File file; 

            if (id == 4)
                file= new File("tokenRef_0_" + id + subID); 
            else
                file= new File("tokenRef_" + group.charAt(1) + "_" + id + subID);
            
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                this.entrySet()
                        .stream()
                        .sorted(Entry.comparingByKey())
                        .forEachOrdered(e -> {
                            if (e.getKey().matches(group)) {
                                try {
                                    writer.write(e.getKey() + " - " + e.getValue().entrySet()
                                            .stream()
                                           // .sorted(Entry.comparingByKey())
                                            .map(Object::toString)
                                            .collect(Collectors.joining(", ")) + "\n");
                                } catch (IOException ex) {
                                    Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * especifica onde sao escritos os ficheiros 
     * @param files lista de ficheiros
     * @param firstLetter primeira letra de um token
     */
    public static void storeFinalMap(File[] files, String firstLetter) {
       
        File file = new File("outputs/tokenRef_" + firstLetter);
        
        FileWriter fstream = null;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(file, true);
             out = new BufferedWriter(fstream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
 
        for (File f : files) {
            //System.out.println("merging: " + f.getName());
            FileInputStream fis;
            try {
                fis = new FileInputStream(f);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
                String aLine;
                while ((aLine = in.readLine()) != null) {
                    out.write(aLine);
                    out.newLine();
                }
 
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }


    public void loadTermRefMapAux(String fileLetter) {
        //System.out.println("File: "+ fileLetter); 
        HashMap<Integer, String> hm;
        Path file = Paths.get("tokenRef_" + fileLetter);
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                hm = new HashMap<>();
                String[] refs = line.split(" - ");
                String[] refs2 = refs[1].split(", ");
                ArrayList<String> list = new ArrayList<>();
                for(String s : refs2){
                    String[] refs3 = s.split("=");
                    for(String s2 : refs3){
                        list.add(s2);
                    }
                }                
                for (int j = 0; j < list.size() - 1; j += 2) {
                    hm.put(Integer.parseInt(list.get(j).trim()), (list.get(j + 1).trim()));
                }
                this.put(refs[0].trim(), hm);
            }
            reader.close();
        } catch (IOException ex) {}
    }

        public void mergeRefMap(TokenPost trm) {
        //For each key in termRefMap 
        for (String s : trm.keySet()) {
            HashMap<Integer, String> temp = this.get(s);
            //if that key exists in current termRefmap
            if (temp != null) {
                //Merge current value with new one
                HashMap<Integer, String> toMerge = trm.get(s);
                toMerge.forEach((k, v) -> temp.merge(k, v, (a, b) -> a + b));
                //replace old hashmap with new one
                this.put(s, temp);
            } else {
                //key does not exist in current map, add it
                this.put(s, trm.get(s));
            }
        }

    }
        
    public void storeFinalMap(String firstLetter, int doc) {
        
        File file = new File("outputs/tokenRef_" + firstLetter+doc);
        
        final String letter;

        if (firstLetter.equals("0"))
            letter = "\\d+";
        else    
            letter = firstLetter;
        
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            this.entrySet()
                    .stream()
                    .sorted(Entry.comparingByKey())
                    .forEachOrdered(e -> {
                        if (e.getKey().matches("^"+letter+".*")) {
                            try {
                                writer.write(e.getKey() + " - " + e.getValue().entrySet()
                                        .stream()
                                        .sorted(Entry.comparingByKey())
                                        .map(Object::toString)
                                        .collect(Collectors.joining(", ")) + "\n");
                            } catch (IOException ex) {
                                Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TokenPost.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    /**
     * Loads an hashmap from a file.
     * @param firstLetter
     * @param i 
     */
    public void loadTermRefMap(String firstLetter, int i) {
        //System.out.println("firstLetter: "+ firstLetter); 
        loadTermRefMapAux(firstLetter + "_" + id + i);
    }

    
    
    

    /**
     * funcao que retorna o subID que e usado na escrita dos ficheiros temporarios
     * @return 
     */
    public int getSubID() {
        return subID;
    }

    /**
     * funcao que retorna o grupo a que pertence um termo de acordo com o id recebido na funcao findToken
     * @param id especificacao do grupo
     * @return 
     */
    public String[] getChar(int id) {
        String[] group;
        switch (id) {
            case 0: {
                return group = new String[]{"^a.*", "^b.*", "^c.*", "^d.*"};
            }
            case 1: {
                return group = new String[]{"^e.*", "^f.*", "^g.*", "^h.*", "^i.*", "^j.*", "^k.*", "^l.*"};
            }
            case 2: {
                return group = new String[]{"^m.*", "^n.*", "^o.*", "^p.*", "^q.*", "^r.*"};
            }
            case 3: {
                return group = new String[]{"^s.*", "^t.*", "^u.*", "^v.*", "^w.*", "^x.*", "^y.*", "^z.*"};
            }
            default: {
                return group = new String[]{"^\\d.*"};
            }
        }
    }
    

}

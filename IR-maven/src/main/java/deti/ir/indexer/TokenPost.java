package deti.ir.indexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class TokenPost extends HashMap<String, HashMap<Integer, String>> {

    private int id, subId;

    public TokenPost(int id, int subID) {
        super();
        this.id = id;
        subId = subID;
    }

    
    public void storeTermRefMap(int subID) {
        String[] groups = getCharGroup(id);

        for (String group : groups) {
            File file = new File("outputs/termRef_" + group.charAt(1) + "_" + id + subID);
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                this.entrySet()
                        .stream()
                        .sorted(Entry.comparingByKey())
                        .forEachOrdered(e -> {
                            if (e.getKey().matches(group)) {
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
    }

    public void storeFinalMap(String firstLetter) {
       
        File file = new File("outputs/termRef_" + firstLetter);
                
        final String letter;
        if(firstLetter.equals("\\"))
            letter = firstLetter.concat("d+");
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

    public void loadTermRefMapAux(String fileLetter) {
        HashMap<Integer, String> hm;
        Path file = Paths.get("outputs/termRef_" + fileLetter);
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

    
    public void loadTermRefMap(String firstLetter, int i) {
        loadTermRefMapAux(firstLetter + "_" + id + i);
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

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getId() {
        return id;
    }

    public String[] getCharGroup(int id) {
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

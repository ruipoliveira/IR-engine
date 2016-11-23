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
                return group = new String[]{"^0.*"};
            }
        }
    }
    

}

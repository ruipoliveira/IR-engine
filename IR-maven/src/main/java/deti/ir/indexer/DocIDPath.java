package deti.ir.indexer;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class DocIDPath {
   private int id;
   private String docId; 
   private String path; 
   
   /**
    * Constructor para a identificação do ficheiro, DocID e caminho 
    * @param id
    * @param docId
    * @param path 
    */
   public DocIDPath(int id, String docId, String path){
       this.id = id; 
       this.docId = docId; 
       this.path = path; 
   }
   
   /**
    * Retorna o ID do documento
    * @return docID
    */
    public String getDocId() {
        return docId;
    }
    
    /**
     * set docId to docID
     * @param docId 
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }
    
    /**
     * função que retorna o path
     * @return path 
     */
    public String getPath() {
        return path;
    }
    
    /**
     * set path to path
     * @param path 
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * função que retorna o ID do documento
     * @return id 
     */
    public int getId() {
        return id;
    }
    
    /**
     * set id to id
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Função que estabelece o formato de impressão
     * @return 
     */
    @Override
    public String toString() {
        return "DocIDPath{" + "id=" + docId + ", path=" + path + '}';
    }
}

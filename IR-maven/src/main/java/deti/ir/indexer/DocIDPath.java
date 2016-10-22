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
   
   public DocIDPath(int id, String docId, String path){
       this.id = id; 
       this.docId = docId; 
       this.path = path; 
   }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DocIDPath{" + "id=" + docId + ", path=" + path + '}';
    }
}

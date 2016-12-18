package deti.ir.searcher;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class Query {
    private String contQuery; 
    private int type; 
    private int proximity; 

    /**
     * Type 1 e Type 0
     * @param type
     * @param contQuery 
     */
    public Query(int type, String contQuery){
        this.contQuery = contQuery; 
        this.type = type;
        this.proximity = -1;
    }
    
    
    /**
     * Type 3 
     * @param type
     * @param contQuery 
     * @param proximity 
     */
    public Query(int type, String contQuery, int proximity){
        this.contQuery = contQuery; 
        this.type = type;
        this.proximity = proximity;
    }
    
    
    public String getContQuery() {
        return contQuery;
    }

    public int getType() {
        return type;
    }

    public int getProximity() {
        return proximity;
    }

    public void setContQuery(String contQuery) {
        this.contQuery = contQuery;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.query;

/**
 *
 * @author roliveira
 */
public class Query {
    private String contQuery; 
    private int type; 
    private String fieldKey; 
    private int proximity; 
    private String fieldValue;

    /**
     *  Type 1 e Type 0
     * @param type
     * @param contQuery 
     */
    
    public Query(int type, String contQuery){
        this.contQuery = contQuery; 
        this.type = type;
        this.proximity = -1;
        this.fieldKey = "";
        this.fieldValue = "";
    }
    
    
    /**
     * Type 3 
     * @param type
     * @param contQuery 
     */
    public Query(int type, String contQuery, int proximity){
        this.contQuery = contQuery; 
        this.type = type;
        this.proximity = proximity;
        this.fieldKey = "";
        this.fieldValue = "";
    }
    
        /**
     * Type 4 
     * @param type
     * @param contQuery 
     * @param fieldKey 
     * @param fieldValue 
     */
    public Query(int type, String contQuery, String fieldKey, String fieldValue){
        this.contQuery = contQuery; 
        this.type = type;
        this.proximity = -1;
        this.fieldKey = fieldKey;
        this.fieldValue = fieldValue;
    }

    public String getContQuery() {
        return contQuery;
    }

    public int getType() {
        return type;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public int getProximity() {
        return proximity;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setContQuery(String contQuery) {
        this.contQuery = contQuery;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}

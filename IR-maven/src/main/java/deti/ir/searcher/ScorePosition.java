package deti.ir.searcher;

import java.util.*;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class ScorePosition { 

    private final double score;
    

    private final ArrayList<Integer> positions;

    public ScorePosition(double score, ArrayList<Integer> positions){
        this.score = score;
        this.positions = positions;
    }
    
    
    /**
     * Método que retorna o score
     * @return 
     */
    public double getScore() {
        return score;
    }
    
    /**
     * Método que retorna as posições dos termos 
     * @return 
     */
    public ArrayList<Integer> getPositions() {
        return positions;
    }
}

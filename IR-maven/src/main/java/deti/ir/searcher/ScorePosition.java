/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.searcher;

import java.util.*;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
 */
public class ScorePosition {

    private double score;
    

    private ArrayList<Integer> positions;

    public ScorePosition(double score, ArrayList<Integer> positions){
        this.score = score;
        this.positions = positions;
    }


    public double getScore() {
        return score;
    }


    public ArrayList<Integer> getPositions() {
        return positions;
    }
}

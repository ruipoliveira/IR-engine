/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deti.ir.memory;

/**
 *
 * @author gabriel
 */
public class MemoryManagement {
    
  
    /**
     * JVM Runtime state.
     */
    private Runtime runtime;
    
    /**
     * Simple class to get memory values.
     */
    public MemoryManagement()
    {
        runtime = Runtime.getRuntime();
    }
    
    /**
     * Get the amount of memory currently being consumed.
     * @return Memory in Mbs.
     */
    public long getCurrentMemory()
    {
        return (runtime.totalMemory() - runtime.freeMemory()) / 1000000;
    }
    
    /**
     * Print the amount of memory currently being consumed.
     */
    public void printMemory(){
        System.out.println((runtime.totalMemory() - runtime.freeMemory()) / 1000000);
}
}

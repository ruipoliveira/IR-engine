
package deti.ir.memory;

/**
 * Universidade de Aveiro, DETI, Recuperação de Informação 
 * @author Gabriel Vieira, gabriel.vieira@ua.pt
 * @author Rui Oliveira, ruipedrooliveira@ua.pt
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

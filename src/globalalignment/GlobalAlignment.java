/**
 * Computes the best global alignment.
 */
package globalalignment;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main class. Creates interface that parses input from the fasta file and from the score matrix. Then creates class SmithWaterman that computes the best global alignment.
 * Call format of the main function: expected 2 arguments: first - pairs.fasta, second - blosum62.txt
 * @author Kuba
 */
public class GlobalAlignment {
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        if(args.length != 2){
            System.out.printf("Wrong call format.\n");
            return;
        }
        
        Interface i = new Interface();
        try {
            i.getPairs(args[0]);
            i.getMatrix(args[1]);
        } catch (IOException ex) {
            System.out.printf("Could not read data from the given files.\n");
            Logger.getLogger(GlobalAlignment.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
       
        SmithWaterman solution = new SmithWaterman(i.first, i.second, i.score);
        solution.align();
        solution.backtrack();
        
        System.out.printf("\n---\n\nScore = %d\n%s\n%s\n%s\n", solution.getScore(), solution.getFirstAligned(), solution.getAlignment(), solution.getSecondAligned());
    }
}

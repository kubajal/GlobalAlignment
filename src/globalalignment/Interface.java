package globalalignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles all input, e. g. reads sequences from the fasta file and score matrix.
 * @author Kuba
 */
public class Interface {
    
    /**
     * The alphabet of the score matrix.
     */
    
    public String alphabet = null;
    
    /**
     * A Java String representation of the first DNA sequence.
     */
    public String first = null;
    /**
     * A Java String representation of the second DNA sequence.
     */
    public String second = null;
    /**
     * A HashMap representing the score matrix used during aligning two sequences: {@link globalalignment.Interface#first} and {@link globalalignment.Interface#second}.
     * To get the generic value score[a][b] (where a and b are characters that we compare) we do a lookup in the outer HashMap (aka score) with the key of a. In return we get the HashMap which represents the weight verctor for the character a in which we do a second lookup to find the relation between a and b.
     */
    public HashMap<Character, HashMap<Character, Integer>> score;
    
    /**
     * Parses the given .fast file. Initializes first and second String objects that represent DNA sequences to align.
     * @param fileName the name of the file in FASTA format containing two sequences to align
     * @throws IOException if error occured while reading the file
     */
    
    public void getPairs(String fileName) throws IOException{
        BufferedReader br = null;
        InputStream in = null;
        InputStreamReader s = null;
        String text = null;
        
        in = new FileInputStream(fileName);
        s = new InputStreamReader(in);
        br = new BufferedReader(s);
        StringBuilder sb = new StringBuilder();
        String line = null;
        
        /* ------ parse the .fasta file ------ */
        
        while((line = br.readLine()) != null){
            if(line.charAt(0) != '>')
                break;
            sb.append("\n");
            sb.append(line);
        }
        
        System.out.printf("FASTA description of the first sequence: %s\n", sb.toString());
        
        sb = new StringBuilder().append(line);
        
        while((line = br.readLine()) != null){
            
            if(line.charAt(0) != '>')
                sb = sb.append(line);
            else
                break;
        }

        first = sb.toString();
        
        System.out.printf("first sequence: %s\n", first);
        
        sb = new StringBuilder().append(line);
        
        while((line = br.readLine()) != null){
            if(line.charAt(0) != '>')
                break;
            sb.append("\n");
            sb.append(line);
        }
        
        System.out.printf("FASTA description of the second sequence: \n%s\n", sb.toString());
        
        sb = new StringBuilder().append(line);
        
        while((line = br.readLine()) != null)
            sb = sb.append(line);

        second = sb.toString();
        
        System.out.printf("second sequence: %s\n", second);
        
        
        /* ---------------------------------- */
    }
    
    /**
     * Reads the score matrix {@link globalalignment.Interface#score} with the values given in the matrix.txt file.
     * @param fileName the name of the file containing score matrix. Its format should be as follows:
     * 1) optional: a few first lines starting with # are comments,
     * 2) first non-comment line consists the alphabet (letters separated by blank spaces)
     * 3) further non-comment lines start with a letter followed by |sigma| integers, describing the line in the score matrix corresponding to the given letter.
     * @throws IOException if error occured while reading the file
     */
    
    public void getMatrix(String fileName) throws IOException{
        
        BufferedReader br = null;
        InputStream in = null;
        InputStreamReader s = null;
        String text = null;
        
        in = new FileInputStream(fileName);
        s = new InputStreamReader(in);
        br = new BufferedReader(s);
        String line = null;
        
        /* ------ parse blosum62.txt -------- */
        
        while((line = br.readLine()) != null && line.charAt(0) == '#'); // comment lines
        
        alphabet = line.replaceAll("\\s", "");  // ereases all whitespaces
        
        int c;
        score = new HashMap<>();
        for(int i = 0; i < alphabet.length(); i++){
            score.put(alphabet.charAt(i), new HashMap<>());
        }
        
        for(int i = 0; i < alphabet.length(); i++){
            
            c = br.read();
            if(c != alphabet.charAt(i))
                throw new IOException("Wrong matrix.txt format.\n");    // simple validation of matrix.txt: first letter of this row doesnt correspond to the expected letter
            
            line = br.readLine();
            line = line.substring(1);
            
            List<String> weights = new ArrayList(Arrays.asList(line.split("\\s")));
            weights.removeAll(Arrays.asList("", null));
            int j = 0;
            
            for(String it : weights){
                
                score.get(alphabet.charAt(j)).put((char) c, Integer.parseInt(it));
                j++;
            }
        }
        
        /*for(int i = 0; i < alphabet.length(); i++){
            
            for(int j = 0; j < alphabet.length(); j++){
                
                System.out.printf("%d ", score.get(alphabet.charAt(i)).get(alphabet.charAt(j)));
            }
            
            System.out.printf("\n");
        }*/
        
        /* --------------------------------- */
        
    }
}

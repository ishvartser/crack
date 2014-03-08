import java.util.*;
import java.io.*;
import java.lang.*;

// Defines all possible substrings according to transition matrix
class WordEntry {
	public String[] letters;
	public int[] location;
}

public class SuperWordSearch {

	/* Generate transition matrix */

	public void generateTransitionMatrix(char firstChar, String[] puzzle) {

		

	}

	// Build all possible strings


	// Check to see if one of possible strings match with word in wordbank


	public static void main (String[] args) {

	
		try {

 			File file = new File(args[0]);
 			BufferedReader reader = new BufferedReader (new FileReader(file));
 			String line = null;
 			int[] values = new int[10]; // this value needs to be changed
 			String[] words = new String[10]; // this value needs to be changed
 			int i = 0;
 			int w = 0;
 			int whereWordsCtr = 0;
 			String wrapInfo = "";
 			boolean wrap;

 			// Clean up input, get words, & read WRAP or NO_WRAP option
 			while ((line = reader.readLine()) != null) {
 				line = line.trim();
 				if (!line.equals("")) {
 					// Get words from wordbank
 					if (whereWordsCtr > 5) { // (# of rows + 2)
	 					System.out.println ("Word: " + line);
	 					words[w] = line;
	 					w++;
	 				}
	 				// Get puzzle from file
	 				else if (whereWordsCtr > 0 && whereWordsCtr < 4) { // (>0, <#rows+1)
	 					System.out.println ("Puzzle: " + line);
	 				}
	 				whereWordsCtr++;
	 			
	 			if (line.equals("WRAP")) {
	 				wrap = true;
	 				System.out.println ("WrapTrue: " + wrap);
	 			}
	 			if (line.equals("NO_WRAP")) {
	 				wrap = false;
	 				System.out.println ("WrapFalse: " + wrap);
	 			}
	 			}
 			}

 			// Extract matrix dimension & number of words (last entry)
 			Scanner scan = new Scanner(file);
 			while (scan.hasNext()) {
 				if (scan.hasNextInt()) {
	 				values[i] = scan.nextInt();
 					System.out.println ("Value: " + values[i]);
 					i++;
 				}
 				else {
 					scan.next();
 				}
 			}


		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
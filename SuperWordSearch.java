import java.util.*;
import java.io.*;
import java.lang.*;

// Defines all possible substrings according to transition matrix
class WordEntry {
	public String[] letters;
	public int[] location;
}

class SuperWordSearch {

	/* Generate transition matrix */

	void generateTransitionMatrix(char firstChar, char[][] puzzle) {

		char[] possibleTransitionLetter = new char[100]; // Value needs to be changed
		int[] letterLocation = new int[100]; // Value needs to be changed
		int ctr = 0;
		// if some letter is 1 away from firstChar, add it to possibleTransitionLetter[]
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[i].length; j++) {
				System.out.println ("firstChar: "+ firstChar);
				System.out.println ("generate..Puzzle: "+ puzzle[i][j]);
				if (letterLocation[i]-1 == i ||
						letterLocation[i]+1 == i ||
						letterLocation[j]-1 == j ||
						letterLocation[j]+1 == j) {

					possibleTransitionLetter[ctr] = puzzle[i][j];
					ctr++;
					System.out.println ("possibleTransitionLetter at "+ctr+" : "+possibleTransitionLetter[ctr]);
				}

			}
		}



	}

	// Build all possible strings


	// Check to see if one of possible strings match with word in wordbank



	void run(String[] args) throws IOException {

		File file = new File(args[0]);
		BufferedReader reader = new BufferedReader (new FileReader(file));

		String line = null;
		char[][] puzzle = new char[3][3]; // Value needs to be changed
		int[] values = new int[10]; // this value needs to be changed
		String[] words = new String[10]; // this value needs to be changed
		int i = 0;
		int w = 0;
		int whereWordsCtr = 0;
		String wrapInfo = "";
		boolean wrap;

		int numCols;

		// Clean up input, get words, & read WRAP or NO_WRAP option
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (!line.equals("")) {
				numCols = 0;
				// Get words from wordbank
				if (whereWordsCtr > 5) { // (# of rows + 2)
					System.out.println ("Word: " + line);
					words[w] = line;
					w++;
				}
				// Get puzzle from file
				else if (whereWordsCtr > 0 && whereWordsCtr < 4) { // (>0, <#rows+1)
					//System.out.println ("Puzzle: " + line);
					for (int a=0; a<1; a++) {
						for (int b=0; b<puzzle[a].length; b++) {
							puzzle[a][b] = line.charAt(b);
							System.out.print(" Letter added: "+puzzle[a][b]);
						}
						System.out.println ("");
						numCols++;
					}
					System.out.println ("");

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
		generateTransitionMatrix(puzzle[1][1], puzzle);

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

	public static void main (String[] args) throws IOException{
		new SuperWordSearch().run(args);
	}

}
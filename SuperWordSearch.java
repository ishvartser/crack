import java.util.*;
import java.io.*;
import java.lang.*;

// Defines all possible substrings according to transition matrix
class WordEntry {
	public String[] letters;
	public int[][] location;
}

class PuzzleEntry {
	public String[] letters;
	public int[] location;
}

class SuperWordSearch {


	/* Retrieve location of letter in */



	/* Generate transition matrix */

	// need to pass in location here, not the actual first character
	void generateTransitionMatrix(char firstChar, char[][] puzzle) {

		char[] possibleTransitionLetter = new char[100]; // Value needs to be changed
		int[] letterLocation = new int[100]; // Value needs to be changed
		int ctr = 0;
		int[][] directions = new int[][] { {-1,-1},
												   {-1,0},
												   {-1,1},
												   {0,1},
												   {1,1},
												   {1,0},
												   {1,-1},
												   {0,-1} };
		// if some letter is 1 away from firstChar, add it to possibleTransitionLetter[]
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[i].length; j++) {
				//System.out.println ("firstChar: "+ firstChar);
				//System.out.println ("generate..Puzzle: "+ puzzle[i][j]);
				System.out.println ("firstChar: "+firstChar);
				System.out.println ("puzzle[i][j]: "+puzzle[i][j]);
				if (firstChar == puzzle[i][j]) {
					// get location of char, which are the current indices
					letterLocation[0] = i;
					letterLocation[1] = j;
					System.out.println ("[i]: "+i);
					System.out.println ("[j]: "+j);

					List<Integer> res = new ArrayList<Integer>();
				    for (int[] direction : directions) {
				        int cx = i + direction[0];
				        System.out.println ("direction[0]: "+direction[0]);
				        System.out.println ("cx: "+cx);
				        int cy = j + direction[1];
				        System.out.println ("direction[1]: "+direction[1]);
				        System.out.println ("cy: "+cy);
				        if(cy >=0 && cy < puzzle.length) {
				            if(cx >= 0 && cx < puzzle[cy].length){
				                //res.add(puzzle[cy][cx]);
				                System.out.println("Added surrounding letter... "+puzzle[cx][cy]);
				            }
				        }
				    }

					// now loop through the puzzle to find possible transitions
					// for (int a=0; a<puzzle.length; a++) {
					// 	for (int b=0; b<puzzle[a].length; b++) {
						
					// 		// if current index 1 away from letterLocation, then possible transition found
					// 		if (letterLocation[0]-1 == a ||
					// 			letterLocation[0]+1 == a ||
					// 			letterLocation[1]-1 == b ||
					// 			letterLocation[1]+1 == b) {

					// 			possibleTransitionLetter[ctr] = puzzle[i][j];
					// 			System.out.println ("possibleTransitionLetter at "+ctr+" : "+possibleTransitionLetter[ctr]);
					// 			ctr++;
					// 		}
					// 	}
					// }
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

		WordEntry wordEntry = new WordEntry();
		wordEntry.letters = new String[12];
		wordEntry.location = new int[5][5];

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
					System.out.println ("Letters added...");
					for (int a=0; a<puzzle.length; a++) {
						puzzle[whereWordsCtr-1][a] = line.charAt(a);

						System.out.print(" " + puzzle[whereWordsCtr-1][a] + " ");
						numCols++;
					}
					System.out.println ("");
				}

				whereWordsCtr++;
				if (line.equals("WRAP")) {
					wrap = true;
					//System.out.println ("WrapTrue: " + wrap);
				}
				if (line.equals("NO_WRAP")) {
					wrap = false;
					//System.out.println ("WrapFalse: " + wrap);
				}
			}
		}

		System.out.println ("First word's 1st letter: "+ words[0].charAt(0));
		generateTransitionMatrix(words[0].charAt(0), puzzle);

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
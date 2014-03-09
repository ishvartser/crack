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

	/* Constants */

	final int[][] directions = new int[][] {{-1,-1},
										   	{-1,0},
											{-1,1},
											{0,1},
											{1,1},
											{1,0},
											{1,-1},
											{0,-1}};


	/* Generate transition matrix */

	void generateTransitionMatrix(char firstChar, char[][] puzzle) {

		int[] letterLocation = new int[2];
		// If some letter is 1 away from firstChar, add it to possibleTransitions list
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[i].length; j++) {
				if (firstChar == puzzle[i][j]) {
					letterLocation[0] = i;
					letterLocation[1] = j;
					List<Character> possibleTransitions = new ArrayList<Character>();
				    for (int[] direction : directions) {
				        int cx = i + direction[0];
				        int cy = j + direction[1];
				        if(cy >=0 && cy < puzzle.length) {
				            if(cx >= 0 && cx < puzzle[cy].length){
				                possibleTransitions.add(puzzle[cy][cx]);
				                System.out.println("Added surrounding letter... " + puzzle[cx][cy]);
				                System.out.println("Direction[0]... " + direction[0] + "   Direction[1]... " + direction[1]);
				                
				                // Continue on this direction to check out next possible letter
				                if (findLetters(cx, cy, direction, puzzle)) {
				                	// Start building possible strings
				                }
				            }
				        }
				    }
				}
			}
		}
	}

	/* Find all letters on the trajectory */

	boolean findLetters (int cx, int cy, int[] direction, char[][] puzzle) {
		int nextX = cx + direction[0];
        int nextY = cy + direction[1];
        if(nextY >=0 && nextY < puzzle.length) {
            if(nextX >= 0 && nextX < puzzle[cy].length){ 
            	System.out.println ("OH YEAH, Also added... "+puzzle[nextX][nextY]);
            	return true;
            }
        }
        return false;
	}


	/* Build all possible strings */

	void buildPossibleStrings (List<Character> possibleTransitions, char[][] puzzle, char firstChar, 
														char surroundingLetter, int dirx, int diry) {

		// Build strings by continuing in the given direction, if possible
		// List<String> possibleStrings = new ArrayList<String>();

		// // Can we continue on the given direction?
		// if(diry >=0 && diry < puzzle.length) {
  //           if(dirx >= 0 && dirx < puzzle[cy].length){
  //               possibleTransitions.add(puzzle[cy][cx]);
  //               System.out.println("Added surrounding letter... " + puzzle[cx][cy]);
  //               System.out.println("Direction[0]... " + direction[0] + "   Direction[1]... " + direction[1]);
  //           }
  //       }

		// for (char c : possibleTransitions) {
		// 	String stringToAdd = "" + firstChar + c;
		// 	possibleStrings.add();
		// }

	}

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

		//System.out.println ("First word's 1st letter: "+ words[2].charAt(0));
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
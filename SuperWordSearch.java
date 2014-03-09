import java.util.*;
import java.io.*;
import java.lang.*;

// Defines all possible substrings according to transition matrix
class WordEntry {
	public String[] letters;
	public int[][] location;
}

// Defines various input arguments
class PuzzleInput {
	public String[] words;
	public int x_dimension;
	public int y_dimension;
	public int whichWord;
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

	List<String> possibleStrings = new ArrayList<String>();
	PuzzleInput input;
	char[][] puzzle;

	/* Generate transition matrix */

	void generateTransitionMatrix(char firstChar, char[][] puzzle) 
	{
		int[] letterLocation = new int[2];
		// If some letter is 1 away from firstChar, add it to possibleTransitions list
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[i].length; j++) {
				if (firstChar == puzzle[i][j]) {
					letterLocation[0] = i;
					letterLocation[1] = j;
					List<String> possibleTransitions = new ArrayList<String>();
				    for (int[] direction : directions) {
				        int cx = i + direction[0];
				        int cy = j + direction[1];
				        if(cy >=0 && cy < puzzle.length) {
				            if(cx >= 0 && cx < puzzle[cy].length){
				                possibleTransitions.add(Character.toString(puzzle[cy][cx]));
				                //System.out.println("Added surrounding letter... " + puzzle[cx][cy]);
				                //System.out.println("Direction[0]... " + direction[0] + "   Direction[1]... " + direction[1]);
				                buildPossibleStrings(Character.toString(puzzle[cx][cy]), firstChar);
				                // Continue on this direction to check out next possible letter
				                if (findLetters(cx, cy, direction, puzzle)) {
				                	possibleTransitions.add (""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]]);
				                	//System.out.println( "Combo: "+""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]] );
				                	buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]], firstChar);
				                }
				            }
				        }
				    }
				}
			}
		}
	}

	/* Find all letters on the trajectory */

	boolean findLetters (int cx, int cy, int[] direction, char[][] puzzle) 
	{
		int nextX = cx + direction[0];
        int nextY = cy + direction[1];
        if(nextY >=0 && nextY < puzzle.length) {
            if(nextX >= 0 && nextX < puzzle[cy].length){ 
            	//System.out.println ("OH YEAH, Also added... "+puzzle[nextX][nextY]);
            	return true;
            }
        }
        return false;
	}

	/* Build all possible strings */

	void buildPossibleStrings (String nextChar, char firstChar) 
	{
		// Build strings by continuing in the given direction, if possible
		String stringToAdd = "" + firstChar + nextChar;
		possibleStrings.add(stringToAdd);
		checkWord();
		System.out.println ("Possible string: "+stringToAdd);
	}

	/* Check to see if one of possible strings match with word in wordbank */

	boolean checkWord ()
	{	
		for (String s : possibleStrings) {
			// System.out.println ("s: "+s);
			// System.out.println ("Input.words @ QTY: "+input.words[input.wordBankQty-1]);
			if (s.equals(input.words[input.whichWord])) {
				System.out.println ("Woohoo! We found the word!");
				return true;
			}
		}
		return false;
	}

	void run(String[] args) throws IOException 
	{
		File file 			  	= new File(args[0]);
		BufferedReader reader 	= new BufferedReader (new FileReader(file));
		int[] values 			= new int[10];
		input 					= new PuzzleInput();
		String line = null;
		int i = 0;
		int w = 0;
		int whereWordsCtr = 0;
		String wrapInfo = "";
		boolean wrap;
		int numCols;


		// Extract matrix dimension & number of words (last entry)
		Scanner scan = new Scanner(file);
		while (scan.hasNext()) {
			if (scan.hasNextInt()) {
				values[i] = scan.nextInt();
				puzzle = new char[values[0]][values[1]];
				input.words = new String[values[2]];
				System.out.println ("Value: " + values[i]);
				i++;
			}
			else {
				scan.next();
			}
		}

		// Clean up input, get words, & read WRAP or NO_WRAP option
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (!line.equals("")) {
				numCols = 0;
				// Get words from wordbank
				if (whereWordsCtr > 5) { // (# of rows + 2)
					System.out.println ("Word: " + line);
					input.words[w] = line;
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
		for (int x=0; x<input.words.length; x++) {
			// We may want to empty the list possibleStrings after we finish with a letter
			input.whichWord = x;
			generateTransitionMatrix (input.words[x].charAt(0), puzzle);
		}
	}

	public static void main (String[] args) throws IOException
	{
		new SuperWordSearch().run(args);
	}

}
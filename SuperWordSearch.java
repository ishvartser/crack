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

	/* Constants & Globals */

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
		boolean nbymMatrix_X = false;
		boolean nbymMatrix_Y = false;
		boolean nbymMatrix = false;
		// If some letter is 1 away from firstChar, add it to possibleTransitions list
		for (int i=0; i<puzzle.length; i++) {
			for (int j=0; j<puzzle[i].length; j++) {
				if (firstChar == puzzle[i][j]) {
					letterLocation[0] = i;
					letterLocation[1] = j;
					System.out.println ("i: "+i+" "+" j: "+j);
					List<String> possibleTransitions = new ArrayList<String>();
				    for (int[] direction : directions) {

				    	// System.out.println ("puzzle-length-row : "+puzzle.length);
				    	// System.out.println ("puzzle-length-col : "+puzzle[i].length);

				        int cx = i + direction[0];
				        int cy = j + direction[1];
				        if (cx < 0) {
				        	cx++;
				        }
				        if (cy < 0) {
				        	cy++;
				        }
				        // System.out.println ("dir[0]: "+direction[0]+" "+" dir[1]: "+direction[1]);
				        // System.out.println ("cx: "+cx+" "+" cy: "+cy);
				        // System.out.println ("puzzle.length (ROWS) "+puzzle.length);
				        // System.out.println ("puzzle.length[i] (COLS) "+puzzle[i].length);
				        
				        // calculate difference and add to puzzle.length?

				        /* IF NxM matrix, N<M */
				        if (puzzle.length <= puzzle[i].length) {

				         if(cy >=0 && cy < puzzle.length) {
				        // if(cy >=0 && cy < puzzle.length - Math.abs(puzzle.length - puzzle[i].length) ) {
				        	// System.out.println ("cy after FIRSTCHECK: "+cy);
				            if(cx >= 0 && cx < puzzle[cy].length - Math.abs(puzzle.length - puzzle[i].length) ){
				            	// cy = (cy - Math.abs(puzzle.length - puzzle[cy].length));
				            	//System.out.println ("cx < : " + (puzzle[cy].length - Math.abs(puzzle.length - puzzle[cy].length)));
				                // System.out.println("Adding surrounding letter... " + puzzle[cx][cy]);
				                possibleTransitions.add(Character.toString(puzzle[cx][cy]));
				                //System.out.println("Direction[0]... " + direction[0] + "   Direction[1]... " + direction[1]);
				                buildPossibleStrings(Character.toString(puzzle[cx][cy]), firstChar);
				                // Continue on this direction to check out next possible letter
				                if (findLetters(cx, cy, direction, puzzle)) {
				                	// correct for out of bounds
				                	if (nbymMatrix_X) {
				                		possibleTransitions.add (""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]]);
				                		// System.out.println( "NbyM Combo: "+""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]] );
										buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx][cy+direction[1]], firstChar);
									}
									else if (nbymMatrix_Y) {
				                		possibleTransitions.add (""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]]);
				                		// System.out.println( "NbyM Combo: "+""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]] );
										buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy], firstChar);	
									}
				                	else {
				                		if ((cx+direction[0]) >= puzzle.length) {
								        	cx--;
								        	// System.out.println ("cx after decrement: "+cx);
								        	nbymMatrix_X = true;
								        }
								        if ((cy+direction[1]) >= puzzle[cy].length) {
								        	cy--;
								        //	System.out.println ("cy after decrement: "+cy);
								        	nbymMatrix_Y = true;
								        }
				                		//System.out.println ("possibleTransitionStuff: "+puzzle[cx][cy]);//+" & "+ puzzle[cx+direction[0]][cy+direction[1]]);
				                		possibleTransitions.add (""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]]);
				                		//System.out.println( "Combo: "+""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]] );
				                		buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy+direction[1]], firstChar);
				                	}
				                }
				            }
				        }
						} else {

						// if(cy >=0 && cy < puzzle.length) {
				        if(cy >=0 && cy < puzzle.length - Math.abs(puzzle.length - puzzle[i].length) ) {
				        	// System.out.println ("cy after FIRSTCHECK: "+cy);
				            if(cx >= 0 && cx < puzzle[cy].length){// - Math.abs(puzzle.length - puzzle[i].length) ){
				            	// cy = (cy - Math.abs(puzzle.length - puzzle[cy].length));
				            	//System.out.println ("cx < : " + (puzzle[cy].length - Math.abs(puzzle.length - puzzle[cy].length)));
				                // System.out.println("Adding surrounding letter... " + puzzle[cx][cy]);
				                possibleTransitions.add(Character.toString(puzzle[cx][cy]));
				                //System.out.println("Direction[0]... " + direction[0] + "   Direction[1]... " + direction[1]);
				                buildPossibleStrings(Character.toString(puzzle[cx][cy]), firstChar);
				                // Continue on this direction to check out next possible letter
				                if (findLetters(cx, cy, direction, puzzle)) {
				                	// correct for out of bounds
				                	if (nbymMatrix_X) {
				                		possibleTransitions.add (""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]]);
				                		// System.out.println( "NbyM Combo: "+""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]] );
										buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx][cy+direction[1]], firstChar);
									}
									else if (nbymMatrix_Y) {
				                		possibleTransitions.add (""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]]);
				                		// System.out.println( "NbyM Combo: "+""+puzzle[cx][cy]);
				                			//+puzzle[cx+direction[0]][cy+direction[1]] );
										buildPossibleStrings(""+puzzle[cx][cy]+puzzle[cx+direction[0]][cy], firstChar);	
									}
				                	else {
				                		if ((cx+direction[0]) >= puzzle.length) {
								        	cx--;
								        	// System.out.println ("cx after decrement: "+cx);
								        	nbymMatrix_X = true;
								        }
								        if ((cy+direction[1]) >= puzzle[cy].length) {
								        	cy--;
								        //	System.out.println ("cy after decrement: "+cy);
								        	nbymMatrix_Y = true;
								        }
				                		//System.out.println ("possibleTransitionStuff: "+puzzle[cx][cy]);//+" & "+ puzzle[cx+direction[0]][cy+direction[1]]);
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
			if (s.equals(input.words[input.whichWord])) {
				System.out.println ("Woohoo! We found the word!");
				return true;
			}
		}
		return false;
	}

	/* Collects input data and runs the program */

	void run(String[] args) throws IOException 
	{
		File file 			  	= new File(args[0]);
		BufferedReader reader 	= new BufferedReader (new FileReader(file));
		int[] values 			= new int[10];
		input 					= new PuzzleInput();
		String line = null;
		int i = 0;
		int w = 0;
		int whereWordsCtr = -1;
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
				whereWordsCtr++;
				numCols = 0;
				// Get words from wordbank
				if (whereWordsCtr > (values[0]+2)) { // (# of rows + 2)
					System.out.println ("Word: " + line);
					input.words[w] = line;
					w++;
				}
				// Get puzzle from file
				else if ( (whereWordsCtr > 0) && (whereWordsCtr < (values[0]+1)) )  { // (>0, <#rows+1)
					System.out.println ("Puzzle: " + line);
					System.out.println ("Letters added...");
					for (int a=0; a<line.length(); a++) {
						puzzle[whereWordsCtr-1][a] = line.charAt(a);
						System.out.print(" " + puzzle[whereWordsCtr-1][a] + " ");
						numCols++;
					}
					System.out.println ("");
				}
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
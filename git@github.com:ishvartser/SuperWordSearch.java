import java.util.*;
import java.io.*;
import java.lang.*;

public class SuperWordSearch {

	public static void main (String[] args) {

	
		try {

 			File file = new File(args[0]);
 			BufferedReader reader = new BufferedReader (new FileReader(file));
 			String line = null;
 			int[] values = new int[10];
 			String[] words = new String[10];
 			int i = 0;
 			int ctr = 0;
 			String wrapInfo = "";
 			boolean wrap;

 			// Clean up input & read WRAP or NO_WRAP option
 			while ((line = reader.readLine()) != null) {
 				line = line.trim();
 				if (!line.equals("") && ctr > 5) {
	 				System.out.println ("line: " + line);
	 				ctr++;
	 			}
	 			if (line.equals("WRAP")) {
	 				wrap = true;
	 				System.out.println ("WrapTrue: " + wrap);
	 			}
	 			if (line.equals("NO_WRAP")) {
	 				wrap = false;
	 				System.out.println ("WrapFalse: " + wrap);
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
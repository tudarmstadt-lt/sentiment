/**
 * Copyright (C) 2008 by
 * 
 * 	Cam-Tu Nguyen
 *  ncamtu@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *  
 *  Thu-Trang Nguyen
 *  trangnt84@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *
 * JNSP is a free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JNSP is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JNSP; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package jnsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class Util {
	
	//Specify the hypothesis which we want to test
	// for example: 0:1:2 = 0|1|2 for testing P(w0w1w2) = P(w0) P(w1) P(w2)
	//              0:1:2 = 0:1|2 for testing P(w0w1w2) = P(w0w1) P(w2) 
	public static String [] parseHypoPattern(String pattern){
		try {
			String [] ret = pattern.split("[=\\|]");
			for (int i = 0; i < ret.length; ++i){
				ret[i] = ret[i].trim();
			}			
			return ret;
		}
		catch (Exception e){
			System.out.println("Error while parsing hypothesis pattern:" + e.getMessage());
			return null;
		}
	}
	
	// extract frequency combo from pattern with the following format
	// for trigrams and the pattern 3=0:1:2|0|1|2|0:1|0:2
	//		we count the frequency of w0w1w2, w0 at the first place, w1 at the second place,... 
	public static  Hashtable<Integer, String[]> readFreqCombos(File file){
		try {
			Hashtable<Integer, String[]> freqCombo = new Hashtable<Integer, String[]>();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null){
				String[] tokens = line.split("=");
				
				if (tokens.length!=2) continue;
				
				String key = tokens[0];
				String val = tokens[1];
				String [] patterns = val.split("\\|");
				freqCombo.put(Integer.valueOf(key), patterns);
			}
			reader.close();			
			return freqCombo;
		}
		catch (Exception e){
			System.out.println("Error while reading freq combo; set to default");			
			return null;
		}		
	}
	
	
	public static String [] genDefaultFreqCombo(int window){
		String [] freqCombo;
		freqCombo = new String[(int)Math.pow(2, window) - 1];
		
		//set combinations
		String [] combinations =  new String[window];
		for (int i = 0; i < combinations.length; ++i)
			combinations[i] = "";
	
		for (int i = 1; i < freqCombo.length + 1; ++i){						
			int [] temp = checkBits(i);
			
			String curCombination = "";
			for (int j = 0; j < temp.length - 1 ; ++j){
				curCombination += temp[j] + ":";
			}
			curCombination += temp[temp.length - 1];
			
			combinations[temp.length % window] += ";" + curCombination;
		}
		
		int count = 0;
		for (int i = 0; i < combinations.length; ++i){
			String temp = combinations[i];
			
			//System.out.println(temp);
			StringTokenizer tk = new StringTokenizer(temp, ";");
			while (tk.hasMoreTokens()){
				String next = tk.nextToken();
				if (!next.equals("")){
					freqCombo[count++] = next;
				}	
			}
		}
		
		return freqCombo;
	}
	
	public static Hashtable<Integer, String[]> genDefaultFreqCombos(int window){
	
		Hashtable<Integer, String[]> freqCombo = new Hashtable<Integer, String[]>();
		
		for(int i=1; i<=window; i++){
			String [] freq = Util.genDefaultFreqCombo(i);
			freqCombo.put(i, freq);
		}
		
		return freqCombo;
	}
	
	public static int [] checkBits( int x ){
		Vector<Integer> setBitPosVec = new Vector<Integer>();
		for (int i = 0; i < 32; ++i){
			int test  = x & 1;
			if (test == 1)
				setBitPosVec.add(i);
			
			x >>>= 1;
		}
		
		int [] ret = new int[setBitPosVec.size()];
		for (int i = 0; i < ret.length; ++i){
			ret[i] = setBitPosVec.elementAt(i);
		}
		
		return ret;
	}
	
	
	static public List<File> getFileListing( File aStartingDir) 
	throws FileNotFoundException {
		validateDirectory(aStartingDir);
	    List<File> result = new ArrayList<File>();

	    File[] filesAndDirs = aStartingDir.listFiles();
	    List<File> filesDirs = Arrays.asList(filesAndDirs);
	    for(File file : filesDirs) {	      
	      if ( ! file.isFile() ) {
	        //must be a directory
	        //recursive call!
	        List<File> deeperList = getFileListing(file);
	        result.addAll(deeperList);
	      }
	      else {
	    	  if (file.getName().endsWith(".txt"))
	    		  result.add(file); //add file *.txt only
	      }
	    }
	    
	    return result;
	}
	
	static private void validateDirectory (File aDirectory)
		throws FileNotFoundException {
		if (aDirectory == null) {
		       throw new IllegalArgumentException("Directory should not be null.");
		     }
		     if (!aDirectory.exists()) {
		       throw new FileNotFoundException("Directory does not exist: " + aDirectory);
		     }
		     if (!aDirectory.isDirectory()) {
		       throw new IllegalArgumentException("Is not a directory: " + aDirectory);
		     }
		     if (!aDirectory.canRead()) {
		       throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
		     }
	}
	
	/**
	 * Check whether we should filter this ngram
	 * @param n
	 * @return true if ngram shuold be filtered and false otherwise
	 */
	static boolean ngramFilter(NGram n){
		for (int i = 0; i < n.size(); ++i){
			String token = n.dict.getWord((n.getTokenId(i)));
			for (int j = 0; j < token.length(); ++j)
				if (!Character.isLetter(token.charAt(j)) )
					return true;
		}
		return false;
	}
	
	public static void main(String [] args){
		parseHypoPattern("0:1:2 = 0|1|2");
	}
}

package collapsingDep;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*this program gets only dependencies (per sentence) include words belong to this set 
 * {"JJ", "NN", "NNS", "RB", "RBR", "RBS", "VB", "VBD", "VBN", "VBG"}
 * And frequently belong to this set only through all the file 
 * */

public class CollapsingDep {
	public static void main(String[] args) throws IOException {
		// 
		File[] listOfDepFiles = new File("C:/Users/skohail/Desktop/experiment/dep").listFiles();
		String fnum;
		String current;
		String pos_conc ="";
		String dep_conc="";
		for (int i=0; i<listOfDepFiles.length;i++){
			System.out.println("sttttttttttttttttttttttttt");
		fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
		//System.out.println(fnum);
		Scanner dep_file = new Scanner(new File("C:/Users/skohail/Desktop/experiment/dep/only_sen"+fnum+".txt_dep.txt"));
		Scanner indx_file = new Scanner(new File("C:/Users/skohail/Desktop/experiment/indx/only_indx"+fnum+".txt"));
		Scanner POS_file = new Scanner(new File("C:/Users/skohail/Desktop/experiment/POS/only_sen"+fnum+".txt_POS.txt"));	
		FileWriter collapsed_dep = new FileWriter("C:/Users/skohail/Desktop/experiment/collapsed/filtered_by_POS"+fnum+".txt");
		dep_file.useDelimiter("@_@"); 
		POS_file.useDelimiter("@_@"); 
		indx_file.useDelimiter("\n");
			String [] words;
			String []co;
			String filtered="";
			//String finale="";
			  Map<Integer, Integer> sentences_index_map = new TreeMap<Integer, Integer>();
			while (indx_file.hasNext()){
				
				current=indx_file.next().split(":")[1];
				Integer freq = sentences_index_map.get(Integer.valueOf(current)); 
				sentences_index_map.put(Integer.valueOf(current), (freq == null) ? 1 : freq + 1); 
			}
			//int ix=0;
			for (int indx: sentences_index_map.keySet()){
				
			//reuniting POS and deps for each sentence according to the index file		
				for (int x=0;x<sentences_index_map.get(indx);x++){
					if(POS_file.hasNext())
					pos_conc=pos_conc+" "+POS_file.next().replaceAll("\n", " ").trim();
					if(dep_file.hasNext())
					dep_conc=dep_conc+" "+dep_file.next().replaceAll("\n", "@_@").trim();
			}
				//this hashset includes the words with allowed POS tags
			     HashSet<String> allowed_pos = new HashSet<>();
			
				//ix++;
				words=pos_conc.trim().split(" ");
				// put all words belons to arr array into a hashmap named (unique)
				for (int y=0;y<words.length;y++){
					co=words[y].split("/");
				//	System.out.println(co.length);
					if(co.length>=2 && checkele(co[1])) 
						allowed_pos.add(co[0].trim().toLowerCase());
					
				}
				 // filter allowed pos from non real arr pos tags 
		/*		 Iterator<String> iterator = allowed_pos.iterator(); 
				 //this hashset includes the final words after removing those that are not frequently have allowed POS tags
				 HashSet<String> bigFinalMap = new HashSet<String>();
			      // check values
			      while (iterator.hasNext()){
			    		Map<String, Integer> mapPerWord = new TreeMap<String, Integer>();
						Scanner POS_file_for_check = new Scanner(new File("C:/Users/skohail/Desktop/experiment/POS/only_sen"+fnum+".txt_POS.txt"));	
						POS_file_for_check.useDelimiter("@_@");
						   String theWord=iterator.next(); 
						   String [] wordsAndTagsInLine;
						   String [] pairWordTag;
						while (POS_file_for_check.hasNext()){
							wordsAndTagsInLine=POS_file_for_check.next().split(" ");
							
							for (int w=0;w<wordsAndTagsInLine.length;w++){
								pairWordTag=wordsAndTagsInLine[w].split("/");
								if(!theWord.isEmpty() && pairWordTag.length>=2){
								
									if(pairWordTag[0].trim().equals(theWord)){
										Integer tagFrequency = mapPerWord.get(pairWordTag[1]); 
										mapPerWord.put(pairWordTag[1], (tagFrequency == null) ? 1 : tagFrequency + 1); 
									}
								}
							}
							
						}
						POS_file_for_check.close();
						if(!mapPerWord.isEmpty()){
						String popular= checkPop(mapPerWord);
						if(checkele(popular))
							bigFinalMap.add(theWord);
						}

			      }*/
				
				filtered=compareToFilter(allowed_pos,dep_conc.toLowerCase()).replaceAll("@_@", "\n").trim();
				collapsed_dep.write(filtered+"\n");
				dep_conc="";
				pos_conc="";
				filtered="";


	} 
			
			collapsed_dep.close();
			dep_file.close();
			indx_file.close();
			POS_file.close();
			
		}
		
		

	}
	/*this function returns true when the pos tag assigned to the word belongs to arr array
	 * and false otherwise*/
	public static boolean checkele( String targetValue) {
		String[] arr={"JJ", "NN", "NNS", "RB", "RBR", "RBS", "VB", "VBD", "VBN", "VBG","VBZ","NNP"};
	    for(String s: arr){
	        if(s.equals(targetValue))
	            return true;
	    }
	    return false;
	}
	/*function to pront a hash set contents
	public static void printSet(HashSet <String>newset){
		 Iterator<String> iterator = newset.iterator(); 
	      
	      // check values
	      while (iterator.hasNext()){
	         System.out.print(iterator.next() + " ");  
	      }
	      System.out.println();
		
	}
	*/
	/*function returns pairs dependencies (per sentence) that include at least
	 * one word belongs to arr array*/
	public static String compareToFilter(HashSet <String>newset, String s){
		String []pairs=s.split("@_@");
		String []toCompare;
		//int m=0;
		String result="";
		for (int i=0;i<pairs.length;i++){
			Pattern p = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
			Matcher matcher = p.matcher(pairs[i]);
		
			if (matcher.find()){
				String match= matcher.group(0);
			
			toCompare=match.split(",");
		
			toCompare[0]=toCompare[0].replaceAll("[\\)\\(\\-\\d]", "").trim();
			toCompare[1]=toCompare[1].replaceAll("[\\)\\(\\-\\d]", "").trim();
			if(newset.contains(toCompare[0]) || newset.contains(toCompare[1]) ){
		//	m++;
			result=result+"@_@"+pairs[i];	
			}
			}
		
		
	}
		///System.out.println(m);
		return result.trim();
	}
	/* check what is the popular POS tag for one word by checking the map of
	 * <tag , tag frequncy> per word MAP
	 * and return the String of the popular POS tag  */
	
public static String checkPop(Map<String, Integer> map){
		
		String mxi=null;
		        int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
		        for (Entry<String, Integer> entry : map.entrySet()) {  // Itrate through hashmap
		            if (entry.getValue()==maxValueInMap) {
		               mxi=entry.getKey();     // Print the key with max value
		            }
		        }
return mxi;
		    
	}
}

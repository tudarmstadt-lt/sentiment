package classifyingWordsbyPOS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class ClassifyingWordsbyPOS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
		File[] listOfCPOSFiles = new File("C:/Users/skohail/Desktop/experiment/tfidf_filtered_ordered").listFiles();
		String fnum;
		for (int i=0; i<listOfCPOSFiles.length;i++){
		fnum=listOfCPOSFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
		//System.out.println(fnum);
			Scanner tfidf_filtered_ordered = new Scanner(new File("C:/Users/skohail/Desktop/experiment/tfidf_filtered_ordered/tfidf_filtered_"+fnum+".txt"));
			Scanner words_pos = new Scanner(new File("C:/Users/skohail/Desktop/experiment/allowed_words_withPOS/final_allowed_words_withPOS"+fnum+".txt"));
			FileWriter word_tfidf_pos = new FileWriter("C:/Users/skohail/Desktop/experiment/word_tfidf_pos/word_tfidf_pos"+fnum+".txt");
			Map <String, String> wordsnPOS = new HashMap <String,String>();
			tfidf_filtered_ordered.useDelimiter("\n");
			words_pos.useDelimiter("\n");
			
			String current;
			
		while(words_pos.hasNext()){
			current=words_pos.next();
			if(current.split(" ").length>=2)
			wordsnPOS.put(current.split(" ")[0], current.split(" ")[1]);
			
		}
			
			while(tfidf_filtered_ordered.hasNext()){
				
				current = tfidf_filtered_ordered.next().trim();
				
			if(current.split("\t").length>=2 ){
			word_tfidf_pos.write(current.split("\t")[0].trim()+"\t"+current.split("\t")[1].trim()+"\t"+wordsnPOS.get(current.split("\t")[0].trim())+"\n");
			}
			
			}
			
			tfidf_filtered_ordered.close();
			word_tfidf_pos.close();
			words_pos.close();
			
			
		}

	}
	public static boolean checkele( String targetValue) {
		String[] arr={"JJ", "NN", "NNS", "RB", "RBR", "RBS", "VB", "VBD", "VBN", "VBG","VBZ","NNP"};
	    for(String s: arr){
	        if(s.equalsIgnoreCase(targetValue))
	            return true;
	    }
	    return false;
	}
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

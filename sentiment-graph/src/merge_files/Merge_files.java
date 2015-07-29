package merge_files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Merge_files {
public static void main(String[] args) throws IOException {
	System.out.println("tfidf dir (tfidf_filteredNUM.txt) \n allowed w pos freq dir (allowed_words_wPOS_and_freqNUM.txt) \n word_pos_freq_tfidf dir (word_pos_freq_tfidfNUM.txt)");
	
	File[] listOfDepFiles = new File(args[0]).listFiles();
	String fnum;
	for (int i=0; i<listOfDepFiles.length;i++){
		fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
		System.out.println(fnum);
		Map<String, String> single = new HashMap<String, String>(); 
	Scanner single_tfidf_file = new Scanner(new File(args[0]+"tfidf_filtered"+fnum+".txt"));
	Scanner w_pos_freq = new Scanner(new File(args[1]+"allowed_words_wPOS_and_freq"+fnum+".txt"));
	FileWriter merged = new FileWriter(args[2]+"word_pos_freq_tfidf"+fnum+".txt");
	single_tfidf_file.useDelimiter("\n");
	w_pos_freq.useDelimiter("\n");
	
	while (w_pos_freq.hasNext()){
		String []next=w_pos_freq.next().split("\\t");
		if (next.length>=3)
		single.put(next[0].trim(), next[1].trim());
		
		
	}
	w_pos_freq.close();
	while (single_tfidf_file.hasNext()){
	String real= single_tfidf_file.next();
	String [] splitted =real.split("\\t");
	if(splitted.length>=4){
		merged.write(splitted[0].trim()+"\t"+splitted[1].trim()+"\t"+splitted[3].trim()+"\t"+single.get(splitted[0].trim())+"\n");
	}
	}
	
	single_tfidf_file.close();
	
	merged.close();
	}
	}
}

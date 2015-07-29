package filter_opinion_words;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class filter_opinions {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
    	File[] listOfDepFiles = new File("C:/Users/skohail/Desktop/experiment/triple_tfidf/").listFiles();
    	String fnum;
    	Map<String, Integer> opinions = new HashMap<String, Integer>(); 
    	Scanner opinion_words = new Scanner(new File("C:/Users/skohail/Desktop/experiment/sentiment/opinion_words.txt"));
    	opinion_words.useDelimiter("\n");
    	int ii=0;
    	while (opinion_words.hasNext()){
    		opinions.put(opinion_words.next().trim(), ii);
    		ii++;
    		
    	}
    	opinion_words.close();
    	   
		for (int i=0; i<listOfDepFiles.length;i++){
			fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
			System.out.println(fnum);
			FileWriter graph_structure = new FileWriter("C:/Users/skohail/Desktop/experiment/filtered_by_opinion_words- this is for comparison/filtered_by_opinion_words"+fnum+".txt");
			
			Scanner graph_table = new Scanner(new File("C:/Users/skohail/Desktop/experiment/triple_tfidf/triple_pairs_"+fnum+".txt"));
			
			graph_table.useDelimiter("\n");
			while (graph_table.hasNext()){
				String this_next=graph_table.next();
				String []line = this_next.split("\t")[0].split(" ");
				//String freq=line[0];
				String relation = line[0];
				String source = line [1];
				String dist=line [2];
				if((relation.equals("amod") || relation.equals("nsubj")) && (opinions.containsKey(source) || opinions.containsKey(dist)))
				{

					graph_structure.write(this_next+"\n");
					
				}
			}
			
			graph_structure.close();
			graph_table.close();
		
		}
	}

}

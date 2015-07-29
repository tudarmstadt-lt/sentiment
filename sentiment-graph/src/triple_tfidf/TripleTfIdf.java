package triple_tfidf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TripleTfIdf {

	public static void main(String[] args) throws IOException {

    	String fnum;
    	System.out.println("files dir (table_graphNUM.txt) \n output dir (triple_tfidfNUM.txt)");
    	File[] listOfDepFiles = new File(args[0]).listFiles();
    	String []save_file_numbers = new String [listOfDepFiles.length];
    	
    //	int trak=0;
    	List<String[]> documents = new ArrayList<String[]>();
    	List<Integer[]> numbers_doc = new ArrayList<Integer[]>();
    	for (int i=0; i<listOfDepFiles.length;i++){
 
    		fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
    		System.out.println(fnum);
    		save_file_numbers[i]=fnum;
    		String []theTokens;
    		String current_pair;
    		Scanner collapsed_table = new Scanner(new File(args[0]+"table_graph"+fnum+".txt"));
    		collapsed_table.useDelimiter("\n");
    		ArrayList<String> temporary=new ArrayList<String>();
    		ArrayList<Integer> numbers=new ArrayList<Integer>();
    		while (collapsed_table.hasNext()){
    	
    			current_pair=collapsed_table.next().trim();
    				theTokens=current_pair.split("\t");
   			if(theTokens.length>=4){
   				numbers.add(Integer.valueOf(theTokens[0].trim()));
   				temporary.add(theTokens[1].trim()+" "+theTokens[2].trim()+" "+theTokens[3].trim());
    			}
    			}
    		
    		String[] terms = new String[temporary.size()];
    		Integer[] frequencies = new Integer[numbers.size()];
    		terms=temporary.toArray(new String[temporary.size()]);
    		frequencies=numbers.toArray(new Integer[numbers.size()]);
    		documents.add(terms);
    		numbers_doc.add(frequencies);
    		collapsed_table.close();
    		
    	}
    	
    	double tf_idf;
    	double tf=0;
    	double idf=0;
    	
    	
    	for (int x=0;x<documents.size();x++){
    		HashMap <String, Double> hash_for_term_tfidf =new HashMap<String, Double>();
    		//List<String> used_words = new ArrayList<String>();
    		String [] current_doc=documents.get(x);
    		Integer [] current_num=numbers_doc.get(x);
    		System.out.println("/////////////////"+x+"////////////////");
    		for (int y=0;y<current_doc.length;y++){
    		
    			tf=current_num[y];
				idf=idfCalculator(documents,current_doc[y]);
    			tf_idf=current_num[y] * idfCalculator(documents,current_doc[y]);
    			hash_for_term_tfidf.put(current_doc[y]+"\t"+tf+"\t"+idf, tf_idf);
    		
    		}
    		String filenum=listOfDepFiles[x].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
    		writeHashToFile(hash_for_term_tfidf,args[1]+"triple_tfidf"+filenum+".txt");
    		System.out.println("written: "+filenum);
    		//filtered_table.close();
    		
    	}
    
    	
    

	}
	/* public static double tfCalculator(String[] totalterms, String termToCheck) {
	        double count = 0;  //to count the overall occurrence of the term termToCheck
	        for (String s : totalterms) {
	            if (s.equalsIgnoreCase(termToCheck)) {
	                count++;
	            }
	        }
	        return count ;
	    }*/

	    /**
	     * Calculates idf of term termToCheck
	     * @param allTerms : all the terms of all the documents
	     * @param termToCheck
	     * @return idf(inverse document frequency) score
	     */
	    public static double idfCalculator(List<String[]> allTerms, String termToCheck) {
	        double count = 0;
	   
	        for (String[] ss : allTerms) {
	            for (String s : ss) {
	                if (termToCheck.equalsIgnoreCase(s)) {
	                    count++;
	                    break;
	                }
	            }
	        }
	        return  Math.log(allTerms.size() / count);
	    }
	    static void writeHashToFile(HashMap<String, Double> hashToWrite, String fileName) throws IOException{
	    	
	    	FileWriter filtered_tfidf = new FileWriter(fileName);
			for (HashMap.Entry <String,Double>entry : hashToWrite.entrySet()) {
				filtered_tfidf.write(entry.getKey() + "\t" + entry.getValue()+"\n");
			}
			filtered_tfidf.close();
	    	 
	    }

}

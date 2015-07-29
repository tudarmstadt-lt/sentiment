package tfidf_pairs;

 


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class PairsTfIdf {
    public static void main(String[] args) throws IOException {
    	String fnum;
    	System.out.println("files dir (table_graphNUM.txt) \n output dir (pairs_tfidfNUM.txt)");
    	File[] listOfDepFiles = new File(args[0]).listFiles();
    	String []save_file_numbers = new String [listOfDepFiles.length];
    	
    //	int trak=0;
    	List<String[]> documents = new ArrayList<String[]>();
    	for (int i=0; i<listOfDepFiles.length;i++){
 
    		fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
    		System.out.println(fnum);
    		save_file_numbers[i]=fnum;
    		String []theTokens;
    		//String []terms = null;
    		
    		String current_pair;
    		Scanner collapsed_table = new Scanner(new File(args[0]+"table_graph"+fnum+".txt"));
    		collapsed_table.useDelimiter("\n");
    		ArrayList<String> temporary=new ArrayList<String>();;
    		while (collapsed_table.hasNext()){
    		// temporary = new ArrayList<String>();
    			current_pair=collapsed_table.next().trim();
    			String [] toSort=new String[2];
    			//Arrays.sort(
    				theTokens=current_pair.split("\t");
   			if(theTokens.length>=4){
   				toSort[0]=theTokens[2].trim();
   				toSort[1]=theTokens[3].trim();
   				Arrays.sort(toSort);
        			temporary.add(toSort[0]+" "+toSort[1]);
    			}
    			}
    		
    		String[] terms = new String[temporary.size()];
    		terms=temporary.toArray(new String[temporary.size()]);
    		documents.add(terms);
    		collapsed_table.close();
    		
    	}
    	
    	double tf_idf;
    	double tf=0;
    	double idf=0;
    	for (int x=0;x<documents.size();x++){
    		HashMap <String, Double> hash_for_term_tfidf =new HashMap<String, Double>();
    		List<String> used_words = new ArrayList<String>();
    		String [] current_doc=documents.get(x);
    		System.out.println("/////////////////"+x+"////////////////");
    		for (int y=0;y<current_doc.length;y++){
    			if(!used_words.contains(current_doc[y])){
    				tf=tfCalculator(current_doc,current_doc[y]);
    				idf=idfCalculator(documents,current_doc[y]);
    				tf_idf=tf * idf;
    				hash_for_term_tfidf.put(current_doc[y]+"\t"+tf+"\t"+idf, tf_idf);
    		
    				used_words.add(current_doc[y]);
    			}
    			//	System.out.println(current_doc[y]+"\t"+tf_idf+"\n");
    		}
    		String filenum=listOfDepFiles[x].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
    		writeHashToFile(hash_for_term_tfidf,args[1]+"pairs_tfidf"+filenum+".txt");
    		//filtered_table.close();
    		
    	}
    
    	
    }
    /**
     * Calculates the tf of term termToCheck
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public static double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count ;
    }

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
                if (s.equalsIgnoreCase(termToCheck)) {
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

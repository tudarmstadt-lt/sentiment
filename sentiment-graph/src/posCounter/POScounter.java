package posCounter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class POScounter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
	
		//System.out.println(fnum);
			Scanner freq_file = new Scanner(new File(args[0]));
			FileWriter allowed_words = new FileWriter(args[1]);
		//	FileWriter test_words = new FileWriter("C:/Users/skohail/Desktop/experiment/words_for_test/test_words"+fnum+".txt");
			FileWriter allowed_words_wPOS_wfreq = new FileWriter(args[2]);
			ArrayList<String[]> entry_list = new ArrayList<String[]>();
			
			freq_file.useDelimiter("\n");
			String current;
			
			String frequncy;
			
			while(freq_file.hasNext()){
				String []entry = new String[3];
				current = freq_file.next().trim();
			//System.out.println(current);
				
			//wordnPOS=theWordSplit.split("/");
			//	System.out.println(wordnPOS.length);
			if(current.split(" ").length>=2 ){
			String []wordnPOS=current.split(" ")[1].trim().split("/");	
			frequncy=current.split(" ")[0].trim();
			if(wordnPOS.length>=2){
			entry[0]=wordnPOS[0].trim();
			entry[1]=wordnPOS[1].trim();
			entry[2]=frequncy;
			//System.out.println(entry[0]+" "+entry[1]+" "+entry[2]);
			entry_list.add(entry);
			//System.out.println("k");
			//System.out.println(entry[0]+" "+entry[1]+" "+entry[2]);
			}
			//	System.out.println(entry[0]+" "+entry[1]+" "+entry[2]);
			}
			
			}
			//0 word 1 pos 2 freq
			HashSet<String> used= new HashSet<String>();
			//System.out.println(entry_list.size());
			for (int m=0; m<entry_list.size();m++){
				
				String [] current_entry =entry_list.get(m);
				String fword=current_entry[0].replaceAll("[^a-zA-Z0-9]", "");
				fword=fword.replaceAll("\\-", "");
				if(! fword.isEmpty() && !fword.equals("rrb") && !fword.equals("lrb") && !fword.equals("lsb") && !fword.equals("rsb") && !fword.equals("lsbrsb") && !fword.equals("lrbrrb")){
				
				//System.out.println(current_entry[0]+" "+current_entry[1]+" "+current_entry[2]);
				String maximum= checkPop(entry_list,current_entry[0],Integer.parseInt(current_entry[2]), current_entry[1] );
			//	System.out.println("maximum " +maximum);
				if(checkele(maximum.split("_@_")[0])){
					used.add(current_entry[0]+"\t"+maximum.split("_@_")[0]+"\t"+ maximum.split("_@_")[1]);
				//	test_words.write(current_entry[0]+"\n");
				}
				
			//	used.add(current_entry[0]);	
				}
			//	used.add(current_entry[0]);	
			}
			
			for (String s : used) {

				allowed_words_wPOS_wfreq.write(s+"\n");
				allowed_words.write(s.split("\t")[0]+"\n");
				
				
			}
			freq_file.close();
			allowed_words.close();
			allowed_words_wPOS_wfreq.close();
			//test_words.close();
			
			
		}

	
	public static boolean checkele( String targetValue) {
		String[] arr={"JJ", "NN", "NNS", "RB", "RBR", "RBS", "VB", "VBD", "VBN", "VBG","VBZ","NNP"};
	    for(String s: arr){
	        if(s.equalsIgnoreCase(targetValue))
	            return true;
	    }
	    return false;
	}
	public static String checkPop(ArrayList<String[]> entry_list, String word, int max, String pos){
		
		String mxi=pos;
		
		for (int counter = 0; counter < entry_list.size(); counter++)
		{
			String [] decMax =entry_list.get(counter);
		     if (word.equalsIgnoreCase(decMax[0]) && Integer.valueOf(decMax[2]) >= max)
		     {
		    	// System.out.println("OK book");
		      max = Integer.valueOf(decMax[2]);
		      mxi= decMax[1];
		     }
		} 
return mxi+"_@_"+max;
		    
	}

}

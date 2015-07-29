package Extraction;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AspectExtraction {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File[] listOfFiles = new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/done-collapsed").listFiles();
		for (int x=0;x<listOfFiles.length; x++){
			String fname=listOfFiles[x].getName();
		Scanner dep_file = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/done-collapsed/"+fname));
		fname = fname.replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "");
		fname=fname.trim();
		
		FileWriter freq_table = new FileWriter("C:/Users/skohail/Desktop/freq_tables/freq_table_"+fname+".txt");
		// sent_indx = new Scanner(new File("data/index.txt"));
		dep_file.useDelimiter("\n");
		//sent_indx.useDelimiter("\n");
		String current;
	    Map<String, Integer> map = new HashMap<String, Integer>();
	 
	  
		String myString ;
		String []toSort ;
		 
		//int mark=0;
	
		while (dep_file.hasNext()){
			current=dep_file.next().toLowerCase();
		
			Pattern p = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
			Matcher matcher = p.matcher(current);
			if (matcher.find()){
				
			toSort=matcher.group(1).split(",");
			Arrays.sort(toSort);
			toSort[0]=toSort[0].replaceAll("[\\-\\d]", "").trim();
			toSort[1]=toSort[1].replaceAll("[\\-\\d]", "").trim();
			myString = String.join(",", toSort);

			
		Integer freq = map.get(myString); 
		map.put(myString, (freq == null) ? 1 : freq + 1); 
			}
			
		//}
		//mark++;	
		

		//	freq_table.write(papa camarad+"\n");
		
			}
		MyComparator comparator = new MyComparator(map);

	    Map<String, Integer> newMap = new TreeMap<String, Integer>(comparator);
	    newMap.putAll(map);
	
		for (String name: newMap.keySet()){

            String key =name.toString();
            String value = map.get(name).toString();
            String formatStr = "%-15s %-15s%n";
            freq_table.write(String.format(formatStr, key , value));  


} 
		freq_table.close();
		dep_file.close();

		
}


	}
}
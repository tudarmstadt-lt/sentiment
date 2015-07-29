package forGraphiz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GraphizTablesGraphs {

	public static void main(String[] args) throws IOException {
		String [] aspects={"focus", "images", "shots", "lens", "photos", "pictures", "flash", "battery", "usage", "macro", "autofocus", "range", "speed", "contrast", "optics", "zoom", "colors", "quality", "aperture", "price", "clarity", "resolution" , "memory", "tripod", "light"};
		String line;
		String []pairs;
	//	int ind=0;
		File[] listOfDepFiles = new File("C:/Users/skohail/Desktop/experiment/graph_tables").listFiles();
		String fnum;
		for (int i=0; i<listOfDepFiles.length;i++){
		
			
			fnum=listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
			System.out.println(fnum);
			Scanner table_graph = new Scanner(new File("C:/Users/skohail/Desktop/experiment/graph_tables/filtered_by_pos"+fnum+".txt_table_graph.txt"));
			table_graph.useDelimiter("\n");
			FileWriter graph_structure = new FileWriter("C:/Users/skohail/Desktop/experiment/graph_tables_for_graphiz/graph_tables_for_graphiz"+fnum+".txt");
			
			
			while (table_graph.hasNext()){
				
				line=table_graph.next();
			//	System.out.println("line: "+line);
				pairs = line.split("\t");
		int length=pairs.length;
		//System.out.println("length: "+length);
		if(length>=4){
			
			int freq= Integer.parseInt(pairs[0]);
			String relation=pairs[1].trim();
			String source= pairs[2].replaceAll("[^a-zA-Z0-9 ]", "").trim();
			String dist =pairs[3].replaceAll("[^a-zA-Z0-9 ]", "").trim();
			int slen = source.length();
			int dlen =	dist.length();	
		if( freq>100 && ((!Pattern.matches("\\p{Punct}", source) && !Pattern.matches("\\p{Punct}", dist) && !relation.equals("det")  && !relation.endsWith("root") && !source.equals("rrb")&& !dist.equals("rrb") && !source.equals("lrb")&& !dist.equals("lrb") && !source.equals("this") && !dist.equals("this") && !source.equals("the") && !dist.equals("the") && !source.equals("there") && !dist.equals("there") &&   !source.equals("you") && !dist.equals("you") && slen>2 && dlen>2 && !source.equals("your") && !dist.equals("your")) || (Arrays.asList(aspects).contains(source) || Arrays.asList(aspects).contains(dist) ))){
		if(!isNumeric(source)	&& !isNumeric(dist)){
			
			graph_structure.write("\"" +source+ "\" -> " + "\"" +dist + "\"" + "[label=\""+relation+"-"+freq+"\"]"+";\n");					
		}		
		}
			
		}
				
			}
			table_graph.close();
			graph_structure.close();	
			
				
}
			}
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}

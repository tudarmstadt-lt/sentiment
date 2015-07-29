package filterPOS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterPOS {

	public static void main(String[] args) throws IOException {
		
			Scanner allowed_words = new Scanner(new File(args[0]));
			Scanner collapsed_file = new Scanner(new File(args[1])); 
			FileWriter filtered_by_pos = new FileWriter(args[2]);
			System.out.println("allowd_words_list---- dep file(c-n,c-n)---- output");
			allowed_words.useDelimiter("\n");
			collapsed_file.useDelimiter("\n");
			ArrayList <String>newset = new ArrayList<String>();
			String []toCompare;
			while (allowed_words.hasNext()){
				newset.add(allowed_words.next().trim());
			}
			String current;
			while (collapsed_file.hasNext()){
				Pattern p = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
				current =collapsed_file.next();
				Matcher matcher = p.matcher(current);
			
				if (matcher.find()){
					String match= matcher.group(0);
				
				toCompare=match.split(",");
				if (toCompare[0].trim().split("-").length>=2 && toCompare[1].trim().split("-").length>=2){
				toCompare[0]=toCompare[0].trim().split("-")[0].replaceAll("[\\)\\(\\']", "").trim();
				toCompare[1]=toCompare[1].trim().split("-")[0].replaceAll("[\\)\\(\\']", "").trim();
				//System.out.println(toCompare[0]);
				if(!toCompare[0].equals("rrb") && !toCompare[1].equals("rrb") && !toCompare[0].equals("lrb") && !toCompare[1].equals("lrb") &&!Pattern.matches("\\p{Punct}", toCompare[0]) && !Pattern.matches("\\p{Punct}", toCompare[1]) && !isNumeric(toCompare[0]) && !isNumeric(toCompare[1]) && toCompare[0].length()>1 && toCompare[1].length()>1)
				{
					if(newset.contains(toCompare[0]) && newset.contains(toCompare[1]) ){
			//	m++;
					filtered_by_pos.write(current.replaceAll("[\\']", "")+"\n");
				//result=result+"@_@"+pairs[i];	
				}
				}
		}
				}
			
		}
			filtered_by_pos.close();
			allowed_words.close();
			collapsed_file.close();
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

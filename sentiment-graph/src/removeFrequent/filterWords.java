package removeFrequent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class filterWords {

	public static void main(String[] args) throws IOException {
		System.out.println("stopwordsfile, 2 column file");
		// TODO Auto-generated method stub
		Set<String> frequ = new HashSet <String>(FileUtils.readLines(new File (args[0])));
		String []words ;
		String current;
		String filtered="";
	//	String last="";
	//	String point=".";
		int i;
		String real;
		int c=1;
	//	FileWriter writer = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/filteredReviews/filteredTextManualwithoutDots.txt"); 
		Scanner t = new Scanner(new File(args[1]));
		FileWriter writer = new FileWriter(args[2]); 
	//	FileWriter writer_real = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/allu-realText.txt");
		t.useDelimiter("\\n");
	int ii=0;
		while (t.hasNext()){
	   		 real=t.next();
	   		 current=real.toLowerCase();
	   		 //remove non alphabet/ numbers/ dots and replace it with space
	   		current= current.replaceAll("[^a-zA-Z0-9]", " ");
	   		current=current.replaceAll(" +", " ");
	   		//replace multiple dots to one dot
	   	//	current=current.replaceAll("\\.+", ".");
	   		// attach a space before all dots
	   	//	current=current.trim().replaceAll("\\.", " . ");
	   		// replace multiple spaces with one space
	   	//	current=current.replaceAll(" +", " ");
	   	//	current=current.trim().replaceAll(" . ", ". ");
	   		// split by space to get the words of the current review
	   		words=current.split(" ");
	   		// iterate over words and remove the word if in frequent list
	   	for ( i=0; i<words.length;i++){
	   	//	filtered=filtered+" "+words[i].trim();
	   		
	  		if(!frequ.contains(words[i].trim())){
	  			
	   		//	if(!words[i].trim().equals(point))
	   		//	{
	   			//	System.out.println(last+"/"+ words[i].trim() );
	  			filtered=filtered+" "+words[i].trim();
	  		//	last=words[i].trim();
	   		//	}
	   			 	
	  	//	else if(!last.equals(point)){
	  			
	  				//System.out.println(last+"/"+ words[i].trim() );
	  		//	filtered=filtered+" "+words[i].trim();
	  		//	last=words[i].trim();
	  	//	}
	   			}
	   	}
	   	// write the filtered review to a text file
	  
	   	//	System.out.println(real);
	   	if(filtered.trim().split(" ").length>10){
	   	writer.write(real+"\t"+filtered.trim()+"\n");
	   	System.out.println("written"+ii++);
	   	}
	//   	writer_real.write(real+"\n");
	  
	   		filtered="";	
		   //	c++;
		}
	   		
	   
		writer.close();
	//	writer_real.close();
	   	t.close();
	  /*	Scanner frst = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/filteredReviews/filteredTextManualwithoutDots.txt"));
	   	frst.useDelimiter("\\n");
		Scanner snd = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/filteredReviews/filteredTextManualwithDots.txt"));
		snd.useDelimiter("\\n");
		Scanner trd = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/filteredReviews/nonfilteredTextManualwithDots.txt"));
		trd.useDelimiter("\\n");
		FileWriter writer1 = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/newFiles/newNONfiltered.txt"); 
		//FileWriter writer2 = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/newFiles/filteredTextManualwithDots.txt"); 
		//FileWriter writer3 = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/newFiles/AllSample.txt"); 
		int m=1;
		String f,s,tt;
		String [] arr, arr2 ;
	   	while (frst.hasNext()){
	   		f=frst.next().trim();
	   		arr=f.split(" ");
	   		s=snd.next().trim();
	   		arr2=s.split(" ");
   			tt=trd.next().trim();
	   		 if(!f.isEmpty() && !s.isEmpty() &&  arr.length>1 && arr2.length>1){
	   			writer1.write(tt+"\n");
	   		//	writer2.write(s+"\n");
	   		//	writer3.write(t+"\n");
	   		 }
	   		 m++;
	   		 }
	   		writer1.close();
		//	writer2.close();
		//	writer3.close();*/
		}
	
		
	
}

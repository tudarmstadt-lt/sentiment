package dataMatrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.tartarus.snowball.ext.EnglishStemmer;

//import org.tartarus.snowball.ext.EnglishStemmer;



public class catMatrix {
	

public static void main(String[] args) throws IOException{
	
	File[] listOfFiles = new File("dataR/").listFiles();
	int count1=0,count2=0;
	
for (int x=0;x<listOfFiles.length; x++){
	System.out.println("x: "+x);
	Set<String> myList = new TreeSet <String>();
	if(x==0){
Scanner s = new Scanner(new File("dataR/"+listOfFiles[x].getName()));
s.useDelimiter("\\s+|\\n|\\.|\\s|\\{Punct}");
	
	
	// Set s2 = new HashSet();
	  

	
	while (s.hasNext()){
	   		
		myList.add(stemming(s.next()));
	

	}
	count1=myList.size();
	writetofile("tokens of "+listOfFiles[x].getName(),myList);
	s.close();
	}
	else{
	
		myList = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+"tokens of "+listOfFiles[x].getName())));
		count1=myList.size();
	}
	if (x==0){
	for (int y=x+1; y<listOfFiles.length;y++){
		Set<String> file2 = new TreeSet <String>();
		System.out.println("y: "+y);
		
		Scanner s2 = new Scanner(new File("dataR/"+listOfFiles[y].getName()));
		s2.useDelimiter("\\s+|\\n|\\.|\\s|\\{Punct}");
	
		while (s2.hasNext()){
	   		
			file2.add(stemming(s2.next()));

		}
		s2.close();
		writetofile("tokens of "+listOfFiles[y].getName(),file2);
		count2=file2.size();
//		 Set<String> intersect = new TreeSet(myList);
		
	
		
		   myList.retainAll(file2);
		   System.out.println("Intersection: "+listOfFiles[x].getName()+" "+count1+"/"+listOfFiles[y].getName()+" "+count2+" --> "+myList.size());
		   myList = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+"tokens of "+listOfFiles[x].getName())));
			
	}
	}
	else{
		for (int y=x+1; y<listOfFiles.length;y++){
			Set<String> file2 = new TreeSet <String>();
			System.out.println("y: "+y);	
			file2 = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+"tokens of "+listOfFiles[y].getName())));
			count2=myList.size();
		
			myList.retainAll(file2);
			System.out.println("Intersection: "+listOfFiles[x].getName()+" "+count1+"/"+listOfFiles[y].getName()+" "+count2+" --> "+myList.size());
			myList = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+"tokens of "+listOfFiles[x].getName())));
			
		}
		
	}
	
}


}
protected static void print(String label, Collection<String> c) {

    System.out.println("--------------" + label + "--------------");

    Iterator<String> itr = c.iterator();

    while (itr.hasNext()){
        System.out.println(itr.next());
    }
  }
protected static String stemming(String line){
	EnglishStemmer stemmer = new EnglishStemmer();
	stemmer.setCurrent(line.replaceAll("[^a-zA-Z]", "").toLowerCase());
	stemmer.stem();
	return stemmer.getCurrent();
	
	
}
protected static void writetofile(String label,Set<String> c) throws IOException {
	FileWriter writer = new FileWriter("tokens/"+label); 
	Iterator<String> itr = c.iterator();

    while (itr.hasNext()){
        writer.write(itr.next()+"\n");
    }
    writer.close();
  }

}

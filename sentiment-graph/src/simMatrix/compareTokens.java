package simMatrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public class compareTokens {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 FileWriter writer = new FileWriter("results.txt"); 
		File[] listOfFiles = new File("tokens/").listFiles();
		int count1=0,count2=0;
		
	for (int x=0;x<listOfFiles.length; x++){
		
		System.out.println("x: "+x);
		Set<String> myList = new TreeSet <String>();
		
	 	myList = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+listOfFiles[x].getName())));
		count1=myList.size();
	
		
		for (int y=x+1; y<listOfFiles.length;y++){
			Set<String> file2 = new TreeSet <String>();
			System.out.println("y: "+y);
			file2 =  new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+listOfFiles[y].getName())));
			count2=file2.size();

			   myList.retainAll(file2);
			 System.out.println("Intersection: "+listOfFiles[x].getName()+" "+count1+"/"+listOfFiles[y].getName()+" "+count2+" --> "+myList.size());
			  writer.write(myList.size()+",");
			 myList.clear();
			  myList = new TreeSet<String>(FileUtils.readLines(new File ("tokens/"+listOfFiles[x].getName())));
				
		}
	
		writer.write("\n");
		
	}
	writer.close();


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

package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AmazonReviewToMahout {
public static void main(String[] args) throws IOException{
    //create new buffer reader with argument from command line.	
	File[] listOfFiles = new File("data/").listFiles();
	for (int x=0;x<listOfFiles.length; x++){
	BufferedReader br = new BufferedReader(new FileReader("data/"+listOfFiles[x].getName()));
	//counter i for number of reviews 
	int i=1;
	FileWriter writer = new FileWriter("dataR/reviewsOf "+listOfFiles[x].getName()); 
	//Initialize string iterator line to the first line in document
    String line = br.readLine();
    //start looping through lines in the document 
	while (line != null) {
		// if the line start with "review/text:" string, this indicates a start of a review
				if(line.startsWith("review/text:")){
					//"review/text" will be removed/ replaced by empty string 
					line = line.replaceFirst("review/text:", ""); 
					//and the review only will be written in a new file text
				
					//write a line
					//line.;
					writer.write(line.replaceAll("[^a-zA-Z|\\s+|\\{Punct}|\\n]", " ")+"\n");
					//increase counter by 1
					i++;
					//close file
				
				}
				//read the next line 
				line = br.readLine();
				// just for tracing 
				 if(i%1000==0){
					 System.out.println(i);
				 }
		}
	// print number of reviews
	System.out.println(i);
// close the buffered reader
	br.close();
	writer.close();
	}
	
}
}

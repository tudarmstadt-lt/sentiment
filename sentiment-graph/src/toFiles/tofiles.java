package toFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class tofiles {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner dep_file = new Scanner(new File("data/dep_sequencial_edited.txt"));
		Scanner sent_indx = new Scanner(new File("data/index.txt"));
		String current;
		int indx=0;
		int track=0;
		dep_file.useDelimiter("\n");
		sent_indx.useDelimiter("\n");
		FileWriter freq_table = new FileWriter("data/freq_tableT"+track+".txt"); 
		while (dep_file.hasNext() && sent_indx.hasNext()){
			current=dep_file.next();
			indx=Integer.valueOf(sent_indx.next());
			
			if (indx<= 70 )
			{
				
				freq_table.write(current+"\n");
				
			}
			else{
				System.out.println(indx);
				track++;
				 freq_table = new FileWriter("data/freq_tableT"+track+".txt"); 
				freq_table.write(current+"\n");
			}
			
			
		}
		
	}

}

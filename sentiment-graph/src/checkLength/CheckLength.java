package checkLength;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.mahout.math.Arrays;

import edu.stanford.nlp.util.StringUtils;

public class CheckLength {

	public static void main(String[] args) throws IOException {
		int topics_num=200;
		String line;
		String []pairs;
		String [] words;
		//String indx;
		String txt;
		for (int i=0;i<topics_num;i++){
			
			Scanner segments_file = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/segments/"+i+".txtseggmented.txt"));
			FileWriter only_sentences = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/segments/only_sentences_less_80_words/only_sen"+i+".txt");
			FileWriter only_indx = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/segments/only_sentences_less_80_words/only_indx"+i+".txt");
			segments_file.useDelimiter("\n");
			while (segments_file.hasNext()){
				line = segments_file.next();
				pairs=line.split("\t");
				words=pairs[1].split("\\s+");
				txt=pairs[1].replaceAll("[\\s+\\{Punct}\\.\\_\\:]", "").trim();
				String joinedString = StringUtils.join(words, " ");
				//System.out.println(joinedString);
				//words=line.split("\\s");
				if(pairs.length==2&& words.length>80 && txt != null && !txt.isEmpty()){
					only_sentences.write(joinedString.trim().subSequence(0, 80)+"\n");
					only_indx.write(pairs[0]+"\n");
					//	System.out.println(i);
				}
				else if(pairs.length==2&& words.length>2 && words.length<=80 && txt != null && !txt.isEmpty()){
					only_sentences.write(joinedString.trim()+"\n");
					only_indx.write(pairs[0]+"\n");
				}
				
			}
			segments_file.close();	
			only_sentences.close();
			only_indx.close();
		}
		
	}

}

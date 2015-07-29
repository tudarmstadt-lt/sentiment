package selectReleated;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.math3.util.Precision;

public class MostRelated {

	public static void main(String[] args) throws IOException {
		// Select most related reviews for each topic
		
		String []topics ;
		
		Scanner tassign = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/c1.txt"));
		Scanner  filteredText = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/c2.txt"));
		Scanner realText = new Scanner(new File("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/c3.txt"));
		
		FileWriter A60_tassign = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/A60_tassign.txt");
		FileWriter A60_filteredText = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/A60_filteredText.txt");
		FileWriter A60_realText = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/A60_realText.txt");
		FileWriter A60_all = new FileWriter("C:/Users/skohail/Desktop/All Experiments files/LDA original data results/A60_real_fil_tas_scores.txt");
		tassign.useDelimiter("\\n");
		filteredText.useDelimiter("\\n");
		realText.useDelimiter("\\n");
		
		double score;
		double popular;
		double count;
		String []return_v;
		String tassign_line;
		String realText_line;
		String filteredText_line;
		
		while (tassign.hasNext()){
			tassign_line = tassign.next();
			filteredText_line = filteredText.next();
			realText_line = realText.next();
			topics = tassign_line.split("\\s");
			return_v=getPopularElement(topics).split(",");
			popular = Integer.parseInt(return_v[0]);
			count = Integer.parseInt(return_v[1]);
			score=(count/(topics.length)) * 100;
			//System.out.println(popular+ " "+ count+ " "+score);
			if (score>=60){
				A60_tassign.write(tassign_line+"\t"+ Precision.round(score, 2) +"\t"+topics.length+"\t"+return_v[0] +"\t"+return_v[1]+"\n");
				A60_filteredText.write(filteredText_line+"\n");
				A60_realText.write(realText_line+"\n");
				A60_all.write(filteredText_line+"\t"+realText_line+"\t"+tassign_line+"\t"+ Precision.round(score, 2) +"\t"+topics.length+"\t"+return_v[0] +"\t"+return_v[1]+"\n");
			}
		}
		tassign.close();
		filteredText.close();
		realText.close();
		A60_tassign.close();
		A60_all.close();
		A60_filteredText.close();
		A60_realText.close();
	}
	public static String getPopularElement(String[] a)
	{
	  int count = 1, tempCount;
	  int popular = Integer.parseInt(a[0]);
	  int temp = 0;
	  for (int i = 0; i < (a.length - 1); i++)
	  {
	    temp = Integer.parseInt(a[i]);
	    tempCount = 0;
	    for (int j = 0;j < a.length; j++)
	    {
	      if (temp == Integer.parseInt(a[j]))
	        tempCount++;
	    }
	    if (tempCount > count)
	    {
	      popular = temp;
	      count = tempCount;
	    }
	  }
	  return popular+","+count;
	}

}

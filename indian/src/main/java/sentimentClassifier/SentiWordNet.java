package sentimentClassifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 19-07-2015.
 */
public class SentiWordNet {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    int featureCount;

    SentiWordNet(String rootDirectory)throws IOException{
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        //testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        LinkedHashMap<String, Integer> wordListPos = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> wordListNeg = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> wordListNeu = new LinkedHashMap<String, Integer>();

        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\sentimentWordsIL.txt"), "UTF-8"));

        String line;
        int count =1;
        while((line = read.readLine()) != null)
        {
            line = line.trim();
            String tokens[] = line.split("\\|");
            int val = Integer.parseInt(tokens[1]);
            if(val == 1)
            {
                if(wordListPos.containsKey(tokens[0]))
                {
                    System.out.println("Pos found again: "+count);
                }
                else if(wordListNeg.containsKey(tokens[0]))
                {
                    System.out.println("Pos and Neg: "+count);
                }

                if (wordListNeu.containsKey(tokens[0]))
                {
                    System.out.println("Pos and Neu: "+count);
                }

                wordListPos.put(tokens[0],1);
            }
            else if(val == -1)
            {
                if(wordListNeg.containsKey(tokens[0]))
                {
                    System.out.println("Neg found again: "+count);
                }
                else if(wordListPos.containsKey(tokens[0]))
                {
                    System.out.println("Neg and Pos: "+count);
                }

                if (wordListNeu.containsKey(tokens[0]))
                {
                    System.out.println("Neg and Neu: "+count);
                }

                wordListNeg.put(tokens[0],-1);
            }
            else if(val == 0)
            {
                if(wordListNeu.containsKey(tokens[0]))
                {
                    System.out.println("Neu found again: "+count);
                }
                else if(wordListPos.containsKey(tokens[0]))
                {
                    System.out.println("Neu and Pos: "+count);
                }

                if (wordListNeg.containsKey(tokens[0]))
                {
                    System.out.println("Neg and Neu: "+count);
                }

                wordListNeu.put(tokens[0],0);
            }
            count++;
        }
    }
    public static void main(String[] args)throws IOException
    {
        SentiWordNet ob = new SentiWordNet("D:\\Course\\Semester VII\\Internship\\IndianSentiment");
    }
}

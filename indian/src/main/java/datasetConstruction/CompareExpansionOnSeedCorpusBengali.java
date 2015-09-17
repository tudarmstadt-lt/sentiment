package datasetConstruction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by krayush on 29-07-2015.
 */
public class CompareExpansionOnSeedCorpusBengali {
    public static void main(String[] args)throws IOException {
        String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";

        BufferedReader readLexicon = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\DT_COOCBengaliLexicon.txt"), "UTF-8"));
        BufferedReader remainingWords = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\RemainingWordsBengali.txt"), "UTF-8"));
        //BufferedReader remainingWords = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\OriginalSeedCorpusFromHSWN.txt"), "UTF-8"));


        HashMap<String, Integer> expanded = new HashMap<String, Integer>();
        //HashMap<String, Integer> seed = new HashMap<String, Integer>();

        String line;

        while((line = readLexicon.readLine()) != null)
        {
            String token[] = line.split("\\|");
            expanded.put(token[0], Integer.parseInt(token[3]));
        }

        double numPos=0, matchPos=0;
        double numNeg=0, matchNeg=0;
        double numNeu=0, matchNeu=0;

        while((line = remainingWords.readLine()) != null)
        {
            String token[] = line.split("\\|");
            //seed.put(token[0], Integer.parseInt(token[1]));
            if(expanded.containsKey(token[0]))
            {
                int val = Integer.parseInt(token[1]);
                int score = expanded.get(token[0]);

                if(val == 1)
                {
                    numPos++;
                    if(score == val)
                    {
                        matchPos++;
                    }
                }
                else if(val == -1)
                {
                    numNeg++;
                    if(score == val)
                    {
                        matchNeg++;
                    }
                }
                else if(val == 0)
                {
                    numNeu++;
                    if(score == val)
                    {
                        matchNeu++;
                    }
                }
            }
        }

        System.out.println("Total found: " + (numPos+numNeg+numNeu) + ", Match: " + (matchPos+matchNeg+matchNeu));
        System.out.println("Total pos found: " + numPos + ", Match: "+matchPos);
        System.out.println("Total neg found: " + numNeg + ", Match: "+matchNeg);
        System.out.println("Total neu found: " + numNeu + ", Match: "+matchNeu);
    }
}

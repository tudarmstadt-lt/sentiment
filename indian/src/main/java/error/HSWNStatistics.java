package error;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by krayush on 23-08-2015.
 */

class checkHSWNErrorPOS
{
    HashMap<String, Integer> originalLabel = new HashMap<String, Integer>();
    checkHSWNErrorPOS(String rootDirectory)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\resources\\sentimentSeedWordsHindi.txt"), "UTF-8"));
        String line;

        while((line = reader.readLine()) != null)
        {
            //System.out.println(line);
            String tokens[] = line.split("\\|");
            //System.out.println(tokens);
            //System.out.println(tokens[3]);
            originalLabel.put(tokens[0], Integer.parseInt(tokens[1]));
        }

        generateError(rootDirectory + "\\dataset\\POS_Hindi_Train.txt");
        generateError(rootDirectory + "\\dataset\\POS_Hindi_Test.txt");
    }

    void generateError(String dataFile)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
        String line;

        int total = 0, uniqueMatching=0;
        int tweets=0, noMatch = 0;
        HashSet<String>found = new HashSet<String>();
        HashSet<String>totalFound = new HashSet<String>();

        while((line = reader.readLine()) != null)
        {
            String tokens[] = line.split(" ");
            //int entryCount = uniqueMatching;
            int adjFlag=0, matchFlag=0;
            for(int i=0; i<tokens.length; i++)
            {
                String pos[] = tokens[i].split("_");
                if(pos[1].compareToIgnoreCase("JJ") == 0)
                {
                    adjFlag=1;                                 //to identify if tweets contain atleast 1 adj.
                    if(!totalFound.contains(pos[0]))        //to identify unique adjectives
                    {
                        total++;
                        totalFound.add(pos[0]);
                    }

                    if(originalLabel.containsKey(pos[0]))
                    {
                        matchFlag=1;
                        if(!found.contains(pos[0]))     //to identify unique matching adjectives
                        {
                            uniqueMatching++;
                            found.add(pos[0]);
                        }
                    }
                }
            }

            if(adjFlag == 1)
            {
                tweets++;
                if(matchFlag != 1)
                {
                    noMatch++;
                }
            }
            /*if(flag == 1)
            {
                tweets++;
                if(entryCount == count)
                {
                    noMatch++;
                }
            }*/
        }

        System.out.println("Total Adjective: " +total+" Adjectives Matching with Lexicon: "+uniqueMatching+" Tweets having atleast one adj. "+ tweets+ " Tweets without any match: "+noMatch);
    }
}
public class HSWNStatistics {
    public static void main(String[] args)throws IOException
    {
        checkHSWNErrorPOS ob = new checkHSWNErrorPOS(System.getProperty("user.dir"));
    }
}

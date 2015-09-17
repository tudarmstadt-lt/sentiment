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

class checkHindiErrorPOS
{
    HashSet<String> trainNgram = new HashSet<String>();
    checkHindiErrorPOS(String rootDirectory)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\dataset\\tokenized_Hindi_Train.txt"), "UTF-8"));
        BufferedReader readerLabel = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\dataset\\trainingLabels.txt")));

        String line;

        while((line = reader.readLine()) != null)
        {
            //System.out.println(line);
            String label = readerLabel.readLine();
            if(label.compareToIgnoreCase("0") == 0)
            {
                String tokens[] = line.split(" ");
                for(int i=0; i<tokens.length; i++)
                {
                    if(!trainNgram.contains(tokens[i]))
                    {
                        trainNgram.add(tokens[i]);
                    }
                }
            }
            //System.out.println(tokens);
            //System.out.println(tokens[3]);
        }

        //generateError(rootDirectory + "\\dataset\\tokenized_Hindi_Train.txt");
        generateError(rootDirectory, rootDirectory + "\\dataset\\tokenized_Hindi_Test.txt");
    }

    void generateError(String rootDirectory, String dataFile)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
        BufferedReader readerLabel = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\dataset\\Gold Set\\HI_Test_Gold.txt")));
        String line;
        HashMap<String, Integer>notFound = new HashMap<String, Integer>();
        int uniqueCount=0;
        int noMatch = 0;
        //HashSet<String>found = new HashSet<String>();
        //HashSet<String>totalFound = new HashSet<String>();
        HashSet<String> testNgrams= new HashSet<String>();

        int totalToken=0, totalNotFound=0;
        while((line = reader.readLine()) != null) {
            String label = readerLabel.readLine().split("\\t")[1];
            if(label.compareToIgnoreCase("positive")==0) {


                String tokens[] = line.split(" ");
                int entryCount = uniqueCount;
                int flag = 0;
                totalToken+=tokens.length;
                for (int i = 0; i < tokens.length; i++) {
                    if (!testNgrams.contains(tokens[i])) {
                        testNgrams.add(tokens[i]);
                    }

                    if (!trainNgram.contains(tokens[i])) {
                        totalNotFound++;
                        if (!notFound.containsKey(tokens[i])) {
                            uniqueCount++;
                            notFound.put(tokens[i], 1);
                        }
                    } else {
                        flag = 1;
                    }

                /*else
                {
                    flag=1;
                }*/
                }


                if (flag != 1) {
                    noMatch++;
                }
            }

        }

        //System.out.println(totalToken+", "+totalNotFound);
        System.out.println("Train Ngram Size: "+trainNgram.size()+", Test Ngrams Size: "+testNgrams.size()+", Not Found in Train NGrams: "+uniqueCount+", Total tweets having absolutely no match "+noMatch);
    }
}
public class HindiNgramStatistics {
    public static void main(String[] args)throws IOException
    {
        checkHindiErrorPOS ob = new checkHindiErrorPOS(System.getProperty("user.dir"));
    }
}

package error;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by krayush on 22-08-2015.
 */

class checkError
{
    HashMap<String, Integer> originalLabel = new HashMap<String, Integer>();
    checkError(String rootDirectory)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\resources\\DT_COOCHindiLexicon.txt"), "UTF-8"));
        String line;

        while((line = reader.readLine()) != null)
        {
            //System.out.println(line);
            String tokens[] = line.split("\\|");
            //System.out.println(tokens);
            //System.out.println(tokens[3]);
            originalLabel.put(tokens[0], Integer.parseInt(tokens[3]));
        }

        generateError(rootDirectory + "\\dataset\\tokenized_Hindi_Train.txt", rootDirectory + "\\dataset\\trainingLabels.txt", rootDirectory + "\\dataset\\errorHindiTraining.txt");
        generateError(rootDirectory + "\\dataset\\tokenized_Hindi_Test.txt", rootDirectory + "\\dataset\\testLabels.txt", rootDirectory + "\\dataset\\errorHindiTest.txt");
    }

    void generateError(String dataFile, String labelFile, String errorFile)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
        BufferedReader readerLabel = new BufferedReader(new InputStreamReader(new FileInputStream(labelFile)));
        HashMap<String, Integer> pos = new HashMap<String, Integer>();
        HashMap<String, Integer> neg = new HashMap<String, Integer>();
        HashMap<String, Integer> neu = new HashMap<String, Integer>();
        String line;
        while((line = reader.readLine())!= null)
        {
            String labelL = readerLabel.readLine();
            String tokens[] = line.split(" ");
            for(int i=0; i<tokens.length; i++)
            {
                if(originalLabel.containsKey(tokens[i]))
                {
                    //System.out.println(labelL);
                    Integer label = Integer.parseInt(labelL);
                    if(pos.containsKey(tokens[i]))
                    {
                        if(label == 1)
                        {
                            pos.put(tokens[i], pos.get(tokens[i])+1);
                        }
                        else if(label == -1)
                        {
                            neg.put(tokens[i], neg.get(tokens[i])+1);
                        }
                        else if(label == 0)
                        {
                            neu.put(tokens[i], neu.get(tokens[i])+1);
                        }
                    }
                    else
                    {
                        if(label == 1)
                        {
                            pos.put(tokens[i], 1);
                            neg.put(tokens[i], 0);
                            neu.put(tokens[i], 0);
                        }
                        else if(label == -1)
                        {
                            pos.put(tokens[i], 0);
                            neg.put(tokens[i], 1);
                            neu.put(tokens[i], 0);
                        }
                        else if(label == 0)
                        {
                            pos.put(tokens[i], 0);
                            neg.put(tokens[i], 0);
                            neu.put(tokens[i], 1);
                        }
                    }
                }
            }
        }

        /*System.out.println(pos);
        System.out.println();
        System.out.println(neg);
        System.out.println();
        System.out.println(neu);*/

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(errorFile, true), "UTF-8");
        BufferedWriter fbw = new BufferedWriter(writer);
        Set set = pos.entrySet();
        Iterator iterator = set.iterator();
        //featureVector.get(i).clear();
        fbw.write("key|pos|neg|neu|original\n");
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            String key = mentry.getKey().toString();
        fbw.write(key + "|" + mentry.getValue() + "|" + neg.get(key) + "|" + neu.get(key) + "|" + originalLabel.get(key)+"\n");
        }
        //fbw.newLine();
        fbw.close();
    }
}


public class PosNegNeuCountofEachWordinHindiLexicon {
    public static void main(String[] args)throws IOException
    {
        checkError ob = new checkError(System.getProperty("user.dir"));
    }
}

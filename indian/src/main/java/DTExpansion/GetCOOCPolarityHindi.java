package DTExpansion;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.*;
import java.util.*;

/**
 * Created by krayush on 08-07-2015.
 */
public class GetCOOCPolarityHindi {
    public static void main(String[] args)throws IOException
    {
        //Indexing all words in the COOC word list
        String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";

        File fR = new File(rootDirectory+"\\resources\\DTExpansion\\IndianSentCooc\\hindi_2Mu.tok.words");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));
        String line;
        LinkedHashMap<String, Integer>index = new LinkedHashMap<String, Integer>();
        //LinkedHashMap<Integer, String>reverseIndex = new LinkedHashMap<Integer, String>();

        int count=1;
        while((line = bf.readLine()) != null)
        {
            String tokens[] = line.split("\\t");
            //System.out.println(line);
            //System.out.println(tokens[0]);
            if(count>1)
            {
                if(Integer.parseInt(tokens[0]) > 100)
                {
                    index.put(tokens[1], Integer.parseInt(tokens[0]));
                    //reverseIndex.put(Integer.parseInt(tokens[0]), tokens[1]);
                    //System.out.println(tokens.length);
                }
            }

            count++;
        }

        //System.out.println(reverseIndex.size());

        //Assigning the respective id to the DT word list
        File fR1 = new File(rootDirectory+"\\resources\\DTExpansion\\HTTPResults\\sortedDT.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf1 = new BufferedReader(new InputStreamReader(new FileInputStream(fR1), "UTF-8"));
        LinkedHashMap<Integer, String>DTwords = new LinkedHashMap<Integer, String>();

        count=1;
        while((line = bf1.readLine()) != null)
        {
            String tokens[] = line.split("\\|");
            if(index.containsKey(tokens[0]))
            {
                DTwords.put(index.get(tokens[0]), tokens[0]);
            }
            else
            {
                System.out.println("Not found: "+count);
            }
            count++;
        }

        //Assigning the respective id to Root Word List
        File wordFile = new File(rootDirectory+"\\resources\\sentimentSeedWordsHindi.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader wordReader = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile), "UTF-8"));
        LinkedHashMap<Integer, String> rootList = new LinkedHashMap<Integer, String>();
        LinkedHashMap<Integer, Double> rootPolarityList = new LinkedHashMap<Integer, Double>();

        while((line = wordReader.readLine()) != null)
        {
            String tokens[] = line.split("\\|");
            if(index.containsKey(tokens[0]))
            {
                rootList.put(index.get(tokens[0]), tokens[0]);
                rootPolarityList.put(index.get(tokens[0]), Double.parseDouble(tokens[1]));
            }
        }

        //System.out.println(rootList.size());

        //System.out.println(DTwords.size());


        //Creating the linked list of all co-occuring words which are there in the DT list
        File fR2 = new File(rootDirectory+"\\resources\\DTExpansion\\IndianSentCooc\\hindi_2Mu.tok.co_s");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf2 = new BufferedReader(new InputStreamReader(new FileInputStream(fR2)));
        ListMultimap<Integer, Integer> multimap = ArrayListMultimap.create();

        count=1;
        while((line = bf2.readLine()) != null)
        {
            String tokens[] = line.split("\\t");
            int freq = Integer.parseInt(tokens[2]);

            //if(freq >= 3)
            {
                int val1 = Integer.parseInt(tokens[0]);
                int val2 = Integer.parseInt(tokens[1]);

                if(DTwords.containsKey(val1) && rootList.containsKey(val2))
                {
                    multimap.put(val1, val2);
                }
            }
            //System.out.println(count);
            count++;
        }

        Iterator modifiedIterator = DTwords.entrySet().iterator();
        LinkedHashMap<String, Double>COOCPolarity = new LinkedHashMap<String, Double>();
        //int count=1;

        //LinkedHashMap<String, Double> polarityValues = new LinkedHashMap<>();

        double max=-100000,min=100000,keyMax=-1, keyMin=-1;
        while(modifiedIterator.hasNext()) {
            Map.Entry me = (Map.Entry) modifiedIterator.next();
            Integer key = Integer.parseInt(me.getKey().toString());
            String val = me.getValue().toString();

            double polarity=0.0;
            List<Integer> match= multimap.get(key);
            for(int i=0; i<match.size(); i++)
            {
                polarity += rootPolarityList.get(match.get(i));
            }

            if(polarity >= max)
            {
                max = polarity;
                keyMax = key;
            }

            if(polarity <= min)
            {
                min = polarity;
                keyMin = key;
            }

            if(multimap.get(key).size() == 0)
            {
                polarity=0;
            }
            else
            {
                polarity = polarity/multimap.get(key).size();
            }
            COOCPolarity.put(DTwords.get(key), polarity);
            //System.out.println(key+":"+multimap.get(key).size()+" "+polarity);
            //System.out.println("Written: "+count);
            //Double polarity= wordPolarity.get(key)/wordCount.get(key);


        }
        COOCPolarity = sortHashMapByValuesD(COOCPolarity);

        Writer polWriter = new OutputStreamWriter(
                new FileOutputStream(rootDirectory+"\\resources\\DTExpansion\\IndianSentCooc\\polarity.txt"), "UTF-8");
        BufferedWriter pfout = new BufferedWriter(polWriter);

        Iterator polarityIterator = COOCPolarity.entrySet().iterator();
        //count=1;

        while(polarityIterator.hasNext()) {
            Map.Entry me = (Map.Entry) polarityIterator.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());

            pfout.write(key+"|"+val+"\n");
        }
        pfout.close();
        //System.out.println(keyMax+": "+max+"  :  "+keyMin+": "+min );


    }
    private static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, val);
                    break;
                }

            }

        }
        return sortedMap;
    }
}

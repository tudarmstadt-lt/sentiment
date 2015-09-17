package DTExpansion;

import java.io.*;
import java.util.*;

/**
 * Created by krayush on 08-07-2015.
 */
public class GetDTTopWordsPolarityHindiTwitter {
    public static void main(String[] args)throws IOException
    {
        String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";

        LinkedHashMap<String, Double> modifiedFrequency = new LinkedHashMap();
        LinkedHashMap<String, Double> wordCount = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> wordPolarity = new LinkedHashMap<String, Double>();

        File fR = new File(rootDirectory+"\\resources\\TwitterDT\\DTRequest\\valuesDT.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));
        String line;
        while((line = bf.readLine()) != null) {
            String tokens[] = line.split("\\|");
            //System.out.println(line);
            modifiedFrequency.put(tokens[0], Double.parseDouble(tokens[1]));
            wordCount.put(tokens[0], Double.parseDouble(tokens[2]));
            wordPolarity.put(tokens[0], Double.parseDouble(tokens[3]));
        }


        modifiedFrequency = sortHashMapByValuesD(modifiedFrequency);
        Iterator modifiedIterator = modifiedFrequency.entrySet().iterator();
        int count=1;

        LinkedHashMap<String, Double> polarityValues = new LinkedHashMap<String, Double>();
        Writer polWriter1 = new OutputStreamWriter(
                new FileOutputStream(rootDirectory+"\\resources\\TwitterDT\\DTRequest\\sortedDT.txt"), "UTF-8");
        BufferedWriter pfout1 = new BufferedWriter(polWriter1);
        while(modifiedIterator.hasNext()) {
            Map.Entry me = (Map.Entry) modifiedIterator.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());

            System.out.println("Written: "+count);
            Double polarity= wordPolarity.get(key)/wordCount.get(key);

            if(count==501)
            {
                System.out.println(val);
                //break;
            }

            if(count>500)
            {
                polarityValues.put(key, polarity);
                pfout1.write(key+"|"+polarity+"\n");

            }
            count++;
        }

        pfout1.close();


        polarityValues = sortHashMapByValuesD(polarityValues);
        Writer polWriter = new OutputStreamWriter(
                new FileOutputStream(rootDirectory+"\\resources\\TwitterDT\\DTRequest\\polarityValuesAll.txt"), "UTF-8");
        BufferedWriter pfout = new BufferedWriter(polWriter);

        Iterator polarityIterator = polarityValues.entrySet().iterator();
        //count=1;

        while(polarityIterator.hasNext()) {
            Map.Entry me = (Map.Entry) polarityIterator.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());

            pfout.write(key+"|"+val+"\n");
        }
        pfout.close();


        //System.out.println(wordCount);

        //wordCount = sortHashMapByValuesD(wordCount);


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

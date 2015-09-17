package DTExpansionBengali;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by krayush on 23-07-2015.
 */
public class GenerateBengaliDTSeedWord {

    private static LinkedHashMap<String, Double> getSentiList(String fileName, Double val)throws IOException
    {
        LinkedHashMap<String, Double> list = new LinkedHashMap<String, Double>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        String line;
        while((line = reader.readLine()) != null)
        {
            line = line.trim();
            //sentiWords.put(line.split("\\t")[1], 1.0);
            list.put(line.split("\\t")[1], val);
        }
        System.out.println(list.size());
        reader.close();
        return list;
    }

    /*private static int writeToFile(String rootDirectory, LinkedHashMap<String, Double>list)throws IOException
    {

        int num=1;
        for (Map.Entry<String, Double> entry : list.entrySet()) {
            String key = entry.getKey();
            //cfout.write(key+"\n");
            //Double value = entry.getValue();
            System.out.println("# "+num);
            File fR = new File(rootDirectory+"\\resources\\TwitterDT\\twitterBengaliDT.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));
            String line;

            Writer countWriter = new OutputStreamWriter(
                    new FileOutputStream(rootDirectory+"\\resources\\TwitterDT\\"+num+".txt"), "UTF-8");
            BufferedWriter cfout = new BufferedWriter(countWriter);

            while((line = bf.readLine()) != null)
            {
                String tokens[] = line.split("\\t");
                String matchingLine = line;
                if(tokens[0].compareToIgnoreCase(key) == 0)
                {
                    int count = 1;
                    do {
                        cfout.write(matchingLine+"\n");
                        matchingLine = bf.readLine();
                        count++;
                    }while(matchingLine != null && matchingLine.split("\\t")[0].compareToIgnoreCase(key) == 0 && count <= 100);
                    cfout.close();
                    break;
                }
            }
            num++;
        }
        return num;
    }*/

    private static LinkedHashMap<String, Double>genFinalList(String rootDirectory, LinkedHashMap<String, Double>pos, LinkedHashMap<String, Double>neg, LinkedHashMap<String, Double>neu)throws IOException
    {
        Writer countWriter = new OutputStreamWriter(new FileOutputStream(rootDirectory+"\\resources\\sentimentSeedWordsBengali.txt"), "UTF-8");
        BufferedWriter cfout = new BufferedWriter(countWriter);
        LinkedHashMap<String, Double>finalList = new LinkedHashMap<String, Double>();

        for (Map.Entry<String, Double> entry : pos.entrySet()) {
            String key = entry.getKey();
            //cfout.write("sdvsdvsdvdvdv");
            if(!(key.contains("_")))
            {
                if(!neg.containsKey(key) && !neu.containsKey(key))
                {
                    cfout.write(key+"|1"+"\n");
                    finalList.put(key, 1.0);
                }
            }
        }

        for (Map.Entry<String, Double> entry : neg.entrySet()) {
            String key = entry.getKey();
            if(!(key.contains("_")))
            {
                if(!pos.containsKey(key) && !neu.containsKey(key))
                {
                    cfout.write(key+"|-1"+"\n");
                    finalList.put(key, -1.0);
                }
            }
        }

        for (Map.Entry<String, Double> entry : neu.entrySet()) {
            String key = entry.getKey();
            if(!(key.contains("_")))
            {
                if(!neg.containsKey(key) && !pos.containsKey(key))
                {
                    cfout.write(key+"|0"+"\n");
                    finalList.put(key, 0.0);
                }
            }
        }

        cfout.close();
        return finalList;

    }
    private static void generateSeedWords(String rootDirectory)throws IOException
    {
        //LinkedHashMap<String, Double> sentiWords = new LinkedHashMap<String, Double>();

        LinkedHashMap<String, Double> pos = getSentiList(rootDirectory+"\\resources\\Bengali_SentiWordNet\\BN_POS.txt", 1.0);
        LinkedHashMap<String, Double> neg = getSentiList(rootDirectory+"\\resources\\Bengali_SentiWordNet\\BN_NEG.txt", -1.0);
        LinkedHashMap<String, Double> neu = getSentiList(rootDirectory+"\\resources\\Bengali_SentiWordNet\\BN_NEU.txt", 0.0);

        LinkedHashMap<String, Double> finalList = genFinalList(rootDirectory, pos, neg, neu);

        System.out.println(finalList.size());
        String line;


        int end = 0;

        //end = writeToFile(rootDirectory, finalList);

        //end = writeToFile(rootDirectory, pos);
        //end += writeToFile(rootDirectory+"\\resources\\TwitterDT\\twitterBengaliDT.txt", neg);
        //end += writeToFile(rootDirectory+"\\resources\\TwitterDT\\twitterBengaliDT.txt", neu);

        //System.out.println(pos.size()+neg.size()+neu.size());
        //System.out.println(pos.size());
        //System.out.println(neg.size());

        //HashSet

    }

    public static void main(String[] args) throws IOException {
        String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        generateSeedWords(rootDirectory);
    }
}

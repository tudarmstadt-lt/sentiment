package DTExpansion;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by krayush on 29-06-2015.
 */
public class GetTopWords {
    public static void main(String[] args)throws IOException {
        int num = 1;
        //BufferedWriter wr = new BufferedWriter(fW);
        Writer writer = new OutputStreamWriter(
                new FileOutputStream("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\HTTPResults\\topModifiedWords.txt"), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        LinkedHashMap<String, Double> wordCount = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> wordPolarity = new LinkedHashMap<String, Double>();

        File wordFile = new File("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentWordsIL.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader wordReader = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile), "UTF-8"));

        LinkedHashMap<String, Double> polarityRoot = new LinkedHashMap<String, Double>();
        LinkedHashMap<Integer, String> wordIndex = new LinkedHashMap<Integer, String>();
        String line = null;
        int index = 1;
        while ((line = wordReader.readLine()) != null) {
            String values[] = line.split("\\|");
            polarityRoot.put(values[0], Double.parseDouble(values[1]));
            wordIndex.put(index, values[0]);
            index++;
        }

        while (num < 5022) {
            System.out.println("Reading file number: " + num);
            File fR = new File("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\HTTPResults\\" + num + ".txt");
            //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));


            int lineCount = 1;
            String root = wordIndex.get(num);
            Double val = 0.0;
            val = polarityRoot.get(wordIndex.get(num));
            //System.out.println(wordIndex.get(num));
            //System.out.println(val);
            while ((line = bf.readLine()) != null) {
                line = line.trim();


                //Double val = polarityRoot.get(root);
                //System.out.println(val);

                if (lineCount > 5) {
                    if (lineCount < 75) {
                        String[] tokens = line.split("\\t");
                        if (wordCount.containsKey(tokens[0])) {
                            wordCount.put(tokens[0], wordCount.get(tokens[0]) + 1);
                        } else {
                            wordCount.put(tokens[0], 1.0);
                        }

                        if (wordPolarity.containsKey(tokens[0])) {
                            //System.out.println(wordPolarity.get(tokens[0]));
                            wordPolarity.put(tokens[0], wordPolarity.get(tokens[0]) + val);
                        } else if (!wordIndex.containsKey(tokens[0])) {
                            //System.out.println(polarityRoot.get(root));
                            //System.out.println(val);
                            wordPolarity.put(tokens[0], val);
                        }
                    }
                }
                lineCount++;
                //fout.write(line);
                //System.out.println(line);
            }
            if (lineCount <= 5) {
                System.out.println("No results");
            }
            num++;
        }

        LinkedHashMap<String, Double> modifiedFrequency = new LinkedHashMap<String, Double>();
        Writer countWriter = new OutputStreamWriter(
                new FileOutputStream("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\HTTPResults\\valuesDT.txt"), "UTF-8");
        BufferedWriter cfout = new BufferedWriter(countWriter);

        Iterator iPos = wordCount.entrySet().iterator();
        // Display elements
        //Double min=Double.parseDouble(((Map.Entry) iPos.next()).getValue().toString());
        //Double max=0.0;
        int count = 1;
        while (iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            System.out.println("Reading count: " + count);
            count++;
            String url = URLEncoder.encode(key, "UTF-8");
            URL oracle = new URL("http://maggie.lt.informatik.tu-darmstadt.de:10080/jobim/ws/api/hindiBigram/jo/count/" + url + "?format=tsv");
            URLConnection yc = oracle.openConnection();
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
                String inputLine, prev = "";
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    //fout.write(inputLine+"\n");
                    prev = inputLine;
                }

                double corpusCount = Double.parseDouble(prev);
                modifiedFrequency.put(key, (val / corpusCount) * 10000.00);
                cfout.write(key + "|" + modifiedFrequency.get(key)+"|"+wordCount.get(key)+"|"+wordPolarity.get(key)+"\n");
                //System.out.println(corpusCount);

                in.close();
            }catch(IOException e)
            {
                System.out.println(e);
            }





            //fout.write(key+": "+val+"\n");
        }
        cfout.close();
    }
}

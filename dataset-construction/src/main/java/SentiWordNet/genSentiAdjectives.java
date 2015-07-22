import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by krayush on 12-06-2015.
 */
public class genSentiAdjectives {
    public static void main(String[] args)throws IOException
    {
        final String root = "D:\\Course\\Semester VII\\Internship\\datasetConstruction";

        FileReader fR = new FileReader(root+"\\resources\\adjectives.txt");
        PrintWriter writer = new PrintWriter(root+"\\resources\\adjectivesScore.txt");
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;

        LinkedHashMap<String, Double> posScore = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> negScore = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Integer> count = new LinkedHashMap<String, Integer>();

        while((line = bf.readLine()) != null)
        {
            line = line.trim();
            String words[] = line.split("\\t");

            //System.out.println(words.length);
            String adjective[] = words[4].split(" ");
            //System.out.println(verbs.length);

            for(int i=0; i<adjective.length; i++)
            {
                String token[] = adjective[i].split("#");
                //System.out.println(token[0]);
                if(posScore.containsKey(token[0]))
                {
                    posScore.put(token[0], Double.parseDouble(words[2])+posScore.get(token[0]));
                    negScore.put(token[0], Double.parseDouble(words[3])+negScore.get(token[0]));
                    count.put(token[0], count.get(token[0])+1);
                }
                else
                {
                    posScore.put(token[0], Double.parseDouble(words[2]));
                    negScore.put(token[0], Double.parseDouble(words[3]));
                    count.put(token[0], 1);
                }
            }
        }

        for (Map.Entry<String, Double> entry : posScore.entrySet()) {
            writer.println(entry.getKey() + "|" +posScore.get(entry.getKey())+"|"+negScore.get(entry.getKey().toString())+"|"+count.get(entry.getKey().toString()));
            System.out.println(entry.getKey() + ": " + entry.getValue() );
        }

        bf.close();
        writer.close();
    }
}

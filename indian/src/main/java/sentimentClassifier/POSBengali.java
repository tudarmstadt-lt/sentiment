package sentimentClassifier;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by krayush on 27-08-2015.
 */
public class POSBengali {
    String rootDirectory;

    POSBengali(String rootDirectory) throws IOException {
        //this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        //this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        generateDataset(rootDirectory + "\\dataset\\tokenized_Bengali_Train.txt", rootDirectory+"\\dataset\\tokenized_Train.txt");
        generateDataset(rootDirectory + "\\dataset\\tokenized_Bengali_Test.txt", rootDirectory+"\\dataset\\tokenized_Test.txt");
        //trainingFeature = generateFeature(rootDirectory + "\\dataset\\posHindiTrainTags.txt", rootDirectory + "\\dataset\\POS_Hindi_Train.txt", rootDirectory + "\\dataset\\tokenized_Train.txt");
        //testFeature = generateFeature(rootDirectory + "\\dataset\\posHindiTestTags.txt", rootDirectory + "\\dataset\\POS_Hindi_Test.txt", rootDirectory + "\\dataset\\tokenized_Test.txt");
    }

    private void generateDataset(String input, String output)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF-8"));
        Writer countWriter = new OutputStreamWriter(new FileOutputStream(output), "UTF-8");
        BufferedWriter fbw = new BufferedWriter(countWriter);

        String line;

        while((line = reader.readLine()) != null)
        {
            fbw.write(line+"\n");
        }

        fbw.close();

    }

    /*public static void main(String[] args)throws IOException
    {
        POSBengali ob = new POSBengali(System.getProperty("user.dir"));
    }*/


}

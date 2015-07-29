package de.tudarmstadt.lt.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 22-06-2015.
 */
public class NegatedWord {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    ArrayList<String> negatedWord;
    String rootDirectory;

    NegatedWord(String rootDirectory)throws IOException{
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        negatedWord = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(rootDirectory+"\\resources\\negatedWord.txt")));
        String word;

        while((word=reader.readLine()) != null)
        {
            negatedWord.add(word);
        }

        try {
            trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
            testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();

        String line;
        int count=0;
        double num=0;
        while((line = reader.readLine()) != null)
        {
            num=0;
            String tokens[] = line.split(" ");
            for(int i=0; i<tokens.length; i++)
            {
                if(negatedWord.contains(tokens[i]))
                {
                    //System.out.print(tokens[i]+" ");
                    num++;
                }
            }
            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            if(num != 0)
            {
                featureVector.get(count).put(1, num);
            }
            //System.out.println(count+" "+featureVector.get(count));
            count++;
        }
        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println("xyz: " + trainingFeature.size());
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        //System.out.println("abc: " + testFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(trainingFeature.get(0).size());
        return 1;
    }

    public static  void main(String[] args)throws IOException
    {
        NegatedWord ob = new NegatedWord("D:\\Course\\Semester VII\\Internship\\sentiment");
    }
}

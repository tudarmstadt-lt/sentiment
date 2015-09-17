package de.tudarmstadt.lt.sentimentClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 14-06-2015.
 */
public class BingLiuLexicon {

    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    BingLiuLexicon(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        File posFile = new File(rootDirectory + "\\resources\\BingLiuLexicon\\positive.txt");
        ArrayList<String> posToken = new ArrayList<String>();

        File negFile = new File(rootDirectory + "\\resources\\BingLiuLexicon\\negative.txt");
        ArrayList<String> negToken = new ArrayList<String>();

        String line = null;
        BufferedReader posReader = new BufferedReader(new FileReader(posFile));
        while ((line = posReader.readLine()) != null) {
            line = line.trim();
            posToken.add(line);
        }

        posReader.close();

        BufferedReader negReader = new BufferedReader(new FileReader(negFile));
        while ((line = negReader.readLine()) != null) {
            line = line.trim();
            negToken.add(line);
        }

        trainingFeature = generateFeature(posToken, negToken, rootDirectory + "\\dataset\\tokenized_Train.txt");
        System.out.println();
        testFeature = generateFeature(posToken, negToken, rootDirectory + "\\dataset\\tokenized_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(ArrayList posToken, ArrayList negToken, String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);

        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int count = 0;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String word[] = line.split(" ");
            double pos = 0, neg = 0;
            for (int i = 0; i < word.length; i++) {
                if (posToken.contains(word[i])) {
                    pos++;
                } else if (negToken.contains(word[i])) {
                    neg++;
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            featureVector.get(count).put(1, pos);
            featureVector.get(count).put(2, neg);
            /*if(neg != 0)
            {
                featureVector.get(count).put(4, pos/neg);
            }
            else if(pos == 0 && neg ==0)
            {
                featureVector.get(count).put(4, 1.0);
            }
            else
            {
                featureVector.get(count).put(4, 20.0);
            }*/
            //featureVector.get(count).put(3,Math.abs(pos-neg));

            //System.out.println("Pos: " + pos + ", Neg: " + neg);
            count++;
        }

        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        //System.out.println(trainingFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(trainingFeature.get(0).size());
        return trainingFeature.get(0).size();
    }

    /*public static void main(String[] args) throws IOException {
        BingLiuLexicon ob = new BingLiuLexicon("D:\\Course\\Semester VII\\Internship\\aspectCategorization");
    }*/
}

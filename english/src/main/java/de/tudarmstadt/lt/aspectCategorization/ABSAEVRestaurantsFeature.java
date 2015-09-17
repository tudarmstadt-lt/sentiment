package de.tudarmstadt.lt.aspectCategorization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 12-07-2015.
 */
public class ABSAEVRestaurantsFeature {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    int featureCount;

    ABSAEVRestaurantsFeature(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        //LinkedHashMap<String, Integer> indexedNgrams = new LinkedHashMap<String, Integer>();
        //indexedNgrams = generateNgrams();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\dataset_aspectCategorization\\EV_Train_Restaurants.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\dataset_aspectCategorization\\EV_Test_Restaurants.txt");

        //featureCount = 1;
    }


    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        BufferedReader read = new BufferedReader(new FileReader(new File(fileName)));
        String line = "";
        int count = 0;
        //int length=0;
        //String text = "";

        while ((line = read.readLine()) != null) {
            line = line.replace("\n", "").replace("\r", "");
            String tokens[] = line.split(",");
            featureCount = tokens.length;

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            for (int i = 0; i < tokens.length; i++) {
                if (Double.parseDouble(tokens[i]) == 1.0) {
                    featureVector.get(count).put(i + 1, 1.0);
                }
            }

            System.out.println(featureVector.get(count));
            count++;
        }
        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(featureCount);
        return featureCount;
    }

    /*public static void main(String[] args) throws IOException {
        ABSAEVRestaurantsFeature ob = new ABSAEVRestaurantsFeature("D:\\Course\\Semester VII\\Internship\\sentiment");
        //ob.getFeatureCount();
    }*/
}

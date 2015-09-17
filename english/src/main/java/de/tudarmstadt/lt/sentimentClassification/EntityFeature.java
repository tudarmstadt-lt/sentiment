package de.tudarmstadt.lt.sentimentClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 15-06-2015.
 */
public class EntityFeature {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    EntityFeature(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Restaurants_Cleansed.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Restaurants_Cleansed.txt");

        getTrainingList();
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        LinkedHashMap<String, Integer> entityMap = new LinkedHashMap<String, Integer>();
        entityMap.put("RESTAURANT", 1);
        entityMap.put("FOOD", 2);
        entityMap.put("DRINKS", 3);
        entityMap.put("AMBIENCE", 4);
        entityMap.put("SERVICE", 5);
        entityMap.put("LOCATION", 6);

        entityMap.put("GENERAL", 7);
        entityMap.put("PRICES", 8);
        entityMap.put("QUALITY", 9);
        entityMap.put("STYLE_OPTIONS", 10);
        entityMap.put("MISCELLANEOUS", 11);

        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split("\\|");
            String entity[] = words[5].split("#");
            if (entityMap.containsKey(entity[0])) {
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.get(count).put(entityMap.get(entity[0]), 1.0);
                featureVector.get(count).put(entityMap.get(entity[1]), 1.0);
                count++;
            } else {
                System.out.println("Error");
            }
        }
        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        /*for(int i=0; i<trainingFeature.size(); i++)
        {
            System.out.println(trainingFeature.get(i));
        }*/
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        //System.out.println(trainingFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(trainingFeature.get(0).size());
        return 11;
    }

    /*public static void main(String[] args) throws IOException {
        EntityFeature ef = new EntityFeature("D:\\Course\\Semester VII\\Internship\\aspectCategorization");
    }*/
}

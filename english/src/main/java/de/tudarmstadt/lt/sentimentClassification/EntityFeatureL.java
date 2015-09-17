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
public class EntityFeatureL {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    EntityFeatureL(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Laptops_Cleansed.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Laptops_Cleansed.txt");

        getTrainingList();
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        LinkedHashMap<String, Integer> entityMap = new LinkedHashMap<String, Integer>();
        entityMap.put("LAPTOP", 1);
        entityMap.put("DISPLAY", 2);
        entityMap.put("KEYBOARD", 3);
        entityMap.put("MOUSE", 4);
        entityMap.put("MOTHERBOARD", 5);
        entityMap.put("CPU", 6);
        entityMap.put("FANS_COOLING", 7);
        entityMap.put("PORTS", 8);
        entityMap.put("MEMORY", 9);
        entityMap.put("POWER_SUPPLY", 10);
        entityMap.put("OPTICAL_DRIVES", 11);
        entityMap.put("BATTERY", 12);
        entityMap.put("GRAPHICS", 13);
        entityMap.put("HARD_DISC", 14);
        entityMap.put("MULTIMEDIA_DEVICES", 15);
        entityMap.put("HARDWARE", 16);
        entityMap.put("SOFTWARE", 17);
        entityMap.put("OS", 18);
        entityMap.put("WARRANTY", 19);
        entityMap.put("SHIPPING", 20);
        entityMap.put("SUPPORT", 21);
        entityMap.put("COMPANY", 22);

        entityMap.put("GENERAL", 23);
        entityMap.put("PRICE", 24);
        entityMap.put("QUALITY", 25);
        entityMap.put("OPERATION_PERFORMANCE", 26);
        entityMap.put("USABILITY", 27);
        entityMap.put("DESIGN_FEATURES", 28);
        entityMap.put("PORTABILITY", 29);
        entityMap.put("CONNECTIVITY", 30);
        entityMap.put("MISCELLANEOUS", 31);

        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split("\\|");
            String entity[] = words[4].split("#");
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
        return trainingFeature.get(0).size();
    }

    /*public static void main(String[] args) throws IOException {
        EntityFeature ef = new EntityFeature("D:\\Course\\Semester VII\\Internship\\aspectCategorization");
    }*/
}

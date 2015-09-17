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
public class EntityFeatureH {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    EntityFeatureH(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeatureTrain(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Hotels_Cleansed.txt");
        testFeature = generateFeatureTest(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Hotels_Cleansed.txt");

        getTrainingList();
    }

    private List<LinkedHashMap<Integer, Double>> generateFeatureTest(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        LinkedHashMap<String, Integer> entityMap = new LinkedHashMap<String, Integer>();

        entityMap.put("GENERAL", 1);
        entityMap.put("PRICE", 2);
        entityMap.put("PRICES", 2);
        entityMap.put("QUALITY", 3);
        entityMap.put("OPERATION_PERFORMANCE", 3);
        entityMap.put("USABILITY", 4);
        entityMap.put("DESIGN_FEATURES", 5);
        entityMap.put("PORTABILITY", 4);
        entityMap.put("COMFORT", 4);
        entityMap.put("CONNECTIVITY", 3);
        entityMap.put("MISCELLANEOUS", 6);

        //entityMap.put("GENERAL", 1.0);
        //entityMap.put("PRICES", 2.0);
        //entityMap.put("QUALITY", 3.0);
        entityMap.put("STYLE_OPTIONS", 7);
        entityMap.put("CLEANLINESS", 6);
        //entityMap.put("MISCELLANEOUS", 5.0);


        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split("\\|");
            String entity[] = words[5].split("#");
            if (entityMap.containsKey(entity[1])) {
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                //featureVector.get(count).put(1, entityMap.get(entity[0]));
                featureVector.get(count).put(entityMap.get(entity[1]), 1.0);
                count++;
            } else {
                System.out.println("Error: " + entity[1] + count + "ABC");
            }
        }
        return featureVector;
    }

    private List<LinkedHashMap<Integer, Double>> generateFeatureTrain(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        LinkedHashMap<String, Integer> entityMap = new LinkedHashMap<String, Integer>();

        entityMap.put("GENERAL", 1);
        entityMap.put("PRICE", 2);
        entityMap.put("PRICES", 2);
        entityMap.put("QUALITY", 3);
        entityMap.put("OPERATION_PERFORMANCE", 3);
        entityMap.put("USABILITY", 4);

        entityMap.put("DESIGN_FEATURES", 5);
        entityMap.put("PORTABILITY", 4);
        entityMap.put("CONNECTIVITY", 3);
        entityMap.put("MISCELLANEOUS", 6);

        //entityMap.put("GENERAL", 1.0);
        //entityMap.put("PRICES", 2.0);
        //entityMap.put("QUALITY", 3.0);
        entityMap.put("STYLE_OPTIONS", 7);
        entityMap.put("CLEANLINESS", 6);
        //entityMap.put("MISCELLANEOUS", 5.0);


        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split("\\|");
            String entity[];
            if (count < 1200) {
                entity = words[4].split("#");
            } else {
                entity = words[5].split("#");
            }

            if (entityMap.containsKey(entity[1])) {
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                //featureVector.get(count).put(1, entityMap.get(entity[0]));
                featureVector.get(count).put(entityMap.get(entity[1]), 1.0);
                count++;
            } else {
                System.out.println("Error: " + entity[1] + count + "ABC");
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

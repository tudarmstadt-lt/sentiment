package de.tudarmstadt.lt.sentimentClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 23-06-2015.
 */
public class BrownClusters {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    LinkedHashMap<String, Integer> cluster;
    LinkedHashMap<String, Integer> clusterNumber;
    String rootDirectory;

    BrownClusters(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        cluster = new LinkedHashMap<String, Integer>();
        clusterNumber = new LinkedHashMap<String, Integer>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\brownclusters.txt")));
        String line;
        int count = 1;
        while ((line = reader.readLine()) != null) {
            String tokens[] = line.split("\\t");
            if (!clusterNumber.containsKey(tokens[0])) {
                clusterNumber.put(tokens[0], count++);
            }

            cluster.put(tokens[1], clusterNumber.get(tokens[0]));
        }

        //System.out.println(cluster);

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
        int count = 0, num = 0;
        Double arr[] = new Double[1000];
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < 1000; i++) {
                arr[i] = 0.0;
            }

            String tokens[] = line.split(" ");
            for (int i = 0; i < tokens.length; i++) {
                if (cluster.containsKey(tokens[i])) {
                    arr[cluster.get(tokens[i]) - 1]++;
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());

            for (int i = 0; i < 1000; i++) {
                if (arr[i] != 0) {
                    featureVector.get(count).put(i + 1, 1.0);
                }
            }
            //System.out.println(featureVector.get(count));
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
        return 1000;
    }

    public static void main(String[] args) throws IOException {
        BrownClusters ob = new BrownClusters("D:\\Course\\Semester VII\\Internship\\sentiment");
    }
}

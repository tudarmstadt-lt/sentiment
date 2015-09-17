package de.tudarmstadt.lt.sentimentClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 17-06-2015.
 */
public class MPQALexicon {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    MPQALexicon(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\tokenized_Train.txt");
        System.out.println();
        testFeature = generateFeature(rootDirectory + "\\dataset\\dataset_sentimentClassification\\tokenized_Test.txt");

    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        LinkedHashMap<String, Double> mpqaHash = new LinkedHashMap<String, Double>();

        BufferedReader mpqa = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\MPQALexicon\\mpqa.txt")));

        String line;

        while ((line = mpqa.readLine()) != null) {
            //System.out.println(line);
            line = line.trim();
            String tokens[] = line.split(" ");
            String type[] = tokens[0].split("=");
            String polarity[] = tokens[5].split("=");
            //System.out.println(polarity.length);
            if (polarity[1].compareToIgnoreCase("positive") == 0) {
                if (type[1].compareToIgnoreCase("strongsubj") == 0) {
                    mpqaHash.put(tokens[2].split("=")[1], 5.0);
                } else {
                    mpqaHash.put(tokens[2].split("=")[1], 2.5);
                }
            } else if (polarity[1].compareToIgnoreCase("negative") == 0) {
                if (type[1].compareToIgnoreCase("strongsubj") == 0) {
                    mpqaHash.put(tokens[2].split("=")[1], -5.0);
                } else {
                    mpqaHash.put(tokens[2].split("=")[1], -2.5);
                }
            } else if (polarity[1].compareToIgnoreCase("neutral") == 0) {
                mpqaHash.put(tokens[2].split("=")[1], 0.0);
            }
        }

        int count = 0;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            String tokens[] = line.split(" ");
            double pos = 0, neg = 0, neutral = 0, total = 0;
            for (int i = 0; i < tokens.length; i++) {
                if (mpqaHash.containsKey(tokens[i].toLowerCase())) {
                    //System.out.println(tokens[i]);
                    //System.out.println(mpqaHash.get(tokens[i]));
                    total += mpqaHash.get(tokens[i]);
                    if (mpqaHash.get(tokens[i]) == 5.0 || mpqaHash.get(tokens[i]) == 2.5) {
                        //System.out.print(tokens[i]+", ");
                        pos++;
                    } else if (mpqaHash.get(tokens[i]) == -5.0 || mpqaHash.get(tokens[i]) == -2.5) {
                        //System.out.print(tokens[i]+", ");
                        neg++;
                    } else if (mpqaHash.get(tokens[i]) == 0.0) {
                        neutral++;
                    }
                }
            }


            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            if (pos != 0 || neg != 0) {
                featureVector.get(count).put(1, pos);
                featureVector.get(count).put(2, neg);
                featureVector.get(count).put(3, total);
                /*if(neg != 0)
                {
                    featureVector.get(count).put(3, pos/neg);
                }
                else if(pos == 0 && neg ==0)
                {
                    featureVector.get(count).put(3, 1.0);
                }
                else
                {
                    featureVector.get(count).put(3, 50.0);
                }*/

                //featureVector.get(count).put(3, neutral);
                //featureVector.get(count).put(3, pos-neg);
                //System.out.println(count+" "+pos+" "+neg+" "+neutral);
            }
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
        return trainingFeature.get(0).size();
    }

    public static void main(String[] main) throws IOException {
        MPQALexicon ob = new MPQALexicon("D:\\Course\\Semester VII\\Internship\\aspectCategorization");
    }

}

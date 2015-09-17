package de.tudarmstadt.lt.sentimentClassification;

//import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Senti140Lexicon {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    Senti140Lexicon(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");

    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(rootDirectory + "\\resources\\Sentiment140Lexicon\\unigrams-pmilexicon.txt");
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            HashMap<String, Double> scoreMap = new HashMap<String, Double>();
            while ((line = reader.readLine()) != null) {
                String[] str = line.split("\\t");
                scoreMap.put(str[0], Double.parseDouble(str[1]));
                //System.out.println(str[1]);
            }
            reader.close();

            file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            //int i = 0;
            int count = 0;
            //List<HashMap<Integer, Double>> trainingFeature = new ArrayList<HashMap<Integer, Double>>();
            while ((line = reader.readLine()) != null) {

                double pos = 0, neg = 0;
                String[] str = line.split("[ \t\n\\x0B\f\r]+");
                double totalScore = 0.0;
                //System.out.println(str.length);       //Check the split method
                double max = -20.0;
                for (int i = 0; i < str.length; i++) {
                    if (scoreMap.get(str[i]) != null) {
                        double currScore = scoreMap.get(str[i]);
                        if (currScore > 0) {
                            pos++;
                        } else if (currScore < 0) {
                            neg++;
                        }
                        totalScore += currScore;
                        if (max < currScore) {
                            max = currScore;
                        }
                        //System.out.println(count);
                    }
                }

                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.get(count).put(1, totalScore);
                featureVector.get(count).put(2, pos);
                featureVector.get(count).put(3, neg);
                featureVector.get(count).put(4, max);
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
                //featureVector.get(count).put(4,Math.abs(pos-neg));

                count++;
                //System.out.println(totalScore+" "+pos+" "+neg);
            }

            //System.out.println("In NRC Class " + featureVector.size());
            /*for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
            {
                //System.out.println(trainingFeature.get(i).size());
                System.out.println(trainingFeature.get(i));
            }*/
        } catch (IOException e) {
            System.out.println(e);
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


}


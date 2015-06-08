package de.tudarmstadt.lt.sentiment;

//import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

class NRCHashtag {
    List<LinkedHashMap<Integer, Double>> trainingFeature;

    NRCHashtag() {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        generateFeature();
    }

    private void generateFeature() {
        File file = new File("/Users/biem/sentiment/dataset/resources/nrcHashtagLexicon/unigrams-pmilexicon.txt");
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

            file = new File("/Users/biem/sentiment/dataset/tokenized_Train.txt");
            reader = new BufferedReader(new FileReader(file));
            //int i = 0;
            int count = 0;
            //List<HashMap<Integer, Double>> trainingFeature = new ArrayList<HashMap<Integer, Double>>();
            while ((line = reader.readLine()) != null) {

                double pos = 0, neg = 0;
                String[] str = line.split("[ \t\n\\x0B\f\r]+");
                double totalScore = 0.0;
                //System.out.println(str.length);       //Check the split method
                for (int i = 0; i < str.length; i++) {
                    if (scoreMap.get(str[i]) != null) {
                        double currScore = scoreMap.get(str[i]);
                        if (currScore > 0) {
                            pos++;
                        } else if (currScore < 0) {
                            neg++;
                        }
                        totalScore += currScore;
                        //System.out.println(count);
                    }
                }

                trainingFeature.add(count, new LinkedHashMap<Integer, Double>());
                trainingFeature.get(count).put(1, totalScore);
                trainingFeature.get(count).put(2, pos);
                trainingFeature.get(count).put(3, neg);

                count++;
                //System.out.println(totalScore+" "+pos+" "+neg);
            }

            System.out.println("In NRC Class " + trainingFeature.size());
            /*for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
            {
                //System.out.println(trainingFeature.get(i).size());
                System.out.println(trainingFeature.get(i));
            }*/
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        return this.trainingFeature;
    }

    public int getFeatureCount() {
        return trainingFeature.get(0).size();
    }
}


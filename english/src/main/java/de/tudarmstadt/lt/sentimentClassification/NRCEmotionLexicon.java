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

public class NRCEmotionLexicon {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    LinkedHashMap<String, Double> emotion;
    LinkedHashMap<String, Double> sentiment;
    String rootDirectory;

    NRCEmotionLexicon(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        emotion = new LinkedHashMap<String, Double>();
        sentiment = new LinkedHashMap<String, Double>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\NRCEmotionLexicon\\nrcemotion.txt")));
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            String tokens[] = line.split("\\t");
            emotion.put(tokens[0], Double.parseDouble(tokens[1]));
            sentiment.put(tokens[0], Double.parseDouble(tokens[2]));
        }

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");

    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        //File file = new File(rootDirectory + "\\resources\\NRCEmotionLexicon\\nrcemotion.txt");
        String line = null;

        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        //int i = 0;
        int count = 0;
        //List<HashMap<Integer, Double>> trainingFeature = new ArrayList<HashMap<Integer, Double>>();
        while ((line = reader.readLine()) != null) {
            String[] str = line.split(" ");
            double pos=0.0, neg=0.0, neutral=0.0;
            double totalSentiment=0, totalEmotion=0, total=0;

            for (int i = 0; i < str.length; i++) {
                if (sentiment.get(str[i]) != null) {
                    double currSentiment= sentiment.get(str[i]);
                    double currEmotion = emotion.get(str[i]);

                    if (currSentiment > 0) {
                        pos++;
                    } else if (currSentiment < 0) {
                        neg++;
                    }
                    else {
                        neutral++;
                    }
                    totalSentiment += currSentiment;
                    totalEmotion += currEmotion;
                    total += totalEmotion*totalSentiment;
                    //System.out.println(count);
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            featureVector.get(count).put(1, pos);
            featureVector.get(count).put(2, neg);
            featureVector.get(count).put(3, neutral);
            featureVector.get(count).put(4, totalSentiment);
            featureVector.get(count).put(5, totalEmotion);
            featureVector.get(count).put(6, total);
            //System.out.println(featureVector.get(count));
            count++;
            //System.out.println(totalScore+" "+pos+" "+neg);
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

    public static void main(String[] args)throws IOException
    {
        NRCEmotionLexicon ef = new NRCEmotionLexicon("D:\\Course\\Semester VII\\Internship\\sentiment");
    }


}


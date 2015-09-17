package de.tudarmstadt.lt.sentimentClassification;

//import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AFINNLexicon {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    LinkedHashMap<String, Double> scoreMap;
    //LinkedHashMap<String, Double> aspectCategorization;
    String rootDirectory;

    AFINNLexicon(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        scoreMap = new LinkedHashMap<String, Double>();
        //aspectCategorization = new LinkedHashMap<String, Double>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\AFINNLexicon\\afinn.txt")));
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            String tokens[] = line.split("\\t");
            scoreMap.put(tokens[0], Double.parseDouble(tokens[1]));
            //aspectCategorization.put(tokens[0], Double.parseDouble(tokens[2]));
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
            double pos = 0.0, neg = 0.0;
            double totalScore = 0;
            double max = 0;
            for (int i = 0; i < str.length; i++) {
                if (scoreMap.get(str[i]) != null) {
                    double currScore = scoreMap.get(str[i]);
                    //double currEmotion = emotion.get(str[i]);

                    if (currScore > 0) {
                        pos++;
                    } else if (currScore < 0) {
                        neg++;
                    }
                    if (max < pos) {
                        max = pos;
                    }
                    totalScore += currScore;
                    //totalEmotion += currEmotion;
                    //total += totalEmotion*totalSentiment;
                    //System.out.println(count);
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            featureVector.get(count).put(1, pos);
            featureVector.get(count).put(2, neg);
            featureVector.get(count).put(3, totalScore);
            featureVector.get(count).put(4, max);
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

    /*public static void main(String[] args) throws IOException {
        AFINNLexicon ef = new AFINNLexicon("D:\\Course\\Semester VII\\Internship\\aspectCategorization");
    }*/


}


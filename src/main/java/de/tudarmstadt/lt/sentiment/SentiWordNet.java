package de.tudarmstadt.lt.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 12-06-2015.
 */
public class SentiWordNet {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    SentiWordNet(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        try {
            trainingFeature = generateFeature(rootDirectory + "\\dataset\\raw_POS_Train.txt");
            testFeature = generateFeature(rootDirectory + "\\dataset\\raw_POS_Test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*private LinkedHashMap<String, Double> getSentiWordNetScores(String filename)
    {

    }*/

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();

        String line;

        BufferedReader noun = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\SentiWordNet\\nounsScore.txt")));
        BufferedReader adverb = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\SentiWordNet\\adverbsScore.txt")));
        BufferedReader adjective = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\SentiWordNet\\adjectivesScore.txt")));
        BufferedReader verb = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\SentiWordNet\\verbsScore.txt")));

        LinkedHashMap<String, Double> nounPos = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> nounNeg = new LinkedHashMap<String, Double>();
        while ((line = noun.readLine()) != null) {
            String values[] = line.split("\\|");
            nounPos.put(values[0], Double.parseDouble(values[1]));
            nounNeg.put(values[0], Double.parseDouble(values[2]));
        }

        LinkedHashMap<String, Double> adverbPos = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> adverbNeg = new LinkedHashMap<String, Double>();
        while ((line = adverb.readLine()) != null) {
            String values[] = line.split("\\|");
            adverbPos.put(values[0], Double.parseDouble(values[1]));
            adverbNeg.put(values[0], Double.parseDouble(values[2]));
        }

        LinkedHashMap<String, Double> adjectivePos = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> adjectiveNeg = new LinkedHashMap<String, Double>();
        while ((line = adjective.readLine()) != null) {
            String values[] = line.split("\\|");
            adjectivePos.put(values[0], Double.parseDouble(values[1]));
            adjectiveNeg.put(values[0], Double.parseDouble(values[2]));
        }

        LinkedHashMap<String, Double> verbPos = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> verbNeg = new LinkedHashMap<String, Double>();
        while ((line = verb.readLine()) != null) {
            String values[] = line.split("\\|");
            verbPos.put(values[0], Double.parseDouble(values[1]));
            verbNeg.put(values[0], Double.parseDouble(values[2]));
        }


        int count = 0;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            double pos = 0, neg = 0, occurence = 0;
            double total = 0.0;
            double equal = 0;
            String tokens[] = line.split(" ");

            for (String token : tokens) {
                String word[] = token.split("_");
                if (word[1].compareToIgnoreCase("NN") == 0 || word[1].compareToIgnoreCase("NNS") == 0 || word[1].compareToIgnoreCase("NNP") == 0 || word[1].compareToIgnoreCase("NNPS") == 0) {
                    if (nounPos.containsKey(word[0])) {
                        occurence++;
                        double pVal = nounPos.get(word[0]);
                        double nVal = nounNeg.get(word[0]);

                        if (pVal > nVal) {
                            pos++;
                        } else if (pVal < nVal) {
                            neg++;
                        } else {
                            equal++;
                        }

                        total += pVal - nVal;
                    }
                } else if (word[1].compareToIgnoreCase("RB") == 0 || word[1].compareToIgnoreCase("RBR") == 0 || word[1].compareToIgnoreCase("RBS") == 0) {
                    if (adverbPos.containsKey(word[0])) {
                        occurence++;
                        double pVal = adverbPos.get(word[0]);
                        double nVal = adverbNeg.get(word[0]);

                        if (pVal > nVal) {
                            pos++;
                        } else if (pVal < nVal) {
                            neg++;
                        } else {
                            equal++;
                        }

                        total += pVal - nVal;
                    }
                } else if (word[1].compareToIgnoreCase("JJ") == 0 || word[1].compareToIgnoreCase("JJR") == 0 || word[1].compareToIgnoreCase("JJS") == 0) {
                    if (adjectivePos.containsKey(word[0])) {
                        occurence++;
                        double pVal = adjectivePos.get(word[0]);
                        double nVal = adjectiveNeg.get(word[0]);

                        if (pVal > nVal) {
                            pos++;
                        } else if (pVal < nVal) {
                            neg++;
                        } else {
                            equal++;
                        }

                        total += pVal - nVal;
                    }
                } else if (word[1].compareToIgnoreCase("VB") == 0 || word[1].compareToIgnoreCase("VBD") == 0 || word[1].compareToIgnoreCase("VBG") == 0 || word[1].compareToIgnoreCase("VBN") == 0 || word[1].compareToIgnoreCase("VBP") == 0 || word[1].compareToIgnoreCase("VBZ") == 0) {
                    if (verbPos.containsKey(word[0])) {
                        occurence++;
                        double pVal = verbPos.get(word[0]);
                        double nVal = verbNeg.get(word[0]);

                        if (pVal > nVal) {
                            pos++;
                        } else if (pVal < nVal) {
                            neg++;
                        } else {
                            equal++;
                        }

                        total += pVal - nVal;
                    }
                }
                //System.out.println(word[0]);
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            //featureVector.get(count).put(1, pos);
            //featureVector.get(count).put(2, neg);
            featureVector.get(count).put(1, total);
            //featureVector.get(count).put(2, equal);

            count++;
            //System.out.println("Pos: "+pos+", Neg: "+neg+", Total: "+total+ ", Equal: "+equal+", Count: "+occurence);

        }
        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        System.out.println("xyz: " + trainingFeature.size());
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        System.out.println("abc: " + testFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(trainingFeature.get(0).size());
        return trainingFeature.get(0).size();
    }
}

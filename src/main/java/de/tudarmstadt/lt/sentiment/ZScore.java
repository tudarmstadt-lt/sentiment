package de.tudarmstadt.lt.sentiment;

//import com.oracle.webservices.internal.api.message.BasePropertySet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by krayush on 24-06-2015.
 */
public class ZScore {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    LinkedHashMap<String, Double> posZList;
    LinkedHashMap<String, Double> negZList;
    LinkedHashMap<String, Double> neutralZList;

    ZScore(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        posZList = new LinkedHashMap<String, Double>();
        negZList = new LinkedHashMap<String, Double>();
        neutralZList = new LinkedHashMap<String, Double>();
        //totalWords = 0;

        calculateWordFrequency(rootDirectory + "\\dataset\\tokenized_Train.txt", rootDirectory + "\\dataset\\trainingLabels.txt");


        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        //System.out.println();
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");

    }

    private void calculateWordFrequency(String fileName, String labelFile) throws IOException {
        BufferedReader trainFile = new BufferedReader(new FileReader(new File(fileName)));
        BufferedReader label = new BufferedReader(new FileReader(new File(labelFile)));
        String line;
        int totalPosWords = 0, totalNegWords = 0, totalNeutralWords = 0;

        LinkedHashMap<String, Integer> posList = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> negList = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> neutralList = new LinkedHashMap<String, Integer>();

        while ((line = trainFile.readLine()) != null) {
            String words[] = line.split(" ");
            Integer polarity = Integer.parseInt(label.readLine());
            for (int i = 0; i < words.length; i++) {
                if (polarity == -1) {
                    if (!negList.containsKey(words[i])) {
                        negList.put(words[i], 1);
                    } else {
                        negList.put(words[i], negList.get(words[i]) + 1);
                    }
                    totalNegWords++;
                } else if (polarity == 1) {
                    if (!posList.containsKey(words[i])) {
                        posList.put(words[i], 1);
                    } else {
                        posList.put(words[i], posList.get(words[i]) + 1);
                    }
                    totalPosWords++;
                } else if (polarity == 0) {
                    if (!neutralList.containsKey(words[i])) {
                        neutralList.put(words[i], 1);
                    } else {
                        neutralList.put(words[i], neutralList.get(words[i]) + 1);
                    }
                    totalNeutralWords++;
                }
            }
        }

        int totalWords = totalNegWords + totalPosWords + totalNeutralWords;
        //System.out.println(totalWords);

        //System.out.println(posList);
        posZList = sortHashMapByValuesD(calculateZScore(posList, negList, neutralList, totalPosWords, totalWords));
        negZList = sortHashMapByValuesD(calculateZScore(negList, posList, neutralList, totalNegWords, totalWords));
        neutralZList = sortHashMapByValuesD(calculateZScore(neutralList, posList, negList, totalNeutralWords, totalWords));

        //System.out.println(posZList);
        //System.out.println(negZList);
        //System.out.println(neutralZList);

        /*posZList = normalize(posZList);
        negZList = normalize(negZList);
        neutralZList = normalize(neutralZList);*/

        //System.out.println(posZList);
        //System.out.println(negZList);
        //System.out.println(neutralZList);
    }

    private LinkedHashMap<String, Double> normalize(LinkedHashMap<String, Double> zScore) {
        Iterator iPos = zScore.entrySet().iterator();
        // Display elements
        Double min = Double.parseDouble(((Map.Entry) iPos.next()).getValue().toString());
        Double max = 0.0;
        while (iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            max = val;
        }

        Iterator map = zScore.entrySet().iterator();
        while (map.hasNext()) {
            Map.Entry me = (Map.Entry) map.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            val = 10 * (val - min) / (max - min);
            zScore.put(key, val);
        }


        return zScore;
    }

    private LinkedHashMap<String, Double> calculateZScore(LinkedHashMap<String, Integer> wordList, LinkedHashMap<String, Integer> wordListA, LinkedHashMap<String, Integer> wordListB, int totalClassWords, int totalWords) {

        LinkedHashMap<String, Double> scoreMap = new LinkedHashMap<String, Double>();
        Set set = wordList.entrySet();
        // Get an iterator
        Iterator iPos = set.iterator();
        // Display elements
        while (iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            //System.out.println(val);
            Double tfir = val;
            Double totalOccurences = val;
            if (wordListA.containsKey(key)) {
                totalOccurences += wordListA.get(key);
            }
            if (wordListB.containsKey(key)) {
                totalOccurences += wordListB.get(key);
            }

            int n = totalClassWords;
            Double prob = totalOccurences / totalWords;
            Double mean = n * prob;
            Double sd = Math.sqrt(n * prob * (1 - prob));

            Double zScore = (tfir - mean) / sd;
            /*if((tfir-mean)>0)
            {
                System.out.println(key);
            }*/
            scoreMap.put(key, zScore);
        }

        return scoreMap;
    }


    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        //LinkedHashMap<String, Double> mpqaHash = new LinkedHashMap<String, Double>();

        //BufferedReader mpqa = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\MPQALexicon\\mpqa.txt")));

        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            Double pos = 0.0, neg = 0.0, neutral = 0.0;
            Double posVal = 0.0, negVal = 0.0, neutralVal = 0.0;
            Double extraPos = 0.0, extraNeg = 0.0, extraNeutral = 0.0;
            Double val;
            String tokens[] = line.split(" ");
            /*System.out.println();
            System.out.println();
            System.out.println(count+"  ###################");
            System.out.println();
            for(int i=0; i<tokens.length; i++)
            {
                System.out.println();
                if(posZList.containsKey(tokens[i]))
                {
                    System.out.print(posZList.get(tokens[i])+" ");
                }
                else
                {
                    System.out.print("P  ");
                }
                if(negZList.containsKey(tokens[i]))
                {
                    System.out.print(negZList.get(tokens[i])+" ");
                }
                else
                {
                    System.out.print("N  ");
                }
                if(neutralZList.containsKey(tokens[i]))
                {
                    System.out.print(neutralZList.get(tokens[i])+" ");
                }
                else
                {
                    System.out.print("T  ");
                }
            }*/


            for (int i = 0; i < tokens.length; i++) {
                if (posZList.containsKey(tokens[i]) && posZList.get(tokens[i]) >= 1.31) {
                    pos++;
                    posVal += posZList.get(tokens[i]);

                    /*if(posZList.get(tokens[i]) >=1.81)
                    {
                        extraPos++;
                    }*/
                }
                if (negZList.containsKey(tokens[i]) && negZList.get(tokens[i]) >= 1.67) {
                    neg++;
                    negVal += negZList.get(tokens[i]);

                    /*if(negZList.get(tokens[i]) >=2.73)
                    {
                        extraNeg++;
                    }*/
                }
                if (neutralZList.containsKey(tokens[i]) && neutralZList.get(tokens[i]) >= 1.93) {
                    neutral++;
                    neutralVal += neutralZList.get(tokens[i]);

                    /*if(neutralZList.get(tokens[i]) >=5)
                    {
                        extraNeutral++;
                    }*/
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            /*if(pos != 0)
            {
                featureVector.get(count).put(1, posVal/pos);
            }
            else
            {
                featureVector.get(count).put(1, 0.0);
            }

            if(neg != 0)
            {
                featureVector.get(count).put(2, negVal/neg);
            }
            else
            {
                featureVector.get(count).put(2, 0.0);
            }*/

            featureVector.get(count).put(1, pos);
            featureVector.get(count).put(2, neg);
            //featureVector.get(count).put(1, extraPos);
            //featureVector.get(count).put(2, extraNeg);
            //featureVector.get(count).put(5, extraNeutral);
            //featureVector.get(count).put(3, neutral/tokens.length);
            /*if(pos>neg && pos>neutral)
            {
                featureVector.get(count).put(4, 1.0);
            }
            else if(neg>pos && neg>neutral)
            {
                featureVector.get(count).put(5, 1.0);
            }
            else if(neutral>pos && neutral>neg)
            {
                featureVector.get(count).put(6, 1.0);
            }*/

            //System.out.println(featureVector.get(count));
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
        return 2;
    }

    public static void main(String[] args) throws IOException {
        ZScore ob = new ZScore("D:\\Course\\Semester VII\\Internship\\sentiment");
    }

    private LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap =
                new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, val);
                    break;
                }

            }

        }
        return sortedMap;
    }

}

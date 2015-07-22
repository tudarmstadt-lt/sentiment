package de.tudarmstadt.lt.sentiment;

import com.oracle.webservices.internal.api.message.BasePropertySet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by krayush on 24-06-2015.
 */
public class PMIScore {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    LinkedHashMap<String, Double> posPList;
    LinkedHashMap<String, Double> negPList;
    LinkedHashMap<String, Double> pmiScore;
    //LinkedHashMap<String, Double> neutralPList;

    PMIScore(String rootDirectory)throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        posPList = new LinkedHashMap<String, Double>();
        negPList = new LinkedHashMap<String, Double>();
        pmiScore = new LinkedHashMap<String, Double>();
        //neutralPList = new LinkedHashMap<String, Double>();
        //totalWords = 0;

        calculateWordFrequency(rootDirectory + "\\dataset\\tokenized_Train.txt", rootDirectory + "\\dataset\\trainingLabels.txt");


        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        //System.out.println();
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");

    }

    private void calculateWordFrequency(String fileName, String labelFile)throws  IOException
    {
        BufferedReader trainFile = new BufferedReader(new FileReader(new File(fileName)));
        BufferedReader label = new BufferedReader(new FileReader(new File(labelFile)));
        String line;
        double totalPosWords=0, totalNegWords=0;
        double posInstances=0, negInstances=0;

        LinkedHashMap<String, Integer> posList = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> negList = new LinkedHashMap<String, Integer>();;
        //LinkedHashMap<String, Integer> neutralList = new LinkedHashMap<String, Integer>();;

        while((line = trainFile.readLine()) != null)
        {
            String words[] = line.split(" ");
            Integer polarity = Integer.parseInt(label.readLine());

                if(polarity == -1)
                {
                    negInstances++;
                    for(int i=0; i<words.length; i++) {
                        if(!negList.containsKey(words[i]))
                        {
                            negList.put(words[i], 1);
                        }
                        else
                        {
                            negList.put(words[i], negList.get(words[i])+1);
                        }
                        totalNegWords++;
                    }
                }
                else if(polarity == 1)
                {
                    posInstances++;
                    for(int i=0; i<words.length; i++) {
                        if(!posList.containsKey(words[i]))
                        {
                            posList.put(words[i], 1);
                        }
                        else
                        {
                            posList.put(words[i], posList.get(words[i])+1);
                        }
                        totalPosWords++;
                    }
                }
            }

        double totalWords=totalNegWords+totalPosWords;
        double totalInstances = posInstances+negInstances;
        //System.out.println(totalWords);
        //System.out.println(posInstances+" "+negInstances);
        //System.out.println(posList);
        posPList = calculatePMIClassScore(posList, negList, posInstances, totalPosWords, totalWords, totalInstances);
        negPList = calculatePMIClassScore(negList, posList, negInstances, totalNegWords, totalWords, totalInstances);
        //neutralPList = sortHashMapByValuesD(calculateZScore(neutralList, posList, negList, totalNeutralWords, totalWords));

        pmiScore = sortHashMapByValuesD(calculatePMIScore(posPList, negPList));
        System.out.println(pmiScore);
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

    private LinkedHashMap<String, Double> normalize(LinkedHashMap<String, Double>zScore)
    {
        Iterator iPos = zScore.entrySet().iterator();
        // Display elements
        Double min=Double.parseDouble(((Map.Entry) iPos.next()).getValue().toString());
        Double max=0.0;
        while(iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            max=val;
        }

        Iterator map = zScore.entrySet().iterator();
        while(map.hasNext())
        {
            Map.Entry me  = (Map.Entry) map.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            val = 10*(val-min)/(max-min);
            zScore.put(key, val);
        }


        return zScore;
    }

    private LinkedHashMap<String, Double> calculatePMIClassScore(LinkedHashMap<String, Integer> wordList, LinkedHashMap<String, Integer> wordListA, double totalClassInstances, double classWords, double totalWords, double totalInstances)
    {
        LinkedHashMap<String, Double> scoreMap = new LinkedHashMap<String, Double>();
        Set set = wordList.entrySet();
        // Get an iterator
        Iterator iPos = set.iterator();
        // Display elements
        while(iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());
            //System.out.println(val);
            Double tfir = val;
            Double totalOccurences=val;
            if(wordListA.containsKey(key))
            {
                totalOccurences += wordListA.get(key);
            }

            double P1 = tfir/totalWords;
            //double P2 = totalOccurences/totalWords;
            double P2 = totalOccurences/totalWords;
            double P3 = classWords/totalWords;

            /*System.out.println(tfir+" "+totalOccurences+" "+P1);
            System.out.println(totalOccurences+" "+totalWords+" "+P2);
            System.out.println(totalClassInstances+" "+totalInstances+" "+P3);*/

            double pmi = (Math.log10(P1/(P2*P3)))/Math.log10(2);
            //System.out.println(P1+" "+P2+" "+P3+" "+key+" "+pmi);
            scoreMap.put(key, pmi);
        }

        return scoreMap;
    }

    private LinkedHashMap<String, Double> calculatePMIScore(LinkedHashMap<String, Double> posList, LinkedHashMap<String, Double> negList)
    {
        LinkedHashMap<String, Double> scoreMap = new LinkedHashMap<String, Double>();
        Set set = posList.entrySet();
        // Get an iterator
        Iterator iPos = set.iterator();
        // Display elements
        while(iPos.hasNext()) {
            Map.Entry me = (Map.Entry) iPos.next();
            String key = me.getKey().toString();
            Double val = Double.parseDouble(me.getValue().toString());

            if(negList.containsKey(key))
            {
                scoreMap.put(key, val-negList.get(key));
            }
            else
            {
                scoreMap.put(key, val);
            }
        }

        System.out.println(scoreMap.size());

        //Set set = posList.entrySet();
        // Get an iterator
        Iterator iNeg = negList.entrySet().iterator();
        // Display elements
        while(iNeg.hasNext()) {
            Map.Entry me = (Map.Entry) iNeg.next();
            String key = me.getKey().toString();
            if(!scoreMap.containsKey(key))
            {
                Double val = Double.parseDouble(me.getValue().toString());
                scoreMap.put(key, 0-val);
            }
        }
        System.out.println(scoreMap.size());

        return  scoreMap;
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName)throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        //LinkedHashMap<String, Double> mpqaHash = new LinkedHashMap<String, Double>();

        //BufferedReader mpqa = new BufferedReader(new FileReader(new File(rootDirectory + "\\resources\\MPQALexicon\\mpqa.txt")));

        String line;
        int count=0;
        while ((line = reader.readLine()) != null) {
            Double pos=0.0, neg=0.0;
            //Double posVal=0.0, negVal=0.0, neutralVal=0.0;
            //Double extraPos=0.0, extraNeg=0.0, extraNeutral=0.0;
            double totalScore = 0, max=0;

            //Double val;
            String tokens[] = line.split(" ");
            /*System.out.println();
            System.out.println();
            System.out.println(count+"  ###################");
            System.out.println();*/


            for(int i=0; i<tokens.length; i++)
            {
                if(pmiScore.containsKey(tokens[i]))
                {
                    double currScore = pmiScore.get(tokens[i]);
                    if(currScore>0)
                    {
                        pos++;
                    }
                    else if(currScore<0)
                    {
                        neg++;
                    }

                    totalScore += currScore;
                    if(max<currScore)
                    {
                        max = currScore;
                    }
                }
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            featureVector.get(count).put(1, totalScore);
            featureVector.get(count).put(2, pos);
            featureVector.get(count).put(3, neg);
            //featureVector.get(count).put(4, max);
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
        return 3;
    }

    public static void main(String[] args)throws IOException
    {
        PMIScore ob = new PMIScore("D:\\Course\\Semester VII\\Internship\\sentiment");
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

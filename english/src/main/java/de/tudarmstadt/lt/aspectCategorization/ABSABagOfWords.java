package de.tudarmstadt.lt.aspectCategorization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//package de.tu.darmstadt.lt.ner.util;

public class ABSABagOfWords {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    int featureCount;

    ABSABagOfWords(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        LinkedHashMap<String, Integer> indexedNgrams = new LinkedHashMap<String, Integer>();
        indexedNgrams = generateNgrams();

        trainingFeature = generateFeature(indexedNgrams, rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(indexedNgrams, rootDirectory + "\\dataset\\tokenized_Test.txt");

        //featureCount = 1;
    }

    private LinkedHashMap<String, Integer> generateNgrams() {
        LinkedHashMap<String, Integer> indexedNgram = new LinkedHashMap<String, Integer>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\tokenized_Train.txt")));
            String line = "";
            String text = "";
            int num = 0;
            while ((line = read.readLine()) != null) {
                line.trim();
                text += line + " ";
                num++;
            }

            LinkedHashMap<String, Integer> ngramMap = new LinkedHashMap<String, Integer>();

            int count = 1;
            for (int n = 1; n < 2; n++) {

                // Use .isEmpty() instead of .length() == 0
                if (text == null || text.isEmpty()) {
                    throw new IllegalArgumentException("null or empty text");
                }
                String[] words = text.split(" ");
                List<String> list = new ArrayList<String>();

                for (int i = 0; i <= words.length - n; i++) {
                    //StringBuilder keyBuilder = new StringBuilder(words[i].trim());
                    words[i].trim();
                    String ngram = words[i];
                    for (int j = 1; j <= n - 1; j++) {
                        ngram += " " + words[i + j];
                    }
                    //System.out.println(ngram);
                    /*if (ngramMap.containsKey(ngram)) {
                        ngramMap.put(ngram, ngramMap.get(ngram) + 1);
                    } else {
                        ngramMap.put(ngram, 1);
                        indexedNgram.put(ngram, count++);
                        //System.out.println(ngram+" "+count);
                    }*/

                    if (indexedNgram.containsKey(ngram)) {
                    } else {
                        indexedNgram.put(ngram, count++);
                    }

                    //************System.out.println(ngram + ": " + count);
                }
            }

            indexedNgram = sortHashMapByValuesD(indexedNgram);


            setFeatureCount(count);

            System.out.println("Func: " + featureCount);


        } catch (IOException e) {
            System.out.println(e);
        }

        return indexedNgram;
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(LinkedHashMap<String, Integer> indexedNgram, String fileName) {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(fileName)));
            String line = "";
            int count = 0;
            //String text = "";

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                //System.out.println(line);
                //String tokens[] = line.split(" ");
                //trainingFeature.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.add(count, new LinkedHashMap<Integer, Double>());

                //System.out.println(line);

                for (int n = 1; n < 2; n++) {

                    // Use .isEmpty() instead of .length() == 0
                    if (line == null || line.isEmpty()) {
                        throw new IllegalArgumentException("null or empty text");
                    }
                    String[] words = line.split(" ");
                    //List<String> list = new ArrayList<String>();

                    for (int i = 0; i <= words.length - n; i++) {
                        //StringBuilder keyBuilder = new StringBuilder(words[i].trim());
                        words[i].trim();
                        String ngram = words[i];
                        for (int j = 1; j <= n - 1; j++) {
                            ngram += " " + words[i + j];
                        }
                        //System.out.println(ngram);
                        if (indexedNgram.containsKey(ngram)) {
                            //ngramMap.put(ngram, ngramMap.get(ngram) + 1);
                            //trainingFeature.get(count).remove(indexedNgram.get(ngram).intValue());
                            if (featureVector.get(count).containsKey(indexedNgram.get(ngram))) {
                                featureVector.get(count).put(indexedNgram.get(ngram).intValue(), featureVector.get(count).get(indexedNgram.get(ngram)) + 1.0);
                            } else {
                                featureVector.get(count).put(indexedNgram.get(ngram).intValue(), 1.0);
                            }

                        } /*else {
                            //ngramMap.put(ngram, 1);
                            //indexedNgram.put(ngram, count++);
                            trainingFeature.get(count).put(indexedNgram.get(ngram).intValue(), 0.0);
                        }*/
                    }
                }
                count++;
            }


            for (int i = 0; i < featureVector.size(); i++)    //Print the feature values in sorted order
            {

                //System.out.println(trainingFeature.get(i));
                LinkedHashMap<Integer, Double> linkedHashMap = new LinkedHashMap<Integer, Double>();
                Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(featureVector.get(i));
                //System.out.println(sortedMap);

                Set set = sortedMap.entrySet();
                Iterator iterator = set.iterator();
                featureVector.get(i).clear();
                while (iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    featureVector.get(i).put(Integer.parseInt(mentry.getKey().toString()), Double.parseDouble(mentry.getValue().toString()));
                    //linkedHashMap.put(Integer.parseInt(mentry.getKey().toString()), 1.0);
                    //System.out.print("key is: "+ mentry.getKey() + " ");
                    //System.out.println(mentry.getValue());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());

        /*for(int i=0; i<trainingFeature.size(); i++)
        {
            System.out.println();
            for (Map.Entry<Integer,Double> entry : trainingFeature.get(i).entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                // do stuff
            }
        }*/

        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(featureCount);
        return featureCount;
    }

    private void setFeatureCount(int count) {
        this.featureCount = count;
    }

    private LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

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
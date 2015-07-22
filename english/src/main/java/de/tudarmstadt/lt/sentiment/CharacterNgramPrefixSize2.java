package de.tudarmstadt.lt.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by krayush on 15-06-2015.
 */
public class CharacterNgramPrefixSize2 {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    int featureCount;

    CharacterNgramPrefixSize2(String rootDirectory) throws IOException {
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        LinkedHashMap<String, Integer> indexedNgrams = new LinkedHashMap<String, Integer>();
        indexedNgrams = generateCharacterNgrams();

        trainingFeature = generateFeature(indexedNgrams, rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(indexedNgrams, rootDirectory + "\\dataset\\tokenized_Test.txt");

        getTrainingList();
        getTestList();

        //featureCount = 1;
    }

    private LinkedHashMap<String, Integer> generateCharacterNgrams() throws IOException {
        LinkedHashMap<String, Integer> indexedNgram = new LinkedHashMap<String, Integer>();
        BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\tokenized_Train.txt")));
        String line = "";
        String text = "";
        //int num = 0;
        while ((line = read.readLine()) != null) {
            line.trim();
            text += line + " ";
            //num++;
        }

        LinkedHashMap<String, Integer> ngramMap = new LinkedHashMap<String, Integer>();

        int count = 1;

        // Use .isEmpty() instead of .length() == 0
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("null or empty text");
        }
        String[] words = text.split(" ");
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < words.length; i++) {
            //StringBuilder keyBuilder = new StringBuilder(words[i].trim());
            words[i].trim();
            String ngram;
            if (words[i].length() >= 2) {
                ngram = words[i].substring(0, 2);

                //String ngram = words[i].substring(0,2);

                if (!indexedNgram.containsKey(ngram)) {
                    indexedNgram.put(ngram, count++);
                }
            }
            //************System.out.println(ngram + ": " + count);
        }


        indexedNgram = sortHashMapByValuesD(indexedNgram);

        System.out.println(indexedNgram);
        setFeatureCount(count);

        System.out.println("Func: " + featureCount);

        return indexedNgram;
    }


    private List<LinkedHashMap<Integer, Double>> generateFeature(LinkedHashMap<String, Integer> indexedNgram, String fileName) throws IOException {
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
                String[] words = line.split(" ");
                //List<String> list = new ArrayList<String>();

                for (int i = 0; i < words.length; i++) {
                    //StringBuilder keyBuilder = new StringBuilder(words[i].trim());
                    words[i].trim();
                    String ngram;
                    if (words[i].length() >= 2) {
                        ngram = words[i].substring(0, 2);
                        //System.out.println(ngram);
                        if (indexedNgram.containsKey(ngram)) {
                            //ngramMap.put(ngram, ngramMap.get(ngram) + 1);
                            //trainingFeature.get(count).remove(indexedNgram.get(ngram).intValue());
                            featureVector.get(count).put(indexedNgram.get(ngram).intValue(), 1.0);
                        } /*else {
                            //ngramMap.put(ngram, 1);
                            //indexedNgram.put(ngram, count++);
                            trainingFeature.get(count).put(indexedNgram.get(ngram).intValue(), 0.0);
                        }*/
                    }

                /*PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("E:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\ngram_Train" + n + ".txt")));
                write.println(indexedNgram);
                System.out.println(ngramMap.size());
                write.close();*/
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
        /*for(int i=0; i<testFeature.size(); i++)
        {
            System.out.println();
            for (Map.Entry<Integer,Double> entry : testFeature.get(i).entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                // do stuff
            }
        }*/
        return this.testFeature;
    }

    private void setFeatureCount(int count) {
        this.featureCount = count;
    }

    public int getFeatureCount() {
        //System.out.println(featureCount);
        return featureCount;
    }


    private LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap =
                new LinkedHashMap();

        for (Object val : mapValues) {
            for (Object key : mapKeys) {
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

    public static void main(String[] args) throws IOException {
        CharacterNgramPrefixSize2 ob = new CharacterNgramPrefixSize2("D:\\Course\\Semester VII\\Internship\\sentiment");
    }
}

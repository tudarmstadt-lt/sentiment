package de.tudarmstadt.lt.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//package de.tu.darmstadt.lt.ner.util;

public class Ngram {
    /*public static void main(String[] args) {
        Ngram obj = new Ngram();
    }*/

    List<LinkedHashMap<Integer, Double>> trainingFeature;
    int count;

    Ngram() {

        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        generateNgrams();
        count = 1;
    }

    public void generateNgrams() {

        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("/Users/biem/sentiment/dataset/tokenized_Train.txt")));
            String line = "";
            String text = "";
            int num = 0;
            while ((line = read.readLine()) != null) {
                line.trim();
                text += line + " ";
                num++;
            }

            LinkedHashMap<String, Integer> ngramMap = new LinkedHashMap<String, Integer>();
            LinkedHashMap<String, Integer> indexedNgram = new LinkedHashMap<String, Integer>();
            //int count = 1;
            for (int n = 1; n < 5; n++) {

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

                    System.out.println(ngram + ": " + count);
                }

                /*PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("E:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\ngram_Train.txt")));
                for(Map.Entry<String, Integer> entry : indexedNgram.entrySet()){
                    //System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
                    write.println(entry.getKey() +" "+ entry.getValue());
                }
                //write.println(indexedNgram);
                //System.out.println(ngramMap.size());
                write.close();*/
            }

            //System.out.println("ABC  "+count);

            for (int i = 0; i < num; i++) {
                trainingFeature.add(i, new LinkedHashMap<Integer, Double>());
                /*for (int j = 1; j < count; j++) {
                    trainingFeature.get(i).put(j, 0.0);
                }*/
            }
            indexedNgram = sortHashMapByValuesD(indexedNgram);

            generateFeature(indexedNgram);

            /*try {
                PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("E:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\ngram_Train.txt")));
                Iterator it = indexedNgram.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    write.println(pair.getKey() + " = " + pair.getValue());
                }
                //write.println(ngramMap);
                write.close();
            } catch (IOException e) {
                System.out.println(e);
            }*/

            //return list;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void generateFeature(LinkedHashMap<String, Integer> indexedNgram) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("/Users/biem/sentiment/dataset/tokenized_Train.txt")));
            String line = "";
            int count = 0;
            //String text = "";

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                //System.out.println(line);
                //String tokens[] = line.split(" ");
                //trainingFeature.add(count, new LinkedHashMap<Integer, Double>());
                for (int n = 1; n < 5; n++) {

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
                            trainingFeature.get(count).put(indexedNgram.get(ngram).intValue(), 1.0);
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


            for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
            {

                //System.out.println(trainingFeature.get(i));
                LinkedHashMap<Integer, Double> linkedHashMap = new LinkedHashMap<Integer, Double>();
                Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(trainingFeature.get(i));
                //System.out.println(sortedMap);

                Set set = sortedMap.entrySet();
                Iterator iterator = set.iterator();
                trainingFeature.get(i).clear();
                while (iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    trainingFeature.get(i).put(Integer.parseInt(mentry.getKey().toString()), Double.parseDouble(mentry.getValue().toString()));
                    //linkedHashMap.put(Integer.parseInt(mentry.getKey().toString()), 1.0);
                    //System.out.print("key is: "+ mentry.getKey() + " ");
                    //System.out.println(mentry.getValue());
                }

                //System.out.println(linkedHashMap);
                //System.out.println(trainingFeature.get(i));


                /*for(int j=0; j<sortedMap.size(); j++)
                {
                    trainingFeature.get(i).put(sortedMap.)
                }
                System.out.println(trainingFeature.get(i));*/
                /*for(int j=1; j<=trainingFeature.get(i).size(); j++)
                {
                    if(trainingFeature.get(i).get(j).intValue() == 1)
                    {
                        System.out.print(j+" ");
                    }
                }*/
                //System.out.println();
            }

            /*for (int i = 0; i < trainingFeature.size(); i++) {
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
        return this.count;
    }

    private static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
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
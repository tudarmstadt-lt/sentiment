package de.tudarmstadt.lt.sentiment;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class POS {


    List<LinkedHashMap<Integer, Double>> listOfMaps;

    POS() {
        this.listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();
        generateFeature();
    }

    private void generateFeature() {
        //File file =

        String line = null;
        try {
            PrintWriter write = new PrintWriter("/Users/biem/sentiment/dataset/raw_POS.txt");
            BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/biem/sentiment/dataset/Train_Restaurants_Contextual_Cleansed.txt")));
            MaxentTagger tagger = new MaxentTagger("/Users/biem/sentiment/resources/stanford-postagger-full-2015-04-20/models/english-left3words-distsim.tagger");
            //HashMap<String, Double> scoreMap = new HashMap<String, Double>();
            while ((line = reader.readLine()) != null) {
                String tagged = tagger.tagString(line);

                //String[] str = line.split("\\|");

                //String tagged = tagger.tagString(str[3]);
                write.println(tagged);
                //System.out.println(line);
                //System.out.println(tagged);
                //scoreMap.put(str[0], Double.parseDouble(str[1]));
                //System.out.println(str[1]);
            }
            reader.close();
            write.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        generateFeatureHelper();
    }

    private void generateFeatureHelper() {
        try {
            HashMap<String, Integer> posTags = new HashMap<String, Integer>();
            posTags.put("CC", 1);
            posTags.put("CD", 2);
            posTags.put("DT", 3);
            posTags.put("EX", 4);
            posTags.put("FW", 5);
            posTags.put("IN", 6);
            posTags.put("JJ", 7);
            posTags.put("JJR", 8);
            posTags.put("JJS", 9);
            posTags.put("LS", 10);
            posTags.put("MD", 11);
            posTags.put("NN", 12);
            posTags.put("NNS", 13);
            posTags.put("NNP", 14);
            posTags.put("NNPS", 15);
            posTags.put("PDT", 16);
            posTags.put("POS", 17);
            posTags.put("PRP", 18);
            posTags.put("PRP$", 19);
            posTags.put("RB", 20);
            posTags.put("RBR", 21);
            posTags.put("RBS", 22);
            posTags.put("RP", 23);
            posTags.put("SYM", 24);
            posTags.put("TO", 25);
            posTags.put("UH", 26);
            posTags.put("VB", 27);
            posTags.put("VBD", 28);
            posTags.put("VBG", 29);
            posTags.put("VBN", 30);
            posTags.put("VBP", 31);
            posTags.put("VBZ", 32);
            posTags.put("WDT", 33);
            posTags.put("WP", 34);
            posTags.put("WP$", 35);
            posTags.put("WRB", 36);


            /*for(int i=0; i<posTags.size(); i++)
            {
                trainingFeature.get(i).put(i+1, 0.0);
            }*/

            BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/biem/sentiment/dataset/raw_POS.txt")));
            PrintWriter write = new PrintWriter("/Users/biem/sentiment/dataset/tokenized_Train.txt");

            String line = null;

            int count = 0;
            while ((line = reader.readLine()) != null) {
                String tokenized = "";
                double[] arr = new double[36];
                /*for(int i=0; i<36; i++)
                {
                    arr[i] = 0.0;
                }*/

                String[] str = line.split("[ \t\n\\x0B\f\r]+");
                for (int i = 0; i < str.length; i++) {
                    if (str[i] != null) {
                        String[] pos = str[i].split("_");
                        tokenized += pos[0] + " ";
                        //System.out.println(pos[1]);
                        if (posTags.containsKey(pos[1])) {
                            //System.out.print(posTags.get(pos[1]) - 1 + "  **  ");
                            arr[posTags.get(pos[1]) - 1]++;
                        }
                    }
                }

                write.println(tokenized);

                listOfMaps.add(count, new LinkedHashMap<Integer, Double>());
                for (int i = 0; i < 36; i++) {

                    listOfMaps.get(count).put(i + 1, arr[i]);
                }
                count++;
                //System.out.println();

            }
            write.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        /*for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
        {
            //System.out.println(trainingFeature.get(i).size());
            System.out.println(trainingFeature.get(i));
        }*/
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        return this.listOfMaps;
    }

    public int getFeatureCount() {
        return listOfMaps.get(0).size();
    }
    /*public static void main(String[] args)
    {
        String a = "I like watching movies... @abc";
        MaxentTagger tagger =  new MaxentTagger("E:\\COURSE\\Semester VII\\Internship\\sentiment\\resources\\stanford-postagger-full-2015-04-20\\models\\english-left3words-distsim.tagger");
        String tagged = tagger.tagString(a);
        System.out.println(tagged);
    }*/
}

package de.tudarmstadt.lt.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by krayush on 15-06-2015.
 */
public class ABSALaptopsPairs {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    ABSALaptopsPairs(String rootDirectory)throws IOException{
        this.rootDirectory = rootDirectory;
        trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\Train_Laptops_Cleansed.txt");
        //testFeature = generateFeature(rootDirectory + "\\dataset\\Test_Restaurants_Cleansed.txt");

        getTrainingList();
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName)throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        LinkedHashMap<String, Integer> entityMap = new LinkedHashMap<String, Integer>();
        /*entityMap.put("RESTAURANT#GENERAL",1);
        entityMap.put("SERVICE#GENERAL",2);
        entityMap.put("FOOD#QUALITY",3);
        entityMap.put("FOOD#STYLE_OPTIONS",4);
        entityMap.put("DRINKS#STYLE_OPTIONS",5);
        entityMap.put("DRINKS#PRICES",6);

        entityMap.put("RESTAURANT#PRICES",7);
        entityMap.put("AMBIENCE#GENERAL",8);
        entityMap.put("RESTAURANT#MISCELLANEOUS",9);
        entityMap.put("FOOD#PRICES",10);
        entityMap.put("LOCATION#GENERAL",11);
        entityMap.put("DRINKS#QUALITY",12);
        entityMap.put("FOOD#GENERAL",13);*/

        String line;
        int count=1;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split("\\|");
            //String entity[] = words[5].split("#");
            if(!entityMap.containsKey(words[4]))
            {
                entityMap.put(words[4],count);
                count++;
            }
        }

        for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println('"'+key+'"'+","+value);
            //weightFile.write(key+": "+value*1000+"\n");
            // now work with key and value...
        }

        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        /*for(int i=0; i<trainingFeature.size(); i++)
        {
            System.out.println(trainingFeature.get(i));
        }*/
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        //System.out.println(trainingFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(trainingFeature.get(0).size());
        return 11;
    }

    public static void main(String[] args)throws IOException
    {
        ABSALaptopsPairs ef = new ABSALaptopsPairs("D:\\Course\\Semester VII\\Internship\\sentiment");
    }
}

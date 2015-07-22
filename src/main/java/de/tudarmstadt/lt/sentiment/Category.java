package de.tudarmstadt.lt.sentiment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Category {

    /*public static void main(String[] args)
    {
        Category obj = new Category();
    }*/

    List<LinkedHashMap<Integer, Double>> trainingFeature;

    Category() {

        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        generateFeature();
    }

    private  void generateFeature()
    {
        HashMap<String, Integer> entityTags = new HashMap<String, Integer>();
        entityTags.put("LAPTOP", 1);
        entityTags.put("DISPLAY", 2);
        entityTags.put("KEYBOARD", 3);
        entityTags.put("MOUSE", 4);
        entityTags.put("MOTHERBOARD", 5);
        entityTags.put("CPU", 6);
        entityTags.put("FANS_COOLING", 7);
        entityTags.put("PORTS", 8);
        entityTags.put("MEMORY", 9);
        entityTags.put("POWER_SUPPLY", 10);
        entityTags.put("OPTICAL_DRIVES", 12);
        entityTags.put("BATTERY", 13);
        entityTags.put("GRAPHICS", 14);
        entityTags.put("HARD_DISC", 15);
        entityTags.put("MULTIMEDIA_DEVICES", 16);
        entityTags.put("HARDWARE", 17);
        entityTags.put("SOFTWARE", 18);
        entityTags.put("OS", 19);
        entityTags.put("WARRANTY", 20);
        entityTags.put("SHIPPING", 21);
        entityTags.put("SUPPORT", 22);
        entityTags.put("COMPANY", 23);

        HashMap<String, Integer> attributeTags = new HashMap<String, Integer>();
        attributeTags.put("GENERAL", 1);
        attributeTags.put("PRICE", 2);
        attributeTags.put("QUALITY", 3);
        attributeTags.put("OPERATION_PERFORMANCE", 4);
        attributeTags.put("USABILITY", 5);
        attributeTags.put("DESIGN_FEATURES", 6);
        attributeTags.put("PORTABILITY", 7);
        attributeTags.put("CONNECTIVITY", 8);
        attributeTags.put("MISCELLANEOUS", 9);


        try
        {
            BufferedReader read = new BufferedReader(new FileReader(new File("E:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Train_Laptops_Cleansed.txt")));
            String line = "";

            int count=0;
            while((line = read.readLine()) != null)
            {
                String words[] = line.split("\\|");
                String labels[] = words[4].split("#");

                //System.out.println(labels[0]+": "+labels[1]);

                trainingFeature.add(count, new LinkedHashMap<Integer, Double>());
                trainingFeature.get(count).put(1, Double.parseDouble(entityTags.get(labels[0])+"."+attributeTags.get(labels[1])));
                count++;
            }
            read.close();

            /*for(int i=0; i<trainingFeature.size(); i++)
            {
                System.out.println(trainingFeature.get(i));
            }*/

        }catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        return this.trainingFeature;
    }
}

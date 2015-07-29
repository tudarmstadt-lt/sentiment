package de.tudarmstadt.lt.sentiment;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityLaptops {

    private void generateTestLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Test_Laptops_Cleansed.txt")));
            BufferedReader pairs = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\ABSALaptopsPair.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\testLabels.txt")));

            LinkedHashMap<String, Integer> polarity = new LinkedHashMap<String, Integer>();
            int count=1;
            while((line=pairs.readLine())!=null)
            {
                line = line.trim();
                //System.out.println(line);
                polarity.put(line, count);
                count++;
            }


            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                //System.out.println(words[4]+polarity.get(words[4]));
                if(!polarity.containsKey(words[4]))
                {
                    write.println(100);
                }
                else
                {
                    write.println(polarity.get(words[4]));
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void generateTrainingLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Train_Laptops_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\trainingLabels.txt")));
            BufferedReader pairs = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\ABSALaptopsPair.txt")));

            LinkedHashMap<String, Integer> polarity = new LinkedHashMap<String, Integer>();
            int count=1;
            while((line=pairs.readLine())!=null)
            {
                line = line.trim();
                polarity.put(line, count);
                count++;
            }
            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                write.println(polarity.get(words[4]));
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ABSAPolarityLaptops obj = new ABSAPolarityLaptops();
        obj.generateTrainingLabels();
        obj.generateTestLabels();
        //obj.generateTestLabels();
    }

}

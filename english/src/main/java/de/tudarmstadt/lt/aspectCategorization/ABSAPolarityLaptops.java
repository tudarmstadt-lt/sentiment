package de.tudarmstadt.lt.aspectCategorization;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityLaptops {

    private void generateTestLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Laptops_Test_ABSA.txt")));
            BufferedReader pairs = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\LaptopsPairs.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\testLabels.txt")));

            LinkedHashMap<String, Integer> polarity = new LinkedHashMap<String, Integer>();
            int count = 1;
            while ((line = pairs.readLine()) != null) {
                line = line.trim();
                //System.out.println(line);
                polarity.put(line, count);
                count++;
            }


            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                //System.out.println(words[4]+polarity.get(words[4]));
                if (!polarity.containsKey(words[4])) {
                    write.println(1000);
                } else {
                    write.println(polarity.get(words[4]));
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void generateTrainingLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Laptops_Train_ABSA.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\trainingLabels.txt")));
            BufferedReader pairs = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\LaptopsPairs.txt")));

            LinkedHashMap<String, Integer> polarity = new LinkedHashMap<String, Integer>();
            int count = 1;
            while ((line = pairs.readLine()) != null) {
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
        String rootDirectory = System.getProperty("user.dir");
            /*File file = new File("rootDir.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                rootDirectory = line;
                System.out.println("Root Directory is: " + rootDirectory);
            }*/
        ABSAPolarityLaptops obj = new ABSAPolarityLaptops();
        obj.generateTrainingLabels(rootDirectory);
        obj.generateTestLabels(rootDirectory);
        //obj.generateTestLabels();

    }

}

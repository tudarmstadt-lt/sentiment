package de.tudarmstadt.lt.aspectCategorization;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityLaptops {

    String rootDirectory;

    ABSAPolarityLaptops(int option, String trainFileName, String testFileName) {
        rootDirectory = System.getProperty("user.dir");
        mainFunction(option, trainFileName, testFileName);
    }

    private void generateTestLabels(String dataFile, String labelFile) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            BufferedReader pairs = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\LaptopsPairs.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));

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

    private void generateTrainingLabels(String dataFile, String labelFile) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));
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

    private void mainFunction(int option, String trainingFileName, String testFileName) {

        //String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        //System.out.println(args.length);
        //String rootDirectory = System.getProperty("user.dir");;
        //SetPolarityLabels this = new SetPolarityLabels();
        this.generateTrainingLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + trainingFileName, rootDirectory + "\\dataset\\trainingLabels.txt");
        if (option == 1) {
            this.generateTestLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + trainingFileName, rootDirectory + "\\dataset\\testLabels.txt");
        } else if (option == 2) {

        } else if (option == 3) {
            this.generateTestLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + testFileName, rootDirectory + "\\dataset\\testLabels.txt");
            //this.generateTestLabels(rootDirectory + "\\dataset\\Gold Set\\HI_Test_Gold.txt", rootDirectory + "\\dataset\\testLabels.txt");
        }

    }

}

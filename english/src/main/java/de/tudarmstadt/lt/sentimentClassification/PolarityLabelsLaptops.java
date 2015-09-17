package de.tudarmstadt.lt.sentimentClassification;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class PolarityLabelsLaptops {

    String rootDirectory;
    PolarityLabelsLaptops(int option, String trainFileName, String testFileName)
    {
        rootDirectory = System.getProperty("user.dir");
        mainFunction(option, trainFileName, testFileName);
    }

    private void generateTestLabels(String dataFile, String labelFile ) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words[5].compareTo("negative") == 0) {          //words[5] for laptop set
                    write.println("-1");
                } else if (words[5].compareTo("positive") == 0) {
                    write.println("1");
                } else if (words[5].compareTo("neutral") == 0) {
                    write.println("0");
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

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words[5].compareTo("negative") == 0) {
                    write.println("-1");
                } else if (words[5].compareTo("positive") == 0) {
                    write.println("1");
                } else if (words[5].compareTo("neutral") == 0) {
                    write.println("0");
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //public static void main(String[] args) {
    private void mainFunction(int option, String trainingFileName, String testFileName)
    {
        this.generateTrainingLabels(rootDirectory+"\\dataset\\dataset_sentimentClassification\\"+ trainingFileName, rootDirectory + "\\dataset\\trainingLabels.txt");
        if(option == 1)
        {
            this.generateTestLabels(rootDirectory+"\\dataset\\dataset_sentimentClassification\\"+trainingFileName, rootDirectory + "\\dataset\\testLabels.txt");
        }
        else if(option == 2)
        {

        }
        else if(option == 3)
        {
            this.generateTestLabels(rootDirectory+"\\dataset\\dataset_sentimentClassification\\"+testFileName, rootDirectory + "\\dataset\\testLabels.txt");
            //this.generateTestLabels(rootDirectory + "\\dataset\\Gold Set\\HI_Test_Gold.txt", rootDirectory + "\\dataset\\testLabels.txt");
        }

        //this.generateTrainingLabels(rootDirectory);
        //this.generateTestLabels(rootDirectory);
        //obj.generateTestLabels();

    }

}

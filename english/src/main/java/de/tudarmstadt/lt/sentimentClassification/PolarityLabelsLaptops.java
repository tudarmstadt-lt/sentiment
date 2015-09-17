package de.tudarmstadt.lt.sentimentClassification;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class PolarityLabelsLaptops {

    private void generateTestLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory+"\\dataset\\dataset_sentimentClassification\\Test_Laptops_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory+"\\dataset\\testLabels.txt")));

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

    private void generateTrainingLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory+"\\dataset\\dataset_sentimentClassification\\Train_Laptops_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory+"\\dataset\\trainingLabels.txt")));

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

    public static void main(String[] args) {
        String rootDirectory = System.getProperty("user.dir");
            /*File file = new File("rootDir.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null)
            {
                rootDirectory = line;
                System.out.println("Roor Directory is: "+rootDirectory);
            }*/

        //rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\english";
        PolarityLabelsLaptops obj = new PolarityLabelsLaptops();
        obj.generateTrainingLabels(rootDirectory);
        obj.generateTestLabels(rootDirectory);
        //obj.generateTestLabels();

    }

}

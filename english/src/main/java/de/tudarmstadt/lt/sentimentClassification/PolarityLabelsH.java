package de.tudarmstadt.lt.sentimentClassification;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class PolarityLabelsH {

    private void generateTestLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Test_Hotels_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\testLabels.txt")));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words.length == 4) {
                    continue;
                }
                if (words[6].compareTo("negative") == 0) {          //words[5] for laptop set
                    write.println("-1");
                } else if (words[6].compareTo("positive") == 0) {
                    write.println("1");
                } else if (words[6].compareTo("neutral") == 0) {
                    write.println("0");
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*private void generateTrainingLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Train_Laptops_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\trainingLabels.txt")));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words.length == 4) {
                    continue;
                }
                if (words[5].compareTo("negative") == 0) {
                    write.println("0");
                } else if (words[5].compareTo("positive") == 0) {
                    write.println("1");
                } else if (words[5].compareTo("neutral") == 0) {
                    write.println("2");
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }*/

    public static void main(String[] args) {
        PolarityLabelsH obj = new PolarityLabelsH();
        //obj.generateTrainingLabels();
        obj.generateTestLabels();
        //obj.generateTestLabels();
    }

}

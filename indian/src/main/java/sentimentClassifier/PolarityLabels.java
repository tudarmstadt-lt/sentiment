package sentimentClassifier;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class PolarityLabels {

    private void generateTestLabels(String dataFile, String labelFile) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words[1].compareTo("-1") == 0) {
                    write.println("-1");
                } else if (words[1].compareTo("1") == 0) {
                    write.println("1");
                } else if (words[1].compareTo("0") == 0) {
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
                if (words[1].compareTo("-1") == 0) {
                    write.println("-1");
                } else if (words[1].compareTo("1") == 0) {
                    write.println("1");
                } else if (words[1].compareTo("0") == 0) {
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
        String root = "D:\\Course\\Semester VII\\Internship\\IndianSentiment";
        PolarityLabels obj = new PolarityLabels();
        obj.generateTrainingLabels(root+"\\dataset\\hindiCleansedTraining.txt", root+"\\dataset\\trainingLabels.txt");
        obj.generateTestLabels(root+"\\dataset\\hindiCleansedTraining.txt", root+"\\dataset\\testLabels.txt");
        //obj.generateTestLabels();
    }

}

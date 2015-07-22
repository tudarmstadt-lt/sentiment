package de.tudarmstadt.lt.sentimentClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by krayush on 12-06-2015.
 */
public class calculateAccuracy {
    public static void main(String[] args) throws IOException {
        final String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment";
        File file1 = new File(rootDirectory + "\\dataset\\testLabels.txt");
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));

        File file2 = new File(rootDirectory + "\\dataset\\predictedRestaurantsLabels.txt");
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        String line;
        int count = 0, num = 0;
        while ((line = reader1.readLine()) != null) {
            num++;

            if (Double.parseDouble(line) == Double.parseDouble(reader2.readLine())) {
                count++;
                //System.out.println(num);
            }
            else
            {
                System.out.println(num);
            }
        }
        System.out.println(count);
    }
}

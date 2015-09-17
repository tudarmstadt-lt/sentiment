package sentimentClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by krayush on 12-06-2015.
 */
public class CalculateAccuracyBengali {
    public static void main(String[] args) throws IOException {
        final String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        File file1 = new File(rootDirectory + "\\dataset\\testLabels.txt");
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));

        File file2 = new File(rootDirectory + "\\dataset\\predictedLabels.txt");
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        String line;
        int count = 0, num = 0;
        int accPos = 0, accNeg = 0, accNeu = 0;
        while ((line = reader1.readLine()) != null) {
            num++;

            if (Double.parseDouble(line) == Double.parseDouble(reader2.readLine())) {
                count++;
                //System.out.println(num);
            } else {
                if (Double.parseDouble(line) == 1.0) {
                    accPos++;
                } else if (Double.parseDouble(line) == -1.0) {
                    accNeg++;
                } else if (Double.parseDouble(line) == 0.0) {
                    accNeu++;
                }
                //System.out.println(num);
            }
        }
        System.out.println(count);
        //double posAcc = ;
        System.out.println((277 - accPos) / 277.0);
        System.out.println((354 - accNeg) / 354.0);
        System.out.println((368 - accNeu) / 368.0);
    }
}

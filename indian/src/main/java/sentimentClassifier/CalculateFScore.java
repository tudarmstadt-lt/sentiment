package sentimentClassifier;

import java.io.*;

/**
 * Created by krayush on 27-07-2015.
 */
public class CalculateFScore {

    CalculateFScore()throws IOException
    {
        mainFunction();
    }

    //public static void main(String[] args) throws IOException {
    private void mainFunction() throws IOException
    {
        final String rootDirectory = System.getProperty("user.dir");
        File file1 = new File(rootDirectory + "\\dataset\\testLabels.txt");
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));

        File file2 = new File(rootDirectory + "\\dataset\\predictedLabels.txt");
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        /*BufferedReader testreader = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory+"\\dataset\\tokenized_Hindi_Test.txt"), "UTF-8"));
        Writer countWriter = new OutputStreamWriter(new FileOutputStream(rootDirectory+"\\dataset\\Gold Set\\hindiError.txt"), "UTF-8");
        //BufferedWriter cfout = new BufferedWriter(countWriter);
        //OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(rootDirectory+"\\dataset\\Gold Set\\hindiError.txt"), true), "UTF-8");
        BufferedWriter fbw = new BufferedWriter(countWriter);*/

        double arr[][] = new double[3][3];



        String line;
        double total=0, correct=0;
        //fbw.write("predLabel"+"|"+"actualLabel"+"|"+"test"+"\n");*/
        while ((line = reader1.readLine()) != null) {
            double actualLabel = Double.parseDouble(line);
            double predLabel = Double.parseDouble(reader2.readLine());
            //String test = testreader.readLine();

            /*if(actualLabel != predLabel)
            {
                fbw.write(predLabel+"|"+actualLabel+"|"+test+"\n");
            }*/
            total++;
            if (actualLabel == 1.0) {
                if (predLabel == 1.0) {
                    correct++;
                    arr[0][0]++;
                } else if (predLabel == -1.0) {
                    arr[0][1]++;
                } else if (predLabel == 0.0) {
                    arr[0][2]++;
                }
            } else if (actualLabel == -1.0) {
                if (predLabel == -1.0) {
                    correct++;
                    arr[1][1]++;
                } else if (predLabel == 1.0) {
                    arr[1][0]++;
                } else if (predLabel == 0.0) {
                    arr[1][2]++;
                }
            } else if (actualLabel == 0.0) {
                if (predLabel == 0.0) {
                    correct++;
                    arr[2][2]++;
                } else if (predLabel == 1.0) {
                    arr[2][0]++;
                } else if (predLabel == -1.0) {
                    arr[2][1]++;
                }
            }
        }

        //fbw.close();*/
        System.out.println("Printing the confusion matrix: Positive | Negative | Neutral");
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + "  ");
            }
        }

        System.out.println("\n");

        //POS
        double pp = arr[0][0] / (arr[0][0] + arr[1][0] + arr[2][0]);
        double pr = arr[0][0] / (arr[0][0] + arr[0][1] + arr[0][2]);
        double pf = (2 * pp * pr) / (pp + pr);

        //NEG
        double np = arr[1][1] / (arr[1][1] + arr[0][1] + arr[2][1]);
        double nr = arr[1][1] / (arr[1][0] + arr[1][1] + arr[1][2]);
        double nf = (2 * np * nr) / (np + nr);

        //NEUTRAL
        double neup = arr[2][2] / (arr[2][2] + arr[0][2] + arr[1][2]);
        double neur = arr[2][2] / (arr[2][0] + arr[2][1] + arr[2][2]);
        double neuf = (2 * neup * neur) / (neup + neur);

        System.out.println("Pos Precision: " + pp + ", Pos Recall: " + pr + ", Pos FScore: " + pf);
        System.out.println("Neg Precision: " + np + ", Neg Recall: " + nr + ", Neg FScore: " + nf);
        System.out.println("Neu Precision: " + neup + ", Neu Recall: " + neur + ", Neu FScore: " + neuf);

        System.out.println();
        System.out.println("Avg FScore: " + (pf + nf + neuf) / 3);
        System.out.println("Overall Accuracy: "+correct/total);

        System.out.println();


    }

}

package sentimentClassifier;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class POSHindi {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    POSHindi(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\posHindiTrainTags.txt", rootDirectory + "\\dataset\\POS_Hindi_Train.txt", rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\posHindiTestTags.txt", rootDirectory + "\\dataset\\POS_Hindi_Test.txt", rootDirectory + "\\dataset\\tokenized_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String rawDataset, String tokenizedDataset, String POSFile) throws IOException {
        String line = null;
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();

        Writer writer1 = new OutputStreamWriter(new FileOutputStream(POSFile), "UTF-8");
        BufferedWriter fbw1 = new BufferedWriter(writer1);

        Writer writer2 = new OutputStreamWriter(new FileOutputStream(tokenizedDataset), "UTF-8");
        BufferedWriter fbw2 = new BufferedWriter(writer2);

        //PrintWriter write1 = new PrintWriter(POSFile);
        //PrintWriter write2 = new PrintWriter(tokenizedDataset);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rawDataset), "UTF-8"));
        HashMap<String, Integer> posTags = new HashMap<String, Integer>();

        posTags.put("NN", 1);
        posTags.put("NST", 1);
        posTags.put("NNP", 1);
        posTags.put("PRP", 2);
        posTags.put("DEM", 2);
        posTags.put("VM", 3);
        posTags.put("VAUX", 3);
        posTags.put("JJ", 4);
        posTags.put("RB", 5);
        posTags.put("PSP", 6);
        posTags.put("RP", 7);
        posTags.put("CC", 8);
        posTags.put("WQ", 9);
        posTags.put("QF", 10);
        posTags.put("QC", 11);
        posTags.put("QO", 12);
        posTags.put("CL", 13);
        posTags.put("INTF", 14);
        posTags.put("INJ", 15);
        posTags.put("NEG", 16);
        posTags.put("UT", 17);
        posTags.put("SYM", 18);

        posTags.put("XC", 19);

        /*posTags.put("NN", 1);
        posTags.put("NST", 2);
        posTags.put("NNP", 3);
        posTags.put("PRP", 4);
        posTags.put("DEM", 5);
        posTags.put("VM", 6);
        posTags.put("VAUX", 7);
        posTags.put("JJ", 8);
        posTags.put("RB", 9);
        posTags.put("PSP", 10);
        posTags.put("RP", 11);
        posTags.put("CC", 12);
        posTags.put("WQ", 13);
        posTags.put("QF", 14);
        posTags.put("QC", 15);
        posTags.put("QO", 16);
        posTags.put("CL", 17);
        posTags.put("INTF", 18);
        posTags.put("INJ", 1);
        posTags.put("NEG", 16);
        posTags.put("UT", 17);
        posTags.put("SYM", 18);

        posTags.put("XC", 19);*/

        /*posTags.put("RDP", 20);
        posTags.put("ECH", 21);
        posTags.put("UNK", 22);*/


        int count = 0;
        String tokenized = "";
        double[] arr = new double[19];
        while ((line = reader.readLine()) != null) {
            if (line.compareToIgnoreCase("DONEEOF") == 0) {
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                /*for (int i = 0; i < 19; i++) {
                    if (arr[i] != 0) {
                        featureVector.get(count).put(i + 1, arr[i]);
                    }
                }*/

                //System.out.println(featureVector.get(count));
                count++;
                fbw1.write(tokenized + "\n");
                fbw2.write("\n");
                tokenized = "";
                arr = new double[19];
                continue;
            }


                /*for(int i=0; i<36; i++)
                {
                    arr[i] = 0.0;
                }*/

            String[] str = line.split("[ \t\n\\x0B\f\r]+");
            if (str.length >= 4) {
                if (posTags.containsKey(str[2])) {
                    //System.out.print(posTags.get(pos[1]) - 1 + "  **  ");
                    arr[posTags.get(str[2]) - 1]++;
                }
                tokenized += str[0] + " ";
                fbw2.write(str[0] + "_" + str[2] + " ");
            }


        }

        //System.out.println();

        reader.close();
        fbw1.close();
        fbw2.close();
        return featureVector;
        /*for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
        {
            //System.out.println(trainingFeature.get(i).size());
            System.out.println(trainingFeature.get(i));
        }*/
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        //System.out.println(trainingFeature.size());
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        //System.out.println(trainingFeature.size());
        return this.testFeature;
    }

    public int getFeatureCount() {
        return 1;
    }

    public static void main(String[] args) throws IOException {
        POSHindi ob = new POSHindi("D:\\Course\\Semester VII\\Internship\\sentiment\\indian");
    }
}

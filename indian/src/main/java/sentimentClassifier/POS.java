package sentimentClassifier;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class POS {


    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;

    POS(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\posTrainTags.txt", rootDirectory + "\\dataset\\tokenizedPOS_Train.txt", rootDirectory + "\\dataset\\raw_POS_Train.txt");
        //testFeature = generateFeature(rootDirectory + "\\dataset\\Test_Restaurants_Contextual_Cleansed.txt", rootDirectory + "\\dataset\\tokenized_Test.txt", rootDirectory + "\\dataset\\raw_POS_Test.txt");
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
        posTags.put("INJ", 19);
        posTags.put("NEG", 20);
        posTags.put("UT", 21);
        posTags.put("SYM", 22);

        posTags.put("", 23);

        posTags.put("RDP", 24);
        posTags.put("ECH", 25);
        posTags.put("UNK", 26);
            /*posTags.put("JJ", 1);
            posTags.put("JJR", 1);
            posTags.put("JJS", 1);

            posTags.put("NN", 2);
            posTags.put("NNS", 2);
            posTags.put("NNP", 2);
            posTags.put("NNPS", 2);

            posTags.put("RB", 3);
            posTags.put("RBR", 3);
            posTags.put("RBS", 3);

            posTags.put("VB", 4);
            posTags.put("VBD", 4);
            posTags.put("VBG", 4);
            posTags.put("VBN", 4);
            posTags.put("VBP", 4);
            posTags.put("VBZ", 4);*/

        int count = 0;
        String tokenized = "";
        double[] arr = new double[26];
        while ((line = reader.readLine()) != null) {
            if (line.compareToIgnoreCase("DONEEOF") == 0) {
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                for (int i = 0; i < 26; i++) {
                    if (arr[i] != 0) {
                        featureVector.get(count).put(i + 1, arr[i]);
                    }
                }
                System.out.println(featureVector.get(count));
                count++;
                fbw1.write(tokenized + "\n");
                fbw2.write("\n");
                tokenized = "";
                arr = new double[26];
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
        return 26;
    }

    public static void main(String[] args) throws IOException {
        POS ob = new POS("D:\\Course\\Semester VII\\Internship\\IndianSentiment");
    }
}

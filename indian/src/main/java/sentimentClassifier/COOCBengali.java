package sentimentClassifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by krayush on 23-07-2015.
 */
public class COOCBengali {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    int featureCount;

    COOCBengali(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        //testFeature = generateFeature(rootDirectory + "\\dataset\\Test_Restaurants_Contextual_Cleansed.txt", rootDirectory + "\\dataset\\tokenized_Test.txt", rootDirectory + "\\dataset\\raw_POS_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        LinkedHashMap<String, Double> lexicon = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> lexiconDT = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> lexiconCOOC = new LinkedHashMap<String, Double>();
        try {
            BufferedReader readLexicon = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\COOCPolarityBengali.txt"), "UTF-8"));
            String line = "";
            while ((line = readLexicon.readLine()) != null) {
                String tokens[] = line.split("\\|");
                if (lexicon.containsKey(tokens[0])) {
                    System.out.println("Error");
                } else {
                    lexicon.put(tokens[0], Double.parseDouble(tokens[1]));
                }
            }
            System.out.println(lexicon.size());
            readLexicon.close();

            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            int count = 0;
            //String text = "";

            while ((line = read.readLine()) != null) {
                String tokens[] = line.split(" ");
                double pos = 0, neg = 0, neu = 0;
                double strongPos = 0, strongNeg = 0;
                double totalDT = 0, totalCOOC = 0;
                for (int i = 0; i < tokens.length; i++) {
                    double val;
                    double p1;
                    double p2;
                    if (lexicon.containsKey(tokens[i])) {
                        val = lexicon.get(tokens[i]);
                        /*totalDT += lexiconDT.get(tokens[i]);
                        totalCOOC += lexiconCOOC.get(tokens[i]);

                        p1 = lexiconDT.get(tokens[i]);
                        p2 = lexiconCOOC.get(tokens[i]);*/

                        //System.out.println(p1 + " " + p2);

                        if (val > 0) {
                            pos++;
                            //System.out.println(p1+" "+p2);
                            /*if (p1 >= 0.5 && p2 >= 0.5) {
                                strongPos++;
                            }*/
                        } else if (val < 0) {
                            //if()
                            neg++;
                            //neg++;
                            //System.out.println(p1+" "+p2);
                            /*if (p1 <= -0.5 && p2 <= -0.5) {
                                strongNeg++;
                            }*/
                        } else if (val == 0) {
                            neu++;
                        }
                        //else if
                    }
                }
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.get(count).put(1, pos);
                featureVector.get(count).put(2, neg);
                //featureVector.get(count).put(2, neu);

                //featureVector.get(count).put(1, strongPos);
                //featureVector.get(count).put(2, strongNeg);
                //featureVector.get(count).put(3, totalDT);
                //featureVector.get(count).put(4, totalCOOC);

                System.out.println(featureVector.get(count));
                featureCount = featureVector.get(count).size();
                count++;
            }
            read.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return featureVector;
    }

    public List<LinkedHashMap<Integer, Double>> getTrainingList() {
        return this.trainingFeature;
    }

    public List<LinkedHashMap<Integer, Double>> getTestList() {
        return this.testFeature;
    }

    public int getFeatureCount() {
        //System.out.println(featureCount);
        return featureCount;
    }

    private void setFeatureCount(int count) {
        this.featureCount = count;
    }

    public static void main(String[] args) throws IOException {
        COOCBengali ob = new COOCBengali("D:\\Course\\Semester VII\\Internship\\sentiment\\indian");
    }
}

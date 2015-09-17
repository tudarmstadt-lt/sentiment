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
public class DTCOOCLexiconHindi {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    int featureCount;

    DTCOOCLexiconHindi(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        LinkedHashMap<String, Integer> lexicon = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Double> lexiconDT = new LinkedHashMap<String, Double>();
        LinkedHashMap<String, Double> lexiconCOOC = new LinkedHashMap<String, Double>();
        try {
            BufferedReader readLexicon = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\DT_COOCHindiLexicon.txt"), "UTF-8"));
            String line = "";
            while ((line = readLexicon.readLine()) != null) {
                String tokens[] = line.split("\\|");
                if (lexicon.containsKey(tokens[0])) {
                    System.out.println("Error");
                } else {
                    lexicon.put(tokens[0], Integer.parseInt(tokens[3]));
                    lexiconDT.put(tokens[0], Double.parseDouble(tokens[2]));
                    lexiconCOOC.put(tokens[0], Double.parseDouble(tokens[1]));
                }
            }
            readLexicon.close();

            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            int count = 0;
            int p = 0;
            int n = 0;
            int ne = 0;
            //String text = "";

            while ((line = read.readLine()) != null) {
                String tokens[] = line.split(" ");
                double pos = 0, neg = 0, neu = 0;
                double strongPos = 0, strongNeg = 0;
                double totalDT = 0, totalCOOC = 0;
                double posScoreDT = 0, negScoreDT = 0;
                double posScoreCOOC = 0, negScoreCOOC = 0;
                for (int i = 0; i < tokens.length; i++) {
                    int val = -5;  //Dummy value not in between -1 to 1
                    double p1;
                    double p2;
                    if (lexicon.containsKey(tokens[i])) {
                        val = lexicon.get(tokens[i]);
                        totalDT += lexiconDT.get(tokens[i]);
                        totalCOOC += lexiconCOOC.get(tokens[i]);

                        p1 = lexiconDT.get(tokens[i]);
                        p2 = lexiconCOOC.get(tokens[i]);

                        if (p1 > 0) {
                            posScoreDT += p1;
                        } else if (p1 < 0) {
                            negScoreDT += p1;
                        }

                        if (p2 > 0) {
                            posScoreCOOC += p2;
                        } else if (p2 < 0) {
                            negScoreCOOC += p2;
                        }

                        //System.out.println(p1 + " " + p2);

                        if (val == 1) {
                            pos++;
                            //System.out.println(p1+" "+p2);
                            if (p1 >= 0.5 && p2 >= 0.5) {
                                strongPos++;
                            }
                        } else if (val == -1) {
                            neg++;
                            //System.out.println(p1+" "+p2);
                            if (p1 <= -0.5 && p2 <= -0.5) {
                                strongNeg++;
                            }
                        } else if (val == 0) {
                            neu++;
                        }
                        //else if
                    }
                }
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.get(count).put(1, pos);
                featureVector.get(count).put(2, neg);
                //featureVector.get(count).put(3, pos-neg);
                /*featureVector.get(count).put(1, posScoreDT);
                featureVector.get(count).put(2, negScoreDT);
                featureVector.get(count).put(3, posScoreCOOC);
                featureVector.get(count).put(4, negScoreCOOC);
                featureVector.get(count).put(5, totalDT);
                featureVector.get(count).put(6, totalCOOC);*/
                /*if (count<=167)
                {
                    if(pos>=neg)
                    {
                        p++;
                    }
                }
                else if(count>167 && count<=726)
                {
                    if(neg>=pos)
                    {
                        n++;
                    }
                }
                else if(count>726)
                {
                    if(pos>=neg)
                    {
                        ne++;
                    }
                }*/

                /*if(neg != 0)
                {
                    featureVector.get(count).put(3, pos/neg);
                }
                else if(pos == 0)
                {
                    featureVector.get(count).put(3, 0.0);
                }
                else
                {
                    featureVector.get(count).put(3, 1.0);
                }*/
                //featureVector.get(count).put(3, neu);

                //featureVector.get(count).put(1, strongPos);
                //featureVector.get(count).put(2, strongNeg);
                //featureVector.get(count).put(3, totalDT);
                //featureVector.get(count).put(4, totalCOOC);

                System.out.println(featureVector.get(count));
                featureCount = featureVector.get(count).size();
                count++;
            }
            System.out.println(p + " " + n + " " + ne);
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
        DTCOOCLexiconHindi ob = new DTCOOCLexiconHindi("D:\\Course\\Semester VII\\Internship\\sentiment\\indian");
    }
}

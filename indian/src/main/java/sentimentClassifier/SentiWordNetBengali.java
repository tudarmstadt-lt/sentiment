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
public class SentiWordNetBengali {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    int featureCount;

    SentiWordNetBengali(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        testFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        LinkedHashMap<String, Integer> lexicon = new LinkedHashMap<String, Integer>();

            BufferedReader readLexicon = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\resources\\sentimentSeedWordsBengali.txt"), "UTF-8"));
            String line = "";
            while ((line = readLexicon.readLine()) != null) {
                //System.out.println(line);
                String tokens[] = line.split("\\|");
                if (lexicon.containsKey(tokens[0])) {
                    System.out.println("Error");
                } else {
                    lexicon.put(tokens[0], Integer.parseInt(tokens[1]));
                }
            }
            readLexicon.close();

            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            int count = 0;
            //String text = "";

            while ((line = read.readLine()) != null) {
                String tokens[] = line.split(" ");
                double pos = 0, neg = 0, neu = 0;
                for (int i = 0; i < tokens.length; i++) {
                    int val;
                    if (lexicon.containsKey(tokens[i])) {
                        val = lexicon.get(tokens[i]);
                        if (val == 1) {
                            pos++;
                        } else if (val == -1) {
                            neg++;
                        } else if (val == 0) {
                            neu++;
                        }
                        //else if
                    }
                }
                featureVector.add(count, new LinkedHashMap<Integer, Double>());
                featureVector.get(count).put(1, pos);
                featureVector.get(count).put(2, neg);
                //featureVector.get(count).put(3, neu);

                //featureVector.get(count).put(1, strongPos);
                //featureVector.get(count).put(2, strongNeg);
                //featureVector.get(count).put(3, totalDT);
                //featureVector.get(count).put(4, totalCOOC);

                System.out.println(featureVector.get(count));
                featureCount = featureVector.get(count).size();
                count++;
            }
            read.close();

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
        SentiWordNetBengali ob = new SentiWordNetBengali(System.getProperty("user.dir"));
    }
}

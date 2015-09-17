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
public class UsernameURLHashtag {
    List<LinkedHashMap<Integer, Double>> trainingFeature;
    List<LinkedHashMap<Integer, Double>> testFeature;
    String rootDirectory;
    int featureCount;

    UsernameURLHashtag(String rootDirectory) throws IOException {
        this.trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();
        this.rootDirectory = rootDirectory;

        trainingFeature = generateFeature(rootDirectory + "\\dataset\\tokenized_Train.txt");
        //testFeature = generateFeature(rootDirectory + "\\dataset\\Test_Restaurants_Contextual_Cleansed.txt", rootDirectory + "\\dataset\\tokenized_Test.txt", rootDirectory + "\\dataset\\raw_POS_Test.txt");
    }

    private List<LinkedHashMap<Integer, Double>> generateFeature(String fileName) throws IOException {
        List<LinkedHashMap<Integer, Double>> featureVector = new ArrayList<LinkedHashMap<Integer, Double>>();
        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        int count = 0;
        String line;

        while ((line = read.readLine()) != null) {
            String tokens[] = line.split(" ");
            double user = 0, url = 0, hashtag = 0;
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].compareToIgnoreCase("someuser") == 0) {
                    user++;
                } else if (tokens[i].compareToIgnoreCase("someurl") == 0) {
                    url++;
                } else if (tokens[i].compareToIgnoreCase("#") == 0) {
                    hashtag++;
                }
            }

            if (user != 0) {
                user = 1.0;
            }

            if (url != 0) {
                url = 0.0;
            }

            if (hashtag != 0) {
                hashtag = 0.0;
            }

            featureVector.add(count, new LinkedHashMap<Integer, Double>());
            featureVector.get(count).put(1, user);
            featureVector.get(count).put(2, url);
            featureVector.get(count).put(3, hashtag);
            //System.out.println(featureVector.get(count));
            count++;
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
        return 3;
    }

    public static void main(String[] args) throws IOException {
        UsernameURLHashtag ob = new UsernameURLHashtag("D:\\Course\\Semester VII\\Internship\\sentiment\\indian");
    }
}

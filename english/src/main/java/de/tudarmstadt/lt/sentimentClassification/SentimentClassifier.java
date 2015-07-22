package de.tudarmstadt.lt.sentimentClassification;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.*;

class ClassifierHelper {
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ClassifierHelper(String dataset) throws IOException {
        BufferedReader readerTrain = new BufferedReader(new FileReader(new File(dataset)));

        int count = 0;
        while (readerTrain.readLine() != null) {
            count++;
        }

        for (int i = 0; i < count; i++) {
            featureList.add(i, new LinkedHashMap<Integer, Double>());
        }

    }

    public void setHashMap(int start, List<LinkedHashMap<Integer, Double>> hMap) {
        for (int i = 0; i < hMap.size(); i++) {
            for (Map.Entry<Integer, Double> entry : hMap.get(i).entrySet()) {
                featureList.get(i).put(start + entry.getKey(), entry.getValue());
            }
        }
    }

    public List<LinkedHashMap<Integer, Double>> getList() {
        return featureList;
    }
}

public class SentimentClassifier {

    static String rootDirectory;
    static List<LinkedHashMap<Integer, Double>> trainingFeature;
    static List<LinkedHashMap<Integer, Double>> testFeature;


    int SentimentClassifier()throws IOException
    {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment";
        return generateFeature();
    }

    private int generateFeature()throws IOException
    {
        //TRAINING SET
        ClassifierHelper trainingObject = new ClassifierHelper(rootDirectory + "\\dataset\\Train_Restaurants_Contextual_Cleansed.txt");


        //TESTING SET
        ClassifierHelper testObject = new ClassifierHelper(rootDirectory + "\\dataset\\Test_Restaurants_Contextual_Cleansed.txt");


        //POS FEATURE
        int start = 0;
        POS posObject = new POS(rootDirectory);
        trainingObject.setHashMap(start, posObject.getTrainingList());

        testObject.setHashMap(start, posObject.getTestList());

        //NRC HASHTAG FEATURE
        start += posObject.getFeatureCount();
        NRCHashtag nrcHashtagObject = new NRCHashtag(rootDirectory);
        trainingObject.setHashMap(start, nrcHashtagObject.getTrainingList());

        testObject.setHashMap(start, nrcHashtagObject.getTestList());

        //N GRAM FEATURE
        start += nrcHashtagObject.getFeatureCount();
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());

        testObject.setHashMap(start, ngramObject.getTestList());

        //SENTI WORD NET FEATURE
        start += ngramObject.getFeatureCount();
        SentiWordNet sentiWordNetObject = new SentiWordNet(rootDirectory);
        trainingObject.setHashMap(start, sentiWordNetObject.getTrainingList());

        testObject.setHashMap(start, sentiWordNetObject.getTestList());

        //BING LIU LEXICON FEATURE
        start += sentiWordNetObject.getFeatureCount();
        BingLiuLexicon bingLiuObject = new BingLiuLexicon(rootDirectory);
        trainingObject.setHashMap(start, bingLiuObject.getTrainingList());

        testObject.setHashMap(start, bingLiuObject.getTestList());

        //SENTIMENT 140 LEXICON FEATURE
        start += bingLiuObject.getFeatureCount();
        Senti140Lexicon senti140Object = new Senti140Lexicon(rootDirectory);
        trainingObject.setHashMap(start, senti140Object.getTrainingList());

        testObject.setHashMap(start, senti140Object.getTestList());

        //ENTITY FEATURE
        start += senti140Object.getFeatureCount();
        EntityFeature entityObject = new EntityFeature(rootDirectory);
        trainingObject.setHashMap(start, entityObject.getTrainingList());

        testObject.setHashMap(start, entityObject.getTestList());

        //NRC HASHTAG BIGRAM LEXICON FEATURE

        start += entityObject.getFeatureCount();
        NRCHashtagBigrams nrcBiObject = new NRCHashtagBigrams(rootDirectory);
        trainingObject.setHashMap(start, nrcBiObject.getTrainingList());

        testObject.setHashMap(start, nrcBiObject.getTestList());

        //SENTI140 BIGRAM LEXICON FEATURE

        start += nrcBiObject.getFeatureCount();
        Senti140LexiconBigrams sentiBiObject = new Senti140LexiconBigrams(rootDirectory);
        trainingObject.setHashMap(start, sentiBiObject.getTrainingList());

        testObject.setHashMap(start, sentiBiObject.getTestList());

        //CLUSTER LEXICON FEATURE

        start += sentiBiObject.getFeatureCount();
        BrownClusters clusterObject = new BrownClusters(rootDirectory);
        trainingObject.setHashMap(start, clusterObject.getTrainingList());

        testObject.setHashMap(start, clusterObject.getTestList());

        //NRC EMOTION LEXICON

        start += clusterObject.getFeatureCount();
        NRCEmotionLexicon emotionObject = new NRCEmotionLexicon(rootDirectory);
        trainingObject.setHashMap(start, emotionObject.getTrainingList());

        testObject.setHashMap(start, emotionObject.getTestList());

        //AFINN LEXICON

        start += emotionObject.getFeatureCount();
        AFINNLexicon afinnObject = new AFINNLexicon(rootDirectory);
        trainingObject.setHashMap(start, afinnObject.getTrainingList());

        testObject.setHashMap(start, afinnObject.getTestList());

        trainingFeature = trainingObject.getList();
        testFeature = testObject.getList();


        int finalSize = start + afinnObject.getFeatureCount();

        return finalSize;
    }


    public static void main(String[] args) throws IOException {

        SentimentClassifier object = new SentimentClassifier();
        int finalSize = object.SentimentClassifier();
        System.out.println("Hello sentiment!");

        // Create features
        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[object.trainingFeature.size()];
        File file = new File(rootDirectory + "\\dataset\\trainingLabels.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read;
        int count = 0;
        while ((read = reader.readLine()) != null) {
            a[count++] = Double.parseDouble(read.toString());
        }

        Feature[][] trainFeatureVector = new Feature[trainingFeature.size()][finalSize];

        for (int i = 0; i < trainingFeature.size(); i++) {
            System.out.println(i + " trained.");
            for (int j = 0; j < finalSize; j++) {
                if (trainingFeature.get(i).containsKey(j + 1)) {
                    trainFeatureVector[i][j] = new FeatureNode(j + 1, trainingFeature.get(i).get(j + 1));
                } else {
                    trainFeatureVector[i][j] = new FeatureNode(j + 1, 0.0);
                }
            }
        }

        problem.l = trainingFeature.size(); // number of training examples
        problem.n = finalSize; // number of features
        problem.x = trainFeatureVector; // feature nodes
        problem.y = a; // target values ----

        BasicParser bp = new BasicParser();

        SolverType solver = SolverType.L2R_LR; // -s 7
        double C = 1.0;    // cost of constraints violation
        double eps = 0.001; // stopping criteria

        Parameter parameter = new Parameter(solver, C, eps);
        Model model = Linear.train(problem, parameter);
        File modelFile = new File("model");
        model.save(modelFile);

        // load model or use it directly
        model = Model.load(modelFile);

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\predictedRestaurantsLabels.txt")));
        for (int i = 0; i < testFeature.size(); i++) {
            Feature[] instance = new Feature[testFeature.get(i).size()];
            int j = 0;
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[j++] = new FeatureNode(entry.getKey(), entry.getValue());
            }

            double prediction = Linear.predict(model, instance);
            write.println(prediction);
        }

        write.close();

    }

}
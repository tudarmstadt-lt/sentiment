package de.tudarmstadt.lt.sentimentClassification;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class ClassifierHelperL {
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();
    //static List<LinkedHashMap<Integer, Double>> testFeatureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ClassifierHelperL(String dataset) throws IOException {
        BufferedReader readerTrain = new BufferedReader(new FileReader(new File(dataset)));

        int count = 0;
        while (readerTrain.readLine() != null) {
            count++;
        }

        for (int i = 0; i < count; i++) {
            featureList.add(i, new LinkedHashMap<Integer, Double>());
        }

        System.out.println(count);
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

public class SentimentClassifierL {
    static String rootDirectory;
    static List<LinkedHashMap<Integer, Double>> trainingFeature;
    static List<LinkedHashMap<Integer, Double>> testFeature;


    SentimentClassifierL(int option, String trainFile, String testFile) throws IOException {

        rootDirectory = System.getProperty("user.dir");
        mainClassifierFunction(option, trainFile, testFile);

        //rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        //return generateFeature();
    }

    private void generateDataset(String input, String output)throws IOException {
        FileReader fR = new FileReader(input);
        PrintWriter writer = new PrintWriter(output);
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;
        while ((line = bf.readLine()) != null) {
            String[] part = line.split("\\|");
            writer.println(part[3].toLowerCase());

        }
        fR.close();
        //bf.close();
        writer.close();
    }

    private int generateFeature(int option, String trainFile, String testFile) throws IOException {

        generateDataset(rootDirectory + "\\dataset\\dataset_sentimentClassification\\"+trainFile, rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Laptops_Contextual_Cleansed.txt");

        //TRAINING SET
        ClassifierHelperL trainingObject = new ClassifierHelperL(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Laptops_Contextual_Cleansed.txt");

        //TESTING SET
        ClassifierHelperL testObject = null;
        if(option == 2 || option == 3)
        {
            generateDataset(rootDirectory + "\\dataset\\dataset_sentimentClassification\\"+testFile, rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Laptops_Contextual_Cleansed.txt");
            testObject = new ClassifierHelperL(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Laptops_Contextual_Cleansed.txt");
        }

        //POS FEATURE
        int start = 0;
        POSL posObject = new POSL(rootDirectory);
        trainingObject.setHashMap(start, posObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, posObject.getTestList());
        }
        //System.out.println(trainingFeature.get(10).size());


        //NRC HASHTAG FEATURE
        start += posObject.getFeatureCount();
        NRCHashtag nrcHashtagObject = new NRCHashtag(rootDirectory);
        trainingObject.setHashMap(start, nrcHashtagObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, nrcHashtagObject.getTestList());
        }

        //N GRAM FEATURE
        start += nrcHashtagObject.getFeatureCount();
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, ngramObject.getTestList());
        }
        //testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());


        //SENTI WORD NET FEATURE
        start += ngramObject.getFeatureCount();
        SentiWordNet sentiWordNetObject = new SentiWordNet(rootDirectory);
        trainingObject.setHashMap(start, sentiWordNetObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, sentiWordNetObject.getTestList());
        }
        //testFeature = testObject.getList();

        //BING LIU LEXICON FEATURE
        start += sentiWordNetObject.getFeatureCount();
        BingLiuLexicon bingLiuObject = new BingLiuLexicon(rootDirectory);
        trainingObject.setHashMap(start, bingLiuObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, bingLiuObject.getTestList());
        }
        //testFeature = testObject.getList();


        //SENTIMENT 140 LEXICON FEATURE
        start += bingLiuObject.getFeatureCount();
        Senti140Lexicon senti140Object = new Senti140Lexicon(rootDirectory);
        trainingObject.setHashMap(start, senti140Object.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, senti140Object.getTestList());
        }
        //testFeature = testObject.getList();

        //ENTITY FEATURE
        start += senti140Object.getFeatureCount();
        EntityFeatureL entityObject = new EntityFeatureL(rootDirectory);
        trainingObject.setHashMap(start, entityObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, entityObject.getTestList());
        }
        //testFeature = testObject.getList();

        //NRC HASHTAG BIGRAM LEXICON FEATURE

        start += entityObject.getFeatureCount();
        NRCHashtagBigrams nrcBiObject = new NRCHashtagBigrams(rootDirectory);
        trainingObject.setHashMap(start, nrcBiObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, nrcBiObject.getTestList());
        }
        //testFeature = testObject.getList();

        //SENTI140 BIGRAM LEXICON FEATURE

        start += nrcBiObject.getFeatureCount();
        Senti140LexiconBigrams sentiBiObject = new Senti140LexiconBigrams(rootDirectory);
        trainingObject.setHashMap(start, sentiBiObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, sentiBiObject.getTestList());
        }
        //testFeature = testObject.getList();

        //NRC EMOTION LEXICON

        start += nrcBiObject.getFeatureCount();
        NRCEmotionLexicon emotionObject = new NRCEmotionLexicon(rootDirectory);
        trainingObject.setHashMap(start, emotionObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, emotionObject.getTestList());
        }
        //testFeature = testObject.getList();

        //AFINN LEXICON

        start += emotionObject.getFeatureCount();
        AFINNLexicon afinnObject = new AFINNLexicon(rootDirectory);
        trainingObject.setHashMap(start, afinnObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, afinnObject.getTestList());
        }

        trainingFeature = trainingObject.getList();
        if(option == 2 || option == 3) {
            testFeature = testObject.getList();
        }

        int finalSize = start + afinnObject.getFeatureCount();

        return finalSize;
    }


    private void mainClassifierFunction(int option, String trainFile, String testFile)throws IOException {
        //SentimentClassifierHindi this = new SentimentClassifierHindi();
        //int finalSize = this.SentimentClassifierHindi();
        int finalSize = this.generateFeature(option, trainFile, testFile);
        System.out.println("Hello aspectCategorization!");
        //System.out.println("Hello aspectCategorization!");

        // Create features

        // X = HashMap<List<(int, double)>> ...

        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[this.trainingFeature.size()];
        System.out.println(a.length);
        File file = new File(rootDirectory + "\\dataset\\trainingLabels.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read;
        int count = 0;
        while ((read = reader.readLine()) != null) {
            //System.out.println(read);
            a[count++] = Double.parseDouble(read.toString());
        }

        //Feature[][] f = new Feature[][]{ {}, {}, {}, {}, {}, {} };

        //trainingFeature = trainingObject.getList();
        Feature[][] trainFeatureVector = new Feature[trainingFeature.size()][finalSize];

        System.out.println(trainingFeature.size());
        System.out.println(finalSize);

        for (int i = 0; i < trainingFeature.size(); i++) {
            //System.out.println();
            //System.out.println(trainingFeature.get(i));
            System.out.println(i + " trained.");
            for (int j = 0; j < finalSize; j++) {
                //System.out.print(trainingFeature.get(i).get(j + 1)+" ");
                //trainingFeature.get(i).
                if (trainingFeature.get(i).containsKey(j + 1)) {
                    //System.out.print(j + 1 + ", ");
                    trainFeatureVector[i][j] = new FeatureNode(j + 1, trainingFeature.get(i).get(j + 1));
                } else {
                    trainFeatureVector[i][j] = new FeatureNode(j + 1, 0.0);
                }
            }
            //System.out.println();
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

        //Feature[] instance = new Feature[start + ngramObject.featureCount];

        /*for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[i] = new FeatureNode(entry.getKey(), entry.getValue());
            }
        }*/

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\predictedLabels.txt")));

        if (option == 1) {
            double[] val = new double[trainingFeature.size()];
            Linear.crossValidation(problem, parameter, 5, val);
            for (int i = 0; i < trainingFeature.size(); i++) {
                write.println(val[i]);
            }
            write.close();
            return;
        }

        //LinkedHashMap<String, Double>weight = new LinkedHashMap<String, Double>();

        // load model or use it directly
        if (option == 2 || option == 3) {
            model = Model.load(modelFile);
            //BufferedReader testFile = new BufferedReader(new InputStreamReader(new FileInputStream(rootDirectory + "\\dataset\\hindiTest.txt"), "UTF-8"));
            for (int i = 0; i < testFeature.size(); i++) {
                Feature[] instance = new Feature[testFeature.get(i).size()];
                int j = 0;
                for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                    instance[j++] = new FeatureNode(entry.getKey(), entry.getValue());
                }

                double prediction = Linear.predict(model, instance);
                //String id = testFile.readLine().split("\t")[0];
                //write.println(id+"\t"+prediction);
                write.println(prediction);
            }

            write.close();
            return;
        }

        write.close();

    }
}
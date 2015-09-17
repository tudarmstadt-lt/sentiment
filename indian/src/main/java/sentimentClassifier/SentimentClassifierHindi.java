package sentimentClassifier;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class ClassifierHelperHindi {
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ClassifierHelperHindi(String dataset) throws IOException {
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

public class SentimentClassifierHindi {

    static String rootDirectory;
    static List<LinkedHashMap<Integer, Double>> trainingFeature;
    static List<LinkedHashMap<Integer, Double>> testFeature;


    int SentimentClassifierHindi() throws IOException {
        rootDirectory =System.getProperty("user.dir");
        //rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        return generateFeature();
    }

    private int generateFeature() throws IOException {
        //TRAINING SET
        ClassifierHelperHindi trainingObject = new ClassifierHelperHindi(rootDirectory + "\\dataset\\hindiCleansedTraining.txt");


        //TESTING SET
        //ClassifierHelperHindi testObject = new ClassifierHelperHindi(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Restaurants_Contextual_Cleansed.txt");

        //POSHindi FEATURE
        int start = 0;
        //POSHindi posObject = new POSHindi(rootDirectory);
        //trainingObject.setHashMap(start, posObject.getTrainingList());

        //N GRAM FEATURE
        //start += posObject.getFeatureCount();
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());


        //DT COOC FEATURE
        start += ngramObject.getFeatureCount();
        DTCOOCLexiconHindi dtCOOCObject = new DTCOOCLexiconHindi(rootDirectory);
        trainingObject.setHashMap(start, dtCOOCObject.getTrainingList());

        //SENTI WORDNET HINDI
        start += dtCOOCObject.getFeatureCount();
        SentiWordNetHindi sentiObject = new SentiWordNetHindi(rootDirectory);
        trainingObject.setHashMap(start, sentiObject.getTrainingList());

        /*//DT FEATURE
        start += ngramObject.getFeatureCount();
        DTHindi dtObject = new DTHindi(rootDirectory);
        trainingObject.setHashMap(start, dtObject.getTrainingList());

        //COOC FEATURE
        start += dtObject.getFeatureCount();
        COOCHindi coocObject = new COOCHindi(rootDirectory);
        trainingObject.setHashMap(start, coocObject.getTrainingList());*/

        //USERNAME URL HASHTAG FEATURE
        /*start += dtCOOCObject.getFeatureCount();
        UsernameURLHashtag userUrlObject = new UsernameURLHashtag(rootDirectory);
        trainingObject.setHashMap(start, userUrlObject.getTrainingList());*/

        trainingFeature = trainingObject.getList();
        //testFeature = testObject.getList();


        int finalSize = start + sentiObject.getFeatureCount();

        return finalSize;


        /*System.out.println("TRAINING *************************************");
        for (int i = 0; i < trainingFeature.size(); i++)    //Print the feature values
        {
            //System.out.println(trainingFeature.get(i).size());
            System.out.println(trainingFeature.get(i));
        }

        System.out.println("TEST *************************************");
        for (int i = 0; i < testFeature.size(); i++)    //Print the feature values
        {
            //System.out.println(trainingFeature.get(i).size());
            System.out.println(testFeature.get(i));
        }*/
    }

    public static void main(String[] args) throws IOException {

        SentimentClassifierHindi object = new SentimentClassifierHindi();
        int finalSize = object.SentimentClassifierHindi();

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

        double[] val = new double[trainingFeature.size()];
        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\predictedLabels.txt")));
        Linear.crossValidation(problem, parameter, 5, val);
        for (int i = 0; i < trainingFeature.size(); i++) {
            write.println(val[i]);
            //System.out.println(val[i]);
        }

        //LinkedHashMap<String, Double>weight = new LinkedHashMap<String, Double>();

        // load model or use it directly
        model = Model.load(modelFile);

        write.close();
    }
}
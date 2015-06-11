package de.tudarmstadt.lt.sentiment;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class GenerateTrainFeature {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    GenerateTrainFeature() {
        for (int i = 0; i < 1654; i++) {
            listOfMaps.add(i, new LinkedHashMap<Integer, Double>());
        }
    }

    public void setHashMap(int start, List<LinkedHashMap<Integer, Double>> hMap) {
        //System.out.println(hMap.size() + " ** " + hMap.get(0).size() + " ## "+start);
        System.out.println("start: " + start + ": ");

        for (int i = 0; i < hMap.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : hMap.get(i).entrySet()) {
                //System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                listOfMaps.get(i).put(start + entry.getKey(), entry.getValue());
                // do stuff
            }
        }
    }

    public List<LinkedHashMap<Integer, Double>> getList() {
        //System.out.println(trainingFeature.size());
        return listOfMaps;
    }
}

class GenerateTestFeature {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    GenerateTestFeature() {
        for (int i = 0; i < 845; i++) {
            listOfMaps.add(i, new LinkedHashMap<Integer, Double>());
        }
    }

    public void setHashMap(int start, List<LinkedHashMap<Integer, Double>> hMap) {
        //System.out.println(hMap.size() + " ** " + hMap.get(0).size() + " ## "+start);
        System.out.println("start: " + start + ": ");

        for (int i = 0; i < hMap.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : hMap.get(i).entrySet()) {
                //System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                listOfMaps.get(i).put(start + entry.getKey(), entry.getValue());
                // do stuff
            }
        }
    }

    public List<LinkedHashMap<Integer, Double>> getList() {
        //System.out.println(trainingFeature.size());
        return listOfMaps;
    }
}

public class SentimentClassifier {
    //static int start=0;
    final static String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment";

    public static void main(String[] args) throws IOException {

        //TRAINING SET
        GenerateTrainFeature trainingObject = new GenerateTrainFeature();
        List<LinkedHashMap<Integer, Double>> trainingFeature = new ArrayList<LinkedHashMap<Integer, Double>>();


        //TESTING SET
        GenerateTestFeature testObject = new GenerateTestFeature();
        List<LinkedHashMap<Integer, Double>> testFeature = new ArrayList<LinkedHashMap<Integer, Double>>();



        //POS FEATURE
        int start = 0;
        POS posObject = new POS(rootDirectory);
        trainingObject.setHashMap(start, posObject.getTrainingList());
        trainingFeature = trainingObject.getList();

        testObject.setHashMap(start, posObject.getTestList());
        testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());


        //NRC HASHTAG FEATURE
        start += posObject.getFeatureCount();
        NRCHashtag nrcHashtagObject = new NRCHashtag(rootDirectory);
        trainingObject.setHashMap(start, nrcHashtagObject.getTrainingList());
        trainingFeature = trainingObject.getList();

        testObject.setHashMap(start, nrcHashtagObject.getTestList());
        testFeature = testObject.getList();


        /*System.out.println("*************************************");
        for(int i=0; i<trainingFeature.size(); i++)    //Print the feature values
        {
            //System.out.println(trainingFeature.get(i).size());
            System.out.println(trainingFeature.get(i));
        }*/


        //N GRAM FEATURE
        start += nrcHashtagObject.getFeatureCount();
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());
        trainingFeature = trainingObject.getList();

        testObject.setHashMap(start, ngramObject.getTestList());
        testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());


        //CATEGORY FEATURE
        /*start = trainingFeature.get(0).size();
        Category obj4 = new Category();
        trainingObject.setHashMap(start, obj4.getTrainingList());
        trainingFeature = trainingObject.getList();
        System.out.println(trainingFeature.get(10).size());*/


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


        System.out.println("Hello sentiment!");

        // Create features

        // X = HashMap<List<(int, double)>> ...

        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[nrcHashtagObject.getTrainingList().size()];
        File file = new File(rootDirectory + "\\dataset\\trainingLabels.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read = null;
        int count = 0;
        while ((read = reader.readLine()) != null) {
            //System.out.println(read);
            a[count++] = Double.parseDouble(read.toString());
        }

        //Feature[][] f = new Feature[][]{ {}, {}, {}, {}, {}, {} };

        trainingFeature = trainingObject.getList();
        Feature[][] trainFeatureVector = new Feature[trainingFeature.size()][start + ngramObject.getFeatureCount()];

        System.out.println(trainingFeature.size());
        System.out.println(start + ngramObject.getFeatureCount());

        for (int i = 0; i < trainingFeature.size(); i++) {
            //System.out.println();
            //System.out.println(trainingFeature.get(i));
            for (int j = 0; j < start + ngramObject.getFeatureCount(); j++) {
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
        problem.n = start + ngramObject.getFeatureCount(); // number of features
        problem.x = trainFeatureVector; // feature nodes
        problem.y = a; // target values ----

        /**
         This is similar to original C implementation:
         struct problem
         {
         int l, n;
         int *y;
         struct feature_node **x;
         double bias;
         };
         * */

        BasicParser bp = new BasicParser();

        SolverType solver = SolverType.L2R_LR_DUAL; // -s 7
        double C = 1.0;    // cost of constraints violation
        double eps = 0.001; // stopping criteria

        Parameter parameter = new Parameter(solver, C, eps);
        Model model = Linear.train(problem, parameter);
        File modelFile = new File("model");
        model.save(modelFile);

        // load model or use it directly
        model = Model.load(modelFile);

        Feature[] instance = new Feature[start + ngramObject.featureCount];

        /*for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[i] = new FeatureNode(entry.getKey(), entry.getValue());
            }
        }*/

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\predictedRestaurantsLabels.txt")));
        for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            //System.out.println(testFeature.get(i));
            for (int j = 0; j < start + ngramObject.getFeatureCount(); j++) {
                //System.out.print(trainingFeature.get(i).get(j + 1)+" ");
                if (testFeature.get(i).containsKey(j + 1)) {
                    instance[j] = new FeatureNode(j + 1, testFeature.get(i).get(j + 1));
                    //System.out.print(j+1+":"+testFeature.get(i).get(j + 1)+"; ");
                } else {
                    instance[j] = new FeatureNode(j + 1, 0.0);
                }


                //System.out.println();
            }

            /*System.out.println();
            for(int k=0; k<start + ngramObject.getFeatureCount(); k++)
            {
                System.out.print(instance[k].getValue() + ", ");
            }*/
            double prediction = Linear.predict(model, instance);

            write.println(prediction);
            //System.out.println(prediction);
        }

        write.close();

    }
}
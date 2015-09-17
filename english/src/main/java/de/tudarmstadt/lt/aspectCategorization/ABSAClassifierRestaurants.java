package de.tudarmstadt.lt.aspectCategorization;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.*;


class ABSAClassifierHelperR {
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ABSAClassifierHelperR(String dataset) throws IOException {
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

public class ABSAClassifierRestaurants {
    static String rootDirectory;
    static List<LinkedHashMap<Integer, Double>> trainingFeature;
    static List<LinkedHashMap<Integer, Double>> testFeature;


    int SentimentClassifierL() throws IOException {
        /*File file = new File("rootDir.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            rootDirectory = line;
            System.out.println("Root Directory is: " + rootDirectory);
        }*/
        rootDirectory = System.getProperty("user.dir");
        return generateFeature();
    }

    private int generateFeature() throws IOException {
        //TRAINING SET
        ABSAClassifierHelperR trainingObject = new ABSAClassifierHelperR(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Train_ABSA.txt");


        //TESTING SET
        ABSAClassifierHelperR testObject = new ABSAClassifierHelperR(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Test_ABSA.txt");

        //POS FEATURE
        int start = 0;
        ABSAPOS posObject = new ABSAPOS(rootDirectory, rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Train_ABSA.txt", rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Test_ABSA.txt");
        //trainingObject.setHashMap(start, posObject.getTrainingList());

        //testObject.setHashMap(start, posObject.getTestList());
        //System.out.println(trainingFeature.get(10).size());

        //BAG OF WORDS FEATURE
        ABSABagOfWords bagObject = new ABSABagOfWords(rootDirectory);
        trainingObject.setHashMap(start, bagObject.getTrainingList());

        testObject.setHashMap(start, bagObject.getTestList());
        testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());

        //EV FEATURE
        /*start += bagObject.getFeatureCount();
        ABSAEVRestaurantsFeature evObject = new ABSAEVRestaurantsFeature(rootDirectory);
        trainingObject.setHashMap(start, evObject.getTrainingList());

        testObject.setHashMap(start, evObject.getTestList());
        testFeature = testObject.getList();*/

        //NGRAM FEATURE
        /*start += evObject.getFeatureCount();
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());

        testObject.setHashMap(start, ngramObject.getTestList());
        testFeature = testObject.getList();*/

        trainingFeature = trainingObject.getList();
        testFeature = testObject.getList();

        int finalSize = start + bagObject.getFeatureCount();

        return finalSize;
    }


    public static void main(String[] args) throws IOException {
        ABSAClassifierRestaurants object = new ABSAClassifierRestaurants();
        int finalSize = object.SentimentClassifierL();
        System.out.println("Hello aspectCategorization!");

        // Create features
        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[object.trainingFeature.size()];
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

        /*double[] val = new double[1974];
        Linear.crossValidation(problem, parameter, 5, val);
        for(int i=0; i<1974; i++)
        {
            System.out.println(val[i]);
        }*/

        /*LinkedHashMap<String, Double>weight = new LinkedHashMap<String, Double>();

        double arr[] = model.getFeatureWeights();*/

        // load model or use it directly
        model = Model.load(modelFile);

        //Feature[] instance = new Feature[start + ngramObject.featureCount];

        /*for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[i] = new FeatureNode(entry.getKey(), entry.getValue());
            }
        }*/

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\dataset_aspectCategorization\\predictedRestaurantsLabels.txt")));
        BufferedReader testFile = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Test_ABSA.txt")));
        HashMap<String, Integer> id = new HashMap<String, Integer>();

        for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            Feature[] instance = new Feature[testFeature.get(i).size()];
            int j = 0;
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                //System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                //listOfMaps.get(i).put(start + entry.getKey(), entry.getValue());
                // do stuff
                instance[j++] = new FeatureNode(entry.getKey(), entry.getValue());
            }
            double[] predict = new double[13];
            double prediction = Linear.predictProbability(model, instance, predict);

            //Arrays.sort(predict, Collections.reverseOrder());
            //System.out.println();
            String tokens[] = testFile.readLine().split("\\|");

            if (id.containsKey(tokens[1]) == true) {

            } else {
                int flag = 0;
                for (int p = 0; p < 13; p++) {
                    if (predict[p] >= 0.18) {
                        flag = 1;
                        write.println(p + 1);
                    }
                }
                if (flag == 1) {
                    write.println("next");
                } else {
                    write.println("-1");
                    write.println("next");
                }
                //write.println(prediction);
                id.put(tokens[1], 1);
            }

            /*for(int p=0; p<13; p++)
            {
                System.out.print(predict[p] * 100 + " ");
            }*/

            //write.println(prediction);
            //System.out.println(prediction);
        }

        write.close();

    }

    private static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap =
                new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, val);
                    break;
                }

            }

        }
        return sortedMap;
    }
}
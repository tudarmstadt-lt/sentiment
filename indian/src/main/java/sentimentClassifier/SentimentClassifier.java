package sentimentClassifier;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.*;


class GenerateTrainFeature {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    GenerateTrainFeature() {
        for (int i = 0; i < 1222; i++) {
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
    final static String rootDirectory = "D:\\Course\\Semester VII\\Internship\\IndianSentiment";

    public static void main(String[] args) throws IOException {

        //TRAINING SET
        GenerateTrainFeature trainingObject = new GenerateTrainFeature();
        List<LinkedHashMap<Integer, Double>> trainingFeature;


        //TESTING SET
        /*GenerateTestFeature testObject = new GenerateTestFeature();
        List<LinkedHashMap<Integer, Double>> testFeature;*/

        //POS FEATURE
        int start = 0;
        /*POS posObject = new POS(rootDirectory);
        trainingObject.setHashMap(start, posObject.getTrainingList());

        //N GRAM FEATURE
        start += posObject.getFeatureCount();*/
        Ngram ngramObject = new Ngram(rootDirectory);
        trainingObject.setHashMap(start, ngramObject.getTrainingList());


        int finalSize = start + ngramObject.getFeatureCount();


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
        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[ngramObject.getTrainingList().size()];
        File file = new File(rootDirectory + "\\dataset\\trainingLabels.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read;
        int count = 0;
        while ((read = reader.readLine()) != null) {
            //System.out.println(read);
            a[count++] = Double.parseDouble(read.toString());
        }

        //Feature[][] f = new Feature[][]{ {}, {}, {}, {}, {}, {} };

        trainingFeature = trainingObject.getList();
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

        double[] val = new double[1222];
        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory +  "\\dataset\\predictedLabels.txt")));
        Linear.crossValidation(problem, parameter, 5 , val);
        for(int i=0; i<1222; i++)
        {
            write.println(val[i]);
            //System.out.println(val[i]);
        }

        LinkedHashMap<String, Double>weight = new LinkedHashMap<String, Double>();

        // load model or use it directly
        model = Model.load(modelFile);

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
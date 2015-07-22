package de.tudarmstadt.lt.sentiment;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.*;


class ABSAGenerateTrainFeature {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    ABSAGenerateTrainFeature() {
        for (int i = 0; i < 1478; i++) {
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

class ABSAGenerateTestFeature {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    ABSAGenerateTestFeature() {
        for (int i = 0; i < 775; i++) {
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

public class ABSAClassifier {
    //static int start=0;
    final static String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment";

    public static void main(String[] args) throws IOException {

        //TRAINING SET
        ABSAGenerateTrainFeature trainingObject = new ABSAGenerateTrainFeature();
        List<LinkedHashMap<Integer, Double>> trainingFeature;


        //TESTING SET
        ABSAGenerateTestFeature testObject = new ABSAGenerateTestFeature();
        List<LinkedHashMap<Integer, Double>> testFeature;



        //POS FEATURE
        int start = 0;
        ABSAPOS posObject = new ABSAPOS(rootDirectory);
        //trainingObject.setHashMap(start, posObject.getTrainingList());

        //testObject.setHashMap(start, posObject.getTestList());
        //System.out.println(trainingFeature.get(10).size());

        //BAG OF WORDS FEATURE
        //start += nrcHashtagObject.getFeatureCount();
        ABSABagOfWords bagObject = new ABSABagOfWords(rootDirectory);
        trainingObject.setHashMap(start, bagObject.getTrainingList());

        testObject.setHashMap(start, bagObject.getTestList());
        testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());

        //EV FEATURE
        start += bagObject.getFeatureCount();
        ABSAEVFeature evObject = new ABSAEVFeature(rootDirectory);
        trainingObject.setHashMap(start, evObject.getTrainingList());

        testObject.setHashMap(start, evObject.getTestList());
        testFeature = testObject.getList();

        int finalSize = start + evObject.getFeatureCount();


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
        double a[] = new double[bagObject.getTrainingList().size()];
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

        LinkedHashMap<String, Double>weight = new LinkedHashMap<String, Double>();

        double arr[] = model.getFeatureWeights();
        //System.out.println("Feature Weight: "+ arr.length);
        //Arrays.sort(arr);
        /*for(int p=0; p<arr.length; p++)
        {
            weight.put(p+"",arr[p]);
            //System.out.println(arr[p]);
        }

        weight = sortHashMapByValuesD(weight);
        PrintWriter weightFile = new PrintWriter(rootDirectory+"\\dataset\\weight.txt");
        List mapValues = new ArrayList(weight.values());
        List mapKeys = new ArrayList(weight.keySet());

        //System.out.println(weight);

        for (Map.Entry<String, Double> entry : weight.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            weightFile.write(key+": "+value*1000+"\n");
            // now work with key and value...
        }

        /*Iterator keyIt = mapKeys.iterator();
        while (keyIt.hasNext()) {
            Object key = keyIt.next();
            weightFile.write(key.toString()+": "+weight.get(key)+"\n");
            //System.out.println(weight.get(key).toString());
        }*/
        //weightFile.close();

        // load model or use it directly
        model = Model.load(modelFile);

        //Feature[] instance = new Feature[start + ngramObject.featureCount];

        /*for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[i] = new FeatureNode(entry.getKey(), entry.getValue());
            }
        }*/

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory +  "\\dataset\\predictedRestaurantsLabels.txt")));
        BufferedReader testFile = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\Test_Restaurants_Cleansed.txt")));
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
            double[] predict = new double[81];
            double prediction = Linear.predictProbability(model, instance, predict);

            //Arrays.sort(predict, Collections.reverseOrder());
            //System.out.println();
            String tokens[] = testFile.readLine().split("\\|");

            //write.println(prediction);

            /*System.out.println("************************");
            for(int p=0; p<81; p++)
            {
                System.out.print(predict[p] * 100 + " ");
            }

            System.out.println();*/

            if(id.containsKey(tokens[1]) == true)
            {
                write.println(getHighestIndex(predict, id.get(tokens[1]), predict.length)+".0");
                //write.println(0);
                id.put(tokens[1], 1+id.get(tokens[1]));
            }
            else
            {
                write.println(prediction);
                id.put(tokens[1], 1);
            }

            for(int p=0; p<13; p++)
            {
                System.out.print(predict[p] * 100 + " ");
            }

            //write.println(prediction);
            //System.out.println(prediction);
        }

        write.close();

    }

    private static int getHighestIndex(double arr[], int p, int n)
    {
        double max;
        int pos=-1;
        for(int i=0; i<=p; i++)
        {
            max=-5;
            pos=-1;
            for(int j=0; j<n; j++)
            {
                if(arr[j]>max)
                {
                    max=arr[j];
                    pos=j;
                }
            }
            arr[pos] = -2;
        }
        return pos+1;
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
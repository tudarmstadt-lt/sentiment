package de.tudarmstadt.lt.sentimentClassification;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/*class GenerateTrainFeatureH {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    GenerateTrainFeatureH() {
        for (int i = 0; i < 2296; i++) {
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

class GenerateTestFeatureH {
    static List<LinkedHashMap<Integer, Double>> listOfMaps = new ArrayList<LinkedHashMap<Integer, Double>>();

    GenerateTestFeatureH() {
        for (int i = 0; i < 339; i++) {
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
}*/

class ClassifierHelperH
{
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();
    //static List<LinkedHashMap<Integer, Double>> testFeatureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ClassifierHelperH(String dataset)throws IOException
    {
        //String trainingSet = "\\dataset\\Train_Restaurants_Contextual_Cleansed.txt";
        //String testSet = "\\dataset\\Test_Restaurants_Contextual_Cleansed.txt";
        BufferedReader readerTrain = new BufferedReader(new FileReader(new File(dataset)));
        //BufferedReader readerTest = new BufferedReader(new FileReader(new File(testSet)));

        String line;
        int count=0;
        while((line = readerTrain.readLine()) != null)
        {
            count++;
        }

        for (int i = 0; i < count; i++) {
            featureList.add(i, new LinkedHashMap<Integer, Double>());
        }

        System.out.println(count);

        /*count=0;
        while((line = readerTrain.readLine()) != null)
        {
            count++;
        }

        for (int i = 0; i < count; i++) {
            testFeatureList.add(i, new LinkedHashMap<Integer, Double>());
        }*/
    }

    public void setHashMap(int start, List<LinkedHashMap<Integer, Double>> hMap) {
        //System.out.println(hMap.size() + " ** " + hMap.get(0).size() + " ## "+start);
        System.out.println("start: " + start + ": ");
        System.out.println("$$$$$$$$$$$$$$$$$"+featureList.size());
        //System.out.println(hMap.size());
        for (int i = 0; i < hMap.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : hMap.get(i).entrySet()) {
                //System.out.print(entry.getKey() + ": " + entry.getValue() + ";   ");
                featureList.get(i).put(start + entry.getKey(), entry.getValue());
                // do stuff
            }
        }
    }

    public List<LinkedHashMap<Integer, Double>> getList() {
        //System.out.println(trainingFeature.size());
        //System.out.println("$$$$$$$$$$$$$$$$$"+featureList.size());
        return featureList;
    }
}

public class SentimentClassifierH {
    //static int start=0;
    final static String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment";

    public static void main(String[] args) throws IOException {

        //TRAINING SET
        ClassifierHelperH trainingObject = new ClassifierHelperH(rootDirectory + "\\dataset\\Train_Hotels_Contextual_Cleansed.txt");
        List<LinkedHashMap<Integer, Double>> trainingFeature;

        /*GenerateTrainFeatureH trainingObject = new GenerateTrainFeatureH();
        List<LinkedHashMap<Integer, Double>> trainingFeature;*/


        //TESTING SET
        ClassifierHelperH testObject = new ClassifierHelperH(rootDirectory + "\\dataset\\Test_Hotels_Contextual_Cleansed.txt");
        List<LinkedHashMap<Integer, Double>> testFeature;
        /*GenerateTestFeatureH testObject = new GenerateTestFeatureH();
        List<LinkedHashMap<Integer, Double>> testFeature;*/



        //POS FEATURE
        int start = 0;
        POSH posObject = new POSH(rootDirectory);
        trainingObject.setHashMap(start, posObject.getTrainingList());

        testObject.setHashMap(start, posObject.getTestList());
        //System.out.println(trainingFeature.get(10).size());


        //NRC HASHTAG FEATURE
        start += posObject.getFeatureCount();
        NRCHashtag nrcHashtagObject = new NRCHashtag(rootDirectory);
        trainingObject.setHashMap(start, nrcHashtagObject.getTrainingList());

        testObject.setHashMap(start, nrcHashtagObject.getTestList());


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

        testObject.setHashMap(start, ngramObject.getTestList());
        testFeature = testObject.getList();
        //System.out.println(trainingFeature.get(10).size());


        //SENTI WORD NET FEATURE
        start += ngramObject.getFeatureCount();
        SentiWordNet sentiWordNetObject = new SentiWordNet(rootDirectory);
        trainingObject.setHashMap(start, sentiWordNetObject.getTrainingList());

        testObject.setHashMap(start, sentiWordNetObject.getTestList());
        testFeature = testObject.getList();

        //BING LIU LEXICON FEATURE
        start += sentiWordNetObject.getFeatureCount();
        BingLiuLexicon bingLiuObject = new BingLiuLexicon(rootDirectory);
        trainingObject.setHashMap(start, bingLiuObject.getTrainingList());

        testObject.setHashMap(start, bingLiuObject.getTestList());
        testFeature = testObject.getList();


        //SENTIMENT 140 LEXICON FEATURE
        start += bingLiuObject.getFeatureCount();
        Senti140Lexicon senti140Object = new Senti140Lexicon(rootDirectory);
        trainingObject.setHashMap(start, senti140Object.getTrainingList());

        testObject.setHashMap(start, senti140Object.getTestList());
        testFeature = testObject.getList();

        //ENTITY FEATURE
        start += senti140Object.getFeatureCount();
        EntityFeatureH entityObject = new EntityFeatureH(rootDirectory);
        trainingObject.setHashMap(start, entityObject.getTrainingList());

        testObject.setHashMap(start, entityObject.getTestList());
        testFeature = testObject.getList();


        //NRC HASHTAG BIGRAM LEXICON FEATURE

        start += entityObject.getFeatureCount();
        NRCHashtagBigrams nrcBiObject = new NRCHashtagBigrams(rootDirectory);
        trainingObject.setHashMap(start, nrcBiObject.getTrainingList());

        testObject.setHashMap(start, nrcBiObject.getTestList());
        testFeature = testObject.getList();

        //SENTI140 BIGRAM LEXICON FEATURE

        start += nrcBiObject.getFeatureCount();
        Senti140LexiconBigrams sentiBiObject = new Senti140LexiconBigrams(rootDirectory);
        trainingObject.setHashMap(start, sentiBiObject.getTrainingList());

        testObject.setHashMap(start, sentiBiObject.getTestList());
        testFeature = testObject.getList();

        //NRC EMOTION LEXICON

        start += nrcBiObject.getFeatureCount();
        NRCEmotionLexicon emotionObject = new NRCEmotionLexicon(rootDirectory);
        trainingObject.setHashMap(start, emotionObject.getTrainingList());

        testObject.setHashMap(start, emotionObject.getTestList());
        testFeature = testObject.getList();

        //AFINN LEXICON

        start += nrcBiObject.getFeatureCount();
        AFINNLexicon afinnObject = new AFINNLexicon(rootDirectory);
        trainingObject.setHashMap(start, afinnObject.getTrainingList());

        testObject.setHashMap(start, afinnObject.getTestList());
        testFeature = testObject.getList();

        //ZSCORE FEATURE

        /*start += afinnObject.getFeatureCount();
        ZScore zscoreObject = new ZScore(rootDirectory);
        trainingObject.setHashMap(start, zscoreObject.getTrainingList());

        testObject.setHashMap(start, zscoreObject.getTestList());
        testFeature = testObject.getList();*/

        //MPQA LEXICON FEATURE

        /*start += afinnObject.getFeatureCount();
        MPQALexicon mpqaObject = new MPQALexicon(rootDirectory);
        trainingObject.setHashMap(start, mpqaObject.getTrainingList());

        testObject.setHashMap(start, mpqaObject.getTestList());
        testFeature = testObject.getList();*/

        //TF-IDF FEATURE
        /*start += entityObject.getFeatureCount();
        Tf_IdfFeature tfidfObject = new Tf_IdfFeature(rootDirectory);
        trainingObject.setHashMap(start, tfidfObject.getTrainingList());

        testObject.setHashMap(start, tfidfObject.getTestList());
        testFeature = testObject.getList();*/


        //CLUSTER LEXICON FEATURE

        /*start += sentiBiObject.getFeatureCount();
        BrownClusters clusterObject = new BrownClusters(rootDirectory);
        trainingObject.setHashMap(start, clusterObject.getTrainingList());

        testObject.setHashMap(start, clusterObject.getTestList());
        testFeature = testObject.getList();*/
        /*//NEGATED WORD FEATURE

        start += mpqaObject.getFeatureCount();
        NegatedWord negatedWordObject = new NegatedWord(rootDirectory);
        trainingObject.setHashMap(start, negatedWordObject.getTrainingList());

        testObject.setHashMap(start, negatedWordObject.getTestList());
        testFeature = testObject.getList();


        /*
        //CHARACTER NGRAM PREFIX SIZE 2 FEATURE
        start += bingLiuObject.getFeatureCount();
        CharacterNgramPrefixSize2 cNgramObject = new CharacterNgramPrefixSize2(rootDirectory);
        trainingObject.setHashMap(start, cNgramObject.getTrainingList());

        testObject.setHashMap(start, cNgramObject.getTestList());
        testFeature = testObject.getList();

        //CHARACTER NGRAM PREFIX SIZE 3 FEATURE
        start += cNgramObject.getFeatureCount();
        CharacterNgramPrefixSize3 cNgramP3Object = new CharacterNgramPrefixSize3(rootDirectory);
        trainingObject.setHashMap(start, cNgramP3Object.getTrainingList());

        testObject.setHashMap(start, cNgramP3Object.getTestList());
        testFeature = testObject.getList();

        //CHARACTER NGRAM SUFFIX SIZE 2 FEATURE
        start += cNgramP3Object.getFeatureCount();
        CharacterNgramSuffixSize2 cNgramS2Object = new CharacterNgramSuffixSize2(rootDirectory);
        trainingObject.setHashMap(start, cNgramS2Object.getTrainingList());

        testObject.setHashMap(start, cNgramS2Object.getTestList());
        testFeature = testObject.getList();

        //CHARACTER NGRAM SUFFIX SIZE 3 FEATURE
        start += cNgramS2Object.getFeatureCount();
        CharacterNgramSuffixSize3 cNgramS3Object = new CharacterNgramSuffixSize3(rootDirectory);
        trainingObject.setHashMap(start, cNgramS3Object.getTrainingList());

        testObject.setHashMap(start, cNgramS3Object.getTestList());
        testFeature = testObject.getList();*/

        int finalSize = start + afinnObject.getFeatureCount();

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

        // load model or use it directly
        model = Model.load(modelFile);

        //Feature[] instance = new Feature[start + ngramObject.featureCount];

        /*for (int i = 0; i < testFeature.size(); i++) {
            //System.out.println();
            for (Map.Entry<Integer, Double> entry : testFeature.get(i).entrySet()) {
                instance[i] = new FeatureNode(entry.getKey(), entry.getValue());
            }
        }*/

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\predictedRestaurantsLabels.txt")));
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



        /*for (int i = 0; i < testFeature.size(); i++) {
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
            }*/

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
package de.tudarmstadt.lt.sentimentClassification;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.cli.BasicParser;

import java.io.*;
import java.util.*;


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

class ClassifierHelperH {
    List<LinkedHashMap<Integer, Double>> featureList = new ArrayList<LinkedHashMap<Integer, Double>>();
    //static List<LinkedHashMap<Integer, Double>> testFeatureList = new ArrayList<LinkedHashMap<Integer, Double>>();

    ClassifierHelperH(String dataset) throws IOException {
        BufferedReader readerTrain = new BufferedReader(new FileReader(new File(dataset)));
        //BufferedReader readerTest = new BufferedReader(new FileReader(new File(testSet)));

        String line;
        int count = 0;
        while ((line = readerTrain.readLine()) != null) {
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
        return featureList;
    }
}

public class SentimentClassifierH {
    //static int start=0;
    static String rootDirectory;
    static List<LinkedHashMap<Integer, Double>> trainingFeature;
    static List<LinkedHashMap<Integer, Double>> testFeature;


    SentimentClassifierH(int option, String trainFile, String testFile) throws IOException {

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
            if(part.length == 9) {
                String context[] = part[4].split(" ");
                part[8] = part[8].trim();
                if (context[0].compareTo("NULL") == 0) {
                    writer.println(part[3].toLowerCase());
                } else {
                    String part1 = part[3].substring(0, Integer.parseInt(part[7]));
                    String part2 = part[3].substring(Integer.parseInt(part[8]));
                    String part1Split[] = part1.split(",|;|!|\\.|-");
                    String part2Split[] = part2.split(",|;|!|\\.|-");
                    String text;
                    //System.out.println(part1Split.length+" "+part2Split.length+line);
                    //if(part1Split.length != 1  || part2Split.length != 1) {
                    if (part1Split.length > 1 && part2Split.length > 1) {
                        text = part1Split[part1Split.length - 1] + " " + part[4] + " " + part2Split[0];
                        writer.println(text.toLowerCase());
                    } else if (part1Split.length == 0 && part2Split.length > 1) {
                        text = part[4] + " " + part2Split[0];
                        writer.println(text.toLowerCase());
                    } else if (part1Split.length > 1 && part2Split.length == 0) {
                        text = part1Split[part1Split.length - 1] + " " + part[4];
                        writer.println(text.toLowerCase());
                    } else if (part1Split.length == 1 && part2Split.length > 1) {
                        text = part1Split[part1Split.length - 1] + " " + part[4] + " " + part2Split[0];
                        writer.println(text.toLowerCase());
                    } else if (part1Split.length > 1 && part2Split.length == 1) {
                        text = part1Split[part1Split.length - 1] + " " + part[4] + " " + part2Split[0];
                        writer.println(text.toLowerCase());
                    }

                    //}
                    else {
                /*else if(part1Split.length == 1  && part2Split.length > 1) {
                    text = part1Split[part1Split.length-1]+" "+part[4]+" "+part2Split[0];
                    writer.println(text.toLowerCase());
                }*/
                        String part1Context[] = part1.split(" |,|;|!|\\.|-");
                        text = "";
                        int i = part1Context.length - 1;

                /*if(part1Context.length == 0)
                {
                    i=0;
                }
                else if(part1Context.length == 1)
                {
                    i=part1Context.length-1;
                }
                else
                {
                    i=part1Context.length-2;
                }*/

                        int count = 0;
                        while (count < 2 && i >= 0) {
                            if (part1Context[i].compareTo("") == 0) {

                            } else {
                                text += " " + part1Context[i];
                                count++;
                            }
                            i--;
                        }

                        text = reverseWords(text);


                        text += " " + part[4] + " ";

                        part2 = part[3].substring(Integer.parseInt(part[8]));
                        //System.out.println(part2);
                        String part2Context[] = part2.split(" |,|;|!|\\.|-");

                /*for(int p=0; p<part2Context.length; p++)
                {
                    System.out.print(part2Context[p]+"; ");
                }*/

                        //System.out.println();

                        int k = 0;
                        count = 0;
                        while (count < 2 && k <= part2Context.length - 1) {
                            if (part2Context[k].compareTo("") == 0) {
                                ;
                            } else {
                                count++;
                                text += part2Context[k] + " ";
                            }
                            k++;
                        }

                /*for(int k=0, j=part2Context.length-1; k<2 && k<=j; k++)
                {
                    System.out.println(part2Context[k]);
                    text += part2Context[k]+" ";
                }*/

                        writer.println(text.toLowerCase());
                    }
                }
            }
            else
            {
                writer.println(part[3].toLowerCase());
            }

        }
        fR.close();
        //bf.close();
        writer.close();
    }

    private static String reverseWords(String input) {
        Deque<String> words = new ArrayDeque<String>();
        for (String word: input.split(" ")) {
            if (!word.isEmpty()) {
                words.addFirst(word);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!words.isEmpty()) {
            result.append(words.removeFirst());
            if (!words.isEmpty()) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    private int generateFeature(int option, String trainFile, String testFile) throws IOException {

        generateDataset(rootDirectory + "\\dataset\\dataset_sentimentClassification\\"+trainFile, rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Hotels_Contextual_Cleansed.txt");


        //TRAINING SET
        ClassifierHelperH trainingObject = new ClassifierHelperH(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Train_Hotels_Contextual_Cleansed.txt");

        //TESTING SET
        ClassifierHelperH testObject = null;
        if(option == 2 || option == 3)
        {
            generateDataset(rootDirectory + "\\dataset\\dataset_sentimentClassification\\"+testFile, rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Hotels_Contextual_Cleansed.txt");
            testObject = new ClassifierHelperH(rootDirectory + "\\dataset\\dataset_sentimentClassification\\Test_Hotels_Contextual_Cleansed.txt");
        }


        //POS FEATURE
        int start = 0;
        POSH posObject = new POSH(rootDirectory);
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
        testFeature = testObject.getList();


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
        EntityFeatureH entityObject = new EntityFeatureH(rootDirectory);
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

        start += nrcBiObject.getFeatureCount();
        AFINNLexicon afinnObject = new AFINNLexicon(rootDirectory);
        trainingObject.setHashMap(start, afinnObject.getTrainingList());
        if(option == 2 || option == 3) {
            testObject.setHashMap(start, afinnObject.getTestList());
        }
        //testFeature = testObject.getList();

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
        System.out.println("Hello Sentiment!");

        // Create features
        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[this.trainingFeature.size()];
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

    }
}
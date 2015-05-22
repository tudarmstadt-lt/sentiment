package de.tudarmstadt.lt.sentiment;

import de.bwaldvogel.liblinear.*;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.cli.BasicParser;


class NRCHashtag
{
    int start;
    List<HashMap<Integer, Double>> listOfMaps;
    NRCHashtag(int start, List<HashMap<Integer, Double>> listOfMaps)
    {
        this.start = start;
        this.listOfMaps = listOfMaps;
        generateFeature();
    }

    private void generateFeature()
    {
        File file = new File("E:\\COURSE\\Semeter VII\\Internship\\sentiment\\tools\\nrcHashtagLexicon\\unigrams-pmilexicon.txt");
        String line=null;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            HashMap<String, Double> scoreMap = new HashMap<String, Double>();
            while((line = reader.readLine()) != null)
            {
                String[] str = line.split("\\t");
                scoreMap.put(str[0], Double.parseDouble(str[1]));
                //System.out.println(str[1]);
            }
            reader.close();

            file = new File("E:\\COURSE\\Semeter VII\\Internship\\sentiment\\dataset\\training_set.txt");
            reader = new BufferedReader(new FileReader(file));
            //int i = 0;
            int count=0;
            //List<HashMap<Integer, Double>> listOfMaps = new ArrayList<HashMap<Integer, Double>>();
            while((line = reader.readLine()) != null)
            {
                count++;
                double pos=0, neg=0;
                String[] str = line.split("[ \t\n\\x0B\f\r]+");
                double totalScore=0.0;
                //System.out.println(str.length);       //Check the split method
                for(int i=0; i<str.length; i++)
                {
                    if(scoreMap.get(str[i]) != null)
                    {
                        double currScore = scoreMap.get(str[i]);
                        if(currScore > 0)
                        {
                            pos++;
                        }
                        else if(currScore < 0)
                        {
                            neg++;
                        }
                        totalScore += currScore;
                        //System.out.println(count);
                    }
                }

                listOfMaps.add(count-1, new HashMap<Integer,Double>());
                listOfMaps.get(count-1).put(1,totalScore);
                listOfMaps.get(count-1).put(2,pos);
                listOfMaps.get(count-1).put(3,neg);
                //System.out.println(totalScore+" "+pos+" "+neg);
            }

            System.out.println(listOfMaps.size());
            /*for(int i=0; i<listOfMaps.size(); i++)    //Print the feature values
            {
                //System.out.println(listOfMaps.get(i).size());
                //System.out.println(listOfMaps.get(i));
            }*/
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }

    public List<HashMap<Integer, Double>> getList()
    {
        //System.out.println(listOfMaps.size());
        return this.listOfMaps;
    }
}
public class Main {
    public static void main(String[] args) throws IOException {

        List<HashMap<Integer, Double>> listOfMaps = new ArrayList<HashMap<Integer, Double>>();
        NRCHashtag obj = new NRCHashtag(0, listOfMaps);

        System.out.println("Hello sentiment!");

        // Create features

        // X = HashMap<List<(int, double)>> ...

        Problem problem = new Problem();

        // Save X to problem
        double a[] = new double[obj.getList().size()];
        File file = new File("E:\\COURSE\\Semeter VII\\Internship\\sentiment\\dataset\\training_label.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String read = null;
        int count=0;
        while((read = reader.readLine()) != null)
        {
            //System.out.println(read);
            a[count++] = Double.parseDouble(read.toString());
        }

        //Feature[][] f = new Feature[][]{ {}, {}, {}, {}, {}, {} };
        Feature[][] f = new Feature[obj.getList().size()][obj.getList().get(0).size()];

        System.out.println(obj.getList().size());
        System.out.println(obj.getList().get(10).size());

        for(int i=0; i<obj.getList().size(); i++)
        {

            for(int j=0; j<obj.getList().get(i).size(); j++)
            {
                //System.out.print(listOfMaps.get(i).get(j + 1)+" ");
                f[i][j] = new FeatureNode(j+1,obj.getList().get(i).get(j+1));
            }
            //System.out.println();
        }

        problem.l = obj.getList().size(); // number of training examples
        problem.n = obj.getList().get(0).size(); // number of features
        problem.x = f; // feature nodes
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
        double eps = 0.01; // stopping criteria

        Parameter parameter = new Parameter(solver, C, eps);
        Model model = Linear.train(problem, parameter);
        File modelFile = new File("model");
        model.save(modelFile);

        // load model or use it directly
        model = Model.load(modelFile);

        Feature[] instance = { new FeatureNode(1, 4), new FeatureNode(2, 2) };
        double prediction = Linear.predict(model, instance);
    }
}

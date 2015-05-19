package de.tudarmstadt.lt.sentiment;

import de.bwaldvogel.liblinear.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by alex on 19/05/15.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello sentiment!");

        Problem problem = new Problem();
        //problem.l = ... // number of training examples
        //problem.n = ... // number of features
        //problem.x = ... // feature nodes
        //problem.y = ... // target values

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

        SolverType solver = SolverType.L2R_LR; // -s 0
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


        /*
        Expected output:

        Hello sentiment!
            Exception in thread "main" java.lang.IllegalArgumentException: problem has zero features
            at de.bwaldvogel.liblinear.Linear.train(Linear.java:1643)
            at de.tudarmstadt.lt.sentiment.Main.main(Main.java:40)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            at java.lang.reflect.Method.invoke(Method.java:606)
            at com.intellij.rt.execution.application.AppMain.main(AppMain.java:134)
        * */

    }
}

package sentimentClassifier;

import java.io.IOException;

/**
 * Created by krayush on 27-08-2015.
 */
public class SentimentWrapper {
    public static void main(String[] args) throws IOException
    {
        if(args.length > 0)
        {
            int option = Integer.parseInt(args[0]);
            if(option == 0)
            {
                SetPolarityLabels lob = new SetPolarityLabels(option, args[2], "dummyParamter");
                if(args[1].compareToIgnoreCase("h") == 0)
                {
                    SentimentClassifierHindi sob = new SentimentClassifierHindi(option, args[2], "dummyParamter");
                }
                else if(args[1].compareToIgnoreCase("b") == 0)
                {
                    SentimentClassifierBengali sob = new SentimentClassifierBengali(option, args[2], "dummyParamter");
                }
                CalculateFScore fob = new CalculateFScore();
            }
            else if(option == 2)
            {
                SetPolarityLabels lob = new SetPolarityLabels(option, args[2], "dummyParameter");
                if(args[1].compareToIgnoreCase("h") == 0)
                {
                    SentimentClassifierHindi ob = new SentimentClassifierHindi(option, args[2], args[3]);
                }
                else if(args[1].compareToIgnoreCase("b") == 0)
                {
                    SentimentClassifierBengali sob = new SentimentClassifierBengali(option, args[2], args[3]);
                }
            }
            else if(option == 3)
            {
                SetPolarityLabels lob = new SetPolarityLabels(option, args[2], args[4]);
                if(args[1].compareToIgnoreCase("h") == 0)
                {
                    SentimentClassifierHindi ob = new SentimentClassifierHindi(option, args[2], args[3]);
                }
                else if(args[1].compareToIgnoreCase("b") == 0)
                {
                    SentimentClassifierBengali sob = new SentimentClassifierBengali(option, args[2], args[3]);
                }
                CalculateFScore fob = new CalculateFScore();
            }
        }
    }

}

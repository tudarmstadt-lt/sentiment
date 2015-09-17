package de.tudarmstadt.lt.aspectCategorization;

/**
 * Created by krayush on 27-08-2015.
*/

import java.io.IOException;

public class AspectCategorizationWrapper {
    public static void main(String[] args) throws IOException
    {
        if(args.length > 0)
        {
            int option = Integer.parseInt(args[0]);
            if(option == 1)
            {
                if(args[1].compareToIgnoreCase("r") == 0)
                {
                    new ABSAPolarityRestaurants(option, args[2], "dummyParameter");
                    new ABSAClassifierRestaurants(option, args[2], "dummyParameter");
                }
                else if(args[1].compareToIgnoreCase("l") == 0)
                {
                    new ABSAPolarityLaptops(option, args[2], "dummyParameter");
                    new ABSAClassifierLaptops(option, args[2], "dummyParameter");
                }
                //new calculateAccuracy();
            }
            else if(option == 2)
            {
                if(args[1].compareToIgnoreCase("r") == 0)
                {
                    new ABSAPolarityRestaurants(option, args[2], "dummyParameter");
                    new ABSAClassifierRestaurants(option, args[2], args[3]);
                }
                else if(args[1].compareToIgnoreCase("l") == 0)
                {
                    new ABSAPolarityLaptops(option, args[2], "null");
                    new ABSAClassifierLaptops(option, args[2], args[3]);
                }
            }
            else if(option == 3)
            {
                if(args[1].compareToIgnoreCase("r") == 0)
                {
                    new ABSAPolarityRestaurants(option, args[2], args[4]);
                    new ABSAClassifierRestaurants(option, args[2], args[3]);
                    new CreateRestaurantsPredictedXML();
                }
                else if(args[1].compareToIgnoreCase("l") == 0)
                {
                    new ABSAPolarityLaptops(option, args[2], args[4]);
                    new ABSAClassifierLaptops(option, args[2], args[3]);
                    new CreateLaptopsPredictedXML();
                }
                //new calculateAccuracy();
                //CalculateFScore fob = new CalculateFScore();
            }
        }
    }

}


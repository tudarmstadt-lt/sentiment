import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by krayush on 13-07-2015.
 */
public class RemoveDuplicatesLaptopsSemEval {
    String rootDirectory;

    RemoveDuplicatesLaptopsSemEval()throws IOException
    {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\datasetConstruction\\dataset\\";

        removeDuplicates(rootDirectory, "Laptops_Test.txt", "Laptops_Test_Without_Duplicates.txt", "Laptops_Test_Duplicate_Flag.txt");
        removeDuplicates(rootDirectory, "Laptops_Train.txt", "Laptops_Train_Without_Duplicates.txt", "Laptops_Train_Duplicate_Flag.txt");
    }

    void removeDuplicates(String root, String original, String modifiedFile, String flagFile)throws IOException
    {
        FileReader fR = new FileReader(root+original);
        PrintWriter modified = new PrintWriter(root+modifiedFile);
        PrintWriter flag = new PrintWriter(root+flagFile);
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;
       ListMultimap<String, String> tags = ArrayListMultimap.create();

        while((line = bf.readLine()) != null)
        {
            line = line.trim();
            String[] tokens = line.split("\\|");
            if(tokens.length == 4)
            {
                flag.write(tokens[0]+"|"+tokens[1]+"|"+"0"+"\n");
            }
            else
            {
                if(tags.containsKey(tokens[1]) && tags.get(tokens[1]).contains(tokens[4]))
                {
                    flag.write(tokens[0]+"|"+tokens[1]+"|"+"0"+"\n");
                }
                else
                {
                    flag.write(tokens[0]+"|"+tokens[1]+"|"+"1"+"\n");
                    modified.write(line+"\n");
                    tags.put(tokens[1], tokens[4]);
                }
            }
        }

        modified.close();
        flag.close();
    }

    public static void main(String[] args)throws  IOException
    {
        RemoveDuplicatesLaptopsSemEval ob = new RemoveDuplicatesLaptopsSemEval();
    }
}

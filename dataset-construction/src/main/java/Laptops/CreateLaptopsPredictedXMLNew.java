package Laptops;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by krayush on 22-07-2015.
 */
public class CreateLaptopsPredictedXMLNew {
    String rootDirectory;

    CreateLaptopsPredictedXMLNew() throws IOException {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\dataset-construction\\dataset\\";

        create(rootDirectory, "predictedLaptopsLabels.txt", "Laptops_Test.xml", "pred.xml");
    }

    void create(String root, String restLabel, String goldXML, String predictedXML) throws IOException {
        FileReader fR1 = new FileReader(root + "LaptopsPairs.txt");

        BufferedReader bf1 = new BufferedReader(fR1);

        HashMap<String, String> pair = new HashMap<String, String>();
        String line;
        int count=1;
        while ((line = bf1.readLine()) != null) {
            line = line.trim();
            pair.put(count+"", line);
            count++;
        }


        FileReader fR = new FileReader(root + goldXML);
        PrintWriter modified = new PrintWriter(root + predictedXML);

        FileReader fR2 = new FileReader(root + restLabel);
        //FileReader fR2 = new FileReader(root + svmlabels);

        BufferedReader bf = new BufferedReader(fR);
        BufferedReader bf2 = new BufferedReader(fR2);
        //BufferedReader bf2 = new BufferedReader(fR2);

        //String line;

        while ((line = bf.readLine()) != null) {
            if(line.contains("<Opinions>"))
            {
                modified.println("\t\t\t\t<Opinions>");
                String labelLine;
                //System.out.println(line);
                //bf2.readLine().
                while(!((labelLine = bf2.readLine()).contains("next")))
                {
                    //System.out.println(labelLine);
                    if(labelLine.compareToIgnoreCase("-1") != 0)
                    {
                        String category = pair.get(labelLine);
                        System.out.println(category);
                        String part1 = "\t\t\t\t\t"+"<Opinion category=\"";
                        String part2 = "polarity=\"positive\"/>";
                        modified.println(part1+ category+"\" "+part2);
                    }
                }
                modified.println("\t\t\t\t</Opinions>");
                while(!bf.readLine().contains("</Opinions>"))
                {
                    ;
                }
            }
            else
            {
                //System.out.println(line);
                modified.println(line);
            }
        }
        modified.close();
        bf.close();
        bf1.close();
        bf2.close();
    }

    public static void main(String[] args)throws IOException
    {
        CreateLaptopsPredictedXMLNew ob = new CreateLaptopsPredictedXMLNew();
    }
}

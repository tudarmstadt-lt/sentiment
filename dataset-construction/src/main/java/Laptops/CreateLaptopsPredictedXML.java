import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by krayush on 13-07-2015.
 */
public class CreateLaptopsPredictedXML {
    String rootDirectory;

    CreateLaptopsPredictedXML() throws IOException {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\datasetConstruction\\dataset\\";

        create(rootDirectory, "predictedLaptops.txt", "Laptops_Test.xml", "pred.xml");
    }

    void create(String root, String restLabel, String goldXML, String predictedXML)throws IOException {
        FileReader fR = new FileReader(root + goldXML);
        PrintWriter modified = new PrintWriter(root + predictedXML);

        FileReader fR1 = new FileReader(root + restLabel);
        //FileReader fR2 = new FileReader(root + svmlabels);

        BufferedReader bf = new BufferedReader(fR);
        BufferedReader bf1 = new BufferedReader(fR1);
        //BufferedReader bf2 = new BufferedReader(fR2);

        String line;

        while((line = bf.readLine()) != null)
        {
            //line = line.trim();
            if(line.contains("polarity="))
            {
                int start1 = line.indexOf("category=");
                int start2 = line.indexOf("polarity=");
                modified.write(line.substring(0,start1)+"category="+'"'+bf1.readLine().split("\\|")[3]+'"'+" "+line.substring(start2, line.length())+"\n");
            }
            else
            {
                modified.write(line+"\n");
            }
        }
        modified.close();
    }

    public static void main(String[] args)throws IOException
    {
        CreateLaptopsPredictedXML ob = new CreateLaptopsPredictedXML();
    }
}

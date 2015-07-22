import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by krayush on 13-07-2015.
 */
public class CreateLaptopsPolarityFile {
    String rootDirectory;

    CreateLaptopsPolarityFile() throws IOException {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\datasetConstruction\\dataset\\";

        create(rootDirectory, "predictedLaptops.txt", "predictedLaptopsLabels.txt", "Laptops_Test_Duplicate_Flag.txt");
    }

    void create(String root, String restLabel, String svmlabels, String flag)throws IOException
    {
        FileReader fR = new FileReader(root+flag);
        PrintWriter modified = new PrintWriter(root+restLabel);

        FileReader fR1 = new FileReader(root+"LaptopsPairs.txt");
        FileReader fR2 = new FileReader(root+svmlabels);

        BufferedReader bf = new BufferedReader(fR);
        BufferedReader bf1 = new BufferedReader(fR1);
        BufferedReader bf2 = new BufferedReader(fR2);

        HashMap<String, String>pair = new HashMap<String, String>();
        String line;
        double count=1.0;
        while((line = bf1.readLine()) != null)
        {
            //String tokens[] = line.split("\\|");
            line=line.trim();
            pair.put(count+"", line);
            count++;

            //System.out.println(line+count);
        }

        while((line=bf.readLine()) != null)
        {
            String tokens[] = line.split("\\|");
            if(tokens[2].compareToIgnoreCase("0") == 0)
            {
                modified.write(line+"|abc"+"\n");
            }
            else
            {
                //String x = bf2.readLine().trim();
                //System.out.println(pair.get(x));
                //System.out.println(bf2.readLine());
                modified.write(line+"|"+pair.get(bf2.readLine())+"\n");
            }
        }

        modified.close();
    }

    public static void main(String[] args)throws  IOException
    {
        CreateLaptopsPolarityFile ob = new CreateLaptopsPolarityFile();
    }
}

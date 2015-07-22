import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by krayush on 13-07-2015.
 */
public class CreateRestaurantsPolarityFile {
    String rootDirectory;

    CreateRestaurantsPolarityFile() throws IOException {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\datasetConstruction\\dataset\\";

        create(rootDirectory, "predictedRestaurants.txt", "predictedRestaurantsLabels.txt", "Restaurants_Test_Duplicate_Flag.txt");
    }

    void create(String root, String restLabel, String svmlabels, String flag)throws IOException
    {
        FileReader fR = new FileReader(root+flag);
        PrintWriter modified = new PrintWriter(root+restLabel);

        FileReader fR1 = new FileReader(root+"RestPairs.txt");
        FileReader fR2 = new FileReader(root+svmlabels);

        BufferedReader bf = new BufferedReader(fR);
        BufferedReader bf1 = new BufferedReader(fR1);
        BufferedReader bf2 = new BufferedReader(fR2);

        HashMap<String, String>pair = new HashMap<String, String>();
        String line;
        while((line = bf1.readLine()) != null)
        {
            String tokens[] = line.split("\\|");
            pair.put(tokens[1], tokens[0]);
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
                modified.write(line+"|"+pair.get(bf2.readLine())+"\n");
            }
        }

        modified.close();
    }

    public static void main(String[] args)throws  IOException
    {
        CreateRestaurantsPolarityFile ob = new CreateRestaurantsPolarityFile();
    }
}

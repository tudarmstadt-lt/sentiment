import java.io.*;

/**
 * Created by krayush on 13-07-2015.
 */
public class CorrectEVLaptops {

    String rootDirectory;

    CorrectEVLaptops()throws IOException
    {
        rootDirectory = "D:\\Course\\Semester VII\\Internship\\datasetConstruction\\dataset\\";

        removeDuplicates(rootDirectory, "EV_Test_Laptops.txt", "EV_Test_Laptops_Without_Duplicates.txt", "Test_Laptops_Cleansed.txt", "Laptops_Test_Duplicate_Flag.txt");
        removeDuplicates(rootDirectory, "EV_Train_Laptops.txt", "EV_Train_Laptops_Without_Duplicates.txt", "Train_Laptops_Cleansed.txt", "Laptops_Train_Duplicate_Flag.txt");
    }

    void removeDuplicates(String root, String duplicateEV, String newEV, String duplicateData, String flag)throws IOException
    {
        FileReader fR = new FileReader(root+flag);
        FileReader fR1 = new FileReader(root+duplicateData);
        FileReader fR2 = new FileReader(root+duplicateEV);


        BufferedReader bf = new BufferedReader(fR);
        BufferedReader bf1 = new BufferedReader(fR1);
        BufferedReader bf2 = new BufferedReader(fR2);

        PrintWriter modified = new PrintWriter(root+newEV);
        //PrintWriter flag = new PrintWriter(root+flagFile);
        String line;
        String line1;
        String line2;
        int check=1; //if 1 then line in duplicateData is to be read
        line1 = bf1.readLine();
        line2 = bf2.readLine();
        while((line = bf.readLine()) != null && line1 != null)
        {
            line = line.trim();
            String tokens[] = line.split("\\|");

            //if(check == 1)
            {
                //line1 = bf1.readLine();
                String tokens1[] = line1.split("\\|");

                if(tokens[0].compareToIgnoreCase(tokens1[0])==0 && tokens[1].compareToIgnoreCase(tokens1[1]) == 0)
                {
                    if(tokens[2].compareToIgnoreCase("1") == 0)
                    {
                        modified.write(line2+"\n");
                    }
                    line1 = bf1.readLine();
                    line2 = bf2.readLine();
                }
            }
        }
        modified.close();
    }

    public static void main(String[] args)throws IOException
    {
        CorrectEVLaptops ob = new CorrectEVLaptops();
    }
}

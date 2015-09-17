package datasetConstruction;

import java.io.*;

/**
 * Created by krayush on 18-07-2015.
 */
public class GenerateBengaliDataset {
    public static void main(String[] args)throws IOException
    {
        String root = "D:\\Course\\Semester VII\\Internship\\IL Sentiment Analysis Folder 2\\Training Data\\BN_Train_SAIL2015_Tweets";

        createDataset(root+"\\bengaliTraining.txt", root+"\\BN_POS.TXT");
        createDataset(root+"\\bengaliTraining.txt", root+"\\BN_NEG.TXT");
        createDataset(root+"\\bengaliTraining.txt", root+"\\BN_NEU.TXT");

    }

    public static void createDataset(String trainFile, String fileName)throws IOException
    {
        File fR = new File(fileName);
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));
        String line="";
        while((line = br.readLine())!=null) {
            //String words[]=line.split("\t");
            line = line.trim();
            String word[] = line.split("\\t");
            //line.con
            //if(line.contains("|1|") || line.contains("|0|") || line.contains("|-1|"))
            {
                file_append(trainFile, line+"\n");
            }
            /*else
            {
                file_append(trainFile, " "+line);
            }*/
        }

    }

    public static void file_append(String path,String txt)
    {
        try
        {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(txt);
            //fbw.newLine();
            fbw.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

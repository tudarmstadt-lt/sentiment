package datasetConstruction;

import java.io.*;

/**
 * Created by krayush on 18-07-2015.
 */
public class GenerateCleansedHindiSet {
    public static void main(String[] args)throws IOException
    {
        String root = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian\\dataset";
        generateCleanSet(root+"\\hindiTraining.txt", root+"\\hindiCleansedTraining.txt");
    }

    private static void generateCleanSet(String fileName, String cleanFile)throws IOException
    {
        File fR = new File(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));

        Writer writer = new OutputStreamWriter(new FileOutputStream(cleanFile), "UTF-8");
        BufferedWriter fbw = new BufferedWriter(writer);

        String line="";
        while((line = br.readLine())!=null) {
            //line.re
            String tokens[] = line.split("\\|", 3);
            tokens[2] = tokens[2].replaceAll("http://t.co/[a-zA-Z0-9-.]{10}", "http://someurl");
            tokens[2] = tokens[2].replaceAll("@[a-zA-Z0-9_]+", "@someuser");
            tokens[2] = tokens[2].replaceAll("[0-9]+", "100");
            //tokens[2] = tokens[2].replaceAll("^[0-9]+\\|-1\\|", "");

            fbw.write(tokens[0]+"|"+tokens[1]+"|"+tokens[2]+"\n");
        }

        fbw.close();
    }
}

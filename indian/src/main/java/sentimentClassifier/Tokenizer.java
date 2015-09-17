package sentimentClassifier;

import java.io.*;

/**
 * Created by krayush on 27-08-2015.
 */
public class Tokenizer {

    String rootDirectory;
    Tokenizer(String inputFile, String outputFile) throws IOException
    {
        rootDirectory = System.getProperty("user.dir");
        tokenize(rootDirectory+"\\dataset\\"+inputFile, rootDirectory+"\\dataset\\"+outputFile);
    }

    private void tokenize(String np, String op) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(np)), "UTF-8"));

        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(op)), "UTF-8");
        BufferedWriter fbw = new BufferedWriter(writer);

        String line;
        
        while((line = br.readLine()) != null)
        {
            line = line.replaceAll("http://t.co/[a-zA-Z0-9-.]{10}", "someurl");
            line = line.replaceAll("@[a-zA-Z0-9_]+", "someuser");
            //line = line.replaceAll("[0-9]+", "100");
            //" |,|\\t|<|>|;|:|\'|\"|\\||-|(|)|!"
            String tokens[] = line.split(" |,");

            for(int i=0; i<tokens.length; i++)
            {
                fbw.write(tokens[i]+" ");
            }
            fbw.write("\n");
        }
        fbw.close();
    }

    public static void main(String[] args)throws IOException
    {
        Tokenizer ob = new Tokenizer("demoTraining.txt", "demoTest.txt");
    }

}

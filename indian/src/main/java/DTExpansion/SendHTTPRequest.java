package DTExpansion;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by krayush on 29-06-2015.
 */
public class SendHTTPRequest {
    String rootDirectory;
    SendHTTPRequest()throws IOException
    {
        rootDirectory = System.getProperty("user.dir");
        mainFunction(rootDirectory);
    }

    //public static void main(String[] args)throws IOException {
    private void mainFunction(String rootDirectory)throws IOException
    {
        File fR = new File(rootDirectory+"\\resources\\sentimentSeedWordsHindi.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fR), "UTF-8"));

        String line = null;
        int count=1;
        while((line = bf.readLine()) != null)
        {
            //fout.write(line);
            String tokens[] = line.split("\\|");
            String url = URLEncoder.encode(tokens[0], "UTF-8");
            //System.out.println(url);
            URL oracle = new URL("http://maggie.lt.informatik.tu-darmstadt.de:10080/jobim/ws/api/hindiBigram/jo/similar/"+url+"?numberOfEntries=100&format=tsv");
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            Writer writer = new OutputStreamWriter(
                    new FileOutputStream(rootDirectory+"\\resources\\DTExpansion\\HTTPResults\\"+count+".txt"), "UTF-8");
            BufferedWriter fout = new BufferedWriter(writer);
            while ((inputLine = in.readLine()) != null)
            {
                //System.out.println(inputLine);
                String words[] = inputLine.split("\\t");
                if(words.length>=2)
                {
                    fout.write(words[0]+"\t"+words[1]+"\n");
                }
            }
            count++;
            fout.close();
        }
        bf.close();
        System.out.println(count);
        //getTopWords(count);
        //fout.close();
    }
}

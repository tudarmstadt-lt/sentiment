package DTExpansion;

import java.net.*;
import java.io.*;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
        URL oracle = new URL("http://maggie.lt.informatik.tu-darmstadt.de:10080/jobim/ws/api/hindiBigram/jo/similar/%E0%A4%86%E0%A4%AA%E0%A4%95%E0%A5%87?numberOfEntries=50&format=tsv");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        Writer writer = new OutputStreamWriter(
                new FileOutputStream("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt"), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        while ((inputLine = in.readLine()) != null)
        {
            //System.out.println(inputLine);
            fout.write(inputLine+"\n");
        }
            //System.out.println(inputLine);
        fout.close();
        in.close();
    }
}
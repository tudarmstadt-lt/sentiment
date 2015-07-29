package DTExpansion;

import java.io.*;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class maggieTUD {
    public static void main(String[] args)throws IOException
    {
        File fR = new File("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentWords.txt");
        //PrintWriter writer = new PrintWriter("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt");
        BufferedReader bf = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(fR), "UTF-8")
                                );
        //BufferedWriter wr = new BufferedWriter(fW);
        Writer writer = new OutputStreamWriter(
                new FileOutputStream("D:\\Course\\Semester VII\\Internship\\Results\\Maggie TUD\\sentimentJavaWords.txt"), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        String line = null;
        while((line = bf.readLine()) != null)
        {
                fout.write(line);
                System.out.println(line);
        }
        bf.close();
        fout.close();
    }
}

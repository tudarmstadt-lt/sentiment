package de.tudarmstadt.lt;

import org.apache.xpath.SourceTree;

import java.io.*;

/**
 * Created by krayush on 29-07-2015.
 */
public class SetRootDirectory {
    public static void main(String[] args)
    {
        try
        {
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("rootDir.txt")));
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the roor directory (eg: \"D:\\Course\\Semester VII\\Internship\\sentiment\\english\")");
            write.println(read.readLine());
            write.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}

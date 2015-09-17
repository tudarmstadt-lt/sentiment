package de.tudarmstadt.lt.aspectCategorization;

import java.io.IOException;

/**
 * Created by krayush on 27-08-2015.
 */
public class UsingJarFile {
    public static void main(String[] args)throws IOException
    {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java -cp ./A.jar absa15.Do Eval ./pred.xml ./teGld.xml 1 0");
    }
}

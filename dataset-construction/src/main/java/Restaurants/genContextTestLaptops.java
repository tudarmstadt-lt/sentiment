
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author krayush
 */
public class genContextTestLaptops {
    public static void main(String[] args)throws IOException
    {
        FileReader fR = new FileReader("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Test\\ABSA15_LaptopsTest\\Test_Laptops_Cleansed.txt");
            PrintWriter writer = new PrintWriter("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Test\\ABSA15_LaptopsTest\\Test_Laptops_Contextual_Cleansed.txt");
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;
        while((line = bf.readLine()) != null)
        {
            String[] part = line.split("\\|");
            writer.println(part[3].toLowerCase());

        }
        fR.close();
        //bf.close();
        writer.close();
        //wr.close();
    }

    public static String reverseWords(String input) {
        Deque<String> words = new ArrayDeque<String>();
        for (String word: input.split(" ")) {
            if (!word.isEmpty()) {
                words.addFirst(word);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!words.isEmpty()) {
            result.append(words.removeFirst());
            if (!words.isEmpty()) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author krayush
 */
public class genTrainRestaurants {
    public static void main(String[] args)throws IOException
    {
        FileReader fR = new FileReader("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Train\\ABSA15_RestaurantsTrain\\Train_Restaurants.txt");
        PrintWriter writer = new PrintWriter("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Train\\ABSA15_RestaurantsTrain\\Train_Restaurants_Cleansed.txt");
        PrintWriter writer1 = new PrintWriter("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Train\\ABSA15_RestaurantsTrain\\Train_Restaurants_Reviews.txt");
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;
        while((line = bf.readLine()) != null)
        {
            String[] part = line.split("\\|");
            //System.out.println(part[0]+part[1]+part[3]);
            
            //System.out.println(part.length);
            
            if(part.length == 4)
            {
                ;
            }
            else
            {
                System.out.println(line);
                writer.println(line);
                writer1.println(part[3]);
            }
        }
        fR.close();
        //bf.close();
        writer.close();
        writer1.close();
        //wr.close();
    }
}

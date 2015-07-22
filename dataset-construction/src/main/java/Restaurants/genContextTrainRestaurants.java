
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
public class genContextTrainRestaurants {
    public static void main(String[] args)throws IOException
    {
        FileReader fR = new FileReader("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Train\\ABSA15_RestaurantsTrain\\Train_Restaurants_Cleansed.txt");
        PrintWriter writer = new PrintWriter("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Train\\ABSA15_RestaurantsTrain\\Train_Restaurants_Contextual_Cleansed.txt");
        BufferedReader bf = new BufferedReader(fR);
        //BufferedWriter wr = new BufferedWriter(fW);
        String line = null;
        while((line = bf.readLine()) != null)
        {
            String[] part = line.split("\\|");
            //System.out.println(part[0]+part[1]+part[3]);
            
            //System.out.println(part.length);
            
            /*List<String> items = Arrays.asList(part[3].split(" |,|;|!|\\.|-"));
            String words[] = part[3].split(" ");
            String context[] = part[4].split(" ");
            
            if(context[0].compareTo("NULL") == 0)
            {
                writer.println(part[3]);
            }
            else 
            {
                System.out.println(part[1]+": "+context[0]+" "+items.contains(context[0]));
            }*/
            String context[] = part[4].split(" ");
            part[8]=part[8].trim();
            if(context[0].compareTo("NULL") == 0)
            {
                writer.println(part[3].toLowerCase());
            }
            else
            {
                String part1 = part[3].substring(0,Integer.parseInt(part[7]));
                String part1Context[] = part1.split(" |,|;|!|\\.|-");
                
                String text = "";
                int i = part1Context.length-1;
                 
                /*if(part1Context.length == 0)
                {
                    i=0;
                }
                else if(part1Context.length == 1)
                {
                    i=part1Context.length-1;
                }
                else
                {
                    i=part1Context.length-2;
                }*/
                
                int count=0;
                while(count<2 && i>=0)
                {
                    if(part1Context[i].compareTo("") == 0)
                    {
                        
                    }
                    else
                    {
                        text += " "+part1Context[i];
                        count++;
                    }
                    i--;
                }
                
                text = reverseWords(text);
   
                
                text += " "+part[4]+" ";
                
                String part2 = part[3].substring(Integer.parseInt(part[8]));
                //System.out.println(part2);
                String part2Context[] = part2.split(" |,|;|!|\\.|-");
                
                /*for(int p=0; p<part2Context.length; p++)
                {
                    System.out.print(part2Context[p]+"; ");
                }*/
                
                //System.out.println();
                
                int k=0;
                count=0;
                while(count<2 && k<=part2Context.length-1)
                {
                    if(part2Context[k].compareTo("") == 0)
                    {
                        ;
                    }
                    else
                    {
                        count++;
                        text += part2Context[k]+" ";
                    }
                    k++;
                }
                
                /*for(int k=0, j=part2Context.length-1; k<2 && k<=j; k++)
                {
                    System.out.println(part2Context[k]);
                    text += part2Context[k]+" ";
                }*/
                
                writer.println(text.toLowerCase());
                //System.out.println(text.toLowerCase());
                //System.out.println(part2);
            }
            
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


import java.io.*;

public class genTestHotels {
    public static void main(String[] args)throws IOException
    {
        FileReader fR = new FileReader("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Test\\ABSA15_HotelsTest\\Test_Hotels.txt");
        PrintWriter writer = new PrintWriter("D:\\COURSE\\Semester VII\\Internship\\SemEval ABSA Submission 2015\\Test\\ABSA15_HotelsTest\\Test_Hotels_Cleansed.txt");
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
            }
        }
        fR.close();
        //bf.close();
        writer.close();
        //wr.close();
    }
}

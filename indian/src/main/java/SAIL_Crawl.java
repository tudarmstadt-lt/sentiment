
import java.io.*;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.ConfigurationBuilder;


public class SAIL_Crawl
{
    //////here you have to have your own consumerKey, consumerSecret, token, and tokenSecret
    /////Get your authentication details at: https://dev.twitter.com/
    public static String consumerKey = "n9Ro54tSLcFqAeCwKwhm0kbvg";
    public static String consumerSecret = "bajjm6a01Hp7TtDs2bkT8jq7hhNpzuGnBhex44xnaTgvi5DQFj";
    public static String token = "566399289-qpeWB1FlRkJ4pRjQ998zSR9uhiC0bS7EUINnn4Jy";
    public static String tokenSecret = "q06bCP8mhaCtWGtT2p0H4m8EZJO2fH2RRjtamxMRF5bQO";


    public static void main(String args[])
    {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken accessToken = new AccessToken(token,tokenSecret);
        twitter.setOAuthAccessToken(accessToken);

        try
        {
            //////Change input file name
            FileInputStream fis = new FileInputStream("D:\\Course\\Semester VII\\Internship\\Hindi Sentiment Analysis Folder 2\\Training Data\\HI_Train_SAIL2015\\HI_NEU.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line="";
            while((line = br.readLine())!=null)
            {
                //String words[]=line.split("\t");
                line = line.trim();

                try
                {
                    Status status = twitter.showStatus(Long.parseLong(line));

                    if (status == null)
                    {
                    }
                    else
                    {
                        System.out.println(status.getText());
                        //////Change output file name
                        file_append("D:\\Course\\Semester VII\\Internship\\Hindi Sentiment Analysis Folder 2\\Training Data\\HI_Train_SAIL2015\\HI_NEU_TWEET.txt",line+"|0|"+status.getText());
                    }
                }catch(Exception e)
                {
                    System.err.println(e);
                }
            }
            fis.close();
        }catch(IOException f){}
    }
    public static void file_append(String path,String txt)
    {
        try
        {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(txt);
            fbw.newLine();
            fbw.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


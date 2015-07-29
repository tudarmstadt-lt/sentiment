package de.tudarmstadt.lt.sentiment;

import java.io.*;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.ConfigurationBuilder;


public class SAIL_Crawl
{
    //////here you have to have your own consumerKey, consumerSecret, token, and tokenSecret
    /////Get your authentication details at: https://dev.twitter.com/
    public static String consumerKey = "E7fZUt26B6N1UAtsBx8kcmt0S";
    public static String consumerSecret = "RZzRjAxvSW3NHlmVGGyAJhN5ih2NlMqkcWMfatmpVj5fW3UI9W";
    public static String token = "566399289-6SWgm6YeJ2yoD7VCoRDWslhV4CNUiXKzdk53q530";
    public static String tokenSecret = "J7O0hIHM2ZNlWMVzOTW77I3lWj1wPVOPw3dtkm79Oh39w";


    public static void main(String args[])
    {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken accessToken = new AccessToken(token,tokenSecret);
        twitter.setOAuthAccessToken(accessToken);

        try
        {
            //////Change input file name
            FileInputStream fis = new FileInputStream("D:\\Course\\Semester VII\\Internship\\sentiment\\dataset\\BN_SENTI_TRAIL_60.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line="";
            while((line = br.readLine())!=null)
            {
                String words[]=line.split("\t");

                try
                {
                    Status status = twitter.showStatus(Long.parseLong(words[0]));

                    if (status == null)
                    {
                    }
                    else
                    {
                        System.out.println(status.getText());
                        //////Change output file name
                        file_append("D:\\Course\\Semester VII\\Internship\\sentiment\\dataset\\BN_SENTI_TRAIL_60_TWEET.txt",words[0]+"\t"+status.getText()+"\t"+words[1]);
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


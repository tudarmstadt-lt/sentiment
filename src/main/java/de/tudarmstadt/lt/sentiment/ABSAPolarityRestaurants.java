package de.tudarmstadt.lt.sentiment;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityRestaurants {

    private void generateTestLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Test_Restaurants_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\testLabels.txt")));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words[5].compareTo("RESTAURANT#GENERAL") == 0) {          //words[5] for laptop set
                    write.println("1");
                } else if (words[5].compareTo("SERVICE#GENERAL") == 0) {
                    write.println("2");
                } else if (words[5].compareTo("FOOD#QUALITY") == 0) {
                    write.println("3");
                } else if (words[5].compareTo("FOOD#STYLE_OPTIONS") == 0) {
                    write.println("4");
                } else if (words[5].compareTo("DRINKS#STYLE_OPTIONS") == 0) {
                    write.println("5");
                } else if (words[5].compareTo("DRINKS#PRICES") == 0) {
                    write.println("6");
                } else if (words[5].compareTo("RESTAURANT#PRICES") == 0) {
                    write.println("7");
                } else if (words[5].compareTo("AMBIENCE#GENERAL") == 0) {
                    write.println("8");
                } else if (words[5].compareTo("RESTAURANT#MISCELLANEOUS") == 0) {
                    write.println("9");
                } else if (words[5].compareTo("FOOD#PRICES") == 0) {
                    write.println("10");
                } else if (words[5].compareTo("LOCATION#GENERAL") == 0) {
                    write.println("11");
                } else if (words[5].compareTo("DRINKS#QUALITY") == 0) {
                    write.println("12");
                } else if (words[5].compareTo("FOOD#GENERAL") == 0) {
                    write.println("13");
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void generateTrainingLabels() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\Train_Restaurants_Cleansed.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter("D:\\COURSE\\Semester VII\\Internship\\sentiment\\dataset\\trainingLabels.txt")));

            while ((line = read.readLine()) != null) {
                line = line.replace("\n", "").replace("\r", "");
                String words[] = line.split("\\|");
                if (words[5].compareTo("RESTAURANT#GENERAL") == 0) {          //words[5] for laptop set
                    write.println("1");
                } else if (words[5].compareTo("SERVICE#GENERAL") == 0) {
                    write.println("2");
                } else if (words[5].compareTo("FOOD#QUALITY") == 0) {
                    write.println("3");
                } else if (words[5].compareTo("FOOD#STYLE_OPTIONS") == 0) {
                    write.println("4");
                } else if (words[5].compareTo("DRINKS#STYLE_OPTIONS") == 0) {
                    write.println("5");
                } else if (words[5].compareTo("DRINKS#PRICES") == 0) {
                    write.println("6");
                } else if (words[5].compareTo("RESTAURANT#PRICES") == 0) {
                    write.println("7");
                } else if (words[5].compareTo("AMBIENCE#GENERAL") == 0) {
                    write.println("8");
                } else if (words[5].compareTo("RESTAURANT#MISCELLANEOUS") == 0) {
                    write.println("9");
                } else if (words[5].compareTo("FOOD#PRICES") == 0) {
                    write.println("10");
                } else if (words[5].compareTo("LOCATION#GENERAL") == 0) {
                    write.println("11");
                } else if (words[5].compareTo("DRINKS#QUALITY") == 0) {
                    write.println("12");
                } else if (words[5].compareTo("FOOD#GENERAL") == 0) {
                    write.println("13");
                }
            }
            read.close();
            write.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ABSAPolarityRestaurants obj = new ABSAPolarityRestaurants();
        obj.generateTrainingLabels();
        obj.generateTestLabels();
        //obj.generateTestLabels();
    }

}

package de.tudarmstadt.lt.aspectCategorization;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityRestaurants {

    //String rootDirectory = "D:\\COURSE\\Semester VII\\Internship\\sentiment\\english";

    private void generateTestLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Test_ABSA.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\testLabels.txt")));

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

    private void generateTrainingLabels(String rootDirectory) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(rootDirectory + "\\dataset\\dataset_aspectCategorization\\Restaurants_Train_ABSA.txt")));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(rootDirectory + "\\dataset\\trainingLabels.txt")));

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
        String rootDirectory = System.getProperty("user.dir");
            /*File file = new File("rootDir.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                rootDirectory = line;
                System.out.println("Root Directory is: " + rootDirectory);
            }*/
        ABSAPolarityRestaurants obj = new ABSAPolarityRestaurants();
        obj.generateTrainingLabels(rootDirectory);
        obj.generateTestLabels(rootDirectory);
        //obj.generateTestLabels();
        //obj.generateTestLabels();
    }

}

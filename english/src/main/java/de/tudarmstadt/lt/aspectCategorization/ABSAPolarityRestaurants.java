package de.tudarmstadt.lt.aspectCategorization;

import java.io.*;

/**
 * Created by krayush on 02-Jun-15.
 */
public class ABSAPolarityRestaurants {

    String rootDirectory;

    ABSAPolarityRestaurants(int option, String trainFileName, String testFileName) {
        rootDirectory = System.getProperty("user.dir");
        mainFunction(option, trainFileName, testFileName);
    }

    private void generateTestLabels(String dataFile, String labelFile) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));

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

    private void generateTrainingLabels(String dataFile, String labelFile) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File(dataFile)));
            String line = null;
            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(labelFile)));

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

    private void mainFunction(int option, String trainingFileName, String testFileName) {

        //String rootDirectory = "D:\\Course\\Semester VII\\Internship\\sentiment\\indian";
        //System.out.println(args.length);
        //String rootDirectory = System.getProperty("user.dir");;
        //SetPolarityLabels this = new SetPolarityLabels();
        this.generateTrainingLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + trainingFileName, rootDirectory + "\\dataset\\trainingLabels.txt");
        if (option == 1) {
            this.generateTestLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + trainingFileName, rootDirectory + "\\dataset\\testLabels.txt");
        } else if (option == 2) {

        } else if (option == 3) {
            this.generateTestLabels(rootDirectory + "\\dataset\\dataset_aspectCategorization\\" + testFileName, rootDirectory + "\\dataset\\testLabels.txt");
            //this.generateTestLabels(rootDirectory + "\\dataset\\Gold Set\\HI_Test_Gold.txt", rootDirectory + "\\dataset\\testLabels.txt");
        }

    }
}

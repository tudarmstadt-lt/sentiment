package de.tudarmstadt.lt.sentiment;

import org.apache.commons.cli.*;

public class SentimentGraphBuilder {
    boolean printLine = true;

    public SentimentGraphBuilder(boolean printLine) {
        this.printLine = printLine;
    }

    public static void main(final String[] args) throws Exception {
        CommandLineParser parser = new GnuParser();
        Options options = new Options();
        options.addOption( "h", "help", false, "show help");
        options.addOption( "o", "output", true, "Output file");
        // 1) Add new CLI parameters here

        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h") || line.getArgs().length < 1) {
                HelpFormatter formater = new HelpFormatter();
                formater.printHelp("[options] text1 text2 ...", options);
                System.exit(0);
            }

            // ner = line.hasOption("ner") ? Boolean.parseBoolean(line.getOptionValue("ner")) : false;
            String outputPath = line.hasOption("o") ? line.getOptionValue("o") : "";
            // 2) Add new CLI parameters here

            System.out.println("Output file: " + outputPath);
            // 3) Add new CLI parameters here

            for(String path : line.getArgs()){
                System.out.println("Input file: " + path);
            }

            SentimentGraphBuilder jbf = new SentimentGraphBuilder(true);

            for(String in : line.getArgs()){
                String out = outputPath.equals("") ? in + ".csv" : outputPath;
                boolean append = line.getArgs().length > 1;
                //jbf.processFile(in, out, append);
                System.out.println("Building sentiment graphs...");

                // Insert useful code here
                // LDA lda = new ...
                //lda.process(inputFile, outputFile)
                //Graph g = new ...
                //g.process(outputFile, outputFile2)
                // ...

                System.out.println("Done!");
            }
        } catch (Exception exp) {
            System.out.println("Runtime error.");
            exp.printStackTrace();
        }

    }

}

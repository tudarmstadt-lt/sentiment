 
import org.apache.commons.cli.*;
 
public class CCC {
    boolean printLine = true;
    boolean offset = true;
    boolean morphAnalysis = false;
    boolean globalSimilarTerms = false;
    boolean lowercaseLemma = true;
    boolean printSimilarity = false;
    boolean wsd = true;
    boolean computeDependencies = true;
    boolean collapseDependencies = true;
    boolean ner = false;
    boolean twsi = false;
    boolean semantifyDependencies = true;
    
    public CCC(boolean printLine, boolean offset, boolean morphAnalysis, boolean globalSimilarTerms,
                         boolean lowercaseLemma, boolean printSimilarity, String dtType, boolean wsd, int maxBims,
                         boolean computeDependencies, boolean ner, boolean twsi) {
        this.printLine = printLine;
        this.offset = offset;
        this.morphAnalysis = morphAnalysis;
        this.globalSimilarTerms = globalSimilarTerms;
        this.lowercaseLemma = lowercaseLemma;
        this.printSimilarity = printSimilarity;
        this.dtType = dtType;
        this.wsd = wsd;
        this.maxBims = maxBims;
        this.computeDependencies = computeDependencies;
        this.ner = ner;
        this.twsi = twsi;
        init();
    }
 
    public static void main(final String[] args) throws Exception {
        CommandLineParser parser = new GnuParser();
        Options options = new Options();
        options.addOption( "h", "help", false, "show help");
        options.addOption( "o", "output", true, "Output file");
        options.addOption( "off", "offset", true, "Print offset.");
        options.addOption( "t", "print-text", true, "Print input text" );
        options.addOption( "m", "morphology", true, "Morphological analysis");
        options.addOption( "g", "global-similarity", true, "Global similarities");
        options.addOption( "l", "lowercase", true, "Lowercase lemmas");
        options.addOption( "s", "similarity-score", true, "Print similarity score");
        options.addOption( "jbt", "jobimtext-backend", true, "Type of JoBimText backend server.");
        options.addOption( "dp", "dependency-parse", true, "Dependency parsing.");
        options.addOption( "wsd", "word-sense-disambiguation", true, "Enable contextualization (WSD).");
        options.addOption( "mb", "max-bims", true, "Max number of contextualization features (Bims).");
        options.addOption( "ner", "named-entity-recognition", true, "Named entity recognition.");
        options.addOption( "twsi", "twsi-substitution", true, "TWSI lexical substitution.");
        options.addOption( "tt", "tab-tab", true, "Use double tab as separator and tab as secondary separator.");
 
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h") || line.getArgs().length < 1) {
                HelpFormatter formater = new HelpFormatter();
                formater.printHelp("[options] text1 text2 ...", options);
                System.exit(0);
            }
 
            boolean ner = line.hasOption("ner") ? Boolean.parseBoolean(line.getOptionValue("ner")) : false;
            boolean printText = line.hasOption("t") ? Boolean.parseBoolean(line.getOptionValue("t")) : true;
            boolean offset = line.hasOption("off") ? Boolean.parseBoolean(line.getOptionValue("off")) : true;
            boolean morphoAnalysis = line.hasOption("m") ? Boolean.parseBoolean(line.getOptionValue("m")) : true;
            boolean globalSim = line.hasOption("g") ? Boolean.parseBoolean(line.getOptionValue("g")) : true;
            boolean lowerLemma = line.hasOption("l") ? Boolean.parseBoolean(line.getOptionValue("l")) : true;
            boolean depParse = line.hasOption("dp") ? Boolean.parseBoolean(line.getOptionValue("dp")) : false;
            boolean printSimilarity = line.hasOption("s") ? Boolean.parseBoolean(line.getOptionValue("s")) : false;
            String outputPath = line.hasOption("o") ? line.getOptionValue("o") : "";
            boolean wsd = line.hasOption("wsd") ? Boolean.parseBoolean(line.getOptionValue("wsd")) : false;
            int maxBims = line.hasOption("mb") ? Integer.parseInt(line.getOptionValue("mb")) : 10;
            boolean doubleTabs = line.hasOption("tt") ? Boolean.parseBoolean(line.getOptionValue("tt")) : false;
            boolean twsi = line.hasOption("twsi") ? Boolean.parseBoolean(line.getOptionValue("twsi")) : false;
            List<String> dtOptions = Arrays.asList("web", "db", "dca");
            String dtType = line.hasOption("jbt") ? line.getOptionValue("jbt") : "db";
            if (!dtOptions.contains(dtType)) {
                log.info("Error: wrong argument of 'jbt': " + dtType);
                return;
            }
 
            log.info("Output file: " + outputPath);
            log.info("Print input text: " + printText);
            log.info("Print input offset: " + offset);
            log.info("Morphologial analysis: " + morphoAnalysis);
            log.info("Global similar terms: " + globalSim);
            log.info("Lowercase lemmas: " + lowerLemma);
            log.info("Print similarity score: " + printSimilarity);
            log.info("Type of JoBimText backend: " + dtType);
            log.info("Contextualization (WSD): " + wsd);
            log.info("Contextualization, max number of Bims: " + maxBims);
            log.info("Double tabs: " + doubleTabs);
            log.info("Dependency parse: " + depParse);
            log.info("Named entity recognition: " + ner);
            log.info("TWSI: " + twsi);
            for(String path : line.getArgs()){
                log.info("Input file: " + path);
            }
 
            CCC jbf = new CCC(printText, offset, morphoAnalysis, globalSim, lowerLemma,
                    printSimilarity, dtType, wsd, maxBims, depParse, ner, twsi);
 
            for(String in : line.getArgs()){
                String out = outputPath.equals("") ? in + ".csv" : outputPath;
                boolean append = line.getArgs().length > 1;
                jbf.processFile(in, out, append);
                if (!doubleTabs) {
                    JntUtils.tabs2commas(out, out + ".tmp");
                    (new File(out)).delete();
                    (new File(out + ".tmp")).renameTo(new File(out));
                }
                log.info("Output file: " + out);
            }
        } catch (Exception exp) {
            log.info("Runtime error.");
            exp.printStackTrace();
        }
 
    }
 
}

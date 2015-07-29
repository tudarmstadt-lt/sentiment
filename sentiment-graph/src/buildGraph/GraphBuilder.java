package buildGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphBuilder {

    public static void main(String[] args) throws IOException {

        int min_s_freq = Integer.valueOf(args[1]);
        int min_s_tfidf = Integer.valueOf(args[2]);
        int min_p_freq = Integer.valueOf(args[3]);
        int min_p_tfidf = Integer.valueOf(args[4]);
        int min_t_freq = Integer.valueOf(args[5]);
        int min_t_tfidf = Integer.valueOf(args[6]);
        int max_adj_freq = Integer.valueOf(args[7]);

        File[] listOfDepFiles = new File(args[0] + "/triple_tfidf").listFiles();
        String fnum;
        Map<String, Integer> opinions = new HashMap<String, Integer>();
        Scanner opinion_words = new Scanner(new File(args[0] + "/sentiment/opinion_words.txt"));
        opinion_words.useDelimiter("\n");
        int ii = 0;
        while (opinion_words.hasNext()) {
            opinions.put(opinion_words.next().trim(), ii);
            ii++;
        }
        opinion_words.close();
        for (int i = 0; i < listOfDepFiles.length; i++) {
            Map<String, ArrayList<String>> single = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> single_all = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> pair = new HashMap<String, ArrayList<String>>();
            ArrayList<String[]> triple = new ArrayList<String[]>();

            String line;
            String[] pairs = new String[3];
            fnum = listOfDepFiles[i].getName().replaceAll("[a-zA-Z\\s+\\{Punct}\\.\\_]", "").trim();
            System.out.println(fnum);
            Scanner single_tfidf_file = new Scanner(new File(args[0] + "/word_pos_freq_tfidf/word_pos_freq_tfidf" + fnum + ".txt"));
            Scanner pair_tfidf_file = new Scanner(new File(args[0] + "/pairs_tfidf/pairs_tfidf" + fnum + ".txt"));
            Scanner triple_tfidf_file = new Scanner(new File(args[0] + "/triple_tfidf/triple_tfidf" + fnum + ".txt"));

            FileWriter graph_structure = new FileWriter(args[0] + "/graphs/Graph" + fnum + ".txt");
            single_tfidf_file.useDelimiter("\n");
            pair_tfidf_file.useDelimiter("\n");
            triple_tfidf_file.useDelimiter("\n");

            while (single_tfidf_file.hasNext()) {
                line = single_tfidf_file.next().trim();
                pairs = line.split("\t");
                ArrayList<String> list = new ArrayList<String>();
                if (pairs.length >= 4) {

                    list.add(pairs[1].trim());
                    list.add(pairs[2].trim());
                    list.add(pairs[3].trim());
                    single_all.put(pairs[0].trim(), list);
                    if (Double.valueOf(pairs[1]) > min_s_freq && Double.valueOf(pairs[2]) >= min_s_tfidf)
                        single.put(pairs[0].trim(), list);

                }


            }

            single_tfidf_file.close();
            System.out.println("size after tfidf filtering:" + single.size());
            while (pair_tfidf_file.hasNext()) {
                line = pair_tfidf_file.next().trim();
                pairs = line.split("\t");
                ArrayList<String> list = new ArrayList<String>();
                if (pairs.length >= 4 && Double.valueOf(pairs[1]) > min_p_freq && Double.valueOf(pairs[3]) >= min_p_tfidf) {
                    list.add(pairs[1]);
                    list.add(pairs[3]);
                    pair.put(pairs[0], list);
                }
            }
            pair_tfidf_file.close();

            while (triple_tfidf_file.hasNext()) {
                line = triple_tfidf_file.next().trim();
                pairs = line.split("\t");
                ArrayList<String> list = new ArrayList<String>();
                String[] first = pairs[0].split(" ");
                String[] arr = new String[2];
                if (first.length >= 3) {
                    String relation = first[0].trim();
                    String source = first[1].trim();
                    String dist = first[2].trim();
                    list.add(source);
                    list.add(dist);
                    Collections.sort(list);
                    arr[0] = list.get(0) + " " + list.get(1);
                    arr[1] = relation;
                    triple.add(arr);
                }
            }
            triple_tfidf_file.close();
            triple_tfidf_file = new Scanner(new File(args[0] + "/triple_tfidf/triple_tfidf" + fnum + ".txt"));
            triple_tfidf_file.useDelimiter("\n");
            while (triple_tfidf_file.hasNext()) {
                line = triple_tfidf_file.next().trim();
                pairs = line.split("\t");
                boolean is_noun_compoud = false;
                ArrayList<String> list = new ArrayList<String>();
                String[] first = pairs[0].split(" ");
                if (pairs.length >= 4 && Double.valueOf(pairs[1]) > min_t_freq && Double.valueOf(pairs[3]) >= min_t_tfidf) {
                    if (first.length >= 3) {
                        String relation = first[0].trim();
                        String source = first[1].trim();
                        String dist = first[2].trim();
                        list.add(source);
                        list.add(dist);
                        Collections.sort(list);

                        String s_d = list.get(0) + " " + list.get(1);
                        for (int lc = 0; lc < triple.size(); lc++) {
                            String[] check = triple.get(lc);
                            if (check[0].equals(s_d) && check[1].equals("nn")) {
                                is_noun_compoud = true;
                            }
                        }
                        String pos_source = single_all.get(source).get(2);
                        String pos_dist = single_all.get(dist).get(2);
                        double freq_source = Double.valueOf(single_all.get(source).get(1));
                        double freq_dist = Double.valueOf(single_all.get(dist).get(1));
                        if (!source.equals(dist) && source.length() > 1 && dist.length() > 1 && !is_noun_compoud && (opinions.containsKey(source) || opinions.containsKey(dist))) {
                            if (((single.containsKey(source) || single.containsKey(dist)) && (relation.equals("amod") || relation.equals("nsubj")) && pair.containsKey(s_d)) || ((pos_source.equals("jj") && pos_dist.equals("nn") && freq_source > max_adj_freq) || (pos_dist.equals("jj") && pos_source.equals("nn") && freq_dist > max_adj_freq))) {
                                graph_structure.write("\"" + source + "\" -> " + "\"" + dist + "\"" + "[label=\"" + relation + "-" + pairs[1] + "\"]" + ";\n");
                            }
                        }
                    }
                }
            }
            triple_tfidf_file.close();
            graph_structure.close();
        }


    }

}

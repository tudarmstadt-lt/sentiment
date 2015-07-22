package de.tudarmstadt.lt.sentiment;

import java.io.*;
import java.util.*;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.*;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCreationUtils;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
//import org.apache.uima.fit.factory.*;
// Custor lemmatizer:
//import de.tudarmstadt.lt.wsi.StanfordLemmatizer;

//https://github.com/tudarmstadt-lt/joint/blob/master/jobimificator/src/main/java/de/tudarmstadt/lt/wsi/StanfordLemmatizer.java

public class Lemmatize {

    AnalysisEngine segmentEngine;
    AnalysisEngine posEngine;
    AnalysisEngine lemmatizeEngine;
    JCas jCas;

    public Lemmatize(){
        try {
            segmentEngine = AnalysisEngineFactory.createEngine(StanfordSegmenter.class);
            posEngine = AnalysisEngineFactory.createEngine(OpenNlpPosTagger.class);
            lemmatizeEngine = AnalysisEngineFactory.createEngine(StanfordLemmatizer.class);
            jCas = CasCreationUtils.createCas(createTypeSystemDescription(), null, null).getJCas();

        } catch (ResourceInitializationException e) {
            log.error("Couldn't initialize analysis engine:", e);
        } catch (CASException e) {
            log.error("Couldn't create new CAS:", e);
        } catch (Exception e) {
            log.error("Exception:", e);
        }
    }

    public void process(String text, FileWriter output) throws IOException, InterruptedException {
        try {
            jCas.reset();
            jCas.setDocumentText(text);
            jCas.setDocumentLanguage("en");
            segmentEngine.process(jCas);

            posEngine.process(jCas);
            lemmatizeEngine.process(jCas);
            
            for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
                Collection<Token> tokens = JCasUtil.selectCovered(jCas, Token.class, sentence.getBegin(), sentence.getEnd());

                for (Token token : tokens) {
                    String surface = text.substring(token.getBegin(), token.getEnd());
                    String lemma = "";
                    String pos = "";
                    lemma = token.getLemma().getValue();
                    if (lowercaseLemma) lemma = lemma.toLowerCase();
                    pos = token.getPos().getPosValue();
                    output.write(lemma + "#" + pos + "\n");
                }
            }

        } catch (Exception e) {
            log.error("Can't process line: " + text, e);
        }
    }
}

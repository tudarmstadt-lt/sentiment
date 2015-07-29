package filter;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import reader.StanfordReader;
import writer.StanfordOutWriter;


	public class StanfordePipline
	{

	    public static void main(String[] args)
	        throws UIMAException, IOException
	    {

	        CollectionReader stanfordReade = createReader(StanfordReader.class,
	                StanfordReader.PARAM_DIRECTORY_NAME, "C://Users//skohail//Desktop//PhD//complete Data//reviewsfile");

	        AnalysisEngine stanfordannotator = createEngine(StanfordSegmenter.class, StanfordSegmenter.PARAM_CREATE_SENTENCES,false);

	        AnalysisEngine stanfordWriter = createEngine(StanfordOutWriter.class);

	        SimplePipeline.runPipeline(stanfordReade, stanfordannotator, stanfordWriter);
	    }
	}




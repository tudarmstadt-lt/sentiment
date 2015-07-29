package writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class StanfordOutWriter
extends JCasConsumer_ImplBase
{

public static final String LF = System.getProperty("line.separator");

@Override
public void process(JCas jcas)
    throws AnalysisEngineProcessException
{

    String filter = "";
    int track=0;
    try {
        filter = FileUtils.readFileToString(new File("data/list.txt"));
    }
    catch (IOException e2) {
        // TODO Auto-generated catch block
        e2.printStackTrace();
    }
    Set<String> filters = new HashSet<String>();
    for(String s:filter.split("\n")){
    	filters.add(s.trim());
    }
    FileWriter os = null;
    try {
        os = new FileWriter(new File("C:\\Users\\skohail\\Desktop\\files\\filteredreviewC.txt"));
    }
    catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
    	if (track%100000==0)
    		System.out.println("w"+track);
        StringBuffer sb = new StringBuffer();

        for (Token token : JCasUtil.selectCovered(Token.class, sentence)) {
            if (!filters.contains(token.getCoveredText().trim())) {
                sb.append(token.getCoveredText() + " ");
            }
        }
        try {
            IOUtils.write(sb.toString().trim() + "\n", os);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        track++;
    }
    
    try {
		os.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}

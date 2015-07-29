package reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;



import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public class StanfordReader extends JCasCollectionReader_ImplBase {

	public static final String PARAM_DIRECTORY_NAME = "DirectoryName";
	@ConfigurationParameter(name = PARAM_DIRECTORY_NAME,
			description = "The name of the directory of text files to be read",
			mandatory = true)
	private File dir;

	List<File> documents;
	int i = 0;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		documents = new ArrayList<File>(FileUtils.listFiles(dir,
				new String[] { "txt" }, false));
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return i < documents.size();
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(i, documents.size(),
				Progress.ENTITIES) };
	}

	@Override
	public void getNext(JCas aJcas) throws IOException, CollectionException {
		File f = documents.get(i);
		LineIterator it = FileUtils.lineIterator(f);
		int start =0;
		int inds=0;
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
		    String line = it.nextLine();
		    Sentence sent = new Sentence(aJcas, start, start+line.length());
		    sent.addToIndexes();
		    start = start + line.length() + 1;
		    sb.append(line+"\n");
		    if (inds%10000==0)
		    	System.out.println("R"+inds);
		}
		aJcas.setDocumentText(sb.toString());
		//had to add english as default language, one could also add another configuration parameter
		aJcas.setDocumentLanguage("en");
		i++;
	}

}

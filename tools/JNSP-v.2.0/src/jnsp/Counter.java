/**
 * Copyright (C) 2008 by
 * 
 * 	Cam-Tu Nguyen
 *  ncamtu@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *  
 *  Thu-Trang Nguyen
 *  trangnt84@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *
 * JNSP is a free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JNSP is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JNSP; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package jnsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import viet.text.parsing.filter.AndFilter;
import viet.text.parsing.filter.MarkFilter;
import viet.text.parsing.filter.NumberFilter;
import viet.text.parsing.filter.RegexFilter;
import viet.text.parsing.filter.StopWordFilter;
import viet.text.parsing.filter.TokenFilter;
import viet.text.parsing.filter.TokenLengthFilter;


public class Counter {
	//---------------------------------------------------
	// Member Variables
	//---------------------------------------------------
	
	NGramSet ngramSet;	
	
	Dictionary dict;

	//int nActiveNGram = 0;
	
	NSPOption option;	

	static TokenFilter tokenFilter;	

	int window;
	
	//---------------------------------------------------
	// Constructors
	//---------------------------------------------------
	public Counter(NSPOption option){
		
		this.option = option;
		System.out.println("###############window:" + option.window);
		dict = new Dictionary();
		
		window = option.window;		
		ngramSet = new NGramSet(option,dict);
		
//		TokenFilter [] filterArray = {new StopWordFilter(),
//				  new MarkFilter(),
//				  new NumberFilter(),
//				  new RegexFilter(".*[0-9,\\.;:?/\\\\%$@!\\[\\]]+.*"),
//				  new TokenLengthFilter(2)};
//		this.setTokenFilter(new AndFilter(filterArray));
	}	
	
	public Counter(NSPOption option, Dictionary dict){
		
		this.option = option;
		this.dict = dict;
		window = option.window;
		
		ngramSet = new NGramSet(option,dict);

	}
	
	//---------------------------------------------------
	// Get and Set Methods
	//---------------------------------------------------
	public static void setTokenFilter(TokenFilter tokenFilter){
		Counter.tokenFilter = tokenFilter;
	}

	public static TokenFilter getTokenFilter(){
		return tokenFilter;
	}
	
	public NGramSet getNGramSet(){
		return ngramSet;
	}
	
	public Map<Integer, Vector<Long>> genMultiGram(Document doc, int window){	
		Map <Integer, Vector<Long>> ngramset = new HashMap <Integer, Vector<Long>>() ;		
		Map<Integer, Vector<String[]>> docngrams = doc.shiftWindows(window);				
		
		for(int i=1;  i<= window; i++){
			ngramset.put(i, new Vector<Long>());
		}

		for( int w = 1 ; w <= window ; w++){
			for ( String[] itr: docngrams.get(w)){
				if (tokenFilter != null){
					boolean filtered = false;
					for (int i = 0; i < itr.length; ++i)
						if (!tokenFilter.accept(itr[i])){
							//System.out.println("---filtered: " + itr[i]);
							filtered = true;
							break;
						}				
					if (filtered) continue;
				}
				
				int[] tokenids = new int[itr.length];								
				for ( int i = 0; i < itr.length; ++i){
					tokenids[i] = dict.addWord(itr[i]);					
				}
				
				NGram ngram  = null;				
				if (NGram.cache) ngram = new NGram(w,itr,dict);
				else ngram = new NGram(w,tokenids,dict);				
				
				long ngramid = ngramSet.addNgram(ngram);
				
				if (ngramid != -1){
					ngramset.get(w).add(ngramid);
					
					//System.out.println("hello:" + ngramSet.getNGram(ngramid).toString());
					ngramSet.getNGram(ngramid).frequency++;				
				}
			}
		}
		return ngramset;
	}
	
	public Map<Integer, Vector<Long>> genMultiGram(String doc, int window){
		Document document = new Document(doc);
		//System.out.println("genMulti:" + window);
		return genMultiGram(document, window);
	}
	
	public Vector<Long> genNGram(Document doc, int window){		
		
		Vector<String[]> docngrams = doc.shiftWindow(window);		
		
		Vector<Long> ngrams = new Vector<Long>();		
	
		for (String[] itr: docngrams){					
			//filter ngrams with one token in stop list if neccessary
			if (tokenFilter != null){
				boolean filtered = false;
				for (int i = 0; i < itr.length; ++i)
					if (!tokenFilter.accept(itr[i])){
						filtered = true;
						break;
					}				
				if (filtered) continue;
			}
			
			int[] tokenids = new int[itr.length];			
			
			for (int i = 0; i < itr.length; ++i){
				String tk = itr[i];
				
				Integer a= dict.addWord(tk);				
				tokenids[i] = a;				
			}
			
			NGram ngram  =null;
			
			if (NGram.cache){
			//	System.out.println("cache");
				ngram = new NGram(window, itr,dict);
			}
			else ngram = new NGram(window,tokenids,dict);
			
			long ngramid = ngramSet.addNgram(ngram);
			
			ngrams.add(ngramid);			
			ngramSet.getNGram(ngramid).frequency++;
		}	
		return ngrams;
	}
	
	public Vector<Long> genNGram(String doc, int ngram){		
		Document document = new Document(doc);
		return genNGram(document, ngram);		
	}

	
	//---------------------------------------------------
	// IO Methods
	//---------------------------------------------------
	private void readDocs(File dataDir) throws Exception{
		try {
			List<File> files = Util.getFileListing(dataDir);
			
			for (int i = 0; i < files.size(); ++i){
				//System.out.println(ngrams.size() + "-Reading " + files.get(i).getPath());
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(files.get(i)), "UTF-8"));
				
				String content = "";
				String line = ""; int count = 0;
				while((line = reader.readLine()) != null){
					count++;
					if (count <= 4) continue;
					
					content += line + "\n";
				}
				
				content = content.trim();
				Document doc = new Document(content);
				
				if (option.agressiveCount){
					System.out.println("aggressive");
					genMultiGram(doc, window);
				}
				else
					genNGram(doc, window);
				if (i%100 == 0) {
					if (i%5000==0 && i > 0){
						freqCutOff();
						rareCutOff();
						System.out.println("------------------------------");
					}
					
					System.gc();
					System.out.println(i + " doc completed ...........");
					System.out.println("NGramSet size:" + ngramSet.size());
					System.out.println("Token dict size:" + ngramSet.dict.word2id.size());					
					
				}				
								
				reader.close();
			}			
		}
		catch (Exception e){
			throw e;
		}		
	}
	
	public void count(){		
		try{		
			readDocs(option.dataDir);			
			ngramSet.nTotalActiveGram = ngramSet.size();
			
			System.out.println("Ngram size before cutting off:" + ngramSet.size());
			freqCutOff();			
			rareCutOff();
			
			System.out.println("Ngram size after cutting off:" + ngramSet.nTotalActiveGram);
			
			genNGramFreq();	
			if (option.agressiveCount){
				for (int i = 1; i <= window; ++i)
					writeCnt(option.cntFile, i);
			}
			else writeCnt(option.cntFile, window);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Writing couting results to file
	 */
	
	private void writeCnt(String cntFilename, int w){		
			try {			
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(cntFilename+w+".cnt"), "UTF-8"));
				writer.write(ngramSet.window2nActiveGram.get(w) + "\n");
				
				//System.out.println("window: " + w);
				NSIterator it = ngramSet.getNSIterator(w);
				
				while (it.hasNext()){					
					NGram ngram = ngramSet.getNGram((Long)it.next());
					writer.write(ngram.getNGramStat() + "\n");
				}			
										
				
				writer.close();
			}
			catch (Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
		}
	}
	
	//---------------------------------------------------
	// Counting
	//---------------------------------------------------
	public void genNGramFreq(){
		//init all counts in ngrams		
		NSIterator iter = ngramSet.getNSIterator();		
		while(iter.hasNext()){
			//System.out.println("before:");
			Long i = iter.next();
			
			//System.out.println("i:" + i);
			NGram ngram = ngramSet.getNGram(i);						
			ngram.initCounts(ngramSet.window2freqCombo.get(ngram.N));
		}
		
		if (option.agressiveCount)
			for(int i=1; i<=window; i++)
				genNGramFreq(i);
		else genNGramFreq(window);
	}
	
	private void genNGramFreq(int w){		
		String[] freqCombo = ngramSet.window2freqCombo.get(w);		
		
		// generate a vector of position array
		Vector<Integer[]> freqPositionVector = new Vector<Integer[]>();
		
		for (int i = 0; i < freqCombo.length; ++i){
			// gen corresponding postions
			
			StringTokenizer tk = new StringTokenizer(freqCombo[i], ":");
			if (tk.countTokens() > w || tk.countTokens() == 0){
				continue;
			}
			
			Integer [] positions = new Integer[tk.countTokens()];
			for (int j = 0; j < positions.length; ++j){			
				try {
					int pos = Integer.parseInt(tk.nextToken());				
					if (pos < 0 || pos >= w)
						continue;	
					
					positions[j] = pos;
				}
				catch (NumberFormatException e){
					System.out.println(e.getMessage());
					e.printStackTrace();
					continue;
				}
			}
			
			freqPositionVector.add(positions);
		}
		
		// generate Ngram frequency				
		int nGenedNgram = 0;
				
		NSIterator iter = ngramSet.getNSIterator(w);
		
		while (iter.hasNext()){			
			NGram curNgram = ngramSet.getNGram(iter.next());
			
			//	generate frequency acc. to different combinations
			
			Set<Long> relatedNgrams = new HashSet<Long>();
			for (int j = 0; j < curNgram.size(); ++j){
				String token = curNgram.getTokenId(j) + ":" + j;
				Vector<Long> relatedList = ngramSet.invertedIndecies.get(token);
				
				for (int k = 0; k < relatedList.size(); ++k){
					NGram gram = ngramSet.getNGram(relatedList.get(k));
					if (gram == null)
						continue;
					else if (gram.size() == w){
						//if (gram.size() != 3) System.out.println("hello" + gram.size());
						relatedNgrams.add(gram.ID);
					}
				}
			}
			//System.out.print("curNgram "+curNgram.ID+" " +freqPositionVector.size()+" ");
			
			for (int j = 0; j < freqPositionVector.size(); ++j){			
				Integer [] positions = freqPositionVector.get(j);
				if (positions.length == w){
					curNgram.counts[j] += curNgram.frequency;
					continue;
				}
				
				Iterator<Long> it = relatedNgrams.iterator();
				
				while (it.hasNext()){
					NGram relatedNgram = ngramSet.getNGram(it.next());
					if (relatedNgram == null) // if this ngram has been cut off
						continue;

					if (relatedNgram.checkNGram(positions, curNgram)){
					//	System.out.println("len:" + curNgram.counts.length + " " + j);
						curNgram.counts[j] += relatedNgram.frequency;
					}
				}
			}
			
			nGenedNgram++;
			if (nGenedNgram % 1000 == 0)
				System.out.println(nGenedNgram + " " + w + "grams completed");
		}	
	}
	
	//-------------------------------------------
	// Cut-off
	//-------------------------------------------
	public void freqCutOff(){
		if (option.freqCutOff > 0){
			System.out.println("before freq cutoff:" + ngramSet.nTotalActiveGram);
			
			int nCuttOff = 0;
			Vector<Long> grams = ngramSet.getNGramIDs();			
			for (int i = 0; i < grams.size(); ++i){
				long id = grams.get(i);
				NGram curNGram = ngramSet.getNGram(id);				
				if (curNGram.frequency > option.freqCutOff || curNGram.frequency >= ngramSet.size()){
					//System.out.println("cutoff:" + curNGram.toString());
					ngramSet.removeNgram(id);
					nCuttOff++;
				}
			}
			
			System.out.println("Number of freqcutoff:" + nCuttOff);
			System.out.println("After cutoff:" + ngramSet.nTotalActiveGram);
		}
	}
	
	public void rareCutOff(){
		if (option.rareCutOff > 0){
			//System.out.println("before rare cutoff:" + ngramSet.nTotalActiveGram);
			int nCutOff = 0;
			
			Vector<Long> grams = ngramSet.getNGramIDs();			
			for (int i = 0; i < grams.size(); ++i){
				long id = grams.get(i);
				NGram curNGram = ngramSet.getNGram(id);
			
				if (curNGram.frequency < option.rareCutOff){
					//System.out.println("cutoff:" + curNGram.toString());
					ngramSet.removeNgram(id);
					nCutOff++;
				}
			}
			
			System.out.println("Number of RareCutoff:" + nCutOff);
			System.out.println("after rare cutoff:" + ngramSet.nTotalActiveGram);
		}
	}
	
	//--------------------------------------------
	// MAIN METHOD
	//--------------------------------------------
	public static void main(String[] args ){		
		NSPOption option = new NSPOption();
		
		option = NSPOption.readOption(args[0]);
		try {
			
			TokenFilter [] filterArray = {new StopWordFilter(option.stopFile),
			  new MarkFilter(),
			  new NumberFilter(),
			  new RegexFilter(".*[0-9,\\.;:?/\\\\%$@!\\[\\]]+.*"),
			  new TokenLengthFilter(2)};
			Counter.setTokenFilter(new AndFilter(filterArray));
			
			long startTime = (new Date()).getTime();
			
			Counter c = new Counter(option);
			c.count();
			
			long endTime = (new Date()).getTime();
			System.out.println("counting in " + (endTime - startTime) + "miliseconds");
		}
		catch (Exception e){
			System.out.println(e.getMessage());		
		}
		
	}
}

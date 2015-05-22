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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class NGramSet {
	
	//----------------------------------------------------
	// Member Variables
	//----------------------------------------------------
	int window;
	
	Dictionary dict; //word-token dict
	
	// Dictionary : NGgram	<-> ngramId
	HashMap<Long, NGram> gramid2gram;
	
	HashMap<NGram, Long> gram2gramid;
	
	Hashtable<Integer, Vector<Long>> window2gramIds;// 1-uni; 2-bi; 3-tri
	
	Hashtable<Integer, Integer> window2nActiveGram;

	int nTotalActiveGram = 0;
	
	//from token:position to indies of ngrams containing the token at the same position
	HashMap<String, Vector<Long>> invertedIndecies;
	
	long  globalIndx = 0;
	
	// freq_combo
	Hashtable<Integer, String[]> window2freqCombo;// 1-uni; 2-bi; 3-tri
	
	boolean initialized = false;

	NSPOption option;	
	
	//TokenFilter tokenFilter;
	
	//----------------------------------------------------
	// Constructors
	//----------------------------------------------------
	public NGramSet(NSPOption option, Dictionary dict){
		this.option = option;
		this.dict = dict;
		window = option.window;
		
		init();
	}
	public NGramSet(NSPOption option){
		this.option = option;
		dict = new Dictionary();
		window = option.window;
		
		init();
	}
	
	private void init(){
		try {				
			if (option.ngramCache)
				NGram.cache = true;
			
			window2freqCombo = Util.readFreqCombos(option.freqComboFile);
			if (window2freqCombo == null){
				this.window2freqCombo = new Hashtable<Integer, String[]>();
			}
			
			for (int i = 1; i <= window; ++i){
				if (!window2freqCombo.containsKey(i))
					window2freqCombo.put(i, Util.genDefaultFreqCombo(i));
			}
			
			gramid2gram = new HashMap<Long, NGram> ();
			gram2gramid = new HashMap<NGram, Long> ();
			
			window2gramIds = new Hashtable<Integer, Vector<Long>>();					
			window2nActiveGram = new Hashtable<Integer, Integer>();
			
			if (option.agressiveCount){
					for( int i = 1; i <= option.window; i++){					
					window2gramIds.put(i, new Vector<Long>());
					window2nActiveGram.put(i, 0);
				}
			}
			else {
				window2gramIds.put(option.window, new Vector<Long>());
				window2nActiveGram.put(option.window, 0);
			}
			
			invertedIndecies = new HashMap<String, Vector<Long>>();
			
			initialized = true;			
			
		}
		catch (Exception e){
			System.out.println("Error while init counter " + e.getMessage());
			e.printStackTrace();
			initialized = false;
		}
	}
	
	
	//----------------------------------------------------
	// Get, Set Methods
	//----------------------------------------------------
	public int size(){
		return nTotalActiveGram;
	}
		
	
	protected Vector<Long> getNGramIDs(){
		Vector<Long> ret = new Vector<Long>();
		
		NSIterator it = getNSIterator();
		while (it.hasNext()){
			ret.add(it.next());
		}
		return ret;
	}
	
	protected Vector<Long> getNGramIDs(int window){	
		return window2gramIds.get(window);		
	}
	
	protected Iterator<Long> getGramIDIterator(int window){
		return window2gramIds.get(window).iterator();
	}
	
	protected Iterator<Long> getGramIDIterator(){		
		return gramid2gram.keySet().iterator();
	}
	
	public NSIterator getNSIterator(){
		return new NSIterator(getGramIDIterator(), this, nTotalActiveGram);
	}
	
	public NSIterator getNSIterator(int window){
		return new NSIterator(getGramIDIterator(window), this, window2nActiveGram.get(window));
	}
	
	public Dictionary getTokenDictionary(){
		return dict;
	}
	
	public long getNGramID(String gramStr){
		String [] strArray = gramStr.split(NGram.jointStr);
		NGram ngram = new NGram(strArray.length, strArray, this.dict);
		
		return gram2gramid.get(ngram);
	}
	
	public long addNgram(String gramStr){
		String [] tokens = gramStr.split(" ");
		NGram ngram = new NGram(tokens.length, tokens, dict);
		
		return addNgram(ngram);
	}
	
	public boolean removeNgram(Long gramid){
		NGram ngram = this.getNGram(gramid);
		if (ngram != null){
			gramid2gram.remove(gramid);
			gram2gramid.remove(ngram);
			
			nTotalActiveGram--;
			window2nActiveGram.put(ngram.size(), window2nActiveGram.get(ngram.size()) - 1);
			return true;
		}
		else return false;
	}
	
	public long addNgram(NGram ngram){		
		if (!gram2gramid.containsKey(ngram)){
			ngram.ID = ++globalIndx;			
			gramid2gram.put(ngram.ID, ngram);			
			gram2gramid.put(ngram,ngram.ID);
			
			if (window2gramIds.get(ngram.size())== null){
				Vector<Long> indx = new Vector<Long>();
				indx.add(ngram.ID);
				
				//System.out.println("test:"  + window2nActiveGram.get(ngram.size()) + ngram.size());
				
				window2gramIds.put(ngram.size(), indx);				
				window2nActiveGram.put(ngram.size(), window2nActiveGram.get(ngram.size()) + 1);
				
				
				nTotalActiveGram++;
			}
			else {
				window2gramIds.get(ngram.size()).add(ngram.ID);
				window2nActiveGram.put(ngram.size(), window2nActiveGram.get(ngram.size()) + 1);
				nTotalActiveGram++;
			}
				
			
			//update inverted index
			for (int k = 0; k < ngram.size(); ++k){
				String tokenid = String.valueOf(ngram.getTokenId(k));
				
				tokenid += ":" + k; // token:pos				
				Vector<Long> linkedList;
				if (invertedIndecies.containsKey(tokenid)){						
					linkedList = invertedIndecies.get(tokenid);
					
					linkedList.add(ngram.getID());
				}
				else {
					linkedList = new Vector<Long>();
					linkedList.add(ngram.getID());
					invertedIndecies.put(tokenid, linkedList);
				}					
			}				
			return globalIndx;
		}
		else {
			//System.out.println("exist");
			return gram2gramid.get(ngram);//gramid2gram.get(gram2gramid.get(ngram));
		}
	}
	
	public NGram getNGram(long ngramid){		
		return gramid2gram.get(ngramid);				
	}	

	//----------------------------------------------------
	// For testing
	//----------------------------------------------------
	public void print(){
		Vector<Long> gramID2 = getNGramIDs(2);
		for (int i = 0; i < gramID2.size(); ++i){
			NGram ngram = getNGram(gramID2.get(i));
			if (ngram == null)
				continue;
			System.out.println(ngram.toString() + " " + ngram.counts[0] + " " + ngram.counts[1] + " " + ngram.counts[2]);
		}
	}
	
	public void print(long gramid){
			NGram ngram = getNGram(gramid);
			if (ngram == null)
				return;
			System.out.println(ngram.toString() + " " + ngram.getCount(0) + " " + ngram.counts[1] + " " + ngram.counts[2]);
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

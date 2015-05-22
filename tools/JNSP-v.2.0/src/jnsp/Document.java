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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Document {
	Vector<String> [] chunks;
	
	int N;
	int chunkIndex = 0;
	int chunkOffset = 0;
	
	public static String tokenRegex = " "; //separated by white space by default
		
	boolean toLower = true;// non case-sensitive by default
	int length; //sum of lengths of chunks
	
	//public Dictionary dict;
	
	// Constructor
	public Document(String doc, boolean toLower){		
		this.toLower = toLower;
		 
		init(doc);
	}
	
	public Document(String doc){
		
		init(doc);
	}
	
	@SuppressWarnings("unchecked")
	protected void init(String doc){
		//long startTime = (new Date()).getTime();
		
		// split document into chunks and set length
		if (toLower) doc = doc.toLowerCase() + " . "; //insert the ending mark of the last chunk
		
		//split doc into chunks
		//for statistic words
		Pattern ptn = Pattern.compile( "[\\n\\r!#$%&\\'()*+,-./:;\\\\<=>?\\@\\[\\]^`{|}~\\\"]");
		Matcher matcher = ptn.matcher(doc);
		
		int lastMatchStop = 0;
		Vector<String> chunkStrVector = new Vector<String>();
		while (matcher.find()){		
			// check if this punct followed by a white space
//			if (matcher.end() < doc.length()){
//				char followed = doc.charAt(matcher.end());
//				
//				if ((followed != ' ' && doc.charAt(matcher.start()) != '\n')
//						&& followed != '\n' ) {
//					// if following is not white space					
//					continue;
//				}	
//			}			
			
			// get chunk
			String chunk = doc.substring(lastMatchStop, matcher.start());

			if (!chunk.trim().equals("")){
				//System.out.println("chunk:" + chunk);
				chunkStrVector.add(chunk);			
			}
			
			lastMatchStop = matcher.end();
		}		
		
		chunks = new Vector[chunkStrVector.size()];		
		length = 0;
		for (int i = 0; i < chunkStrVector.size(); ++i){
			String nextChunk = chunkStrVector.get(i);
			
			if (toLower) nextChunk = nextChunk.toLowerCase();
			chunks[i] = new Vector<String>();
			
			//for word
			StringTokenizer chunkTk = new StringTokenizer(nextChunk, tokenRegex);
			while (chunkTk.hasMoreTokens()){
				String token = chunkTk.nextToken();
				
				//System.out.println(token);
				chunks[i].add(token);
			}
			
			length += chunks[i].size();
		}
		
	//	long endTime = (new Date()).getTime();
	//	System.out.println("Init doc with length = " + length + " in " + (endTime - startTime) + " miliseconds");
	}
	
	//-------------------------------
	// get and set function
	//-------------------------------
	//set window, accessable only from Count
	protected void setWindow(int w){
		N = w;
	}
	
	public void resetPostion(){
		this.chunkIndex = 0;
		this.chunkOffset = 0;
	}
	
	/*public NGram nextWindow(int window){
		try{
			
			if (chunkIndex >= chunks.length) return null;
			
			while (chunkOffset + window > chunks[chunkIndex].size()){
				if (chunkIndex < chunks.length - 1) chunkIndex++;
				else return null;
				
				chunkOffset = 0;
			}
						
			List<String> next = chunks[chunkIndex].subList(chunkOffset, chunkOffset + window);
			chunkOffset++;
			
			String [] tokens = new String[next.size()];
			tokens = next.toArray(tokens);
			
//			for (int i = 0; i < tokens.length; ++i){
//				System.out.println(tokens[i]);
//			}
			int [] tokenids = new int[tokens.length];
			
			for (int i = 0; i < tokens.length; ++i)
				tokenids[i]= dict.addWord(tokens[i]);
			
			NGram ngram =null;
			
			if (NGram.cache)
				ngram = new NGram(window, tokenids, tokens);
			else
				ngram = new NGram(window, tokenids);
			
			//System.out.println(ngram.toString());
			return ngram;
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}		
		
	}
	*/
	
	public String [] nextWindow(int window){
		
		try{			
			if (chunkIndex >= chunks.length) return null;
			
			while (chunkOffset + window > chunks[chunkIndex].size()){
				if (chunkIndex < chunks.length - 1) chunkIndex++;
				else return null;
				
				chunkOffset = 0;
			}
						
			List<String> next = chunks[chunkIndex].subList(chunkOffset, chunkOffset + window);
			chunkOffset++;
			
			String [] tokens = new String[next.size()];
			tokens = next.toArray(tokens);
			
			return tokens;
			

		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}		
		
	}
	
	
	// shift the window and return all ngram within windows
/*	public Vector<String[]> shiftWindow(int w){
		//long startTime = (new Date()).getTime();
		
		//setWindow(w);
		
		Vector<String[]> ngramVector = new Vector<String[]>();
		NGram next = null;
		resetPostion();
		
		while ((next = nextWindow(w)) != null){
			ngramVector.add(next);
		}
		
		NGram [] ngrams = new NGram[ngramVector.size()];
		
		//long endTime = (new Date()).getTime();
		//System.out.println("Shift doc with length = " + length + " in " + (endTime - startTime) + " miliseconds");
		return ngramVector.toArray(ngrams);
	}*/
	
	public Vector<String[]> shiftWindow(int w){
		//long startTime = (new Date()).getTime();
		
		//setWindow(w);
		
		Vector<String[]> ngramVector = new Vector<String[]>();
		String[] next;
		
		//NGram next = null;
		resetPostion();
		
		while ((next = nextWindow(w)) != null){
			ngramVector.add(next);
		}	
		
		return ngramVector;
	}
	
	public Map<Integer,String[]> nextWindows(int window){
		Map<Integer, String[]> ngram2tokens = new HashMap<Integer, String[]>();
		
		try{			
			if (chunkIndex >= chunks.length) return null;
			
			if (chunkOffset > chunks[chunkIndex].size()){
				if (chunkIndex < chunks.length - 1) chunkIndex++;
				else return null;
				
				chunkOffset = 0;
			}
			
			int chunkInd_ = chunkIndex;
			int chunkOffset_ = chunkOffset;

			for(int w=1; w<= window; w++){
				chunkIndex = chunkInd_;
				chunkOffset = chunkOffset_;
				
				if (chunkOffset + (w - 1) >= chunks[chunkIndex].size()){
					break;
				}
				
				List<String> next = chunks[chunkIndex].subList(chunkOffset, chunkOffset + w);
				//chunkOffset++;
				
//				System.out.println("-----------" + window);
//				for (int i = 0; i < next.size(); ++i){
//					System.out.println(next.get(i));
//				}
				String [] tokens = new String[next.size()];
				tokens = next.toArray(tokens);
				ngram2tokens.put(w, tokens);
				
			}
			
			chunkIndex = chunkInd_;
			chunkOffset = chunkOffset_ + 1;
			
			return ngram2tokens;
			

		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}		
		
	}
	
	
	public Map <Integer,Vector<String[]>> shiftWindows(int w){
		//long startTime = (new Date()).getTime();
		
		//setWindow(w);
		
		Map <Integer,Vector<String[]>> ngramVector = new HashMap <Integer,Vector<String[]>>();
		Map <Integer, String[]> next;
		
		//NGram next = null;
		resetPostion();
		
		for(int i=1;  i<=w; i++)
			ngramVector.put(i, new Vector<String[]>());
		
		
		//System.out.println("---------");
		
		while ((next = nextWindows(w)) != null){
			for(int i=1;  i<=w; i++){
				if (!next.containsKey(i)) break;
				ngramVector.get(i).add(next.get(i));				
			}
		}
		//System.out.println("---------"+next.size());
		
		return ngramVector;
	}
	
}

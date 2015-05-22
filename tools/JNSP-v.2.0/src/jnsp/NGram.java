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

import java.util.StringTokenizer;



public class NGram implements Comparable{
	int N; // number of gram
	
	int [] tokenIds = null;
	
	Long ID = Long.valueOf(0); // index of ngram in NGramSet
	
	double statVal = 0;
	
	protected static boolean cache = false;	
	
	public static final String jointStr = "<>";	
	
	Dictionary dict;
	
	// if freqCombo == null, length of counts is 2^(N-1)
	// else length of counts equals length of freqCombo
	// accessable only from Count
	protected int [] counts = null; 
	
	//protected
	public int frequency = 0;
		
	private String rawStr = null;		
	

	//----------------------------------------
	// initial methods
	//----------------------------------------
	protected NGram(int N, int [] tokens, Dictionary dict){
		this.N = N;
		this.dict = dict;
		this.tokenIds = tokens.clone();	
	}
		
	protected NGram(int N, String [] str, Dictionary dict){
		this.N = N;			
		this.dict = dict;		
		
		if (NGram.cache){
			rawStr = str[0];		
			for(int i=1; i<str.length; i++)
				rawStr +=  jointStr + str[i];
		}

		tokenIds = new int[str.length];
		for (int i = 0; i < tokenIds.length; ++i){
			tokenIds[i] = dict.addWord(str[i]);
		}
		
		//cache = true;
	}	
	
	public Long getID(){
		return ID;
	}
	
	/**
	 * @param id the iD to set
	 */
	public void setID(Long id) {
		ID = id;
	}
	
	
	/**
	 * @return the statVal
	 */
	public double getStatVal() {
		return statVal;
	}

	/**
	 * @param statVal the statVal to set
	 */
	public void setStatVal(double statVal) {
		this.statVal = statVal;
	}

	
	protected void initCounts(String [] freqCombo){
		if (freqCombo == null){
			counts = new int[(int)Math.pow(2, N - 1)];
		}
		else counts = new int[freqCombo.length];
		
		for (int i = 0; i < counts.length; ++i){
			counts[i] = 0;
		}
	}
	
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		 if (o instanceof NGram) {
           NGram n = (NGram) o;
           if (n.N != N){        	  
        	   return (n.N > N ? 1:-1);
        	   //throw new ClassCastException("Cannot compare 2 NGram with different N");
           }
           if (cache)
        	   return n.rawStr.compareTo(rawStr);
           else{
		       for(int i =0; i<tokenIds.length; i++)
		    	   if (tokenIds[i] < n.getTokenId(i)) return -1;
		    	   else if (tokenIds[i] > n.getTokenId(i)) return 1;     
		       
		       return 0;
           }
        }
        throw new ClassCastException("Cannot compare NGram with " + o.getClass().getName());		
	}
	
	
	public boolean equals(Object o){
		return compareTo(o) == 0;
	}
	
	public int hashCode(){
		//System.out.println("*** hashCode"+tokenIds[0]);
		int hash =0;
		for(int i=0; i<tokenIds.length;i++)
			hash= hash*10 + tokenIds[i];		
		
		return hash;//tokenIds.hashCode();
	}
	//------------------------------------------
	// get methods
	//------------------------------------------
	public int getTokenId(int pos){
		if (pos >= N || pos < 0)
			return -1;
		
		return tokenIds[pos];
	}
	
	public int [] getCounts(){	
		int [] copy = counts.clone();
		
		return copy;
	}	
	
	public int getCount(int index){
		if (index >= counts.length || index < 0)
			return -1;
		
		return counts[index];
	}
	
	public int size(){
		return N;
	}
	//------------------------------------------
	// check functions
	//------------------------------------------
	
	public boolean checkToken(int pos, int tokenId){
		if (pos >= N || pos < 0)
			return false;
		
		return (tokenIds[pos]==tokenId);
	}
	
	// Eg: pattern has the form of "1:3" 
	//     this function check whether this Ngram contains the first and third words
	//     of the argument ngram at the same positions
	public boolean checkNGram(String pattern, NGram ngram){				
		StringTokenizer tk = new StringTokenizer(pattern, ":");
		
		//System.out.println(pattern);
		// check valid positions		
		if (tk.countTokens() > this.N || tk.countTokens() == 0){
			return false;
		}
		
		int [] positions = new int[tk.countTokens()];
		for (int i = 0; i < positions.length; ++i){			
			try {
				int pos = Integer.parseInt(tk.nextToken());				
				if (pos < 0 || pos >=N)
					return false;	
				
				positions[i] = pos;
			}
			catch (NumberFormatException e){
				return false;
			}
		}
		
		return checkNGram(positions, ngram);
		
	}
	
	public boolean checkNGram(int [] positions, NGram ngram){
		if (positions.length > this.N)
			return false;
				
		for (int i = 0; i < positions.length; ++i){
			if (!checkToken(positions[i], ngram.getTokenId(positions[i])))
				return false;
		}
		
		return true;
	}
	
	public boolean checkNGram(Integer [] positions, NGram ngram){
		if (positions.length > this.tokenIds.length || positions.length > ngram.tokenIds.length)
			return false;
		
		for (int i = 0; i < positions.length; ++i){
			if (!checkToken(positions[i], ngram.getTokenId(positions[i])))
				return false;
		}
		
		return true;
	}
	//---------------------------------------------
	//toString method
	//---------------------------------------------
	public String toString(){
		if (cache) return rawStr;
		
		String ret="";
		ret = dict.getWord(tokenIds[0]);		
		
		for (int i = 1; i < tokenIds.length; ++i){
			ret += jointStr + dict.getWord(tokenIds[i]);
		}		
		return ret;
	}
	
	public String getNGramStat(){
		String ret = "";				
		if (counts == null) return ret;
		
		ret = dict.getWord(tokenIds[0]);		
		
		for (int i = 1; i < tokenIds.length; ++i){
			ret += jointStr +dict.getWord(tokenIds[i]);
		}
		
		ret += jointStr+counts[0];
		
		for (int i = 1; i < counts.length; ++i){
			ret += " "+counts[i];
		}
		
		return ret;
	}	
	
}

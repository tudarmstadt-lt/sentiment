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

package jnsp.stat;

import java.util.HashMap;
import java.util.Map;

import jnsp.NGram;
import jnsp.Util;

public class TScore3D extends Measure{
	long N; //number of  ngrams
	
	//Specify the hypothesis which we want to test
	// for example: 0:1:2 = 0|1|2 for testing P(w0w1w2) = P(w0) P(w1) P(w2)
	//              0:1:2 = 0:1|2 for testing P(w0w1w2) = P(w0w1) P(w2)--> hypothesis[0] = hypothesis[1] * hypothesis[2] 
	
	String [] hypothesis; 
	Map<String, Integer> freqCombo2Index;
	
	public boolean initilizeStatistic(long N, String hypoPattern, String [] freqCombo){
		//System.out.println("Initilize:" + getStatisticName());
		if (N < 0) return false;		
		this.N = N;
		
		this.hypothesis = Util.parseHypoPattern(hypoPattern);
		
		this.freqCombo2Index = new HashMap<String, Integer>();		
		for (int i = 0; i < freqCombo.length; ++i){
			freqCombo2Index.put(freqCombo[i], i);
		}
		
		return true;
	}
	
	public double calculateStatistic(NGram ngram){
		try {
			int [] count = ngram.getCounts();
			
			int fcId = freqCombo2Index.get(hypothesis[1]);
			double temp = count[fcId];
			for (int i = 2; i < hypothesis.length - 1; ++i){
				fcId = freqCombo2Index.get(hypothesis[i]);
				temp = (temp * count[fcId]) / N; 
			}
			
			double tscore = (count[0] - temp) / Math.sqrt(count[0]);
			return tscore;
		}
		catch (Exception e){
			return 0;
		}
	}
	
	public String getStatisticName(){
		return "TScore3D";
	}
}

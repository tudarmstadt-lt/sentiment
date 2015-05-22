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


import jnsp.NGram;

public class TScore2D extends Measure {

	@Override
	/**
	 * Calculate tscore for this ngram
	 * 			w0     |   not w0
	 * --------------------------
	 * w1      | n00   |   n10  | f(1)
	 * ---------------------------
	 * not w1  | n01   | n11 =  N - n00 - n01 - n10
	 * --------------------------
	 * 			 f(0)
	 * 			
	 * n00 = f(0,1) = count(0)
	 * n01 = f(0) - n00
	 * n10 = f(1) - n00
	 * 
	 * f(0,1) = count(11) (here, 11 is in binary) = count(0) 
	 * f(0)   = count(01)      = count (1)
	 * f(1)   = count (10)     = count (2)
	 * 
	 *  null Hypothesis: 
	 * 	- the samples are drawn from a normal distribution with mean muy
	 *  - P(w0, w1) = P(w0)P(w1) = f(0)/N * f(1)/N
	 *  
	 *  Set p(w0)p(w1)
	 *    
	 *  If the null hypothesis is true, then the process of randomly generating bigrams
	 *  of words and assigning 1 to the outcome w0w1 and 0 the any other outcome is 
	 *  a Bernoulli trail with probability = p
	 *  
	 * t-score = (x~ - muy)/SQRT(s^2/N) 
	 * 
	 * x~: observed mean = p(w0w1)= n00/N
	 * muy: expected mean ~ p(w0)p(w1) = f(0) * f(1) / N^2(mean of a bernoulli random variable)
	 * s^2: sample variance = p(1-p)~ p(w0w1) = n00/N (because p is too small)
	 * 
	 * t-score = (n00 - f(0)f(1)/N)/sqrt(n00)
	 * 
	
	 * 
	 */
	public double calculateStatistic(NGram ngram) {
		// TODO Auto-generated method stub
		if (ngram.size() != 2) {
			System.out.println("tsCORE2D: Only handle bigram!");
			return 0;
		}
				
	
//		int [] count = ngram.getCounts();
		//long start1 = (new Date()).getTime();		
		
		double temp = (ngram.getCount(1) * ngram.getCount(2))/N;		
		double tscore = (ngram.getCount(0) - temp) / Math.sqrt(ngram.getCount(0));
		//long end1 = (new Date()).getTime();
		
		//System.out.println("..." + (end1 - start1));
		return tscore;
	}

	@Override
	public String getStatisticName() {
		// TODO Auto-generated method stub
		return "TScore2D";
	}

}

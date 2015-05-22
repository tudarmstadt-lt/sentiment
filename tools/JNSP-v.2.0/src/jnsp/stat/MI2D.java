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

public class MI2D extends Measure {

	@Override
	/**
	 * Calculate the mutual information for this bigram
	 * 			w0     |   not w0
	 * --------------------------
	 * w1      | n00   |   n10
	 * ---------------------------
	 * not w1  | n01   |  n11 =  N - n00 - n01 - n10
	 * 
	 * n00 = f(0,1)
	 * n10 = f(1) - f(0,1) or f(1) = n00 + n10
	 * n01 = f(0) - f(0,1) or f(0) = n00 + n01
	 * 
	 * f(0,1) = count(11) (here, 11 is in binary) = count(0) 
	 * f(0)   = count(01)      = count (1)
	 * f(1)   = count (10)     = count (2)
	 * MI2D = log_2 (p(w0,01)/P(w0)P(w1))
	 * 	    = log_2 ( f(0,1)/N) / (f(0)/N) * (f(1)/N) 
	 */
	public double calculateStatistic(NGram ngram) {
		// TODO Auto-generated method stub
		if (ngram.size() != 2) {
			System.out.println("Only handle bigram!");
			return 0;
		}
				
		int [] count = ngram.getCounts();
		double MI = Math.log10((N /(count[1] * count[2])) * count[0])/ Math.log10(2);
		return MI;
	}

	@Override
	public String getStatisticName() {
		// TODO Auto-generated method stub
		return "MI2D";
	}

}
